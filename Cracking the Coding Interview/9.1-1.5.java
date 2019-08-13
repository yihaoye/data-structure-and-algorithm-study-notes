/* Question:
编写一个方法，将字符串中的空格全部替换为 “%20”。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。（注：用 Java 的话，使用字符数组实现，以便直接在数组上操作）
*/



// 迭代访问字符串，将字符拷贝至新字符串，并数出重复字符
public class Solution1 {
    public String compressBad(String str) {
		String mystr = "";
        char last = str.charAt(0);
        int count = 1;
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == last) { // 找到重复字符
                count++;
            } else { // 插入字符的数目，更新 last 字符
                mystr += last + "" + count;
                last = str.charAt(i);
                count = 1;
            }
		}
		return mystr + last + count;
	}
}
// 此代码执行时间为 O(p+k^2)，其中 p 为原始字符串长度，k 为字符序列的数量即多少个不同字符，而字符直接拼接操作的时间复杂度为 O(n^2)。



// 使用 StringBuffer 优化解题方案 1
public class Solution2 {
    public String compressBetter(String str) {
        /* 检查压缩后的字符串是否会变得更长 */
        int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }

		StringBuffer mystr = new StringBuffer();
        char last = str.charAt(0);
        int count = 1;
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == last) { // 找到重复字符
                count++;
            } else { // 插入字符的数目，更新 last 字符
                mystr.append(last);
                mystr.append(count);
                last = str.charAt(i);
                count = 1;
            }
        }
        
        mystr.append(last);
        mystr.append(count);
		return mystr.toString();
	}
}

// 不使用 StringBuffer 优化解题方案 1
public class Solution3 {
    public String compressAlternate(String str) {
        /* 检查压缩后的字符串是否会变得更长 */
        int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }

        char[] array = new char[size];
        int index = 0;
        char last = str.charAt(0);
        int count = 1;
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == last) { // 找到重复字符
                count++;
            } else { // 插入字符的数目，更新 last 字符
                index = setChar(array, last, index, count);
                last = str.charAt(i);
                count = 1;
            }
        }
        
        index = setChar(array, last, index, count);
		return String.valueOf(array);
    }
    
    int setChar(char[] array, char c, int index, int count) {
        array[index] = c;
        index++;

        char[] cnt = String.valueOf(count).toCharArray();

        for (char x : cnt) {
            array[index] = x;
            index++;
        }
        return index;
    }

    int countCompression(String str) {
        // 与之前实现相同
    }
}