// 本代码请配合参考 ../Java%20Memory%20Model%203.png


public class MyRunnable implements Runnable() {
    private int count = 0;

    @Override
    public void run() {
        for (int i=0; i<1_000_000; i++) {
            // 在下面的 SharedObjects 里，至少会有一个 thread 打印出 2000000（另一个可能是少于 2000000 的一个不定的数），
            // 如果没有 synchronized，则两个 thread 都不会打印到 2000000（小于且数不确定且两 thread 最终打印数不一定相同）。
            synchronized(this) {
                this.count++;
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + this.count);
    }
}

public class MyObject {}

// Race Conditions in Java threads
public class SharedObjects {
    public static void main(String[] args) {
        Runnable runnable = new MyRunnable();

        Thread thread1 = new Thread(runnable, "Thread 1");
        Thread thread2 = new Thread(runnable, "Thread 2");
        thread1.start();
        thread2.start();
    }
}
