package tp.java_new_21plus.loom.structured_concurrency;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;

import tp.java_new_21plus.data.TownWithWeather;
import tp.java_new_21plus.loom.fetcher.TownInfoFetcher;

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
    
	/*
	 ShutdownOnFailure captures the first exception and shuts down the task scope. 
	 This class is intended for cases where the results of all subtasks are required 
	 (“invoke all”); 
	 if any subtask fails then the results of other unfinished subtasks are no longer needed. 
	 If defines methods to throw an exception if any of the subtasks fail
	 */
	
    public TownWithWeather fetchTownWithNamePopulationAndCoordsFromZip(String zip) {
		TownWithWeather initialTown = new TownWithWeather(zip);
		TownWithWeather town = new TownWithWeather(initialTown);
    	try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    		
    		StructuredTaskScope.Subtask<TownWithWeather> subTaskTownWithNameAndPopulation = 
		    		scope.fork(() -> fetchTownNameAndPopulationFromZip(initialTown));
    		StructuredTaskScope.Subtask<TownWithWeather> subTaskTownWithCoord = 
		    		scope.fork(() -> fetchTownLatitudeAndLongitudeFromZip(initialTown));
		    //scope.join();//waiting for all subtask terminate
		    scope.joinUntil(Instant.now().plusSeconds(3)); //with TimeoutException if all is not finish 3s later now
		    scope.throwIfFailed(e -> new RuntimeException("cannot fetch Name,Population,Latitude,Longitude from zip"));
		    TownWithWeather townWithNameAndPopulation = subTaskTownWithNameAndPopulation.get();
		    town.setName(townWithNameAndPopulation.getName());
		    town.setPopulation(townWithNameAndPopulation.getPopulation());
		    TownWithWeather townWithCoords = subTaskTownWithCoord.get();
		    town.setLatitude(townWithCoords.getLatitude());
		    town.setLongitude(townWithCoords.getLongitude());
		}catch(InterruptedException ex){
			ex.printStackTrace();
    	}catch(TimeoutException toEx){
    		toEx.printStackTrace();
    	}
    	return town;
    }
    
    public TownWithWeather fetchTownWithWeatherAndElevationFromTownWithCoords(TownWithWeather townWithCoords) {
    	TownWithWeather town = new TownWithWeather(townWithCoords);
    	try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
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
		}
    	return town;
    }

	
}
