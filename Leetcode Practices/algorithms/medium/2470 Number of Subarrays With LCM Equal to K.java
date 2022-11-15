/**
Given an integer array nums and an integer k, return the number of subarrays of nums where the least common multiple of the subarray's elements is k.

A subarray is a contiguous non-empty sequence of elements within an array.

The least common multiple of an array is the smallest positive integer that is divisible by all the array elements.

 

Example 1:

Input: nums = [3,6,2,7,1], k = 6
Output: 4
Explanation: The subarrays of nums where 6 is the least common multiple of all the subarray's elements are:
- [3,6,2,7,1]
- [3,6,2,7,1]
- [3,6,2,7,1]
- [3,6,2,7,1]
Example 2:

Input: nums = [3], k = 2
Output: 0
Explanation: There are no subarrays of nums where 2 is the least common multiple of all the subarray's elements.
 

Constraints:

1 <= nums.length <= 1000
1 <= nums[i], k <= 1000
 */



// My Solution:
class Solution {
    public int subarrayLCM(int[] nums, int k) {
        // dp + 数学
        // O(N^2)
        int res = 0, n = nums.length;
        int[][] dp = new int[n][n]; // dp[i][j] -> lcm[i..j]
        for (int i=0; i<n; i++) {
            for (int j=i; j<n; j++) {
                if (i == j) dp[i][j] = nums[j];
                else dp[i][j] = lcm(dp[i][j-1], nums[j]);
                
                if (dp[i][j] == k) res++;
            }
        }

        return res;
    }
    
    public int gcd(int a, int b) {
        if (a == 0) return b;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}
