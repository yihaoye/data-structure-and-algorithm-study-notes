Knuth-Morris-Pratt字符串查找算法（简称为 KMP 算法）可在一个主文本字符串 S (或称 txt) 内查找一个词 W (或称 pattern) 的出现位置。此算法通过运用对这个词在不匹配时本身就包含足够的信息来确定下一个匹配将在哪里开始的发现，从而避免重新检查先前匹配的字符（即避免暴力匹配算法 - BF / Brute Force 算法）。KMP 算法的时间复杂度为 O(N+M) 其中 N = S.length, M = W.length。  
暴力匹配算法：循环主字符串 S 中每个字符，从该字符起始一个个匹配 W 中的每个字符，若发生任一不匹配则退出本次匹配动作并从主字符串 S 的下一个字符重复前面的匹配动作，直到主字符串所有字符皆被试完或中间发生了正确匹配。时间复杂度为 O(N*M) 其中 N = S.length, M = W.length。  
KMP 算法则是为了避免暴力匹配算法中在主字符串循环中回退的行为而设计的，从而减少浪费减少时间复杂度。  
KMP 算法的核心思想是先基于 W (pattern) 创建一个 Partial Match Table，其中的每个字符下的值是以该字符为尾的最大可能的后缀 Suffix 的对应前缀 Prefix 的尾 index。KMP 算法除了可以用 Partial Match Table 实现外也可以用 DFA (Deterministic Finite Automaton - 确定有限状态自动机) 实现 (https://blog.csdn.net/congduan/article/details/45459963, https://zhuanlan.zhihu.com/p/83334559)。  
![](KMP(PMT).png)  
![](KMP(Example).png)  
![](KMP(DFA).png)  
![](KMP(DFA2).png)  
![](KMP(DFA3).webp)  
简易教程：https://www.youtube.com/watch?v=V5-7GzOfADQ  
详解教程：https://zhuanlan.zhihu.com/p/83334559, https://blog.csdn.net/v_july_v/article/details/7041827  
    
  
  
## Java 实现（KMP DFA）
```java
/******************************************************************************
 *  Compilation:  javac KMP.java
 *  Execution:    java KMP pattern text
 *  Dependencies: StdOut.java
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  KMP algorithm.
 *
 *  % java KMP abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:               abracadabra          
 *
 *  % java KMP rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:         rab
 *
 *  % java KMP bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:                                   bcara
 *
 *  % java KMP rabrabracad abacadabrabracabracadabrabrabracad 
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java KMP abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ******************************************************************************/

/**
 *  The {@code KMP} class finds the first occurrence of a pattern string
 *  in a text string.
 *  <p>
 *  This implementation uses a version of the Knuth-Morris-Pratt substring search
 *  algorithm. The version takes time proportional to <em>n</em> + <em>m R</em>
 *  in the worst case, where <em>n</em> is the length of the text string,
 *  <em>m</em> is the length of the pattern, and <em>R</em> is the alphabet size.
 *  It uses extra space proportional to <em>m R</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/53substring">Section 5.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class KMP {
    private int[][] dfa;       // the KMP automoton
    private String pat;        // or the pattern string

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public KMP(String pat) {
        this.pat = pat;

        // build DFA from pattern
        int R = 256; // the radix, 256 种 ASCII 码（Char）
        int m = pat.length();
        dfa = new int[R][m]; 
        dfa[pat.charAt(0)][0] = 1; // 创建一个二维数组 dfa[][] 为了用于 search 函数中 -- 维度 1 是指 search 的 for 循环中某次输入的 text 的一个字符/Char，维度 2 是指 search 的上一个输入的字符/Char 已匹配到 pattern 的索引。
        for (int x = 0, j = 1; j < m; j++) { // 关于 x 这一核心关键，参考 https://zhuanlan.zhihu.com/p/83334559, 尤其看其中的 https://pic4.zhimg.com/v2-6594ba99da1f3c8ea609d0b24be448cb_b.webp
            for (int c = 0; c < R; c++) 
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases. 
            dfa[pat.charAt(j)][j] = j+1;   // Set match case. 
            x = dfa[pat.charAt(j)][x];     // Update restart state. 
        } 
    } 

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; N if no such match
     */
    public int search(String txt) {
        // simulate operation of DFA on text
        int m = this.pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) return i - m;    // found
        return n;                    // not found
    }


    /** 
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        KMP kmp = new KMP(pat);
        int index = kmp.search(txt);

        // print results
        StdOut.println("text:    " + txt);

        StdOut.print("pattern: ");
        for (int i = 0; i < index; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
```
参考：https://algs4.cs.princeton.edu/53substring/KMP.java.html  
### (重要) DFA 已理清思路：  
确实比较难，for 语句执行前已初始化一次是非匹配与影子状态（非匹配与影子状态赋值没明显因为它们初始值必为 0，即 int 初值所以不用写），
进入 for 语句之后：是非匹配先行，所以执行后面的更新影子状态时已为该更新准备好数据，第二轮的是非匹配不会更改前一轮的是非匹配结果，
不过上一轮的是非匹配结果与上一轮的更新的影子状态会影响这一轮的非匹配结果。  
影子状态 x 与 j 的关系就是如 PMT 的 prefix 尾与 suffix 尾的关系。  
具体可以再看一次上面的动图理解。  
是非匹配即代码中的：  
```java
for (int c = 0; c < R; c++) 
    dfa[c][j] = dfa[c][x];
dfa[pat.charAt(j)][j] = j+1;
```
更新影子状态即代码中的：  
```java
x = dfa[pat.charAt(j)][x];
```
  
  
  
## Java 实现 2 (KMP PMT)
```java
// 此实现或可返回多匹配结果？
public static List<Integer> KMP(String txt, String pat) {
    List res = new ArrayList<Integer>();
    int[] pmt = PMT(pat);
    
    for (int i=0, j=0; i < txt.length(); i++) {
        while (j > 0 && txt.charAt(i) != pat.charAt(j)) j = pmt[j];
        if (txt.charAt(i) == pat.charAt(j)) j++;
        if (j == pat.length()) {
            res.add(i-j+1);
            j = pmt[j];
        }
    }
    
    return res;
}

// 基于 pattern 来构建 PMT (Partial Match Table)
public static int[] PMT(String pat) { // 有些实现把 pmt 叫做 next 数组
    int pmt[] = new int[pat.length()+1]; // 定义大一个，最后元素 pmt[pat.length()] 指向 LPS 最长前缀的最尾元素的索引值
    pmt[0] = 0;
    pmt[1] = 0;
    // 这部分的循环就是字符串匹配，循环和上面很相似
    for (int i=1, j=0; i < pat.length(); i++) { // i 从 1 开始，j 从 0 开始是因为 LPS (len of the longest prefix of pat[0...j] which is also the suffix) 从索引 0 和 1 就可以开始对比，比如若 pat 为 AAXXX 时，PMT 就是 [0,0,1,x,x,x]，即在 pat[0...1] 时 pat[1] 为 pat[0] 的 LPS
        while (j > 0 && pat.charAt(i) != pat.charAt(j)) j = pmt[j]; // 比如 pat: ABCABDABCABC 或 ABCABDABCABD
        if (pat.charAt(i) == pat.charAt(j)) j++;
        pmt[i+1] = j;
    }
    return pmt;
}
```
![](KMP(PMT2).png)  
参考：https://www.youtube.com/watch?v=uKr9qIZMtzw&t=258s
  
  
  
更多：字符串匹配算法中，有 KMP 算法更快的算法，比如 BM / Boyer-Moore 算法。另外与字符串匹配/搜索相关的有趣算法还有 BK / Burkhard-Keller 树算法等等。  