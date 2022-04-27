// 字符串操作套路



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
