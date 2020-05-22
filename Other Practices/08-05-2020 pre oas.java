// https://leetcode.com/discuss/interview-question/411357/
// Other's Solution (BFS):
public class Solution {
	public int humanDays(int[][] matrix) {
		Queue<int[]> q = new LinkedList<>();
    	int target = matrix.length * matrix[0].length;
    	int cnt = 0, res = 0;
    	for(int i=0;i<matrix.length;i++) {
    		for(int j=0;j<matrix[0].length;j++) {
    			if(matrix[i][j] == 1) {
    				q.offer(new int[] {i,j});
    				cnt++;
    			}
    		}
    	}
    	int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    	while(!q.isEmpty()) {
    		int size = q.size();
    		if(cnt == target)
    			return res;
    		for(int i=0;i<size;i++) {
    			int[] cur = q.poll();
    			for(int[] dir : dirs) {
    				int ni = cur[0] + dir[0];
    				int nj = cur[1] + dir[1];
    				if(ni >=0 && ni < matrix.length && nj >=0 && nj < matrix[0].length && matrix[ni][nj] == 0) {
    					cnt++;
    					q.offer(new int[] {ni, nj});
    					matrix[ni][nj] = 1;
    				}
    			}
    		}
    		res++;
    	}
    	return -1;
	}
}



// https://leetcode.com/discuss/interview-question/542597/Amazon-or-OA-2020-or-Top-K-Frequently-Mentioned-Keywords
// Other's Solution:
public class Solution {
	public static void main(String[] args) {
		int k1 = 2;
		String[] keywords1 = { "anacell", "cetracular", "betacellular" };
		String[] reviews1 = { "Anacell provides the best services in the city", "betacellular has awesome services",
				"Best services provided by anacell, everyone should use anacell", };
		int k2 = 2;
		String[] keywords2 = { "anacell", "betacellular", "cetracular", "deltacellular", "eurocell" };
		String[] reviews2 = { "I love anacell Best services; Best services provided by anacell",
				"betacellular has great services", "deltacellular provides much better services than betacellular",
				"cetracular is worse than anacell", "Betacellular is better than deltacellular.", };
		System.out.println(solve(k1, keywords1, reviews1));
		System.out.println(solve(k2, keywords2, reviews2));
	}

	private static List<String> solve(int k, String[] keywords, String[] reviews) {
		List<String> res = new ArrayList<>();
		Set<String> set = new HashSet<>(Arrays.asList(keywords));
		Map<String, Integer> map = new HashMap<>();
		for(String r : reviews) {
			String[] strs = r.split("\\W");
			Set<String> added = new HashSet<>();
			for(String s : strs) {
				s = s.toLowerCase();
				if(set.contains(s) && !added.contains(s)) {
					map.put(s, map.getOrDefault(s, 0) + 1);
					added.add(s);
				}
			}
		}
		Queue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>((a, b)->a.getValue() == b.getValue() ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue());
		maxHeap.addAll(map.entrySet());
		while(!maxHeap.isEmpty() && k-- > 0) {
			res.add(maxHeap.poll().getKey());
		}
		return res;
	}
}



// https://leetcode.com/discuss/interview-question/373202
// Other's Solution:
public class Solution {
	public List<int[]> getPairs(List<int[]> a, List<int[]> b, int target) {
		Collections.sort(a, (i,j) -> i[1] - j[1]);
		Collections.sort(b, (i,j) -> i[1] - j[1]);
		List<int[]> result = new ArrayList<>();
		int max = Integer.MIN_VALUE;
		int m = a.size();
		int n = b.size();
		int i =0;
		int j =n-1;
		while(i < m && j >= 0) {
			int sum = a.get(i)[1] + b.get(j)[1];
			if(sum > target) {
				--j;
			} else {
				if(max <= sum) {
					if(max < sum) {
						max = sum;
						result.clear();
					}
					result.add(new int[]{a.get(i)[0], b.get(j)[0]});
					int index = j-1;
					while(index >=0 && b.get(index)[1] == b.get(index+1)[1]) {
						result.add(new int[]{a.get(i)[0], b.get(index--)[0]});
					}
				}
				++i;
			}
		}
		return result;
	}
}



// https://leetcode.com/discuss/interview-question/436073/
// Other's Solution:
public class Solution {
	
	static int time = 0;

