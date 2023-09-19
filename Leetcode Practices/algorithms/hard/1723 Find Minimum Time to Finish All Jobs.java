/*
You are given an integer array jobs, where jobs[i] is the amount of time it takes to complete the ith job.

There are k workers that you can assign jobs to. Each job should be assigned to exactly one worker. The working time of a worker is the sum of the time it takes to complete all jobs assigned to them. Your goal is to devise an optimal assignment such that the maximum working time of any worker is minimized.

Return the minimum possible maximum working time of any assignment.

 

Example 1:

Input: jobs = [3,2,3], k = 3
Output: 3
Explanation: By assigning each person one job, the maximum time is 3.
Example 2:

Input: jobs = [1,2,4,7,8], k = 2
Output: 11
Explanation: Assign the jobs the following way:
Worker 1: 1, 2, 8 (working time = 1 + 2 + 8 = 11)
Worker 2: 4, 7 (working time = 4 + 7 = 11)
The maximum working time is 11.
 

Constraints:

1 <= k <= jobs.length <= 12
1 <= jobs[i] <= 10^7
*/



// Other's Solution:
// https://www.acoier.com/2021/05/08/1723.%20%E5%AE%8C%E6%88%90%E6%89%80%E6%9C%89%E5%B7%A5%E4%BD%9C%E7%9A%84%E6%9C%80%E7%9F%AD%E6%97%B6%E9%97%B4%EF%BC%88%E5%9B%B0%E9%9A%BE%EF%BC%89/
class Solution {
    int[] jobs;
    int[] works = new int[20];
    int n, k;
    int ans = Integer.MAX_VALUE;    
    Random random = new Random();
    // 最高温/最低温/变化速率（以什么速度进行退火，系数越低退火越快，迭代次数越少，落入「局部最优」（WA）的概率越高；系数越高 TLE 风险越大）
    double hi = 1e8, lo = 1e-4, fa = 0.90; 
    // 迭代次数，与变化速率同理
    int N = 400;

    // 计算当前 jobs 序列对应的最小「最大工作时间」是多少
    int calc() {
        Arrays.fill(works, 0);
        for (int i = 0; i < n; i++) {
            // [固定模式分配逻辑] : 每次都找最小的 worker 去分配
            int idx = 0, cur = works[idx];
            for (int j = 0; j < k; j++) {
                if (works[j] < cur) {
                    cur = works[j];
                    idx = j;
                }
            }
            works[idx] += jobs[i];
        }
        int cur = 0;
        for (int i = 0; i < k; i++) cur = Math.max(cur, works[i]);
        ans = Math.min(ans, cur);
        return cur;
    }

    void shuffle(int[] nums) {
        for (int i = n; i > 0; i--) {
            int idx = random.nextInt(i);
            swap(nums, idx, i - 1);
        }
    }

    void swap(int[] arr, int i, int j) {
        int c = arr[i];
        arr[i] = arr[j];
        arr[j] = c;
    }

    void sa() {
        shuffle(jobs);
        for (double t = hi; t > lo; t *= fa) {
            int a = random.nextInt(n), b = random.nextInt(n);
            int prev = calc(); // 退火前
            swap(jobs, a, b);
            int cur = calc(); // 退火后
            int diff = cur - prev;
            // 退火为负收益（温度上升），以一定概率回退现场
            if (Math.log(diff / t) > random.nextDouble()) swap(jobs, a, b);
        }
    }

    public int minimumTimeRequired(int[] _jobs, int _k) {
        jobs = _jobs;
        n = jobs.length;
        k = _k;
        while (N-- > 0) sa();
        return ans;
    }
}