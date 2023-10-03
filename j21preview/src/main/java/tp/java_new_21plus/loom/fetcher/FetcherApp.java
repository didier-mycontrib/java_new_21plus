package tp.java_new_21plus.loom.fetcher;

import java.util.List;

import tp.java_new_21plus.data.TownWithWeather;



public class FetcherApp {
	
   public static void main(String[] args) {
	   System.out.println("FetcherApp");
	   String mode= TownInfoFetcherWithVirtualThread.WITH_VIRTUAL_THREAD;//1700ms environ
	   //String mode= TownInfoFetcherWithVirtualThread.WITH_THREAD;//1850ms
	   //String mode= TownInfoFetcherWithVirtualThread.WITHOUT_THREAD;//8800ms environ
	   System.out.println("mode="+mode);
	   fetchTowsWithFetcher(new TownInfoFetcherWithVirtualThread(mode));
   }
   
   public static void fetchTowsWithFetcher(TownInfoFetcher townInfoFetcher) {
	   System.out.println("With townInfoFetcher of class " + townInfoFetcher.getClass().getSimpleName());
	   long timeBefore = System.currentTimeMillis();
	   List<TownWithWeather> towns = townInfoFetcher.fetchTowns();
	   long timeAfter = System.currentTimeMillis();
	   System.out.println("towns fetch in " + (timeAfter-timeBefore) + "ms");
	   for(TownWithWeather town : towns) {
		   System.out.println("\t" + town);
	   }
	   System.out.println("==============");
   }
}
