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
/*with this solution, the following is others' comments

"what if max-min+1 is bigger than 2147483647?"

"instead of int arr[max - min + 1] better to create boolean array and instead of incrementing just 
make value true if it is false, otherwise just return true"

*/


//My Solution: (which is time limit exceeded, why? what is the different between mine and the first solution in this file)
class Solution {
public:

    void quickSort(vector<int>& A, int p,int r)
    {
        int q;
        if(p<r)
        {
            q=partition(A, p, r);
            quickSort(A,p,q-1);  
            quickSort(A,q+1,r);
        }
    }
    
    
    int partition(vector<int>& A, int p,int r)
    {
        int x= A[r];
        int i=p-1;
        int j;
    
        for(j=p; j<=r-1; j++)
        {
            if(A[j]<=x)
            {
                i=i+1;
                swap(A[i],A[j]);
            }
    
        }
    
        swap(A[i+1],A[r]);
        return i+1;
    }

    bool containsDuplicate(vector<int>& nums) {
        if(nums.size()<=1)
            return false;
        
        //sort(nums.begin(), nums.end());
        quickSort(nums, 0, nums.size());
        
        for(int i=0; i<nums.size(); i++)
        {
            if(nums[i]==nums[i+1])
                return true;
        }
        return false;
    }
};