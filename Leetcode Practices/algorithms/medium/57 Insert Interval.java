/**
You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent the start and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval newInterval = [start, end] that represents the start and end of another interval.

Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return intervals after the insertion.

 

Example 1:

Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
Example 2:

Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 

Constraints:

0 <= intervals.length <= 10^4
intervals[i].length == 2
0 <= starti <= endi <= 10^5
intervals is sorted by starti in ascending order.
newInterval.length == 2
0 <= start <= end <= 10^5
 */



// My Solution:
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // 模拟
        // Time: O(N), Space: O(N)
        List<int[]> resList = new ArrayList<>();
        for (int[] interval : intervals) {
            if (newInterval[1] < interval[0]) {
                resList.add(newInterval);
                resList.add(interval);
                newInterval = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
                continue;
            }
            if (interval[1] < newInterval[0]) {
                resList.add(interval);
                continue;
            }
            if (interval[0] <= newInterval[0] && newInterval[1] <= interval[1]) {
                resList.add(interval);
                newInterval = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
                continue;
            }
            if (interval[0] <= newInterval[0] && interval[1] <= newInterval[1]) {
                newInterval[0] = interval[0];
                continue;
            }
            if (newInterval[0] <= interval[0] && newInterval[1] <= interval[1]) {
                newInterval[1] = interval[1];
                continue;
            }
        }
        if (newInterval[0] != Integer.MAX_VALUE) resList.add(newInterval);
        int[][] res = new int[resList.size()][2];
        for (int i=0; i<resList.size(); i++) {
            res[i][0] = resList.get(i)[0];
            res[i][1] = resList.get(i)[1];
        }
        return res;
    }
}
