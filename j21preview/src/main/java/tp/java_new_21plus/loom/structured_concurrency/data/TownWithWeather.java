package tp.java_new_21plus.loom.structured_concurrency.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TownWithWeather {
	private String zip;
	private String name;
	private Double latitude;
	private Double longitude;
	private String weatherDescription;
	private Double temperature;
	private Integer population;
	private Double elevation;
	
	public TownWithWeather(String zip ,String name, Double latitude, 
			Double longitude, String weatherDescription,
			Double temperature, Integer population,Double elevation) {
		super();
		this.zip = zip;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.weatherDescription = weatherDescription;
		this.temperature = temperature;
		this.population = population;
	}
	
	public TownWithWeather(String zip,String name ) {
		this(zip,name,null,null,null,null,null,null);
	}
	
	public TownWithWeather(String zip) {
		this(zip,null,null,null,null,null,null,null);
	}
	
	public TownWithWeather(TownWithWeather t) {
		this(t.getZip(),t.name,t.getLatitude(),t.getLongitude(),
			 t.getWeatherDescription(),t.getTemperature(),t.getPopulation(),t.getElevation());
	}
	
	
}
