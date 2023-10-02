package tp.java_new_21plus.loom.structured_concurrency;

import java.util.List;

import tp.java_new_21plus.loom.structured_concurrency.data.TownWithWeather;

/*
 StructuredTaskScope (pour l'instant dans jdk.incubator.concurrent)
 correpond à une sorte de Tache/Objectif pouvant généralement se décomposer 
 en sous-tâche/sous-objectif .
 Plusieurs niveaux sont possibles (tachePrincipale , sousTaches, sousSousTaches)
 Les sous-taches pourront être lancées de manières concurrentes(en //) par des virtualThread
 */

public class StructuredConcurrencyApp {
	
  
   public static void main(String[] args) {
	   //in run configuration : VM arguments
	   //--add-modules=jdk.incubator.concurrent
	   System.out.println("StructuredConcurrencyApp");
	   //fetchTowsWithFetcher(new TownInfoFetcherBasic());//environ 3000ms avec .stream() et 1200ms avec .parallelStream()
	   fetchTowsWithFetcher(new TownInfoFetcherWithStructuredConcurrency()); //environ 1500ms avec .stream() et 300ms avec .parallelStream()
       //donc , sans aucun // 3000ms 
	   //et avec beaucoup de // (StructuredConcurrency + parallelStream) , 300ms soit  DIX FOIS PLUS RAPIDE ENVIRON
	   
	   /*
	   try {
		double res = WithFirstFinishedSubTask.INSTANCE.quickerOfSlowSquareRoot(9.0);
		System.out.println("quickerOfSlowSquareRoot() returning "+res);
	} catch (Exception e) {
		System.err.println("quickerOfSlowSquareRoot() throwing "+e.getMessage());
	}
	*/
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
