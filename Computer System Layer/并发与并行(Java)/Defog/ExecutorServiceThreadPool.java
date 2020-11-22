/*
核心线程数 2
最大线程数 4
空闲线程保活时间 60s
使用无界队列 LinkedBlockingQueue（注意一般情况下要配置一下队列大小，设置成有界队列，否则 JVM 内存会被撑爆）
*/
ExecutorService executor =  new ThreadPoolExecutor(
    2, 
    4, 
    60L, 
    TimeUnit.SECONDS, 
    new LinkedBlockingQueue<Runnable>()
);



// 配合参考：../ExecutorService%20and%20Thread%20Pool.png 和 https://www.youtube.com/watch?v=sIkG0X4fqs4
public void main(String[] args) {

    // create the pool
    ExecutorService service = new Executors.newFixedThreadPool(10);

    // submit the task for execution
    for (int i=0; i<100; i++) {
        service.execute(new Task());
    }
    System.out.println("Thread name: " + Thread.currentThread().getName());
}

static class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread name: " + Thread.currentThread().getName());
    }
}



public void main(String[] args) {

    // get count of available cores
    int coreCount = Runtime.getRuntime().availableProcessors();
    ExecutorService service = new Executors.newFixedThreadPool(coreCount);

    // submit the task for execution
    for (int i=0; i<100; i++) {
        service.execute(new CpuIntensiveTask());
    }
}

static class CpuIntensiveTask implements Runnable {
    @Override
    public void run() {
        // some cpu intensive operations
    }
}



public void main(String[] args) {

    // much higher count for IO tasks
    ExecutorService service = new Executors.newFixedThreadPool(100);

    // submit the task for execution
    for (int i=0; i<100; i++) {
        service.execute(new IOIntensiveTask());
    }
}

static class IOIntensiveTask implements Runnable {
    @Override
    public void run() {
        // some io operations which will cause thread to block/wait
    }
}



// 配合参考：../ExecutorService%20and%20Thread%20Pool%202.png
public void main(String[] args) {

    // for lots of short lived tasks
    ExecutorService service = new Executors.newCachedThreadPool();

    // submit the task for execution
    for (int i=0; i<100; i++) {
        service.execute(new Task());
    }
}

static class Task implements Runnable {
    @Override
    public void run() {
        // short lived task
    }
}



// e.g. security check, logging check etc every 10 seconds.
// 配合参考：../ExecutorService%20and%20Thread%20Pool%203.png
public void main(String[] args) {

    // for scheduling for tasks
    ExecutorService service = new Executors.newScheduledThreadPool(10);

    // task to run after 10 sec delay
    service.schedule(new Task(), 10, SECONDS);

    // task to run repeatedly every 10 sec
    service.scheduleAtFixedRate(new Task(), 15, 10, SECONDS);

    // task to run repeatedly 10 sec after previous task completes
    service.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);
}

static class Task implements Runnable {
    @Override
    public void run() {
        // task that need to run
        // based on schedule
    }
}
