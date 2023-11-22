package tp.withWebFlux.news.core.reactive.api;

import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;


public interface ReactiveNewsLifeCycle {
	
	Mono<News> create(News news);//returning created news with id
	Mono<News> update(News news);//returning updated news
	Mono<News> deleteById(String newsId);//returning deleted news

}
