// https://www.youtube.com/watch?v=ImtZgX1nmr8

public void main(String[] args) {
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
        //     .thenApplyAsync(order -> dispatch(order))
        //     .thenAccept(order -> sendEmail(order));
    }
}
