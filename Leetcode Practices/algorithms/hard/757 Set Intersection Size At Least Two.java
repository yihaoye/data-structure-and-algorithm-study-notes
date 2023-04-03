/**
You are given a 2D integer array intervals where intervals[i] = [starti, endi] represents all the integers from starti to endi inclusively.

A containing set is an array nums where each interval from intervals has at least two integers in nums.

For example, if intervals = [[1,3], [3,7], [8,9]], then [1,2,4,7,8,9] and [2,3,4,8,9] are containing sets.
Return the minimum possible size of a containing set.

 

Example 1:

Input: intervals = [[1,3],[3,7],[8,9]]
Output: 5
Explanation: let nums = [2, 3, 4, 8, 9].
It can be shown that there cannot be any containing array of size 4.
Example 2:

Input: intervals = [[1,3],[1,4],[2,5],[3,5]]
Output: 3
Explanation: let nums = [2, 3, 4].
It can be shown that there cannot be any containing array of size 2.
Example 3:

Input: intervals = [[1,2],[2,3],[2,4],[4,5]]
Output: 5
Explanation: let nums = [1, 2, 3, 4, 5].
It can be shown that there cannot be any containing array of size 4.
 

Constraints:

1 <= intervals.length <= 3000
intervals[i].length == 2
0 <= starti < endi <= 10^8
 */



// Other's Solution:
class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        // 排序 + 贪心 - https://leetcode.com/problems/set-intersection-size-at-least-two/solutions/113085/ever-wonder-why-the-greedy-algorithm-works-here-is-the-explanation/
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return (a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
            }
        });
        
        int m = 0, largest = -1, second = -1;
        
        for (int[] interval : intervals) {
            int a = interval[0], b = interval[1];
        
            boolean is_largest_in = (a <= largest);
            boolean is_second_in = (a <= second);
        
            if (is_largest_in && is_second_in) continue;
            
            m += (is_largest_in ? 1 : 2);
            
            second = (is_largest_in ? largest : b - 1);
            largest = b;
        }
        
        return m;
    }
}
