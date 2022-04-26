/**
You are given a 0-indexed string expression of the form "<num1>+<num2>" where <num1> and <num2> represent positive integers.

Add a pair of parentheses to expression such that after the addition of parentheses, expression is a valid mathematical expression and evaluates to the smallest possible value. The left parenthesis must be added to the left of '+' and the right parenthesis must be added to the right of '+'.

Return expression after adding a pair of parentheses such that expression evaluates to the smallest possible value. If there are multiple answers that yield the same result, return any of them.

The input has been generated such that the original value of expression, and the value of expression after adding any pair of parentheses that meets the requirements fits within a signed 32-bit integer.

 

Example 1:

Input: expression = "247+38"
Output: "2(47+38)"
Explanation: The expression evaluates to 2 * (47 + 38) = 2 * 85 = 170.
Note that "2(4)7+38" is invalid because the right parenthesis must be to the right of the '+'.
It can be shown that 170 is the smallest possible value.
Example 2:

Input: expression = "12+34"
Output: "1(2+3)4"
Explanation: The expression evaluates to 1 * (2 + 3) * 4 = 1 * 5 * 4 = 20.
Example 3:

Input: expression = "999+999"
Output: "(999+999)"
Explanation: The expression evaluates to 999 + 999 = 1998.
 

Constraints:

3 <= expression.length <= 10
expression consists of digits from '1' to '9' and '+'.
expression starts and ends with digits.
expression contains exactly one '+'.
The original value of expression, and the value of expression after adding any pair of parentheses that meets the requirements fits within a signed 32-bit integer.
 */



// Other's Solution:
class Solution {
    public String minimizeResult(String expression) {
        String[] nums = expression.split("\\+");
        Long min = Long.parseLong(nums[0]) + Long.parseLong(nums[1]);
        String res = "(" + expression + ")", leftStr = nums[0], rightStr = nums[1];
        Long left = 0L, right = 0L;
        for (int i=0; i<nums[0].length(); i++) {
            left = i == 0 ? 0L : Long.parseLong(nums[0].substring(0, i));
            leftStr = nums[0].substring(i);
            for (int j=1; j<=nums[1].length(); j++) {
                right = Long.parseLong(nums[1].substring(0, j));
                rightStr = j == nums[1].length() ? "" : nums[1].substring(j);
                Long cal = (left == 0L ? 1 : left) * (Long.parseLong(leftStr) + right) * (rightStr == "" ? 1L : Long.parseLong(rightStr));
                if (cal < min) {
                    min = cal;
                    res = (left == 0L ? "" : String.valueOf(left)) + "(" + leftStr + "+" + String.valueOf(right) + ")" + rightStr;
                }
            }
        }
        return res;
    }
}
