/* Question:
用 C 或 C++ 实现 void reverse(char* str) 函数，即反转一个 null 结尾的字符串。
*/



void reverse(char *str) {
    char* end = str;
    char tmp;
    if (str) {
        while (*end) { /* 找出字符串末尾 */
            ++end;
        }
        --end; /* 回退一个字符，最后一个为 null 字符 */

        /* 从字符串首尾开始交换两个字符 */
        /* 直至两个指针在中间碰头 */
        while (str < end) {
            tmp = *end;
            *str++ = *end;
            *end-- = tmp;
        }
    }
}