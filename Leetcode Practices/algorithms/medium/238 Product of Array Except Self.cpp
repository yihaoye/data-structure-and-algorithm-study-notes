//Question:
/*
 Given an array of n integers where n > 1, nums, return an array output such that output[i] is equal to the product of all the elements of nums except nums[i] (除本身之外的数组之积). 

 Solve it without division and in O(n).

 For example, given [1,2,3,4], return [24,12,8,6]. 
*/



//Others' Solution:
class Solution {
public:
    vector<int> productExceptSelf(vector<int>& nums) {
        int n=nums.size();
        vector<int> res(n);
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i-1] * nums[i-1]; //res[i] means all left elements product except nums[i]
        }
        int right = 1;
        for (int i = n-1; i >= 0; i--) {
            res[i] *= right;	//"right" means all right elements product except nums[i] (for nums[i])
            right *= nums[i];	//calculate nums[i-1]'s right handside elements product
        }
        return res;
        }
};

 