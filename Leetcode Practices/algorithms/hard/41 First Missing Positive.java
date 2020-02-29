/*
Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:

Your algorithm should run in O(n) time and uses constant extra space.
*/



// My Solution (use nums.length space):
class Solution {
    public int firstMissingPositive(int[] nums) {
        // the smallest missing positive integer must within range [1, nums.length+1]
        int len = nums.length;
        int[] bucket = new int[len];
        for (int i=0; i<len; i++) {
            if (nums[i] <= len && nums[i] >= 1) bucket[nums[i]-1] = 1;
        }
        for (int i=0; i<len; i++) {
            if (bucket[i] == 0) return i+1;
        }
        return len+1;
    }
}



// Other's Solution (O(n) time, O(1) space):
public class Solution {
    public int firstMissingPositive(int[] nums) {
        // The key here is to use swapping to keep constant space and also make use of the length of the array, which means there can be at most n positive integers. 
        // So each time we encounter an valid integer, find its correct position and swap. Otherwise we continue.
        int i = 0;
        while(i < nums.length){
            if(nums[i] == i+1 || nums[i] <= 0 || nums[i] > nums.length) i++;
            else if(nums[i] != nums[nums[i]-1]) swap(nums, i, nums[i]-1);  
            else i++;
        }
        // Q: why if(nums[i] != nums[nums[i]-1]) instead of if(i != nums[i]-1) ? Aren't they the same?
        // A: Almost the same, but like [3,2,3,4], when i = 0, A[0] = 3, which should be put on position i = 2 where also A[2] = 3. Thus there is no need to do swap and go to else statement i++ for next check or it will go into endless loop

        i = 0;
        while(i < nums.length && nums[i] == i+1) i++;
        return i+1;
    }
    
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
