package tp.java_new_21plus.loom.structured_concurrency;

import java.util.Arrays;
import java.util.List;

import tp.java_new_21plus.dto.GeoApiGouvFr;
import tp.java_new_21plus.dto.OpenElevation;
import tp.java_new_21plus.dto.OpenWeatherMap;
import tp.java_new_21plus.dto.Zippopotam;
import tp.java_new_21plus.http2.MyHttp2Util;
import tp.java_new_21plus.loom.structured_concurrency.data.TownWithWeather;

public abstract class TownInfoFetcher {
	
	
	public static List<String> buildZipList() {
		return Arrays.asList("75001","33000","06000","69001","67000");
	}
	
    public TownWithWeather fetchTownNameAndPopulationFromZip(TownWithWeather townWithZip) {
    	TownWithWeather townWithNameAndPopulation = new TownWithWeather(townWithZip);
    	String wsURL="https://geo.api.gouv.fr/communes?codePostal=" + townWithZip.getZip();
    	List<GeoApiGouvFr.Commune> communes = MyHttp2Util.INSTANCE.fetchList(wsURL, GeoApiGouvFr.Commune.class);
    	if(communes!=null && communes.size()>=1) {
    	   townWithNameAndPopulation.setName(communes.get(0).nom());
    	   townWithNameAndPopulation.setPopulation(communes.get(0).population());
    	}
    	return townWithNameAndPopulation;
	}
    
    public TownWithWeather fetchTownLatitudeAndLongitudeFromZip(TownWithWeather townWithZip) {
    	TownWithWeather townWithCoords = new TownWithWeather(townWithZip);
    	String wsURL="http://api.zippopotam.us/fr/" + townWithZip.getZip();
    	Zippopotam.Response zippopotamResponse = MyHttp2Util.INSTANCE.fetch(wsURL, Zippopotam.Response.class);
    	if(zippopotamResponse!=null && zippopotamResponse.places().size()>=1) {
    		townWithCoords.setLatitude(Double.parseDouble( zippopotamResponse.places().get(0).latitude()) );
    		townWithCoords.setLongitude(Double.parseDouble( zippopotamResponse.places().get(0).longitude()) );
    	}
    	return townWithCoords;
	}
    
    public TownWithWeather fetchTownWeatherFromCoords(TownWithWeather townWithCoords) {
    	TownWithWeather townWithWeather = new TownWithWeather(townWithCoords);
    	String wsURL=OpenWeatherMap.buildCurrentWeatherURL(townWithCoords.getLatitude().toString(),
    			                                           townWithCoords.getLongitude().toString());
    	OpenWeatherMap.Response meteo = 
    			MyHttp2Util.INSTANCE.fetch(wsURL,OpenWeatherMap.Response.class);
    	if(meteo!=null && meteo.weather().size()>=1) {
    		townWithWeather.setTemperature(OpenWeatherMap.kelvinToCelsius( meteo.main().temp()) );
    		townWithWeather.setWeatherDescription(meteo.weather().get(0).description());
    	}
    	return townWithWeather;
    }
    
    public TownWithWeather fetchTownElevationFromCoords(TownWithWeather townWithCoords) {
    	TownWithWeather townWithElevation = new TownWithWeather(townWithCoords);
    	String wsURL=OpenElevation.buildOpenElevationURL(townWithCoords.getLatitude().toString(),
    			                                           townWithCoords.getLongitude().toString());
    	//System.out.println("wsURL="+wsURL);
    	OpenElevation.Response openElevationResponse = 
    			MyHttp2Util.INSTANCE.fetch(wsURL,OpenElevation.Response.class);
    	//sometime return null and poor response time
    	if(openElevationResponse!=null && openElevationResponse.results().size()>=1) {
    		townWithElevation.setElevation(openElevationResponse.results().get(0).elevation());
    	}
    	return townWithElevation;
    }
    
    public TownWithWeather fakeFetchTownElevationFromCoords(TownWithWeather townWithCoords) {
    	TownWithWeather townWithElevation = new TownWithWeather(townWithCoords);
    	try {
			Thread.currentThread().sleep(100);//100ms
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	townWithElevation.setElevation(50.123456789);
    	return townWithElevation;
    }
    
    public abstract List<TownWithWeather> fetchTowns();
    

}
