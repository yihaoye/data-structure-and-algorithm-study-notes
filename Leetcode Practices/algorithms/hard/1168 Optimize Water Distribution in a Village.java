/**
There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.

For each house i, we can either build a well inside it directly with cost wells[i - 1] (note the -1 due to 0-indexing), or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes where each pipes[j] = [house1j, house2j, costj] represents the cost to connect house1j and house2j together using a pipe. Connections are bidirectional, and there could be multiple valid connections between the same two houses with different costs.

Return the minimum total cost to supply water to all houses.

 

Example 1:


Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation: The image shows the costs of connecting houses using pipes.
The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
Example 2:

Input: n = 2, wells = [1,1], pipes = [[1,2,1],[1,2,2]]
Output: 2
Explanation: We can supply water with cost two using one of the three options:
Option 1:
  - Build a well inside house 1 with cost 1.
  - Build a well inside house 2 with cost 1.
The total cost will be 2.
Option 2:
  - Build a well inside house 1 with cost 1.
  - Connect house 2 with house 1 with cost 1.
The total cost will be 2.
Option 3:
  - Build a well inside house 2 with cost 1.
  - Connect house 1 with house 2 with cost 1.
The total cost will be 2.
Note that we can connect houses 1 and 2 with cost 1 or with cost 2 but we will always choose the cheapest option. 
 

Constraints:

2 <= n <= 10^4
wells.length == n
0 <= wells[i] <= 10^5
1 <= pipes.length <= 10^4
pipes[j].length == 3
1 <= house1j, house2j <= n
0 <= costj <= 10^5
house1j != house2j
 */



// My Solution:
class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        // 至少有一间房需要自己挖井，假设最优方案为 m 间房自己挖井，剩下的房都需要直接或间接地接到其中一部分或全部的房
        // 自己挖井的可以转换一下变成和虚拟的 n+1 房连接，权重为挖井的 cost，然后把它们加进 pipes，然后 MST
        // 通过 nested hashmap 存储输入数据并进行最优保留
        
        /* init */
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] pipe : pipes) {
            graph.computeIfAbsent(pipe[0], x -> new HashMap<>()).putIfAbsent(pipe[1], Integer.MAX_VALUE);
            graph.computeIfAbsent(pipe[1], x -> new HashMap<>()).putIfAbsent(pipe[0], Integer.MAX_VALUE);
            
            graph.get(pipe[0]).put(pipe[1], Math.min(graph.get(pipe[0]).get(pipe[1]), pipe[2]));
            graph.get(pipe[1]).put(pipe[0], Math.min(graph.get(pipe[1]).get(pipe[0]), pipe[2]));
        }
        for (int i=0; i<wells.length; i++) {
            graph.computeIfAbsent(n+1, x -> new HashMap<>()).putIfAbsent(i+1, wells[i]);
            graph.computeIfAbsent(i+1, x -> new HashMap<>()).putIfAbsent(n+1, wells[i]);
        }
        
        /* process */
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int res = 0;
        Set<Integer> visited = new HashSet<>();
        pq.offer(new int[]{n+1, 0}); // [startNode, startWeight]
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int curNode = edge[0]; int curWeight = edge[1];
            if (visited.contains(curNode)) continue;
            res += curWeight;
            visited.add(curNode);
            for (Map.Entry<Integer, Integer> nxtEdge : graph.get(curNode).entrySet()) {
                if (visited.contains(nxtEdge.getKey())) continue;
                pq.offer(new int[]{nxtEdge.getKey(), nxtEdge.getValue()});
            }
        }
        
        return res;
    }
}
