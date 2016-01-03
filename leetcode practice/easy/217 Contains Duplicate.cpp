//Question:
/*
Given an array of integers, find if the array contains any duplicates. 
Your function should return true if any value appears at least twice in the array, 
and it should return false if every element is distinct.
*/



//Solution:
class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        if(nums.size()<=1)
            return false;
        
        sort(nums.begin(), nums.end());
        for(int i=0; i<nums.size(); i++)
        {
            if(nums[i]==nums[i+1])
                return true;
        }
        return false;
    }
};



//Other's Solution:
class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        if(nums.size() == 0)    return false;

        int min = nums[0], max = nums[0];
        for(auto n : nums){
            if(n > max)     max = n;
            if(n < min)    min = n;
        }

        int arr[max - min + 1] = {0};
        for(auto n : nums){
            ++arr[n - min];
        }

        for(int i = 0; i != (max - min + 1); ++i)
            if(arr[i] > 1)  return true;
        return false;
    }
};