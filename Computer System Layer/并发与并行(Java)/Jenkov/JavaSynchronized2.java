public class StaticSynchronizedExchanger {

    protected static Object object = null;

    public static synchronized void setObject(Object o) {
        object = o;
    }

    public static synchronized Object getObject() {
        return object;
    }

    public static void setObj(Object o) {
        synchronized(StaticSynchronizedExchanger.class) {
            object = o;
        }
    }

    public static Object getObj() {
        synchronized(StaticSynchronizedExchanger.class) {
            return object;
        }
    }
}
// 该类中的函数总是只有一个线程能访问，因为是全是静态方法，所以 monitor object 总是同一个，所以任意时候都至多只有一个线程在使用该类中的函数。



public class MixedSynchronization {

    protected static Object staticObj = null;

    public static synchronized void setStaticObj(Object obj) {
        staticObj = obj;
    }

    protected Object instanceObj = null;

    public synchronized void setInstanceObj(Object obj) {
        this.instanceObj = obj;
    }
}



public class MultipleMonitorObjects {

    private Object monitor1 = new Object();
    private Object monitor2 = new Object();

    private int counter1 = 0;
    private int counter2 = 0;

    public void incCounter1() {
        synchronized(this.monitor1) {
            this.counter1++;
        }
    }

    public void incCounter2() {
        synchronized(this.monitor2) {
            this.counter2++;
        }
    }
}



public class SharedMonitorObjects {

    private Object monitor = null;

    private int counter = 0;

    public SharedMonitorObjects(Object monitor) {
        if (monitor == null) {
            throw new IllegalArgumentException(
                "Monitor object cannot be null."
            );
        }
        this.monitor = monitor;
    }

    public void incCounter() {
        synchronized(this.monitor) {
            this.counter++;
        }
    }
}

public class SharedMonitorObjectsMain {
    
    public static void main(String[] args) {
        Object monitor1 = new Object();
        SharedMonitorObjects smo1 = new SharedMonitorObjects(monitor1);
        SharedMonitorObjects smo2 = new SharedMonitorObjects(monitor1);
        // 以上可同理是其他的类对象，但注意不要使用某些对象比如 String，
        // 因为 Java 编译优化过程中可能把这里两个直接输入但同值的字符串（比如 "monitor1"）优化为一个对象的情况
        // 虽然结果与上面相同但如果你本身想输入相同值但期望互不 block 的话则不会如愿、会出错
        // 所以一般不要使用这种常用数据类型类或常量作为 monitor object。
        // SharedMonitorObjects smo1 = new SharedMonitorObjects("monitor1");
        // SharedMonitorObjects smo2 = new SharedMonitorObjects("monitor1");
        smo1.incCounter();
        smo2.incCounter();

        Object monitor2 = new Object();
        SharedMonitorObjects smo3 = new SharedMonitorObjects(monitor2);
        smo3.incCounter();
    }
}
