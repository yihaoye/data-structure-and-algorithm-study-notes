/*
Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
Example 2:

Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

 

Constraints:

intervals[i][0] <= intervals[i][1]
*/



// My Solution:
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int j = intervals.length;
        for (int i=0; i<intervals.length-1; i++) {
            if (intervals[i][1] >= intervals[i+1][0]) {
                if (intervals[i][1] <= intervals[i+1][1]) intervals[i][1] = intervals[i+1][1];
                else intervals[i+1][1] = intervals[i][1];
                intervals[i+1][0] = intervals[i][0];
                j--;
            }
        }
        
        int[][] res = new int[j][2];
        for (int i=0, k=0; i<intervals.length; i++) {
            if (i == 0) {
                res[k][0] = intervals[i][0];
                res[k][1] = intervals[i][1];
                k++;
            } else if (res[k-1][0] == intervals[i][0] && res[k-1][1] != intervals[i][1]) {
                res[k-1][1] = intervals[i][1];
            } else if (res[k-1][0] != intervals[i][0]) {
                res[k][0] = intervals[i][0];
                res[k][1] = intervals[i][1];
                k++;
            }
        }
        return res;
    }
}



// My Solution 2:
class Solution {
    public int[][] merge(int[][] intervals) {
        // 排序
        // Time: O(logN), Space: O(N) - resList 或 Java Arrays.sort（非基本类型）
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // 升序排序
        List<int[]> resList = new ArrayList<>();
        for (int[] interval : intervals) {
            int[] last = resList.size() == 0 ? null : resList.get(resList.size()-1);
            
            if (last == null || last[1] < interval[0]) {
                resList.add(interval.clone());
                continue;
            }
            if (last[1] >= interval[0] && last[1] < interval[1]) {
                last[1] = interval[1];
            }
        }
        int[][] res = new int[resList.size()][2];
        resList.toArray(res);
        return res;
    }
}
