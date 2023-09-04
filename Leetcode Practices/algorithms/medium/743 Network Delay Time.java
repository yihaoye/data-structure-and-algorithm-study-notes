/**
You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.

We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.

 

Example 1:


Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
Example 2:

Input: times = [[1,2,1]], n = 2, k = 1
Output: 1
Example 3:

Input: times = [[1,2,1]], n = 2, k = 2
Output: -1
 

Constraints:

1 <= k <= n <= 100
1 <= times.length <= 6000
times[i].length == 3
1 <= ui, vi <= n
ui != vi
0 <= wi <= 100
All the pairs (ui, vi) are unique. (i.e., no multiple edges.)
 */



// My Solution:
class Solution {
    // Dijkstra - https://leetcode.cn/problems/network-delay-time/solution/gong-shui-san-xie-yi-ti-wu-jie-wu-chong-oghpz/
    // 时间复杂度：O((E+V)*logV)，空间复杂度：O(E+V)

    public int networkDelayTime(int[][] times, int n, int k) {
        /* Dijkstra 使用模版 */
        Graph graph = new Graph();
        for (int[] time : times) graph.addEdge(time[0], time[1], time[2]);
        graph.dijkstra(k);
        Map<Integer, Integer> dist = graph.getDist(k);
        /*******************/

        int res = 0;
        for (int i=1; i<=n; i++) res = Math.max(res, dist.getOrDefault(i, Integer.MAX_VALUE));
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public class Graph { // Dijkstra 算法模版
        public Map<Integer, Map<Integer, Integer>> adj; // 邻接表 <from, <to, weight>>，使用 Map 而非 List 可以更好地适配其他数据类型的节点，比如字符串等等
        public Map<Integer, Map<Integer, Integer>> dists; // <from, <to, min_dist>> dists[from][to] 即 from 到 to 的最短距离/时间，一开始全部设为最大值，每次调用 dijkstra(src) 后可以缓存以提高性能，但是如果有新的 addEdge() 调用后，所有 src 都应该重新 dijkstra(src) 而非直接调用 getDist(src)

        public Graph() {
            adj = new HashMap<>();
            dists = new HashMap<>();
        }

        public void addEdge(int u, int v, int w) { // 添加边 (from, to, weight)
            adj.computeIfAbsent(u, k -> new HashMap<>()).put(v, w); // 如果是无向图，需要双向添加
            dists.putIfAbsent(u, null); dists.putIfAbsent(v, null);
        }

        public void dijkstra(int src) { // 使用 Dijkstra 算法从 src 遍历全图（最小堆优化，贪心策略）以计算 dists[src]
            Map<Integer, Integer> srcDist = new HashMap<>();
            dists.put(src, srcDist); // 初始化以及清理旧数据
            for (int node : dists.keySet()) srcDist.put(node, Integer.MAX_VALUE);
            srcDist.put(src, 0); // dists[src][src] = 0

            Set<Integer> visited = new HashSet<>();
            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            minHeap.offer(new int[]{src, 0}); // first next 'to' is src itself

            while (!minHeap.isEmpty()) {
                int[] curr = minHeap.poll(); // 确保总是从距离 src 最近的节点开始扩展，从而找到最短路径。
                int from = curr[0];
                if (!visited.add(from) || !adj.containsKey(from)) continue;

                for (Map.Entry<Integer, Integer> entry : adj.get(from).entrySet()) {
                    int to = entry.getKey();
                    int wei = entry.getValue(); // weight of from..to

                    if (srcDist.get(from) + wei < srcDist.get(to)) {
                        srcDist.put(to, srcDist.get(from) + wei);
                        minHeap.offer(new int[]{to, srcDist.get(to)}); // [to, src_to_cur_best_dist]
                    }
                }
            }
        }

        public Map<Integer, Integer> getDist(int src) { // src 到所有其他结点的最短距离 dists[src]
            return dists.getOrDefault(src, new HashMap<>());
        }
    }
}



// Other's Solution:
class Solution {
    // Dijkstra - https://leetcode.cn/problems/network-delay-time/solution/gong-shui-san-xie-yi-ti-wu-jie-wu-chong-oghpz/
    // 时间复杂度：O(mlogn+n)，空间复杂度：O(m)
    
    // 图结点
    private int N = 110;
    private int M = 6010;

    // 邻接表
    // 存储与 结点v 直接相连的边编号集合(链表)的头结点(边编号)
    private int[] head = new int[N];

    // 存储某一条边指向的结点, 用于访问编号为idx的边所指向的结点
    private int[] edge = new int[M];

    // 由于以链表的形式存储边, 故可根据与 结点v 直接相连的边集合(链表)的头结点在 nextEdge数组 中找到下一条边
    // 存储 链表中边编号为idx的边指向/连接的下一条边
    // this.nextEdge[idx]: 保存边编号为idx边连接的下一条边
    private int[] nextEdge = new int[M];

    // 记录某条边的权重
    private int[] weight = new int[M];

    // 无穷大上界
    private int INF = 0x3f3f3f3f;

    // 结点数
    private int n;

    // 源点
    private int src;

    // 用于对边进行编号: 会自动初始化 赋0
    private int idx = 0;

