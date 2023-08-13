// https://www.youtube.com/watch?v=UOr9kMCCa5g
public class MyBlockingQueue<E> {

    private Queue<E> queue;
    private int max = 16;
    private ReentrantLock lock = new ReentrantLock(true); // true for fairness
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public MyBlockingQueue(int size) {
        queue = new LinkedList<>(); // can also use array, but need to track head and tail indexes
        this.max = size;
    }

    public void put(E e) {
        lock.lock();
        try {
            while (queue.size() == max) { // 不能用 if，因为 await 会造成 unlock（https://stackoverflow.com/a/11308829/6481829），有可能导致多个线程进入此处 https://www.youtube.com/watch?v=UOr9kMCCa5g&t=497s
                notFull.await(); // block the thread until queue has at least 1 slot to add item
            }
            queue.add(e); // protected by lock
            notEmpty.signalAll(); // signal for notEmpty
        } finally {
            lock.unlock();
        }
    }

    public E take() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await(); // block the thread until queue has at least 1 item to take
            }
            E item = queue.remove();
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }
}



// Implement 2
public class MyBlockingQueue<E> {

    private Queue<E> queue;
    private int max = 16;
    private Object notEmpty = new Object();
    private Object notFull = new Object();

    public MyBlockingQueue(int size) {
        queue = new LinkedList<>(); // can also use array, but need to track head and tail indexes
        this.max = size;
    }

    public void put(E e) {
        while (queue.size() == max) {
            synchronized (notFull) { // The object used to wait/notify should be the same object used by threads to synchronize (to avoid IllegalMonitorStateException)
                notFull.wait(); // block the thread until queue has at least 1 slot to add item
            }
        }
        queue.add(e);
        synchronized (notEmpty) {
            notEmpty.notifyAll(); // notify for notEmpty
        }
    }

    public E take() {
        while (queue.size() == 0) {
            synchronized (notEmpty) {
                notEmpty.wait(); // block the thread until queue has at least 1 item to take
            }
        }
        E item = queue.remove();
        synchronized (notFull) {
            notFull.notifyAll();
        }
        return item;
    }
}
