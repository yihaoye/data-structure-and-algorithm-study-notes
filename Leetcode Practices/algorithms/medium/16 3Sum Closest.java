/*
Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.

Return the sum of the three integers.

You may assume that each input would have exactly one solution.

 

Example 1:

Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
Example 2:

Input: nums = [0,0,0], target = 1
Output: 0
 

Constraints:

3 <= nums.length <= 1000
-1000 <= nums[i] <= 1000
-104 <= target <= 104
*/



// Other's Solution:
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        /*
            看题解，排序+双指针，https://leetcode-cn.com/problems/3sum-closest/solution/hua-jie-suan-fa-16-zui-jie-jin-de-san-shu-zhi-he-b/
            时间复杂度 O(N^2)，空间复杂度 O(N) - Java Arrays.sort
        */
        Arrays.sort(nums);
        int res = nums[0] + nums[1] + nums[2];
        for(int i=0; i<nums.length-2; i++) {
            int start = i + 1, end = nums.length - 1;
            while(start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (Math.abs(target - sum) < Math.abs(target - res)) res = sum;
                
                if (sum > target) end--; // 因为数组有序，如果 sum > target 则 end--，如果 sum < target 则 start++，如果 sum == target 则说明距离为 0 直接返回结果
                else if (sum < target) start++;
                else return res; // sum == target
            }
        }
        return res;
    }
    /*
        这是为什么呢？对 a+b+c ≥ target 的情况进行一个详细的分析：
            如果 a+b+c ≥ target，并且知道 Pb 到 Pc 这个范围内的所有数是按照升序排序的，那么如果 Pc 不变而 Pb 向右移动，那么 a+b+c 的值就会不断地增加，显然就不会成为最接近 target 的值了。因此，可以知道在固定了 Pc 的情况下，此时的 Pb 就可以得到一个最接近 target 的值，那么以后就不用再考虑 Pc 了，就可以将 Pc​ 向左移动一个位置。
        同样地，在 a+b+c < target 时：
            如果 a+b+c < target，并且知道 Pb 到 Pc​ 这个范围内的所有数是按照升序排序的，那么如果 Pb​ 不变而 Pc​ 向左移动，那么 a+b+c 的值就会不断地减小，显然就不会成为最接近 target 的值了。因此，可以知道在固定了 Pb 的情况下，此时的 Pc​ 就可以得到一个最接近 target 的值，那么以后就不用再考虑 Pb​ 了，就可以将 Pb​ 向右移动一个位置。

        链接：https://leetcode-cn.com/problems/3sum-closest/solution/zui-jie-jin-de-san-shu-zhi-he-by-leetcode-solution/
    */
}
