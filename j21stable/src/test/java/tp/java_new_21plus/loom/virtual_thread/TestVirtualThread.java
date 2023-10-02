package tp.java_new_21plus.loom.virtual_thread;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tp.java_new_21plus.loom.virtual_thread.TestVirtualThreadApp;

public class TestVirtualThread {
private static Logger logger = LoggerFactory.getLogger(TestVirtualThread.class);
	
	@Test
	public void testMain() {
		try {
			TestVirtualThreadApp.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
