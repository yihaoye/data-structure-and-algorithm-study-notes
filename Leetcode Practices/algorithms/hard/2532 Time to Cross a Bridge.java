/**
There are k workers who want to move n boxes from an old warehouse to a new one. You are given the two integers n and k, and a 2D integer array time of size k x 4 where time[i] = [leftToRighti, pickOldi, rightToLefti, putNewi].

The warehouses are separated by a river and connected by a bridge. The old warehouse is on the right bank of the river, and the new warehouse is on the left bank of the river. Initially, all k workers are waiting on the left side of the bridge. To move the boxes, the ith worker (0-indexed) can :

Cross the bridge from the left bank (new warehouse) to the right bank (old warehouse) in leftToRighti minutes.
Pick a box from the old warehouse and return to the bridge in pickOldi minutes. Different workers can pick up their boxes simultaneously.
Cross the bridge from the right bank (old warehouse) to the left bank (new warehouse) in rightToLefti minutes.
Put the box in the new warehouse and return to the bridge in putNewi minutes. Different workers can put their boxes simultaneously.
A worker i is less efficient than a worker j if either condition is met:

leftToRighti + rightToLefti > leftToRightj + rightToLeftj
leftToRighti + rightToLefti == leftToRightj + rightToLeftj and i > j
The following rules regulate the movement of the workers through the bridge :

If a worker x reaches the bridge while another worker y is crossing the bridge, x waits at their side of the bridge.
If the bridge is free, the worker waiting on the right side of the bridge gets to cross the bridge. If more than one worker is waiting on the right side, the one with the lowest efficiency crosses first.
If the bridge is free and no worker is waiting on the right side, and at least one box remains at the old warehouse, the worker on the left side of the river gets to cross the bridge. If more than one worker is waiting on the left side, the one with the lowest efficiency crosses first.
Return the instance of time at which the last worker reaches the left bank of the river after all n boxes have been put in the new warehouse.

 

Example 1:

Input: n = 1, k = 3, time = [[1,1,2,1],[1,1,3,1],[1,1,4,1]]
Output: 6
Explanation: 
From 0 to 1: worker 2 crosses the bridge from the left bank to the right bank.
From 1 to 2: worker 2 picks up a box from the old warehouse.
From 2 to 6: worker 2 crosses the bridge from the right bank to the left bank.
From 6 to 7: worker 2 puts a box at the new warehouse.
The whole process ends after 7 minutes. We return 6 because the problem asks for the instance of time at which the last worker reaches the left bank.
Example 2:

Input: n = 3, k = 2, time = [[1,9,1,8],[10,10,10,10]]
Output: 50
Explanation: 
From 0  to 10: worker 1 crosses the bridge from the left bank to the right bank.
From 10 to 20: worker 1 picks up a box from the old warehouse.
From 10 to 11: worker 0 crosses the bridge from the left bank to the right bank.
From 11 to 20: worker 0 picks up a box from the old warehouse.
From 20 to 30: worker 1 crosses the bridge from the right bank to the left bank.
From 30 to 40: worker 1 puts a box at the new warehouse.
From 30 to 31: worker 0 crosses the bridge from the right bank to the left bank.
From 31 to 39: worker 0 puts a box at the new warehouse.
From 39 to 40: worker 0 crosses the bridge from the left bank to the right bank.
From 40 to 49: worker 0 picks up a box from the old warehouse.
From 49 to 50: worker 0 crosses the bridge from the right bank to the left bank.
From 50 to 58: worker 0 puts a box at the new warehouse.
The whole process ends after 58 minutes. We return 50 because the problem asks for the instance of time at which the last worker reaches the left bank.
 

Constraints:

1 <= n, k <= 10^4
time.length == k
time[i].length == 4
1 <= leftToRighti, pickOldi, rightToLefti, putNewi <= 1000
 */



// My Solution:
class Solution {
    public int findCrossingTime(int n, int k, int[][] time) {
        // 模拟(+时间轴) + 优先队列 + 对象
        PriorityQueue<Worker> lNotReady = new PriorityQueue<>((a, b) -> a.readyTime == b.readyTime ? (a.efficiency == b.efficiency ? b.id - a.id : b.efficiency - a.efficiency) : a.readyTime - b.readyTime);
        PriorityQueue<Worker> rNotReady = new PriorityQueue<>((a, b) -> a.readyTime == b.readyTime ? (a.efficiency == b.efficiency ? b.id - a.id : b.efficiency - a.efficiency) : a.readyTime - b.readyTime);
        PriorityQueue<Worker> lWait = new PriorityQueue<>((a, b) -> a.efficiency == b.efficiency ? b.id - a.id : b.efficiency - a.efficiency);
        PriorityQueue<Worker> rWait = new PriorityQueue<>((a, b) -> a.efficiency == b.efficiency ? b.id - a.id : b.efficiency - a.efficiency);
        
        for (int i=0; i<k; i++) lWait.offer(new Worker(i, time[i][0] + time[i][2], 0)); // 一开始都在左岸
        
        int timestamp = 0;
        while (n > 0) {
            if (lWait.isEmpty() && rWait.isEmpty() && (lNotReady.isEmpty() || lNotReady.peek().readyTime > timestamp) && (rNotReady.isEmpty() || rNotReady.peek().readyTime > timestamp)) {
                timestamp = Math.min(lNotReady.isEmpty() ? Integer.MAX_VALUE : lNotReady.peek().readyTime, rNotReady.isEmpty() ? Integer.MAX_VALUE : rNotReady.peek().readyTime);
            }
            // 需要另用一个优先队列找出所有 readyTime 匹配的，然后只取最优先的出来（此时不一定是 readyTime 最靠前的）
            while (!lNotReady.isEmpty() && lNotReady.peek().readyTime <= timestamp) lWait.offer(lNotReady.poll());
            Worker lNextWorker = lWait.poll();
            // 同上，右岸
            while (!rNotReady.isEmpty() && rNotReady.peek().readyTime <= timestamp) rWait.offer(rNotReady.poll());
            Worker rNextWorker = rWait.poll();

            if (rNextWorker == null) {
                timestamp += time[lNextWorker.id][0];
                rNotReady.offer(new Worker(lNextWorker.id, time[lNextWorker.id][0] + time[lNextWorker.id][2], timestamp + time[lNextWorker.id][1]));
                n--;
            } else {
                if (lNextWorker != null) lWait.offer(lNextWorker);
                timestamp += time[rNextWorker.id][2];
                lNotReady.offer(new Worker(rNextWorker.id, time[rNextWorker.id][0] + time[rNextWorker.id][2], timestamp + time[rNextWorker.id][3]));
            }
        }

        while (!rNotReady.isEmpty() || !rWait.isEmpty()) { // 最后剩的肯定是右岸
            if (rWait.isEmpty() && rNotReady.peek().readyTime > timestamp) timestamp = rNotReady.peek().readyTime;
            // 需要另用一个优先队列找出所有 readyTime 匹配的，然后只取最优先的出来
            while (!rNotReady.isEmpty() && rNotReady.peek().readyTime <= timestamp) rWait.offer(rNotReady.poll());
            Worker nextWorker = rWait.poll();
            timestamp += time[nextWorker.id][2];
        }
        
        return timestamp;
    }
    
    class Worker {
        int id;
        int efficiency;
        int readyTime;
        
        public Worker(int id, int efficiency, int readyTime) {
            this.id = id;
            this.efficiency = efficiency;
            this.readyTime = readyTime;
        }
    }
}
