/*
Given an integer array nums and an integer k, find three non-overlapping subarrays of length k with maximum sum and return them.

Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are multiple answers, return the lexicographically smallest one.

 

Example 1:

Input: nums = [1,2,1,2,6,7,5,1], k = 2
Output: [0,3,5]
Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
Example 2:

Input: nums = [1,2,1,2,1,2,1,2,1], k = 2
Output: [0,2,4]
 

Constraints:

1 <= nums.length <= 2 * 104
1 <= nums[i] < 216
1 <= k <= floor(nums.length / 3)
*/



// Other's Solution:
class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        /*
            DP+前缀和 - https://leetcode-cn.com/problems/maximum-sum-of-3-non-overlapping-subarrays/solution/gong-shui-san-xie-jie-he-qian-zhui-he-de-ancx/
            时间复杂度 O(N)，空间复杂度 O(N)
        */
        int n = nums.length;
        
        // 反转数组，在后面配合正序 DP 获得字典序最小（因为原数组正序回溯出字典序最大，该题要字典序最小）
        reverse(nums);
        
        // 前缀和
        long[] sum = new long[n + 1];
        for (int i = 1; i <= n; i++) sum[i] = sum[i - 1] + nums[i - 1];
        
        // 计算最优值，正序 DP
        long[][] f = new long[n + 10][4];
        for (int i = k; i <= n; i++) {
            for (int j = 1; j < 4; j++) {
                f[i][j] = Math.max(f[i - 1][j], f[i - k][j - 1] + sum[i] - sum[i - k]);
            }
        }
        
        // 从最优值和最终状态开始回溯 (背包回溯方案)
        int[] ans = new int[3];
        int i = n, j = 3, idx = 0;
        // 在决定第几个枚举点? 
        while (j > 0) {
            // 最优值 f[i][j] 一定从两个状态转移过来 : f[i-1][j] 或者 sum[i-k...i] + f[i-k][j-1] 
            if (f[i-1][j] > f[i-k][j-1] + sum[i] - sum[i-k]) { // (1) 如果 nums[i] 无贡献，则一定有 f[i-1][j] 更大
                i--;
            } else { // (2) 如果 nums[i] 被选择，则一定有 sum[i-k...i] + f[i-k][j-1]更大。
                ans[idx++] = n - i; // 注意前面原数组被反序了，所以实际索引应该是 n 减去现索引
                i -= k; // 选取 nums[i] 后，只能继续从 nums[0...i-k] 继续模拟取后面的点。
                j--;
            }
        }
        return ans;
    }
    
    public void reverse(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int c = nums[l];
            nums[l++] = nums[r];
            nums[r--] = c;
        }
    }
}
