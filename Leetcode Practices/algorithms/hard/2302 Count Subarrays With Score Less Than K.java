/**
The score of an array is defined as the product of its sum and its length.

For example, the score of [1, 2, 3, 4, 5] is (1 + 2 + 3 + 4 + 5) * 5 = 75.
Given a positive integer array nums and an integer k, return the number of non-empty subarrays of nums whose score is strictly less than k.

A subarray is a contiguous sequence of elements within an array.

 

Example 1:

Input: nums = [2,1,4,3,5], k = 10
Output: 6
Explanation:
The 6 subarrays having scores less than 10 are:
- [2] with score 2 * 1 = 2.
- [1] with score 1 * 1 = 1.
- [4] with score 4 * 1 = 4.
- [3] with score 3 * 1 = 3. 
- [5] with score 5 * 1 = 5.
- [2,1] with score (2 + 1) * 2 = 6.
Note that subarrays such as [1,4] and [4,3,5] are not considered because their scores are 10 and 36 respectively, while we need scores strictly less than 10.
Example 2:

Input: nums = [1,1,1], k = 5
Output: 5
Explanation:
Every subarray except [1,1,1] has a score less than 5.
[1,1,1] has a score (1 + 1 + 1) * 3 = 9, which is greater than 5.
Thus, there are 5 subarrays having scores less than 5.
 

Constraints:

1 <= nums.length <= 10^5
1 <= nums[i] <= 10^5
1 <= k <= 10^15
 */



// My Solution:
class Solution {
    public long countSubarrays(int[] nums, long k) {
        // 排除法 + prefixSum + two pointers + math / combination 先计算认为 [0..n-1] 都是 valid 的，则其所有子数组都会 valid，所以总 valid 数为高斯函数计算 n-1，然后使用双指针从左到右遍历，若当前 [l..r] 不 valid，则 [l..r+1] .. [l..n-1] 也不会 valid，则 res -= (n-r), 然后 l 往左移一位，然后重新检查，如果 [l..r] valid 则 r -> r+1 直到触发不 valid
        int n = nums.length;
        long[] prefixSum = new long[n]; // 需要 long，否则最后一个 test case 会溢出
        for (int i=0; i<n; i++) {
            prefixSum[i] = (i > 0 ? prefixSum[i-1] : 0) + nums[i];
        }
        long res = cal(n);
        int l = 0, r = 0;
        while (l < n) {
            long score = (prefixSum[r] - prefixSum[l] + nums[l]) * (r - l + 1);
            if (score >= k) {
                res -= (n - r);
                l++;
                if (l > r) r++;
            } else {
                if (r < n-1) r++;
                else l++;
            }
        }

        return res;
    }

    private long cal(int m) {
        return (long) (m + 1) * m / 2;
    }
}
