/**
Given an integer array nums of size n, return the minimum number of moves required to make all array elements equal.

In one move, you can increment or decrement an element of the array by 1.

Test cases are designed so that the answer will fit in a 32-bit integer.

 

Example 1:

Input: nums = [1,2,3]
Output: 2
Explanation:
Only two moves are needed (remember each move increments or decrements one element):
[1,2,3]  =>  [2,2,3]  =>  [2,2,2]
Example 2:

Input: nums = [1,10,2,9]
Output: 16
 

Constraints:

n == nums.length
1 <= nums.length <= 10^5
-10^9 <= nums[i] <= 10^9
 */



// My Solution:
class Solution {
    public int minMoves2(int[] nums) {
        // 中位数 - 参考 LC 2033
        Arrays.sort(nums);
        int j = nums[nums.length / 2];
        int sum = 0;
        for (int num : nums) {
            int l = Math.abs(j - num);
            sum += l;
        }
        return sum;
    }
}
