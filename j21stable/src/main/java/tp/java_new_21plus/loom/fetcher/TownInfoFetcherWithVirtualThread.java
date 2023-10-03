package tp.java_new_21plus.loom.fetcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import tp.java_new_21plus.data.TownWithWeather;

public class TownInfoFetcherWithVirtualThread extends TownInfoFetcher{
	
	public static final String WITHOUT_THREAD = "WithoutThread";
	public static final String WITH_VIRTUAL_THREAD = "WithVirtualThread";
	public static final String WITH_THREAD = "WithThread";
	
	public String mode;
	
	public static List<String> buildZipList() {
		return Arrays.asList("75001","33000","06000","69001","67000", 
				             "80000" , "24000" , "27000" , "31000" , "13001" ,
				             "38000" , "59000" , "44000" , "37000" , "57000" ,
				             "14000" , "76100" , "87000" , "25000" , "54000");
	}
	
	public TownInfoFetcherWithVirtualThread() {
		this(WITH_VIRTUAL_THREAD);
	}
	
	public TownInfoFetcherWithVirtualThread(String mode) {
		this.mode=mode;
	}

	@Override
	public List<TownWithWeather> fetchTowns(){
		switch(this.mode){
			case WITHOUT_THREAD: return fetchTownsWithoutThreads();
			case WITH_THREAD:
			case WITH_VIRTUAL_THREAD: return fetchTownsWithVirtualThreads();
			default: return new ArrayList<>();
		}
		
	}
	
	public List<TownWithWeather> fetchTownsWithoutThreads() {
		List<String> zipList = buildZipList();
		List<TownWithWeather> townList = new ArrayList<>();
	    for(String zip : zipList) {
			TownWithWeather town = fetchTownInfosFromZip(zip);
			townList.add(town);
	    }
	    return townList;
	}
	
	public List<TownWithWeather> fetchTownsWithVirtualThreads() {
		List<String> zipList = buildZipList();
		List<TownWithWeather> townList = new ArrayList<>();
		ThreadFactory threadFactory = null;
		if(mode == WITH_VIRTUAL_THREAD) 
			threadFactory = Thread.ofVirtual().name("fetcher-", 0).factory();
		else
			threadFactory = new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r);
				}
			};
		
	     try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
	    	 List<Future<TownWithWeather>> futureList = new ArrayList<>();
	    	 for(String zip : zipList) {
				Future<TownWithWeather> futureTownWithWeather = executor.submit(
						()->fetchTownInfosFromZip(zip) 
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
	
	public TownWithWeather fetchTownInfosFromZip(String zip) {
    	
		TownWithWeather town =  new TownWithWeather(zip);
    	town = fetchTownNameAndPopulationFromZip(town);
    	town = fetchTownLatitudeAndLongitudeFromZip(town);
    	town = fetchTownWeatherFromCoords(town);
    	town =  fakeFetchTownElevationFromCoords(town);
    	return town;            
    }
	
	
}
