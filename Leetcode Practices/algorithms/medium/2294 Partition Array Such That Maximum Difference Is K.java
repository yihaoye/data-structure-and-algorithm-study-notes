/**
You are given an integer array nums and an integer k. You may partition nums into one or more subsequences such that each element in nums appears in exactly one of the subsequences.

Return the minimum number of subsequences needed such that the difference between the maximum and minimum values in each subsequence is at most k.

A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

 

Example 1:

Input: nums = [3,6,1,2,5], k = 2
Output: 2
Explanation:
We can partition nums into the two subsequences [3,1,2] and [6,5].
The difference between the maximum and minimum value in the first subsequence is 3 - 1 = 2.
The difference between the maximum and minimum value in the second subsequence is 6 - 5 = 1.
Since two subsequences were created, we return 2. It can be shown that 2 is the minimum number of subsequences needed.
Example 2:

Input: nums = [1,2,3], k = 1
Output: 2
Explanation:
We can partition nums into the two subsequences [1,2] and [3].
The difference between the maximum and minimum value in the first subsequence is 2 - 1 = 1.
The difference between the maximum and minimum value in the second subsequence is 3 - 3 = 0.
Since two subsequences were created, we return 2. Note that another optimal solution is to partition nums into the two subsequences [1] and [2,3].
Example 3:

Input: nums = [2,2,4,5], k = 0
Output: 3
Explanation:
We can partition nums into the three subsequences [2,2], [4], and [5].
The difference between the maximum and minimum value in the first subsequences is 2 - 2 = 0.
The difference between the maximum and minimum value in the second subsequences is 4 - 4 = 0.
The difference between the maximum and minimum value in the third subsequences is 5 - 5 = 0.
Since three subsequences were created, we return 3. It can be shown that 3 is the minimum number of subsequences needed.
 

Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 105
0 <= k <= 105
 */



// My Solution:
class Solution {
    public int partitionArray(int[] nums, int k) {
        // 排序 + 贪心 + 二分搜索 - 获取最大与最小值的差为 diff，res = Math.max(nums.length, diff) - 即 nums[i] 相同的值都在一个数组里，此时使用  k' == 0，然后不断增加 k' 逼近 k，检查每个 k' 的 tmpRes（使用到二分搜索） 是否 < res 直到试完所有 k，可以借助 TreeSet（此题不用在意重复值，只需在意每个值一次）来进行检查和一开始的排序
        // 补充，不需要从 0 试到 k，只需要试最大 k 即可，且也不需要一开始 res 的初始化即 max(len, diff)
        TreeSet<Integer> sortedSet = new TreeSet<>();
        for (int num : nums) sortedSet.add(num);
        // int res = Math.min(nums.length, sortedSet.last() - sortedSet.first() + 1);
        // for (int tmpK=0; tmpK<=k; tmpK++) {
        //     res = Math.min(res, cal(tmpK, sortedSet));
        //     if (res == 1) break;
        // }
        int res = cal(k, sortedSet);
        
        return res;
    }
    
    private int cal(int tmpK, TreeSet<Integer> sortedSet) {
        int res = 0;
        Integer start = sortedSet.first();
        while (start != null) {
            start = sortedSet.ceiling(start + tmpK + 1);
            res++;
        }
        
        return res;
    }
}
