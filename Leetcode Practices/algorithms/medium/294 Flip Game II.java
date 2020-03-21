/*
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

Example:

Input: s = "++++"
Output: true 
Explanation: The starting player can guarantee a win by flipping the middle "++" to become "+--+".
Follow up:
Derive your algorithm's runtime complexity.
*/



// Other's Solution (backtracking):
class Solution {
    public boolean canWin(String s) {
        if(s == null || s.length() < 2) return false;
        Map<String, Boolean> map = new HashMap<>();
        return canWin(s, map);
    }

    public boolean canWin(String s, Map<String, Boolean> map){
        if(map.containsKey(s)) return map.get(s);
        for(int i = 0; i < s.length() - 1; i++) {
            if(s.charAt(i) == '+' && s.charAt(i + 1) == '+') {
                String opponent = s.substring(0, i) + "--" + s.substring(i + 2);
                if(!canWin(opponent, map)) {
                    map.put(s, true);
                    return true;
                }
            }
        }
        map.put(s, false);
        return false;
    }
}
// https://leetcode.com/problems/flip-game-ii/discuss/73962/Share-my-Java-backtracking-solution



// My Solution (Not completed):
class Solution {
    public boolean canWin(String s) {
        // 解法一？
        /*
        2,3,4,6,7,8,10 win
        +++++++++++
        ++++++++++ 10
        +--+--++ 8
        ++++++ 6
        +++++-- 7
            
        5 lose
        +++++
        */
        // 任何中间有-都可以视为等长情况下已经执行了该次操作之后的结果,若-为连续双数则不必改s长度，若有连续的单数则补一即s长度补一（如果有N个分别的连续单数则补N），最后再假定为是在长度为s.length()+N的全员+的字符串下判断是否为先手获胜，最后再计算已有-的总数+补N（f(-)）的奇偶，若全员+先手获胜且f(-)为偶数则返回true，又或若全员+先手失败且f(-)为奇数则返回true，其余情况返回false。
        // 保证纯+字符串获胜的关键除了一开始只能走一步也在于先手者能在某一刻走完一步而到一种局面 -> 剩且必剩偶数步给后手者。
        // 前置必要条件是在那一刻之前可以使其总是能有偶数步解法，而后手者则是要保证走现有的奇数步解法。
        // 即剩下的+皆离散，连续的+不多于3个，比如 --+++-++--+-++--+++ 就只有偶数步解法。
        // 不等式解法比如 ++++++++++ (10+) 解法步数阈在 [3,5], +--+--+--+ 或 ----------
        // 所以 N+ 字符串解法步数阈为 [(N%3==2?1:0)+N/3, N/2]
        // 不能剩下双 4+，否则必输
        // ToDo more thinking...
        
        
        
        // 解法二（多叉树遍历）
        // 根据 Flip Game 解题获取可能的结果的解法
        // 画出一颗树（多叉树），而每一个奇数层则为先手可下出的结果
        // 那么只要确保从第一层的某一个结果（作为根结点）延伸出来的树满足以下条件：
        // 该树的所有叶子的父节点都有一个叶子是奇数层。
    }
}