/**
You are given two jugs with capacities jug1Capacity and jug2Capacity liters. There is an infinite amount of water supply available. Determine whether it is possible to measure exactly targetCapacity liters using these two jugs.

If targetCapacity liters of water are measurable, you must have targetCapacity liters of water contained within one or both buckets by the end.

Operations allowed:

Fill any of the jugs with water.
Empty any of the jugs.
Pour water from one jug into another till the other jug is completely full, or the first jug itself is empty.
 

Example 1:

Input: jug1Capacity = 3, jug2Capacity = 5, targetCapacity = 4
Output: true
Explanation: The famous Die Hard example 
Example 2:

Input: jug1Capacity = 2, jug2Capacity = 6, targetCapacity = 5
Output: false
Example 3:

Input: jug1Capacity = 1, jug2Capacity = 2, targetCapacity = 3
Output: true
 

Constraints:

1 <= jug1Capacity, jug2Capacity, targetCapacity <= 10^6
 */



// Other's Solution:
class Solution {
    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        // Math - https://leetcode.cn/problems/water-and-jug-problem/solution/shui-hu-wen-ti-by-leetcode-solution/
        // https://www.youtube.com/watch?v=0Oef3MHYEC0
        // Time: O(log(min(x, y))), Space: O(1)
        if (jug1Capacity + jug2Capacity < targetCapacity) return false;
        if (jug1Capacity == targetCapacity || jug2Capacity == targetCapacity || jug1Capacity + jug2Capacity == targetCapacity ) return true;

        // 贝祖定理（https://www.youtube.com/watch?v=WGO4Tqx5owg），ax+by=z 有解当且仅当 z 是 x, y 的最大公约数的倍数。因此只需要找到 x,y 的最大公约数并判断 z 是否是它的倍数即可。
        return targetCapacity % GCD(jug1Capacity, jug2Capacity) == 0;
    }

    public int GCD(int a, int b) { // 最大公约数
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
