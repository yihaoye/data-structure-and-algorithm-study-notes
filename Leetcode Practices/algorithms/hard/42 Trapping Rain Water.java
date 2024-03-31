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



// Other's Solution:
class Solution {
    public int trap(int[] height) {
        // 单调栈 - https://leetcode.cn/problems/trapping-rain-water/solutions/185879/dan-diao-zhan-jie-jue-jie-yu-shui-wen-ti-by-sweeti/
        // Time: O(N), Space: O(N)
        if (height == null) return 0;
        Deque<Integer> stack = new LinkedList<>();
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int curIdx = stack.pop();
                // 如果栈顶元素一直相等，那么全都 pop 出去，只留第一个
                while (!stack.isEmpty() && height[stack.peek()] == height[curIdx]) stack.pop();

                if (!stack.isEmpty()) {
                    int stackTop = stack.peek();
                    // stackTop此时指向的是此次接住的雨水的左边界的位置。右边界是当前的柱体，即 i
                    // Math.min(height[stackTop], height[i]) 是左右柱子高度的 min，减去 height[curIdx] 就是雨水的高度
                    // i - stackTop - 1 是雨水的宽度
                    ans += (Math.min(height[stackTop], height[i]) - height[curIdx]) * (i - stackTop - 1);
                }
            }
            stack.push(i);
        }
        return ans;
    }
}



// Other's Solution:
class Solution {
    public int trap(int[] height) {
        // 双指针 - 两边往中间迭代，找到两个边界，取较低的一侧单向推进并累加，直到找到新边界（重复整个过程）
        // Time: O(N), Space: O(1)
        if (height == null || height.length <= 2) return 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, ans = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) leftMax = height[left];
                else ans += leftMax - height[left];
                left++;
            } else {
                if (height[right] >= rightMax) rightMax = height[right];
                else ans += rightMax - height[right];
                right--;
            }
        }
        return ans;
    }
}



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
