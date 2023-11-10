package tp.java_new_21plus.loom.virtual_thread;

import java.time.Duration;
import java.util.concurrent.Callable;

public class MyRunnableUtil {
	
	public static ThreadLocal<Integer> compteurThreadLocal = new ThreadLocal<Integer>();
	
	public static Runnable prepareBasicRunnable() {
		return ()->{
			MyThreadUtil.sleep(Duration.ofSeconds(1));
			MyThreadUtil.log("basic task part1");
			MyThreadUtil.sleep(Duration.ofSeconds(1));
			MyThreadUtil.log("basic task part2");
		};
	}
	
	public static Runnable prepareCoffeeRunnable() {
		return ()->{
			MyThreadUtil.log("prepare coffee");
			//MyThreadUtil.sleep(1000);//nbMs
			//MyThreadUtil.sleep(Duration.ofMillis(1000));
			MyThreadUtil.sleep(Duration.ofSeconds(1));
			MyThreadUtil.log("coffee is ready");
		};
	}
	
	public static Runnable prepareJokeRunnable() {
		return ()->{
			MyThreadUtil.log("prepare a joke");
			MyThreadUtil.sleep(Duration.ofMillis(500));
			MyThreadUtil.log("joke is ready");
		};
	}
	
	public static Callable<Integer> longComputationCallable() {
		return ()->{
			MyThreadUtil.log("start computing");
			MyThreadUtil.sleep(Duration.ofSeconds(1));
			MyThreadUtil.log("cumputing is ready");
			return 5;
		};
	}
	
	public static void subCallWithoutParams() {
		int ii= compteurThreadLocal.get();
		MyThreadUtil.log("subCallWithoutParams ii="+ii);
	}

	public static Runnable withSubCalls(int i) {
		return ()->{
		MyThreadUtil.log("begin of withSubCalls i="+i);
		compteurThreadLocal.set(i);
		subCallWithoutParams();
		MyThreadUtil.sleep(Duration.ofMillis(50));
		MyThreadUtil.log("end of withSubCalls i="+i);
		};
	}
	
	
}
