//Question:
/*
Given an array of size n, find the majority element. 
The majority element is the element that appears more than n/2 times.

You may assume that the array is non-empty and the majority element always exist in the array.
*/




//My Solution: (with a satisfied performance)
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        int pre=nums[0];
        int maj_sum=1;
        int comp=(nums.size())/2;
        
        if(nums.size()<=1)
            return nums[0];
        
        for(int i=1; i<nums.size(); i++)
        {
            if(nums[i]==pre)
            {
                maj_sum++;
                if(maj_sum>comp)
                    return nums[i];
            }
            else
            {
                pre=nums[i];
                maj_sum=1;
            }
        }
    }
};


//Other's Solution 1:
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        //if(nums.size()<=1)
            //return nums[0];
        
        return nums[nums.size()/2];
    }
};


//Other's Solution 2:
// Moore voting algorithm: Runtime: O(n), Space O(1)
// We maintain a current candidate and a counter initialized to 0. 
// As we iterate the array, we look at the current element x: 
// If the counter is 0, we set the current candidate to x and the counter to 1.
// If the counter is not 0, we increment or decrement the counter based on whether x is the current candidate.
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        int cur = 0;
        int cnt = 0;
        for (int x : nums) {
            if (cnt == 0) cur = x, cnt = 1;
            else if (cur == x) ++cnt;
            else --cnt;
        }
        return cur;
        }
};



//My First Solution: (Runtime Error with [-1,-1,2147483647])
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        int max=nums[0];
        int min=nums[0];
        
        for(auto n:nums)
        {
            if(n>max) max=n;
            if(n<min) min=n;
        }
        int length=max-min+1;
        int array[length]={0};
        
        for(auto n : nums)
        {
            array[n-min]++;
        }
        
        for(int i=0; i<length; i++)
        {
            if(array[i]>(nums.size()/2))
                return i+min;
        }
    }
};