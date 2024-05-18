// https://en.wikipedia.org/wiki/Longest_path_problem
// NP-Hard Problem for general graph. If the graph is a DAG, then the longest path can be found by topological sort, and the Time Complexity is O(V+E)
import java.util.*;

public class Solution {
    public Set<Set<Node>> longestPaths(List<Node> graph) { // target graph as input param
        // BFS, and apply HashSet to deduplicate paths, apply LinkedHashSet for combine both Queue and HashSet for simplify the code
        // Time Complexity: O(2^V)
        Set<Set<Node>> res = new HashSet<>();
        for (Node node : graph) res.add(new LinkedHashSet<>(Arrays.asList(node)));
        
        while (true) {
            Set<Set<Node>> tmp = new HashSet<>();
            for (Set<Node> path : res) tmp.addAll(path.extend());
            if (tmp.isEmpty()) break;
            res = tmp;
        }
        return res;
    }

    // extend the path by one node in one of directions, return all possible paths
    public Set<Set<Node>> extend(Set<Node> path) {
        Set<Set<Node>> res = null;
        if (path.size() == 0) return res;
        path.getFirst().neighbors.forEach(node -> {
            if (path.contains(node)) continue;
            Set<Node> next = path.clone();
            next.addFirst(node); // Java 21
            if (res == null) res = new HashSet<>();
            res.add(next);
        });
        if (path.size() == 1) return res;
        path.getLast().neighbors.forEach(node -> {
            if (path.contains(node)) continue;
            Set<Node> next = path.clone();
            next.add(node);
            if (res == null) res = new HashSet<>();
            res.add(next);
        });
        return res;
    }
}

class Node {
    int val;
    List<Node> neighbors;
}
