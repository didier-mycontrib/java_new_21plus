package tp.withWebFlux.converter;

import tp.withWebFlux.data.News;
import tp.withWebFlux.dto.Dto;

public class DtoConverter {
	
	public static DtoConverter INSTANCE = new DtoConverter();
	
	public Dto.NewsL0 newsToNewsL0(News news){
		if(news==null)return null;
		return new Dto.NewsL0(news.getId(), 
                news.getTitle(),
                news.getText());
	}
	
	public News newsL0ToNews(Dto.NewsL0 newsDto){
		if(newsDto==null)return null;
		return new News(newsDto.id(), 
				newsDto.title(),
				newsDto.text());
	}
}
