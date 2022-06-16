/**
Given an array of integers nums, find the maximum length of a subarray where the product of all its elements is positive.

A subarray of an array is a consecutive sequence of zero or more values taken out of that array.

Return the maximum length of a subarray with positive product.

 

Example 1:

Input: nums = [1,-2,-3,4]
Output: 4
Explanation: The array nums already has a positive product of 24.
Example 2:

Input: nums = [0,1,-2,-3,-4]
Output: 3
Explanation: The longest subarray with positive product is [1,-2,-3] which has a product of 6.
Notice that we cannot include 0 in the subarray since that'll make the product 0 which is not positive.
Example 3:

Input: nums = [-1,-2,-3,0,1]
Output: 2
Explanation: The longest subarray with positive product is [-1,-2] or [-2,-3].
 

Constraints:

1 <= nums.length <= 105
-109 <= nums[i] <= 109

 */



// Other's Solution:
class Solution {
    public int getMaxLen(int[] nums) {
        // DP - https://www.youtube.com/watch?v=gwZm6mIYDfk&list=PLLuMmzMTgVK6krji67w8tEAAud71nQQFe&index=4
        // Time: O(N), Space: O(1)
        int maxNegLen = 0, maxPosLen = 0, res = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] > 0) {
                maxPosLen = maxPosLen + 1;
                maxNegLen = maxNegLen > 0 ? maxNegLen + 1 : 0;
            } else if (nums[i] < 0) {
                int tmpMaxPosLen = maxNegLen > 0 ? maxNegLen + 1 : 0;
                int tmpMaxNegLen = maxPosLen + 1;
                maxPosLen = tmpMaxPosLen;
                maxNegLen = tmpMaxNegLen;
            } else {
                maxNegLen = 0;
                maxPosLen = 0;
            }
            res = Math.max(res, maxPosLen);
        }
        return res;
    }
}
