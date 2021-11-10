/*
You are playing a variation of the game Zuma.

In this variation of Zuma, there is a single row of colored balls on a board, where each ball can be colored red 'R', yellow 'Y', blue 'B', green 'G', or white 'W'. You also have several colored balls in your hand.

Your goal is to clear all of the balls from the board. On each turn:

Pick any ball from your hand and insert it in between two balls in the row or on either end of the row.
If there is a group of three or more consecutive balls of the same color, remove the group of balls from the board.
If this removal causes more groups of three or more of the same color to form, then continue removing each group until there are none left.
If there are no more balls on the board, then you win the game.
Repeat this process until you either win or do not have any more balls in your hand.
Given a string board, representing the row of balls on the board, and a string hand, representing the balls in your hand, return the minimum number of balls you have to insert to clear all the balls from the board. If you cannot clear all the balls from the board using the balls in your hand, return -1.

 

Example 1:

Input: board = "WRRBBW", hand = "RB"
Output: -1
Explanation: It is impossible to clear all the balls. The best you can do is:
- Insert 'R' so the board becomes WRRRBBW. WRRRBBW -> WBBW.
- Insert 'B' so the board becomes WBBBW. WBBBW -> WW.
There are still balls remaining on the board, and you are out of balls to insert.
Example 2:

Input: board = "WWRRBBWW", hand = "WRBRW"
Output: 2
Explanation: To make the board empty:
- Insert 'R' so the board becomes WWRRRBBWW. WWRRRBBWW -> WWBBWW.
- Insert 'B' so the board becomes WWBBBWW. WWBBBWW -> WWWW -> empty.
2 balls from your hand were needed to clear the board.
Example 3:

Input: board = "G", hand = "GGGGG"
Output: 2
Explanation: To make the board empty:
- Insert 'G' so the board becomes GG.
- Insert 'G' so the board becomes GGG. GGG -> empty.
2 balls from your hand were needed to clear the board.
Example 4:

Input: board = "RBYYBBRRB", hand = "YRBGB"
Output: 3
Explanation: To make the board empty:
- Insert 'Y' so the board becomes RBYYYBBRRB. RBYYYBBRRB -> RBBBRRB -> RRRB -> B.
- Insert 'B' so the board becomes BB.
- Insert 'B' so the board becomes BBB. BBB -> empty.
3 balls from your hand were needed to clear the board.
 

Constraints:

1 <= board.length <= 16
1 <= hand.length <= 5
board and hand consist of the characters 'R', 'Y', 'B', 'G', and 'W'.
The initial row of balls on the board will not have any groups of three or more consecutive balls of the same color.
*/



class Solution {
    public int findMinStep(String board, String hand) {
        /*
            a: 思路 - 递归（应该不用担心爆栈，因为 board 长度最多 16），暴力尝试直到手上没球，同时 board 还未消完，则回溯，直到所有可能性皆尝试了若仍未有解法，则返回-1，使用合适的数据结构 ArrayList<char> 表示 board（长度最多 16，不用考虑插入性能）。每次插入后，进行一次循环 cleanup，然后把 cleanup 后的 ArrayList 留作后续用。
            b: 时间复杂度 O(N*M) ?，空间复杂度 ?
            c: 超时（需 3 个剪枝优化，第 3 个剪枝优化需看答案才知道）
        */
        List<Character> boardList = new ArrayList<>();
        for (char c : board.toCharArray()) {
            boardList.add(c);
        }
        List<Character> handList = new ArrayList<>();
        for (char c : hand.toCharArray()) {
            handList.add(c);
        }
        
        int res = findMinStep(boardList, handList);
        return res;
    }
    
