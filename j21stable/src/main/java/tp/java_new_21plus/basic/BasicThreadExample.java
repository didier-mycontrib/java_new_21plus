package tp.java_new_21plus.basic;

public class BasicThreadExample {
    public static void main(String[] args) {
        System.out.println("start of main() executed by " + Thread.currentThread().getName());
        CountTask countTask = new CountTask(0,5);
        CountTask countDownTask = new CountTask(5,0);
        Thread t1= new Thread(countTask); t1.setName("countUpThread");
        Thread t2= new Thread(countDownTask); t2.setName("countDownThread");
        t1.start(); t2.start();
        System.out.println("end of main() executed by " + Thread.currentThread().getName());
    }
}
