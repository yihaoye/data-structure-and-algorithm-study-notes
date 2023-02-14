/**
You are given a binary string s, and a 2D integer array queries where queries[i] = [firsti, secondi].

For the ith query, find the shortest substring of s whose decimal value, val, yields secondi when bitwise XORed with firsti. In other words, val ^ firsti == secondi.

The answer to the ith query is the endpoints (0-indexed) of the substring [lefti, righti] or [-1, -1] if no such substring exists. If there are multiple answers, choose the one with the minimum lefti.

Return an array ans where ans[i] = [lefti, righti] is the answer to the ith query.

A substring is a contiguous non-empty sequence of characters within a string.

 

Example 1:

Input: s = "101101", queries = [[0,5],[1,2]]
Output: [[0,2],[2,3]]
Explanation: For the first query the substring in range [0,2] is "101" which has a decimal value of 5, and 5 ^ 0 = 5, hence the answer to the first query is [0,2]. In the second query, the substring in range [2,3] is "11", and has a decimal value of 3, and 3 ^ 1 = 2. So, [2,3] is returned for the second query. 

Example 2:

Input: s = "0101", queries = [[12,8]]
Output: [[-1,-1]]
Explanation: In this example there is no substring that answers the query, hence [-1,-1] is returned.
Example 3:

Input: s = "1", queries = [[4,5]]
Output: [[0,0]]
Explanation: For this example, the substring in range [0,0] has a decimal value of 1, and 1 ^ 4 = 5. So, the answer is [0,0].
 

Constraints:

1 <= s.length <= 10^4
s[i] is either '0' or '1'.
1 <= queries.length <= 10^5
0 <= firsti, secondi <= 10^9
 */



// My Solution:
class Solution {
    public int[][] substringXorQueries(String s, int[][] queries) {
        // HashMap 
        Map<Integer, int[]> map = new HashMap<>(); // <val, leftestRange>
        int n = s.length();
        for (int i=0; i<n; i++) { // get first 0 and 1, the first 1 will be used for next loop purge
            if (s.charAt(i) == '0' && !map.containsKey(0)) map.put(0, new int[]{i, i});
            if (s.charAt(i) == '1' && !map.containsKey(1)) map.put(1, new int[]{i, i});
            if (map.containsKey(0) && map.containsKey(1)) break;
        }
        for (int i=map.getOrDefault(1, new int[]{n, n})[0]; i<n; i++) { // 剪枝优化
            if (s.charAt(i) == '0') continue;
            int val = 1;
            for (int j=i+1; j<Math.min((i+31), n); j++) { // 不需要遍历完，因为整数最多 32 位即可
                val = (val << 1) | (s.charAt(j) - '0');
                if (!map.containsKey(val)) map.put(val, new int[]{i, j});
            }
        }
        int[][] res = new int[queries.length][2];
        for (int i=0; i<queries.length; i++) {
            int val = queries[i][0] ^ queries[i][1];
            res[i] = map.getOrDefault(val, new int[]{-1, -1});
        }
        return res;
    }
}
