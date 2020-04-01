/*
Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

Example:

Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
*/



// My Solution (Backtracking):
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i=1; i<=n; i++) {
            backTracking(i, n, k, new int[k], res);
        }
        
        return res;
    }
    
    public void backTracking(int start, int n, int k, int[] array, List<List<Integer>> res) {
        array[array.length-k] = start;
        if (k == 1) {
            List<Integer> list = new ArrayList<Integer>();
            for (int e : array) list.add(e);
            res.add(list);
            return;
        }
        for (int i=start+1; i<=n; i++) {
            backTracking(i, n, k-1, array, res);
        }
    }
}