package tp.withWebFlux.news.core.reactive.spi;

import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;

public interface ReactiveNewsSaver {
	 public Mono<News> saveNew(News entity);
	 public Mono<News>  updateExisting(News entity);
	 public Mono<News> deleteFromId(String id);
}
