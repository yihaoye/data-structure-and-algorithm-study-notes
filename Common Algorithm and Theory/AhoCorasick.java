public class AhoCorasick { // by Gemini

    private Node root;

    public AhoCorasick() {
        root = new Node();
    }

    public void addKeyword(String keyword) {
        Node node = root;
        for (char c : keyword.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Node();
            }
            node = node.children[index];
        }
        node.isLeaf = true;
    }

    public void buildFailureLinks() { // 构建失败指针，类似于 KMP 算法的 next 数组，但是这里是一个树结构，所以需要使用 BFS，而不是 KMP 算法的 DP
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (int i = 0; i < 26; i++) {
                Node child = node.children[i];
                if (child == null) continue;
                if (node == root) {
                    child.fail = root;
                } else {
                    Node fail = node.fail;
                    while (fail != null && fail.children[i] == null) {
                        fail = fail.fail;
                    }
                    child.fail = fail == null ? root : fail.children[i];
                }
                queue.offer(child);
            }
        }
    }

    /*
        AC 自动机使用以下步骤来搜索文本：
        1. 从 Trie 树的根节点开始。
        2. 对于文本中的每个字符，沿着 Trie 树向下移动，直到找到该字符的节点。
        3. 如果当前节点是叶节点，则表示找到一个模式串的匹配。
        4. 沿着失败指针向上移动，直到找到另一个叶节点或根节点。
        5. 重复步骤 2 到 4，直到遍历完整个文本。
    */
    public List<String> search(String text) { // 搜索文本中的所有模式串
        List<String> matches = new ArrayList<>();
        Node node = root;
        int start = 0; int end = 0;
        for (char c : text.toCharArray()) {
            int index = c - 'a';
            while (node != null && node.children[index] == null) {
                node = node.fail;
            }
            node = node == null ? root : node.children[index]; // 如果当前节点没有子节点，则表示失败，需要回到根节点
            if (node.isLeaf) { // 如果当前节点是叶节点，则表示找到一个模式串的匹配
                matches.add(text.substring(start, end + 1));
            }
            for (Node p = node.fail; p != null; p = p.fail) { // 沿着失败指针向上移动
                if (!p.isLeaf) continue;
                matches.add(text.substring(start, end + 1));
            }
            start = end + 1; end = end + 1;
        }
        return matches;
    }

    private static class Node {
        private Node[] children;
        private Node fail; // 失败指针，类似于 KMP 算法的 next 数组，但是这里是一个树结构
        private boolean isLeaf;

        public Node() {
            children = new Node[26];
            fail = null;
            isLeaf = false;
        }
    }

    public static void main(String[] args) {
        AhoCorasick ac = new AhoCorasick();
        ac.addKeyword("he");
        ac.addKeyword("she");
        ac.addKeyword("his");
        ac.addKeyword("hers");
        ac.buildFailureLinks();
        String text = "hishe";
        List<String> matches = ac.search(text);
        System.out.println(matches);
    }
}
