package tp.withWebFlux.data;

import java.time.LocalDate;

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
	
	private LocalDate date;
	
	public News(String id, String title,String text) {
		this(id,title,text,LocalDate.now());
	}
	
	public News(String id, String title,String text,LocalDate date) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.date = date;
	}
}
