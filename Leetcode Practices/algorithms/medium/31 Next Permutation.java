/**
A permutation of an array of integers is an arrangement of its members into a sequence or linear order.

For example, for arr = [1,2,3], the following are considered permutations of arr: [1,2,3], [1,3,2], [3,1,2], [2,3,1].
The next permutation of an array of integers is the next lexicographically greater permutation of its integer. More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).

For example, the next permutation of arr = [1,2,3] is [1,3,2].
Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does not have a lexicographical larger rearrangement.
Given an array of integers nums, find the next permutation of nums.

The replacement must be in place and use only constant extra memory.

 

Example 1:

Input: nums = [1,2,3]
Output: [1,3,2]
Example 2:

Input: nums = [3,2,1]
Output: [1,2,3]
Example 3:

Input: nums = [1,1,5]
Output: [1,5,1]
 

Constraints:

1 <= nums.length <= 100
0 <= nums[i] <= 100
 */



// My Solution:
class Solution {
    public void nextPermutation(int[] nums) {
        // 排序+双指针 - 从后往前找到第一个不是单调递增（从右往左的递增）的元素，然后把该元素与已经遍历过的元素中的第一个比它大的元素对换（如果有重复要找最后一个），然后将剩下的遍历过的元素全部排序一次（使其从左往右单调递增），因为剩下的必定为倒序，所以可以通过双指针头尾逼近中间 swap 的方式完成；如果找不到第一个元素，则说明整个 nums 都是倒序，将所有元素排序一次即可
        // Time: O(N), Space: O(1)
        int left = -1, right = nums.length-1, swapIndex = -1;
        for (int i=nums.length-1; i>0; i--) {
            if (nums[i] > nums[i-1]) {
                left = i-1;
                swapIndex = i;
                break;
            }
        }
        if (left != -1) {
            for (int i=left+1; i<nums.length; i++) {
                if (nums[i] > nums[left] && nums[i] <= nums[swapIndex]) swapIndex = i;
            }
            int tmp = nums[left];
            nums[left] = nums[swapIndex];
            nums[swapIndex] = tmp;
            left++;
        } else {
            left = 0;
        }
        swapSubArr(nums, left, right);
    }
    
    private void swapSubArr(int[] nums, int l, int r) {
        while (l < r) {
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l++;
            r--;
        }
    }
}