    public int findMinStep(List<Character> boardList, List<Character> handList) {
        if (boardList.isEmpty()) {
            return 0;
        }
        if (!boardList.isEmpty() && handList.isEmpty()) {
            return -1;
        }
        // 回溯优化，若 board 中某个字符加上 hand 上的对应字符都不足 3，则返回失败
        if (!isValid(boardList, handList)) {
            return -1;
        }
        
        int res = Integer.MAX_VALUE;
        for (int boardListIndex=0; boardListIndex<=boardList.size(); boardListIndex++) {            
            Set<Character> duplicate = new HashSet<>();
            for (int handListIndex=0; handListIndex<handList.size(); handListIndex++) {
                // 剪枝优化：若该色球与 board 的当前色球同色，则只在 board 同色连续球列的最左侧插入
                if (boardListIndex > 0 && boardList.get(boardListIndex-1) == handList.get(handListIndex)) {
                    continue;
                }
                // 剪枝优化：若该色球已在之前尝试过，则没必要再尝试
                if (duplicate.contains(handList.get(handListIndex))) {
                    continue;
                } else {
                    duplicate.add(handList.get(handListIndex));
                }
                // 剪枝优化：只在以下两种情况放置新球（插入球与插入右侧的球的颜色相同；插入球与左右两侧球颜色不同且左右两侧球颜色相同），参考了 https://leetcode-cn.com/problems/zuma-game/solution/zu-ma-you-xi-by-leetcode-solution-lrp4/
                boolean goodCase1 = (boardListIndex < boardList.size() && boardList.get(boardListIndex) == handList.get(handListIndex));
                boolean goodCase2 = (boardListIndex > 0 && boardListIndex < boardList.size() && boardList.get(boardListIndex-1) == boardList.get(boardListIndex) && boardList.get(boardListIndex-1) != handList.get(handListIndex));
                if (goodCase1 == false && goodCase2 == false) continue;
                
                // 克隆为免更改原数据结构对象
                List<Character> boardListClone = new ArrayList<>(boardList);
                List<Character> handListClone = new ArrayList<>(handList);
                // 插入 ball 且进行清除
                boardListClone.add(boardListIndex, handListClone.remove(handListIndex));
                cleanup(boardListClone);
                // 递归
                int preRes = findMinStep(boardListClone, handListClone);
                if (preRes != -1 && preRes < res) {
                    res = preRes;
                }
            }
        }
        
        if (res == Integer.MAX_VALUE) {
            return -1;
        }
        return res + 1;
    }
    
    public void cleanup(List<Character> boardList) {
        Boolean isCleanup = false;
        while (!isCleanup) {
            isCleanup = true;
            for (int i=0; i<boardList.size()-2; i++) {
                int repeatLen = 1;
                for (int j=i+1; j<boardList.size(); j++) {
                    if (boardList.get(j) == boardList.get(j-1)) {
                        repeatLen++;
                    } else {
                        break;
                    }
                }
                if (repeatLen >= 3) {
                    isCleanup = false;
                    for (; repeatLen > 0; repeatLen--) {
                        boardList.remove(i);
                    }
                }
            }
        }
    }
    
    // 回溯优化，若 board 中某个字符加上 hand 上的对应字符都不足 3，则返回失败
    public boolean isValid(List<Character> boardList, List<Character> handList) {
        int boardR = 0, boardY = 0, boardB = 0, boardG = 0, boardW = 0;
        int handR = 0, handY = 0, handB = 0, handG = 0, handW = 0;
        for (char c : boardList) {
            if (c == 'R') boardR++;
            if (c == 'Y') boardY++;
            if (c == 'B') boardB++;
            if (c == 'G') boardG++;
            if (c == 'W') boardW++;
        }
        for (char c : handList) {
            if (c == 'R') handR++;
            if (c == 'Y') handY++;
            if (c == 'B') handB++;
            if (c == 'G') handG++;
            if (c == 'W') handW++;
        }
        if (boardR < 3 && boardR > 0 && boardR + handR < 3) return false;
        if (boardY < 3 && boardY > 0 && boardY + handY < 3) return false;
        if (boardB < 3 && boardB > 0 && boardB + handB < 3) return false;
        if (boardG < 3 && boardG > 0 && boardG + handG < 3) return false;
        if (boardW < 3 && boardW > 0 && boardW + handW < 3) return false;
        
        return true;
    }
}
