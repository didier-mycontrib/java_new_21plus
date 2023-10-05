package tp.withWebFlux.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.test.StepVerifier;
import tp.withWebFlux.WithWebFluxApplication;
import tp.withWebFlux.data.News;

@DataMongoTest
//@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={WithWebFluxApplication.class}) //java config
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
		
		News newsA = new News(null,"newsA" , "text of newsA");
		StepVerifier.create(
		     newsRepository.save(newsA)
		     .doOnNext(n->logObjectValue(n))
		)
		.expectNextCount(1)
		.verifyComplete();
	}

}
