package tp.java_new_21plus.loom.structured_concurrency;

import java.util.Arrays;
import java.util.List;

import tp.java_new_21plus.data.TownWithWeather;
import tp.java_new_21plus.loom.fetcher.TownInfoFetcher;
import tp.java_new_21plus.loom.fetcher.TownInfoFetcherBasic;

/*
 StructuredTaskScope (pour l'instant en mode "preview" en java 21)
 correpond à une sorte de Tache/Objectif pouvant généralement se décomposer 
 en sous-tâche/sous-objectif .
 Plusieurs niveaux sont possibles (tachePrincipale , sousTaches, sousSousTaches)
 Les sous-taches pourront être lancées de manières concurrentes(en //) par des virtualThread
 */

public class StructuredConcurrencyApp {
	
  
   public static void main(String[] args) {
	   //in run configuration : VM arguments
	   //--enable-preview
	   System.out.println("StructuredConcurrencyApp");
	   //fetchTownsWithFetcher(new TownInfoFetcherBasic());//environ 7800ms avec .stream() et 2000ms avec .parallelStream()
	   fetchTownsWithFetcher(new TownInfoFetcherWithStructuredConcurrency());//1456ms with structuredConcurrency and virtualThreads
	   
	   testQuickerSubTask();
	   testAllFinishedParallelSubTask();
   }
   
   public static void testQuickerSubTask() {
	 try {
		double res = WithFirstFinishedSubTask.INSTANCE.quickerOfSlowSquareRoot(9.0);
		System.out.println("quickerOfSlowSquareRoot() returning "+res);
	} catch (Exception e) {
		System.err.println("quickerOfSlowSquareRoot() throwing "+e.getMessage());
	}
   }
   
   public static void testAllFinishedParallelSubTask() {
		 try {
			List<Double> doubleList = Arrays.asList(9.0 , 4.0 , 25.0 , 16.0);
			System.out.println("doubleList="+doubleList);
			List<Double> resultList = AllFinishedSubTasks.INSTANCE.doubleListSlowSquareRoot(doubleList);
			System.out.println("doubleListSlowSquareRoot() returning "+resultList);
		} catch (Exception e) {
			System.err.println("doubleListSlowSquareRoot() throwing "+e.getMessage());
		}
	   }
   
   
   public static void fetchTownsWithFetcher(TownInfoFetcher townInfoFetcher) {
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
