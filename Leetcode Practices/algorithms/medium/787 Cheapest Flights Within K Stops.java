/**
There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.

You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.

 

Example 1:
https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-3drawio.png

Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
Output: 700
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 3 is marked in red and has cost 100 + 600 = 700.
Note that the path through cities [0,1,2,3] is cheaper but is invalid because it uses 2 stops.

Example 2:
https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-1drawio.png

Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
Output: 200
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 2 is marked in red and has cost 100 + 100 = 200.

Example 3:
https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-2drawio.png

Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
Output: 500
Explanation:
The graph is shown above.
The optimal path with no stops from city 0 to 2 is marked in red and has cost 500.
 

Constraints:

1 <= n <= 100
0 <= flights.length <= (n * (n - 1) / 2)
flights[i].length == 3
0 <= fromi, toi < n
fromi != toi
1 <= pricei <= 10^4
There will not be any multiple flights between two cities.
0 <= src, dst, k < n
src != dst
 */



// My Solution:
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // BFS (pruning) + DP + HashMap
        // Time: O(<m^2*k) ?, Space: O(max(n*k, m)) -- m = flights.length
        int[][] dp = new int[k+2][n]; // dp[k][n] means min cost from src to n with just k' stop, k stop means tounch k+1 cities therefore up need int[k+2][n], int[0][n] means no city tounched
        int[] res = new int[n]; // res[n] means min cost from src to n within k' stop
        Map<Integer, List<int[]>> map = new HashMap<>(); // <fromi, List<flight_with_fromi>>
        for (int[] flight : flights) {
            List<int[]> fromiList = map.getOrDefault(flight[0], new LinkedList<>());
            fromiList.add(flight);
            map.put(flight[0], fromiList);
        }
        
        int cnt = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int[] flight : map.getOrDefault(src, new LinkedList<>())) {
            queue.offer(flight);
        }
        map.remove(src); // BFS pruning
        while (!queue.isEmpty() && ++cnt <= k+1) {
            Queue<int[]> tmpQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                int[] flight = queue.poll();
                if (res[flight[1]] != 0 && res[flight[1]] < dp[cnt-1][flight[0]] + flight[2]) continue; // BFS pruning. dp[cnt-1][flight[0]] will not be 0 (except dp[0][flight[0]]) because it is last loop's "dp[cnt][flight[1]]", which is already assign value in next line, and if was not assign value which skipped at last loop in this line, then we will not get its nextFlight here.
                
                dp[cnt][flight[1]] = dp[cnt-1][flight[0]] + flight[2];
                res[flight[1]] = res[flight[1]] == 0 ? dp[cnt][flight[1]] : Math.min(res[flight[1]], dp[cnt][flight[1]]);
                tmpQueue.addAll(map.getOrDefault(flight[1], new LinkedList<>()));
            }
            queue = tmpQueue;
        }
        
        return res[dst] == 0 ? -1 : res[dst];
    }
}
