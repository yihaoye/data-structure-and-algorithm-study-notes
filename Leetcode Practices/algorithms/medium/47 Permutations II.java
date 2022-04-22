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



// My Solution (time improved):
class Solution {
    Set<String> set = new HashSet<>();
    
    public List<List<Integer>> permuteUnique(int[] nums) {
        // 回溯法+剪枝
        // Time: O(N!), Space(N!)
        List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, res, new ArrayList<>());
        return res;
    }
    
    private void backtrack(int[] nums, List<List<Integer>> res, List<Integer> tmp) {
        if (tmp.size() == nums.length) {
            List<Integer> list = new ArrayList<>(tmp);
            res.add(list);
        }
        for (int i=0; i<nums.length; i++) {
            if (nums[i] <= 10 && nums[i] >= -10) {
                tmp.add(nums[i]);
                nums[i] -= 21;
                String hash = tmp.toString();
                if (!set.contains(hash)) {
                    set.add(hash);
                    backtrack(nums, res, tmp);
                }
                nums[i] += 21;
                tmp.remove(tmp.size()-1);
            }
        }
    }
}



// Other's Solution (better performance):
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
        if(tempList.size() == nums.length){
            list.add(new ArrayList<>(tempList));
        } else{
            for(int i = 0; i < nums.length; i++){
                if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) continue;
                used[i] = true; 
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, used);
                used[i] = false; 
                tempList.remove(tempList.size() - 1);
            }
        }
    }
}
