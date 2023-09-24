/*
You have a list arr of all integers in the range [1, n] sorted in a strictly increasing order. Apply the following algorithm on arr:

Starting from left to right, remove the first number and every other number afterward until you reach the end of the list.
Repeat the previous step again, but this time from right to left, remove the rightmost number and every other number from the remaining numbers.
Keep repeating the steps again, alternating left to right and right to left, until a single number remains.
Given the integer n, return the last number that remains in arr.

 

Example 1:

Input: n = 9
Output: 6
Explanation:
arr = [1, 2, 3, 4, 5, 6, 7, 8, 9]
arr = [2, 4, 6, 8]
arr = [2, 6]
arr = [6]
Example 2:

Input: n = 1
Output: 1
 

Constraints:

1 <= n <= 10^9
*/



// Other's Solution:
class Solution {
    public int lastRemaining(int n) {
        // https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247490948&idx=1&sn=a71c4ec3529277ad119702d7dacf510e&chksm=fd9cb69bcaeb3f8d5461518a15d969b45a13558a4c5810100f40200a12aac66f1876f2ef6de7&token=831443013&lang=zh_CN&scene=21#wechat_redirect
        return n == 1 ? 1 : 2 * (n / 2 + 1 - lastRemaining(n / 2));
    }
}
