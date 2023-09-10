/*
You are given two integer arrays nums1 and nums2 sorted in non-decreasing order and an integer k.

Define a pair (u, v) which consists of one element from the first array and one element from the second array.

Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

 

Example 1:

Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
Example 2:

Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [[1,1],[1,1]]
Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
Example 3:

Input: nums1 = [1,2], nums2 = [3], k = 3
Output: [[1,3],[2,3]]
Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
 

Constraints:

1 <= nums1.length, nums2.length <= 10^5
-10^9 <= nums1[i], nums2[i] <= 10^9
nums1 and nums2 both are sorted in non-decreasing order.
1 <= k <= 10^4
*/



// My Solution:
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        // 多路归并 - inspired by https://leetcode.com/problems/find-k-pairs-with-smallest-sums/solutions/84551/simple-java-o-klogk-solution-with-explanation/
        // Time: O(N*logN), Space: O(N)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        List<List<Integer>> res = new ArrayList<>();
        for (int i=0; i<nums1.length; i++) { // 初始化 k sort list（均为 nums1[i] 配对 nums2[0]）
            pq.offer(new int[]{i, 0, nums1[i] + nums2[0]});
        }

        while (res.size() < k && !pq.isEmpty()) { // merge k sort list（nums1[i] 的 i 不变，对当前 nums2[j] 的 j++）
            int[] nxtMin = pq.poll();
            res.add(new ArrayList<>(Arrays.asList(nums1[nxtMin[0]], nums2[nxtMin[1]])));
            if (nxtMin[1] < nums2.length - 1) pq.offer(new int[]{nxtMin[0], nxtMin[1] + 1, nums1[nxtMin[0]] + nums2[nxtMin[1] + 1]});
        }
        
        return res;
    }
}
