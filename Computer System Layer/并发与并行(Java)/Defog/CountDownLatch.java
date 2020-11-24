public static void main(String[] args) throws InterruptedException {

    ExecutorService service = new Executors.newFixedThreadPool(4);

    CountDownLatch latch = new CountDownLatch(3);
    service.submit(new DependentTask(latch));
    service.submit(new DependentTask(latch));
    service.submit(new DependentTask(latch));

    latch.await(); // block if count still > 0
    System.out.println("All latch count to 0 already");
}

public static class DependentTask implements Runnable {

    private CountDownLatch latch;
    public DependentTask(CountDownLatch latch) { this.latch = latch; }

    @Override
    public void run() {
        // startup task
        latch.countDown();
        // continue other operations
    }
}
