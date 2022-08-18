/**
Given an integer n, return any array containing n unique integers such that they add up to 0.

 

Example 1:

Input: n = 5
Output: [-7,-1,1,3,4]
Explanation: These arrays also are accepted [-5,-1,1,2,3] , [-3,-1,2,-2,4].
Example 2:

Input: n = 3
Output: [-1,0,1]
Example 3:

Input: n = 1
Output: [0]
 

Constraints:

1 <= n <= 1000
 */



// My Solution:
class Solution {
    public int[] sumZero(int n) {
        // 双指针，检测 n 是否偶数，是则中间不留 0，否则留 0，其余两边各自 +1 -1
        int[] res = new int[n];
        if (n == 1) return res;
        int left = n / 2 - 1, right = n % 2 == 0 ? n / 2 : n / 2 + 1, lVal = -1, rVal = 1;
        while (left >= 0 && right <= n-1) {
            res[left] = lVal;
            res[right] = rVal;
            left--; lVal--; right++; rVal++;
        }
        
        return res;
    }
}
