/**
Given two integer arrays nums1 and nums2, return the maximum length of a subarray that appears in both arrays.

 

Example 1:

Input: nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
Output: 3
Explanation: The repeated subarray with maximum length is [3,2,1].
Example 2:

Input: nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
Output: 5
 

Constraints:

1 <= nums1.length, nums2.length <= 1000
0 <= nums1[i], nums2[i] <= 100
 */



// Other's Solution:
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        /*
            DP - https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/zui-chang-zhong-fu-zi-shu-zu-by-leetcode-solution/
            Time: O(N*M), Space: O(N*M)
        */
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n+1][m+1];
        int res = 0;
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=m; j++) {
                dp[i][j] = (nums1[i-1] == nums2[j-1]) ? dp[i-1][j-1] + 1 : 0;
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }
}
