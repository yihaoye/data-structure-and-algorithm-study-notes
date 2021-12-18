/*
You are given a sorted integer array arr containing 1 and prime numbers, where all the integers of arr are unique. You are also given an integer k.

For every i and j where 0 <= i < j < arr.length, we consider the fraction arr[i] / arr[j].

Return the kth smallest fraction considered. Return your answer as an array of integers of size 2, where answer[0] == arr[i] and answer[1] == arr[j].

 

Example 1:

Input: arr = [1,2,3,5], k = 3
Output: [2,5]
Explanation: The fractions to be considered in sorted order are:
1/5, 1/3, 2/5, 1/2, 3/5, and 2/3.
The third fraction is 2/5.
Example 2:

Input: arr = [1,7], k = 1
Output: [1,7]
 

Constraints:

2 <= arr.length <= 1000
1 <= arr[i] <= 3 * 104
arr[0] == 1
arr[i] is a prime number for i > 0.
All the numbers of arr are unique and sorted in strictly increasing order.
1 <= k <= arr.length * (arr.length - 1) / 2
*/



// My Solution:
class Solution {
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        /*
            看 topic 提示使用优先队列，保持一个长度为 k 的优先队列，按大到小排列，暴力遍历所有组合，若队列长度小于 k 则直接存入，否则每次取队列最大值对比，存较小的那个，最后队列的头元素即答案。
            时间复杂度 O(N^2)，空间复杂度 O(k)
        */
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[0]*1.0/b[1], a[0]*1.0/a[1]));
        for (int i=0; i<arr.length-1; i++) {
            for (int j=i+1; j<arr.length; j++) {
                int[] tmp = new int[]{arr[i], arr[j]};
                if (pq.size() < k) {
                    pq.add(tmp);
                } else {
                    int[] curKth = pq.poll();
                    if (Double.compare(tmp[0]*1.0/tmp[1], curKth[0]*1.0/curKth[1]) < 0) pq.add(tmp);
                    else pq.add(curKth);
                }
            }
        }
        
        return pq.poll();
    }
}
