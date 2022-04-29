/*
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1].

You must write an algorithm with O(log n) runtime complexity.

 

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
Example 3:

Input: nums = [], target = 0
Output: [-1,-1]
 

Constraints:

0 <= nums.length <= 105
-109 <= nums[i] <= 109
nums is a non-decreasing array.
-109 <= target <= 109
*/



// My Solution:
class Solution {
    public int[] searchRange(int[] nums, int target) {
        /*
            二分搜索定位其中一个 target 值，然后再往两边延伸寻找边界
            时间复杂度 O(logN)，空间复杂度最坏 O(N)
        */
        if (nums.length == 0) return new int[]{-1, -1};
        
        int left = 0, right = nums.length-1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] > target) {
                right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                left = middle;
                right = middle;
            }
        }
        if (nums[left] != target) return new int[]{-1, -1};
        
        while (left > 0 && nums[left-1] == target) left--;
        while (right < nums.length-1 && nums[right+1] == target) right++;
        return new int[]{left, right};
    }
}



// Other's Solution:
class Solution {
    public int[] searchRange(int[] nums, int target) {
        /*
            二分搜索上下边界
            Time: O(logN)，Space: O(1)
        */
        int left = lowerBound(nums, target);
        if (left == nums.length || nums[left] != target) return new int[]{-1, -1};
        int right = upperBound(nums, target);
        return new int[]{left, right - 1};
    }
    
    private int lowerBound(int[] nums, int target) { // find first element >= target
        int start = 0, end = nums.length;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] >= target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
    
    private int upperBound(int[] nums, int target) { // find first element > target
        int start = 0, end = nums.length;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}
