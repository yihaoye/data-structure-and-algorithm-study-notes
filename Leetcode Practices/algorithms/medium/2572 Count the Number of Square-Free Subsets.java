/**
You are given a positive integer 0-indexed array nums.

A subset of the array nums is square-free if the product of its elements is a square-free integer.

A square-free integer is an integer that is divisible by no square number other than 1.

Return the number of square-free non-empty subsets of the array nums. Since the answer may be too large, return it modulo 109 + 7.

A non-empty subset of nums is an array that can be obtained by deleting some (possibly none but not all) elements from nums. Two subsets are different if and only if the chosen indices to delete are different.

 

Example 1:

Input: nums = [3,4,4,5]
Output: 3
Explanation: There are 3 square-free subsets in this example:
- The subset consisting of the 0th element [3]. The product of its elements is 3, which is a square-free integer.
- The subset consisting of the 3rd element [5]. The product of its elements is 5, which is a square-free integer.
- The subset consisting of 0th and 3rd elements [3,5]. The product of its elements is 15, which is a square-free integer.
It can be proven that there are no more than 3 square-free subsets in the given array.
Example 2:

Input: nums = [1]
Output: 1
Explanation: There is 1 square-free subset in this example:
- The subset consisting of the 0th element [1]. The product of its elements is 1, which is a square-free integer.
It can be proven that there is no more than 1 square-free subset in the given array.
 

Constraints:

1 <= nums.length <= 1000
1 <= nums[i] <= 30
 */



// My Solution:
class Solution {
    public int squareFreeSubsets(int[] nums) {
        // Math + BFS + Object + memoization - nums[i] <= 30，因此可以把 nums[i] 求解其质约数
        int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        int[] valids = new int[]{2, 3, 5, 6, 7, 10, 11, 13, 14, 15, 17, 19, 21, 22, 23, 26, 29, 30}; // basiceSquareFrees - exclude 1 here since it need special calculate
        int oneCnt = 0;
        Map<Integer, Set<Integer>> map = new HashMap<>(); // <有效 num, 对应的质约数>
        Map<Integer, Integer> numCnt = new HashMap<>(); // <有效 num, 统计个数>
        for (int valid : valids) {
            map.put(valid, new HashSet<>());
            for (int prime : primes) {
                if (prime > valid) break;
                if (valid % prime == 0) map.get(valid).add(prime);
            }
            numCnt.put(valid, 0);
        }
        for (int num : nums) {
            if (num == 1) oneCnt++;
            if (!numCnt.containsKey(num)) continue;
            numCnt.put(num, numCnt.getOrDefault(num, 0) + 1);
        }
        for (int valid : valids) {
            if (numCnt.get(valid) == 0) numCnt.remove(valid);
        }
        
        long res = 0;
        // bfs
        Set<SubSet> queue = new LinkedHashSet<>();
        for (Map.Entry<Integer, Integer> entry : numCnt.entrySet()) {
            int num = entry.getKey(); int cnt = entry.getValue();
            Set<Integer> components = new HashSet<>(); components.add(num);
            queue.add(new SubSet(components, map.get(num), cnt));
        }
        for (int i=1; i<=numCnt.size(); i++) { // subset size
            int size = queue.size();
            while (size-- > 0) {
                SubSet lastSubSet = queue.iterator().next(); queue.remove(lastSubSet);
                res = (res + lastSubSet.combineCnt) % MOD;
                for (Map.Entry<Integer, Integer> entry : numCnt.entrySet()) {
                    int num = entry.getKey(); int cnt = entry.getValue();
                    if (Collections.disjoint(lastSubSet.primes, map.get(num))) {
                        Set<Integer> nextPrimes = new HashSet<>(map.get(num)); nextPrimes.addAll(lastSubSet.primes);
                        Set<Integer> nextComponents = new HashSet<>(lastSubSet.components); nextComponents.add(num);
                        queue.add(new SubSet(nextComponents, nextPrimes, lastSubSet.combineCnt * cnt % MOD));
                    }
                }
            }
        }

        // process cases with 1
        if (oneCnt > 0) {
            // oneCombine = C(n, 0) + C(n, 1) + C(n, 2) + ... + C(n, n) <--> n == oneCnt
            // C(n, k) = n! / ((n-k)! * k!)
            // res = oneCombine * res + oneCombine - 1 // oneCombine - 1 是因为多统计了一个不选任何 1 的情况
            long tmpRes = res; res = 0;
            for (int i=0; i<=oneCnt; i++) {
                long tmpCombination = combination(oneCnt, i);
                res = ((res + tmpRes * tmpCombination % MOD) % MOD + tmpCombination) % MOD;
            }
            res -= 1;
        }
        
        return (int) res;
    }

    long MOD = (long) 1e9 + 7;
    long[][] dp = new long[1001][1001];
    public long combination(int n, int k) { // Binomial Coefficient - C(n, k)
        if (dp[n][k] != 0) return dp[n][k]; // 记忆化搜索优化性能
        // base cases
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;
        // recursive
        long res = (combination(n - 1, k - 1) + combination(n - 1, k)) % MOD;
        dp[n][k] = res;
        return res;
    }

    class SubSet {
        Set<Integer> components;
        Set<Integer> primes;
        long combineCnt;

        SubSet(Set<Integer> components, Set<Integer> primes, long combineCnt) {
            this.components = components;
            this.primes = primes;
            this.combineCnt = combineCnt;
        }

        @Override
        public int hashCode() {
            return components.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            SubSet obj = (SubSet) o;
            return this.components.equals(obj.components);
        }
    }
}
