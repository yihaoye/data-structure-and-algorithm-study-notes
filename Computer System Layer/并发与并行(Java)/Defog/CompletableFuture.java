// https://www.youtube.com/watch?v=ImtZgX1nmr8


public static void main(String[] args) {
    
    ExecutorService service = new Executors.newFixedThreadPool(10);
    try {
        Future<Order> future = service.submit(getOrderTask());
        Order order = future.get(); // blocking

        Future<Order> future1 = service.submit(enrichTask(order));
        order = future1.get(); // blocking

        Future<Order> future2 = service.submit(performPaymentTask(order));
        order = future2.get(); // blocking

        Future<Order> future3 = service.submit(dispatchTask(order));
        order = future3.get(); // blocking

        Future<Order> future4 = service.submit(sendEmailTask(order));
        order = future4.get(); // blocking
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
}


public static void main(String[] args) {

    for (int i=0; i<100; i++) {
        CompletableFuture.supplyAsync(() -> getOrder())
            .thenApply(order -> enrich(order))
            .thenApply(order -> performPayment(order))
            .thenApply(order -> dispatch(order))
            .thenAccept(order -> sendEmail(order));

        // ExecutorService cpuBound = new Executors.newFixedThreadPool(4);
        // ExecutorService ioBound = new Executors.newCachedThreadPool();
        // CompletableFuture.supplyAsync(() -> getOrder(), ioBound)
        //     .thenApplyAsync(order -> enrich(order), cpuBound)
        //     .thenApplyAsync(order -> performPayment(order), ioBound)
        //     .exceptionally(e -> new FailedOrder())
        //     .thenApplyAsync(order -> dispatch(order))
        //     .thenAccept(order -> sendEmail(order))
        //     .thenCombine();
    }
}
