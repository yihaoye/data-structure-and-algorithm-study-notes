/**
You have n jobs and m workers. You are given three arrays: difficulty, profit, and worker where:

difficulty[i] and profit[i] are the difficulty and the profit of the ith job, and
worker[j] is the ability of jth worker (i.e., the jth worker can only complete a job with difficulty at most worker[j]).
Every worker can be assigned at most one job, but one job can be completed multiple times.

For example, if three workers attempt the same job that pays $1, then the total profit will be $3. If a worker cannot complete any job, their profit is $0.
Return the maximum profit we can achieve after assigning the workers to the jobs.

 

Example 1:

Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
Output: 100
Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get a profit of [20,20,30,30] separately.
Example 2:

Input: difficulty = [85,47,57], profit = [24,66,99], worker = [40,25,25]
Output: 0
 

Constraints:

n == difficulty.length
n == profit.length
m == worker.length
1 <= n, m <= 104
1 <= difficulty[i], profit[i], worker[i] <= 105
 */



// My Solution:
class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        // greedy + TreeMap
        // Time: O(Max(M, C)*C*logC), Space: O(C). C 为 difficulty unique 个数
        int n = difficulty.length, m = worker.length;

        /* build a TreeMap <difficulty, max profit before difficulty> */
        HashMap<Integer, Integer> hMap = new HashMap<>(); // use HashMap instead of TreeMap directly so that improve insert performance a bit
        TreeMap<Integer, Integer> tMap = new TreeMap<>();
        for (int i=0; i<n; i++) {
            if (hMap.getOrDefault(difficulty[i], Integer.MIN_VALUE) < profit[i]) {
                hMap.put(difficulty[i], profit[i]);
            }
        }
        tMap.putAll(hMap);
        int lastMax = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : tMap.entrySet()) {
            if (entry.getValue() < lastMax) tMap.put(entry.getKey(), lastMax);
            else lastMax = entry.getValue();
        }
        /**/

        int res = 0;
        for (int i=0; i<m; i++) {
            Integer key = tMap.floorKey(worker[i]);
            if (key == null) continue;
            res += tMap.get(key);
        }

        return res;
    }
}
