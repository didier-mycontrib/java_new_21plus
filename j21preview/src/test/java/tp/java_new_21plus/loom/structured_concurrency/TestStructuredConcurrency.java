package tp.java_new_21plus.loom.structured_concurrency;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tp.java_new_21plus.loom.structured_concurrency.StructuredConcurrencyApp;

public class TestStructuredConcurrency {
private static Logger logger = LoggerFactory.getLogger(TestStructuredConcurrency.class);
	
	@Test
	public void testMain() {
		StructuredConcurrencyApp.main(null);
	}

}
