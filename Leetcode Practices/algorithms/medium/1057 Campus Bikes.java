/*
On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.

Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we choose the pair with the smallest bike index). We repeat this process until there are no available workers.

The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.

Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.

 

Example 1:
![](https://assets.leetcode.com/uploads/2019/03/06/1261_example_1_v2.png)

Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
Output: [1,0]
Explanation: 
Worker 1 grabs Bike 0 as they are closest (without ties), and Worker 0 is assigned Bike 1. So the output is [1, 0].



Example 2:
![](https://assets.leetcode.com/uploads/2019/03/06/1261_example_2_v2.png)

Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
Output: [0,2,1]
Explanation: 
Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2, thus Worker 1 is assigned to Bike 2, and Worker 2 will take Bike 1. So the output is [0,2,1].
 

Note:

0 <= workers[i][j], bikes[i][j] < 1000
All worker and bike locations are distinct.
1 <= workers.length <= bikes.length <= 1000
*/



// Other's Solution:
class Solution {
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        int n = workers.length;
        
        // order by Distance ASC, WorkerIndex ASC, BikeIndex ASC
        PriorityQueue<int[]> q = new PriorityQueue<int[]>((a, b) -> {
            int comp = Integer.compare(a[0], b[0]);
            if (comp == 0) {
                if (a[1] == b[1]) {
                    return Integer.compare(a[2], b[2]);
                }
                
                return Integer.compare(a[1], b[1]);
            }
            
            return comp;
        });
            
        // loop through every possible pairs of bikes and people,
        // calculate their distance, and then throw it to the pq.
        for (int i = 0; i < workers.length; i++) {
            
            int[] worker = workers[i];
            for (int j = 0; j < bikes.length; j++) {
                int[] bike = bikes[j];
                int dist = Math.abs(bike[0] - worker[0]) + Math.abs(bike[1] - worker[1]);
                q.add(new int[]{dist, i, j}); 
            }
        }
        
        // init the result array with state of 'unvisited'.
        int[] res = new int[n];
        Arrays.fill(res, -1);
        
        // assign the bikes.
        Set<Integer> bikeAssigned = new HashSet<>();
        while (bikeAssigned.size() < n) {
            int[] workerAndBikePair = q.poll();
            if (res[workerAndBikePair[1]] == -1 
                && !bikeAssigned.contains(workerAndBikePair[2])) {   
                
                res[workerAndBikePair[1]] = workerAndBikePair[2];
                bikeAssigned.add(workerAndBikePair[2]);
            }
        }
        
        return res;
    }
}



// My Solution 1:
class Solution {
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        // Recursion question actually
        int[] res = new int[workers.length];
        findNextPair(workers, bikes, res);
        
        return res;
    }
    
    public void findNextPair(int[][] workers, int[][] bikes, int[] res) {
        int pair = 0, w = -1, b = -1; // assign pair distance, worker index, bike index
        for (int i = 0; i < workers.length; i++) {
            if (workers[i][0] == -1) continue;
            for (int j = 0; j < bikes.length; j++) {
                if (bikes[j][0] == -1) continue;
                int temp = mDistance(workers[i], bikes[j]);
                if (temp < pair || pair == 0) {
                    pair = temp;
                    w = i;
                    b = j;
                }
            }
        }
        if (w == -1) return;
        res[w] = b;
        workers[w][0] = -1;
        bikes[b][0] = -1;
        findNextPair(workers, bikes, res);
    }
    
    public int mDistance(int[] worker, int[] bike) {
        int x = worker[0] - bike[0] > 0 ? worker[0] - bike[0] : bike[0] - worker[0];
        int y = worker[1] - bike[1] > 0 ? worker[1] - bike[1] : bike[1] - worker[1];
        return x + y;
    }
}