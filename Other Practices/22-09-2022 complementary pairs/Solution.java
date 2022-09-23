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
        Map<String, List<String>> oddCharsMap = new HashMap<>(); // value 其实可以直接为 Integer 即 List<String> 的 size() 以减少空间复杂度
        int res = 0;
        for (String str : strs) {
            TreeSet<Character> oddChars = new TreeSet<>(); // 也可以使用一个 26 位的二进制，以异或操作进行奇数字符的计算
            for (char c : str.toCharArray()) {
                if (oddChars.contains(c)) oddChars.remove(c);
                else oddChars.add(c);
            }
            
            res += oddCharsMap.getOrDefault(oddChars.toString(), new ArrayList<>()).size(); // match numOfOdd i.e. oddChars.size()
            
            for (int i=0; i<26; i++) {
                char c = i + 'a';
                if (oddChars.contains(c)) { // match numOfOdd-1
                    oddChars.remove(c);
                    res += oddCharsMap.getOrDefault(oddChars.toString(), new ArrayList<>()).size();
                    oddChars.add(c);
                } else { // match numOfOdd+1
                    oddChars.add(c);
                    res += oddCharsMap.getOrDefault(oddChars.toString(), new ArrayList<>()).size();
                    oddChars.remove(c);
                }
            }
            oddCharsMap.computeIfAbsent(oddChars.toString(), x -> new ArrayList<>()).add(str);
        }

        return res;
    }
}
