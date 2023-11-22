package tp.withWebFlux.news.core.reactive.impl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.core.exception.NotFoundException;
import tp.withWebFlux.news.core.reactive.api.ReactiveNewsService;
import tp.withWebFlux.news.core.reactive.spi.ReactiveNewsLoader;
import tp.withWebFlux.news.core.reactive.spi.ReactiveNewsSaver;

public class ReactiveNewsServiceImpl implements ReactiveNewsService {
	
	
	private ReactiveNewsLoader newsLoader;
	private ReactiveNewsSaver newsSaver;
	
	
	public ReactiveNewsServiceImpl(ReactiveNewsLoader newsLoader, ReactiveNewsSaver newsSaver) {
		super();
		this.newsLoader = newsLoader;
		this.newsSaver = newsSaver;
	}


	@Override
	public Mono<News> create(News news) {
		return newsSaver.saveNew(news);
	}


	@Override
	public Mono<News> update(News news) {
		return newsSaver.updateExisting(news)
				.switchIfEmpty(Mono.error(new NotFoundException("can not update , no entity found with id="+news.getId()))); 
	}


	@Override
	public Mono<News> deleteById(String newsId) {
		return newsSaver.deleteFromId(newsId)
		      .switchIfEmpty(Mono.error(new NotFoundException("can not update , no entity found with id="+newsId))); 
	}


	@Override
	public Mono<News> getById(String id) throws NotFoundException {
		return newsLoader.loadById(id)
				 .switchIfEmpty(Mono.error(new NotFoundException("no entity found with id="+id)));
	}


	@Override
	public Flux<News> queryAll() {
		return newsLoader.loadAll();
	}


	@Override
	public Flux<News> queryByTitle(String title) {
		return newsLoader.loadByTitle(title);
	}

	
}
