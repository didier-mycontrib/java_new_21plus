package tp.loom.structured_concurrency;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import tp.loom.data.TownWithWeather;
import tp.loom.fetcher.TownInfoFetcher;

public class TownInfoFetcherWithStructuredConcurrency extends TownInfoFetcher{

	@Override
	public List<TownWithWeather> fetchTowns() {
		return fetchTownsWithStructuredConcurrencyAndVirtualThreads();//1456ms with structuredConcurrency and virtualThreads
		//return fetchTownsWithStructuredConcurrencyAndParallelStream();//1732ms with structuredConcurrency and parallelStream
	}
	
	public List<TownWithWeather> fetchTownsWithStructuredConcurrencyAndVirtualThreads() {
		List<String> zipList = buildZipList();
		List<TownWithWeather> townList = new ArrayList<>();
		ThreadFactory threadFactory = Thread.ofVirtual().name("fetcher-", 0).factory();
		try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
			 List<Future<TownWithWeather>> futureList = new ArrayList<>();
	    	 for(String zip : zipList) {
				Future<TownWithWeather> futureTownWithWeather = executor.submit(
						()->{ TownWithWeather town = fetchTownWithNamePopulationAndCoordsFromZip(zip); 
							  return fetchTownWithWeatherAndElevationFromTownWithCoords(town);
						    }
						);
				futureList.add(futureTownWithWeather);
	    	 }
	    	 for(Future<TownWithWeather> futureTownWithWeather :futureList ) {
				TownWithWeather town = futureTownWithWeather.get();
				townList.add(town);
		    }
			}catch(Exception ex) {
				System.out.println("exception: " + ex.getMessage());
			}
	     return townList;
	}
	
	public List<TownWithWeather> fetchTownsWithStructuredConcurrencyAndParallelStream() {
    	return buildZipList().parallelStream()
	              .map( zip -> fetchTownWithNamePopulationAndCoordsFromZip(zip))
	              .map( town -> fetchTownWithWeatherAndElevationFromTownWithCoords(town))
	              .toList();
    }
    

    public TownWithWeather fetchTownWithNamePopulationAndCoordsFromZip(String zip) {
		TownWithWeather initialTown = new TownWithWeather(zip);
		TownWithWeather town = new TownWithWeather(initialTown);
		try (var scope = StructuredTaskScope.open(cf -> cf.withTimeout(Duration.ofMillis(3000)))) {

    		StructuredTaskScope.Subtask<TownWithWeather> subTaskTownWithNameAndPopulation = 
		    		scope.fork(() -> TownInfoFetcher.fetchTownNameAndPopulationFromZip(initialTown));
    		StructuredTaskScope.Subtask<TownWithWeather> subTaskTownWithCoord = 
		    		scope.fork(() -> TownInfoFetcher.fetchTownLatitudeAndLongitudeFromZip(initialTown));
		    scope.join();

		    TownWithWeather townWithNameAndPopulation = subTaskTownWithNameAndPopulation.get();
		    town.setName(townWithNameAndPopulation.getName());
		    town.setPopulation(townWithNameAndPopulation.getPopulation());
		    TownWithWeather townWithCoords = subTaskTownWithCoord.get();
		    town.setLatitude(townWithCoords.getLatitude());
		    town.setLongitude(townWithCoords.getLongitude());
		}catch(InterruptedException ex){
			ex.printStackTrace();
    	}catch(ExecutionException eEx){
    		eEx.printStackTrace();
    	}
    	return town;
    }
    
    public TownWithWeather fetchTownWithWeatherAndElevationFromTownWithCoords(TownWithWeather townWithCoords) {
    	TownWithWeather town = new TownWithWeather(townWithCoords);
    	try (var scope = StructuredTaskScope.open()) {
    		StructuredTaskScope.Subtask<TownWithWeather> subtaskTownWithWeather = 
		    		scope.fork(() -> fetchTownWeatherFromCoords(townWithCoords));
    		StructuredTaskScope.Subtask<TownWithWeather> subtaskTownWithElevation = 
		    		//scope.fork(() -> fetchTownElevationFromCoords(townWithCoords));
		    		scope.fork(() -> fakeFetchTownElevationFromCoords(townWithCoords));
		    scope.join();
		    TownWithWeather townWithWeather = subtaskTownWithWeather.get();
		    town.setTemperature(townWithWeather.getTemperature());
		    town.setWeatherDescription(townWithWeather.getWeatherDescription());
		    TownWithWeather TownWithElevation = subtaskTownWithElevation.get();
		    town.setElevation(TownWithElevation.getElevation());
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}catch(ExecutionException eEx){
			eEx.printStackTrace();
		}
    	return town;
    }

	
}
