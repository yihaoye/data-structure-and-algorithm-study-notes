/**
Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.

The test cases are generated so that the answer can fit in a 32-bit integer.

 

Example 1:

Input: nums = [1,2,3], target = 4
Output: 7
Explanation:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
Note that different sequences are counted as different combinations.
Example 2:

Input: nums = [9], target = 3
Output: 0
 

Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 1000
All the elements of nums are unique.
1 <= target <= 1000
 

Follow up: What if negative numbers are allowed in the given array? How does it change the problem? What limitation we need to add to the question to allow negative numbers?
 */



// My Solution:
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // 排序 + DP（看 tag 提示）- dp[i] means how many combination to sum to i
        // Time: O(K*N), Space: O(Max(K, logN)) - 空间 logN 是排序需要，K == target
        int[] dp = new int[target+1];
        dp[0] = 1;
        Arrays.sort(nums);
        for (int i=1; i<=target; i++) {
            for (int j=0; j<nums.length && nums[j]<=i; j++) {
                dp[i] += dp[i-nums[j]];
            }
        }
        return dp[target];
    }
}
