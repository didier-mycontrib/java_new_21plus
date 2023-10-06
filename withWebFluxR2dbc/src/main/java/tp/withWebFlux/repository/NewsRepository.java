package tp.withWebFlux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import tp.withWebFlux.data.News;

@Repository
public interface NewsRepository extends ReactiveCrudRepository<News, String> {
	public Flux<News> findByTitle(String title);
}
