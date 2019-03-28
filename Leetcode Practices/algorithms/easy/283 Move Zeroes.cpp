//Question:
/*
Given an array nums, write a function to move all 0's to the end of it while maintaining the relative 
order of the non-zero elements.

For example, given nums = [0, 1, 0, 3, 12], after calling your function, 
nums should be [1, 3, 12, 0, 0].

Note:
    You must do this in-place without making a copy of the array.
    Minimize the total number of operations.
*/


//Solution:
class Solution {
public:
    void moveZeroes(vector<int>& nums) {

        int pos = 0; //last non zero element in nums after one pass

        for(int i = 0; i < nums.size(); i++)
        {
            if(nums[i] != 0)
                swap(nums[pos++], nums[i]); // swap() means exchange value of two variable
        }
    }
};

//Explain:
/*
该算法等于是依次把数组中不为零的值从数组第0位开始排好
*/