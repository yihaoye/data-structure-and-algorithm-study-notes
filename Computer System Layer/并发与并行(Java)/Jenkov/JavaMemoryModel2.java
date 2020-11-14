// 本代码请配合参考 ../Java%20Memory%20Model.png


public class MyRunnable implements Runnable() {
    private int count = 0;
    private MyObject myObject = null;

    public MyRunnable() {}
    public MyRunnable(MyObject myObject) {
        this.myObject = myObject;
    }

    @Override
    public void run() {
        System.out.println(this.myObject);

        for (int i=0; i<1_000_000; i++) {
            this.count++;
        }
        System.out.println(Thread.currentThread().getName() + " : " + this.count);
    }
}

public class MyObject {}


// 在这里，因为使用同一个 myObject 对象，则两个 thread.run 的 this.myObject 引用皆指向 JMM 里 Heap 的同一个变量
public class SharedObjects {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();

        Runnable runnable1 = new MyRunnable(myObject);
        Runnable runnable2 = new MyRunnable(myObject);

        Thread thread1 = new Thread(runnable1, "Thread 1");
        Thread thread2 = new Thread(runnable2, "Thread 2");
        thread1.start();
        thread2.start();
    }
}
