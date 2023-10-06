package tp.withWebFlux.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;
import tp.withWebFlux.data.News;
@SpringBootTest(
)
public class NewsRepositoryTest {
	
	private static Logger logger = LoggerFactory.getLogger(NewsRepositoryTest.class);
	
	@Autowired
	private NewsRepository newsRepository;
	
	public static void logObjectValue(Object valObj) {
		logger.debug(valObj.toString());
	}
	
	@Test
	public void testNewsCrud() {
		logger.debug("newsRepository="+newsRepository);
		assertNotNull(newsRepository);
		/*
		News newsA = new News("na","newsAa" , "text of newsAa");
		StepVerifier.create(
		     newsRepository.save(newsA)
		     .doOnNext(n->logObjectValue(n))
		)
		.expectNextCount(1)
		.verifyComplete();
		
		*/
		
		
		StepVerifier.create(
			     newsRepository.findAll()
			)
		.recordWith(ArrayList::new)
		.thenConsumeWhile(news -> true)
		.consumeRecordedWith((newsList)->{ logObjectValue(newsList) ; })
		.verifyComplete();
	
	}

}
