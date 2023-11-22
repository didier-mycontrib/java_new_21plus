package tp.java_new_21plus.record_pattern;

import tp.java_new_21plus.record_pattern.MyRecords.*;

public class RecordDeconstructApp {
	
	public static void main(String[] args) {
		Double d1=23.5;
		print(d1);
		
		Point pt1 = new Point(20,30);
		print(pt1);
		print_destructuring(pt1);
		switch_destructuring(pt1);
		
		Location loc1 = new Location("Paris" , new GPS_Point(48.866667, 2.333333));
		print(loc1);
		print_destructuring(loc1);
		switch_destructuring(loc1);
	}
	
	public static void print(Object o) {
		//Pattern matching for instance of (since java 16/17):
		if(o instanceof Double d) {
			System.out.println("double d="+d);
		}
		if(o instanceof Point pt) {
			System.out.println("Point pt="+pt);
		}
		if(o instanceof Location loc) {
			System.out.println("Location loc="+loc);
		}
	}
	
	public static void print_destructuring(Object o) {
		//record pattern = destructuration (like javascript) in Pattern matching for instance of
		if(o instanceof Point (int a,int b)) {
			System.out.println("Destructuring Point a="+a + " b="+b);
		}
		if(o instanceof Location (String name,GPS_Point(double latitude,double longitude))) {
			System.out.println("Destructuring Location name="+name + " latitude="+latitude + " longitude="+longitude );
		}
	}
	
	public static void switch_destructuring(Object o) {
		//record pattern = destructuration (like javascript) in switch expression
		double  maxi = switch(o) {
			case Point (int x,int y) -> (double) Math.max(x, y);
			case Location (String name,GPS_Point(double latitude,double longitude)) -> Math.max(latitude, longitude);
			default -> 0;
		};
		System.out.println("switch_destructuring, maxi="+maxi);
	}

}
