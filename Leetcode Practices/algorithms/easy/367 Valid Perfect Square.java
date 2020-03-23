/*
Given a positive integer num, write a function which returns True if num is a perfect square else False.

Note: Do not use any built-in library function such as sqrt.

Example 1:

Input: 16
Output: true
Example 2:

Input: 14
Output: false
*/



// My Solution:
class Solution {
    public boolean isPerfectSquare(int num) {
        if (num == 1) return true;
        int max = num/2 + 1;
        return binarySearch(0, max, num); // apply binary search instead of for to improve time complexity performance
    }
    
    public boolean binarySearch(int l, int r, int num) {
        while (l < r) {
            int m = l + (r-l)/2;
            if (m*m == num) return true;
            if (m > 46340 || m*m > num) r = m; // sqrt(Integer.MAX_VALUE) = 46340.xxx..., a trick to handle leetcode multiply overflow test case
            if (m <= 46340 && m*m < num) l = m+1;
        }
        
        return false;
    }
}