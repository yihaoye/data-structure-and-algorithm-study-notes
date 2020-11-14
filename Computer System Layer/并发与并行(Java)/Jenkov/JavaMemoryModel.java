// 本代码请配合参考 ../Java%20Memory%20Model.png


public class MyRunnable implements Runnable() {
    private int count = 0;

    @Override
    public void run() {
        MyObject myObject = new MyObject();
        System.out.println(myObject);

        for (int i=0; i<1_000_000; i++) {
            this.count++;
        }
        System.out.println(Thread.currentThread().getName() + " : " + this.count);
    }
}

public class MyObject {}


// 在这里，两个 thread 没有任何冲突 - 没有指向/引用任何相同的 Heap 变量，自己 Stack 里的变量与 Heap 里变量皆是一对一关系，不存在两个 Thread Stack 变量指向同一个 Heap 变量
public class SeperateObjects {
    public static void main(String[] args) {
        Runnable runnable1 = new MyRunnable();
        Runnable runnable2 = new MyRunnable();

        Thread thread1 = new Thread(runnable1, "Thread 1");
        Thread thread2 = new Thread(runnable2, "Thread 2");
        thread1.start();
        thread2.start();
    }
}


// 在这里，因为使用同一个 runnable 对象，则两个 thread 的 runnable.count 引用皆指向 JMM 里 Heap 的同一个变量，但 run 里的变量 myObject 和 i 都是各自指向不同的 Heap 变量
public class SharedObjects {
    public static void main(String[] args) {
        Runnable runnable = new MyRunnable();

        Thread thread1 = new Thread(runnable, "Thread 1");
        Thread thread2 = new Thread(runnable, "Thread 2");
        thread1.start();
        thread2.start();
    }
}
