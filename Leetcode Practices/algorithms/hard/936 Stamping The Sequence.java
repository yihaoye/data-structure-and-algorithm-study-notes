/**
You are given two strings stamp and target. Initially, there is a string s of length target.length with all s[i] == '?'.

In one turn, you can place stamp over s and replace every letter in the s with the corresponding letter from stamp.

For example, if stamp = "abc" and target = "abcba", then s is "?????" initially. In one turn you can:
place stamp at index 0 of s to obtain "abc??",
place stamp at index 1 of s to obtain "?abc?", or
place stamp at index 2 of s to obtain "??abc".
Note that stamp must be fully contained in the boundaries of s in order to stamp (i.e., you cannot place stamp at index 3 of s).
We want to convert s to target using at most 10 * target.length turns.

Return an array of the index of the left-most letter being stamped at each turn. If we cannot obtain target from s within 10 * target.length turns, return an empty array.

 

Example 1:

Input: stamp = "abc", target = "ababc"
Output: [0,2]
Explanation: Initially s = "?????".
- Place stamp at index 0 to get "abc??".
- Place stamp at index 2 to get "ababc".
[1,0,2] would also be accepted as an answer, as well as some other answers.
Example 2:

Input: stamp = "abca", target = "aabcaca"
Output: [3,0,1]
Explanation: Initially s = "???????".
- Place stamp at index 3 to get "???abca".
- Place stamp at index 0 to get "abcabca".
- Place stamp at index 1 to get "aabcaca".
 

Constraints:

1 <= stamp.length <= target.length <= 1000
stamp and target consist of lowercase English letters.
 */



// Other's Solution:
class Solution {
    public int[] movesToStamp(String stamp, String target) {
        // 贪心 - https://leetcode.com/problems/stamping-the-sequence/discuss/189254/Python-Greedy-and-DFS
        // Reversely change from target to ????....??? whenever we find a matched stamp substring.
        char[] s = stamp.toCharArray(), t = target.toCharArray();
        int n = t.length, m = s.length;
        List<Integer> res = new ArrayList<>();
        boolean changed = true;

        while (changed) {
            changed = false;
            for (int i = 0; i < n - m + 1; i++) {
                changed |= check(s, t, i, res);
            }
        }

        for (int ch : t) {
            if (ch != '?') return new int[]{};
        }

        Collections.reverse(res);
        return res.stream().mapToInt(i -> i).toArray();
    }

    private boolean check(char[] s, char[] t, int i, List<Integer> res) {
        boolean changed = false;
        for (int j = 0; j < s.length; j++) {
            if (t[i + j] == '?') continue;
            if (t[i + j] != s[j]) return false;
            changed = true;
        }
        if (changed) {
            for (int j = 0; j < s.length; j++) t[i + j] = '?';

            res.add(i);
        }
        return changed;
    }
}
