//Question:
/*
    Given a 32-bit signed integer, reverse digits of an integer.

    Example 1:

    Input: 123
    Output: 321
    Example 2:

    Input: -123
    Output: -321
    Example 3:

    Input: 120
    Output: 21
    Note:
    Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
*/

// My Answer:
class Solution {
    public int reverse(int x) {
        long y = 0;
        long limit = 0;
        int sign = 1;
        if (x > 0) {
            limit = 2147483647; // 2^31-1
        } else {
            x = -x;
            limit = -2147483648; // -2^31
            sign = -1;
        }
        while(x >= 10) {
            y = x%10 + y*10;
            x = x/10;
        }
        y = (x + y*10)*sign;
        if ((y < 0 && y < limit) || (y > 0 && y > limit)) {
            return 0;
        }
        return (int)y;
    }
}