package tp.java_new_21plus.dto;

import java.util.List;

public class OpenWeatherMap {
	
	//partial
	public record Weather(String description ) {
	}
	
	public record Wind(Double speed, Integer deg ) {
	}
	
	public record Main(Double temp ,
			           Double feels_like , 
			           Double temp_min , 
			           Double temp_max,
			           Double pressure,
			           Double humidity) {
	}
	
	//partial
	public record Response(List<Weather> weather,Main main, Wind wind ) {
		
	}
	
	public static String  buildCurrentWeatherURL(String latitude, String longitude,String apiKey) {
		return "https://api.openweathermap.org/data/2.5/weather"
				+ "?lat="+latitude
				+ "&lon="+longitude
				+ "&appid="+apiKey;
	}
	
	public static String  buildCurrentWeatherURL(String latitude, String longitude) {
		String defaultApiKey = "27a68278aebee75af9d4fc23d7a68f75";//didierDefrance
		return buildCurrentWeatherURL(latitude,longitude,defaultApiKey);
	}
	
	public static double kelvinToCelsius(double kelvinDeg) {
		return kelvinDeg - 273.15;
	}

}
