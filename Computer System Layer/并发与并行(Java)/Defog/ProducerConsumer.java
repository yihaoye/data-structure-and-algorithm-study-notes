// https://www.youtube.com/watch?v=UOr9kMCCa5g
public static void main(String[] args) {

    BlockingQueue<Item> queue = new ArrayBlockingQueue<>(10);

    // Producer
    final Runnable producer = () -> {
        while (true) {
            queue.put(createItem()); // thread blocks if queue full
        }
    };
    new Thread(producer).start();
    new Thread(producer).start();

    // Consumer
    final Runnable consumer = () -> {
        while (true) {
            Item i = queue.take(); // thread blocks if queue empty
            process(i);
        }
    };
    new Thread(consumer).start();
    new Thread(consumer).start();

    Thread.sleep(1000);
}
