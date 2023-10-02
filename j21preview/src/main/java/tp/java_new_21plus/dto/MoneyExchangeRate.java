package tp.java_new_21plus.dto;

import java.util.HashMap;

public class MoneyExchangeRate {
	
	public record Response(Boolean success, Long timestamp ,  String base , String date,
			HashMap<String,Double> rates) {
	}
	
	public static String  buildCurrentRatesURL(String apiKey) {
		return "http://data.fixer.io/api/latest"
				+ "?access_key="+apiKey;
	}
	
	public static String  buildCurrentRatesURL() {
		String defaultApiKey = "26ca93ee7fc19cbe0a423aaa27cab235";//didierDefrance
		return buildCurrentRatesURL(defaultApiKey);
	}

}
