/**
You are given an array nums of size n consisting of distinct integers from 1 to n and a positive integer k.

Return the number of non-empty subarrays in nums that have a median equal to k.

Note:

The median of an array is the middle element after sorting the array in ascending order. If the array is of even length, the median is the left middle element.
For example, the median of [2,3,1,4] is 2, and the median of [8,4,3,5,1] is 4.
A subarray is a contiguous part of an array.
 

Example 1:

Input: nums = [3,2,1,4,5], k = 4
Output: 3
Explanation: The subarrays that have a median equal to 4 are: [4], [4,5] and [1,4,5].
Example 2:

Input: nums = [2,3,1], k = 3
Output: 1
Explanation: [3] is the only subarray that has a median equal to 3.
 

Constraints:

n == nums.length
1 <= n <= 10^5
1 <= nums[i], k <= n
The integers in nums are distinct.
 */



// My Solution:
class Solution {
    public int countSubarrays(int[] nums, int k) {
        // 295 类似，但使用左右 count，先定位 k 的位置，往左边延伸，计算其左边的差值的统计（左hashmap），往右边延伸，计算其右边的差值的统计（右hashmap）
        // 数学 - 排列组合
        int indexK = -1, n = nums.length;
        for (int i=0; i<n; i++) {
            if (nums[i] == k) {
                indexK = i;
                break;
            }
        }
        if (indexK == -1) return 0;
        
        Map<Integer, Integer> lCnt = new HashMap<>(); // cnt = krNum-klNum
        Map<Integer, Integer> rCnt = new HashMap<>();
        int tmp = 0, res = 0;
        for (int i=indexK-1; i>=0; i--) {
            if (nums[i] < k) tmp--;
            else tmp++;
            lCnt.put(tmp, lCnt.getOrDefault(tmp, 0) + 1);
        }
        tmp = 0; // reset
        for (int i=indexK+1; i<n; i++) {
            if (nums[i] < k) tmp--;
            else tmp++;
            rCnt.put(tmp, rCnt.getOrDefault(tmp, 0) + 1);
        }
        
        for (int lDiff : lCnt.keySet()) {
            int rDiffCnt = rCnt.getOrDefault(0 - lDiff, 0);
            res += lCnt.get(lDiff) * rDiffCnt;
            rDiffCnt = rCnt.getOrDefault(1 - lDiff, 0);
            res += lCnt.get(lDiff) * rDiffCnt;
        }
        res += lCnt.getOrDefault(0, 0);
        res += rCnt.getOrDefault(0, 0);
        res += lCnt.getOrDefault(1, 0);
        res += rCnt.getOrDefault(1, 0);
        return res + 1;
    }
}
