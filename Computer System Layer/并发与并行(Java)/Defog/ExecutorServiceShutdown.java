ExecutorService service = new Executors.newFixedThreadPool(10);
for (int i=0; i<100; i++) {
    service.execute(new Task());
}

// initiate shutdown
service.shutdown();

// will throw RejectionExecutionException
service.execute(new Task());

// will return true, since shutdown has begun
service.isShutdown();

// will return true if all tasks completed
// including queued one
service.isTerminated();

// block until all tasks are completed or if timeout occurs
service.awaitTermination(10, TimeUnit.SECONDS);

// will initiate shutdown and return all queued tasks
List<Runnable> runnables = service.shutdownNow();
