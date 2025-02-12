"""
There is a directed graph of n colored nodes and m edges. The nodes are numbered from 0 to n - 1.

You are given a string colors where colors[i] is a lowercase English letter representing the color of the ith node in this graph (0-indexed). You are also given a 2D array edges where edges[j] = [aj, bj] indicates that there is a directed edge from node aj to node bj.

A valid path in the graph is a sequence of nodes x1 -> x2 -> x3 -> ... -> xk such that there is a directed edge from xi to xi+1 for every 1 <= i < k. The color value of the path is the number of nodes that are colored the most frequently occurring color along that path.

Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.

 

Example 1:
https://assets.leetcode.com/uploads/2021/04/21/leet1.png

Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
Output: 3
Explanation: The path 0 -> 2 -> 3 -> 4 contains 3 nodes that are colored "a" (red in the above image).

Example 2:
https://assets.leetcode.com/uploads/2021/04/21/leet2.png

Input: colors = "a", edges = [[0,0]]
Output: -1
Explanation: There is a cycle from 0 to 0.
 

Constraints:

n == colors.length
m == edges.length
1 <= n <= 10^5
0 <= m <= 10^5
colors consists of lowercase English letters.
0 <= aj, bj < n
"""



# My Solution:
# 本题通常采用拓扑排序
class Solution:
    def largestPathValue(self, colors: str, edges: List[List[int]]) -> int:
        # 记忆化搜索
        outdegree = defaultdict(set)
        roots = set(range(len(colors)))

        for u, v in edges: # init graph
            outdegree[u].add(v)
            roots.discard(v)

        res = -1
        nodes = {}
        for root in roots: # iterate graph
            tmp = self.dfs(root, {root}, nodes, colors, outdegree)
            if tmp == -1: return -1
            res = max(res, tmp)
        if len(nodes) < len(colors): return -1 # cycle which cannot be access from roots
        return res

    def dfs(self, cur: int, visited: {}, nodes: {}, colors: str, outdegree: {}) -> int: # 贪心，记录每个节点的后续路径里每种颜色的最大值
            if cur in nodes: return max(nodes[cur].values())
            tmp = defaultdict(int)
            for nxt in outdegree[cur]:
                if nxt in visited: return -1
                visited.add(nxt)
                if self.dfs(nxt, visited, nodes, colors, outdegree) == -1: return -1
                visited.remove(nxt)
                for k, v in nodes[nxt].items(): tmp[k] = max(tmp[k], v)
            tmp[colors[cur]] = tmp[colors[cur]] + 1
            nodes[cur] = tmp
            return max(nodes[cur].values())
