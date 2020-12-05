// https://www.youtube.com/watch?v=UOr9kMCCa5g
public class MyBlockingQueue<E> {

    private Queue<E> queue;
    private int max = 16;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public MyBlockingQueue(int size) {
        queue = new LinkedList<>(); // can also use array, but need to track head and tail indexes
        this.max = size;
    }

    public void put(E e) {
        lock.lock();
        try {
            while (queue.size() == max) { // 不能用 if，因为 await 会造成 unlock，有可能导致多个线程进入此处 https://www.youtube.com/watch?v=UOr9kMCCa5g&t=497s
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

    public synchronized void put(E e) {
        while (queue.size() == max) {
            notFull.await(); // block the thread until queue has at least 1 slot to add item
        }
        queue.add(e);
        notEmpty.signalAll(); // signal for notEmpty
    }

    public synchronized E take() {
        while (queue.size() == 0) {
            notEmpty.await(); // block the thread until queue has at least 1 item to take
        }
        E item = queue.remove();
        notFull.signalAll();
        return item;
    }
}
