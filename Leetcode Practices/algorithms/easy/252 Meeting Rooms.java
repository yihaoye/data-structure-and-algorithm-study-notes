/*
Given an array of meeting time intervals where intervals[i] = [starti, endi], determine if a person could attend all meetings.

 

Example 1:

Input: intervals = [[0,30],[5,10],[15,20]]
Output: false
Example 2:

Input: intervals = [[7,10],[2,4]]
Output: true
 

Constraints:

0 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti < endi <= 106
*/



// My Solution:
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        /*
            检查是否有重叠，按每个元素数组的元素1排序，然后遍历一次，若下一个数组的元素1小于前一个数组的元素2，则失败
            时间复杂度 O(N*logN)，空间复杂度 O(N) - Java Arrays.sort（非基本类型）
        */
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i=0; i<intervals.length-1; i++) {
            if (intervals[i][1] > intervals[i+1][0]) return false;
        }
        
        return true;
    }
}