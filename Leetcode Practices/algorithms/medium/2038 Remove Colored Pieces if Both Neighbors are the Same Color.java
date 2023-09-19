/*
There are n pieces arranged in a line, and each piece is colored either by 'A' or by 'B'. You are given a string colors of length n where colors[i] is the color of the ith piece.

Alice and Bob are playing a game where they take alternating turns removing pieces from the line. In this game, Alice moves first.

Alice is only allowed to remove a piece colored 'A' if both its neighbors are also colored 'A'. She is not allowed to remove pieces that are colored 'B'.
Bob is only allowed to remove a piece colored 'B' if both its neighbors are also colored 'B'. He is not allowed to remove pieces that are colored 'A'.
Alice and Bob cannot remove pieces from the edge of the line.
If a player cannot make a move on their turn, that player loses and the other player wins.
Assuming Alice and Bob play optimally, return true if Alice wins, or return false if Bob wins.

 

Example 1:

Input: colors = "AAABABB"
Output: true
Explanation:
AAABABB -> AABABB
Alice moves first.
She removes the second 'A' from the left since that is the only 'A' whose neighbors are both 'A'.

Now it's Bob's turn.
Bob cannot make a move on his turn since there are no 'B's whose neighbors are both 'B'.
Thus, Alice wins, so return true.
Example 2:

Input: colors = "AA"
Output: false
Explanation:
Alice has her turn first.
There are only two 'A's and both are on the edge of the line, so she cannot move on her turn.
Thus, Bob wins, so return false.
Example 3:

Input: colors = "ABBBBBBBAAA"
Output: false
Explanation:
ABBBBBBBAAA -> ABBBBBBBAA
Alice moves first.
Her only option is to remove the second to last 'A' from the right.

ABBBBBBBAA -> ABBBBBBAA
Next is Bob's turn.
He has many options for which 'B' piece to remove. He can pick any.

On Alice's second turn, she has no more pieces that she can remove.
Thus, Bob wins, so return false.
 

Constraints:

1 <= colors.length <= 10^5
colors consists of only the letters 'A' and 'B'
*/



// Other's Solution:
class Solution {
    public boolean winnerOfGame(String colors) {
        // https://www.acoier.com/2022/03/21/2038.%20%E5%A6%82%E6%9E%9C%E7%9B%B8%E9%82%BB%E4%B8%A4%E4%B8%AA%E9%A2%9C%E8%89%B2%E5%9D%87%E7%9B%B8%E5%90%8C%E5%88%99%E5%88%A0%E9%99%A4%E5%BD%93%E5%89%8D%E9%A2%9C%E8%89%B2%EF%BC%88%E4%B8%AD%E7%AD%89%EF%BC%89/#%E8%84%91%E7%AD%8B%E6%80%A5%E8%BD%AC%E5%BC%AF
        char[] cs = colors.toCharArray();
        int n = cs.length;
        int a = 0, b = 0;
        for (int i = 1; i < n - 1; i++) {
            if (cs[i] == 'A' && cs[i - 1] == 'A' && cs[i + 1] == 'A') a++;
            if (cs[i] == 'B' && cs[i - 1] == 'B' && cs[i + 1] == 'B') b++;
        }
        return a > b;
    }
}
