/*
Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.

Example 1:

Input: 2
Output: [0,1,1]
Example 2:

Input: 5
Output: [0,1,1,2,1,2]
Follow up:

It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n) /possibly in a single pass?
Space complexity should be O(n).
Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
*/



// My Solution (DP):
class Solution {
    public int[] countBits(int num) {
        // double dp
        int[] res = new int[num+1];
        int[] bits = new int[32];
        int[] count = new int[1];
        for (int i=1; i<=num; i++) {
            res[i] = incBits(bits, count);
        }
        
        return res;
    }
    
    public int incBits(int[] bits, int[] count) {
        int carry = 1;
        for (int i=0; i<32; i++) {
            if (bits[i] == 0) {
                bits[i] = 1;
                carry = 0;
                count[0]++;
                break;
            }
            bits[i] = 0;
            count[0]--;
        }
        return count[0];
    }
}



// Other's Solution:
// An easy recurrence for this problem is f[i] = f[i / 2] + i % 2.
class Solution {
    public int[] countBits(int num) {
        int[] f = new int[num + 1];
        for (int i=1; i<=num; i++) f[i] = f[i >> 1] + (i & 1); // 也可以写 for (int i=1; i<=num; i++) f[i] = f[i/2] + (i%2);
        return f;
    }
}
/*
https://leetcode.com/problems/counting-bits/discuss/79539/Three-Line-Java-Solution/84518
Explaination.
Take number X for example, 10011001.
Divide it in 2 parts:
<1>the last digit ( 1 or 0, which is " i&1 ", equivalent to " i%2 " )
<2>the other digits ( the number of 1, which is " f[i >> 1] ", equivalent to " f[i/2] " )
*/
