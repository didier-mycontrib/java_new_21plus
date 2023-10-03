package tp.java_new_21plus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
/* exemple  http://api.zippopotam.us/fr/75001
 renvoyant
 {"post code": "75001", "country": "France", 
"country abbreviation": "FR", 
"places": [{"place name": "Paris 01 Louvre", "longitude": "2.3417",
 "state": "Ile-de-France", "state abbreviation": "A8", 
 "latitude": "48.8592"}]}
 */
public class Zippopotam{
	
	public record Place(@JsonAlias({"place_name" , "place name"})String placeName,
			 String longitude ,
			 String state ,
			 @JsonAlias({"state_abbreviation" , "state abbreviation"})String stateAbbreviation ,  
			 String latitude) {
	}
	
	public record Response(@JsonAlias({"post_code" , "post code"})String postCode ,
			String country ,
			@JsonAlias({"country_abbreviation" , "country abbreviation"}) String countryAbbreviation , 
			List<Place> places) {
	}
}


