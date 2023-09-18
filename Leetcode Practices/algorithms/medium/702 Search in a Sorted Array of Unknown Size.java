/*
This is an interactive problem.

You have a sorted array of unique elements and an unknown size. You do not have an access to the array but you can use the ArrayReader interface to access it. You can call ArrayReader.get(i) that:

returns the value at the ith index (0-indexed) of the secret array (i.e., secret[i]), or
returns 231 - 1 if the i is out of the boundary of the array.
You are also given an integer target.

Return the index k of the hidden array where secret[k] == target or return -1 otherwise.

You must write an algorithm with O(log n) runtime complexity.

 

Example 1:

Input: secret = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in secret and its index is 4.
Example 2:

Input: secret = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in secret so return -1.
 

Constraints:

1 <= secret.length <= 10^4
-10^4 <= secret[i], target <= 10^4
secret is sorted in a strictly increasing order.
*/



/**
 * // This is ArrayReader's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface ArrayReader {
 *     public int get(int index) {}
 * }
 */
class Solution {
    public int search(ArrayReader reader, int target) {
        // 二分搜索（本题解不符合传统的倍增法 Binary Lifting 定义，虽然有点类似，但是倍增法是要预处理的，然后根据预处理的动态规划表来贪心地往前跳）
        // Time: O(logN), Space: O(1)
        int index = 0; // temp index
        int lift = 1;
        while (reader.get(index) < target) {
            while (reader.get(index + lift) <= target) lift *= 2;
            lift /= 2; // reader.get(index + lift) > target
            if (lift == 0) break; // 再往前一格就大于 target 值了
            index += lift;
            lift = 1;
        }

        return reader.get(index) == target ? index : -1;
    }
}
