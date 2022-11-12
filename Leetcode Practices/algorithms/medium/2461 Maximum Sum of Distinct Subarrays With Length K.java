/**
You are given an integer array nums and an integer k. Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:

The length of the subarray is k, and
All the elements of the subarray are distinct.
Return the maximum subarray sum of all the subarrays that meet the conditions. If no subarray meets the conditions, return 0.

A subarray is a contiguous non-empty sequence of elements within an array.

 

Example 1:

Input: nums = [1,5,4,2,9,9,9], k = 3
Output: 15
Explanation: The subarrays of nums with length 3 are:
- [1,5,4] which meets the requirements and has a sum of 10.
- [5,4,2] which meets the requirements and has a sum of 11.
- [4,2,9] which meets the requirements and has a sum of 15.
- [2,9,9] which does not meet the requirements because the element 9 is repeated.
- [9,9,9] which does not meet the requirements because the element 9 is repeated.
We return 15 because it is the maximum subarray sum of all the subarrays that meet the conditions
Example 2:

Input: nums = [4,4,4], k = 3
Output: 0
Explanation: The subarrays of nums with length 3 are:
- [4,4,4] which does not meet the requirements because the element 4 is repeated.
We return 0 because no subarrays meet the conditions.
 

Constraints:

1 <= k <= nums.length <= 10^5
1 <= nums[i] <= 10^5
 */



// My Solution:
class Solution {
    public long maximumSubarraySum(int[] nums, int k) {
        // two pointer + hashmap + flag
        int l = 0, duplicate = 0;
        Map<Integer, Integer> numCnt = new HashMap<>();
        numCnt.put(nums[l], 1);
        long tmp = nums[l], res = k == 1 ? nums[l] : 0;
        for (int i=1; i<nums.length; i++) {
            int numICnt = numCnt.getOrDefault(nums[i], 0);
            if (numICnt > 0) duplicate++;
            numCnt.put(nums[i], numICnt + 1);
            tmp += nums[i];
            
            if (i-l > k-1) {
                tmp -= nums[l];
                int numLCnt = numCnt.get(nums[l]);
                if (numLCnt > 1) duplicate--;
                numCnt.put(nums[l], numLCnt - 1);
                l++;
            }
            if (i-l == k-1 && duplicate == 0) {
                res = Math.max(res, tmp);
            }
        }
        
        return res;
    }
}
