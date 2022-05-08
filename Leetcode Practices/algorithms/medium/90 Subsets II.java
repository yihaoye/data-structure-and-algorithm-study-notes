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



// Other's Solution:
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums); // 排序 - 未来好剔除重复项

        List<List<Integer>> res = new ArrayList<>(), temp = null;
        res.add(new ArrayList<Integer>());
        for (int i=0; i<nums.length; i++) {
            if (i > 0 && nums[i-1] == nums[i]) {
                for (List<Integer> list : temp) list.add(nums[i]);
            } else {
                temp = clone(res);
                for (List<Integer> list : temp) list.add(nums[i]);
            }
            res.addAll(clone(temp));
        }
        return res;
    }
    
    private List<List<Integer>> clone(List<List<Integer>> origin) {
        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> originList : origin) {
            List<Integer> copyList = new ArrayList<>();
            for (Integer item : originList) copyList.add(item);
            res.add(copyList);
        }
        return res;
    }
}
