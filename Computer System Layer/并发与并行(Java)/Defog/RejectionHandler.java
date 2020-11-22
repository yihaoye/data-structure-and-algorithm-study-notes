ExecutorService service =  new ThreadPoolExecutor(
    10, 
    100, 
    120, TimeUnit.SECONDS, 
    new ArrayBlockingQueue<>(300)
);

try {
    service.execute(new Task());
} catch (RejectedExecutionException e) {
    System.err.println("task rejected: " + e.getMessage());
}



ExecutorService service =  new ThreadPoolExecutor(
    10, 
    100, 
    120, TimeUnit.SECONDS, 
    new ArrayBlockingQueue<>(300),
    new CustomizedRejectionHandler()
);

private static class CustomizedRejectionHandler implements RejectedExecutionHandler {
    @Override
    public void RejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // logging / operation to perform on rejection
    }
}
