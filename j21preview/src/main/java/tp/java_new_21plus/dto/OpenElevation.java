package tp.java_new_21plus.dto;

import java.util.List;
/*exemple
 https://api.open-elevation.com/api/v1/lookup?locations=10,10|20,20|41.161758,-8.583933
 renvoyant
 {"results":[{"latitude":10.0,"longitude":10.0,"elevation":515.0},{"elevation":545.0,"longitude":20.0,"latitude":20.0},{"latitude":41.161758,"longitude":-8.583933,"elevation":117.0}]}
 */
public class OpenElevation {
	
	public record Coords(Double longitude,Double latitude, Double elevation ) {
				
	}
	
	public record Response(List<Coords> results ) {
	}
	
	public static String  buildOpenElevationURL(String latitude, String longitude) {
		return "https://api.open-elevation.com/api/v1/lookup"
				+ "?locations="+latitude+ ","+longitude;
	}

}
