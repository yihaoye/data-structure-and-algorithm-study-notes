/*
There is an undirected tree with n nodes labeled from 1 to n. You are given the integer n and a 2D integer array edges of length n - 1, where edges[i] = [ui, vi] indicates that there is an edge between nodes ui and vi in the tree.

Return the number of valid paths in the tree.

A path (a, b) is valid if there exists exactly one prime number among the node labels in the path from a to b.

Note that:

The path (a, b) is a sequence of distinct nodes starting with node a and ending with node b such that every two adjacent nodes in the sequence share an edge in the tree.
Path (a, b) and path (b, a) are considered the same and counted only once.
 

Example 1:
https://assets.leetcode.com/uploads/2023/08/27/example1.png

Input: n = 5, edges = [[1,2],[1,3],[2,4],[2,5]]
Output: 4
Explanation: The pairs with exactly one prime number on the path between them are: 
- (1, 2) since the path from 1 to 2 contains prime number 2. 
- (1, 3) since the path from 1 to 3 contains prime number 3.
- (1, 4) since the path from 1 to 4 contains prime number 2.
- (2, 4) since the path from 2 to 4 contains prime number 2.
It can be shown that there are only 4 valid paths.

Example 2:
https://assets.leetcode.com/uploads/2023/08/27/example2.png

Input: n = 6, edges = [[1,2],[1,3],[2,4],[3,5],[3,6]]
Output: 6
Explanation: The pairs with exactly one prime number on the path between them are: 
- (1, 2) since the path from 1 to 2 contains prime number 2.
- (1, 3) since the path from 1 to 3 contains prime number 3.
- (1, 4) since the path from 1 to 4 contains prime number 2.
- (1, 6) since the path from 1 to 6 contains prime number 3.
- (2, 4) since the path from 2 to 4 contains prime number 2.
- (3, 6) since the path from 3 to 6 contains prime number 3.
It can be shown that there are only 6 valid paths.
 

Constraints:

1 <= n <= 10^5
edges.length == n - 1
edges[i].length == 2
1 <= ui, vi <= n
The input is generated such that edges represent a valid tree.
*/



// Other's Solution:
class Solution {
    private final int MX = 100001;
    private int[] lpf = new int[MX]; // 最小质因数

    public long countPaths(int n, int[][] edges) {
        // 并查集 - https://leetcode.com/problems/count-valid-paths-in-a-tree/solutions/4082791/python3-dsu/comments/2070363
        for (int i = 2; i < MX; i++) { // 埃氏筛法
            if (lpf[i] != 0) continue;
            for (int j = i; j < MX; j += i) lpf[j] = i;
        }

        // Union composite(not prime) nodes so that
        // they can be treated as one single node
        UF uf = new UF(n + 1);
        for (int[] edge : edges) {
            int u = edge[0]; int v = edge[1];
            // Condition checks that both u and v are not prime.
            if (lpf[u] != u && lpf[v] != v) uf.union(u, v);
        }

        // count[i] stores the number of composite nodes directly reachable from node `i`.
        int[] count = new int[n + 1];
        Arrays.fill(count, 1);
        long ans = 0;
        for (int[] edge : edges) {
            // Make sure one of the node is prime and other is composite.
            int u = edge[0]; int v = edge[1];
            if ((lpf[u] == u) ^ (lpf[v] == v)) { // must one prime one composite
                if (lpf[u] != u) {
                    int temp = u;
                    u = v;
                    v = temp;
                }
                // u is a prime node, v is a composite node.
                // Here, multiplication is used because we are calculating the 
                // the number of paths passing through node `u` where
                // only u is the prime number in the path.
                // We are mix and matching two composite branches here.
                ans += count[u] * uf.size(v);
                count[u] += uf.size(v);
            }
        }

        return ans;
    }

    public class UF {
        private int[] par;
        private int[] sz; // ranks_

        public UF(int N) {
            par = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                par[i] = i;
                sz[i] = 1;
            }
        }

        public int find(int x) {
            if (par[x] != x) {
                par[x] = find(par[x]);
            }
            return par[x];
        }

        public boolean union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return false;

            if (sz[px] < sz[py]) {
                int temp = px;
                px = py;
                py = temp;
            }
            par[py] = px;
            sz[px] += sz[py];
            return true;
        }

        public int size(int x) {
            return sz[find(x)];
        }
    }
}
