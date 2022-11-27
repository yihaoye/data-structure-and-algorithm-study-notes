/**
There are n cities labeled from 1 to n. You are given the integer n and an array connections where connections[i] = [xi, yi, costi] indicates that the cost of connecting city xi and city yi (bidirectional connection) is costi.

Return the minimum cost to connect all the n cities such that there is at least one path between each pair of cities. If it is impossible to connect all the n cities, return -1,

The cost is the sum of the connections' costs used.

 

Example 1:
https://assets.leetcode.com/uploads/2019/04/20/1314_ex2.png

Input: n = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
Output: 6
Explanation: Choosing any 2 edges will connect all cities so we choose the minimum 2.

Example 2:
https://assets.leetcode.com/uploads/2019/04/20/1314_ex1.png

Input: n = 4, connections = [[1,2,3],[3,4,4]]
Output: -1
Explanation: There is no way to connect all cities even if all edges are used.
 

Constraints:

1 <= n <= 10^4
1 <= connections.length <= 10^4
connections[i].length == 3
1 <= xi, yi <= n
xi != yi
0 <= costi <= 10^5
 */



// My Solution 1 (MST - Prim Algorithm):
class Solution {
    public int minimumCost(int n, int[][] connections) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] conn : connections) {
            graph.computeIfAbsent(conn[0], x -> new ArrayList<>()).add(new int[]{conn[1], conn[2]});
            graph.computeIfAbsent(conn[1], x -> new ArrayList<>()).add(new int[]{conn[0], conn[2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int cost = 0;
        Set<Integer> visited = new HashSet<>();
        pq.offer(new int[]{1, 0});
        while (!pq.isEmpty()) {
            int[] conn = pq.poll();
            int curNode = conn[0]; int curWeight = conn[1];
            if (visited.contains(curNode)) continue;
            cost += curWeight;
            visited.add(curNode);
            for (int[] nxtConn : graph.get(curNode)) {
                if (visited.contains(nxtConn[0])) continue;
                pq.offer(nxtConn);
            }
        }
        if (visited.size() != n) return -1;
        return cost;
    }
}



// My Solution 2 (MST - Kruskal Algorithm):
class Solution {
    public int minimumCost(int n, int[][] connections) {
        Arrays.sort(connections, (a, b) -> a[2] - b[2]);        
        UnionFindSet ufs = new UnionFindSet(n);
        int cost = 0, unionCnt = 0;
        for (int[] conn : connections) {
            int u = conn[0] - 1; int v = conn[1] - 1; int w = conn[2];
            int ru = ufs.find(u); int rv = ufs.find(v);
            if (ru == rv) continue;
            ufs.union(ru, rv);
            unionCnt++;
            cost += w;
        }
        if (unionCnt != n-1) return -1;
        
        return cost;
    }
    
    /* 并查集 */
    class UnionFindSet {
        private int[] parents_;
        private int[] ranks_;

        public UnionFindSet(int n) {
            parents_ = new int[n + 1];
            ranks_ = new int[n + 1];
            for (int i = 0; i < parents_.length; ++i) {
                parents_[i] = i;
                ranks_[i] = 1;
            }
        }

        public boolean union(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu == pv) return false;

            if (ranks_[pv] > ranks_[pu])
                parents_[pu] = pv;
            else if (ranks_[pu] > ranks_[pv])
                parents_[pv] = pu;
            else {
                parents_[pv] = pu;
                ranks_[pu] += 1;
            }

            return true;
        }

        public int find(int u) {
            while (parents_[u] != u) {
                parents_[u] = parents_[parents_[u]];
                u = parents_[u];
            }
            return u;
        }
    }
}
