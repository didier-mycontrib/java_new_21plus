package tp.java_new_21plus.loom.structured_concurrency;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;

import tp.java_new_21plus.loom.fetcher.FakeLongTaskService;

//import jdk.incubator.concurrent.StructuredTaskScope;//incubator in jdk20
//scope.fork(...) returning Future<Double> in java20_incubator
//scope.fork(....) returning StructuredTaskScope.Subtask<Double> in java21_preview


public class AllFinishedSubTasks {
	
	public static AllFinishedSubTasks INSTANCE = new AllFinishedSubTasks();
	/*
	 ShutdownOnSuccess captures the first result and shuts down the task scope 
	 to interrupt unfinished threads and wakeup the owner. 
	 This class is intended for cases where the result of any subtask will do (“invoke any”) 
	 and where there is no need to wait for results of other unfinished tasks. 
	 It defines methods to get the first result or throw an exception if all subtasks fail.
	 */
	
	public List<Double> doubleListSlowSquareRoot(List<Double> doubleList) {
		List<Double> doubleResultList = new ArrayList<>();
	    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
	      List<StructuredTaskScope.Subtask<Double>> subtaskList = new ArrayList<StructuredTaskScope.Subtask<Double>>();
	      //int extra = 1000; //causing timeoutException
	      int extra = 10; //no global error
	      for(Double val : doubleList) {
	           StructuredTaskScope.Subtask<Double> subtask = scope.fork(
	        		   () -> (Double) FakeLongTaskService.INSTANCE.slowSquareRoot(val, 100+extra)
	        		   );
	           subtaskList.add(subtask);
	      }
	      //scope.join();
	      scope.joinUntil(Instant.now().plusSeconds(1)); //with TimeoutException if all is not finish 1s later now
	      
	      for(StructuredTaskScope.Subtask<Double> subtask : subtaskList ) {
	    	  doubleResultList.add(subtask.get());
	      }
	    } catch (InterruptedException e) {
	      throw new RuntimeException(e);
	    } catch (TimeoutException e) {
	      throw new RuntimeException(e);
	    } catch (/*ExecutionException*/ Exception e) {
		  throw new RuntimeException(e);
		}
	    return doubleResultList;
	  }
	

}
