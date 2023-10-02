package tp.java_new_21plus.loom.structured_concurrency;

import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;

//import jdk.incubator.concurrent.StructuredTaskScope;//incubator in jdk20
//scope.fork(...) returning Future<Double> in java20_incubator
//scope.fork(....) returning StructuredTaskScope.Subtask<Double> in java21_preview


public class WithFirstFinishedSubTask {
	
	public static WithFirstFinishedSubTask INSTANCE = new WithFirstFinishedSubTask();
	/*
	 ShutdownOnSuccess captures the first result and shuts down the task scope 
	 to interrupt unfinished threads and wakeup the owner. 
	 This class is intended for cases where the result of any subtask will do (“invoke any”) 
	 and where there is no need to wait for results of other unfinished tasks. 
	 It defines methods to get the first result or throw an exception if all subtasks fail.
	 */
	
	public Double quickerOfSlowSquareRoot(double x) {
		Double firstFinishedResult=0.0;
	    try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Double>()) {
	      //int extra = -200; //causing all subtaskToFail because xxx + extra < 50
	      //int extra = 1000; //causing timeoutException
	      int extra = 10; //no global error
	      StructuredTaskScope.Subtask<Double> subtask1 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 200+extra));//200ms
	      StructuredTaskScope.Subtask<Double> subtask2 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 100+extra));
	      StructuredTaskScope.Subtask<Double> subtask3 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 50+extra));//50ms
	      StructuredTaskScope.Subtask<Double> subtask4 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 40));//less than 50->lunatic Exception
	      //NB: futurRes4 throws a RuntimeException , it fails , but "no problem" if at least another // subtask succeed
	      
	      //scope.join();
	      scope.joinUntil(Instant.now().plusSeconds(1)); //with TimeoutException if all is not finish 1s later now
	      
	      //firstFinishedResult = scope.result(); //without specific exception
	      firstFinishedResult = scope.result(e -> new RuntimeException("cannot compute squareRoot"));//with specific exception if all failed (but not timeout)
	      System.out.println("firstFinishedResult="+firstFinishedResult);
	    } catch (InterruptedException e) {
	      throw new RuntimeException(e);
	    } catch (TimeoutException e) {
	      throw new RuntimeException(e);
	    } catch (/*ExecutionException*/ Exception e) {
		  throw new RuntimeException(e);
		}
	    return firstFinishedResult;
	  }
	

}
