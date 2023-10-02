package tp.java_new_21plus.loom.structured_concurrency;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;

import tp.java_new_21plus.loom.structured_concurrency.data.TownWithWeather;

public class TownInfoFetcherWithStructuredConcurrency extends TownInfoFetcher{

	@Override
	public List<TownWithWeather> fetchTowns() {
		return fetchTownsWithStructuredConcurrency();
	}
	
	public List<TownWithWeather> fetchTownsWithStructuredConcurrency() {
    	//return buildZipList().stream()
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
