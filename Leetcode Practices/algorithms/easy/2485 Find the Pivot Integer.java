/**
Given a positive integer n, find the pivot integer x such that:

The sum of all elements between 1 and x inclusively equals the sum of all elements between x and n inclusively.
Return the pivot integer x. If no such integer exists, return -1. It is guaranteed that there will be at most one pivot index for the given input.

 

Example 1:

Input: n = 8
Output: 6
Explanation: 6 is the pivot integer since: 1 + 2 + 3 + 4 + 5 + 6 = 6 + 7 + 8 = 21.
Example 2:

Input: n = 1
Output: 1
Explanation: 1 is the pivot integer since: 1 = 1.
Example 3:

Input: n = 4
Output: -1
Explanation: It can be proved that no such integer exist.
 

Constraints:

1 <= n <= 1000
 */



// My Solution:
class Solution {
    public int pivotInteger(int n) {
        /* 数学 - Newton Method to calculate the square root or num
            sum = n * (n+1) / 2;
            res = -1;
            sub1 = sum - res / 2;
            sub2 = sub1;

            (sum - res) / 2 = res * (res - 1) / 2;
            sum - res = res * (res - 1);
            sum - res = res * res - res;
            sum = res^2;
        */
        int sum = n * (n+1) / 2;
        long x = sum;
        while (x * x > sum) {
            x = (x + sum / x) >> 1; // Or: x = (x + sum / x) / 2;
        }
        if (x * x == sum) return (int) x;
        return -1;
    }
}
