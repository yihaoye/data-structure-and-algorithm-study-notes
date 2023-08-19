/*
Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:

0 <= a, b, c, d < n
a, b, c, and d are distinct.
nums[a] + nums[b] + nums[c] + nums[d] == target
You may return the answer in any order.

 

Example 1:

Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
Example 2:

Input: nums = [2,2,2,2,2], target = 8
Output: [[2,2,2,2]]
 

Constraints:

1 <= nums.length <= 200
-10^9 <= nums[i] <= 10^9
-10^9 <= target <= 10^9
*/



// Other's Solution:
class Solution {
    int len = 0;
    
    public List<List<Integer>> fourSum(int[] nums, int target) {
        // kSum 模版（排序 + 双指针 + 递归） - https://leetcode.com/problems/4sum/discuss/8609/My-solution-generalized-for-kSums-in-JAVA
        // Time: O(N^(K-1))
        len = nums.length;
        Arrays.sort(nums);
        return kSum(nums, (long)target, 4, 0);
    }
    
    private List<List<Integer>> kSum(int[] nums, long target, int k, int index) { // 把 target 换成 long 以避免溢出，如果想更 smart 的解决方案需要应用对大数取模
        List<List<Integer>> res = new ArrayList<>();
        if (index >= len) return res;

        if (k == 2) {
            int l = index, r = len-1;
            while (l < r) {
                if (nums[l] + nums[r] == target) { // find a pair
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[l]); temp.add(nums[r]);
                    res.add(temp);
                    // skip duplication
                    while (l < r && nums[l] == nums[l+1]) l++;
                    while (l < r && nums[r-1] == nums[r]) r--;
                    l++; r--;
                } else if (nums[l] + nums[r] < target) {
                    l++; // move left bound
                } else {
                    r--; // move right bound
                }
            }
        } else {
            for (int i = index; i < len-k+1; i++) {
                // use current number to reduce ksum into k-1sum
                List<List<Integer>> temp = kSum(nums, target-nums[i], k-1, i+1);
                if (temp != null) {
                    for (List<Integer> t : temp) t.add(nums[i]); // add nums[i] to sub results
                    res.addAll(temp);
                }
                while (i < len-1 && nums[i] == nums[i+1]) i++; // skip duplicated numbers
            }
        }
        return res;
    }
}
