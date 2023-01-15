/**
Given an integer array nums and an integer k, return the number of good subarrays of nums.

A subarray arr is good if it there are at least k pairs of indices (i, j) such that i < j and arr[i] == arr[j].

A subarray is a contiguous non-empty sequence of elements within an array.

 

Example 1:

Input: nums = [1,1,1,1,1], k = 10
Output: 1
Explanation: The only good subarray is the array nums itself.
Example 2:

Input: nums = [3,1,4,3,2,2,4], k = 2
Output: 4
Explanation: There are 4 different good subarrays:
- [3,1,4,3,2,2] that has 2 pairs.
- [3,1,4,3,2,2,4] that has 3 pairs.
- [1,4,3,2,2,4] that has 2 pairs.
- [4,3,2,2,4] that has 2 pairs.
 

Constraints:

1 <= nums.length <= 10^5
1 <= nums[i], k <= 10^9
 */



// My Solution:
class Solution {
    public long countGood(int[] nums, int k) {
        // Two Pointer + HashMap<Integer, Queue> - <num, <indexs>>
        long res = 0;
        int left = 0, right = 0, curPairs = 0, n = nums.length;
        Map<Integer, Queue<Integer>> map = new HashMap<>();
        map.computeIfAbsent(nums[0], x -> new LinkedList<>()).add(0);
        while (right < n-1) {
            if (curPairs >= k) {
                res += (n-right);
                map.get(nums[left]).poll();
                curPairs -= map.get(nums[left]).size();
                left++;
            } else {
                right++;
                map.computeIfAbsent(nums[right], x -> new LinkedList<>()).add(right);
                curPairs += map.get(nums[right]).size() - 1;
            }
        }
        
        while (left < right && curPairs >= k) {
            res++;
            map.get(nums[left]).poll();
            curPairs -= map.get(nums[left]).size();
            left++;
        }
        
        return res;
    }
}
