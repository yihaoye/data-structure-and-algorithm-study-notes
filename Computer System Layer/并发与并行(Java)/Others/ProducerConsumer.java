// https://www.twle.cn/c/yufei/javatm/javatm-basic-blockingqueue.html
// 多线程生产者 - 消费者示例

/*
生产者将生成一个 0 到 100 的随机数，并将该数字放在 BlockingQueue 中。这里将创建 4 个线程用于生成随机数并使用 put() 方法阻塞，直到队列中有可用空间。需要记住的重要一点是，需要阻止消费者线程无限期地等待元素出现在队列中。
从生产者向消费者发出信号的好方法是，不需要处理消息，而是发送称为毒（ poison ）丸（ pill ）的特殊消息。需要发送尽可能多的毒（ poison ）丸（ pill ），因为有消费者。然后当消费者从队列中获取特殊的毒（ poison ）丸（ pill ）消息时，它将优雅地完成执行。
*/
public class NumbersProducer implements Runnable {
    private BlockingQueue<Integer> numbersQueue;
    private final int poisonPill;
    private final int poisonPillPerProducer;

    public NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill, int poisonPillPerProducer) {
        this.numbersQueue = numbersQueue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
    }
    public void run() {
        try {
            generateNumbers();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            numbersQueue.put(ThreadLocalRandom.current().nextInt(100));
        }
        for (int j = 0; j < poisonPillPerProducer; j++) {
            numbersQueue.put(poisonPill);
        }
     }
}

/*
生成器构造函数将 BlockingQueue 作为参数，用于协调生产者和使用者之间的处理。这里看到方法 generateNumbers() 将 100 个元素放入队列中。它还需要有毒（ poison ）丸（ pill ）消息，以便知道在执行完成时放入队列的消息类型。该消息需要将 poisonPillPerProducer 次放入队列中。
每个消费者将使用 take() 方法从 BlockingQueue 获取一个元素，因此它将阻塞，直到队列中有一个元素。从队列中取出一个 Integer 后，它会检查该消息是否是毒（ poison ）丸（ pill ），如果是，则完成一个线程的执行。否则，它将在标准输出上打印出结果以及当前线程的名称。
*/
public class NumbersConsumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private final int poisonPill;

    public NumbersConsumer(BlockingQueue<Integer> queue, int poisonPill) {
        this.queue = queue;
        this.poisonPill = poisonPill;
    }
    public void run() {
        try {
            while (true) {
                Integer number = queue.take();
                if (number.equals(poisonPill)) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " result: " + number);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
// 需要注意的重要事项是队列的使用。与生成器构造函数中的相同，队列作为参数传递。这里可以这样做，是因为 BlockingQueue 可以在线程之间共享而无需任何显式同步。

/*
既然这里有生产者和消费者，就可以开始下面的计划。这里需要定义队列的容量，并将其设置为 100 个元素。现在希望有 4 个生产者线程，并且有许多消费者线程将等于可用处理器的数量
BlockingQueue 是使用具有容量的构造创建的。这里正在创造 4 个生产者和 N 个消费者。将毒（ poison ）丸（ pill ）消息指定为 Integer.MAX_VALUE，因为这里的生产者在正常工作条件下永远不会发送这样的值。这里要注意的最重要的事情是 BlockingQueue 用于协调它们之间的工作。
当运行程序时，4 个生产者线程将随机整数放入 BlockingQueue 中，消费者将从队列中获取这些元素。每个线程将打印到标准输出线程的名称和结果。
*/
public void main(String[] args) {
    int BOUND = 10;
    int N_PRODUCERS = 4;
    int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
    int poisonPill = Integer.MAX_VALUE;
    int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
    int mod = N_CONSUMERS % N_PRODUCERS;
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);

    for (int i = 1; i < N_PRODUCERS; i++) new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
    for (int j = 0; j < N_CONSUMERS; j++) new Thread(new NumbersConsumer(queue, poisonPill)).start();

    new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer + mod)).start();
}
