/**
You may recall that an array arr is a mountain array if and only if:

arr.length >= 3
There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
Given an integer array arr, return the length of the longest subarray, which is a mountain. Return 0 if there is no mountain subarray.

 

Example 1:

Input: arr = [2,1,4,7,3,2,5]
Output: 5
Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
Example 2:

Input: arr = [2,2,2]
Output: 0
Explanation: There is no mountain.
 

Constraints:

1 <= arr.length <= 104
0 <= arr[i] <= 104
 

Follow up:

Can you solve it using only one pass?
Can you solve it in O(1) space?
 */



// My Solution:
class Solution {
    public int longestMountain(int[] arr) {
        /*
            标记+模拟算法
            Time: O(N), Space: O(1)
        */
        int res = 0, start = -1, peek = -1;
        for (int i=1; i<arr.length; i++) {
            if (arr[i] > arr[i-1]) {
                if (start == -1) {
                    start = i-1;
                    peek = -1;
                }
                if (peek != -1) {
                    res = Math.max(res, (i-1) - start + 1);
                    start = i-1;
                    peek = -1;
                }
            }
            if (arr[i] < arr[i-1] && start != -1) {
                if (peek != -1) res = Math.max(res, (i-1) - start + 1);
                else peek = i - 1;
            }
            if (arr[i] == arr[i-1]) {
                if (start != -1 && peek != -1) {
                    res = Math.max(res, (i-1) - start + 1);
                }
                peek = -1;
                start = -1;
            }
        }
        if (start != -1 && peek != -1) {
            res = Math.max(res, arr.length - 1 - start + 1);
        }

        return res;
    }
}
