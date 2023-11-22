package tp.withWebFlux.news.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;
import tp.withWebFlux.news.persistence.repository.NewsRepository;
import tp.withWebFlux.news.persitence.data.NewsMongoDoc;
@SpringBootTest(
	properties = {
		"de.flapdoodle.mongodb.embedded.databaseDir=./my_mongo_tmp_dir"
	}
)

//@DataMongoTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes={WithWebFluxApplication.class}) //java config
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
		
		NewsMongoDoc newsA = new NewsMongoDoc(null,"newsAa" , "text of newsAa");
		StepVerifier.create(
		     newsRepository.save(newsA)
		     .doOnNext(n->logObjectValue(n))
		)
		.expectNextCount(1)
		.verifyComplete();
	}

}
