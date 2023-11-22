package tp.withWebFlux.news.persistence.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.core.reactive.spi.ReactiveNewsLoader;
import tp.withWebFlux.news.persistence.repository.NewsRepository;
@Component
@Transactional
public class NewsReactiveLoaderAdapter implements ReactiveNewsLoader {
	
	@Autowired
	private NewsRepository newsRepository;

	@Override
	public Mono<News> loadById(String id) {
		return newsRepository.findById(id)
		       .map(EntityConverter.INSTANCE::newsMongoDocToNews);
	}

	@Override
	public Flux<News> loadAll() {
		return newsRepository.findAll()
			  .map(EntityConverter.INSTANCE::newsMongoDocToNews);
	}

	@Override
	public Flux<News> loadByTitle(String title) {
		return newsRepository.findByTitle(title)
			   .map(EntityConverter.INSTANCE::newsMongoDocToNews);
	}


	

}
