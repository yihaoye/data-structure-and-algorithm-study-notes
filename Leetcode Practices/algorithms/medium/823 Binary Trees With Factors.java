/**
Given an array of unique integers, arr, where each integer arr[i] is strictly greater than 1.

We make a binary tree using these integers, and each number may be used for any number of times. Each non-leaf node's value should be equal to the product of the values of its children.

Return the number of binary trees we can make. The answer may be too large so return the answer modulo 109 + 7.

 

Example 1:

Input: arr = [2,4]
Output: 3
Explanation: We can make these trees: [2], [4], [4, 2, 2]
Example 2:

Input: arr = [2,4,5,10]
Output: 7
Explanation: We can make these trees: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2].
 

Constraints:

1 <= arr.length <= 1000
2 <= arr[i] <= 10^9
All the values of arr are unique.
 */



// My Solution:
class Solution {
    public long MOD = (long) 1e9 + 7;
    
    public int numFactoredBinaryTrees(int[] arr) {
        // TreeMap<i, dp[i]> + DP - dp[i] 意味着以 i 为根节点的二叉树的组合数量，先从较小的 i 进行计算：TreeMap 从小到大遍历直到 i，假设当前遍历的是 k 则检查是否有 i%k == 0 且 TreeMap 有键 i/k，若均符合则 dp[i] += dp[k] * dp[i/k]，（dp[i] 初始值为 1）。最后遍历整个 TreeMap 把所有 dp[i] 加起来即 res
        TreeMap<Integer, Long> tMap = new TreeMap<>();
        long res = 0;
        for (int num : arr) {
            tMap.put(num, 1L);
        }
        for (Map.Entry<Integer, Long> ei : tMap.entrySet()) {
            Integer i = ei.getKey();
            Long dpi = ei.getValue();
            for (Map.Entry<Integer, Long> ek : tMap.entrySet()) {
                Integer k = ek.getKey();
                if (k >= i) break;
                Long dpk = ek.getValue();
                if (i % k == 0 && tMap.containsKey(i/k)) dpi = (dpi + (dpk * tMap.get(i/k)) % MOD) % MOD;
            }
            tMap.put(i, dpi);
            res = (res + dpi) % MOD;
        }
        return (int) res;
    }
}
