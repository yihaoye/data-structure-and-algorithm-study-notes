// https://algs4.cs.princeton.edu/54regexp/NFA.java.html

/******************************************************************************
 *  Compilation:  javac NFA.java
 *  Execution:    java NFA regexp text
 *  Dependencies: Stack.java Bag.java Digraph.java DirectedDFS.java
 *
 *  % java NFA "(A*B|AC)D" AAAABD
 *  true
 *
 *  % java NFA "(A*B|AC)D" AAAAC
 *  false
 *
 *  % java NFA "(a|(bc)*d)*" abcbcd
 *  true
 *
 *  % java NFA "(a|(bc)*d)*" abcbcbcdaaaabcbcdaaaddd
 *  true
 *
 *  Remarks
 *  -----------
 *  The following features are not supported:
 *    - The + operator
 *    - Multiway or
 *    - Metacharacters in the text
 *    - Character classes.
 *
 ******************************************************************************/

/**
 *  The {@code NFA} class provides a data type for creating a
 *  <em>nondeterministic finite state automaton</em> (NFA) from a regular
 *  expression and testing whether a given string is matched by that regular
 *  expression.
 *  It supports the following operations: <em>concatenation</em>,
 *  <em>closure</em>, <em>binary or</em>, and <em>parentheses</em>.
 *  It does not support <em>mutiway or</em>, <em>character classes</em>,
 *  <em>metacharacters</em> (either in the text or pattern),
 *  <em>capturing capabilities</em>, <em>greedy</em> or <em>reluctant</em>
 *  modifiers, and other features in industrial-strength implementations
 *  such as {@link java.util.regex.Pattern} and {@link java.util.regex.Matcher}.
 *  <p>
 *  This implementation builds the NFA using a digraph and a stack
 *  and simulates the NFA using digraph search (see the textbook for details).
 *  The constructor takes time proportional to <em>m</em>, where <em>m</em>
 *  is the number of characters in the regular expression.
 *  The <em>recognizes</em> method takes time proportional to <em>m n</em>,
 *  where <em>n</em> is the number of characters in the text.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/54regexp">Section 5.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class NFA {

    private Digraph graph;     // digraph of epsilon transitions
    private String regexp;     // regular expression
    private final int m;       // number of characters in regular expression

    /**
     * Initializes the NFA from the specified regular expression.
     *
     * @param  regexp the regular expression
     */
    public NFA(String regexp) {
        this.regexp = regexp;
        m = regexp.length();
        Stack<Integer> ops = new Stack<Integer>();
        graph = new Digraph(m+1);
        for (int i = 0; i < m; i++) {
            int lp = i;
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|')
                ops.push(i);
            else if (regexp.charAt(i) == ')') {
                int or = ops.pop();

                // 2-way or operator
                if (regexp.charAt(or) == '|') {
                    lp = ops.pop();
                    graph.addEdge(lp, or+1);
                    graph.addEdge(or, i);
                }
                else if (regexp.charAt(or) == '(')
                    lp = or;
                else assert false;
            }

            // closure operator (uses 1-character lookahead)
            if (i < m-1 && regexp.charAt(i+1) == '*') {
                graph.addEdge(lp, i+1);
                graph.addEdge(i+1, lp);
            }
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
                graph.addEdge(i, i+1);
        }
        if (ops.size() != 0)
            throw new IllegalArgumentException("Invalid regular expression");
    }

    /**
     * Returns true if the text is matched by the regular expression.
     *
     * @param  txt the text
     * @return {@code true} if the text is matched by the regular expression,
     *         {@code false} otherwise
     */
    public boolean recognizes(String txt) {
        DirectedDFS dfs = new DirectedDFS(graph, 0);
        Bag<Integer> pc = new Bag<Integer>(); // Bag 类在这里表示一个简单的集合，它是用于收集元素的数据结构，类似于集合（Set）或列表（List），但没有顺序，允许元素重复。在这个代码示例中，Bag 类用于收集NFA的可能状态，以便在状态转移过程中进行处理。
        for (int v = 0; v < graph.V(); v++)
            if (dfs.marked(v)) pc.add(v);

        // Compute possible NFA states for txt[i+1]
        for (int i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || txt.charAt(i) == '(' || txt.charAt(i) == ')')
                throw new IllegalArgumentException("text contains the metacharacter '" + txt.charAt(i) + "'");

            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc) {
                if (v == m) continue;
                if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.')
                    match.add(v+1);
            }
            if (match.isEmpty()) continue;

            dfs = new DirectedDFS(graph, match);
            pc = new Bag<Integer>();
            for (int v = 0; v < graph.V(); v++)
                if (dfs.marked(v)) pc.add(v);

            // optimization if no states reachable
            if (pc.size() == 0) return false;
        }

        // check for accept state
        for (int v : pc)
            if (v == m) return true;
        return false;
    }

    /**
     * Unit tests the {@code NFA} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String regexp = "(" + args[0] + ")";
        String txt = args[1];
        NFA nfa = new NFA(regexp);
        StdOut.println(nfa.recognizes(txt));
    }
}
/*
这段代码实现了一个基于正则表达式的非确定性有限状态自动机（NFA）：

1. NFA 类定义了一个非确定性有限状态自动机，其构造函数接受一个正则表达式作为参数，然后根据正则表达式构建对应的NFA。
2. 构造函数中首先初始化了一些成员变量，包括正则表达式 regexp 的长度 m，以及用于构建状态转移图的有向图 graph。
3. 接下来的代码通过遍历正则表达式的每个字符，并根据字符的类型构建NFA的状态转移图。具体来说：
    * 遇到左括号 ( 或者管道符 | 时，将当前位置入栈，以便后续的处理。
    * 遇到右括号 ) 时，弹出栈顶的元素，判断是一个左括号还是一个管道符。如果是管道符，则在状态转移图中添加两条边，表示二选一的选择。
    * 遇到闭包操作符 * 时，根据闭包的性质，在状态转移图中添加两条边，表示可以重复零次或多次。
    * 其他情况下，在状态转移图中添加一条边，表示当前字符的匹配。
4. recognizes 方法用于判断输入文本是否被正则表达式匹配。该方法使用深度优先搜索（DFS）来模拟NFA的状态转移过程，并在每个字符匹配后更新可能的NFA状态。最终判断是否达到接受状态。
5. main 方法用于进行单元测试，接受两个命令行参数：一个正则表达式和一个文本。它创建一个NFA实例，并调用 recognizes 方法来判断文本是否匹配正则表达式，并输出结果。

这段代码实现了一个简单的正则表达式匹配器，可以用于判断文本是否满足特定的正则表达式模式。
*/
