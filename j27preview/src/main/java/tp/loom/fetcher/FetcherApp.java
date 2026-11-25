package tp.loom.fetcher;

import java.util.List;

import tp.loom.data.TownWithWeather;



public class FetcherApp {
	
   public static void main(String[] args) {
	   System.out.println("FetcherApp");
	   //String mode= TownInfoFetcherWithVirtualThread.WITH_VIRTUAL_THREAD;//1226 , 1204 , 1148 , 1203 ms environ
	   String mode= TownInfoFetcherWithVirtualThread.WITH_THREAD;//1253 , 1439 , 1450 , 1243 , 1209 , 1393 ms
	   //String mode= TownInfoFetcherWithVirtualThread.WITHOUT_THREAD;//5568ms environ
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
