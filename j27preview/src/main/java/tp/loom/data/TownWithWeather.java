package tp.loom.data;
/*
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor //lombok pas encore pret pour java 27
*/
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

	@Override
	public String toString() {
		return "TownWithWeather{" +
				"zip='" + zip + '\'' +
				", name='" + name + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", weatherDescription='" + weatherDescription + '\'' +
				", temperature=" + temperature +
				", population=" + population +
				", elevation=" + elevation +
				'}';
	}

	public TownWithWeather() {
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
}
