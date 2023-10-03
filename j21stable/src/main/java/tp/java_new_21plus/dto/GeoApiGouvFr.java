package tp.java_new_21plus.dto;

import java.util.List;

/*
 exemple
 
 https://geo.api.gouv.fr/communes?codePostal=78000
 renvoyant
 [{"nom":"Versailles","code":"78646","codeDepartement":"78","codeRegion":"11","codesPostaux":["78000"],"population":84808}]
 */
public class GeoApiGouvFr {
	
	public record Commune(String nom ,
			String code ,
			String codeDepartement ,
			String codeRegion ,
			List<String> codesPostaux,
			Integer population) {
	}

}
