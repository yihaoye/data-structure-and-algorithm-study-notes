/**
You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given an array relations where relations[i] = [prevCoursei, nextCoursei], representing a prerequisite relationship between course prevCoursei and course nextCoursei: course prevCoursei has to be taken before course nextCoursei.

In one semester, you can take any number of courses as long as you have taken all the prerequisites in the previous semester for the courses you are taking.

Return the minimum number of semesters needed to take all courses. If there is no way to take all the courses, return -1.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/24/course1graph.jpg

Input: n = 3, relations = [[1,3],[2,3]]
Output: 2
Explanation: The figure above represents the given graph.
In the first semester, you can take courses 1 and 2.
In the second semester, you can take course 3.

Example 2:
https://assets.leetcode.com/uploads/2021/02/24/course2graph.jpg

Input: n = 3, relations = [[1,2],[2,3],[3,1]]
Output: -1
Explanation: No course can be studied because they are prerequisites of each other.
 

Constraints:

1 <= n <= 5000
1 <= relations.length <= 5000
relations[i].length == 2
1 <= prevCoursei, nextCoursei <= n
prevCoursei != nextCoursei
All the pairs [prevCoursei, nextCoursei] are unique.
 */



// My Solution:
class Solution {
    Map<Integer, Set<Integer>> indegree; // - key course, value pre-request of the course
    Map<Integer, Set<Integer>> outdegree; // - key course, value course which pre-request is key
    
    public int minimumSemesters(int n, int[][] relations) {
        int res = 0, taken = 0;
        init(n, relations);
        while (true) {
            Set<Integer> curAvailables = findAvailable();
            taken += curAvailables.size();
            if (curAvailables.size() > 0) {
                removeCurAvailables(curAvailables);
            } else {
                if (taken < n) return -1;
                else break;
            }
            res++;
        }
        return res;
    }
    
    public void init(int n, int[][] relations) {
        indegree = new HashMap<>();
        outdegree = new HashMap<>();
        for (int i=1; i<=n; i++) {
            indegree.put(i, new HashSet<>());
            outdegree.put(i, new HashSet<>());
        }
        for (int[] relation : relations) {
            indegree.get(relation[1]).add(relation[0]);
            outdegree.get(relation[0]).add(relation[1]);
        }
    }
    
    public Set<Integer> findAvailable() {
        Set<Integer> availables = new HashSet<>();
        for (Map.Entry<Integer, Set<Integer>> entry : indegree.entrySet()) {
            if (entry.getValue().size() == 0) availables.add(entry.getKey());
        }
        return availables;
    }
    
    public void removeCurAvailables(Set<Integer> curAvailables) {
        for (int available : curAvailables) {
            for (int relyer : outdegree.get(available)) {
                indegree.get(relyer).remove(available);
            }
            indegree.remove(available);
        }
    }
}
