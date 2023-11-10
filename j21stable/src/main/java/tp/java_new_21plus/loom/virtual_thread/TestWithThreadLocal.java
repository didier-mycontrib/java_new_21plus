package tp.java_new_21plus.loom.virtual_thread;

public class TestWithThreadLocal {
	
	
	public static void main(String[] args)throws Exception {
		test1();
	}
	
	public static void test1()throws Exception {
		for(int i=1;i<=3;i++) {
			Thread vti = Thread.ofVirtual().name("vt"+i)
			         .unstarted(MyRunnableUtil.withSubCalls(i*10));
			vti.start();// démarrage de vti
			vti.join();//attente de la fin de vti depuis main Thread
		}
	}

}
