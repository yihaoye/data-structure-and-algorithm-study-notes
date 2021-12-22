/*
You are given an integer array nums. The range of a subarray of nums is the difference between the largest and smallest element in the subarray.

Return the sum of all subarray ranges of nums.

A subarray is a contiguous non-empty sequence of elements within an array.

 

Example 1:

Input: nums = [1,2,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0 
[2], range = 2 - 2 = 0
[3], range = 3 - 3 = 0
[1,2], range = 2 - 1 = 1
[2,3], range = 3 - 2 = 1
[1,2,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.
Example 2:

Input: nums = [1,3,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0
[3], range = 3 - 3 = 0
[3], range = 3 - 3 = 0
[1,3], range = 3 - 1 = 2
[3,3], range = 3 - 3 = 0
[1,3,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 2 + 0 + 2 = 4.
Example 3:

Input: nums = [4,-2,-3,4,1]
Output: 59
Explanation: The sum of all subarray ranges of nums is 59.
 

Constraints:

1 <= nums.length <= 1000
-109 <= nums[i] <= 109
 

Follow-up: Could you find a solution with O(n) time complexity?
*/



// Other's Solution:
class Solution {
    public long subArrayRanges(int[] nums) {
        /*
            看题解，单调栈，可以考虑每个元素作为最大值出现在了多少子数组中，以及作为最小值出现在了多少子数组中，然后计算各自的贡献，不同的是最大值是正贡献，最小值是负贡献。https://leetcode-cn.com/problems/sum-of-subarray-ranges/solution/cong-on2-dao-ondan-diao-zhan-ji-suan-mei-o1op/
            时间复杂度：O(n)，空间复杂度：O(n)
        */
        long res = 0;
        Deque<Integer> sMax = new LinkedList<>();
        Deque<Integer> sMin = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (!sMax.isEmpty() && nums[sMax.peek()] <= nums[i]) {
                int j = sMax.pop();
                int l = sMax.isEmpty() ? -1 : sMax.peek();
                res += 1L * (i - j) * (j - l) * nums[j];
            }
            sMax.push(i);

            while (!sMin.isEmpty() && nums[sMin.peek()] >= nums[i]) {
                int j = sMin.pop();
                int l = sMin.isEmpty() ? -1 : sMin.peek();
                res -= 1L * (i - j) * (j - l) * nums[j];
            }
            sMin.push(i);
        }

        int r = nums.length;
        while (!sMax.isEmpty()) {
            int j = sMax.pop();
            int l = sMax.isEmpty() ? -1 : sMax.peek();
            res += 1L * (r - j) * (j - l) * nums[j];
        }
        while (!sMin.isEmpty()) {
            int j = sMin.pop();
            int l = sMin.isEmpty() ? -1 : sMin.peek();
            res -= 1L * (r - j) * (j - l) * nums[j];
        }

        return res;
    }
}

