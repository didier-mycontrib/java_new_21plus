package tp.withWebFlux.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tp.withWebFlux.data.News;

@SpringBootTest
public class NewsRepositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(NewsRepositoryTest.class);
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Test
	public void testNewsCrud() {
		logger.debug("newsRepository="+newsRepository);
		assertNotNull(newsRepository);
		
		News newsA = new News(null,"newsA" , "text of newsA");
		newsRepository.save(newsA)
		              .subscribe((n)->logger.debug("n="+n));
		
	}

}
