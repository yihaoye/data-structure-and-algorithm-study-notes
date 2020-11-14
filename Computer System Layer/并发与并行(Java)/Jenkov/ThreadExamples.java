/* Subclass or Runnable?
Prefer implementing Runnable, and handing an instance of the implementation to a Thread instance. 
When having the Runnable's executed by a thread pool it is easy to queue up the Runnable instances until a thread from the pool is idle. 
This is a little harder to do with Thread subclasses.

Sometimes may have to implement Runnable as well as subclass Thread. 
For instance, if creating a subclass of Thread that can execute more than one Runnable. 
This is typically the case when implementing a thread pool.
*/


public class MyThread extends Thread {
    public void run(){
       System.out.println("MyThread running");
    }
}
MyThread myThread = new MyThread();
myTread.start();


Thread thread = new Thread(){
    public void run(){
      System.out.println("Thread Running");
    }
}
thread.start();


public class MyRunnable implements Runnable {
    @Override
    public void run(){
       System.out.println("MyRunnable running");
    }
}
Runnable runnable = new MyRunnable(); // or an anonymous class, or lambda...
// Runnable runnable = () -> { 
//     System.out.println("Lambda Runnable running"); 
// };
Thread thread = new Thread(runnable);
thread.start();





Thread thread = new Thread("New Thread") {
    public void run(){
      System.out.println("run by: " + getName());
    }
};
thread.start();


Runnable runnable = () -> { 
    String threadName = Thread.currentThread().getName();
    System.out.println(threadName + " running"); 
};
Thread thread = new Thread(runnable, "New Thread 2");
thread.start();





public class ThreadExample {
    public static void main(String[] args){
        Runnable runnable = () -> { 
            for(int i=0; i<5; i++){
                sleep(1000);
                System.out.println("Thread: running");
            }
        }

        Thread thread = new Thread(runnable);
        /*
            Java程序入口就是由JVM启动main线程，main线程又可以启动其他线程。当所有线程都运行结束时，JVM退出，进程结束。
            如果有一个线程没有退出，JVM进程就不会退出。所以，必须保证所有线程都能及时结束
            但是有一种线程的目的就是无限循环，例如，一个定时触发任务的线程：
            class TimerThread extends Thread {
                @Override
                public void run() {
                    while (true) {
                        System.out.println(LocalTime.now());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
            如果这个线程不结束，JVM进程就无法结束。问题是，由谁负责结束这个线程？
            然而这类线程经常没有负责人来负责结束它们。但是，当其他线程结束时，JVM进程又必须要结束，怎么办？
            答案是使用守护线程（Daemon Thread）。
            守护线程是指为其他线程服务的线程。在JVM中，所有非守护线程都执行完毕后，无论有没有守护线程，虚拟机都会自动退出。
            因此，JVM退出时，不必关心守护线程是否已结束。
        */
        thread.setDaemon(true);
        thread.start();

        try {
            /*
                在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程将可能早于子线程结束。
                如果主线程需要知道子线程的执行结果时，就需要等待子线程执行结束了。
                主线程可以 sleep(xx)，但这样的xx时间不好确定，因为子线程的执行时间不确定，join()方法比较合适这个场景。
                join()方法的作用，是等待这个线程结束；也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），
                等待子线程结束了(Waits for this thread to die.)，才能继续执行t.join()之后的代码块。
            */
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}





public class MyRunnable implements Runnable {
    private boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }
    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    @Override
    public void run() {
        while(keepRunning()) {
            // keep doing what this thread should do.
            System.out.println("Running");
            try {
                Thread.sleep(3L * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class MyRunnableMain {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
        try {
            Thread.sleep(10L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myRunnable.doStop();
    }
}
