/*
Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.

Each number in candidates may only be used once in the combination.

Note: The solution set must not contain duplicate combinations.

 

Example 1:

Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: 
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]
Example 2:

Input: candidates = [2,5,2,1,2], target = 5
Output: 
[
[1,2,2],
[5]
]
 

Constraints:

1 <= candidates.length <= 100
1 <= candidates[i] <= 50
1 <= target <= 30
*/



// My Solution:
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(list, target, new ArrayList<>(), candidates, 0, new boolean[candidates.length]);
        return list;
    }

    private void backtrack(List<List<Integer>> list, int target, List<Integer> tempList, int [] candidates, int startIndex, boolean [] used) {
        if (target == 0) {
            list.add(new ArrayList<>(tempList));
        } else {
            for (int i = startIndex; i < candidates.length; i++) {
                if (used[i] || i > 0 && candidates[i] == candidates[i-1] && !used[i - 1]) continue;
                if (target < candidates[i]) return; 
                used[i] = true;
                tempList.add(candidates[i]);
                backtrack(list, target-candidates[i], tempList, candidates, i+1, used);
                used[i] = false;
                tempList.remove(tempList.size() - 1);
            }
        }
    }
}