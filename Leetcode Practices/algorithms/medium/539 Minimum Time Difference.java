/*
Given a list of 24-hour clock time points in "HH:MM" format, return the minimum minutes difference between any two time-points in the list.
 

Example 1:

Input: timePoints = ["23:59","00:00"]
Output: 1
Example 2:

Input: timePoints = ["00:00","23:59","00:00"]
Output: 0
 

Constraints:

2 <= timePoints.length <= 2 * 104
timePoints[i] is in the format "HH:MM".
*/





// Other's Solution:
class Solution {
    public int findMinDifference(List<String> timePoints) {
        /*
            排序 - https://leetcode-cn.com/problems/minimum-time-difference/solution/gong-shui-san-xie-jian-dan-mo-ni-ti-by-a-eygg/
            时间复杂度：O(n*logn)，空间复杂度：O(n)
        */
        int n = timePoints.size()*2;
        int[] nums = new int[n];
        for (int i = 0, idx = 0; i < n/2; i++, idx += 2) {
            String[] ss = timePoints.get(i).split(":");
            int h = Integer.parseInt(ss[0]), m = Integer.parseInt(ss[1]);
            nums[idx] = h * 60 + m;
            nums[idx+1] = nums[idx] + 1440;
        }
        Arrays.sort(nums);
        int res = nums[1] - nums[0];
        for (int i=0; i < n-1; i++) res = Math.min(res, nums[i+1] - nums[i]);
        return res;
    }
}
