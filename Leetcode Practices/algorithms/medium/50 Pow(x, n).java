/*
Implement pow(x, n), which calculates x raised to the power n (i.e. x^n).

Example 1:
    Input: x = 2.00000, n = 10
    Output: 1024.00000

Example 2:
    Input: x = 2.10000, n = 3
    Output: 9.26100

Example 3:
    Input: x = 2.00000, n = -2
    Output: 0.25000
    Explanation: 2^-2 = (1/2)^2 = 1/4 = 0.25
 
Constraints:
    -100.0 < x < 100.0
    -2^31 <= n <= 2^31-1
    -10^4 <= xn <= 10^4
*/



// Other's Solution: (分治法/Divide-and-conquer+Recursion - logN)
class Solution {
    public double myPow(double x, int n) {
        /*
            分治法 + 特殊情况处理
            时间复杂度 O(logN)，空间复杂度 O(logN) - 栈
        */
        if (n == 0) return 1;
        if (n == Integer.MIN_VALUE) { // because MIN_VALUE is -2147483648 but MAX_VALUE is 2147483647 ,so below n = -n is failed （特殊情况，因为若参数 n == Integer.MIN_VALUE 时，n = -n 会越界赋值出错）
            x = x * x;
            n = n/2;
        }
        if (n < 0) {
            n = -n;
            x = 1/x;
        }

        return (n%2 == 0) ? myPow(x * x, n/2) : x * myPow(x * x, n/2);
    }
}
