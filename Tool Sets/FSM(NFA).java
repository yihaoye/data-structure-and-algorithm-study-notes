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
        Bag<Integer> pc = new Bag<Integer>();
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
