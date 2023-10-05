package tp.withWebFlux.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.converter.DtoConverter;
import tp.withWebFlux.data.News;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.dto.Dto.NewsL0;
import tp.withWebFlux.exception.NotFoundException;
import tp.withWebFlux.repository.NewsRepository;

@Service
@Transactional
public class NewsServiceImpl implements  NewsServiceWithDto {
	
	@Autowired
	private NewsRepository newsRepository;

	
	@Override
	public Mono<NewsL0> searchDtoById(String id) {
		return newsRepository.findById(id)
				 .map(DtoConverter.INSTANCE::newsToNewsL0)
				 .switchIfEmpty(Mono.error(new NotFoundException("no entity found with id="+id)));
		        // .switchIfEmpty(Mono.error(new RuntimeException("no entity found with id="+id)));
	}
	
	@Override
	public Flux<Dto.NewsL0> searchAllDto() {
		return newsRepository.findAll()
			     .map(DtoConverter.INSTANCE::newsToNewsL0);
	}



	@Override
	public Flux<NewsL0> searchDtoByTitle(String title) {
		return newsRepository.findByTitle(title)
		  .map(DtoConverter.INSTANCE::newsToNewsL0);
	}
	
		
	@Override
	public Mono<NewsL0> createWithDto(NewsL0 newsDto) {
		News news  = DtoConverter.INSTANCE.newsL0ToNews(newsDto);
		if(news.getDate()==null)
			news.setDate(LocalDate.now());
		return newsRepository.save(news)
			  .map(DtoConverter.INSTANCE::newsToNewsL0);
	}
	
		
	@Override
	public Mono<NewsL0> updateWithDto(NewsL0 newsDto) {
		return newsRepository.findById(newsDto.id())
                .flatMap(persistentNews -> {
                	persistentNews.setTitle(newsDto.title());
                	persistentNews.setText(newsDto.text());
                	LocalDate localDate  = newsDto.date()!=null ? LocalDate.parse(newsDto.date()) : null;
                	persistentNews.setDate(localDate);
                    return newsRepository.save(persistentNews)
                    		.map((News updatedNews)->DtoConverter.INSTANCE.newsToNewsL0(updatedNews));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("can not update , no entity found with id="+newsDto.id()))); 
	}
	
	
	
	@Override
	public Mono<Dto.NewsL0> deleteNewsById(String newsId) {
		return newsRepository.findById(newsId)
                .flatMap(existingNews -> 
                         newsRepository.delete(existingNews)
                        .then(
                        		Mono.just(existingNews)
                        		     .map(DtoConverter.INSTANCE::newsToNewsL0)
                        		)
                        )
                .switchIfEmpty(Mono.error(new NotFoundException("can not delete , no entity found with id="+newsId))); 
	}
	
	
	

	
	
}
