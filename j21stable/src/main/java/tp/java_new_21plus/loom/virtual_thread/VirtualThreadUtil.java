package tp.java_new_21plus.loom.virtual_thread;

public class VirtualThreadUtil {
	
	static Thread createVirtualThread(String virtualThreadName, Runnable runnable) {
		  return Thread.ofVirtual()
		    .name(virtualThreadName)
		    //.start(runnable); //code automatiquement démarré
		    .unstarted(runnable);//code à démarrer via appel à .start() ou équivalent
		}
	
}
