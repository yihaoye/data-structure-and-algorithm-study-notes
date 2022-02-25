/*
Given an integer array nums and two integers k and t, return true if there are two distinct indices i and j in the array such that abs(nums[i] - nums[j]) <= t and abs(i - j) <= k.

 

Example 1:

Input: nums = [1,2,3,1], k = 3, t = 0
Output: true
Example 2:

Input: nums = [1,0,1,1], k = 1, t = 2
Output: true
Example 3:

Input: nums = [1,5,9,1,5,9], k = 2, t = 3
Output: false
 

Constraints:

1 <= nums.length <= 2 * 104
-231 <= nums[i] <= 231 - 1
0 <= k <= 104
0 <= t <= 231 - 1

*/





// Other's Solution:
class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        /*
            滑动窗口+有序集合
            https://leetcode-cn.com/problems/contains-duplicate-iii/solution/cun-zai-zhong-fu-yuan-su-iii-by-leetcode-bbkt/
            时间复杂度：O(n*log(min(n,k)))，空间复杂度：O(min(n,k))
        */
        int n = nums.length;
        TreeSet<Long> set = new TreeSet<Long>();
        for (int i = 0; i < n; i++) {
            Long ceiling = set.ceiling((long) nums[i] - (long) t); // 二分查找
            if (ceiling != null && ceiling <= (long) nums[i] + (long) t) return true;

            set.add((long) nums[i]);
            if (i >= k) set.remove((long) nums[i-k]); // 如果当前有序集合中存在相同元素，那么此前将直接返回 true，因此这里的有序集合无需处理相同元素的情况。
        }
        return false;
    }
}
