package tp.java_new_25plus;


import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/*
Les Stream Gatherers (pour personnaliser des opérations intermédiaires pointues
au sein des collection/stream() )
 */

public class StreamGatherersTests {
	public static void essai() throws Throwable {
	    System.out.println("**** StreamGatherersTests ****");
		with_streamGatherers();
	}

	private static void with_streamGatherers() {

		//built-in gatherers:

		var windowFixed2Serie =
		Stream.iterate(0, i -> i + 1)
				.gather(Gatherers.windowFixed(2))
				.limit(5)
				.collect(Collectors.toList());
		System.out.println("windowFixed2Serie="+windowFixed2Serie); //par sous series de taille 2
		if(windowFixed2Serie.get(0) instanceof List) System.out.println("subSeries as sub List");

		var windowSliding2Serie =
				Stream.iterate(0, i -> i + 1)
						.gather(Gatherers.windowSliding(2))
						.limit(5)
						.collect(Collectors.toList());
		System.out.println("windowSliding2Serie="+windowSliding2Serie); //fenetre mobile/glissante de taille 2

		var liste3 = Stream.of(1,2,3,4,5,6,7,8,9)
				.gather(
						Gatherers.scan(() -> "", (string, number) -> string + number)
				)
				.toList();

		System.out.println("liste3="+liste3);//Gatherers.scan (special accumulator)

        //custom Gatherer:
		Gatherer<String, Set<Integer>, String> distinctByLength =
				Gatherer.ofSequential(
						// INTIIALIZER
						HashSet::new,

						// INTEGRATOR
						(state, element, downstream) -> {
							var added = state.add(element.length());
							return added ? downstream.push(element)
									: !downstream.isRejecting(); //on retransmet l'elt que si sa taille est différente de celle de ceux des éléments précédents
						},

						// FINISHER
						Gatherer.defaultFinisher()
				);

		var liste4= Stream.of("apple", "orange", "banana", "melon", "pineapple")
				.gather(distinctByLength)
				.toList();
		System.out.println("liste4="+liste4);
	}





}
