/**
You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in adjacent plots.

Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n, return if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule.

 

Example 1:

Input: flowerbed = [1,0,0,0,1], n = 1
Output: true
Example 2:

Input: flowerbed = [1,0,0,0,1], n = 2
Output: false
 

Constraints:

1 <= flowerbed.length <= 2 * 10^4
flowerbed[i] is 0 or 1.
There are no two adjacent flowers in flowerbed.
0 <= n <= flowerbed.length
 */



// My Solution:
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        // 双指针 - 计算每个连续 0 的段落的最大可种植值（去除掉头尾两个 0）= len % 2 == 0 ? len/2 : len/2 + 1，然后全部相加即最大可种植值 max，如果 max >= n 则返回 true，否则 false
        // Time: O(N), Space: O(1)
        if (n == 0) return true;
        if (flowerbed.length == 1) return flowerbed[0] != 1;
        int left = 0, right = 0, max = 0;
        for (int i=0; i<flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                int len = (right - left + 1 >= 2) ? right - left + 1 - 2 : 0;
                if (left == 0) len = right - left + 1 - 1;
                max += (len % 2 == 0) ? len/2 : len/2 + 1;
                left = i+1;
                right = i+1;
            } else {
                right = i;
            }
        }
        if (left != right) { // 最后面一段为连续 0
            int len = left == 0 ? right - left + 1 : right - left + 1 - 1;
            max += (len % 2 == 0) ? len/2 : len/2 + 1;
        }
        
        return max >= n;
    }
}
