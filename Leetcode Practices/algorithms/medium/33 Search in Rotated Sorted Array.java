/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Your algorithm's runtime complexity must be in the order of O(log n).

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
*/



// My Solution:
class Solution {
    public int search(int[] nums, int target) {
        // 二分搜索 + recursion (可以用迭代改写，但是比较麻烦)
        return recursion(nums, 0, nums.length-1, target);
    }

    public int recursion(int[] nums, int l, int r, int target) {
        if (l == r) return nums[l] == target ? l : -1;
        int m = l + (r - l) / 2; // mid
        if (inSub(nums, l, m, target)) return recursion(nums, l, m, target);
        else return recursion(nums, m+1, r, target);
    }

    public boolean inSub(int[] nums, int l, int r, int target) {
        if (nums[l] <= target && target <= nums[r]) return true;
        if (nums[l] > nums[r] && (target <= nums[r] || nums[l] <= target)) return true;
        return false;
    }
}



// My Solution:
class Solution {
    public int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) return -1;
        int index = findIndex(nums); // binary search: find index which index > index+1
        
        if (index == -1) {
            return binarySearch(nums, 0, len-1, target);
        } else if (target > nums[len-1]) {
            return binarySearch(nums, 0, index, target);
        } else {
            return binarySearch(nums, index+1, len-1, target);
        }
    }
    
    public int findIndex(int[] nums) {
        int start = 0, mid = 0, end = nums.length-1;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (nums[mid] > nums[end]) start = mid + 1;
            else end = mid;
        }
        return start-1;
    }
    
    public int binarySearch(int[] nums, int start, int end, int key) {
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] < key)
                start = mid + 1;
            else if (nums[mid] > key)
                end = mid - 1;
            else
                return mid;
        }
        return -1;
    }
}