package tp.withWebFlux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.converter.DtoConverter;
import tp.withWebFlux.data.News;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.dto.Dto.NewsL0;
import tp.withWebFlux.exception.NotFoundException;
import tp.withWebFlux.repository.NewsRepository;

@Service
@Transactional
public class NewsServiceImpl implements NewsServiceInternal, NewsServiceWithDto {
	
	@Autowired
	private NewsRepository newsRepository;

	@Override
	public Mono<News> findById(String id) {
		return newsRepository.findById(id);
	}

	@Override
	public Mono<NewsL0> searchDtoById(String id) {
		return newsRepository.findById(id)
				 .map(DtoConverter.INSTANCE::newsToNewsL0)
				 .switchIfEmpty(Mono.error(new NotFoundException("no entity found with id="+id)));
		        // .switchIfEmpty(Mono.error(new RuntimeException("no entity found with id="+id)));
	}

	
	
	@Override
	public Mono<News> createNews(News news){
        return newsRepository.save(news);
    }
	
	@Override
	public Mono<News> updateNews(News news){
		return newsRepository.findById(news.getId())
                .flatMap(persistentNews -> {
                	persistentNews.setTitle(news.getTitle());
                	persistentNews.setText(news.getText());
                    return newsRepository.save(persistentNews);
                });
    }
	
	@Override
	public Mono<News> deleteNews(String newsId){
        return newsRepository.findById(newsId)
                .flatMap(existingNews -> newsRepository.delete(existingNews)
                        .then(Mono.just(existingNews)));
    }
	
	@Override
	public Flux<News> findAll() {
		return newsRepository.findAll();
	}

	@Override
	public Flux<Dto.NewsL0> searchAllDto() {
		return newsRepository.findAll()
			     .map(DtoConverter.INSTANCE::newsToNewsL0);
	}



	

	@Override
	public Flux<News> findByTitle(String title) {
		return newsRepository.findByTitle(title);
	}


	@Override
	public Flux<NewsL0> searchDtoByTitle(String title) {
		return newsRepository.findByTitle(title)
		  .map(DtoConverter.INSTANCE::newsToNewsL0);
	}
	
	
}
