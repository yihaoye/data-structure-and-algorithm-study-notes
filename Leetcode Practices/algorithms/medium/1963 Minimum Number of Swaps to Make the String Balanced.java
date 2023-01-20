/**
You are given a 0-indexed string s of even length n. The string consists of exactly n / 2 opening brackets '[' and n / 2 closing brackets ']'.

A string is called balanced if and only if:

It is the empty string, or
It can be written as AB, where both A and B are balanced strings, or
It can be written as [C], where C is a balanced string.
You may swap the brackets at any two indices any number of times.

Return the minimum number of swaps to make s balanced.

 

Example 1:

Input: s = "][]["
Output: 1
Explanation: You can make the string balanced by swapping index 0 with index 3.
The resulting string is "[[]]".
Example 2:

Input: s = "]]][[["
Output: 2
Explanation: You can do the following to make the string balanced:
- Swap index 0 with index 4. s = "[]][][".
- Swap index 1 with index 5. s = "[[][]]".
The resulting string is "[[][]]".
Example 3:

Input: s = "[]"
Output: 0
Explanation: The string is already balanced.
 

Constraints:

n == s.length
2 <= n <= 10^6
n is even.
s[i] is either '[' or ']'.
The number of opening brackets '[' equals n / 2, and the number of closing brackets ']' equals n / 2.
 */



// Other's Solution:
class Solution {
    public int minSwaps(String s) {
        // Greedy + 数学 - https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-string-balanced/solutions/1390319/how-to-reach-the-optimal-solution-explained-with-intuition-and-diagram/comments/1040249
        int count = 0, stackSize = 0;
        for (char c : s.toCharArray()) { // 筛选出净 ]]]...[[[ 的对数，如果对数为奇数，则最优为中间为 ][ - 比如 ]]][[[ -> [[][]]，若对数为偶数，则中间无论如何都会是 []，所以即是长度除以 2
            if (c == '[') stackSize++;
            else if (stackSize == 0) count++;
            else stackSize--;
        }
        
        return (count + 1) / 2; // ceil(count / 2)
    }
}
