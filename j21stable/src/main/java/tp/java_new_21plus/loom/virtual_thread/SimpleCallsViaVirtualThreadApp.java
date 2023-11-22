package tp.java_new_21plus.loom.virtual_thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tp.java_new_21plus.dto.Zippopotam;
import tp.java_new_21plus.http2.MyHttp2Util;

public class SimpleCallsViaVirtualThreadApp {
	
	private static MyHttp2Util myHttp2Util = MyHttp2Util.INSTANCE;
	
	public static List<String> buildZipBigList() {
		List<String> fullZipList = new ArrayList<>();
		List<String> zipList = Arrays.asList(
				             "33000",  "06000",  "67000",  "13002" , "13003" , "13004",
				             "14000" , "76100" , "87000" , "25000" , "54000" , "13001",
				             "80000" , "24000" , "27000" , "31000" , "60000" , "27200",
				             "38000" , "59000" , "44000" , "37000" , "57000" , "28000");
		for(String z: zipList)
			fullZipList.add(z);
		for(int i=1;i<=9;i++) {
			fullZipList.add("7500"+i);
			fullZipList.add("6900"+i);
		   }
		for(int i=10;i<=20;i++)
			fullZipList.add("750"+i);
		return fullZipList;
	}
	
	
	
	public static ExecutorService executorService(boolean withVirtualThread) {
		if(withVirtualThread)
		     return Executors.newVirtualThreadPerTaskExecutor();
		else
			return Executors.newThreadPerTaskExecutor(Thread.ofPlatform().factory());
	}

	public static void main(String[] args) {
		List<String> zipList = buildZipBigList(); 
		System.out.println("zipList_size="+zipList.size()+ " zipList="+zipList);

		 boolean withDisplay=true;
		// boolean withDisplay=false;
		 
        //boolean withVirtualThread=false; //environ 1000 ms
        boolean withVirtualThread=true; //environ 700 ms  (à priori un gain de l'ordre de 25 à 30% , si pas d'erreur ????)
		
		int nbMs = fetchAllZippopotam(zipList,withVirtualThread,withDisplay);//withVirtualThread=true,doDisplay=false;
		System.out.println("fetchAllZippopotam,withVirtualThread="+withVirtualThread+ ", temps d'execution (ms)=" + nbMs);
			
	}
	
	public static int fetchAllZippopotamV1(List<String> zipList,boolean withVirtalThread , boolean doDisplay) {
		int nbResponses = 0;
		Long td = System.nanoTime();
		try (var executor = SimpleCallsViaVirtualThreadApp.executorService(withVirtalThread)) {
			List<Future<Zippopotam.Response>> fList = new ArrayList<>();
			for(String codePostal : zipList) {
				Future<Zippopotam.Response> f = executor.submit( () -> fetchZippopotam(codePostal));
				fList.add(f);
			}
			executor.shutdown();//action différée lorsque tout sera fini
			try {
				  executor.awaitTermination(60, TimeUnit.SECONDS);  //temps d'attente maxi (timeout)
				} catch (InterruptedException e) {
				  System.err.println(e.getMessage());
				}
			for(Future<Zippopotam.Response> f : fList) {
				try {
					Zippopotam.Response resp = f.get();
					nbResponses++;
					if(doDisplay)
						System.out.println(resp);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			Long tf = System.nanoTime();
			System.out.println("fetchAllZippopotam, nbResponses="+nbResponses);
			return (int) ((tf-td)/ 1000000);
		   }
		
	}
	
	public static int fetchAllZippopotam(List<String> zipList,boolean withVirtalThread , boolean doDisplay) {
		int nbRequests = 0;
		int nbResponses = 0;
		Long td = System.nanoTime();
		try (var executor = SimpleCallsViaVirtualThreadApp.executorService(withVirtalThread)) {
			CompletionService<Zippopotam.Response> completionService = 
					new ExecutorCompletionService<>(executor);
			for(String codePostal : zipList) {
				completionService.submit( () -> fetchZippopotam(codePostal));
				nbRequests++;
			}
			executor.shutdown();//action différée lorsque tout sera fini
			for(nbResponses=0;nbResponses<nbRequests;nbResponses++) {
				try {
					//Future<Zippopotam.Response> futureResp = completionService.take(); //wait for first terminated (but no timeout)
					Future<Zippopotam.Response> futureResp = completionService.poll(60, TimeUnit.SECONDS); //trying retreive first terminated (with timeout for small waiting if necessary)
					Zippopotam.Response resp = futureResp.get();
					if(doDisplay)
						System.out.println(resp);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			Long tf = System.nanoTime();
			System.out.println("fetchAllZippopotam, nbResponses="+nbResponses);
			return (int) ((tf-td)/ 1000000);
		   }
		
	}
	
	public static Zippopotam.Response fetchZippopotam(String codePostal) {
		Zippopotam.Response zippopotamResponse = myHttp2Util.fetch("http://api.zippopotam.us/fr/"+codePostal, 
				                                                   Zippopotam.Response.class);
		return zippopotamResponse;
	}
	
	

}
