package tp.withWebFlux.news.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;
import tp.withWebFlux.news.core.reactive.impl.MyBasicReactorService;

@SpringBootTest
public class MyBasicReactorServiceTest {
	
	private static Logger logger = LoggerFactory.getLogger(MyBasicReactorServiceTest.class);
	
	@Autowired
	private MyBasicReactorService basicService;
	
	public static void logObjectValue(Object valObj) {
		logger.debug(valObj.toString());
	}
	
	/*
	 https://www.baeldung.com/reactive-streams-step-verifier-test-publisher
	 */
	
	@Test
	public void testWithReactor() {
		logger.debug("basicService="+basicService);
		assertNotNull(basicService);
		List<String> sList = Arrays.asList("John", "Monica", "Mark", "Cloe", "Frank",
		          "Casper", "Olivia", "Emily", "Kate" , "Alex");
		StepVerifier.create(
				    basicService.smallStringInUppercase(sList,4)
				    .doOnNext(s->logObjectValue(s))
				)
		.expectNextCount(5)
		.verifyComplete(); //NB: .verifyComplete() should be call !!!
	
	}

}
