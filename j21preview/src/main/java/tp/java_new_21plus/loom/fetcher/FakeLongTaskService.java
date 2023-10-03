package tp.java_new_21plus.loom.fetcher;

public class FakeLongTaskService {
	public static FakeLongTaskService INSTANCE = new FakeLongTaskService();
	
	public double slowSquareRoot(double x,int nbMs) throws InterruptedException{
		try {
			Thread.currentThread().sleep(nbMs);
		} catch (InterruptedException e) {
			System.err.println(Thread.currentThread()+"was interrupted before end of sleep="+nbMs);
			throw e;
		}
		if(nbMs<50) throw new RuntimeException("lunatic exception because nbMs<50");
		double res = Math.sqrt(x);
		System.out.println("slowSquareRoot(x=" +x + ") returning res="+res + " after sleep=" + nbMs + "  "+ Thread.currentThread());
		return res;
	}
}
