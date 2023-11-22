package tp.withWebFlux.news.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import tp.withWebFlux.news.persitence.data.NewsMongoDoc;

@Repository
//public interface NewsRepository extends ReactiveCrudRepository<News, String> {
public interface NewsRepository extends ReactiveMongoRepository<NewsMongoDoc, String> {
	public Flux<NewsMongoDoc> findByTitle(String title);
}
