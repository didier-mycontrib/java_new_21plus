package tp.main;


import java.util.ArrayList;

import org.reactivestreams.Subscriber; //version plus élaborée que java.util.concurrent.Flow.Subscriber
import org.reactivestreams.Subscription;//version plus élaborée que java.util.concurrent.Flow.Subscription

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BasicApp {
	private String result="";

	public static void main(String[] args) {
		helloWorldReactor();
		BasicApp basicApp = new BasicApp();
		basicApp.withOnNextOnErrorOnCompleteCallbacks();
		collectingElementsV1();
		collectingElementsV2();
	}
	
	public static void helloWorldReactor() {
		//Mono is a sort of Observable stream with 0 or 1 element
		Mono<String> monoHello = Mono.just("hello world Reactor");
		//org.reactivestreams.Publisher<String> pubHello = Mono.just("hello world Reactor"); //Mono is a sort of reactivestreams Publisher 
		monoHello.subscribe(s -> System.out.println(s));
	}
	
	public void withOnNextOnErrorOnCompleteCallbacks() {
		result="";
		//Flux is a sort of Observable stream with 0 or n elements
		String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
		Flux<String> fluxLetters = Flux.fromArray(letters); 
		//org.reactivestreams.Publisher<String> pubLetters = Flux.fromArray(letters);//Flux is a sort of reactivestreams Publisher
		fluxLetters.subscribe(
		  letter -> result += letter,  //OnNext
		  Throwable::printStackTrace, //OnError
		  () -> result += "_completed" //OnCompleted
		);
		System.out.println("result="+result);//abcdefg_completed
	}
	
	public static void collectingElementsV1() {
		var resultElements = new ArrayList<>();

		Flux.just(1, 2, 3, 4)
		  .log()
		  .subscribe(/*resultElements::add*/ e -> resultElements.add(e));
	
		System.out.println("resultElements="+resultElements);
	}
	
	public static void collectingElementsV2() {
		//juste pour expliquer les rapprochements avec les flow asynchrones de java 9
		var resultElements = new ArrayList<>();
		
		//NB: java 8 synchrounous streams is a pull model
		//    reactor or rxJava streams ares "push models"

		Flux.just(1, 2, 3, 4)
		  .log()
		  .subscribe(new Subscriber<Integer>() {
			    @Override
			    public void onSubscribe(Subscription s) {
			      s.request(Long.MAX_VALUE);
			    }

			    @Override
			    public void onNext(Integer e) {
			    	resultElements.add(e);
			    }

			    @Override
			    public void onError(Throwable t) {}

			    @Override
			    public void onComplete() {}
			});
	
		System.out.println("resultElements="+resultElements);
	}

}
