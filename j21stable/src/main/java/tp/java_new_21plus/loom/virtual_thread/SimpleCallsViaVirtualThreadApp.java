package tp.java_new_21plus.loom.virtual_thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tp.java_new_21plus.dto.Zippopotam;
import tp.java_new_21plus.http2.MyHttp2Util;

public class SimpleCallsViaVirtualThreadApp {
	
	private static MyHttp2Util myHttp2Util = MyHttp2Util.INSTANCE;
	
	public static List<String> buildZipBigList(int nbRepeats) {
		List<String> fullZipList = new ArrayList<>();
		List<String> zipList = Arrays.asList("33000","06000","67000", 
				             "80000" , "24000" , "27000" , "31000" , "60000" , "27200",
				             "38000" , "59000" , "44000" , "37000" , "57000" , "28000",
				             "14000" , "76100" , "87000" , "25000" , "54000");
		for(String z: zipList)
			fullZipList.add(z);
		for(int i=1;i<=9;i++) {
			fullZipList.add("7500"+i);
			fullZipList.add("6900"+i);
			fullZipList.add("1300"+i);
		   }
		for(int i=10;i<=21;i++)
			fullZipList.add("750"+i);
		List<String> fullZipList2 = new ArrayList<>();
		//nbRepeats fois même sous série de codePostaux dans fullZipList2
		for(int i=0;i<nbRepeats;i++)
		  for(String z: fullZipList)fullZipList2.add(z);
		return fullZipList2;
	}
	
	public static ExecutorService executorService(boolean withVirtualThread) {
		return null; //code temporaire à remplacer
		//A faire en TP:
		//créer et retourner un Executor utilisant ou pas des "virtualThreads" 
		//via une instruction du genre Executors.new....PerTaskExecutor();
	}

	public static void main(String[] args) {
		List<String> zipList = buildZipBigList(4); //platformThread:1933ms , virtualThread:557ms
		//List<String> zipList = buildZipBigList(16); //platformThread:5411ms , virtualThread:1446ms
		System.out.println("zipList_size="+zipList.size()+ " zipList="+zipList);
		
		//int nbMs = fetchAllZippopotam(zipList,false,true);//withVirtualThread=false,doDisplay=true;
		int nbMs = fetchAllZippopotam(zipList,false,false);//withVirtualThread=false,doDisplay=false;
		System.out.println("fetchAllZippopotam,withVirtualThread=false, temps d'execution (ms)=" + nbMs);
		//nbMs = fetchAllZippopotam(zipList,true,true);//withVirtualThread=true,doDisplay=true;
		nbMs = fetchAllZippopotam(zipList,true,false);//withVirtualThread=true,doDisplay=false;
		System.out.println("fetchAllZippopotam,withVirtualThread=true, temps d'execution (ms)=" + nbMs);
	}
	
	public static int fetchAllZippopotam(List<String> zipList,boolean withVirtalThread , boolean doDisplay) {
		//A FAIRE EN TP:
		//dans un try(var executor = ...){  }
		     //soumettre (via une boucle sur zipList) , une tâche de type fetchZippopotam(codePostal)
		     /*
		     //attendre la fin de toutes l'execution de toutes les tâches via un code de ce genre
		      executor.shutdown();//action différée lorsque tout sera fini
			  try {
				  executor.awaitTermination(60, TimeUnit.SECONDS);  //temps d'attente maxi (timeout)
				} catch (InterruptedException e) {
				  System.err.println(e.getMessage());
			   }
		      */
		     //dans une boucle sur une liste de Future<Zippopotam.Response>
		     //récupérer les résultats , les afficher à la console si doDisplay vaut true
		     //    incrémenter le compteur de réponses obtenues
		
		//en  fin de méthode , afficher le nombre de réponses obtenues
		//et retourner le temps d'exécution (en ms) de toute la méthode fetchAllZippopotam()
		return 0;//code temporaire à replacer
	}
	
	public static Zippopotam.Response fetchZippopotam(String codePostal) {
		Zippopotam.Response zippopotamResponse = myHttp2Util.fetch("http://api.zippopotam.us/fr/"+codePostal, 
				                                                   Zippopotam.Response.class);
		return zippopotamResponse;
	}
	
	

}
