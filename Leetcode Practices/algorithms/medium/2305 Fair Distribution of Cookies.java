/**
You are given an integer array cookies, where cookies[i] denotes the number of cookies in the ith bag. You are also given an integer k that denotes the number of children to distribute all the bags of cookies to. All the cookies in the same bag must go to the same child and cannot be split up.

The unfairness of a distribution is defined as the maximum total cookies obtained by a single child in the distribution.

Return the minimum unfairness of all distributions.

 

Example 1:

Input: cookies = [8,15,10,20,8], k = 2
Output: 31
Explanation: One optimal distribution is [8,15,8] and [10,20]
- The 1st child receives [8,15,8] which has a total of 8 + 15 + 8 = 31 cookies.
- The 2nd child receives [10,20] which has a total of 10 + 20 = 30 cookies.
The unfairness of the distribution is max(31,30) = 31.
It can be shown that there is no distribution with an unfairness less than 31.
Example 2:

Input: cookies = [6,1,3,2,2,4,1,2], k = 3
Output: 7
Explanation: One optimal distribution is [6,1], [3,2,2], and [4,1,2]
- The 1st child receives [6,1] which has a total of 6 + 1 = 7 cookies.
- The 2nd child receives [3,2,2] which has a total of 3 + 2 + 2 = 7 cookies.
- The 3rd child receives [4,1,2] which has a total of 4 + 1 + 2 = 7 cookies.
The unfairness of the distribution is max(7,7,7) = 7.
It can be shown that there is no distribution with an unfairness less than 7.
 

Constraints:

2 <= cookies.length <= 8
1 <= cookies[i] <= 105
2 <= k <= cookies.length
 */



// Other's Solution:
class Solution {
    int res = Integer.MAX_VALUE;
    
    public int distributeCookies(int[] cookies, int k) {
        // 回溯法 - https://leetcode.com/problems/fair-distribution-of-cookies/discuss/2140935/Java-Backtracking-or-With-Explanation
        /*
            Intuition
            Generate all possible cases using backtracking, and maintain the total cookies for every child.

            Steps
                1. use an array children to store the total cookies for each child
                2. for each cookie, try to distribute it to any child
                3. if all the cookies are distributed, find the maximum cookie among children, and update the result

            Complexity
                time: O(k^n) where n is cookies.length
                space: O(k+n) where k is children array, n is number of call stacks
        */
        dfs(cookies, 0, k, new int[k]);
        
        return res;
    }
    
    public void dfs(int[] cookies, int cur, int k, int[] children) {
        if (cur == cookies.length) {
            int max = 0;
            for (int c : children) max = Math.max(max, c);
            res = Math.min(res, max);
            return;
        }
        for (int i=0; i<k; i++) {
            children[i] += cookies[cur];
            dfs(cookies, cur + 1, k, children);
            children[i] -= cookies[cur];
        }
    }
}
