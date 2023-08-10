// 双端队列
Deque deque = new LinkedList<>();
// or
Deque deque = new ArrayDeque<>();



// 优先队列
// example
PriorityQueue<Obj> pQueue = new PriorityQueue(
    Comparator.comparing(Obj::getVal1).reversed()
    .thenComparing(Obj::getVal2)
    .thenComparing(Obj::getVal3));

pQueue.add(obj1);
Obj firstObj = pQueue.poll();



// 优先队列 with customized Object element and customized Comparator (lc q692)
Queue<Node> pq = new PriorityQueue<>(new MyComparator());

public class MyComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if (n1.val != n2.val) return n1.val.compareTo(n2.val);
        return n2.key.compareTo(n1.key);
    } 
}

public class Node {
    public String key;
    public Integer val;
}



// 线程安全队列 - https://jenkov.com/tutorials/java-util-concurrent/blockingqueue.html
public class BlockingQueueExample {
    public static void main(String[] args) throws Exception {
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
        Thread.sleep(4000);
    }
}

public class Producer implements Runnable {
    protected BlockingQueue queue = null;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Consumer implements Runnable {
    protected BlockingQueue queue = null;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
