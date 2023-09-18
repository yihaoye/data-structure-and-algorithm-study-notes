/*
Alice and Bob continue their games with piles of stones.  There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].  The objective of the game is to end with the most stones. 

Alice and Bob take turns, with Alice starting first.  Initially, M = 1.

On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M.  Then, we set M = max(M, X).

The game continues until all the stones have been taken.

Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.

 

Example 1:

Input: piles = [2,7,9,4,4]
Output: 10
Explanation:  If Alice takes one pile at the beginning, Bob takes two piles, then Alice takes 2 piles again. Alice can get 2 + 4 + 4 = 10 piles in total. If Alice takes two piles at the beginning, then Bob can take all three piles left. In this case, Alice get 2 + 7 = 9 piles in total. So we return 10 since it's larger. 
Example 2:

Input: piles = [1,2,3,4,5,100]
Output: 104
 

Constraints:

1 <= piles.length <= 100
1 <= piles[i] <= 10^4
*/



// My Solution:
class Solution {
    int[][] dp;
    Map<Integer, Map<Integer, Integer>> mem;

    public int stoneGameII(int[] piles) {
        // dp + dfs - dp[i][j] means take [i..j] piles' sum
        int n = piles.length;
        dp = new int[n][n]; mem = new HashMap<>();
        for (int i=0; i<n; i++)
            for (int j=i; j<n; j++) 
                dp[i][j] = (i < j ? dp[i][j-1] : 0) + piles[j];
        
        return stoneGameII(piles, 0, 1);
    }

    public int stoneGameII(int[] piles, int idx, int M) {
        int n = piles.length;
        if (idx + 2 * M - 1 >= n - 1) return dp[idx][n - 1]; // greedy
        if (mem.containsKey(idx) && mem.get(idx).containsKey(M)) return mem.get(idx).get(M);

        int res = 0;
        for (int x = 0; x <= 2 * M - 1; x++) {
            int otherMax = stoneGameII(piles, idx + x + 1, Math.max(M, x + 1)); // another one
            int curMax = dp[idx][n - 1] - otherMax;
            res = Math.max(curMax, res);
        }

        mem.computeIfAbsent(idx, k -> new HashMap<>()).put(M, res);
        return res;
    }
}
