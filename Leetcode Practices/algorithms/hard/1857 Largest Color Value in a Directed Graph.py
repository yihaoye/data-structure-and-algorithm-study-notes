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
class Solution:
    def largestPathValue(self, colors: str, edges: List[List[int]]) -> int:
        # 回溯法 + 记忆化搜索
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
            nodes[cur] = defaultdict(int)
            for nxt in outdegree[cur]:
                if nxt in visited: return -1
                visited.add(nxt)
                if self.dfs(nxt, visited, nodes, colors, outdegree) == -1: return -1
                visited.remove(nxt)
                for k, v in nodes[nxt].items(): nodes[cur][k] = max(nodes[cur][k], v)
            nodes[cur][colors[cur]] += 1
            return max(nodes[cur].values())



# Other's Solution:
class Solution:
    def largestPathValue(self, colors: str, edges: List[List[int]]) -> int:
        # 拓扑排序 + DP
        N = len(colors)                                     # Number of nodes
        incoming, Graph = [0] * N, [[] for _ in range(N)]   # Define count for incoming edges, graph
        for u, v in edges: incoming[v] += 1                 # Count incoming edges
        for u, v in edges: Graph[u].append(v)               # Code the graph
        queue = [u for u in range(N) if incoming[u] == 0]   # Populate stack with the nodes without incoming edges
        cnts = [[0] * 26 for _ in range(N)]                 # Max. colors along all the incoming paths for the node 

        for u in queue:                                     # While we have nodes to process
            cnts[u][ord(colors[u]) - ord('a')] += 1         # Increment the color of the node itself
            for v in Graph[u]:                              # For all outgoing edges of the node
                cnts[v] = list(map(max, cnts[v], cnts[u]))  # Update max. colors of the outgoing node 相当于 """for i in range(26): cnts[v][i] = max(cnts[v][i], cnts[u][i])"""
                incoming[v] -= 1                            # Decrement the counter of edges for the outgoing node
                if incoming[v] == 0: queue.append(v)        # If outgoing node has no more incoming edges, add to the stack

        return -1 if len(queue) < N else max(map(max, cnts))
