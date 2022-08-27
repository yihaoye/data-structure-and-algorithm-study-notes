/**
You are given a 0-indexed integer array buses of length n, where buses[i] represents the departure time of the ith bus. You are also given a 0-indexed integer array passengers of length m, where passengers[j] represents the arrival time of the jth passenger. All bus departure times are unique. All passenger arrival times are unique.

You are given an integer capacity, which represents the maximum number of passengers that can get on each bus.

When a passenger arrives, they will wait in line for the next available bus. You can get on a bus that departs at x minutes if you arrive at y minutes where y <= x, and the bus is not full. Passengers with the earliest arrival times get on the bus first.

More formally when a bus arrives, either:

If capacity or fewer passengers are waiting for a bus, they will all get on the bus, or
The capacity passengers with the earliest arrival times will get on the bus.
Return the latest time you may arrive at the bus station to catch a bus. You cannot arrive at the same time as another passenger.

Note: The arrays buses and passengers are not necessarily sorted.

 

Example 1:

Input: buses = [10,20], passengers = [2,17,18,19], capacity = 2
Output: 16
Explanation: Suppose you arrive at time 16.
At time 10, the first bus departs with the 0th passenger. 
At time 20, the second bus departs with you and the 1st passenger.
Note that you may not arrive at the same time as another passenger, which is why you must arrive before the 1st passenger to catch the bus.
Example 2:

Input: buses = [20,30,10], passengers = [19,13,26,4,25,11,21], capacity = 2
Output: 20
Explanation: Suppose you arrive at time 20.
At time 10, the first bus departs with the 3rd passenger. 
At time 20, the second bus departs with the 5th and 1st passengers.
At time 30, the third bus departs with the 0th passenger and you.
Notice if you had arrived any later, then the 6th passenger would have taken your seat on the third bus.
 

Constraints:

n == buses.length
m == passengers.length
1 <= n, m, capacity <= 10^5
2 <= buses[i], passengers[i] <= 10^9
Each element in buses is unique.
Each element in passengers is unique.
 */



// My Solution:
class Solution {
    public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {
        // 贪心 - 优先队列 bPQ pPQ
        // bPQ.poll() : for (capacity) if (pPQ.peek() <= bT) pPQ.poll(); res = pPQ.poll()-1; // preTime
        int res = 0;
        PriorityQueue<Integer> bPQ = new PriorityQueue<>();
        PriorityQueue<Integer> pPQ = new PriorityQueue<>();
        for (int bus : buses) bPQ.offer(bus);
        for (int passenger : passengers) pPQ.offer(passenger);
        int lastPassengerTime = 0;
        while (!bPQ.isEmpty()) {
            int busTime = bPQ.poll();
            int cnt = capacity;
            while (cnt-- > 0) {
                if (!pPQ.isEmpty() && pPQ.peek() <= busTime) {
                    if (pPQ.peek() - 1 != lastPassengerTime) res = pPQ.peek() - 1;
                    lastPassengerTime = pPQ.poll();
                } else {
                    if (busTime != lastPassengerTime) res = busTime;
                    break;
                }
            }
        }
        
        return res;
    }
}
