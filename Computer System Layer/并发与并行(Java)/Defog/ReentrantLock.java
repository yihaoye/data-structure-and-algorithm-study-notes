// 配合参考：https://www.youtube.com/watch?v=ahBC69_iyk4
private static ReentrantLock lock = new ReentrantLock();

private static void accessResource() {

    lock.lock();
    try {
        // access the resource
    } finally {
        lock.unlock(); // 应该实现 try 块并把解锁放在此处以保证无论是否有抛出异常都执行解锁，否则其他线程就永远不能访问该资源了
    }
}
/*
其实与 synchronized 类似
synchronized (this) { // lock.lock()
    // access resource
} // lock.unlock()
*/

public static void main(String[] args) {
    Thread t1 = new Thread(() -> accessResource());
    t1.start();

    Thread t2 = new Thread(() -> accessResource());
    t2.start();

    Thread t3 = new Thread(() -> accessResource());
    t3.start();

    Thread t4 = new Thread(() -> accessResource());
    t4.start();
}





private static void reEnterTheLock() {
    ReentrantLock lock = new ReentrantLock(true);

    lock.lock();
    lock.lock();

    int number = lock.getHoldCount(); // number = 2
    
    lock.unlock();
    lock.unlock();
}





private static ReentrantLock lock = new ReentrantLock();

private static void accessResource() {

    lock.lock();

    // update shared resources
    
    if (someCondition()) {
        accessResource(); // getHoldCount will increase based on number of recursions
    }

    lock.unlock();
}





private static ReentrantLock lock = new ReentrantLock(true);

private static void accessResource() throws InterruptedException {

    boolean lockAcquired = lock.tryLock(5, TimeUnit.SECONDS); // lock.tryLock() doesn't honor fairness although `lock = new ReentrantLock(true)` above, work-around is use lock.tryLock(0, TimeUnit.SECONDS) which support fairness 

    if (lockAcquired) {
        try {
            // access the resource
        } finally {
            lock.unlock();
        }
    } else {
        // do alternate thing
    }
}