    /**
     * 邻接表 加边操作
     * @param srcV: 源点
     * @param desV: 目标点
     * @param weight: 边权重
     */
    private void add(int srcV, int desV, int weight) {
        // 由源点(srcV)出发 经 编号为idx的边 指向 目标点(desV)
        // 新建一条编号为idx, 指向desV结点的边
        this.edge[idx] = desV;

        // 头插法
        this.nextEdge[idx] = this.head[srcV];
        this.head[srcV] = idx;

        // 将编号为idx的边的权值 赋为 weight
        this.weight[idx] = weight;
        idx++;
    }

    // /**
    //  * 遍历 结点src 与其相连的所有边
    //  * @param src
    //  */
    // private void traversal(int src) {
    //     for (int idx = head[src]; idx != -1; idx = this.nextEdge[idx]) {
    //         int des = this.edge[idx];
    //         System.out.println("结点" + src + " -> 结点" + des);
    //         System.out.println("编号为" + idx + "的边, 权重为: " + weight[idx]);
    //     }
    // }

    public int networkDelayTime(int[][] times, int n, int k) {
        this.n = n;
        this.src = k;

        Arrays.fill(head, -1);

        for (int[] time : times) {
            int src = time[0];
            int des = time[1];
            int weight = time[2];
            this.add(src, des, weight);
        }

        // 最短路径
        this.dijkstraOptimizedByHeap();

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, dist[i]);
        }

        return ans == INF ? -1 : ans;
    }

    // dist[v] = m 表示从[源点/起点 src/k] 到 结点v 的最短距离为 m
    private int[] dist = new int[N];

    // 记录结点的访问状态
    private boolean[] visited = new boolean[N];

    /**
     * Dijkstra算法: 单源最短路径算法
     *
     * 堆优化邻接表实现
     */
    private void dijkstraOptimizedByHeap() {
        // 起始先将所有的点标记为「未更新」和「距离为正无穷」
        Arrays.fill(dist, INF);
        Arrays.fill(visited, false);

        // 只有起点最短距离为 0
        dist[this.src] = 0;

        // 使用升序排列最小堆存储所有可用于更新的点
        // 以 (点编号, 到起点的距离) 进行存储，优先弹出「最短距离」较小的点
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        // new int[]{结点v, 与源点src/k的最短距离}
        pq.add(new int[]{this.src, 0});

        while (!pq.isEmpty()) {
            // 每次抛出与源点(src)最短距离最小的结点
            int[] poll = pq.poll();

            int v = poll[0];
            int distance = poll[1];

            // 已访问, 则跳过
            if (visited[v]) continue;

            // 将 结点v 置为已访问状态
            visited[v] = true;

            // 使用该 结点v 更新其他点的「最短距离」
            for (int idx = head[v]; idx != -1; idx = nextEdge[idx]) {
                int des = edge[idx];

                // 若源点(src/k) -> 结点v 的最短距离 + 结点v -> 结点des 的距离 < 源点(src/k) -> 结点des的最短距离
                if (dist[v] + weight[idx] < dist[des]) {
                    // 更新 结点des 的最短距离
                    dist[des] = dist[v] + weight[idx];
                    pq.add(new int[]{des, dist[des]});
                }
            }
        }
    }
}



// Other's Solution:
class Solution {
    // 朴素 Dijkstra（无堆优化）- https://leetcode.cn/problems/network-delay-time/solution/gong-shui-san-xie-yi-ti-wu-jie-wu-chong-oghpz/
    // Time: O(n^2), Space: O(n^2)

    int N = 110, M = 6010;
    // 邻接矩阵数组：w[a][b] = c 代表从 a 到 b 有权重为 c 的边
    int[][] w = new int[N][N];
    // dist[x] = y 代表从「源点/起点」到 x 的最短距离为 y
    int[] dist = new int[N];
    // 记录哪些点已经被更新过
    boolean[] vis = new boolean[N];
    int INF = 0x3f3f3f3f;
    int n, k;

    public int networkDelayTime(int[][] ts, int _n, int _k) {
        n = _n; k = _k;
        // 初始化邻接矩阵
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                w[i][j] = w[j][i] = i == j ? 0 : INF;
            }
        }
        // 存图
        for (int[] t : ts) {
            int u = t[0], v = t[1], c = t[2];
            w[u][v] = c;
        }
        // 最短路
        dijkstra();
        // 遍历答案
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, dist[i]);
        }
        return ans > INF / 2 ? -1 : ans;
    }

    public void dijkstra() {
        // 起始先将所有的点标记为「未更新」和「距离为正无穷」
        Arrays.fill(vis, false);
        Arrays.fill(dist, INF);
        // 只有起点最短距离为 0
        dist[k] = 0;
        // 迭代 n 次
        for (int p = 1; p <= n; p++) {
            // 每次找到「最短距离最小」且「未被更新」的点 t
            int t = -1;
            for (int i = 1; i <= n; i++) {
                if (!vis[i] && (t == -1 || dist[i] < dist[t])) t = i;
            }
            // 标记点 t 为已更新
            vis[t] = true;
            // 用点 t 的「最小距离」更新其他点
            for (int i = 1; i <= n; i++) {
                dist[i] = Math.min(dist[i], dist[t] + w[t][i]);
            }
        }
    }
}
