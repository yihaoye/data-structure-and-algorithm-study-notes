/**
Question (airbnb oa):
![](./22-09-2022%20oa(A).jpg)
![](./22-09-2022%20oa(B).jpg)
 */



// My Solution:
class Solution {
    public int complementaryPairs(List<String> strs) {
        // Time: O(N) N - number of all chars in strs
        // Space: O(M) M - strs.size()
        Map<String, List<String>> oddCharsMap = new HashMap<>();
        int res = 0;
        for (String str : strs) {
            TreeSet<Character> oddChars = new TreeSet<>();
            for (char c : str.toCharArray()) {
                if (oddChars.contains(c)) oddChars.remove(c);
                else oddChars.add(c);
            }
            // match numOfOdd i.e. oddChars.size()
            res += oddCharsMap.getOrDefault(oddChars.toString(), new ArrayList<>()).size();
            // match numOfOdd-1
            for (char c : new ArrayList<Character>( oddChars )) {
                oddChars.remove(c);
                res += oddCharsMap.getOrDefault(oddChars.toString(), new ArrayList<>()).size();
                oddChars.add(c);
            }
            // match numOfOdd+1
            for (int i=0; i<26; i++) {
                if (oddChars.contains(i+'a')) continue;
                oddChars.add(i+'a');
                res += oddCharsMap.getOrDefault(oddChars.toString(), new ArrayList<>()).size();
                oddChars.remove(i+'a');
            }
            oddCharsMap.computeIfAbsent(oddChars.toString(), x -> new ArrayList<>()).add(str);
        }

        return res;
    }
}
