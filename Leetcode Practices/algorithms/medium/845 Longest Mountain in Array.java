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
        int res = 0, start = -1, peek = -1; // 后两个为新山的标记元素，分别为新山数组的开始位置、新山数组的峰值位置，-1 时均为无效初值
        for (int i=1; i<arr.length; i++) {
            if (arr[i] > arr[i-1]) { // 如果现元素比上次元素大
                if (start == -1) { // 新山还没开始
                    start = i-1;
                    peek = -1;
                }
                if (peek != -1) { // 新山已开始且已遇到遇到峰时，结算本次山数组的长度，并把标记元素全部设置回无效初值
                    res = Math.max(res, (i-1) - start + 1);
                    start = i-1;
                    peek = -1;
                }
            }
            if (arr[i] < arr[i-1] && start != -1) { // 如果现元素比上次元素小且新山已经开始（新山若未开始则只在意左边山脚是否开始即可 - 即现元素大于上次元素的情况，所以现元素小于上次元素跳过即可不予理会）
                // if (peek != -1) res = Math.max(res, (i-1) - start + 1); // 貌似多余，因为 for 循环结束之后还会尝试结算一次新山数组的长度，所以这里没有必要
                // else peek = i - 1;
                if (peek == -1) peek = i - 1; // 新山开始且未遇峰值，此时可认为刚好找到峰值
            }
            if (arr[i] == arr[i-1]) { // 如果现元素等于上次元素，无论现在记录新山的情况如何，均在结算新山的长度（比如 [0,1,0,0] 遍历到最后一个 0 时 [0,1,0] 仍为有效山数组）后初始化所有标记元素为无效，总是初始化标记是因为题目定义的山数组不允许任何重复数比如 [1,2,3,3,2,1] 也是无效的，除非前面 [0,1,0,0] 那种还是部分有效的
                if (start != -1 && peek != -1) {
                    res = Math.max(res, (i-1) - start + 1);
                }
                peek = -1;
                start = -1;
            }
        }
        if (start != -1 && peek != -1) { // 因为 for 循环的逻辑只在现元素大于等于前元素时（发现山脚）才结算一次，但是输入数组有可能存在山脚在数组外（即数组右方结束）的情况比如 [4,1,2,3,2]，所以此时需额外再结算一次否则可能漏掉
            res = Math.max(res, arr.length - 1 - start + 1);
        }

        return res;
    }
}
