/**
A car travels from a starting position to a destination which is target miles east of the starting position.

There are gas stations along the way. The gas stations are represented as an array stations where stations[i] = [positioni, fueli] indicates that the ith gas station is positioni miles east of the starting position and has fueli liters of gas.

The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it. It uses one liter of gas per one mile that it drives. When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.

Return the minimum number of refueling stops the car must make in order to reach its destination. If it cannot reach the destination, return -1.

Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there. If the car reaches the destination with 0 fuel left, it is still considered to have arrived.

 

Example 1:

Input: target = 1, startFuel = 1, stations = []
Output: 0
Explanation: We can reach the target without refueling.
Example 2:

Input: target = 100, startFuel = 1, stations = [[10,100]]
Output: -1
Explanation: We can not reach the target (or even the first gas station).
Example 3:

Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
Output: 2
Explanation: We start with 10 liters of fuel.
We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
We made 2 refueling stops along the way, so we return 2.
 

Constraints:

1 <= target, startFuel <= 10^9
0 <= stations.length <= 500
0 <= positioni <= positioni+1 < target
1 <= fueli < 10^9
 */



// Other's Solution:
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // Greedy - https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/294025/Java-Simple-Code-Greedy
        if (startFuel >= target) return 0;
        Queue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        int i = 0, n = stations.length, stops = 0, maxDistance = startFuel;
        while (maxDistance < target) {
            while (i < n && stations[i][0] <= maxDistance) {
                queue.offer(stations[i++][1]);
            }
            if (queue.isEmpty()) return -1;
            maxDistance += queue.poll();
            stops++;
        }
        return stops;
    }
}



// Other's Solution:
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // DP - https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/149839/DP-O(N2)-and-Priority-Queue-O(NlogN)
        /*
            dp[t] means the furthest distance that we can get with t times of refueling.

            So for every station stations[i],
            if the current distance dp[t] >= stations[i][0], we can refuel:
            dp[t + 1] = max(dp[t + 1], dp[t] + stations[i][1])

            In the end, we'll return the first t with dp[t] >= target,
            otherwise we'll return -1.
        */
        long[] dp = new long[stations.length + 1];
        dp[0] = startFuel;
        for (int i = 0; i < stations.length; ++i)
            for (int t = i; t >= 0 && dp[t] >= stations[i][0]; --t)
                dp[t + 1] = Math.max(dp[t + 1], dp[t] + stations[i][1]);
        
        for (int t = 0; t <= stations.length; ++t)
            if (dp[t] >= target) return t;
        
        return -1;
    }
}
