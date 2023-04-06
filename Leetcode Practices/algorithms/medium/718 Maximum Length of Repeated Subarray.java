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



// Other's Solution:
class Solution {
    private static final int PRIME = 101;
    private static final int MOD = 1_000_000_007;
    
    public int findLength(int[] A, int[] B) {
        // https://leetcode.com/problems/maximum-length-of-repeated-subarray/solutions/1181612/java-clean-o-n-logn-rolling-hash-solution-with-comments/?orderBy=most_votes&topicTags=rolling-hash
        if (A.length < B.length) return findLength(B, A);
        
        int min = 1, max = Math.min(A.length, B.length) + 1;
        while (min < max) {
            int mid = min + (max - min) / 2;
            if (rollingHashCheck(mid, A, B)) min = mid + 1;
            else max = mid;
        }
        return min - 1; // min means min invalid length
    }
    
    // check every subarray with length mid
    private boolean rollingHashCheck(int mid, int[] A, int[] B) {
        Set<Integer> set = new HashSet<>();
        long power = 1;
        for (int i = 0; i < mid-1; i++) {
            power = (power * PRIME) % MOD;
        }
        
        // 1). Adding hash values of subarrays of B into set
        long hash = 0;
        for (int j = 0; j < mid; j++) {
            hash = (hash * PRIME % MOD + B[j]) % MOD;
        }
        set.add((int) hash);
        
        for (int i = 0, j = mid; j < B.length; i++, j++) {
            hash = (hash + MOD - B[i] * power % MOD) % MOD;
            hash = (hash * PRIME + B[j]) % MOD;
            set.add((int) hash);
        }
        
        // 2.) Check the hash values of subarrays of A
        hash = 0;
        for (int j = 0; j < mid; j++) {
            hash = (hash * PRIME % MOD + A[j]) % MOD;
        }
        if (set.contains((int) hash)) return true;
        
        for (int i = 0, j = mid; j < A.length; i++, j++) {
            hash = (hash + MOD - A[i] * power % MOD) % MOD;
            hash = (hash * PRIME + A[j]) % MOD;
            if (set.contains((int) hash)) return true;
        }
        
        return false;
    }
}
