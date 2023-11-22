package tp.withWebFlux.news.core.reactive.spi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;

public interface ReactiveNewsLoader {
	public Mono<News> loadById(String id);
	 public Flux<News> loadAll();
	 public Flux<News> loadByTitle(String title);
}
