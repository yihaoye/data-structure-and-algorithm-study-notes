/**
We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

(Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).  Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

 

Example 1:

Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation: There are a total of three employees, and all common
free time intervals would be [-inf, 1], [3, 4], [10, inf].
We discard any intervals that contain inf as they aren't finite.
Example 2:

Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
Output: [[5,6],[7,9]]
 

Constraints:

1 <= schedule.length , schedule[i].length <= 50
0 <= schedule[i].start < schedule[i].end <= 10^8
 */



/*
// Definition for an Interval.
class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
};
*/
class Solution {
    int OPEN = 0, CLOSE = 1;
    
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        // 扫描线 - https://leetcode-cn.com/problems/employee-free-time/solution/yuan-gong-kong-xian-shi-jian-by-leetcode/
        // 给定一组区间，找出所有员工都不包含的区间。可以使用区间问题中的 “事件” 方法。对于每个区间 [s, e]，可以看作有两个事件：当 time = s 时，employees++；当 time = e 时，employees--。只需关心 employees == 0 的区间。
        // 时间复杂度：O(C*logC)，空间复杂度：O(C)，C 是所有员工的区间数
        List<int[]> events = new ArrayList();
        for (List<Interval> employee : schedule) {
            for (Interval iv : employee) {
                events.add(new int[]{iv.start, OPEN});
                events.add(new int[]{iv.end, CLOSE});
            }
        }

        Collections.sort(events, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]); // a[0] == b[0] 的情况下对 a[1]、b[1] 进行排序，保证 CLOSE 的放在后面，以确保后面的逻辑 - 如果当 employees 减为 0 时（employees 不为 0 时的情况都不特别关心，只维护 employees 与 prev 值）此时 event[1] 必为 CLOSE（如果最后一个是 OPEN 就可能导致 employees 在前面已经减为 0 但实际上该时间点仍有人工作 - 即 employees 在此时间点其实不为 0 但最后结果里已添加了错误的时间区间）且此时的 event 必为最后一个以 event[0] 值 i（假设）为 time 的 event，后面的 event 的 event[0] 均不等于（其实必然大于）i 了
        List<Interval> res = new ArrayList();

        int prev = -1, employees = 0;
        for (int[] event : events) { // event[0] is time, event[1] is command
            if (employees == 0 && prev >= 0) res.add(new Interval(prev, event[0])); // 因为之前的 employees 已为 0 不可能是负数，所以新的 event 的 event[1] 肯定是 OPEN 的，所以每次添加了新的 Interval 后后续都不用考虑再更改（必然是一次性的）
            if (event[1] == OPEN) employees += 1;
            else employees -= 1;
            prev = event[0];
        }

        return res;
    }
}
