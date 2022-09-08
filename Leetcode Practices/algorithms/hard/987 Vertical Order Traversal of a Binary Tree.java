/**
Given the root of a binary tree, calculate the vertical order traversal of the binary tree.

For each node at position (row, col), its left and right children will be at positions (row + 1, col - 1) and (row + 1, col + 1) respectively. The root of the tree is at (0, 0).

The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each column index starting from the leftmost column and ending on the rightmost column. There may be multiple nodes in the same row and same column. In such a case, sort these nodes by their values.

Return the vertical order traversal of the binary tree.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/29/vtree1.jpg

Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Explanation:
Column -1: Only node 9 is in this column.
Column 0: Nodes 3 and 15 are in this column in that order from top to bottom.
Column 1: Only node 20 is in this column.
Column 2: Only node 7 is in this column.

Example 2:
https://assets.leetcode.com/uploads/2021/01/29/vtree2.jpg

Input: root = [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
Column -2: Only node 4 is in this column.
Column -1: Only node 2 is in this column.
Column 0: Nodes 1, 5, and 6 are in this column.
          1 is at the top, so it comes first.
          5 and 6 are at the same position (2, 0), so we order them by their value, 5 before 6.
Column 1: Only node 3 is in this column.
Column 2: Only node 7 is in this column.

Example 3:
https://assets.leetcode.com/uploads/2021/01/29/vtree3.jpg

Input: root = [1,2,3,4,6,5,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
This case is the exact same as example 2, but with nodes 5 and 6 swapped.
Note that the solution remains the same since 5 and 6 are in the same location and should be ordered by their values.
 

Constraints:

The number of nodes in the tree is in the range [1, 1000].
0 <= Node.val <= 1000
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // BFS + PriorityQueue + class NodeMeta + HashMap<TreeNode, NodeMeta>
        Queue<TreeNode> queue = new LinkedList<>();
        PriorityQueue<NodeMeta> pq = new PriorityQueue<>((nm1, nm2) -> nm1.x != nm2.x ? nm1.x - nm2.x : (nm1.y != nm2.y ? nm1.y - nm2.y : nm1.val - nm2.val));
        Map<TreeNode, NodeMeta> map = new HashMap<>();
        if (root != null) {
            queue.offer(root);
            map.put(root, new NodeMeta(0, 0, root.val));
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            NodeMeta nodeMeta = map.get(node);
            pq.offer(nodeMeta);
            
            if (node.left != null) {
                queue.offer(node.left);
                map.put(node.left, new NodeMeta(nodeMeta.x - 1, nodeMeta.y + 1, node.left.val));
            }
            if (node.right != null) {
                queue.offer(node.right);
                map.put(node.right, new NodeMeta(nodeMeta.x + 1, nodeMeta.y + 1, node.right.val));
            }
        }
        
        List<List<Integer>> res = new ArrayList<>();
        int lastX = Integer.MIN_VALUE;
        while (!pq.isEmpty()) {
            NodeMeta nm = pq.poll();
            if (nm.x != lastX) {
                res.add(new ArrayList<>());
                lastX = nm.x;
            }
            res.get(res.size()-1).add(nm.val);
        }
        return res;
    }
    
    class NodeMeta {
        int x = 0;
        int y = 0;
        int val = 0;
        
        public NodeMeta(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }
}
