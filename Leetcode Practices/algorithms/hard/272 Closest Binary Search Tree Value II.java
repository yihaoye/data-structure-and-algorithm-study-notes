/**
Given the root of a binary search tree, a target value, and an integer k, return the k values in the BST that are closest to the target. You may return the answer in any order.

You are guaranteed to have only one unique set of k values in the BST that are closest to the target.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/12/closest1-1-tree.jpg

Input: root = [4,2,5,1,3], target = 3.714286, k = 2
Output: [4,3]

Example 2:

Input: root = [1], target = 0.000000, k = 1
Output: [1]
 

Constraints:

The number of nodes in the tree is n.
1 <= k <= n <= 104.
0 <= Node.val <= 109
-109 <= target <= 109
 

Follow up: Assume that the BST is balanced. Could you solve it in less than O(n) runtime (where n = total nodes)?
 */



// My Solution:
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        // 左右双栈 - 栈顶为当前左右最接近 target 的节点
        // Time: O(logN), Space: O(logN)
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> leftCloserNodes = new LinkedList<>(), rightCloserNodes = new LinkedList<>();
        initLeftRightStacks(root, target, leftCloserNodes, rightCloserNodes);
        
        while (res.size() < k) {
            if (rightCloserNodes.isEmpty() && !leftCloserNodes.isEmpty()) {
                res.add(leftNext(leftCloserNodes));
            }
            
            if (leftCloserNodes.isEmpty() && !rightCloserNodes.isEmpty()) {
                res.add(rightNext(rightCloserNodes));
            }
            
            if (!leftCloserNodes.isEmpty() && !rightCloserNodes.isEmpty()) {
                if (Math.abs((double) rightCloserNodes.peek().val - target) < Math.abs((double) leftCloserNodes.peek().val - target)) {
                    res.add(rightNext(rightCloserNodes));
                } else {
                    res.add(leftNext(leftCloserNodes));
                }
            }
        }
        
        return res;
    }
    
    public void initLeftRightStacks(TreeNode root, double target, Deque<TreeNode> leftCloserNodes, Deque<TreeNode> rightCloserNodes) {
        TreeNode tmp = root;
        while (tmp != null) {
            while (tmp != null && (double) tmp.val >= target) {
                rightCloserNodes.push(tmp);
                tmp = tmp.left;
            }
            while (tmp != null && (double) tmp.val <= target) {
                leftCloserNodes.push(tmp);
                tmp = tmp.right;
            }
        }
    }
    
    public int leftNext(Deque<TreeNode> leftCloserNodes) {
        TreeNode nextClosest = leftCloserNodes.pop();
        TreeNode tmp = nextClosest.left;
        while (tmp != null) {
            leftCloserNodes.push(tmp);
            tmp = tmp.right;
        }
        return nextClosest.val;
    }
    
    public int rightNext(Deque<TreeNode> rightCloserNodes) {
        TreeNode nextClosest = rightCloserNodes.pop();
        TreeNode tmp = nextClosest.right;
        while (tmp != null) {
            rightCloserNodes.push(tmp);
            tmp = tmp.left;
        }
        return nextClosest.val;
    }
}
