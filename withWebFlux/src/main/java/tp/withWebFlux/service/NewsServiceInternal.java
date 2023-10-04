package tp.withWebFlux.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.data.News;
import tp.withWebFlux.dto.Dto;

public interface NewsServiceInternal {	
	Mono<News> findById(String id) ;
	Flux<News> findAll() ;
	Flux<News> findByTitle(String title) ;
	Mono<News> createNews(News news);
	Mono<News> updateNews(News news);
	Mono<News> deleteNews(String newsId);
}
