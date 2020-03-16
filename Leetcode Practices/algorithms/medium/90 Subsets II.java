/*
Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: [1,2,2]
Output:
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
*/



// My Solution:
class Solution {
    private Set<List<Integer>> set;
        
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        set = new HashSet<List<Integer>>();
        set.add(new ArrayList<Integer>());
        dfs(nums, 0, new ArrayList<Integer>());
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (List<Integer> list : set) res.add(list);
        
        return res;
    }
    
    public void dfs(int[] nums, int index, List<Integer> combine) {
        if (index == nums.length) return;
        dfs(nums, index+1, combine);
        List<Integer> newCombine = new ArrayList<>(combine);
        newCombine.add(nums[index]);
        Collections.sort(newCombine); // not must, but leetcode only accept sorted sublist so add it.
        set.add(newCombine);
        dfs(nums, index+1, newCombine);
    }
}