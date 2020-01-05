/*
Implement int sqrt(int x).

Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.

Example 1:

Input: 4
Output: 2
Example 2:

Input: 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since 
             the decimal part is truncated, 2 is returned.
*/



// My Solution 1:
class Solution {
    public int mySqrt(int x) {
        if (x == 0) return 0;
        if (x < 4) return 1;
        // binary search
        int start = 0, end = x;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            // notice: cannot (mid*mid <> x), since int may exceed when x is too large
            if (mid < x/mid) start = mid + 1;
            else if (mid > x/mid) end = mid - 1;
            else return mid;
        }
        
        return start-1;
    }
}