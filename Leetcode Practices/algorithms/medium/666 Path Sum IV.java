/*
If the depth of a tree is smaller than 5, then this tree can be represented by an array of three-digit integers. For each integer in this array:

The hundreds digit represents the depth d of this node where 1 <= d <= 4.
The tens digit represents the position p of this node in the level it belongs to where 1 <= p <= 8. The position is the same as that in a full binary tree.
The units digit represents the value v of this node where 0 <= v <= 9.
Given an array of ascending three-digit integers nums representing a binary tree with a depth smaller than 5, return the sum of all paths from the root towards the leaves.

It is guaranteed that the given array represents a valid connected binary tree.

 

Example 1:
https://assets.leetcode.com/uploads/2021/04/30/pathsum4-1-tree.jpg

Input: nums = [113,215,221]
Output: 12
Explanation: The tree that the list represents is shown.
The path sum is (3 + 5) + (3 + 1) = 12.

Example 2:
https://assets.leetcode.com/uploads/2021/04/30/pathsum4-2-tree.jpg

Input: nums = [113,221]
Output: 4
Explanation: The tree that the list represents is shown. 
The path sum is (3 + 1) = 4.
 

Constraints:

1 <= nums.length <= 15
110 <= nums[i] <= 489
nums represents a valid binary tree with depth less than 5.
*/



// My Solution:
class Solution {
    private int[] lNum = new int[6]; // level node start number = 2^(level-1)

    public int pathSum(int[] nums) {
        // 数学（树基础） + 哈希表
        for (int i=1; i<=5; i++) lNum[i] = lNum[i-1] + (int) Math.pow(2, i-1);
        Map<Integer, Integer> tree = new HashMap<>();
        Set<Integer> leafs = new HashSet<>();
        for (int num : nums) {
            int level = num / 100;
            int order = num / 10 % 10;
            int value = num % 10;
            int nodeIndex = lNum[level-1] + order;
            tree.put(nodeIndex, value);
            leafs.remove(nodeIndex / 2);
            if (!leafs.contains(nodeIndex * 2) && !leafs.contains(nodeIndex * 2 + 1)) leafs.add(nodeIndex);
        }

        int res = 0;
        for (int leafIndex : leafs) res += sum(tree, leafIndex);

        return res;
    }

    public int sum(Map<Integer, Integer> tree, int curIndex) {
        // 从叶子往上递归父节点
        int res = 0;
        if (!tree.containsKey(curIndex)) return res;
        while (curIndex >= 1) {
            res += tree.get(curIndex);
            curIndex = curIndex / 2;
        }
        return res;
    }
}
