/**
You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the graph.

Return true if the edges of the given graph make up a valid tree, and false otherwise.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/12/tree1-graph.jpg

Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true

Example 2:
https://assets.leetcode.com/uploads/2021/03/12/tree2-graph.jpg

Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
 

Constraints:

1 <= n <= 2000
0 <= edges.length <= 5000
edges[i].length == 2
0 <= ai, bi < n
ai != bi
There are no self-loops or repeated edges.
 */



// Other's Solution:
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // 简易并查集（ranks_ 可忽略） - https://leetcode.com/problems/graph-valid-tree/discuss/69018/AC-Java-Union-Find-solution
        int[] parents_ = new int[n];
        for (int i = 0; i < n; i++) parents_[i] = i;
        
        for (int i = 0; i < edges.length; i++) {
            if (union(parents_, edges[i][0], edges[i][1])) continue; // 因为是无向图，所以 union 函数里 edges[i][0]、edges[i][1] 先后顺序没关系，因为假设 Example 1 的 1 为根结点的话也没关系，因为只是把 0 换成 1 而已，如果加上 ranks_ 路径压缩的话最后还是以一个结点为根（可以是 0..n 任意一个，换谁实际都没有区别）的 2 层无向多叉树
            else return false; // if two vertices happen to be in the same set then there's a cycle
        }
        
        return edges.length == n - 1;
    }
    
    public boolean union(int parents_[], int u, int v) {
        int pu = find(parents_, u);
        int pv = find(parents_, v);
        if (pu == pv) return false;

        parents_[pu] = pv;
        return true;
    }
    
    public int find(int parents_[], int u) {
        while (parents_[u] != u) {
            parents_[u] = parents_[parents_[u]];
            u = parents_[u];
        }
        return u;
    }
}
