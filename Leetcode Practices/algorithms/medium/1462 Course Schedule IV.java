/*
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course ai first if you want to take course bi.

For example, the pair [0, 1] indicates that you have to take course 0 before you can take course 1.
Prerequisites can also be indirect. If course a is a prerequisite of course b, and course b is a prerequisite of course c, then course a is a prerequisite of course c.

You are also given an array queries where queries[j] = [uj, vj]. For the jth query, you should answer whether course uj is a prerequisite of course vj or not.

Return a boolean array answer, where answer[j] is the answer to the jth query.

 

Example 1:


Input: numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
Output: [false,true]
Explanation: The pair [1, 0] indicates that you have to take course 1 before you can take course 0.
Course 0 is not a prerequisite of course 1, but the opposite is true.
Example 2:

Input: numCourses = 2, prerequisites = [], queries = [[1,0],[0,1]]
Output: [false,false]
Explanation: There are no prerequisites, and each course is independent.
Example 3:


Input: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
Output: [true,true]
 

Constraints:

2 <= numCourses <= 100
0 <= prerequisites.length <= (numCourses * (numCourses - 1) / 2)
prerequisites[i].length == 2
0 <= ai, bi <= n - 1
ai != bi
All the pairs [ai, bi] are unique.
The prerequisites graph has no cycles.
1 <= queries.length <= 104
0 <= ui, vi <= n - 1
ui != vi
*/





// Other's Solution:
class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        /*
            拓扑排序（卡恩算法） - https://leetcode.com/problems/course-schedule-iv/discuss/1548205/Multiple-Approaches-oror-BFS-and-DFS-oror-C%2B%2B-Clean-Code
            M = prerequisites.length，时间复杂度 O(numCourses*M)，空间复杂度 O(numCourses*M)
        */
        boolean[][] isPrerequisite = new boolean[numCourses][numCourses];
        List<List<Integer>> adj = new ArrayList<>();
        for (int i=0; i<numCourses; i++) adj.add(new ArrayList<>());
        int[] indegree = new int[numCourses];
        
        for (int[] pre : prerequisites) {
            adj.get(pre[0]).add(pre[1]);
            isPrerequisite[pre[0]][pre[1]] = true;
            indegree[pre[1]]++;
        }
        
        Queue<Integer> q = new LinkedList<>();
        for (int i=0; i<numCourses; i++) if (indegree[i] == 0) q.add(i);
        
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int adjNode : adj.get(node)) {
                for (int i=0; i<numCourses; i++) {
                    if (isPrerequisite[i][node]) isPrerequisite[i][adjNode] = true;
                }
                
                indegree[adjNode]--; 
                if (indegree[adjNode] == 0) q.add(adjNode);
            }
        }
    
        List<Boolean> res = new ArrayList<>();
        for (int[] query : queries) {
            res.add(isPrerequisite[query[0]][query[1]]);
        }
        
        return res;
    }
}



// Other's Solution:
class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        /*
            https://leetcode-cn.com/problems/course-schedule-iv/solution/floyed-suan-fa-by-15228207-7r9k/
            弗洛伊德算法 - 基于动态规划，A[i][j] 
            if there is path from i to j
        */
        int[][] A = new int[numCourses][numCourses];
        for (int i=0; i<numCourses; i++) Arrays.fill(A[i], 100);

        for (int[] pre : prerequisites) {
            A[pre[0]][pre[1]] = 1;
        }

        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    A[i][j] = Math.min(A[i][j], A[i][k]+A[k][j]);
                }
            }
        }

        List<Boolean> result = new ArrayList<>();
        for (int[] query : queries) {
            int i = query[0], j = query[1];
            if (A[i][j] != 100) result.add(true);
            else result.add(false);
        }
        return result;
    }
}



// My Solution:
class Solution {
    Map<Integer, Set<Integer>> map = new HashMap<>();
    
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        /*
            哈希表+哈希集+记忆化搜索，先遍历 prerequisites，把 prerequisites 数组中的所有上下级添入哈希表中，比如 [1, 0] 就以 1 为键插入哈希表，其值为一个哈希集其中包含 0、若键值对已存在则只哈希集添加值，遍历完数组后重新遍历一次哈希表，对遍历到的键的值作为查找子值的键再在哈希表中找寻其哈希集（递归直到最后的值不是哈希表的键，且在递归过程中对正在递归完的键的哈希集更新为其完整子值集 - 在哈希集中放入该键以标记该哈希集已完成更新即记忆化搜索，所以下次如果对另外一个键递归到该键时直接返回该键的哈希集即可不必再递归该键的哈希集）
            M = prerequisites.length，时间复杂度 O(numCourses*M)，空间复杂度 O(numCourses*M)
        */
        List<Boolean> res = new ArrayList<>();
        
        for (int[] prerequisite : prerequisites) {
            if (!map.containsKey(prerequisite[0])) {
                map.put(prerequisite[0], new HashSet<>());
            }
            map.get(prerequisite[0]).add(prerequisite[1]);
        }
        
        for (Integer key : map.keySet()) {
            recursion(key, map);
        }
        
        for (int[] query : queries) {
            boolean isPrerequisite = false;
            if (map.containsKey(query[0]) && map.get(query[0]).contains(query[1])) {
                isPrerequisite = true;
            }
            res.add(isPrerequisite);
        }
        
        return res;
    }
    
    public Set<Integer> recursion(Integer key, Map<Integer, Set<Integer>> map) {
        Set<Integer> tmpSet = new HashSet<>();
        if (!map.containsKey(key)) return tmpSet;
        
        Set<Integer> set = map.get(key);
        if (set.contains(key)) return set;

        for (Integer subKey : set) {
            tmpSet.addAll(recursion(subKey, map));
        }
        set.addAll(tmpSet);
        set.add(key);
        
        return set;
    }
}
