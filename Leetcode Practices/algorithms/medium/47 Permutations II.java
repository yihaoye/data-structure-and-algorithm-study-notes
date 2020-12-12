/*
Given a collection of numbers, nums, that might contain duplicates, return all possible unique permutations in any order.

 

Example 1:

Input: nums = [1,1,2]
Output:
[[1,1,2],
 [1,2,1],
 [2,1,1]]
Example 2:

Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 

Constraints:

1 <= nums.length <= 8
-10 <= nums[i] <= 10
*/



// My Solution:
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        // 1st, the solution could apply backtracking.
        // 2nd, the key is how to identify a List<Integer> from another one (check if they are the same) is sort hash them to string
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> stack = new ArrayList<>();
        Set<String> set = new HashSet<>();
        
        backtracking(nums, res, stack, set);
        
        return res;
    }
    
    public void backtracking(int[] nums, List<List<Integer>> res, List<Integer> stack, Set<String> set) {
        if (stack.size() == nums.length) {
            List<Integer> list = new ArrayList<>(stack);
            String hash = list.toString();
            if (set.contains(hash)) return;
            set.add(hash);
            res.add(list);
            return;
        }
        for (int i=0; i<nums.length; i++) {
            if (nums[i] <= 10 && nums[i] >= -10) {
                stack.add(nums[i]);
                nums[i] -= 21;
                backtracking(nums, res, stack, set);
                nums[i] += 21;
                stack.remove(stack.size()-1);
            }
        }
    }
}
