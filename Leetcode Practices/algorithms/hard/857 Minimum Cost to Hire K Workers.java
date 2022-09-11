/**
There are n workers. You are given two integer arrays quality and wage where quality[i] is the quality of the ith worker and wage[i] is the minimum wage expectation for the ith worker.

We want to hire exactly k workers to form a paid group. To hire a group of k workers, we must pay them according to the following rules:

Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid group.
Every worker in the paid group must be paid at least their minimum wage expectation.
Given the integer k, return the least amount of money needed to form a paid group satisfying the above conditions. Answers within 10-5 of the actual answer will be accepted.

 

Example 1:

Input: quality = [10,20,5], wage = [70,50,30], k = 2
Output: 105.00000
Explanation: We pay 70 to 0th worker and 35 to 2nd worker.
Example 2:

Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], k = 3
Output: 30.66667
Explanation: We pay 4 to 0th worker, 13.33333 to 2nd and 3rd workers separately.
 

Constraints:

n == quality.length == wage.length
1 <= k <= n <= 10^4
1 <= quality[i], wage[i] <= 10^4
 */



// Other's Solution:
class Solution {
    public double mincostToHireWorkers(int[] q, int[] w, int K) {
        // https://leetcode.com/problems/minimum-cost-to-hire-k-workers/discuss/141768/Detailed-explanation-O(NlogN)
        double[][] workers = new double[q.length][2];
        for (int i = 0; i < q.length; ++i)
            workers[i] = new double[]{(double)(w[i]) / q[i], (double)q[i]};
        Arrays.sort(workers, (a, b) -> Double.compare(a[0], b[0]));
        double res = Double.MAX_VALUE, qsum = 0;
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (double[] worker : workers) {
            if (pq.size() == K) qsum += pq.poll();
            qsum += worker[1];
            pq.add(-worker[1]);
            if (pq.size() == K) res = Math.min(res, qsum * worker[0]);
        }
        return res;
    }
}



// My Solution (Test Case 倒数第二个精度有些偏差):
class Solution {
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        // 计算每个工人的性价比，然后 k 这一组内的价格是按照整个组内性价比最低的工人来决定的
        // 求解是要最低成本而非工作量，所以成本 = k 这一组内所有工作量 * 最低性价比的单价（即单价 ratio 最大）
        // 先对性价比数组（[单价, 索引]）进行按照单价从大到小排序
        // 对 [quality, 索引] 数组进行按照 quality 从小到大的排序
        // 然后再使用贪心算法找出最大单价及其索引，然后在 quality 数组里按序（索引不能与之前找到的最大单价包括现在最大单价的索引重复）找出 k-1 个进行计算（这里再使用一个优先队列存储但是按照其单价从大到小排序）
        double res = Double.MAX_VALUE;
        int n = quality.length;
        PriorityQueue<Worker> ratioPQ = new PriorityQueue<>((a, b) -> Double.compare(b.ratio, a.ratio));
        PriorityQueue<Worker> qualityPQ = new PriorityQueue<>((a, b) -> a.quality - b.quality);
        for (int i=0; i<n; i++) {            
            Worker worker = new Worker(quality[i], wage[i], (double) wage[i] / (double) quality[i]);
            ratioPQ.offer(worker);
            qualityPQ.offer(worker);
        }

        Set<Worker> used = new HashSet<>();
        int qSum = 0;
        PriorityQueue<Worker> curGroup = new PriorityQueue<>((a, b) -> Double.compare(b.ratio, a.ratio));
        while (!ratioPQ.isEmpty()) {
            Worker hrWorker = ratioPQ.poll(); // highest ratio worker
            used.add(hrWorker);
            while (!curGroup.isEmpty() && (curGroup.peek().ratio >= hrWorker.ratio || curGroup.peek().equals(hrWorker))) {
                qSum -= curGroup.poll().quality;
            }
            
            curGroup.offer(hrWorker);
            qSum += hrWorker.quality;
            while (!qualityPQ.isEmpty() && curGroup.size() < k) {
                Worker lrWorker = qualityPQ.poll();
                if (lrWorker.ratio <= hrWorker.ratio && !used.contains(lrWorker)) {
                    curGroup.offer(lrWorker);
                    qSum += lrWorker.quality;
                }
            }
            if (qualityPQ.isEmpty() && curGroup.size() < k) return res;
            
            res = Math.min(res, hrWorker.ratio * (double) qSum);
        }
        return res;
    }
    
    class Worker {
        int quality;
        int wage;
        double ratio;
        
        public Worker(int quality, int wage, double ratio) {
            this.quality = quality;
            this.wage = wage;
            this.ratio = ratio;
        }
    }
}
