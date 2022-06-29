/**
Given the availability time slots arrays slots1 and slots2 of two people and a meeting duration duration, return the earliest time slot that works for both of them and is of duration duration.

If there is no common time slot that satisfies the requirements, return an empty array.

The format of a time slot is an array of two elements [start, end] representing an inclusive time range from start to end.

It is guaranteed that no two availability slots of the same person intersect with each other. That is, for any two time slots [start1, end1] and [start2, end2] of the same person, either start1 > end2 or start2 > end1.

 

Example 1:

Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
Output: [60,68]
Example 2:

Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 12
Output: []
 

Constraints:

1 <= slots1.length, slots2.length <= 10^4
slots1[i].length, slots2[i].length == 2
slots1[i][0] < slots1[i][1]
slots2[i][0] < slots2[i][1]
0 <= slots1[i][j], slots2[i][j] <= 10^9
1 <= duration <= 10^6
 */



// My Solution:
class Solution {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        // 双指针
        // Time: O(N*logN) - 排序决定, Space: O(logN) - 排序决定
        List<Integer> res = new ArrayList<>();
        Arrays.sort(slots1, (a, b) -> (a[0] == b[0]) ? (a[1] - b[1]) : (a[0] - b[0]));
        Arrays.sort(slots2, (a, b) -> (a[0] == b[0]) ? (a[1] - b[1]) : (a[0] - b[0]));
        for (int i=0, j=0; i<slots1.length && j<slots2.length;) {
            int start = Math.max(slots1[i][0], slots2[j][0]);
            int end = Math.min(slots1[i][1], slots2[j][1]);
            if (end - start >= duration) {
                res.add(start);
                res.add(start + duration);
                return res;
            }
            if (slots1[i][1] < slots2[j][0] || slots1[i][1] < slots2[j][1]) i++;
            else if (slots2[j][1] < slots1[i][0] || slots2[j][1] < slots1[i][1]) j++;
            else if (slots1[i][1] == slots2[j][1]) {
                i++;
                j++;
            }
        }
        return res;
    }
}
