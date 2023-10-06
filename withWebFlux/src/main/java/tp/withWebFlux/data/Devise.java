package tp.withWebFlux.data;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Devise {
	
	@Id
	private String code;
	
	private String nom;
	
	private Double dChange;

	public Devise(String code, String nom, Double dChange) {
		super();
		this.code = code;
		this.nom = nom;
		this.dChange = dChange;
	}
	

}
