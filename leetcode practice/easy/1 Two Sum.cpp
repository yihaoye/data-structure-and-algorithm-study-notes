//Question:
/*
    Given an array of integers, return indices of the two numbers such that they add up to a specific target.

    You may assume that each input would have exactly one solution.

    Example:

    Given nums = [2, 7, 11, 15], target = 9,

    Because nums[0] + nums[1] = 2 + 7 = 9,
    return [0, 1].

    UPDATE (2016/2/13):
    The return format had been changed to zero-based indices. Please read the above updated description carefully. 
*/




//Answer:
//Approach 3 (One-pass Hash Table)
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        unordered_map<int, int> map;
        for (int i = 0; i < nums.size(); i++) 
        {
            if (map.count(target - nums[i]))
                return {map[target - nums[i]], i};
            //map.insert(nums[i], i);
            map[nums[i]] = i; //this is map.put in C++ ?
        }
        return {-1,-1};
    }
};