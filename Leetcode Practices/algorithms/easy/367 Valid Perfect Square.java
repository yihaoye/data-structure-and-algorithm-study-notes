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



// Other's Solution:
/*
A square number is 1+3+5+7+... : 
This is a math problem：
1 = 1
4 = 1 + 3
9 = 1 + 3 + 5
16 = 1 + 3 + 5 + 7
25 = 1 + 3 + 5 + 7 + 9
36 = 1 + 3 + 5 + 7 + 9 + 11
....
so 1+3+...+(2n-1) = (2n-1 + 1)n/2 = nn
*/
class Solution1 { // time complexity is O(sqrt(n))
    public boolean isPerfectSquare(int num) {
        int i = 1;
        while (num > 0) {
            num -= i;
            i += 2;
        }
        return num == 0;
    }
}

// Another way is to use Newton Method to calculate the square root or num, refer to Newton Method for details.
// https://zh.wikipedia.org/wiki/%E7%89%9B%E9%A1%BF%E6%B3%95
// https://en.wikipedia.org/wiki/Integer_square_root#Using_only_integer_division
class Solution2 {
    public boolean isPerfectSquare(int num) {
        long x = num;
        while (x * x > num) {
            x = (x + num / x) >> 1;
        }
        return x * x == num;
    }
}
// https://leetcode.com/problems/valid-perfect-square/discuss/83874/A-square-number-is-1%2B3%2B5%2B7%2B...-JAVA-code