/**
Given an integer array nums, return true if there exists a triple of indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exists, return false.

 

Example 1:

Input: nums = [1,2,3,4,5]
Output: true
Explanation: Any triplet where i < j < k is valid.
Example 2:

Input: nums = [5,4,3,2,1]
Output: false
Explanation: No triplet exists.
Example 3:

Input: nums = [2,1,5,0,4,6]
Output: true
Explanation: The triplet (3, 4, 5) is valid because nums[3] == 0 < nums[4] == 4 < nums[5] == 6.
 

Constraints:

1 <= nums.length <= 5 * 10^5
-2^31 <= nums[i] <= 2^31 - 1
 

Follow up: Could you implement a solution that runs in O(n) time complexity and O(1) space complexity?
 */



// Other's Solution:
class Solution {
    public boolean increasingTriplet(int[] nums) {
        // 哨兵变量 - https://leetcode.com/problems/increasing-triplet-subsequence/discuss/79004/Concise-Java-solution-with-comments.
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        // Time: O(N), Space: O(1)
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) small = n; // update small if n is smaller than both（这里需要注意到，big 的索引比新的 small 小，但是无需担心，因为 big 一旦不是 MAX_VALUE，则下次 n > big 时说明 last_small < cur_big < n 成立，若下次轮到 big 更新则 new_big 的索引必大于 cur_small 的索引。另外需注意这里都是 <= 因为题目要求成立条件为严格的 <）
            else if (n <= big) big = n; // update big only if greater than small but smaller than big
            else return true; // return if you find a number bigger than both
        }
        return false;
    }
}
