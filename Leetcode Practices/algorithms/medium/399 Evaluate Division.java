/*
Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.

Example:
Given a / b = 2.0, b / c = 3.0.
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
return [6.0, 0.5, -1.0, 1.0, -1.0 ].

The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.

According to the example above:

equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ]. 
 

The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
*/



// Other's Solution (Union Find):  
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        UnionFindSet u = new UnionFindSet();
    
        for (int i = 0; i < equations.size(); ++i) {
              u.union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
        }

        double[] ans = new double[queries.size()];

        for (int i = 0; i < queries.size(); ++i) {      
            Node rx = u.find(queries.get(i).get(0));
            Node ry = u.find(queries.get(i).get(1));
            if (rx == null || ry == null || !rx.parent.equals(ry.parent))
                ans[i] = -1.0;        
            else
                ans[i] = rx.ratio / ry.ratio;
        }

        return ans;
    }
    
    class Node {
        public String parent;
        public double ratio;
        public Node(String parent, double ratio) {
            this.parent = parent;
            this.ratio = ratio;
        }
    }
  
    class UnionFindSet {
        private Map<String, Node> parents = new HashMap<>();

        public Node find(String s) {
            if (!parents.containsKey(s)) return null;
            Node n = parents.get(s);
            if (!n.parent.equals(s)) {
                Node p = find(n.parent);
                n.parent = p.parent;
                n.ratio *= p.ratio;
            }
            return n;
        }
    
        public void union(String s, String p, double ratio) {
            boolean hasS = parents.containsKey(s);
            boolean hasP = parents.containsKey(p);
            if (!hasS && !hasP) {
                parents.put(s, new Node(p, ratio));
                parents.put(p, new Node(p, 1.0));
            } else if (!hasP) {
                parents.put(p, new Node(s, 1.0 / ratio));
            } else if (!hasS) {
                parents.put(s, new Node(p, ratio));
            } else {
                Node rS = find(s);
                Node rP = find(p);
                rS.parent = rP.parent;
                rS.ratio = ratio / rS.ratio * rP.ratio;
            }
        }
    }
}
// https://zxi.mytechroad.com/blog/graph/leetcode-399-evaluate-division/
