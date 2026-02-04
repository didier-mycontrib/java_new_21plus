package tp.java_new_21plus.basic;

public class CountTask implements Runnable {

    int from;
    int to;

    CountTask(int from,int to){
        this.from=from; this.to=to;
    }

    CountTask(){
        this(1,5);
    }

    public void display_with_pause(int i) {
        System.out.println(">>> i=" + i + " - " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); //1000ms de pause
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
       if(this.from <= this.to){
           for(int i=from;i<=to;i++){
               display_with_pause(i);
           }
       }else{
           for(int i=from;i>=to;i--){
               display_with_pause(i);
           }
       }
    }
}
