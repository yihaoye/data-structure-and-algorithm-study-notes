/*
Given a set of distinct integers, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
*/



// My Solution:
class Solution {
    private List<List<Integer>> res;
    
    public List<List<Integer>> subsets(int[] nums) {
        res = new ArrayList<List<Integer>>();
        res.add(new ArrayList<Integer>());
        dfs(nums, 0, new ArrayList<Integer>());
        
        return res;
    }
    
    public void dfs(int[] nums, int index, List<Integer> combine) {
        if (index == nums.length) return;
        dfs(nums, index+1, combine);
        List<Integer> newCombine = new ArrayList<>(combine);
        newCombine.add(nums[index]);
        res.add(newCombine);
        dfs(nums, index+1, newCombine);
    }
}