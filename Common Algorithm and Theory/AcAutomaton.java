// https://zhuanlan.zhihu.com/p/80325757
// by 司徒正美
import java.util.*;

class Node {
    char val;
    Map<Character, Node> children = new HashMap<>();
    Node fail;
    String pattern;
    int endCount;

    public Node(char val) {
        this.val = val;
    }
}

class Trie {
    Node root;

    public Trie() {
        root = new Node(' ');
    }

    public void insert(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Node node = cur.children.get(c);
            if (node == null) {
                node = new Node(c);
                cur.children.put(c, node);
            }
            cur = node;
        }
        cur.pattern = word;
        cur.endCount++;
    }
}

public class AhoCorasick {
    public static void createGoto(Trie trie, String[] patterns) {
        for (String pattern : patterns) {
            trie.insert(pattern);
        }
    }

    public static void createFail(Trie ac) {
        Node root = ac.root;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node == null) continue;
            for (char c : node.children.keySet()) {
                Node child = node.children.get(c);
                if (node == root) {
                    child.fail = root;
                } else {
                    Node p = node.fail;
                    while (p != null) {
                        if (p.children.containsKey(c)) {
                            child.fail = p.children.get(c);
                            break;
                        }
                        p = p.fail;
                    }
                    if (p == null) {
                        child.fail = root;
                    }
                }
                queue.add(child);
            }
        }
    }

    public static List<String> match(Trie ac, String text) {
        Node root = ac.root;
        Node p = root;
        List<String> ret = new ArrayList<>();
        Map<String, Integer> unique = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (p != root && !p.children.containsKey(c)) {
                p = p.fail;
            }
            p = p.children.getOrDefault(c, root);
            Node node = p;
            while (node != root) {
                if (node.endCount > 0) {
                    int pos = i - node.pattern.length() + 1;
                    String msg = String.format("匹配模式串 %s 其起始位置在 %d", node.pattern, pos);
                    System.out.println(msg);
                    if (!unique.containsKey(node.pattern)) {
                        unique.put(node.pattern, 1);
                        ret.add(node.pattern);
                    }
                }
                node = node.fail;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Trie ac = new Trie();
        createGoto(ac, new String[]{"she", "shr", "say", "he", "her"});
        createFail(ac);
        List<String> result = match(ac, "one day she say her has eaten many shrimps");
        System.out.println(result);
    }
}
