/*
Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

Note:

You may assume the interval's end point is always bigger than its start point.
Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
 

Example 1:

Input: [ [1,2], [2,3], [3,4], [1,3] ]

Output: 1

Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
 

Example 2:

Input: [ [1,2], [1,2], [1,2] ]

Output: 2

Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
 

Example 3:

Input: [ [1,2], [2,3] ]

Output: 0

Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
*/



// Other's Solution: (Greedy)
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {        
        if (intervals.length == 0) return 0;
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        int curEnd = intervals[0][1];
        int count = 1;

        for (int[] interval : intervals) {
            if (interval[0] >= curEnd) {
                count++;
                curEnd = interval[1];
            }
        }

        return intervals.length - count;
    }
}



// My Solution: (Greedy) (last 2 test case not passed yet, basically concept is the similar as above but sort different dimension in the beginning)
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        // remove larger interval arr
        // so remain the smallest int[1] for each key int[0], iterate through int[0] (sort first) to find longest combination
        
        if (intervals.length == 0) return 0;
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> arrList = new ArrayList<>();
        int tempKey = intervals[0][0], keyMinVal = intervals[0][1];
        for (int[] interval : intervals) {
            if (interval[0] != tempKey) {
                arrList.add(new int[]{tempKey, keyMinVal});
                tempKey = interval[0];
                keyMinVal = interval[1];
            } else if (interval[1] < keyMinVal) {
                keyMinVal = interval[1];
            }
        }
        arrList.add(new int[]{tempKey, keyMinVal});
        
        int maxCombination = 0;
        for (int i=0; i < arrList.size(); i++) {
            int tempRightBoundary = arrList.get(i)[1];
            int combination = 1;
            for (int j=i; j < arrList.size(); j++) {
                if (tempRightBoundary <= arrList.get(j)[0]) {
                    tempRightBoundary = arrList.get(j)[1];
                    combination++;
                }
            }
            if (combination > maxCombination) maxCombination = combination;
        }
        
        return intervals.length - maxCombination;
    }
}