/**
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

 

Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
Example 2:

Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
Example 3:

Input: numCourses = 1, prerequisites = []
Output: [0]
 

Constraints:

1 <= numCourses <= 2000
0 <= prerequisites.length <= numCourses * (numCourses - 1)
prerequisites[i].length == 2
0 <= ai, bi < numCourses
ai != bi
All the pairs [ai, bi] are distinct.
 */



// My Solution:
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 拓扑排序（DFS 解法）
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        ArrayList<Integer> sortedList = new ArrayList<>();

        for (int i = 0; i < numCourses; ++i)
            graph.add(new ArrayList<Integer>());

        for (int i = 0; i < prerequisites.length; ++i) {
            int course = prerequisites[i][0];
            int prerequisite = prerequisites[i][1];            
            graph.get(course).add(prerequisite);
        }

        // int[] visited -- states: 0 == unkonwn, 1 == visiting, 2 == visited
        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; ++i)
            if (dfs(i, graph, visited, sortedList)) return new int[]{}; // empty list means fail with circle

        return sortedList.stream().mapToInt(i->i).toArray();
    }
    
    private boolean dfs(int curr, ArrayList<ArrayList<Integer>> graph, int[] visited, ArrayList<Integer> sortedList) {
        if (visited[curr] == 1) return true;
        if (visited[curr] == 2) return false;

        visited[curr] = 1;

        for (int next : graph.get(curr))
            if (dfs(next, graph, visited, sortedList)) return true;

        sortedList.add(curr);
        visited[curr] = 2;
        return false;
    }
}



// My Solution 2:
class Solution {
    Map<Integer, Set<Integer>> indegree; // <itemA, <pre-requestA-of-itemA, pre-requestB-of-itemA, ...>>
	Map<Integer, Set<Integer>> outdegree; // <pre-requestA, <itemA-relys-pre-requestA, itemB-relys-pre-requestA, ...>>
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 拓扑排序（卡恩算法）
        indegree = new HashMap<>();
		outdegree = new HashMap<>();

		for (int[] edge : prerequisites) {
			outdegree.computeIfAbsent(edge[1], x -> new HashSet<>()).add(edge[0]);
			outdegree.computeIfAbsent(edge[0], x -> new HashSet<>());

			indegree.computeIfAbsent(edge[0], x -> new HashSet<>()).add(edge[1]);
			indegree.computeIfAbsent(edge[1], x -> new HashSet<>());
		}
        while (numCourses-- > 0) {
            outdegree.computeIfAbsent(numCourses, x -> new HashSet<>());
            indegree.computeIfAbsent(numCourses, x -> new HashSet<>());
        }
        
        List<Integer> sortedList = topoSort();
        return sortedList.stream().mapToInt(i->i).toArray();
    }

	public int findAvailableNode() { // available node means node has no pre-request
		for (Map.Entry<Integer, Set<Integer>> entry : indegree.entrySet()) {
			if (entry.getValue().size() == 0) return entry.getKey();
		}
		return -1;
	}

	public boolean removeNode(int node) { // normally this is used for remove pre-request
		if (!outdegree.containsKey(node)) return false;
		Set<Integer> relyers = outdegree.get(node);
		for (int relyer : relyers) {
			indegree.get(relyer).remove(node);
		}
		outdegree.remove(node);
		indegree.remove(node);
		return true;
	}

	public List<Integer> topoSort() {
		List<Integer> sortedList = new ArrayList<>();
		while (indegree.size() > 0) {
			int nextAvailable = findAvailableNode();
			if (nextAvailable == -1) return new ArrayList<>(); // Graph has at least one cycle. Topological sorting is not possible
			sortedList.add(nextAvailable);
			removeNode(nextAvailable);
		}

		return sortedList;
	}
}



// My Solution 3:
class Solution {
    int[] indegree; // 一维整数数组，元素里记录的是 itemA 的 pre-request 个数
	Map<Integer, Set<Integer>> outdegree; // <pre-requestA, <itemA-relys-pre-requestA, itemB-relys-pre-requestA, ...>>
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
		indegree = new int[numCourses];
		outdegree = new HashMap<>();

		for (int[] edge : prerequisites) {
			indegree[edge[0]]++;

			outdegree.computeIfAbsent(edge[1], x -> new HashSet<>()).add(edge[0]);
			outdegree.computeIfAbsent(edge[0], x -> new HashSet<>());
		}
        
        List<Integer> sortedList = topoSort();
        return sortedList.stream().mapToInt(i->i).toArray();
    }

	private List<Integer> topoSort() {
		List<Integer> sortedList = new ArrayList<>();

		Queue<Integer> queue = new LinkedList<>();
		for (int i=0; i<indegree.length; i++)
			if (indegree[i] == 0) queue.offer(i);

		while (!queue.isEmpty()) {
			int cur = queue.poll();
			sortedList.add(cur);
			for (int nei : outdegree.getOrDefault(cur, new HashSet<>()))
				if (--indegree[nei] == 0) queue.offer(nei);
		}
		if (sortedList.size() != indegree.length) return new ArrayList<>();

		return sortedList;
	}
}
