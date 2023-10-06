package tp.withWebFlux.converter;

import java.time.LocalDate;

import tp.withWebFlux.data.News;
import tp.withWebFlux.dto.Dto;

public class DtoConverter {
	
	public static DtoConverter INSTANCE = new DtoConverter();
	
	public Dto.NewsL0 newsToNewsL0(News news){
		if(news==null)return null;
		String sDate = news.getPub_date()!=null?news.getPub_date().toString():null;
		return new Dto.NewsL0(String.valueOf(news.getId()), 
                news.getTitle(),
                news.getText(),
                sDate
                );
	}
	
	public News newsL0ToNews(Dto.NewsL0 newsDto){
		if(newsDto==null)return null;
		LocalDate localDate  = newsDto.date()!=null ? LocalDate.parse(newsDto.date()) : null;
		Long newsId = null;
		if(newsDto.id()!=null) {
			newsId=Long.parseLong(newsDto.id());
		}
		return new News(newsId, 
				newsDto.title(),
				newsDto.text(),
				localDate
				);
	}
}
