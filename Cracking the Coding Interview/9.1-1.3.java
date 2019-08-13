/* Question:
给定两个字符串，请编写程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串？
*/



// 排序字符串
public class Solution1 {
    public static String sort(String s) {
		char[] content = s.toCharArray();
		java.util.Arrays.sort(content);
		return new String(content);
	}
	
	public static boolean permutation(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
		return sort(s).equals(sort(t));
	}
}



// 检查两个字符串的各字符数是否相同
public class Solution2 {
    public static boolean permutation(String s, String t) {
		if (s.length() != t.length()) return false; // Permutations must be same length
		
		int[] letters = new int[128]; // Assumption: ASCII
		for (int i = 0; i < s.length(); i++) {
			letters[s.charAt(i)]++;
		}
		  
		for (int i = 0; i < t.length(); i++) {
			letters[t.charAt(i)]--;
		    if (letters[t.charAt(i)] < 0) {
		    	return false;
		    }
		}
		  
		return true; // letters array has no negative values, and therefore no positive values either
	}
}