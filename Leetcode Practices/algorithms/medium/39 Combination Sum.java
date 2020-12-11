/*
Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.

The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for the given input.

 

Example 1:

Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.
Example 2:

Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
Example 3:

Input: candidates = [2], target = 1
Output: []
Example 4:

Input: candidates = [1], target = 1
Output: [[1]]
Example 5:

Input: candidates = [1], target = 2
Output: [[1,1]]
 

Constraints:

1 <= candidates.length <= 30
1 <= candidates[i] <= 200
All elements of candidates are distinct.
1 <= target <= 500
*/



// My Solution (backtracking):
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 1st, the solution could apply backtracking.
        // 2nd, the key is how to identify a List<Integer> from another one (check if they are the same) is sort hash them to string
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> stack = new LinkedList<>();
        Set<String> set = new HashSet<>();
        
        backtracking(candidates, target, res, stack, set);
        
        return res;
    }
    
    public void backtracking(int[] candidates, int target, List<List<Integer>> res, Deque<Integer> stack, Set<String> set) {
        if (target == 0) {
            if (stack.isEmpty()) return;
            List<Integer> list = new ArrayList<>(stack);
            Collections.sort(list);
            String hash = list.toString();
            if (set.contains(hash)) return;
            set.add(hash);
            res.add(list);
            return;
        }
        for (int c : candidates) {
            if (c <= target) {
                stack.offerLast(c);
                backtracking(candidates, target-c, res, stack, set);
                stack.pollLast();
            }
        }
        return;
    }
}
