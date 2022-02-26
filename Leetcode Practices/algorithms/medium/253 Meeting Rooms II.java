/*
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.

Example 1:

Input: [[0, 30],[5, 10],[15, 20]]
Output: 2
Example 2:

Input: [[7,10],[2,4]]
Output: 1
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
*/



// My Solution (Greedy):
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // greedy, in the same room gap between meetings should be as small as possible
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int[][] rooms = new int[intervals.length+1][2]; // intervals.length+1 since one more index at the end for edge cases (e.g. n input -> n rooms or 1 input)
        for (int[] interval : intervals) {
            int suitRoomIndex = 0;
            for (int i=0, minGap=Integer.MAX_VALUE; i<rooms.length; i++) {
                if (interval[0] >= rooms[i][1] && minGap > interval[0]-rooms[i][1]) {
                    minGap = interval[0]-rooms[i][1];
                    suitRoomIndex = i;
                }
            }
            rooms[suitRoomIndex] = interval;
        }
        for (int i=0; i<rooms.length; i++) {
            if (rooms[i][0] == 0 && rooms[i][1] == 0) return i;
        }
        
        return 0;
    }
}



// Other's Solution:
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        /*
            扫描线 - 将区间在x轴上画出来，并用一条垂直于x轴的线作为扫描线从左至右扫描，会很容易得出答案，即与扫描线相交的区间的数量的最大值为所求答案。步骤：1. 对所有点进行标记，区分起始点和终止点；2. 对所有点进行排序；3. 依次遍历每个点，遇到起始点+1，遇到终止点-1，并更新记录最大值。
            用两个一维数组来做，分别保存起始时间和结束时间，然后各自排序，定义结果变量minRooms和结束时间指针endpos，然后开始遍历，如果当前起始时间start[i]小于结束时间指针的时间ends[endpos]，则结果自增1，反之结束时间指针自增1，这样可以找出重叠的时间段，从而安排新的会议室 https://techlarry.github.io/Leetcode/253.%20Meeting%20Rooms%20II/
            时间复杂度 O(N*logN)，空间复杂度 O(N)
        */
        int len = intervals.length;
        int[] starts = new int[len], ends = new int[len];
        for (int i=0; i<len; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        // 扫描线段
        int minRooms = 0, endpos = 0;
        for (int i=0; i<len; i++) {
            if (starts[i] < ends[endpos]) minRooms++;
            else endpos++;
        }
        return minRooms;
    }
}



// My Solution:
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        /*
            排序+贪心+优先队列
            时间复杂度 O(N*logN)，空间复杂度 O(N) - Java Arrays.sort
        */
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        rooms.add(intervals[0][1]);
        for (int i=1; i<intervals.length; i++) {
            if (rooms.peek() <= intervals[i][0]) rooms.poll();
            rooms.add(intervals[i][1]);
        }
        
        return rooms.size();
    }
}
