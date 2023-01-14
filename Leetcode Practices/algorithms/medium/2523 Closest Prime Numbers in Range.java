/**
Given two positive integers left and right, find the two integers num1 and num2 such that:

left <= nums1 < nums2 <= right .
nums1 and nums2 are both prime numbers.
nums2 - nums1 is the minimum amongst all other pairs satisfying the above conditions.
Return the positive integer array ans = [nums1, nums2]. If there are multiple pairs satisfying these conditions, return the one with the minimum nums1 value or [-1, -1] if such numbers do not exist.

A number greater than 1 is called prime if it is only divisible by 1 and itself.

 

Example 1:

Input: left = 10, right = 19
Output: [11,13]
Explanation: The prime numbers between 10 and 19 are 11, 13, 17, and 19.
The closest gap between any pair is 2, which can be achieved by [11,13] or [17,19].
Since 11 is smaller than 17, we return the first pair.
Example 2:

Input: left = 4, right = 6
Output: [-1,-1]
Explanation: There exists only one prime number in the given range, so the conditions cannot be satisfied.
 

Constraints:

1 <= left <= right <= 10^6
 */



// My Solution:
class Solution {
    public int[] closestPrimes(int left, int right) {
        // 埃氏筛 + 优先队列
        PriorityQueue<Integer> primesPq = getPrimes(right + 1);
        int[] res = new int[]{-1, -1};
        int lastPrime = -1;
        while (!primesPq.isEmpty()) {
            int curPrime = primesPq.poll();
            if (curPrime < left) continue;
            if (lastPrime != -1 && (res[1] == -1 || res[1] - res[0] > curPrime - lastPrime)) res = new int[]{lastPrime, curPrime};
            lastPrime = curPrime;
        }

        return res;
    }

    private PriorityQueue<Integer> getPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        PriorityQueue<Integer> res = new PriorityQueue<>();
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                res.offer(i);
                for (int j = 2; i*j < n; j++) notPrime[i*j] = true;
            }
        }
        return res;
    }
}
