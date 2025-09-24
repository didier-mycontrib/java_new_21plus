package tp.java_new_25plus;


import tp.java_new_25plus.jep447.PositiveBigInteger;
import tp.java_new_25plus.jep447.SubClass;

import java.util.Arrays;
import java.util.List;

public class J25TestApp {
	public static void main(String[] args) throws Throwable {
		System.out.println("**** J25TestApp ****");

        with_unnamed_variable();
        with_unnamed_pattern();

        jep447_flexible_constructors_with_super();

        StreamGatherersTests.essai();
        //générer dynamiquement le byte-cote de la classe HelloWorld:
        BasicClassApiExample.generateHelloWorldClass();

        //lancer l'exécution de la classe HelloWorld générée dynamiquement:
        BasicClassApiExample.lancerProcessHelloWorldClass();
	}

    public static  void jep447_flexible_constructors_with_super(){
        System.out.println(">>>> jep447_flexible_constructors_with_super");
        PositiveBigInteger.essai();
        SubClass.essai();
    }

    public static void with_unnamed_variable(){
        System.out.println(">>>> with_unnamed_local_variable _");
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
        System.out.println(">>>> with_unnamed_local_variable _");
        ColoredPoint cp1 = new ColoredPoint(new Point(10,10),Color.BLUE);
        ColoredPoint cp2 = new ColoredPoint(new Point(20,20),Color.RED);
        System.out.println("cp1="+cp1);System.out.println("cp2="+cp2);
        double d1 = getDistanceWithUnusedColor(cp1,cp2);
        System.out.println("d1="+d1);
        double d2 = getDistanceWithUnnamedPattern(cp1,cp2);
        System.out.println("d2="+d2);
    }
}