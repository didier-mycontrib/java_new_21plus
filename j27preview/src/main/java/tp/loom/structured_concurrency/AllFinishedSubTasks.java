package tp.loom.structured_concurrency;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

import tp.loom.fetcher.FakeLongTaskService;



public class AllFinishedSubTasks {
	
	public static AllFinishedSubTasks INSTANCE = new AllFinishedSubTasks();

	
	public List<Double> doubleListSlowSquareRoot(List<Double> doubleList) {
		List<Double> doubleResultList = new ArrayList<>();
	    try (var scope = StructuredTaskScope.open(cf -> cf.withTimeout(Duration.ofMillis(1000)))) {
	      List<StructuredTaskScope.Subtask<Double>> subtaskList = new ArrayList<StructuredTaskScope.Subtask<Double>>();
	      //int extra = 1000; //causing timeoutException
	      int extra = 10; //no global error
	      for(Double val : doubleList) {
	           StructuredTaskScope.Subtask<Double> subtask = scope.fork(
	        		   () -> (Double) FakeLongTaskService.INSTANCE.slowSquareRoot(val, 100+extra)
	        		   );
	           subtaskList.add(subtask);
	      }
	      scope.join();

	      
	      for(StructuredTaskScope.Subtask<Double> subtask : subtaskList ) {
	    	  doubleResultList.add(subtask.get());
	      }
	    } catch (InterruptedException e) {
	      throw new RuntimeException(e);
	    } catch (/*ExecutionException*/ Exception e) {
		  throw new RuntimeException(e);
		}
	    return doubleResultList;
	  }
	

}
