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



// Other's Solution:
class Solution {
    public int findMinDifference(List<String> timePoints) {
        // 鸽巢原理，一共有 24×60=1440 种不同的时间。由鸽巢原理可知，如果 timePoints 的长度超过 14401440，那么必然会有两个相同的时间，此时可以直接返回 0 - https://leetcode-cn.com/problems/minimum-time-difference/solution/zui-xiao-shi-jian-chai-by-leetcode-solut-xolj/
        // 时间复杂度：O(Min(N, C)*logMin(N, C))，其中 N 是数组 timePoints 的长度，C=24×60=1440。由于当 N>C 时直接返回 0，排序时的 N 不会超过 C，因此排序需要 O(Min(N, C)*logMin(N, C)) 的时间。空间复杂度：O(Min(N, C)) 为 Java 排序需要的空间。
        int n = timePoints.size();
        if (n > 1440) return 0;

        Collections.sort(timePoints);
        int res = 720; // 最大差只可能是 1440 / 2
        int firstMinutes = toMinutes(timePoints.get(0));
        int preMinutes = firstMinutes;
        for (int i = 1; i < n; ++i) {
            int minutes = toMinutes(timePoints.get(i));
            res = Math.min(res, minutes - preMinutes); // 相邻时间的时间差
            preMinutes = minutes;
        }
        res = Math.min(res, firstMinutes + 1440 - preMinutes); // 首尾时间的时间差
        return res;
    }

    public int toMinutes(String t) {
        return ((t.charAt(0) - '0') * 10 + (t.charAt(1) - '0')) * 60 + (t.charAt(3) - '0') * 10 + (t.charAt(4) - '0');
    }
}
