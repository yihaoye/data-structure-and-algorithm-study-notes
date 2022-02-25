/*
There are N gas stations along a circular route, where the amount of gas at station i is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.

Note:

If there exists a solution, it is guaranteed to be unique.
Both input arrays are non-empty and have the same length.
Each element in the input arrays is a non-negative integer.
Example 1:

Input: 
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]

Output: 3

Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
Example 2:

Input: 
gas  = [2,3,4]
cost = [3,4,3]

Output: -1

Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.
*/



// Other's Solution:
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        /*
            https://leetcode-cn.com/problems/gas-station/solution/jia-you-zhan-by-leetcode-solution/
            本质是模拟暴力解的数学优化版：如果 x 到不了 y+1（但能到 y），那么从 x 到 y 的任一点出发都不可能到达 y+1。因为从其中任一点出发的话，相当于从 0 开始加油，而如果从 x 出发到该点则不一定是从 0 开始加油，可能还有剩余的油。既然不从 0 开始都到不了 y+1，那么从 0 开始就更不可能到达 y+1
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        int n = gas.length, i = 0;
        while (i < n) { // 从头到尾遍历每个加油站，并且检查以该加油站为起点，能否行驶一周
            int sumOfGas = 0, sumOfCost = 0, cnt = 0; // 总共加的油，总共消费的油，记录能走过几个站点
            while (cnt < n) { // 退出循环的条件是走过所有的站点
                int j = (i + cnt) % n; // 加油站是环形的
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                if (sumOfCost > sumOfGas) break; // 如果这个站点发现油不够了
                cnt++; // 这个站点满足情况
            }
            if (cnt == n) return i; // 如果能环绕一圈
            else i = i+cnt+1; // 不行的话 从下一个站点开始 检查
        }
        return -1;
    }
}



// My Solution:
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int[] diff = new int[len];
        for (int i=0; i<len; i++) {
            diff[i] = gas[i] - cost[i];
        }
        for (int i=0; i<len; i++) {
            if (run(diff, i)) return i;
        }
        
        return -1;
    }
    
    public boolean run(int[] diff, int startIndex) {
        int diffSum = diff[startIndex];
        int i = startIndex;
        while (diffSum >= 0) {
            i++;
            if (i == diff.length) i = 0;
            if (i == startIndex) return true;
            diffSum += diff[i];
        }
        
        return false;
    }
}



// Other's Solution:
/*
The algorithm is pretty easy to understand. Imagine we take a tour around this circle, the only condition that we can complete this trip is to have more fuel provided than costed in total. That's what the first loop does.

If we do have more fuel provided than costed, that means we can always find a start point around this circle that we could complete the journey with an empty tank. 
Hence, we check from the beginning of the array, if we can gain more fuel at the current station, we will maintain the start point, else, which means we will burn out of oil before reaching to the next station, we will start over at the next station.
*/
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int tank = 0;
        for(int i = 0; i < gas.length; i++)
            tank += gas[i] - cost[i];
        if(tank < 0)
            return - 1;
            
        int start = 0;
        int accumulate = 0;
        for(int i = 0; i < gas.length; i++){
            int curGain = gas[i] - cost[i];
            if(accumulate + curGain < 0){
                start = i + 1;
                accumulate = 0;
            }
            else accumulate += curGain;
        }
        
        return start;
    }
}