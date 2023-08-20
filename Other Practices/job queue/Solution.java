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
    }

    public void run() { 
        // pure process ...
    }
}

class JobQueue {
    private BlockingQueue<Job> queue;
    private BlockingQueue<Job> results;;
    private static final int CPU_CORES_NUM;

    public JobQueue() {
        this.queue = new LinkedBlockingQueue<>();
        this.results = new LinkedBlockingQueue<>();
        this.CPU_CORES_NUM = Runtime.getRuntime().availableProcessors();
    }

    public void enqueue(Job job) {
        for (Job subJob : job.subJobs) enqueue(subJob); // 除了递归，也可以考虑采用 BFS，因为题目要求子任务也放进队列且实现异步执行，所以在这里就不使用拓扑排序了，因为异步会乱序所以排序无意义
        queue.offer(job);
    }

    public void runNextJob(int maxRetry) {
        Job job = queue.poll(100, TimeUnit.MILLISECONDS); // 从队列中取出任务，如果队列为空则等待 0.1s，不用 take 因为可能 queue 剩 1 个元素时刚好多个线程同时 runNextJob
        if (job == null) return;

        int dependsStatus = job.dependsStatus();
        if (dependsStatus == 0) {
            queue.offer(job); // 依赖任务未全部完成，放回队列
            return;
        }
        while (dependsStatus == 1 && maxRetry-- > 0) {
            try {
                job.run();
                job.status = 1;
                results.offer(job); // 成功和失败结果可以放进不同队列，这里简化为放进同一个队列
                return;
            } catch (Exception e) { // 不是因为依赖任务无法执行，而是其他临时故障，比如本任务网络错误，可以重试解决的那种
                System.out.println(e.getMessage());
            }
        }
        job.status = -1; // 依赖任务无法完成或重试次数用完，都标记当前 job 为失败
        results.offer(job); // 或者添加 handleFailure(job);
    }

    public void runJobs() {
        int maxRetry = 3; // 这里写死，实际上可以根据实际情况调整
        while (!queue.isEmpty()) {
            runNextJob(maxRetry);
        }
    }

    public void runJobsAsync() {
        int jobCount = queue.size();
        int maxRetry = 5;
        ExecutorService executor = Executors.newFixedThreadPool(CPU_CORES_NUM);
        while (results.size() < jobCount) { // 这里不用 !queue.isEmpty()，因为可能会出现当前多线程处理余下任务但因依赖项未完成需要往 queue 放回，而之前刚好 while 判断为 true，导致提前退出循环
            if (queue.isEmpty()) continue; // 避免起多余线程导致性能下降，但这里也无法保证不为空时会不会刚好多个线程抢少数 job，所以 runNextJob 里还会有 poll 限时双重保证
            executor.execute(() -> runNextJob(maxRetry)); // 比 CompletableFuture.runAsync(() -> runNextJob(maxRetry)) 更好，因为 CompletableFuture 在当调用/任务数比 CPU 核数远多的情况下会因线程切换、竞争导致性能下降，而线程池会阻塞任务队列，保证线程数不会超过 CPU 核数
            // 注意：Java 没有像 JavaScript 中的事件循环（Event Loop）机制，Java 的异步任务通常都是通过线程实现的
        }
        executor.shutdown();
    }
}
