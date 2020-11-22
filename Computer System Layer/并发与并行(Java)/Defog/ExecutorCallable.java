public static void main(String[] args) {

    ExecutorService service = new Executors.newFixedThreadPool(10);

    Future<Integer> future = service.submit(new Task());
    /*
    List<Future> futures = new ArrayList<>();
    for (int i=0; i<100; i++) {
        Future<Integer> future = service.submit(new Task());
        futures.add(future);
    }
    */

    // perform some unrelated operations since last thread sleep
    // ...

    try {
        Integer result = future.get(); // blocking the main thread until task thread finished aka sleep end here
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
    /*
    for (int i=0; i<100; i++) {
        Future<Integer> future = futures.get(i);
        try {
            Integer result = future.get();
            System.out.println("Result of future " + i + " = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    */
    // 上述 for 循环要注意的是，有可能后面的 i 的线程提早完成、Future提早获取数据，
    // 但若当前的 i 的线程若未完成、Future未获取数据的话，则整个 main 线程会阻塞在当前未获取数据的 Future 上。
    // 一个缓解方法可以用下面的 TimeoutException 方案
    /*
    Future<Integer> future = futures.get(i);
    try {
        Integer result = future.get(1, TimeUnit.SECONDS);
        System.out.println("Result of future " + i + " = " + result);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } catch (TimeoutException e) {
        System.out.println("Couldn't complete task before timeout");
    }
    */
    // 或强制结束 Future 阻塞
    /*
    future.cancel(false); // 参数为 false，意味着不打断 running，即若 task 未开始执行则不再执行 task，若已开始则不打断不 cancel 了
    future.isCancelled();
    future.isDone(); // return ture if task is completed (successfully or otherwise)
    */

    System.out.println("Thread Name: " + Thread.currentThread().getName());
}

static class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(3000);
        return new Random().nextInt();
    }
}
