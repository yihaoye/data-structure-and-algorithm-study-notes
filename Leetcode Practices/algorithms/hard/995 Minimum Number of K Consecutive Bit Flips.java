/**
You are given a binary array nums and an integer k.

A k-bit flip is choosing a subarray of length k from nums and simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.

Return the minimum number of k-bit flips required so that there is no 0 in the array. If it is not possible, return -1.

A subarray is a contiguous part of an array.

 

Example 1:

Input: nums = [0,1,0], k = 1
Output: 2
Explanation: Flip nums[0], then flip nums[2].
Example 2:

Input: nums = [1,1,0], k = 2
Output: -1
Explanation: No matter how we flip subarrays of size 2, we cannot make the array become [1,1,1].
Example 3:

Input: nums = [0,0,0,1,0,1,1,0], k = 3
Output: 3
Explanation: 
Flip nums[0],nums[1],nums[2]: nums becomes [1,1,1,1,0,1,1,0]
Flip nums[4],nums[5],nums[6]: nums becomes [1,1,1,1,1,0,0,0]
Flip nums[5],nums[6],nums[7]: nums becomes [1,1,1,1,1,1,1,1]
 

Constraints:

1 <= nums.length <= 10^5
1 <= k <= nums.length
 */



// My Solution:
class Solution {
    public int minKBitFlips(int[] nums, int k) {
        // inspired by - https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/solutions/238609/java-c-python-one-pass-and-o-1-space/?orderBy=most_votes
        // 数学 + slide window + 模拟（贪心） + 差分数组(记录每个点的最终翻转次数) - 并且如果原来是 0 的则需要被翻奇数次，原来是 1 的需要被翻偶数次，而且同一段区域不应该被翻超过一次，因为翻两次就变回原来了（即使中间隔一次重叠非相同的区域翻也一样，因为假设该段区域 A 翻了 i 次，重叠区 B 翻了 j 次，则实际 A 非重叠区翻了 i/2 次，重叠区翻了 (i+j)/2 次，B 非重叠区翻了 j/2 次，若 A、B 区均奇数次或均偶数次，则重叠区为 0 次，若 A 区或 B 区一个为奇数次一个为偶数次，则重叠区为 1 次）
        // Time: O(N)
        int n = nums.length, nextFlip = findNextZero(nums, 0), res = 0;
        if (nextFlip == -1) return 0;

        int[] diff = new int[n], restore = new int[n];
        int nextRestore = nextFlip;
        while (nextFlip != -1) {
            int curFlip = nextFlip; nextFlip = -1;
            if (curFlip + k > n) continue;

            res++;
            diff[curFlip]++;
            if (curFlip + k != n) diff[curFlip + k]--; // curFlip + k < n

            // restore and check
            for (; nextRestore < curFlip + k; nextRestore++) {
                restore[nextRestore] = (nextRestore > 0 ? restore[nextRestore-1] : 0) + diff[nextRestore]; // 根据差分恢复前面每个元素翻转次数
                if (nextFlip == -1 && ((nums[nextRestore] == 0 && restore[nextRestore] % 2 == 0) || (nums[nextRestore] == 1 && restore[nextRestore] % 2 == 1))) {
                    nextFlip = nextRestore;
                    break; // 减少冗余的恢复操作
                }
            }
            if (nextFlip != -1) nextRestore = nextFlip;
            else nextFlip = findNextZero(nums, nextRestore);
        }
        
        // restore and check the left
        for (; nextRestore < n; nextRestore++) {
            restore[nextRestore] = (nextRestore > 0 ? restore[nextRestore-1] : 0) + diff[nextRestore];
            if ((nums[nextRestore] == 0 && restore[nextRestore] % 2 == 0) || (nums[nextRestore] == 1 && restore[nextRestore] % 2 == 1)) return -1;
        }

        return res;
    }

    private int findNextZero(int[] nums, int start) {
        for (int i=start; i<nums.length; i++) {
            if (nums[i] == 0) return i;
        }
        return -1;
    }
}
