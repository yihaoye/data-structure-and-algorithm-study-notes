// https://www.youtube.com/watch?v=ImtZgX1nmr8

public void main(String[] args) {
    for (int i=0; i<100; i++) {
        CompletableFuture.supplyAsync(() -> getOrder())
            .thenApply(order -> enrich(order))
            .thenApply(order -> performPayment(order))
            .thenApply(order -> dispatch(order))
            .thenApply(order -> sendEmail(order));
    }
}
