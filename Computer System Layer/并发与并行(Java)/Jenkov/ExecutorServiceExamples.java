ExecutorService executorService = new Executors.newFixedThreadPool(10);
executorService.execute(new Runnable() {
    public void run() {
        System.out.println("Asynchronous task");
    }
});
executorService.shutdown(); // please remember to call and only call .shutdown() when task is finished



ExecutorService executorService1 = new Executors.newFixedThreadPool(10);
ExecutorService executorService2 = new Executors.newSingleThreadExecutor();
ExecutorService executorService3 = new Executors.newScheduledThreadPool(10);
ExecutorService executorService4 = new Executors.newCachedThreadPool();



Future future = executorService.submit(new Runnable() {
    public void run() {
        System.out.println("Asynchronous task");
    }
});
future.get(); // return null if the task has finished correctly.



Future future = executorService.submit(new Callable() {
    public Object call() throws Exception {
        System.out.println("Asynchronous task");
        return "Callable Result";
    }
});
System.out.println("future.get() = " + future.get());
