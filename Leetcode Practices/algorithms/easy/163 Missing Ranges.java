/**
You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in the inclusive range.

A number x is considered missing if x is in the range [lower, upper] and x is not in nums.

Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.

Each range [a,b] in the list should be output as:

"a->b" if a != b
"a" if a == b
 

Example 1:

Input: nums = [0,1,3,50,75], lower = 0, upper = 99
Output: ["2","4->49","51->74","76->99"]
Explanation: The ranges are:
[2,2] --> "2"
[4,49] --> "4->49"
[51,74] --> "51->74"
[76,99] --> "76->99"
Example 2:

Input: nums = [-1], lower = -1, upper = -1
Output: []
Explanation: There are no missing ranges since there are no missing numbers.
 

Constraints:

-109 <= lower <= upper <= 109
0 <= nums.length <= 100
lower <= nums[i] <= upper
All the values of nums are unique.
 */



// My Solution:
class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        // 标记变量
        // Time: O(N), Space: O(1)
        Integer missLeft = null, lastNum = Integer.MIN_VALUE;
        List<String> res = new ArrayList<>();
        for (int num : nums) {
            if (num <= lower || upper < num || num == lastNum + 1) {
                lastNum = num;
                continue;
            }
            if (lastNum + 1 == num - 1) {
                res.add(String.valueOf(lastNum + 1));
            } else {
                if (lastNum == Integer.MIN_VALUE) {
                    if (lower == num - 1) res.add(String.valueOf(lower));
                    else res.add(lower + "->" + (num - 1));
                } else {
                    res.add((lastNum + 1) + "->" + (num - 1));
                }
            }
            lastNum = num;
        }
        if (lastNum < lower) {
            if (lower == upper) res.add(String.valueOf(lower));
            else res.add(lower + "->" + upper);
        } else if (lastNum < upper) {
            if (lastNum + 1 == upper) res.add(String.valueOf(upper));
            else res.add((lastNum + 1) + "->" + upper);
        }
        
        return res;
    }
}
