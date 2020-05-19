/*
There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any other server directly or indirectly through the network.

A critical connection is a connection that, if removed, will make some server unable to reach some other server.

Return all critical connections in the network in any order.

 

Example 1:
![](https://assets.leetcode.com/uploads/2019/09/03/1537_ex1_2.png)


Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
Output: [[1,3]]
Explanation: [[3,1]] is also accepted.
 

Constraints:

1 <= n <= 10^5
n-1 <= connections.length <= 10^5
connections[i][0] != connections[i][1]
There are no repeated connections.
*/



// Other's Solution:
// similar to https://leetcode.com/discuss/interview-question/436073/ (or in this repository -> Other Practices -> 2020 amz oa -> getCriticalNodes)
class Solution {
    static int time = 0;
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
            Map<Integer, Set<Integer>> map = new HashMap<>();
            for(int i=0; i<n; i++) {
                map.put(i, new HashSet<>());
            }
            for(List<Integer> link : connections) {
                map.get(link.get(0)).add(link.get(1));
                map.get(link.get(1)).add(link.get(0));
            }
            ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
            int[] low = new int[n]; // low time of vertex: minimum of all the visit time of all the adjacent vertices which are reachable from the given vertex in the DFS traversal, i.e. back edge vertex (https://www.youtube.com/watch?v=jFZsDDB0-vo)
            int[] visit = new int[n]; // visit for visited order in the program (数据将基于首先哪个节点被起始访问而不同，但是无论是起始于哪个节点，最后整个算法的结果是一样的)
            int[] parent = new int[n];  // parent of given vertex
            Arrays.fill(visit, -1);
            Arrays.fill(parent, -1);
            for(int i=0; i<n; i++) {
                if(visit[i] == -1)
                    dfs(map, low, visit, parent, i, res);
            }
            
            return res;
    }

    private static void dfs(Map<Integer, Set<Integer>> map, int[] low, int[] visit, int[] parent, int cur, List<List<Integer>> res) {
        int children = 0;
        visit[cur] = low[cur] = ++time;
        for(int nei : map.get(cur)) { // nei stand for neighbour
            if(visit[nei] == -1) {
                children++;
                parent[nei] = cur;
                dfs(map, low, visit, parent, nei, res);
                low[cur] = Math.min(low[cur], low[nei]);
                if (parent[cur] != -1 && low[nei] > visit[cur])
                    res.add(new ArrayList<Integer>(Arrays.asList(cur, nei)));
                else if (low[nei] != visit[cur] && parent[cur] == -1 && map.get(cur).size() > 1)
                    res.add(new ArrayList<Integer>(Arrays.asList(cur, nei)));
            }
            else if(nei != parent[cur]) // For avoiding a circle in between 2 points. Also the visited node may have a lower discovery count then the current low.
                low[cur] = Math.min(low[cur], visit[nei]);
        }
    }
}