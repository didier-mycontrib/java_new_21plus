package tp.withWebFlux.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.dto.Dto.NewsL0;


public interface NewsServiceWithDto {
	Mono<NewsL0> searchDtoById(String id) ;
	Flux<NewsL0> searchAllDto() ;
	Flux<NewsL0> searchDtoByTitle(String title);
	
	Mono<NewsL0> createWithDto(NewsL0 news);//returning created news with id
	Mono<NewsL0> updateWithDto(NewsL0 news);//returning updated news
	Mono<NewsL0> deleteNewsById(String newsId);//returning deleted news
}
