/**
There is a tree (i.e., a connected, undirected graph with no cycles) structure country network consisting of n cities numbered from 0 to n - 1 and exactly n - 1 roads. The capital city is city 0. You are given a 2D integer array roads where roads[i] = [ai, bi] denotes that there exists a bidirectional road connecting cities ai and bi.

There is a meeting for the representatives of each city. The meeting is in the capital city.

There is a car in each city. You are given an integer seats that indicates the number of seats in each car.

A representative can use the car in their city to travel or change the car and ride with another representative. The cost of traveling between two cities is one liter of fuel.

Return the minimum number of liters of fuel to reach the capital city.

 

Example 1:


Input: roads = [[0,1],[0,2],[0,3]], seats = 5
Output: 3
Explanation: 
- Representative1 goes directly to the capital with 1 liter of fuel.
- Representative2 goes directly to the capital with 1 liter of fuel.
- Representative3 goes directly to the capital with 1 liter of fuel.
It costs 3 liters of fuel at minimum. 
It can be proven that 3 is the minimum number of liters of fuel needed.
Example 2:


Input: roads = [[3,1],[3,2],[1,0],[0,4],[0,5],[4,6]], seats = 2
Output: 7
Explanation: 
- Representative2 goes directly to city 3 with 1 liter of fuel.
- Representative2 and representative3 go together to city 1 with 1 liter of fuel.
- Representative2 and representative3 go together to the capital with 1 liter of fuel.
- Representative1 goes directly to the capital with 1 liter of fuel.
- Representative5 goes directly to the capital with 1 liter of fuel.
- Representative6 goes directly to city 4 with 1 liter of fuel.
- Representative4 and representative6 go together to the capital with 1 liter of fuel.
It costs 7 liters of fuel at minimum. 
It can be proven that 7 is the minimum number of liters of fuel needed.
Example 3:


Input: roads = [], seats = 1
Output: 0
Explanation: No representatives need to travel to the capital city.
 

Constraints:

1 <= n <= 10^5
roads.length == n - 1
roads[i].length == 2
0 <= ai, bi < n
ai != bi
roads represents a valid tree.
1 <= seats <= 10^5
 */



// Other's Solution:
class Solution {
    long ans = 0;
    int seats;
    
    public long minimumFuelCost(int[][] roads, int seats) {
        // https://leetcode.com/problems/minimum-fuel-cost-to-report-to-the-capital/discuss/2831676/C%2B%2B-Java-Python3-Simple-DFS-O(n)
        /*
            Intuition - We need to track the number of people that reach each node and divide that by the number of seats per car, this will tell us the number of cars required to take us to the node that is closer to node 0

            DFS + 数学
            Imagine you are at a leaf node, you move towards 0. There will be only 1 person in the car (you)
            Now let's say you're somewhere in the middle of the tree, with a car of size 5. You have 3 children nodes. Let's say each child node brings 1 car of 3 people. So a total of 3 * 3 = 9 people. Including you there are 10 people now. Now you have 3 cars from the child nodes and one car of your own. You actually need just 10 / 5 = 2 cars. You take 2 cars and move towards 0

            Time: O(n), Space: O(n)
        */
        List<List<Integer>> graph = new ArrayList(); this.seats = seats;
        for (int i=0; i<roads.length+1; i++) graph.add(new ArrayList());
        for (int[] r : roads) {
            graph.get(r[0]).add(r[1]);
            graph.get(r[1]).add(r[0]);
        }
        dfs(0, 0, graph);
        return ans;
    }
    
    private int dfs(int curr, int prev, List<List<Integer>> graph) {
        int people = 1;
        for (int next : graph.get(curr)) {
            if (next == prev) continue;
            people += dfs(next, curr, graph);
        }
        if (curr != 0) ans += (people + seats - 1) / seats;
        return people;
    }
}
