
# 最小生成树
[什么是生成树](./../SpanningTree/README.md)  

最小生成树是一副连通加权无向图中一棵权值最小的生成树。  
在一给定的无向图 G = (V, E) 中，(u, v) 代表连接顶点 u 与顶点 v 的边，而 w(u, v) 代表此边的权重，若存在 T 为 E 的子集且 (V, T) 为树，使得的 w(T) - 即该树的所有边的权重和最小，则此 T 为 G 的最小生成树。  
最小生成树其实是最小权重生成树的简称。  

https://www.youtube.com/watch?v=wmW8G8SrXDs  

![](./mst1.png)  

## Prim 算法
原理：贪心 + 优先队列  
Time: O(E*logV), Space: O(V+E)  

![](./mst2.png)  

```java
class Solution {
    public int mst(int n, int[][] edges) { // n nodes, edge [nodeA, nodeB, weight]
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(new int[]{edge[1], edge[2]});
            graph.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(new int[]{edge[0], edge[2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int res = 0;
        Set<Integer> visited = new HashSet<>();
        pq.offer(new int[]{0, 0}); // [startNode, startWeight]
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int curNode = edge[0]; int curWeight = edge[1];
            if (visited.contains(curNode)) continue;
            res += curWeight;
            visited.add(curNode);
            for (int[] nxtEdge : graph.get(curNode)) {
                if (visited.contains(nxtEdge[0])) continue;
                pq.offer(nxtEdge);
            }
        }
        if (visited.size() != n) return -1;
        return res;
    }
}
```

## Kruskal 算法
原理：并查集  
Time: O(E*logV), Space: O(V+E)  

![](./mst3.png)  

```java
class Solution {
    public int mst(int n, int[][] edges) { // n nodes, edge [nodeA, nodeB, weight]
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        UnionFindSet ufs = new UnionFindSet(n);
        int res = 0, unionCnt = 0;
        for (int[] edge : edges) {
            int u = edge[0]; int v = edge[1]; int w = edge[2];
            int ru = ufs.find(u); int rv = ufs.find(v);
            if (ru == rv) continue;
            ufs.union(ru, rv);
            unionCnt++;
            res += w;
        }
        if (unionCnt != n-1) return -1;
        
        return res;
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
```

## 例题
* [Leetcode Q1135](./../../../Leetcode%20Practices/algorithms/medium/1135%20Edgeecting%20Cities%20With%20Minimum%20Cost.java)
* [Leetcode Q1168](./../../../Leetcode%20Practices/algorithms/hard/1168%20Optimize%20Water%20Distribution%20in%20a%20Village.java)

## Chazelle 算法
确定性算法（非随机算法），时间复杂度为 `O(M·α(M,N))`，α 是反 Ackermann 函数，实际中几乎等于常数，但理论上并不是线性。  

核心思路可以概括：用一个允许出错的优先队列（Chazelle 发明的 soft heap，会故意损坏一部分键值的近似优先队列）来近似地找 MST，把被弄错的边先放到一边，再递归地修正。该算法结构非常复杂，常数也大，主要是理论上的突破，实际工程中几乎没人用。实践中大家还是用 Kruskal、Prim 或 Borůvka，它们的 `O(M*logN)` 对真实数据已经足够快。  

2026 年 11 月的[新论文](https://arxiv.org/abs/2611.31187)（预印本，尚未经同行评审）声称证明了 MST 存在确定性的 `O(M)` 线性时间算法。思路不是改进 Chazelle 算法，而是证明 MST 的确定性比较复杂度为线性，再借助 Pettie–Ramachandran（2002）的最优算法得出结论。  
