/* Question:
编写一个方法，将字符串中的空格全部替换为 “%20”。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。（注：用 Java 的话，使用字符数组实现，以便直接在数组上操作）
*/



// 此类字符串问题，常见方式可从字符串尾部编辑，从后往前反向操作，好处在于字符串尾部有额外缓冲（如果没有单个字符串长度限制的话）可直接修改（减少物理空间开销）不会覆盖原数据
public class Solution {
    // Assume string has sufficient free space at the end
	public static void replaceSpaces(char[] str, int length) {
		int spaceCount = 0, newLength, i;
		for (i = 0; i < length; i++) {
			if (str[i] == ' ') {
				spaceCount++;
			}
		}
		newLength = length + spaceCount * 2;
		str[newLength] = '\0';
		for (i = length - 1; i >= 0; i--) {
			if (str[i] == ' ') {
				str[newLength - 1] = '0';
				str[newLength - 2] = '2';
				str[newLength - 3] = '%';
				newLength = newLength - 3;
			} else {
				str[newLength - 1] = str[i];
				newLength--;
			}
		}
	}
}
// 因为 Java 字符串不是 immutable 的，所以选用字符数组来解决，好处是只需扫描一次。