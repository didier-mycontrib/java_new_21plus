package tp.withWebFlux.news.core.reactive.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.core.exception.NotFoundException;


public interface ReactiveNewsQuering {
	Mono<News> getById(String id) throws NotFoundException;
	Flux<News> queryAll() ;
	Flux<News> queryByTitle(String title);
}
