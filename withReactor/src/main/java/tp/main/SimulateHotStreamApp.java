package tp.main;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;
import tp.data.Product;
import tp.util.ProductUtil;

public class SimulateHotStreamApp {
	
	int cpt=0;
	int cptProd=0;

	public static void main(String[] args) {
		SimulateHotStreamApp shsa = new SimulateHotStreamApp();
		shsa.simulateInfiniteHotStreamV1();
		shsa.simulateInfiniteHotStreamV2();
		pause(6*2000);//pour attendre fin des autres threads
		System.out.println("end of main");
	}
	
	public static void pause(long nbMs) {
		try {
			Thread.sleep(nbMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void simulateInfiniteHotStreamV1() {
		
		ConnectableFlux<Long> connectablePublisher = Flux.create((FluxSink<Long> fluxSink)-> {
			int i=0;
		    while(i++ <4) {
		        fluxSink.next(System.currentTimeMillis());
		        pause(2000);
		    }
		    fluxSink.complete();
		})
		  //.sample(Duration.ofSeconds(2)) //not needed if pause(2000)
		  .subscribeOn(Schedulers.parallel())
		  .publish();
		
		//connect connectablePublisher to several subscribers
		
		connectablePublisher.subscribe(e -> System.out.println("##" + e)); 
		connectablePublisher.subscribe(e -> System.out.println("**" + e));
		cpt=0;
		connectablePublisher.subscribe(e -> {cpt++;} ,
				                       Throwable::printStackTrace ,
				                       ()->{ System.out.println("completed - cpt="+cpt);});
		
		connectablePublisher.connect(); //start emitting , ( blocking if not .subscribeOn(Schedulers.parallel()))
		System.out.println("suite immediate 1 / main thread");
	}
	
public void simulateInfiniteHotStreamV2() {
		
		ConnectableFlux<Product> connectablePublisher = Flux.create((FluxSink<Product> fluxSink) -> {
			var sourceElements = ProductUtil.initSampleProductList();
			/*
			int i = sourceElements.size() - 1;
		    while(i>=0) {
		        fluxSink.next(sourceElements.get(i));
		        i--;
		        pause(2000);
		    }
		    */
			sourceElements.forEach( p-> { pause(2000); fluxSink.next(p); });
		    fluxSink.complete();
		})
		  //.sample(Duration.ofSeconds(2)) //not needed if pause(2000)
		   .subscribeOn(Schedulers.parallel())	
		  .publish();
		
		//connect connectablePublisher to several subscribers
		
		connectablePublisher
		     .filter(p -> p.getPrice() <= 300 )
		    /* .sort((p1,p2)-> p1.getLabel().compareToIgnoreCase(p2.getLabel())) //sort imply wait for completed state */ 
		     .subscribe(e -> System.out.println(e)); 
		cptProd=0;
		connectablePublisher.subscribe(e -> {cptProd++;} ,
				                       Throwable::printStackTrace ,
				                       ()->{ System.out.println("completed - cptProd="+cptProd);});
		
		connectablePublisher.connect(); //start emitting
		System.out.println("suite immediate 2 / main thread");
      }

}
