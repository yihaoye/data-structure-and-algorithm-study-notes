/**
Given two positive integers num1 and num2, find the integer x such that:

x has the same number of set bits as num2, and
The value x XOR num1 is minimal.
Note that XOR is the bitwise XOR operation.

Return the integer x. The test cases are generated such that x is uniquely determined.

The number of set bits of an integer is the number of 1's in its binary representation.

 

Example 1:

Input: num1 = 3, num2 = 5
Output: 3
Explanation:
The binary representations of num1 and num2 are 0011 and 0101, respectively.
The integer 3 has the same number of set bits as num2, and the value 3 XOR 3 = 0 is minimal.
Example 2:

Input: num1 = 1, num2 = 12
Output: 3
Explanation:
The binary representations of num1 and num2 are 0001 and 1100, respectively.
The integer 3 has the same number of set bits as num2, and the value 3 XOR 1 = 2 is minimal.
 

Constraints:

1 <= num1, num2 <= 10^9
 */



// My Solution:
class Solution {
    public int minimizeXor(int num1, int num2) {
        // 先通过 num1 计算出重叠区，然后计算 num2 的二进制的 1 的个数
        // 然后拿着 1 的个数以贪心算法往重叠区中填入（从较小位开始填），若填完重叠区还有剩就返回已填好的这个数，若填完还有剩则从最小位开始填未填充的
        int num1Cnt = bitCount(num1), num2Cnt = bitCount(num2);
        int res = 0, bit = 1;
        if (num2Cnt == num1Cnt) {
            res = num1;
        } else if (num2Cnt < num1Cnt) {
            bit = 1073741824;
            while (num2Cnt-- > 0) {
                while ((bit & num1) == 0) {
                    bit >>= 1;
                }
                res |= bit;
                bit >>= 1;
            }
        } else if (num2Cnt > num1Cnt) {
            res = num1;
            num2Cnt -= num1Cnt;
            while (num2Cnt-- > 0) {
                while ((bit & res) != 0) {
                    bit <<= 1;
                }
                res |= bit;
                bit <<= 1;
            }
        }
        return res;
    }
    
    public int bitCount(int n) { // count how many 1
        int res = 0;
        while (n > 0) {
            if ((n & 1) == 1) res++;
            n >>= 1;
        }
        return res;
    }
}
