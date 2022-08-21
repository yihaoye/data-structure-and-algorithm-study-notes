/**
You are given the root of a binary tree with unique values, and an integer start. At minute 0, an infection starts from the node with value start.

Each minute, a node becomes infected if:

The node is currently uninfected.
The node is adjacent to an infected node.
Return the number of minutes needed for the entire tree to be infected.

 

Example 1:


Input: root = [1,5,3,null,4,10,6,9,2], start = 3
Output: 4
Explanation: The following nodes are infected during:
- Minute 0: Node 3
- Minute 1: Nodes 1, 10 and 6
- Minute 2: Node 5
- Minute 3: Node 4
- Minute 4: Nodes 9 and 2
It takes 4 minutes for the whole tree to be infected so we return 4.
Example 2:


Input: root = [1], start = 1
Output: 0
Explanation: At minute 0, the only node in the tree is infected so we return 0.
 

Constraints:

The number of nodes in the tree is in the range [1, 10^5].
1 <= Node.val <= 10^5
Each node has a unique value.
A node with a value of start exists in the tree.
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
    public int longestPath = 0;
    
    public int amountOfTime(TreeNode root, int start) {
        // 计算树中与 start 节点最远的节点之间的距离
        // DFS 后顺序遍历，每个节点返回一个两元素数组，分别是到 start 的距离（若子节点不经过 start 则为 Integer.MIN_VALUE）以及最深距离（不经过 start 的路线，若路线必定经过 start 则为 Integer.MIN_VALUE）
        dfs(root, start);
        
        return longestPath;
    }
    
    public int[] dfs(TreeNode node, int start) {
        if (node.val == start) {
            longestPath = Math.max(longestPath, dfsStart(node));
            return new int[]{0, Integer.MIN_VALUE};
        }
        
        int[] res = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        if (node.left == null && node.right == null) res[1] = 0;
        if (node.left != null) {
            int[] lres = dfs(node.left, start);
            res[0] = Math.max(res[0], lres[0] + 1);
            if (!(lres[0] >= 0 && lres[1] >= 0)) res[1] = Math.max(res[1], lres[1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : lres[1] + 1);
        }
        if (node.right != null) {
            int[] rres = dfs(node.right, start);
            res[0] = Math.max(res[0], rres[0] + 1);
            if (!(rres[0] >= 0 && rres[1] >= 0)) res[1] = Math.max(res[1], rres[1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : rres[1] + 1);
        }
        
        if (res[0] != Integer.MIN_VALUE) {
            if (res[1] != Integer.MIN_VALUE) longestPath = Math.max(longestPath, res[0] + res[1]);
            else longestPath = Math.max(longestPath, res[0]);
        }
        
        return res;
    }
    
    public int dfsStart(TreeNode node) {
        int res = 0;
        
        if (node.left != null) {
            res = Math.max(res, dfsStart(node.left) + 1);
        }
        if (node.right != null) {
            res = Math.max(res, dfsStart(node.right) + 1);
        }
        
        return res;
    }
}



// My Solution 2:
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
    public Map<Integer, List<Integer>> graph = new HashMap<>();
    public Set<Integer> visited = new HashSet<>();
    
    public int amountOfTime(TreeNode root, int start) {
        // 计算树中与 start 节点最远的节点之间的距离
        // DFS 构建图（HashMap<node_val, List<adjacent_node_val>>），然后以 start 节点为起始点在图里进行 BFS 遍历，使用一个 HashSet 保存 visited 的节点
        buildGraph(root, null);
        
        return bfs(start);
    }
    
    public void buildGraph(TreeNode node, TreeNode parent) {
        List<Integer> adjacents = new LinkedList<>();
        if (parent != null) adjacents.add(parent.val);
        if (node.left != null) adjacents.add(node.left.val);
        if (node.right != null) adjacents.add(node.right.val);
        graph.put(node.val, adjacents);
        
        if (node.left != null) buildGraph(node.left, node);
        if (node.right != null) buildGraph(node.right, node);
        return;
    }
    
    public int bfs(int start) {
        int res = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int curSize = queue.size();
            while (curSize-- > 0) {
                int curNode = queue.poll();
                visited.add(curNode);
                for (int nextNode : graph.get(curNode))
                    if (!visited.contains(nextNode)) queue.add(nextNode);
            }
            res++;
        }
        return res - 1;
    }
}
