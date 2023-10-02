package tp.java_new_21plus.loom.virtual_thread;

import java.time.Duration;

public class MyThreadUtil {
	
	static void log(String message) {
		  System.out.println(message + " by " + Thread.currentThread());
	}
	
	static void sleep(Duration duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static void sleep(long nbMs) {
		try {
			Thread.sleep(nbMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
