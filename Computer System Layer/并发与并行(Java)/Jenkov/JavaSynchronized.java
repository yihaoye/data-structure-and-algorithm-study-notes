// 配合参考：../Java%20Synchronized.png
public class SynchronizedExchanger {

    protected Object object = null;

    public synchronized void setObject(Object o) {
        this.object = o;
    }

    public synchronized Object getObject() {
        return this.object;
    }

    public void setObj(Object o) {
        // 这里 synchronized(this) 的 this 是 monitor object，这里多个方法和代码块其实都在 synchronize 同一个 object - this
        // 如果有多个 thread 调用该类的同一个对象的任意函数时，也只有一个 thread 能执行（../Java%20Synchronized.png），
        // 因为在这里即使方法/代码块不相同但它们 synchronize 的都是同一个 monitor object（但如果是作用在该类的不同对象，则 thread 均可执行）
        synchronized(this) {
            this.object = o;
        }
    }

    public Object getObj() {
        synchronized(this) {
            return this.object;
        }
    }
}
// 两种 synchronized 的写法不同点在于，
// synchronized 方法总是 synchronized 它从属的类实例/对象，
// 而 synchronized 代码块（synchronized(this) {...}）则可以 synchronized 任意你想设置为 monitor object 的 Java 对象



public class SynchronizedExchangerMain {

    public static void main(String[] args) {
        SynchronizedExchanger exchanger = new SynchronizedExchanger();

        Thread thread1 = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<1000; i++) {
                        exchanger.setObject(" " + i);
                    }
                }
            }
        );

        Thread thread2 = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<1000; i++) {
                        System.out.println(exchanger.getObject());
                    }
                }
            }
        );

        thread1.start();
        thread2.start();
    }
}
// 执行结果是会看到 thread 2 打印一大堆（1000 行）不连续且可能重复的值（[0, 999]内的数字，且数字是递增的，在 0 之前可能是 null - 即线程1尚未访问，且最后不一定能打印到 999）
// 这是因为这两个线程会随机可以访问共享的 monitor object，即每个线程在各自的 1000 次 for 循环中不确定地轮番访问共享的 Heap 变量，所以线程2的打印也会是不确定的。

