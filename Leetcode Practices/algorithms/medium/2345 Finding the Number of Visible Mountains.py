"""
You are given a 0-indexed 2D integer array peaks where peaks[i] = [xi, yi] states that mountain i has a peak at coordinates (xi, yi). A mountain can be described as a right-angled isosceles triangle, with its base along the x-axis and a right angle at its peak. More formally, the gradients of ascending and descending the mountain are 1 and -1 respectively.

A mountain is considered visible if its peak does not lie within another mountain (including the border of other mountains).

Return the number of visible mountains.

 

Example 1:
https://assets.leetcode.com/uploads/2022/07/19/ex1.png

Input: peaks = [[2,2],[6,3],[5,4]]
Output: 2
Explanation: The diagram above shows the mountains.
- Mountain 0 is visible since its peak does not lie within another mountain or its sides.
- Mountain 1 is not visible since its peak lies within the side of mountain 2.
- Mountain 2 is visible since its peak does not lie within another mountain or its sides.
There are 2 mountains that are visible.

Example 2:
https://assets.leetcode.com/uploads/2022/07/19/ex2new1.png

Input: peaks = [[1,3],[1,3]]
Output: 0
Explanation: The diagram above shows the mountains (they completely overlap).
Both mountains are not visible since their peaks lie within each other.
 

Constraints:

1 <= peaks.length <= 10^5
peaks[i].length == 2
1 <= xi, yi <= 10^5
"""



# My Solution:
class Solution:
    def visibleMountains(self, peaks: List[List[int]]) -> int:
        # 排序 + 单调栈 + 几何
        count = Counter(tuple(p) for p in peaks)
        peaks.sort(key=lambda x: x[0])
        stack = []
        for curr in peaks:
            while True:
                if not stack:
                    stack.append(curr)
                    break
                visible = self.overlap(curr, stack[-1])
                if visible == 0: # visible 0 意味着只能看到 curr
                    stack.pop()
                if visible == 1: # visible 1 意味着只能看到 stack[-1]
                    break
                if visible == 2: # visible 2 意味着两个都可见
                    stack.append(curr)
                    break
        res = 0
        for p in stack:
            if count[tuple(p)] == 1:
                res += 1
        return res

    def overlap(self, curr: List[int], prev: List[int]) -> int:
        # 因为外面排过序，curr[0] 必然大于 prev[0]
        shift = curr[0] - prev[0]
        if curr[1] - shift >= prev[1]:
            return 0
        elif prev[1] - shift >= curr[1]:
            return 1
        return 2