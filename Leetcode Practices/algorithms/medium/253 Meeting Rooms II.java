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