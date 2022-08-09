/**
Given the root of a binary tree, return the vertical order traversal of its nodes' values. (i.e., from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from left to right.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/28/vtree1.jpg

Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]

Example 2:
https://assets.leetcode.com/uploads/2021/01/28/vtree2-1.jpg

Input: root = [3,9,8,4,0,1,7]
Output: [[4],[9],[3,0,1],[8],[7]]

Example 3:
https://assets.leetcode.com/uploads/2021/01/28/vtree2.jpg

Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
Output: [[4],[9,5],[3,0,1],[8,2],[7]]
 

Constraints:

The number of nodes in the tree is in the range [0, 100].
-100 <= Node.val <= 100
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
    public List<List<Integer>> verticalOrder(TreeNode root) {
        // BFS + TreeMap<columnIndex, List<Integer>>，注意根节点的 columnIndex 为 0，然后向左为 -1，向右为 +1，再另外用一个 HashMap<TreeNode, columnIndex> 来记录队列中每个节点的 columnIndex 值
        List<List<Integer>> res = new ArrayList<>();
        
        Queue<TreeNode> queue = new LinkedList<>();
        TreeMap<Integer, List<Integer>> tMap = new TreeMap<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        if (root != null) {
            queue.offer(root);
            map.put(root, 0);
        }
        
        while (!queue.isEmpty()) {
            TreeNode preNode = queue.poll();
            Integer preNodeColIdx = map.get(preNode);
            List<Integer> preList = tMap.getOrDefault(preNodeColIdx, new ArrayList<>());
            preList.add(preNode.val);
            tMap.put(preNodeColIdx, preList);
            
            if (preNode.left != null) {
                queue.offer(preNode.left);
                map.put(preNode.left, preNodeColIdx-1);
            }
            if (preNode.right != null) {
                queue.offer(preNode.right);
                map.put(preNode.right, preNodeColIdx+1);
            }
        }
        
        for (List<Integer> preList : tMap.values()) {
            res.add(preList);
        }
        
        return res;
    }
}
