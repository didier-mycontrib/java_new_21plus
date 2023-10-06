package tp.withWebFlux.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import reactor.test.StepVerifier;
import tp.withWebFlux.data.Devise;

@SpringBootTest()
@ActiveProfiles({"r2dbc"})
public class DeviseRepositoryTest {
	
	private static Logger logger = LoggerFactory.getLogger(DeviseRepositoryTest.class);
	
	@Autowired
	private DeviseRepository deviseRepository;
	
	public static void logObjectValue(Object valObj) {
		logger.debug(valObj.toString());
	}
	
	@Test
	public void testDeviseCrud() {
		logger.debug("deviseRepository="+deviseRepository);
		assertNotNull(deviseRepository);
		/*
		Devise deviseA = new Devise("DDK2","couronne danemark" , 7.7);
		StepVerifier.create(
				deviseRepository.save(deviseA)
		     .doOnNext(n->logObjectValue(n))
		)
		.expectNextCount(1)
		.verifyComplete();
		*/
		
		StepVerifier.create(
			      deviseRepository.findAll()
			)
		.recordWith(ArrayList::new)
		.thenConsumeWhile(d -> true)
		.consumeRecordedWith((deviseList)->{ logObjectValue(deviseList) ; })
		.verifyComplete();
	}

}
