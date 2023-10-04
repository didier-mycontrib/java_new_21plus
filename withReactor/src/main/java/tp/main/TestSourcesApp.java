package tp.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestSourcesApp {
	
	public static List<Mono<Object>> buildMonoList(){
		List<Mono<Object>> monoList = new ArrayList<>(); 
		monoList.add(Mono.just("data1"));
		monoList.add(Mono.empty());
		monoList.add(Mono.justOrEmpty("data2"));
		monoList.add(Mono.justOrEmpty(Optional.ofNullable("data3")));
		monoList.add(Mono.error(new RuntimeException("myException in mono")));
		return monoList;
	}
	
	public static List<Flux<Object>> buildFluxList(){
		List<Flux<Object>> fluxList = new ArrayList<>(); 
		fluxList.add(Flux.just(1, 2, 3));
		fluxList.add(Flux.empty());
		fluxList.add(Flux.fromArray(new String[]{"a", "b", "c"}));
		fluxList.add(Flux.fromIterable(Arrays.asList("A", "B", "C")));
		fluxList.add(Flux.error(new RuntimeException("myException in flux")));
		return fluxList;
	}

	public static void main(String[] args) {
		List<Mono<Object>> monoList = buildMonoList();
		for(Mono<Object> mono : monoList) {
			//NB: onNext callback not called if mono is empty , directly onComplete
			mono.subscribe((data)->System.out.println("mono data="+data), 
					       (err)->System.out.println("mono err="+err));
		}
		
		List<Flux<Object>> fluxList = buildFluxList();
		for(Flux<Object> flux : fluxList) {
			flux.subscribe((data)->System.out.println("flux data="+data), 
					       (err)->System.out.println("flux err"+err));
		}
	}

}
