/**
You are given an integer array cards of length 4. You have four cards, each containing a number in the range [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.

You are restricted with the following rules:

The division operator '/' represents real division, not integer division.
For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
You cannot concatenate numbers together
For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
Return true if you can get such expression that evaluates to 24, and false otherwise.

 

Example 1:

Input: cards = [4,1,8,7]
Output: true
Explanation: (8-4) * (7-1) = 24
Example 2:

Input: cards = [1,2,1,2]
Output: false
 

Constraints:

cards.length == 4
1 <= cards[i] <= 9
 */



// Other's Solution:
class Solution {
    static final int TARGET = 24;
    static final double EPSILON = 1e-6;
    static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

    public boolean judgePoint24(int[] nums) {
        // 回溯法 - https://leetcode.cn/problems/24-game/solution/24-dian-you-xi-by-leetcode-solution/
        List<Double> list = new ArrayList<Double>();
        for (int num : nums) list.add((double) num);
        
        return solve(list);
    }

    public boolean solve(List<Double> list) {
        if (list.size() == 0) return false;
        if (list.size() == 1) return Math.abs(list.get(0) - TARGET) < EPSILON;
        
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j) continue;
                List<Double> list2 = new ArrayList<Double>();
                for (int k = 0; k < list.size(); k++) {
                    if (k != i && k != j) list2.add(list.get(k));
                }
                for (int op = ADD; op <= DIVIDE; op++) {
                    if (op < 2 && i > j) continue; // filter out duplicate process

                    if (op == ADD) list2.add(list.get(i) + list.get(j));
                    if (op == MULTIPLY) list2.add(list.get(i) * list.get(j));
                    if (op == SUBTRACT) list2.add(list.get(i) - list.get(j));
                    if (op == DIVIDE) {
                        if (Math.abs(list.get(j)) < EPSILON) continue; // 防止除 0
                        else list2.add(list.get(i) / list.get(j));
                    }

                    if (solve(list2)) return true;

                    list2.remove(list2.size() - 1); // 回溯
                }
            }
        }
        return false;
    }
}
