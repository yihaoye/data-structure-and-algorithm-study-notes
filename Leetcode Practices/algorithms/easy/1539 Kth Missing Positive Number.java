/**
Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.

Find the kth positive integer that is missing from this array.

 

Example 1:

Input: arr = [2,3,4,7,11], k = 5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
Example 2:

Input: arr = [1,2,3,4], k = 2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.
 

Constraints:

1 <= arr.length <= 1000
1 <= arr[i] <= 1000
1 <= k <= 1000
arr[i] < arr[j] for 1 <= i < j <= arr.length

 */



// Other's Solution:
class Solution {
    public int findKthPositive(int[] arr, int k) {
        // 二分搜索，设第 i 个元素已欠缺 p(i) 个数字时，找到第一个 p(i) >= k 的元素的前一个元素，即 res = k - p(i-1) + arr[i-1]，因为已知 arr 严格单调递增（无重复）且元素 >= 1，所以 p(i) = arr[i] - i - 1，另外注意特殊情况 i == 0 或 i == arr.len 时需特殊处理 -- https://leetcode-cn.com/problems/kth-missing-positive-number/solution/di-k-ge-que-shi-de-zheng-zheng-shu-by-leetcode-sol/
        // Time O(logN), Space: O(1)
        if (arr[0] > k) return k; // 特殊情况 i == 0

        int l = 0, r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            int x = mid < arr.length ? arr[mid] : Integer.MAX_VALUE; // 特殊情况 i == arr.len
            
            int p = x - mid - 1; // try to find p(i), i.e. p(l)
            if (p >= k) r = mid;
            else l = mid + 1;
        }

        int p_ = arr[l-1] - (l-1) - 1; // p(i-1)
        return k - p_ + arr[l-1];
    }
}
