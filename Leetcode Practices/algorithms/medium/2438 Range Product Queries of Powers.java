/**
Given a positive integer n, there exists a 0-indexed array called powers, composed of the minimum number of powers of 2 that sum to n. The array is sorted in non-decreasing order, and there is only one way to form the array.

You are also given a 0-indexed 2D integer array queries, where queries[i] = [lefti, righti]. Each queries[i] represents a query where you have to find the product of all powers[j] with lefti <= j <= righti.

Return an array answers, equal in length to queries, where answers[i] is the answer to the ith query. Since the answer to the ith query may be too large, each answers[i] should be returned modulo 109 + 7.

 

Example 1:

Input: n = 15, queries = [[0,1],[2,2],[0,3]]
Output: [2,4,64]
Explanation:
For n = 15, powers = [1,2,4,8]. It can be shown that powers cannot be a smaller size.
Answer to 1st query: powers[0] * powers[1] = 1 * 2 = 2.
Answer to 2nd query: powers[2] = 4.
Answer to 3rd query: powers[0] * powers[1] * powers[2] * powers[3] = 1 * 2 * 4 * 8 = 64.
Each answer modulo 109 + 7 yields the same answer, so [2,4,64] is returned.
Example 2:

Input: n = 2, queries = [[0,0]]
Output: [2]
Explanation:
For n = 2, powers = [2].
The answer to the only query is powers[0] = 2. The answer modulo 109 + 7 is the same, so [2] is returned.
 

Constraints:

1 <= n <= 10^9
1 <= queries.length <= 10^5
0 <= starti <= endi < powers.length
 */



// My Solution:
class Solution {
    public int[] productQueries(int n, int[][] queries) {
        // 二进制 + 数学 + 前缀和（DP）
        int MOD = (int) 1e9 + 7;
        int[] powers = n2Powers(n); // powers store exponent instead of result(2^exponent), since it can better compress and easy calculate for later steps
        int m = powers.length;
        int[] prefixSum = new int[m];
        for (int i=0; i<m; i++) prefixSum[i] = (i > 0 ? prefixSum[i-1] : 0) + powers[i];
        
        int[] calCache = new int[prefixSum[m-1] + 1]; // calCache[i] means (2^i % (1e9 + 7)) -> use dp here for reduce duplicate calculation for performance
        for (int i=0; i<calCache.length; i++) calCache[i] = i > 0 ? (calCache[i-1] * 2 % MOD) : 1;

        int[] res = new int[queries.length];
        for (int i=0; i<res.length; i++) {
            int l = queries[i][0]; int r = queries[i][1];
            res[i] = calCache[prefixSum[r] - (l > 0 ? prefixSum[l-1] : 0)];
        }
        return res;
    }

    private int[] n2Powers(int n) {
        int idx = 0, exponent = 0;
        int[] res = new int[Integer.bitCount(n)];
        while (n > 0) {
            if ((n & 1) == 1) res[idx++] = exponent;
            exponent++;
            n /= 2;
        }
        return res;
    }
}
