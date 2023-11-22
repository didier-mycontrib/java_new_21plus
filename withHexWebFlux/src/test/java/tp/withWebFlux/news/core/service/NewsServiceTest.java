package tp.withWebFlux.news.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;
import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.core.reactive.api.ReactiveNewsService;

@SpringBootTest
public class NewsServiceTest {
	
	private static Logger logger = LoggerFactory.getLogger(NewsServiceTest.class);
	
	@Autowired
	private ReactiveNewsService newsService;
	
	public static void logObjectValue(Object valObj) {
		logger.debug(valObj.toString());
	}
	
	@Test
	public void testNewsCrud() {
		logger.debug("newsService="+newsService);
		assertNotNull(newsService);
		
		News newsA = new News(null,"newsA" , "text of newsA" , null);
		
		StepVerifier.create(
				newsService.create(newsA)
				.doOnNext(n->logObjectValue(n))
			)
			.expectNextCount(1)
			.verifyComplete();
		
	}

}
