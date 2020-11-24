public static void main(String[] args) throws InterruptedException {

    Semaphore semaphore = new Semaphore(3); // permits set to 3

    ExecutorService service = new Executors.newFixedThreadPool(50);
    IntStream.of(100).forEach(i -> service.execute(new Task(semaphore)));

    service.shutdown();
    service.awaitTermination(1, TimeUnit.MINUTES);
}

static class Task implements Runnable {
    @Override
    public void run() {
        // some process

        semaphore.acquireUninterruptibly(); // semaphore.acquireUninterruptibly(2) i.e. acquire 2 permits at a time
        // IO call to the slow service or db
        semaphore.release(); // release semaphore permit back.   semaphore.release(2) i.e. release 2 permits at a time

        // rest of process
    }
}
