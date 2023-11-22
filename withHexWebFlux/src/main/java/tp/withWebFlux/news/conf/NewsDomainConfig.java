package tp.withWebFlux.news.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.withWebFlux.news.core.reactive.api.ReactiveNewsService;
import tp.withWebFlux.news.core.reactive.impl.ReactiveNewsServiceImpl;
import tp.withWebFlux.news.core.reactive.spi.ReactiveNewsLoader;
import tp.withWebFlux.news.core.reactive.spi.ReactiveNewsSaver;

@Configuration
public class NewsDomainConfig {
	
	@Bean
	public ReactiveNewsService newsService(ReactiveNewsLoader newsLoader , ReactiveNewsSaver newsSaver) {
		return new ReactiveNewsServiceImpl(newsLoader,newsSaver);
	}
	
	
}
