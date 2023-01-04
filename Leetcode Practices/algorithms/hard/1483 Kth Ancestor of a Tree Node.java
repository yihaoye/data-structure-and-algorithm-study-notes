/**
You are given a tree with n nodes numbered from 0 to n - 1 in the form of a parent array parent where parent[i] is the parent of ith node. The root of the tree is node 0. Find the kth ancestor of a given node.

The kth ancestor of a tree node is the kth node in the path from that node to the root node.

Implement the TreeAncestor class:

TreeAncestor(int n, int[] parent) Initializes the object with the number of nodes in the tree and the parent array.
int getKthAncestor(int node, int k) return the kth ancestor of the given node node. If there is no such ancestor, return -1.
 

Example 1:
https://assets.leetcode.com/uploads/2019/08/28/1528_ex1.png

Input
["TreeAncestor", "getKthAncestor", "getKthAncestor", "getKthAncestor"]
[[7, [-1, 0, 0, 1, 1, 2, 2]], [3, 1], [5, 2], [6, 3]]
Output
[null, 1, 0, -1]

Explanation
TreeAncestor treeAncestor = new TreeAncestor(7, [-1, 0, 0, 1, 1, 2, 2]);
treeAncestor.getKthAncestor(3, 1); // returns 1 which is the parent of 3
treeAncestor.getKthAncestor(5, 2); // returns 0 which is the grandparent of 5
treeAncestor.getKthAncestor(6, 3); // returns -1 because there is no such ancestor
 

Constraints:

1 <= k <= n <= 5 * 10^4
parent.length == n
parent[0] == -1
0 <= parent[i] < n for all 0 < i < n
0 <= node < n
There will be at most 5 * 10^4 queries.
 */



// Other's Solution:
class TreeAncestor {
    int[][] dp;

    public TreeAncestor(int n, int[] parent) {
        // 倍增法 - dp[i][j] 意味着 i 的 2^j 祖先节点
        // https://www.youtube.com/watch?v=h2NosNVquMU
        init(n, parent);
    }
    
    public int getKthAncestor(int node, int k) {
        for (int i=dp[0].length-1; i>=0; i--) {
            int jump = (int) Math.pow(2, i);
            if (node != -1 && jump <= k) { // 这里用 if 就行不需要 while，因为如果 dp[nextNode][i] 不为 -1 的话，那 dp[lastNode][i+1] 就不会为 -1 也就会被采用而不会被跳过
                node = dp[node][i];
                k -= jump;
            }
            if (node == -1 || k == 0) break; // 优化性能，非必要
        }
        return node;
    }

    private void init(int n, int[] parent) {
        int m = (int) (Math.log(n) / Math.log(2)) + 1;
        dp = new int[n][m];
        for (int i=0; i<n; i++) {
            dp[i][0] = parent[i];
            for (int j=1; j<m; j++) dp[i][j] = -1;
        }
        for (int h=1; h<m; h++) {
            for (int i=0; i<n; i++) {
                if (dp[i][h-1] != -1) dp[i][h] = dp[dp[i][h-1]][h-1];
            }
        }
    }
}
/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */
