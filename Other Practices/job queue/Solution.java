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

    public Job(String id) {
        this(id, new ArrayList<>());
    }

    public Job(String id, List<Job> subJobs) {
        this.id = id;
        this.subJobs = subJobs;
    }

    public void run() { 
        // ...
    }
}

class JobQueue {
    private Queue<Job> queue = new LinkedList<>();

    public void enqueue(Job job) {
        queue.offer(job);
    }

    public void runJob(Job job, int maxRetry) {
         // 考虑到子任务可能有依赖关系，需要拓扑排序，并且建议由单个线程执行同一根任务下的所有子任务而不是把他们也放进 queue 里
         // 因为 runJobsAsync 总是可能乱序执行（如果不太严格的话也不是不行，可以在 runJob 里 catch 依赖错误把当前任务放回 queue 里，注意 queue 需是 BlockingQueue）
        List<Job> subJobs = topoSort(job);

        int attempts = maxRetry;
        for (Job subJob : subJobs) {
            while (attempts-- > 0) {
                try {
                    subJob.run();
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (attempts == 0) {
                System.out.println("Job failed: " + job.id); // or handleFailure(job);
                return;
            }

            attempts = maxRetry;
        }
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
