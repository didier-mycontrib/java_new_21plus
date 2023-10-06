NB: les tests en mode reactor
doivent être écrits en s'appuyant sur StepVerifier de reactor-test.

dépendance maven:

         <dependency>
   			 <groupId>io.projectreactor</groupId>
   			 <artifactId>reactor-test</artifactId>
    		 <scope>test</scope>
		</dependency>
	
-------------------
NB: l'appel à  .verifyComplete() pour le déclenchement !!!
-------------------	
		
Exemple:


import reactor.test.StepVerifier;

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
       

