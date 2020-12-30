// https://www.youtube.com/watch?v=ImtZgX1nmr8


/*
CompletableFuture used for:

perform possible asynchronous (non-blocking) computation and trigger dependant computations which could also be asynchronous.
*/


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


/*
上面的 getOrderTask + enrichTask + performPaymentTask + dispatchTask + sendEmailTask 被视为一个 independant flow，
若 main thread 需要执行多个（比如 100 个）同类的 flow 时，希望其互相不要影响、阻塞，且任意 flow 也不应该阻塞 main thread。

另，independant flow 里的 tasks 却不一定都 dependant，比如后 4 个 tasks 均依赖于 getOrderTask，performPaymentTask 与 enrichTask 互相不依赖，dispatchTask 依赖于 performPaymentTask 与 enrichTask 等等。
*/
public static void main(String[] args) {

    for (int i=0; i<100; i++) {
        CompletableFuture.supplyAsync(() -> getOrder()) // first operation has to be async always, in this case, all the subsequent operations are handled by same thread
            .thenApply(order -> enrich(order))
            .thenApply(order -> performPayment(order))
            .thenApply(order -> dispatch(order))
            .thenAccept(order -> sendEmail(order));
        // 上面的代码没有指定自定义的线程池，则会使用默认内置的 ForkJoinPool.commonPool()

        /* 下面这段代码是指定自定义线程池的情况 */
        // ExecutorService cpuBound = new Executors.newFixedThreadPool(4);
        // ExecutorService ioBound = new Executors.newCachedThreadPool();
        // CompletableFuture.supplyAsync(() -> getOrder(), ioBound) // specify own/customized thread pool when with sec paramter ExecutorService
        //     .thenApplyAsync(order -> enrich(order), cpuBound) // subsequent operation not handled by same thread when using thenApplyAsync()
        //     .thenApplyAsync(order -> performPayment(order), ioBound) // specify own/customized thread pool when with sec paramter ExecutorService
        //     .exceptionally(e -> new FailedOrder()) // exception catch when any of above operation failed
        //     .thenApplyAsync(order -> dispatch(order))
        //     .thenAccept(order -> sendEmail(order))
        //     .thenCombine(CompletableFuture.supplyAsync(() -> otherTask()), (res1, res2) -> System.out.println(res1 + res2)));
    }
}
