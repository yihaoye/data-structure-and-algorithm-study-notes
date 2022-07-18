/**
There is an undirected graph with n nodes, where each node is numbered between 0 and n - 1. You are given a 2D array graph, where graph[u] is an array of nodes that node u is adjacent to. More formally, for each v in graph[u], there is an undirected edge between node u and node v. The graph has the following properties:

There are no self-edges (graph[u] does not contain u).
There are no parallel edges (graph[u] does not contain duplicate values).
If v is in graph[u], then u is in graph[v] (the graph is undirected).
The graph may not be connected, meaning there may be two nodes u and v such that there is no path between them.
A graph is bipartite if the nodes can be partitioned into two independent sets A and B such that every edge in the graph connects a node in set A and a node in set B.

Return true if and only if it is bipartite.

 

Example 1:
https://assets.leetcode.com/uploads/2020/10/21/bi2.jpg

Input: graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
Output: false
Explanation: There is no way to partition the nodes into two independent sets such that every edge connects a node in one and a node in the other.

Example 2:
https://assets.leetcode.com/uploads/2020/10/21/bi1.jpg

Input: graph = [[1,3],[0,2],[1,3],[0,2]]
Output: true
Explanation: We can partition the nodes into two sets: {0, 2} and {1, 3}.
 

Constraints:

graph.length == n
1 <= n <= 100
0 <= graph[u].length < n
0 <= graph[u][i] <= n - 1
graph[u] does not contain u.
All the values of graph[u] are unique.
If graph[u] contains v, then graph[v] contains u.
 */



// Other's Solution:
class Solution {
    public boolean isBipartite(int[][] graph) {
        // BFS - 使用图搜索算法从各个连通域的任一顶点开始遍历整个连通域，遍历的过程中用两种不同的颜色对顶点进行染色，相邻顶点染成相反的颜色。这个过程中倘若发现相邻的顶点被染成了相同的颜色，说明它不是二分图；反之，如果所有的连通域都染色成功，说明它是二分图。 - https://leetcode.cn/problems/is-graph-bipartite/solution/bfs-dfs-bing-cha-ji-san-chong-fang-fa-pan-duan-er-/
        // 时间复杂度 O(N+M), 空间复杂度 O(N)
        
        int[] visited = new int[graph.length]; // 定义 visited 数组，初始值为 0 表示未被访问，赋值为 1 或者 -1 表示两种不同的颜色。 
        Queue<Integer> queue = new LinkedList<>();
        
        for (int i = 0; i < graph.length; i++) { // 因为图中可能含有多个连通域，所以需要判断是否存在顶点未被访问，若存在则从它开始再进行一轮 bfs 染色
            if (visited[i] != 0) continue;
            
            queue.offer(i); // 之前连通域和该点不互联，所以需要作为一个新起点处理（因为和之前的连通域不互联，且该题背景为无向图，所以无论把该点设置为二分图的左右哪一侧都没有关系，所以下面直接一律无脑设置为 1 即可）
            visited[i] = 1;
            while (!queue.isEmpty()) {
                int v = queue.poll(); // 每出队一个顶点，将其所有邻接点染成相反的颜色并入队。
                for (int w : graph[v]) {
                    if (visited[w] == visited[v]) return false; // 如果当前顶点的某个邻接点已经被染过色了，且颜色和当前顶点相同，说明此无向图无法被正确染色，返回 false。

                    if (visited[w] == 0) {
                        visited[w] = -visited[v];
                        queue.offer(w);
                    }
                }          
            }
        }
        
        return true;
    }
}
