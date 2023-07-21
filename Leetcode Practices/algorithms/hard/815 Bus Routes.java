/*
You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.

For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.

Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.

 

Example 1:

Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
Output: 2
Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
Example 2:

Input: routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
Output: -1
 

Constraints:
1 <= routes.length <= 500.
1 <= routes[i].length <= 10^5
All the values of routes[i] are unique.
sum(routes[i].length) <= 10^5
0 <= routes[i][j] < 10^6
0 <= source, target < 10^6
*/



// My Solution:
class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // Graph + BFS
        if (source == target) return 0;
        
        Map<Integer, Set<Integer>> busToStops = new HashMap<>();
        Map<Integer, Set<Integer>> StopToBuses = new HashMap<>();
        Set<Integer> usedBuses = new HashSet<>(); // bus used
        Queue<Integer> curBuses = new LinkedList<>();
        for (int i=0; i<routes.length; i++) {
            for (int j=0; j<routes[i].length; j++) {
                busToStops.computeIfAbsent(i, v -> new HashSet<>()).add(routes[i][j]);
                StopToBuses.computeIfAbsent(routes[i][j], v -> new HashSet<>()).add(i);
                if (routes[i][j] == source && !usedBuses.contains(i)) {
                    usedBuses.add(i);
                    curBuses.offer(i);
                }
            }
        }

        Map<Integer, Set<Integer>> busToBuses = new HashMap<>(); // graph, set<...> means the next bus can be transfer from the key bus
        for (int i=0; i<routes.length; i++) {
            for (int stop : busToStops.get(i))
                busToBuses.computeIfAbsent(i, v -> new HashSet<>()).addAll(StopToBuses.get(stop));
        }
        
        int res = 1;
        while (!curBuses.isEmpty()) {
            int n = curBuses.size();
            while (n-- > 0) {
                int prevBus = curBuses.poll();
                if (busToStops.get(prevBus).contains(target)) return res;
                for (int nextBus : busToBuses.get(prevBus)) {
                    if (usedBuses.contains(nextBus)) continue;
                    usedBuses.add(nextBus);
                    curBuses.offer(nextBus);
                }
            }
            
            res++;
        }

        return -1;
    }
}
