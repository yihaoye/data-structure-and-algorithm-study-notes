/*
There are a total of numCourses courses you have to take, labeled from 0 to numCourses-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

 

Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
 

Constraints:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.
1 <= numCourses <= 10^5
*/



// My Solution:
class Solution {
    Map<Integer, List<Integer>> map = new HashMap<>();// Map<Node, List<others it points to>>
    Set<Integer> visited = new HashSet<>(); // Set<Node> visited
    Set<Integer> mem = new HashSet<>(); // Set<Node> mem

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        for (int[] prereq : prerequisites) { // init
            map.computeIfAbsent(prereq[1], k -> new ArrayList<>()).add(prereq[0]);
        }

        for (int i=0; i<numCourses; i++) {
            if (!map.containsKey(i)) continue;
            if (!dfs(i)) return false;
        }
        return true;
    }

    public boolean dfs(int node) {
        if (mem.contains(node)) return true;
        if (visited.contains(node)) return false;
        visited.add(node);
        for (int next : map.getOrDefault(node, new ArrayList<>())) {
            if (!dfs(next)) return false;
        }
        visited.remove(node);
        mem.add(node);
        return true;
    }
}



// Other's Solution:
// http://zxi.mytechroad.com/blog/graph/leetcode-207-course-schedule/
// HashMap is slower than ArrayList in this problem.
class Solution {    
    public boolean canFinish(int numCourses, int[][] prerequisites) {        
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        
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
            if (dfs(i, graph, visited)) return false;
        
        return true;
    }
    
    private boolean dfs(int curr, ArrayList<ArrayList<Integer>> graph, int[] visited) {
        if (visited[curr] == 1) return true;
        if (visited[curr] == 2) return false;
        
        visited[curr] = 1;
                
        for (int next : graph.get(curr))
            if (dfs(next, graph, visited)) return true;
        
        visited[curr] = 2;
        return false;
    }
}