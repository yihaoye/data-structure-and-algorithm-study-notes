/* Question:
假定有一个方法 isSubstring，可检查一个单词是否为其他字符串的子串。
给定两个字符串 s1 和 s2，请编写代码检查 s2 是否为 s1 旋转而成，要求只能调用一次 isSubstring。
（比如，waterbottle 是 erbottlewat 旋转后的字符串。）
*/



// 解题思路为设法切分 s1 为 x 和 y，以满足 xy = s1 和 yx = s2，同时我们会发现 yx 是 xyxy 的字串，即 s2 总是 s1s1 的字串。
public class Solution {
    public boolean isRotation(String s1, String s2) {
        int len = s1.length();
        /* 检查 s1 与 s2 是否等长且不为空 */
        if (len == s2.length() && len > 0) {
            /* 拼接 s1+s1，放入新字符串中 */
            String s1s1 = s1 + s1;
            return isSubstring(s1s1, s2);
        }
        return false;
    }
}