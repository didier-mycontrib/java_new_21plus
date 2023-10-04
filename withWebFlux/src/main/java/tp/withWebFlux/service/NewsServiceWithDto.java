package tp.withWebFlux.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.dto.Dto.NewsL0;
import tp.withWebFlux.exception.NotFoundException;

public interface NewsServiceWithDto {
	Mono<Dto.NewsL0> searchDtoById(String id) ;
	Flux<Dto.NewsL0> searchAllDto() ;
	Flux<NewsL0> searchDtoByTitle(String title);
	
}
