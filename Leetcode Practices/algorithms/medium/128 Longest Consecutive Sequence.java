/**
Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in O(n) time.

 

Example 1:

Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
Example 2:

Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
 

Constraints:

0 <= nums.length <= 10^5
-10^9 <= nums[i] <= 10^9
 */



// Other's Solution:
class Solution {
    public int longestConsecutive(int[] nums) {
        // HashMap + 双指针 - https://leetcode.com/problems/longest-consecutive-sequence/discuss/41055/My-really-simple-Java-O(n)-solution-Accepted
        // Time: O(N), Space: O(N)
        int res = 0;
        HashMap<Integer, Integer> lMap = new HashMap<Integer, Integer>(); // 以 Key 为左边缘的最长连续区间的长度 Value
        HashMap<Integer, Integer> rMap = new HashMap<Integer, Integer>(); // 以 Key 为右边缘的最长连续区间的长度 Value
        for (int num : nums) {
            if (!lMap.containsKey(num)) { // 此时 rMap 也肯定不包含 num
                int rightLenOfNum = lMap.getOrDefault(num+1, 0);
                int leftLenOfNum = rMap.getOrDefault(num-1, 0);
                res = Math.max(res, leftLenOfNum + 1 + rightLenOfNum);

                lMap.put(num, rightLenOfNum + 1);
                rMap.put(num, leftLenOfNum + 1);

                lMap.put(num-leftLenOfNum, leftLenOfNum + 1 + rightLenOfNum); // 不需要做 max 对比，因为总是会更新为最新最长的连续区间长度
                rMap.put(num+rightLenOfNum, leftLenOfNum + 1 + rightLenOfNum); // 不需要做 max 对比，因为总是会更新为最新最长的连续区间长度
            }
        }
        return res;
    }
}
