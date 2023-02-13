/**
You are given an array of strings equations that represent relationships between variables where each string equations[i] is of length 4 and takes one of two different forms: "xi==yi" or "xi!=yi".Here, xi and yi are lowercase letters (not necessarily different) that represent one-letter variable names.

Return true if it is possible to assign integers to variable names so as to satisfy all the given equations, or false otherwise.

 

Example 1:

Input: equations = ["a==b","b!=a"]
Output: false
Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.
There is no way to assign the variables to satisfy both equations.
Example 2:

Input: equations = ["b==a","a==b"]
Output: true
Explanation: We could assign a = 1 and b = 1 to satisfy both equations.
 

Constraints:

1 <= equations.length <= 500
equations[i].length == 4
equations[i][0] is a lowercase letter.
equations[i][1] is either '=' or '!'.
equations[i][2] is '='.
equations[i][3] is a lowercase letter.
 */



// Other's Solution:
class Solution {
    int[] uf = new int[26];

    public boolean equationsPossible(String[] equations) {
        // https://leetcode.com/problems/satisfiability-of-equality-equations/solutions/234486/java-c-python-easy-union-find/?orderBy=most_votes
        for (int i = 0; i < 26; ++i) uf[i] = i;
        for (String e : equations)
            if (e.charAt(1) == '=') uf[find(e.charAt(0) - 'a')] = find(e.charAt(3) - 'a');
        for (String e : equations)
            if (e.charAt(1) == '!' && find(e.charAt(0) - 'a') == find(e.charAt(3) - 'a')) return false;
        return true;
    }

    public int find(int x) {
        if (x != uf[x]) uf[x] = find(uf[x]);
        return uf[x];
    }
}



// My Solution:
class Solution {
    Map<Integer, Set<Integer>> graph = new HashMap<>();

    public boolean equationsPossible(String[] equations) {
        // 图 + BFS + 去重，如果 var 量级较多，则需要使用并查集
        for (int i=0; i<26; i++) {
            Set<Integer> tmp = new HashSet<>();
            tmp.add(i);
            graph.put(i, tmp);
        }

        for (String equation : equations) {
            char op = equation.charAt(1);
            if (op == '!') continue;
            int nodeA = equation.charAt(0) - 'a';
            int nodeB = equation.charAt(3) - 'a';
            graph.get(nodeA).add(nodeB);
            graph.get(nodeB).add(nodeA);
        }        

        for (String equation : equations) {
            char op = equation.charAt(1);
            if (op == '=') continue;
            int nodeA = equation.charAt(0) - 'a';
            int nodeB = equation.charAt(3) - 'a';
            if (bfs(nodeA, nodeB)) return false;
        }

        return true;
    }

    public boolean bfs(Integer nodeA, Integer nodeB) {
        if (nodeA == nodeB) return true;
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(nodeA);
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            if (visited.contains(cur)) continue;
            visited.add(cur);
            Set<Integer> nexts = graph.get(cur);
            if (nexts.contains(nodeB)) return true;
            queue.addAll(nexts);
        }
        return false;
    }
}
