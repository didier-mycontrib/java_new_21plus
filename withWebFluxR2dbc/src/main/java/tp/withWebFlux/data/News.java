package tp.withWebFlux.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class News {
	
	@Id
	private Long id;
	
	private String title;
	
	private String text;
	
	private LocalDate pub_date;
	
	public News(Long id, String title,String text) {
		this(id,title,text,LocalDate.now());
	}
	
	public News(Long id, String title,String text,LocalDate pub_date) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.pub_date = pub_date;
	}
}
