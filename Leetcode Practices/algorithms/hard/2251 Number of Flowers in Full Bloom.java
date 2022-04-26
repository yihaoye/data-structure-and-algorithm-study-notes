/**
You are given a 0-indexed 2D integer array flowers, where flowers[i] = [starti, endi] means the ith flower will be in full bloom from starti to endi (inclusive). You are also given a 0-indexed integer array persons of size n, where persons[i] is the time that the ith person will arrive to see the flowers.

Return an integer array answer of size n, where answer[i] is the number of flowers that are in full bloom when the ith person arrives.

 

Example 1:
https://assets.leetcode.com/uploads/2022/03/02/ex1new.jpg

Input: flowers = [[1,6],[3,7],[9,12],[4,13]], persons = [2,3,7,11]
Output: [1,2,2,2]
Explanation: The figure above shows the times when the flowers are in full bloom and when the people arrive.
For each person, we return the number of flowers in full bloom during their arrival.

Example 2:
https://assets.leetcode.com/uploads/2022/03/02/ex2new.jpg

Input: flowers = [[1,10],[3,3]], persons = [3,3,2]
Output: [2,2,1]
Explanation: The figure above shows the times when the flowers are in full bloom and when the people arrive.
For each person, we return the number of flowers in full bloom during their arrival.
 

Constraints:

1 <= flowers.length <= 5 * 104
flowers[i].length == 2
1 <= starti <= endi <= 109
1 <= persons.length <= 5 * 104
1 <= persons[i] <= 109
 */



// My Solution:
class Solution {
    public int[] fullBloomFlowers(int[][] flowers, int[] persons) {
        // 双优先队列
        int[] res = new int[persons.length];
        PriorityQueue<int[]> bloomNow = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        PriorityQueue<int[]> bloomUpComing = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int[] flower : flowers) {
            bloomUpComing.add(flower);
        }
        Map<Integer, List<Integer>> tMap = new TreeMap<>(); // (time, [personIndex...])，使用 TreeMap 因为后续需要按观看时间顺序计算每个人将看到的开花数而原 persons 数组不一定按观看时间排序，value 为队列因为可能多个人同时观看
        for (int i=0; i<persons.length; i++) {
            tMap.putIfAbsent(persons[i], new ArrayList<Integer>());
            tMap.get(persons[i]).add(i);
        }
        for (Map.Entry<Integer, List<Integer>> entry : tMap.entrySet()) {
            while (!bloomUpComing.isEmpty() && bloomUpComing.peek()[0] <= entry.getKey()) {
                bloomNow.add(bloomUpComing.poll());
            }
            while (!bloomNow.isEmpty() && bloomNow.peek()[1] < entry.getKey()) {
                bloomNow.poll();
            }
            for (int personIndex : entry.getValue()) {
                res[personIndex] = bloomNow.size();
            }
        }
        
        return res;
    }
}