	public static void main(String[] args) {
		int numRouters1 = 7; // numNodes
		int numLinks1 = 7; // numEdges
		int[][] links1 = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4}}; // edges
		System.out.println(getCriticalNodes(links1, numLinks1, numRouters1));
	}
	
	private static List<Integer> getCriticalNodes(int[][] links, int numLinks, int numRouters) {
		time = 0;
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for(int i=0; i<numRouters; i++) {
			map.put(i, new HashSet<>());
		}
		for(int[] link : links) {
			map.get(link[0]).add(link[1]);
			map.get(link[1]).add(link[0]);
		}
		Set<Integer> set = new HashSet<>();
		int[] low = new int[numRouters]; // low time of vertex: minimum of all the visit time of all the adjacent vertices which are reachable from the given vertex in the DFS traversal, i.e. back edge vertex (可绕过现有路径任何节点所到达的最原始的祖先/根) (https://www.youtube.com/watch?v=jFZsDDB0-vo)
		int[] visit = new int[numRouters]; // visit for visited order in the program (数据将基于首先哪个节点被起始访问而不同，但是无论是起始于哪个节点，最后整个算法的结果是一样的)
		int[] parent = new int[numRouters];  // parent of given vertex
		Arrays.fill(visit, -1);
		Arrays.fill(parent, -1);
		for(int i=0; i<numRouters; i++) {
			if(visit[i] == -1)
				dfs(map, low, visit, parent, i, set);
		}
		return new ArrayList<>(set);
	}
	
	private static void dfs(Map<Integer, Set<Integer>> map, int[] low, int[] visit, int[] parent, int cur, Set<Integer> res) {
		int children = 0;
		visit[cur] = low[cur] = ++time;
		for(int nei : map.get(cur)) { // nei stand for neighbour
			if(visit[nei] == -1) {
				children++;
				parent[nei] = cur;
				dfs(map, low, visit, parent, nei, res);
				low[cur] = Math.min(low[cur], low[nei]);
				if((parent[cur] == -1 && children > 1) || (parent[cur] != -1 && low[nei] >= visit[cur]))
					res.add(cur);
			}
			else if(nei != parent[cur]) // For avoiding a circle in between 2 points. Also the visited node may have a lower discovery count then the current low.
				low[cur] = Math.min(low[cur], visit[nei]);
		}
	}
}
// 简易教程：https://www.youtube.com/watch?v=jFZsDDB0-vo
// Related: https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
/*
How to find all articulation points in a given graph?
A simple approach is to one by one remove all vertices and see if removal of a vertex causes disconnected graph. Following are steps of simple approach for connected graph.

1) For every vertex v, do following
	a) Remove v from graph
	b) See if the graph remains connected (We can either use BFS or DFS)
	c) Add v back to the graph
Time complexity of above method is O(V*(V+E)) for a graph represented using adjacency list. Can we do better?

A O(V+E) algorithm to find all Articulation Points (APs)
The idea is to use DFS (Depth First Search). In DFS, we follow vertices in tree form called DFS tree. In DFS tree, a vertex u is parent of another vertex v, if v is discovered by u (obviously v is an adjacent of u in graph). In DFS tree, a vertex u is articulation point if one of the following two conditions is true.
1) u is root of DFS tree and it has at least two children.
2) u is not root of DFS tree and it has a child v such that no vertex in subtree rooted with v has a back edge to one of the ancestors (in DFS tree) of u.
Following figure shows same points as above with one additional point that a leaf in DFS Tree can never be an articulation point.
![](https://media.geeksforgeeks.org/wp-content/uploads/ArticulationPoints.png)
将无向连通图从某个顶点开始进行DFS遍历，遍历会得到一个DFS树(深度优先生成树)
判断顶点u是否是关节点的原则:
1、如果u是整颗DFS树的根，如果u至少有两个子女,那么u必然是关节点。
2、DFS树中叶节点不是关节点
3、非根顶点u(不整颗DFS树的根)不是关节点的充要条件是：它的任一子孙都可以沿某条路径绕过顶点u达到u的任一祖先w (w!=u),u不属于该路径(这条路径包括回边)
ps:（回边是原本图中有的,在DFS数里没有的)
针对判定原则里的1我们再定义一个变量children用于记录当前顶点的子女数，如果当前顶点是根结点(整棵树的根)，再定一个数组parent,parent[u]表示u的双亲是谁。
*/