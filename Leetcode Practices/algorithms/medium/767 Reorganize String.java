/*
Given a string S, check if the letters can be rearranged so that two characters that are adjacent to each other are not the same.

If possible, output any possible result.  If not possible, return the empty string.

Example 1:

Input: S = "aab"
Output: "aba"
Example 2:

Input: S = "aaab"
Output: ""
Note:

S will consist of lowercase letters and have length in range [1, 500].
*/



// My Solution (greedy + 暴力算法):
class Solution {
    public String reorganizeString(String S) {
        int[] letters = new int[26];
        char[] cArr = S.toCharArray();
        for (char c : cArr) letters[c-'a']++;
        
        int preCharIndex = -1;
        StringBuilder strB = new StringBuilder();
        for (int i=0; i<cArr.length; i++) {
            int nextCharIndex = findNextCharIndex(preCharIndex, letters);
            if (letters[nextCharIndex] < 0) return "";
            strB.append((char)(nextCharIndex + 'a'));
            preCharIndex = nextCharIndex;
        }
        
        return strB.toString();
    }
    
    public int findNextCharIndex(int preCharIndex, int[] letters) {
        int nextCharIndex = -1;
        for (int i=0; i<letters.length; i++) {
            if (i == preCharIndex) continue;
            if (nextCharIndex == -1 || letters[i] >= letters[nextCharIndex]) nextCharIndex = i;
        }
        letters[nextCharIndex]--;
        return nextCharIndex;
    }
}



// Other's Solution (Greedy + PriorityQueue):
class Solution {
    public String reorganizeString(String S) {
        // Create map of each char to its count
        Map<Character, Integer> map = new HashMap<>();
        for (char c : S.toCharArray()) {
            int count = map.getOrDefault(c, 0) + 1;
            // Impossible to form a solution
            if (count > (S.length() + 1) / 2) return "";
            map.put(c, count);
        }
        // Greedy: fetch char of max count as next char in the result.
        // Use PriorityQueue to store pairs of (char, count) and sort by count DESC.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (char c : map.keySet()) {
            pq.add(new int[] {c, map.get(c)});
        }
        // Build the result.
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] first = pq.poll();
            if (sb.length() == 0 || first[0] != sb.charAt(sb.length() - 1)) {
                sb.append((char) first[0]);
                if (--first[1] > 0) {
                    pq.add(first);
                }
            } else {
                int[] second = pq.poll();
                sb.append((char) second[0]);
                if (--second[1] > 0) {
                    pq.add(second);
                }
                pq.add(first);
            }
        }
        return sb.toString();
    }
}
