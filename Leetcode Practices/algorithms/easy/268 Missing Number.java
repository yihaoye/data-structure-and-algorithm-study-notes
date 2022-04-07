/*
Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

Example 1:

Input: [3,0,1]
Output: 2
Example 2:

Input: [9,6,4,2,3,5,7,0,1]
Output: 8
Note:
Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
*/



// Other's Solution 1:
class Solution {
    public int missingNumber(int[] nums) {
        int xor = 0, i = 0;
        for (i = 0; i < nums.length; i++) {
            xor = xor ^ i ^ nums[i];
        }
        return xor ^ i;
    }
}
/*
The basic idea is to use XOR operation. Since a^b^b =a, which means two xor operations with the same number will eliminate the number and reveal the original number.
In this solution, apply XOR operation to both the index and value of the array. In a complete array with no missing numbers, the index and value should be perfectly corresponding( nums[index] = index), so in a missing array, what left finally is the missing number.
*/



// Other's Solution 2:
// 1.XOR
class Solution {
    public int missingNumber(int[] nums) { //xor
        int res = nums.length;
        for(int i=0; i<nums.length; i++){
            res ^= i;
            res ^= nums[i];
        }
        return res;
    }
}
class Solution {
    public int missingNumber(int[] nums) {
        /*
            位运算
            Time: O(N), Space: O(1)
        */
        int xor = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) xor ^= nums[i];
        for (int i = 0; i <= n; i++) xor ^= i;
        return xor;
    }
}

// 2.SUM
class Solution {
    public int missingNumber(int[] nums) { //sum
        int len = nums.length;
        int sum = (0+len)*(len+1)/2;
        for(int i=0; i<len; i++)
            sum-=nums[i];
        return sum;
    }
}



// My Solution:
// Requirement - linear runtime complexity means O(n). Which means Binary Search (need array sort first) will not be acceptable here, since sort need aleast O(n*logn).
// Requirement - only constant extra space complexity. Which means BucketSort way (use the same principle but no need sort which must be O(n) in time & space complexity) is not acceptable here too.