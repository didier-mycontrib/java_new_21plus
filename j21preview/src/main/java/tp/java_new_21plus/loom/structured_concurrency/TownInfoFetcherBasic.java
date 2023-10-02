package tp.java_new_21plus.loom.structured_concurrency;

import java.util.List;

import tp.java_new_21plus.loom.structured_concurrency.data.TownWithWeather;

public class TownInfoFetcherBasic extends TownInfoFetcher{

	@Override
	public List<TownWithWeather> fetchTowns(){
		return fetchTownsWithoutStructuredConcurrency();
	}
	
	public List<TownWithWeather> fetchTownsWithoutStructuredConcurrency() {
    	//return buildZipList().stream()
		return buildZipList().parallelStream()
    	              .map( zip -> new TownWithWeather(zip))
    	              .map( townWithZip -> fetchTownNameAndPopulationFromZip(townWithZip))
    	              .map( town -> fetchTownLatitudeAndLongitudeFromZip(town))
    	              .map( townWithCoords -> fetchTownWeatherFromCoords(townWithCoords))
    	              //.map( townWithCoords -> fetchTownElevationFromCoords(townWithCoords))
    	              .map( townWithCoords -> fakeFetchTownElevationFromCoords(townWithCoords))
    	              .toList();
    }
}
