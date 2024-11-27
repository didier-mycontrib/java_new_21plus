package tp.java_new_22plus;


import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class J22TestApp {
	public static void main(String[] args) throws Throwable {
	    System.out.println("**** J22TestApp ****");
		with_unnamed_variable();
		with_unnamed_pattern();
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

	public static void with_unnamed_variable(){
		List<String> names = Arrays.asList("luc", "jean", "dupond");
		System.out.println(names.toString());

		int total1 = 0;
		for (String n : names)    // n= unused named variable
			total1++;
		System.out.println("total1="+total1);

		int total2 = 0;
		for (String _ : names)    // Unnamed variable
			total2++;
		System.out.println("total2="+total2);
	}

	record Point(double x, double y) {}
	enum Color { RED, GREEN, BLUE }
	record ColoredPoint(Point p, Color c) {}

	static double getDistanceWithUnusedColor(Object obj1, Object obj2) {
		if (obj1 instanceof ColoredPoint(Point p1, Color c1) &&
				obj2 instanceof ColoredPoint(Point p2, Color c2)) {
			return java.lang.Math.sqrt(
					java.lang.Math.pow(p2.x - p1.x, 2) +
							java.lang.Math.pow(p2.y - p1.y, 2));
		} else {
			return -1;
		}
	}

	static double getDistanceWithUnnamedPattern(Object obj1, Object obj2) {
		if (obj1 instanceof ColoredPoint(Point p1, _) &&
				obj2 instanceof ColoredPoint(Point p2, _)) {
			return java.lang.Math.sqrt(
					java.lang.Math.pow(p2.x - p1.x, 2) +
							java.lang.Math.pow(p2.y - p1.y, 2));
		} else {
			return -1;
		}
	}

	public static void with_unnamed_pattern(){
		ColoredPoint cp1 = new ColoredPoint(new Point(10,10),Color.BLUE);
		ColoredPoint cp2 = new ColoredPoint(new Point(20,20),Color.RED);
		System.out.println("cp1="+cp1);System.out.println("cp2="+cp2);
		double d1 = getDistanceWithUnusedColor(cp1,cp2);
		System.out.println("d1="+d1);
		double d2 = getDistanceWithUnnamedPattern(cp1,cp2);
		System.out.println("d2="+d2);
	}

}
