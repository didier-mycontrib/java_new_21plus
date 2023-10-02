package tp.java_new_21plus.record_pattern;

public class MyRecords {
	public record Point(int x,int y) {};
	public record GPS_Point(double latitude,double longitude) {};
	public record Location(String name,GPS_Point position) {};
}
