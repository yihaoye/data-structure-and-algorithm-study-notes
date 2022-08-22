/**
Given a 0-indexed n x n integer matrix grid, return the number of pairs (Ri, Cj) such that row Ri and column Cj are equal.

A row and column pair is considered equal if they contain the same elements in the same order (i.e. an equal array).

 

Example 1:
https://assets.leetcode.com/uploads/2022/06/01/ex1.jpg

Input: grid = [[3,2,1],[1,7,6],[2,7,7]]
Output: 1
Explanation: There is 1 equal row and column pair:
- (Row 2, Column 1): [2,7,7]

Example 2:
https://assets.leetcode.com/uploads/2022/06/01/ex2.jpg

Input: grid = [[3,1,2,2],[1,4,4,5],[2,4,2,2],[2,4,2,2]]
Output: 3
Explanation: There are 3 equal row and column pairs:
- (Row 0, Column 0): [3,1,2,2]
- (Row 2, Column 2): [2,4,2,2]
- (Row 3, Column 2): [2,4,2,2]
 

Constraints:

n == grid.length == grid[i].length
1 <= n <= 200
1 <= grid[i][j] <= 10^5
 */



// My Solution:
class Solution {
    public int mod = (int) 1e9 + 7;
    
    public int equalPairs(int[][] grid) {
        // 哈希表+哈希码
        int res = 0;
        Map<Integer, Integer> row = new HashMap<>();
        Map<Integer, Integer> col = new HashMap<>();
        for (int i=0; i<grid.length; i++) {
            Integer rowHash = hash(grid, i, true);
            Integer colHash = hash(grid, i, false);
            row.put(rowHash, row.getOrDefault(rowHash, 0) + 1);
            col.put(colHash, col.getOrDefault(colHash, 0) + 1);
        }
        for (Integer k : row.keySet()) {
            if (col.containsKey(k)) {
                res += row.get(k) * col.get(k); 
            }
        }
        return res;
    }
    
    public int hash(int[][] grid, int index, boolean isRow) {
        int res = 0, n = grid.length;
        for (int i=0; i<n; i++) {
            if (isRow) res = (res * 31 % mod + grid[index][i]) % mod;
            else res = (res * 31 % mod + grid[i][index]) % mod;
        }
        return res;
    }
}
