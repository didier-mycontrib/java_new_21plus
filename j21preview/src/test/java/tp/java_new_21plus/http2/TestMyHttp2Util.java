package tp.java_new_21plus.http2;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tp.java_new_21plus.dto.GeoApiGouvFr;
import tp.java_new_21plus.dto.MoneyExchangeRate;
import tp.java_new_21plus.dto.OpenElevation;
import tp.java_new_21plus.dto.OpenWeatherMap;
import tp.java_new_21plus.dto.Zippopotam;

public class TestMyHttp2Util {
	
	private static Logger logger = LoggerFactory.getLogger(TestMyHttp2Util.class);
	
	private MyHttp2Util myHttp2Util = MyHttp2Util.INSTANCE;
	

	
	@Test
	public void testFetchZippopotam() {
		Zippopotam.Response zippopotamResponse = myHttp2Util.fetch("http://api.zippopotam.us/fr/75001", Zippopotam.Response.class);
		assertNotNull(zippopotamResponse);
		logger.debug("zippopotamResponse="+zippopotamResponse);
	}
	
	@Test
	public void testFetchGeoApiGouvFrCommune() {
		List<GeoApiGouvFr.Commune> communes = myHttp2Util.fetchList("https://geo.api.gouv.fr/communes?codePostal=78000", GeoApiGouvFr.Commune.class);
		assertNotNull(communes);
		logger.debug("communes="+communes);
	}
	
	@Test
	public void testOpenWeatherMap() {
		OpenWeatherMap.Response meteoParis = 
				myHttp2Util.fetch(OpenWeatherMap.buildCurrentWeatherURL("48.856614", "2.3522219"),
						OpenWeatherMap.Response.class);
		assertNotNull(meteoParis);
		logger.debug("meteoParis="+meteoParis);
		logger.debug("temperatureParis="+OpenWeatherMap.kelvinToCelsius(meteoParis.main().temp()));
	}
	
	@Test
	public void testOpenElevation() {
		OpenElevation.Response openElevationResponseParis = 
				myHttp2Util.fetch(OpenElevation.buildOpenElevationURL("48.856614", "2.3522219"),
						OpenElevation.Response.class);
		assertNotNull(openElevationResponseParis);
		logger.debug("openElevationResponseParis="+openElevationResponseParis);
		logger.debug("elevationParis="+openElevationResponseParis.results().get(0).elevation());
	}
	
	@Test
	public void testMoneyExchangeRate() {
		MoneyExchangeRate.Response moneyExchangeRates = 
				myHttp2Util.fetch(MoneyExchangeRate.buildCurrentRatesURL(),
						MoneyExchangeRate.Response.class);
		assertNotNull(moneyExchangeRates);
		logger.debug("moneyExchangeRates="+moneyExchangeRates);
		logger.debug("1 euro="+moneyExchangeRates.rates().get("USD") + "USD");
	}

}
