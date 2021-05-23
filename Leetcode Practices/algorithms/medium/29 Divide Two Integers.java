/*
Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.

Return the quotient after dividing dividend by divisor.

The integer division should truncate toward zero, which means losing its fractional part. For example, truncate(8.345) = 8 and truncate(-2.7335) = -2.

Note: Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231, 231 − 1]. For this problem, assume that your function returns 231 − 1 when the division result overflows.

 

Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Explanation: 10/3 = truncate(3.33333..) = 3.
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Explanation: 7/-3 = truncate(-2.33333..) = -2.
Example 3:

Input: dividend = 0, divisor = 1
Output: 0
Example 4:

Input: dividend = 1, divisor = 1
Output: 1
 

Constraints:

-231 <= dividend, divisor <= 231 - 1
divisor != 0
*/





// Other's Solution (Bit Manipulation - O(logN^2)):
class Solution {
    public static int divide(int dividend, int divisor) {
        // for the corner case, If A is INT_MIN, and B is -1, the result will be 1 + INT_MAX, hence overflowing, 32 bits, handle it first
        if (Integer.MIN_VALUE == dividend && divisor == -1) return Integer.MAX_VALUE;
        int a = Math.abs(dividend), b = Math.abs(divisor), res = 0;

        // 如果被除数大于或等于除数，则进行如下循环，定义变量 tmp 等于除数，定义计数 count，当 tmp 的两倍小于等于被除数 a 时，进行如下循环：tmp 扩大一倍、count 扩大一倍、然后更新 res 和 a
        while ((a - b) >= 0) {
            int tmp = b, count = 1; // 每次新开一个 tmp 变量初始化为同一个原 divisor，使用 tmp 是为了不改动原 divisor
            while ((a - (tmp << 1)) >= 0) { // (a - tmp*2) >= 0
                tmp <<= 1; // tmp = tmp << 1 (i.e. tmp = tmp * 2)
                count <<= 1; // count = count * 2
            }
            a -= tmp;
            res += count;
        }
        return (dividend > 0) == (divisor > 0) ? res : -res;
    }
}
/*
https://www.cnblogs.com/AlvinZH/p/8643676.html

题目限制这么大，很明显是要考察专一的知识点，那是什么呢？神奇的 位运算操作，简单来讲就是 二进制 的运用。

最初的思路可能是这样子，只运用减法，每次减去除数，最后总能得到结果，没错，但是这样有什么意义呢？一个一个减，多想一下，可不可以多个一起减呢？

举一个简单的例子，求解40÷3，结果为13（忽略余数），如果一个一个减，在第14次减去3的时候可以知道答案是13。这个13可以可以以另一种方式得到，每一个数都有二进制表示，它的含义是什么呢，其实是多个2的幂值相加，这里的13=8+4+1，意味着40=(8*3)+(4*3)+(1*3)···1，所以问题转化为求这多个2的幂值。

具体实现，最初的想法是每次循环减一次（1*除数），现在改进后，每次循环减去（最大2的幂值*除数），通过左移运算，每次将幂值<<1（乘2），求得最大2的幂值后，更新被除数和结果。如何求得最大2的幂值，可参考代码理解。
*/
