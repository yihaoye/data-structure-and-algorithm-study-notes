// Java语言直接提供了synchronized关键字用于加锁，但这种锁一是很重，二是获取时必须一直等待，没有额外的尝试机制。
// java.util.concurrent.locks包提供的ReentrantLock用于替代synchronized加锁，来看一下传统的synchronized代码：
public class Counter {
    private int count;

    public void add(int n) {
        synchronized(this) {
            count += n;
        }
    }
}

// 如果用ReentrantLock替代，可以把代码改造为：
public class Counter {
    private final Lock lock = new ReentrantLock();
    private int count;

    public void add(int n) {
        lock.lock();
        try {
            count += n;
        } finally {
            lock.unlock();
        }
    }
}
// 因为synchronized是Java语言层面提供的语法，所以不需要考虑异常，而ReentrantLock是Java代码实现的锁，我们就必须先获取锁，然后在finally中正确释放锁。
// 顾名思义，ReentrantLock是可重入锁，它和synchronized一样，一个线程可以多次获取同一个锁。

// 和synchronized不同的是，ReentrantLock可以尝试获取锁：
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try {
        ...
    } finally {
        lock.unlock();
    }
}
// 上述代码在尝试获取锁的时候，最多等待1秒。如果1秒后仍未获取到锁，tryLock()返回false，程序就可以做一些额外处理，而不是无限等待下去。
// 所以，使用ReentrantLock比直接使用synchronized更安全，线程在tryLock()失败的时候不会导致死锁。





// synchronized 可以配合 wait 和 notify 实现线程在条件不满足时等待，条件满足时唤醒
class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        return queue.remove();
    }
}
// 用 ReentrantLock 怎么编写 wait 和 notify 的功能呢？
// 答案是使用 Condition 对象来实现 wait 和 notify 的功能。
class TaskQueue {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll(); // 当调用 condition.signalAll() 方法时，它会唤醒等待在相应条件上的所有线程，但被唤醒的线程并不会立即继续执行，而是需要等待当前线程释放锁。
        } finally {
            lock.unlock();
        }
    }

    public String getTask() {
        lock.lock();
        try {
             // 在使用 Condition 进行线程通信时，建议使用 while 循环而不是 if 来等待条件。
             // 使用 while 循环可以避免虚假唤醒（Spurious Wakeup）的问题，确保条件满足时才继续执行，而不是在唤醒后无条件地继续执行。
             // 虚假唤醒是指在多线程环境下，线程可能在没有收到显式的信号（如调用 signal() 或 signalAll()）的情况下，也会从等待状态被唤醒。
             // 这种情况可能是由于操作系统或虚拟机的实现原因导致的，并且不能完全依靠 if 语句来确保条件已满足。
             // 因此，使用 while 循环来等待条件是一种更安全的做法，因为即使发生虚假唤醒，线程仍会在条件满足时继续等待，而不会提前退出等待状态。
            while (queue.isEmpty()) {
                condition.await(); // 释放锁，当前线程立即释放锁并进入等待状态，下次如果被唤醒时会从这一行继续执行（并自动重新获取锁，无需手动处理）
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}
// 使用 Condition 时，引用的 Condition 对象必须从 Lock 实例的 newCondition() 返回，这样才能获得一个绑定了 Lock 实例的 Condition 实例。
// Condition 提供的 await()、signal()、signalAll() 原理和 synchronized 锁对象的 wait()、notify()、notifyAll() 是一致的，并且其行为也是一样的
