/**
Given an integer array nums, return all the different possible increasing subsequences of the given array with at least two elements. You may return the answer in any order.

The given array may contain duplicates, and two equal integers should also be considered a special case of increasing sequence.

 

Example 1:

Input: nums = [4,6,7,7]
Output: [[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
Example 2:

Input: nums = [4,4,3,2,1]
Output: [[4,4]]
 

Constraints:

1 <= nums.length <= 15
-100 <= nums[i] <= 100
 */



// Other's Solution:
class Solution {
    List<Integer> temp = new ArrayList<>();
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        /*
            递归枚举 + 剪枝 - https://leetcode-cn.com/problems/increasing-subsequences/solution/di-zeng-zi-xu-lie-by-leetcode-solution/
            Time: O((2^N)*N), Space: O(N)
        */
        dfs(0, Integer.MIN_VALUE, nums);
        return res;
    }

    private void dfs(int curIndex, int lastVal, int[] nums) {
        if (curIndex == nums.length) {
            if (temp.size() >= 2) res.add(new ArrayList<Integer>(temp));

            return;
        }
        if (nums[curIndex] >= lastVal) { // > 或 == 时才可以尝试选择当前元素进 temp，然后参数 1、2 均循序渐进往右走一步
            temp.add(nums[curIndex]);
            dfs(curIndex + 1, nums[curIndex], nums);
            temp.remove(temp.size() - 1);
        }
        if (nums[curIndex] != lastVal) dfs(curIndex + 1, lastVal, nums); // 如果不选择、跳过当前元素 curIndex -- 因为要记录所有可能性包括不连续，且只有当当前的元素不等于上一个选择的元素的时候，才考虑不选择、跳过当前元素，因为与前面有重复，要去重 -- 假设上次元素与当前元素相同，会有这样四种情况：1.上次被选择，当前也被选择 2.上次被选择，当前不被选择 3.上次不被选择，当前被选择 4.上次不被选择，当前不被选择。其中第二种情况和第三种情况其实是等价的，通过 if (nums[curIndex] != lastVal) 这样限制之后，舍弃了第二种，保留了第三种，即如果上次被选了这次也必须被选，从而达到了去重的目的。
        // 模拟元数组有多个重复数情况 [4, 4, 4, 4, 4] -> 只能是 [y, y, y, y, y] [x, y, y, y, y] [x, x, y, y, y] [x, x, x, y, y] [x, x, x, x, y] [x, x, x, x, x]，x 意味着不选，y 意味着选。
        // 即使重复数不连续也一样 [4, 4, ..., 4, 4, 4]，1. 因为如果在省略号之前选 4 了，后面还选了大的数（即省略号代表的序列里的数）则后面的 4 肯定不会被再选；2. 如果之前选了，后面没选大数则必须要选 4；3. 如果之前没选，后面还选了大的数则后面的 4 肯定不会被再选；4. 如果之前没选，后面也没选大的数则后面的 4 则可以选也可以不选；而以上四种情况都不可能有重复情况
        // 那上面不连续例子中省略号若有更小数则如何？若在省略号之前选 4 了，肯定不会再选更小数；若之前没选，更小数视与前面选的数大小对比决定是否可选。
        // 那上面不连续例子中省略号既有更小数又有更大数则如何？在选了 4 的情况下则只须考虑大数，若之前没选 4 则为小数与大数组合。其他更复杂的（不）连续情况均可以分割子问题如上面三段，而因为子问题保证了不重复，所以子问题们的结果组合也不会重复？
    }
}
