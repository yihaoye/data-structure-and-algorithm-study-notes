// Java 字符串的一个重要特点就是字符串不可变。这种不可变性是通过内部的 private final char[] 字段，以及没有任何修改 char[] 的方法实现的。
// 原因是因为 String 对象缓存在 String 池中。由于缓存的字符串在多个客户之间共享，因此始终存在风险，其中一个客户的操作会影响所有其他客户。
// 
// https://www.cnblogs.com/Andya/p/14067618.html
// 为了提高性能并减少内存的开销，JVM 在实例化字符串常量时进行了一系列的优化操作：
//  1. 在JVM层面为字符串提供字符串常量池，可以理解为是一个缓存区；
//  2. 创建字符串常量时，JVM会检查字符串常量池中是否存在这个字符串；
//  3. 若字符串常量池中存在该字符串，则直接返回引用实例；若不存在，先实例化该字符串，并且，将该字符串放入字符串常量池中，以便于下次使用时，直接取用，达到缓存快速使用的效果。



// 字符串操作套路

// 分割字符串
String str = "abc def   ghi  jkl";
String[] subs = str.split("\\s+");
// subs = ["abc", "def", "ghi", "jkl"]



// 将字符串每一位轮流替换成其他（小写）英文字母，比如 Leetcode 126
char[] charArray = currWord.toCharArray();
for (int j = 0; j < wordLen; j++) {
    char origin = charArray[j];
    for (char c = 'a'; c <= 'z'; c++) {
        charArray[j] = c;
        String nextWord = String.valueOf(charArray);
        process(nextWord);
    }
    charArray[j] = origin; // 把 charArray 复原，因为下一次换一位字母遍历
}



// 字符串转数字
for (int digit = 0; digit <= 9999; digit++) {
    System.out.println(String.format("%04d", digit)); // prepend 0 for digit which smaller than 1000
}



char c = 'Z';
Character.isLetter(c);
Character.isDigit(c);
Character.isUpperCase(c);
Character.isLowerCase(c);
Character.toUpperCase(c);
Character.toLowerCase(c);
Character.toString(c); // char => String



// 处理字符串数字
// incrDigitByIndex("123", 1, 1) => "133", incrDigitByIndex("123", 2, -1) => "122", 实现参考 Leetcode Q752



// 构建 N 个相同字符的字符串
char[] chars = new char[10];
Arrays.fill(chars, '-');
String dashes = new String(chars); // "----------"



// 更改某个字符
StringBuilder strB = new StringBuilder(str1);
strB.setCharAt(0, 'A');
String str2 = strB.toString();



// 字符串转数字
int num = 5;
char ch = (char) (num + '0'); // '5'
