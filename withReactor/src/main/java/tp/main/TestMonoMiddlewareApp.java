package tp.main;

import reactor.core.publisher.Mono;
import tp.data.MyBag;

public class TestMonoMiddlewareApp {
	

	public static void displayMono(Mono<? extends Object> mono) {
	    mono.subscribe((data)->System.out.println("mono data="+data), 
		               (err)->System.out.println("mono err="+err));
	}
	
	//public static Mono<Object> buildMono() {
	public static Mono<String> buildMonoString() {
		//Mono<Object> mono = Mono.empty();
		Mono<String> mono = Mono.empty();
		mono = Mono.just("data1");
		mono = Mono.error(new RuntimeException("ex1"));
		return mono;
	}
	
	/*
	public static Mono<Object> adjustMonoObject(Mono<Object> mono) {
		//return mono.map((obj)->obj.toString()); //operations très limitées sur vague Object 
	   return mono.defaultIfEmpty("default_data");
	}
	*/
	
	public static Mono<String> adjustMonoString(Mono<String> mono) {
	   return mono.map(s->s.toUpperCase())  
	              .defaultIfEmpty("default_data")
	              .onErrorReturn("ERROR")
	              .doOnError(err->System.out.println("** err="+err));
	}
	
	public static Mono<MyBag<String>> adjustMonoStringToMyBagV1(Mono<String> mono) {
		   return mono.map(s->new MyBag<String>(s,"String"))  
		              .defaultIfEmpty(new MyBag<String>("default_data","String"))
		              .onErrorReturn(new MyBag<String>("ERROR","String"))
		              .doOnError(err->System.out.println("## err="+err));
		}
	
	public static Mono<MyBag<?>> adjustMonoStringToMyBagV2(Mono<String> mono) {
		     Mono<MyBag<?>> monoMap =  mono.map(s->new MyBag<String>(s,"String"));
		     return monoMap.defaultIfEmpty(new MyBag<Integer>(0,"Integer"))
		              //.onErrorReturn(new MyBag<String>("ERROR","String"));
		    		 .onErrorResume(RuntimeException.class , 
		    				        err -> Mono.just(new MyBag<String>("ERROR:"+err.getMessage(),"String"))
		    				        );
		              //.doOnError(err->System.out.println("## err="+err));
		}


	public static void main(String[] args) {
		Mono<String> mono = buildMonoString();
		Mono<String> mono2=adjustMonoString(mono);
		//Mono<MyBag<String>> mono3=adjustMonoStringToMyBagV1(mono);
		Mono<MyBag<?>> mono3=adjustMonoStringToMyBagV2(mono);
		displayMono(mono);
		displayMono(mono2);
		displayMono(mono3);
	}

}
