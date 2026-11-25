package tp.loom.structured_concurrency;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;

import tp.loom.fetcher.FakeLongTaskService;


public class WithFirstFinishedSubTask {
	
	public static WithFirstFinishedSubTask INSTANCE = new WithFirstFinishedSubTask();
	/*
	 with specific joiner to capture the first result and shuts down the task scope
	 to interrupt unfinished threads and wakeup the owner.
	 */
	
	public Double quickerOfSlowSquareRoot(double x) {
		Double firstFinishedResult=0.0;
	    try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Double>anySuccessfulOrThrow(),
				cf -> cf.withTimeout(Duration.ofMillis(1000)))) {
	      //int extra = -200; //causing all subtaskToFail because xxx + extra < 50
	      //int extra = 1000; //causing timeoutException
	      int extra = 10; //no global error
	      StructuredTaskScope.Subtask<Double> subtask1 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 200+extra));//200ms
	      StructuredTaskScope.Subtask<Double> subtask2 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 100+extra));
	      StructuredTaskScope.Subtask<Double> subtask3 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 50+extra));//50ms
	      StructuredTaskScope.Subtask<Double> subtask4 = scope.fork(() -> FakeLongTaskService.INSTANCE.slowSquareRoot(x, 40));//less than 50->lunatic Exception
	      //NB: futurRes4 throws a RuntimeException , it fails , but "no problem" if at least another // subtask succeed
	      
	      firstFinishedResult = scope.join(); //without specific exception
	      //old j21 preview: firstFinishedResult = scope.result(e -> new RuntimeException("cannot compute squareRoot"));//with specific exception if all failed (but not timeout)
	      System.out.println("firstFinishedResult="+firstFinishedResult);
	    } catch (InterruptedException e) {
	      throw new RuntimeException(e);
	    }
		catch (/*ExecutionException*/ Exception e) {
		  throw new RuntimeException(e);
		}
	    return firstFinishedResult;
	  }
	

}
