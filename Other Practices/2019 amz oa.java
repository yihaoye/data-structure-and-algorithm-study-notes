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
		int[] low = new int[numRouters];
		int[] ids = new int[numRouters]; // ids for IDs?
		int[] parent = new int[numRouters]; 
		Arrays.fill(ids, -1);
		Arrays.fill(parent, -1);
		for(int i=0; i<numRouters; i++) {
			if(ids[i] == -1)
				dfs(map, low, ids, parent, i, set);
		}
		return new ArrayList<>(set);
	}
	
	private static void dfs(Map<Integer, Set<Integer>> map, int[] low, int[] ids, int[] parent, int cur, Set<Integer> res) {
		int children = 0;
		ids[cur] = low[cur] = ++time;
		for(int nei : map.get(cur)) { // nei for neighbour
			if(ids[nei] == -1) {
				children++;
				parent[nei] = cur;
				dfs(map, low, ids, parent, nei, res);
				low[cur] = Math.min(low[cur], low[nei]);
				if((parent[cur] == -1 && children > 1) || (parent[cur] != -1 && low[nei] >= ids[cur]))
					res.add(cur);
			}
			else if(nei != parent[cur])
				low[cur] = Math.min(low[cur], ids[nei]);
		}
	}
}