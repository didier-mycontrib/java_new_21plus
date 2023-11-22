package tp.withWebFlux.news.persistence.adapter;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.core.reactive.spi.ReactiveNewsSaver;
import tp.withWebFlux.news.persistence.repository.NewsRepository;
import tp.withWebFlux.news.persitence.data.NewsMongoDoc;

@Component
@Transactional
public class NewsReactiveSaverAdapter implements ReactiveNewsSaver {

	@Autowired
	private NewsRepository newsRepository;

	@Override
	public Mono<News> saveNew(News entity) {
		NewsMongoDoc news  = EntityConverter.INSTANCE.newsToNewsMongoDoc(entity);
		if(news.getDate()==null)
			news.setDate(LocalDate.now());
		return newsRepository.save(news)
			  .map(EntityConverter.INSTANCE::newsMongoDocToNews);
	}

	@Override
	public Mono<News> updateExisting(News entity) {
		return newsRepository.findById(entity.getId())
                .flatMap(persistentNews -> {
                	persistentNews.setTitle(entity.getTitle());
                	persistentNews.setText(entity.getText());
                	LocalDate localDate  = entity.getDate()!=null ? LocalDate.parse(entity.getDate()) : null;
                	persistentNews.setDate(localDate);
                    return newsRepository.save(persistentNews)
                    		.map((NewsMongoDoc updatedNews)->EntityConverter.INSTANCE.newsMongoDocToNews(updatedNews));
                });
                
	}

	@Override
	public Mono<News> deleteFromId(String id) {
		return newsRepository.findById(id)
		.flatMap(existingNews -> 
        newsRepository.delete(existingNews)
       .then(
       		Mono.just(existingNews)
       		     .map(EntityConverter.INSTANCE::newsMongoDocToNews)
       		)
       );
	}
	
}
