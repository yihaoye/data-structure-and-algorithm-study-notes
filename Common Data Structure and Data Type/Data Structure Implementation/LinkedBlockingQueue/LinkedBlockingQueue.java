// 源码分析：https://developer.aliyun.com/article/774297

public class LinkedBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {
    private static final long serialVersionUID = -6903933977591709194L;

    // 基于链表实现，肯定要有结点类，典型的单链表结构
    static class Node<E> {
        E item;
        Node<E> next;
        Node(E x) { item = x; }
    }

    //容量
    private final int capacity;

    //当前队列元素数量
    private final AtomicInteger count = new AtomicInteger();

    // 头节点，不存数据
    transient Node<E> head;

         // 尾节点，便于入队
    private transient Node<E> last;

    // take锁，出队锁，只有take，poll方法会持有
    private final ReentrantLock takeLock = new ReentrantLock();

    // 出队等待条件
        // 当队列无元素时，take锁会阻塞在notEmpty条件上，等待其它线程唤醒
    private final Condition notEmpty = takeLock.newCondition();

    // 入队锁，只有put，offer会持有
    private final ReentrantLock putLock = new ReentrantLock();

    // 入队等待条件
      // 当队列满了时，put锁会会阻塞在notFull上，等待其它线程唤醒
    private final Condition notFull = putLock.newCondition();

    //同样提供三个构造器
    public LinkedBlockingQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
       // 初始化head和last指针为空值节点
        this.capacity = capacity;
        last = head = new Node<E>(null);
    }
    
    public LinkedBlockingQueue() {
        // 如果没传容量，就使用最大int值初始化其容量
        this(Integer.MAX_VALUE);
    }

    public LinkedBlockingQueue(Collection<? extends E> c) {}
    
    //入队
    public void put(E e) throws InterruptedException {
        // 不允许null元素
        if (e == null) throw new NullPointerException();
        //规定给当前put方法预留一个本地变量
        int c = -1;
        // 新建一个节点
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        // 使用put锁加锁
        putLock.lockInterruptibly();
        try {
                    // 如果队列满了，就阻塞在notFull条件上
            // 等待被其它线程唤醒
            while (count.get() == capacity) {
                notFull.await();
            }
            // 队列不满了，就入队
            enqueue(node);
            // 队列长度加1
            c = count.getAndIncrement();
            // 如果现队列长度小于容量
                // 就再唤醒一个阻塞在notFull条件上的线程
            // 这里为啥要唤醒一下呢？
            // 因为可能有很多线程阻塞在notFull这个条件上的
            // 而取元素时只有取之前队列是满的才会唤醒notFull
            // 为什么队列满的才唤醒notFull呢？
            // 因为唤醒是需要加putLock的，这是为了减少锁的次数
            // 所以，这里索性在放完元素就检测一下，未满就唤醒其它notFull上的线程
            // 说白了，这也是锁分离带来的代价
            if (c + 1 < capacity)
                notFull.signal();
        } finally {
            // 释放锁
            putLock.unlock();
        }
        // 如果原队列长度为0，现在加了一个元素后立即唤醒notEmpty条件
        if (c == 0)
            signalNotEmpty();
    }
    
    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        // 加take锁
        takeLock.lock();
        try {
            // 唤醒notEmpty条件
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }


    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }


    private void enqueue(Node<E> node) {
        // 直接加到last后面
        last = last.next = node;
    }

    public boolean offer(E e) {
        //用带过期时间的说明
    }

    public boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException {

        if (e == null) throw new NullPointerException();
        //转换为纳秒
        long nanos = unit.toNanos(timeout);
        int c = -1;
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        //获取入队锁，支持等待锁的过程中被中断
        putLock.lockInterruptibly();
        try {
            //队列满了，再看看有没有超时
            while (count.get() == capacity) {
                if (nanos <= 0)
                    //等待时间超时
                    return false;
                //进行等待，awaitNanos(long nanos)是AQS中的方法
                //在等待过程中，如果被唤醒或超时，则继续当前循环
                //如果被中断，则抛出中断异常
                nanos = notFull.awaitNanos(nanos);
            }
            //进入队尾
            enqueue(new Node<E>(e));
            c = count.getAndIncrement();
            //说明当前元素后面还能再插入一个
            //就唤醒一个入队条件队列中阻塞的线程
            if (c + 1 < capacity)
                notFull.signal();
        } finally {
            putLock.unlock();
        }
        //节点数量为0，说明队列是空的
        if (c == 0)
            //唤醒一个出队条件队列阻塞的线程
            signalNotEmpty();
        return true;
    }

    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            // 如果队列无元素，则阻塞在notEmpty条件上
            while (count.get() == 0) {
                notEmpty.await();
            }
            // 否则，出队
            x = dequeue();
            // 获取出队前队列的长度
            c = count.getAndDecrement();
            // 如果取之前队列长度大于1，则唤醒notEmpty
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        // 如果取之前队列长度等于容量
         // 则唤醒notFull
        if (c == capacity)
            signalNotFull();
        return x;
    }
    
    private E dequeue() {
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h; // help GC
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E x = null;
        int c = -1;
        long nanos = unit.toNanos(timeout);
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                //队列为空且已经超时，直接返回空
                if (nanos <= 0)
                    return null;
                //等待过程中可能被唤醒，超时，中断
                nanos = notEmpty.awaitNanos(nanos);
            }
            //进行出队操作
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        //如果出队前，队列是满的，则唤醒一个被take()阻塞的线程
        if (c == capacity)
            signalNotFull();
        return x;
    }

    public E poll() {
        //
    }

    public E peek() {
        if (count.get() == 0)
            return null;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            Node<E> first = head.next;
            if (first == null)
                return null;
            else
                return first.item;
        } finally {
            takeLock.unlock();
        }
    }

    void unlink(Node<E> p, Node<E> trail) {
        // assert isFullyLocked();
        // p.next is not changed, to allow iterators that are
        // traversing p to maintain their weak-consistency guarantee.
        p.item = null;
        trail.next = p.next;
        if (last == p)
            last = trail;
        if (count.getAndDecrement() == capacity)
            notFull.signal();
    }

    public boolean remove(Object o) {
        if (o == null) return false;
        fullyLock();
        try {
            for (Node<E> trail = head, p = trail.next;
                 p != null;
                 trail = p, p = p.next) {
                if (o.equals(p.item)) {
                    unlink(p, trail);
                    return true;
                }
            }
            return false;
        } finally {
            fullyUnlock();
        }
    }

    public boolean contains(Object o) {
    }
   
    static final class LBQSpliterator<E> implements Spliterator<E> {
      
    }
}
