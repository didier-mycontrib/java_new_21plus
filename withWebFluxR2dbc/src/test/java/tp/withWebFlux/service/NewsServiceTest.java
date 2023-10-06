package tp.withWebFlux.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;
import tp.withWebFlux.dto.Dto.NewsL0;

@SpringBootTest
public class NewsServiceTest {
	
	private static Logger logger = LoggerFactory.getLogger(NewsServiceTest.class);
	
	@Autowired
	private NewsServiceWithDto newsService;
	
	public static void logObjectValue(Object valObj) {
		logger.debug(valObj.toString());
	}
	
	@Test
	public void testNewsCrud() {
		logger.debug("newsService="+newsService);
		assertNotNull(newsService);
		
		NewsL0 newsA = new NewsL0(null,"newsA" , "text of newsA" , null);
		
		StepVerifier.create(
				newsService.createWithDto(newsA)
				.doOnNext(n->logObjectValue(n))
			)
			.expectNextCount(1)
			.verifyComplete();
		
	}

}
