/*
Alice has some number of cards and she wants to rearrange the cards into groups so that each group is of size groupSize, and consists of groupSize consecutive cards.

Given an integer array hand where hand[i] is the value written on the ith card and an integer groupSize, return true if she can rearrange the cards, or false otherwise.

 

Example 1:

Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
Output: true
Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]
Example 2:

Input: hand = [1,2,3,4,5], groupSize = 4
Output: false
Explanation: Alice's hand can not be rearranged into groups of 4.

 

Constraints:

1 <= hand.length <= 104
0 <= hand[i] <= 109
1 <= groupSize <= hand.length
 

Note: This question is the same as 1296: https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
*/



// My Solution:
class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        /*
            先排序，然后使用哈希表记录每个数字及其数量，遍历排好序的数组，取出第一个未使用的数字（若哈希表的相关 Val <= 0 则 continue），然后通过哈希表找到后面的连续 groupSize 的数字（若存在但其 Val <= 0 返回 false，若不存在也返回 false，若 Val 符合条件则自减 1 并更新哈希表）
            时间复杂度 O(N*logN)，空间复杂度 O(M) M - hand[i] 多少个不同的数字
        */
        int len = hand.length;
        if (len % groupSize != 0) return false;
        Arrays.sort(hand);
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : hand) {
            int cnt = map.getOrDefault(num, 0);
            map.put(num, ++cnt);
        }
        
        for (int num : hand) {
            int cnt = map.get(num);
            if (cnt <= 0) continue;
            map.put(num, --cnt);
            
            int loop = 1;
            while (loop < groupSize) {
                int curCnt = map.getOrDefault(num+loop, 0);
                if (curCnt <= 0) return false;
                map.put(num+loop, --curCnt);
                ++loop;
            }
        }
        
        return true;
    }
}
