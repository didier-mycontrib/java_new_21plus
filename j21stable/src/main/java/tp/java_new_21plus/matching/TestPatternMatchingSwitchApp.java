package tp.java_new_21plus.matching;

import tp.java_new_21plus.data.AnimalDomestique;
import tp.java_new_21plus.data.Chat;
import tp.java_new_21plus.data.Chien;

public class TestPatternMatchingSwitchApp {
	

	public static void main(String[] args) {
		System.out.println(getDoubleUsingSwitch("12.5"));
		System.out.println(getDoubleUsingSwitch(12.6));
		System.out.println(getDoubleUsingSwitch(12.7f));
		System.out.println(getDoubleUsingSwitch(12));
		System.out.println(getDoubleUsingSwitch(null));
		
		System.out.println(getTypeAnimalDomesticAsString(new Chat()));
		System.out.println(getTypeAnimalDomesticAsString(new Chien()));
		//System.out.println(getTypeAnimalDomesticAsString(new ChienFou()));
		
		
	}
	
	
	//NB: still in "preview mode"  in java 17-LTS !!!
	//java --enable-preview --source 17 ....
	static double getDoubleUsingSwitch(Object o) {
	    return switch (o) {
	        case Integer i -> i.doubleValue();
	        case Float f -> f.doubleValue();
	        case Double d -> d.doubleValue();
	        case String s -> Double.parseDouble(s);
	        case null -> 0d;
	        default -> 0d;
	    };
	}
	
	//NB: still in "preview mode"  in java 17-LTS !!!
	//java --enable-preview --source 17 ....
	static String getTypeAnimalDomesticAsString(AnimalDomestique a) {
		    return switch (a) {
		        case Chat chat -> "chat";
		        case Chien chien -> "chat";
		        //default -> "ni chat , ni chien";
		    };
		    
		  
		     //Remarque importante :
		     //Ce switch/case avec pattern-matching de type 
		     //nécessite absolument une partie "default" si
		     //AnimalDomestique n'est pas scellé (sans le mot clef sealed et ...)
		     //et ne nécessite pas de partie "default" si AnimalDomestique n'est pas scellé
		   
		     //Autrement dit l'intéret principal des classes scéllées tient dans la possibilité
		     //d'utilisiser directement les différents types de classes concrètes 
		     //(dérivant d'un même type abstrait scellé) au sein d'un switch/case sans default
		     //sans avoir besoin de gérer en parallèle une énumération à valeurs possibles fixes/finies.
		    
	}
	


}
