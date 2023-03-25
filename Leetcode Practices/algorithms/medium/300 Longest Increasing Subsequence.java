/*
Given an integer array nums, return the length of the longest strictly increasing subsequence.

A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].

 

Example 1:

Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
Example 2:

Input: nums = [0,1,0,3,2,3]
Output: 4
Example 3:

Input: nums = [7,7,7,7,7,7,7]
Output: 1
 

Constraints:

1 <= nums.length <= 2500
-104 <= nums[i] <= 104
 

Follow up: Can you come up with an algorithm that runs in O(n log(n)) time complexity?
*/



// Other's Solution:
class Solution {
    public int lengthOfLIS(int[] nums) {
        // https://leetcode.com/problems/longest-increasing-subsequence/solutions/74824/java-python-binary-search-o-nlogn-time-with-explanation/?orderBy=most_votes
        // 耐心排序（贪心+动态规划+二分查找）
        // Time: O(N*logN)
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int i = 0, j = size;
            while (i != j) {
                int m = (i + j) / 2;
                if (tails[m] < x) i = m + 1;
                else j = m;
            }
            tails[i] = x;
            if (i == size) ++size;
        }
        return size;
    }
}



// My Solution:
class Solution {
    public int lengthOfLIS(int[] nums) {
        /*
            a: 思路 - DP，维持一个哈希表，以 nums2 为开始，nums2 即为 nums 的首 2 位子数组的哈希表，哈希表包含了 nums2 每个元素作为结尾的 LIS 值（Key 为元素，Val 为其 LIS），然后计算 nums3 时遍历 nums2，若 nums 3 的新元素大于 nums2 的被遍历元素时则对该元素的 LIS 值加一为 nums3 可得的 max 临时值，结尾时 put(nums3新元素, max)。
            b: Time: O(N^2)，Space: O(N)
        */
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 1; // final max
        for (int num : nums) {
            int tempMax = 1;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getKey() < num && entry.getValue() >= tempMax) {
                    tempMax = entry.getValue() + 1;
                }
            }
            map.put(num, tempMax);
            res = Math.max(res, tempMax);
        }
        
        return res;
    }
}



// Other's Solution:
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int res = 0;
        
        for (int i=0; i<nums.length; i++) {
            for (int j=0; j<i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        
        return res;
    }
}
