/*
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

 

Example 1:
https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png

Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
Example 2:

Input: height = [4,2,0,3,2,5]
Output: 9
 

Constraints:

n == height.length
1 <= n <= 2 * 104
0 <= height[i] <= 105
*/



// My Solution:
class Solution {
    public int trap(int[] height) {
        /*
            数学，TreeMap+前缀和+类暴力解
            时间复杂度 O(N*logN)，空间复杂度 O(N)
        */
        int len = height.length, res = 0;
        if (len < 2) return res;
        TreeMap<Integer, int[]> treeMap = new TreeMap<>(Collections.reverseOrder()); // <height, [left, right]>
        int[] prefixSum = new int[len];
        for (int i=0; i<len; i++) {
            prefixSum[i] = (i == 0) ? height[i] : prefixSum[i-1] + height[i];
            if (treeMap.containsKey(height[i])) {
                int[] curHeightRange = treeMap.get(height[i]);
                curHeightRange[1] = i;
            } else {
                int[] curHeightRange = new int[]{i, i};
                treeMap.put(height[i], curHeightRange);
            }
        }

        int heigherLeft = len, heigherRight = -1;
        for (Map.Entry<Integer, int[]> entry : treeMap.entrySet()) {
            int[] curHeightRange = entry.getValue();
            if (heigherLeft != len && heigherRight != -1) { // 其实只要一个不等就可以，只有第一个键不符合
                if (curHeightRange[0] > heigherLeft && curHeightRange[1] < heigherRight) { // 若柱高范围在之前柱高范围内则跳过
                    continue;
                } else if (curHeightRange[0] > heigherRight || curHeightRange[1] < heigherLeft) {
                    if (curHeightRange[0] > heigherRight) { // 本轮柱高范围完全在之前柱高范围之外（右）
                        res += entry.getKey() * (curHeightRange[1] - heigherRight);
                    }
                    if (curHeightRange[1] < heigherLeft) { // 本轮柱高范围完全在之前柱高范围之外（左）
                        res += entry.getKey() * (heigherLeft - curHeightRange[0]);
                    }
                } else {
                    if (curHeightRange[0] < heigherLeft) { // 左边界在之前柱高左右范围之外（左）
                        res += entry.getKey() * (heigherLeft - curHeightRange[0]);
                    }
                    if (curHeightRange[1] > heigherRight) { // 右边界在之前柱高左右范围之外（右）
                        res += entry.getKey() * (curHeightRange[1] - heigherRight);
                    }
                }
            } else {
                res += entry.getKey() * (curHeightRange[1] - curHeightRange[0] + 1);
            }
            if (curHeightRange[0] < heigherLeft) heigherLeft = curHeightRange[0];
            if (curHeightRange[1] > heigherRight) heigherRight = curHeightRange[1];
        }

        res -= (prefixSum[heigherRight] - prefixSum[heigherLeft] + height[heigherLeft]);
        return res;
    }
}
