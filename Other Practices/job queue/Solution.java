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
    public volatile boolean status;

    public Job(String id) {
        this(id, new ArrayList<>());
    }

    public Job(String id, List<Job> subJobs) {
        this.id = id;
        this.subJobs = subJobs;
        this.status = false;
    }

    public boolean checkDependsStatus() { // check if dependancies status are all done/true
        // ...
    }

    public void run() { 
        while (!checkDependsStatus()) {
            // wait
            // 在等待时可能会消耗过多的系统资源。可以考虑使用更适合等待的机制，比如 wait() 和 notify()，这样可以更有效地管理线程等待状态。
        }
        // ...
        this.status = true;
    }
}

class JobQueue {
    private Queue<Job> queue = new LinkedList<>();

    public void enqueue(Job job) {
        for (Job subJob : job.subJobs) enqueue(subJob);
        queue.offer(job);
    }

    public void runJob(Job job, int maxRetry) {
        while (maxRetry-- > 0) {
            try {
                job.run();
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Job failed: " + job.id); // or handleFailure(job);
    }

    public void runJobs() {
        while (!queue.isEmpty()) {
            Job job = queue.poll();
            runJob(job, 5);
        }
    }

    public void runJobsAsync() {
        while (!queue.isEmpty()) {
            Job job = queue.poll();
            CompletableFuture.runAsync(() -> runJob(job, 5));
        }
    }
}
