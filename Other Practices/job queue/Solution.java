/*
Job data structure:
    - run method
Job queue data structure:
    - euqueue(job) add the job to queue
    - runJobs run all the jobs in queue

Follow up: can you run jobs async?
What if job fails, how to handle that?
What if job has sub-job needs to add to queue?
*/

import java.util.*;
import java.util.concurrent.*;

class Job implements Runnable {
    public String id;
    public List<Job> subJobs;
    public volatile int status; // 0: not started, 1: succeed, -1: failed (完全无法执行) // 不能简单的 true/false，因为可能如果子任务抛错根本不能完成则可能因为 dependsStatus 永远返回 false 造成死循环重试

    public Job(String id) {
        this(id, new ArrayList<>());
    }

    public Job(String id, List<Job> subJobs) {
        this.id = id;
        this.subJobs = subJobs;
        this.status = 0;
    }

    public int dependsStatus() { // check if dependancy jobs status are all done/succeed
        // return 0 if not all done, 1 if all succeed, -1 if any failed（可以递归实现）
        // 而且如果是 1 或 -1，可以直接用一个变量缓存了，因为以后都不会变化了
    }

    public void run() { 
        // process ...
    }
}

class JobQueue {
    private BlockingQueue<Job> queue;
    private static final int CPU_CORES_NUM;

    public JobQueue() {
        this.queue = new LinkedBlockingQueue<>();
        this.CPU_CORES_NUM = Runtime.getRuntime().availableProcessors();
    }

    public void enqueue(Job job) {
        for (Job subJob : job.subJobs) enqueue(subJob); // 除了递归，也可以考虑采用 BFS，因为题目要求子任务也放进队列且实现异步执行，所以在这里就不使用拓扑排序了，因为异步会乱序所以排序无意义
        queue.offer(job);
    }

    public void runJob(Job job, int maxRetry) { // maxRetry 可以根据实际情况调整，另外应保证 job.dependsStatus() 此时只能为 1 或 -1
        while (job.dependsStatus() == 1 && maxRetry-- > 0) {
            try {
                job.run();
                job.status = 1;
                return;
            } catch (Exception e) { // 非依赖任务无法执行，乃其他临时故障，比如本任务网络错误，可以重试
                System.out.println(e.getMessage());
            }
        }
        job.status = -1; // 依赖任务无法完成或重试次数用完，都标记为失败
        handleFailure(job);
    }

    public void runJobs() {
        while (!queue.isEmpty()) {
            Job job = queue.poll();
            if (job.dependsStatus() == 0) {
                queue.offer(job); // 依赖任务未全部完成，放回队列
                continue;
            }
            runJob(job, 3);
        }
    }

    public void runJobsAsync() {
        ExecutorService executor = Executors.newFixedThreadPool(CPU_CORES_NUM);
        while (!queue.isEmpty()) {
            Job job = queue.poll();
            if (job.dependsStatus() == 0) {
                queue.offer(job); // 依赖任务未全部完成，放回队列（必须在这里放回队列而不是 executor.execute(() -> runJob(job, 5)) 里，否则可能在放回之前就在下一个 while 判定 queue 为空跳出循环）
                continue;
            }
            executor.execute(() -> runJob(job, 5)); // 比 CompletableFuture.runAsync(() -> runJob(job, 5)) 更好，因为 CompletableFuture 在当调用/任务数比 CPU 核数远多的情况下会因线程切换、竞争导致性能下降，而线程池会阻塞任务队列，保证线程数不会超过 CPU 核数
            // 注意：Java 没有像 JavaScript 中的事件循环（Event Loop）机制，Java 的异步任务通常都是通过线程实现的
        }
        executor.shutdown();
    }

    public void handleFailure(Job job) {
        // 比如放进另一个全局队列 ...
    }
}
