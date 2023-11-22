package tp.withWebFlux.news.core.domain.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class News {
	
	private String id;
	
	private String title;
	
	private String text;
	
	private String date;
	
	public News(String id, String title,String text) {
		this(id,title,text,LocalDate.now().toString());
	}
	
	public News(String id, String title,String text,String date) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.date = date;
	}
}
