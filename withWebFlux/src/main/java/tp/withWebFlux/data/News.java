package tp.withWebFlux.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Document("news")//mongodb Document with collection name
public class News {
	
	@Id
	private String id;
	
	private String title;
	
	private String text;
	
	public News(String id, String title,String text) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
	}
}
