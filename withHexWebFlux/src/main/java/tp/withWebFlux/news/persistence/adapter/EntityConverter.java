package tp.withWebFlux.news.persistence.adapter;

import java.time.LocalDate;

import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.persitence.data.NewsMongoDoc;
import tp.withWebFlux.news.rest.dto.Dto;

public class EntityConverter {
	
	public static EntityConverter INSTANCE = new EntityConverter();
	
	public News newsMongoDocToNews(NewsMongoDoc newsDoc){
		if(newsDoc==null)return null;
		String sDate = newsDoc.getDate()!=null?newsDoc.getDate().toString():null;
		return new News(newsDoc.getId(), 
				newsDoc.getTitle(),
				newsDoc.getText(),
                sDate
                );
	}
	
	public NewsMongoDoc newsToNewsMongoDoc(News news){
		if(news==null)return null;
		LocalDate localDate  = news.getDate()!=null ? LocalDate.parse(news.getDate()) : null;
		return new NewsMongoDoc(news.getId(), 
				news.getTitle(),
				news.getText(),
				localDate
				);
	}
}
