/**
There is a tree (i.e. a connected, undirected graph with no cycles) consisting of n nodes numbered from 0 to n - 1 and exactly n - 1 edges.

You are given a 0-indexed integer array vals of length n where vals[i] denotes the value of the ith node. You are also given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting nodes ai and bi.

A good path is a simple path that satisfies the following conditions:

The starting node and the ending node have the same value.
All nodes between the starting node and the ending node have values less than or equal to the starting node (i.e. the starting node's value should be the maximum value along the path).
Return the number of distinct good paths.

Note that a path and its reverse are counted as the same path. For example, 0 -> 1 is considered to be the same as 1 -> 0. A single node is also considered as a valid path.

 

Example 1:
https://assets.leetcode.com/uploads/2022/08/04/f9caaac15b383af9115c5586779dec5.png

Input: vals = [1,3,2,1,3], edges = [[0,1],[0,2],[2,3],[2,4]]
Output: 6
Explanation: There are 5 good paths consisting of a single node.
There is 1 additional good path: 1 -> 0 -> 2 -> 4.
(The reverse path 4 -> 2 -> 0 -> 1 is treated as the same as 1 -> 0 -> 2 -> 4.)
Note that 0 -> 2 -> 3 is not a good path because vals[2] > vals[0].

Example 2:
https://assets.leetcode.com/uploads/2022/08/04/149d3065ec165a71a1b9aec890776ff.png

Input: vals = [1,1,2,2,3], edges = [[0,1],[1,2],[2,3],[2,4]]
Output: 7
Explanation: There are 5 good paths consisting of a single node.
There are 2 additional good paths: 0 -> 1 and 2 -> 3.

Example 3:
https://assets.leetcode.com/uploads/2022/08/04/31705e22af3d9c0a557459bc7d1b62d.png

Input: vals = [1], edges = []
Output: 1
Explanation: The tree consists of only one node, so there is one good path.
 

Constraints:

n == vals.length
1 <= n <= 3 * 10^4
0 <= vals[i] <= 10^5
edges.length == n - 1
edges[i].length == 2
0 <= ai, bi < n
ai != bi
edges represents a valid tree.
 */



// Other's Solution:
class Solution {
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        // 并查集 - 
        // https://leetcode.com/problems/number-of-good-paths/discuss/2620532/JAVA-UnionFind-Solution-Explanation
        // https://leetcode.com/problems/number-of-good-paths/discuss/2620529/Python-Explanation-with-picture-DSU
        int n = vals.length;
        int sum = n;
        
        // create adjacency list 邻接表
        List<List<Integer>> adjList = new ArrayList<>();
        
        // create sortedmap with key as node value and value as list of node index
        TreeMap<Integer, List<Integer>> tm = new TreeMap<>();
        
        // create unionfind
        UnionFind uf = new UnionFind(n);
        
        for (int i=0; i<n; i++) { // O(N)
            adjList.add(new ArrayList<>());
            tm.computeIfAbsent(vals[i], x -> new ArrayList<>()).add(i);
        }
        for (int[] edge : edges) { // O(N)
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }
        
        // traverse node values from lowest to highest 
        Map.Entry<Integer, List<Integer>> curVal = tm.firstEntry();
        while (curVal != null) {
			List<Integer> curValNodes = curVal.getValue();

            // for each node union with neighbor if neighbor value is
			// lower or equal to node value
            for (int node : curValNodes) {
                List<Integer> neighbors = adjList.get(node);
                for (Integer neighbor : neighbors) {
                    if (vals[node] >= vals[neighbor]) uf.union(node, neighbor);
                }
            }
			
            /* check if each node is in union with other node with same value */
            // create frequency map of parent to count number of nodes of same value in each set
            Map<Integer, Integer> freq = new HashMap<>();
            for (int node : curValNodes) {
                int parent = uf.find(node);
                freq.put(parent, freq.getOrDefault(parent, 0) + 1);
            }
            // apply 排列组合 C(n, 2) to find sum of good paths
            for (int parent : freq.keySet()) {
                int frequency = freq.get(parent);
                sum += (frequency * (frequency - 1)) / 2;
            }
            
            curVal = tm.higherEntry(curVal.getKey());
        }
        return sum;
    }
}

public class UnionFind {
    int[] parents_;
    int[] rank;
    
    public UnionFind(int n) {
        parents_ = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parents_[i] = i;
            rank[i] = 1;
        }
    }
    
    public int find(int u) {
        while (parents_[u] != u) {
            parents_[u] = parents_[parents_[u]];
            u = parents_[u];
        }
        return u;
    }

    public void union(int x, int y) {        
        int xp = find(x);
        int yp = find(y);
        if (xp == yp) return;

        if (rank[xp] < rank[yp]) {
            parents_[xp] = yp;
        } else {
            parents_[yp] = parents_[xp];
            if (rank[xp] == rank[yp]) rank[xp]++;
        }
    }
}
