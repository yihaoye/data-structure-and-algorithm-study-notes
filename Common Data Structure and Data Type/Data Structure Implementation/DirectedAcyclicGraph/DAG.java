import java.util.*;

class DAG {
    Node root;
	Set<Node> nodes;

    public DAG(Node root) {
        this.root = root;
        // ...
    }

    public void addEdge(Node from, Node to) {
        nodes.add(from); nodes.add(to);
        nodes.get(from).to.add(to);
        nodes.get(to).from.add(from);
        if hasCycle() {
            throw new RuntimeException("Cycle detected");
        }
    }

    public boolean hasCycle() {
        // topoSort check ...
    }

    public static void main(String[] args) {
        DAG dag = new DAG(New Node(0, 0));
        // dag.addEdge(xxx, xxx);
        // ...

        Set<Node> nexts = new LinkedHashSet<>();
        nexts.add(dag.root);
        while (!nexts.isEmpty()) {
            for (Node node : nexts) {
                Set<Node> tmp = node.process();
                nexts.addAll(tmp);
            }
        }

        // clean up ready data for next time usage
        for (Node node : nodes) {
            node.ready = 0;
        }

        // next round ...
    }
}

class Node {
    int key, data, ready;
    Set<Node> from, to;

    public Node(int key, int data) {
        this.key = key;
        this.data = data;
        this.from = new LinkedHashSet<>(); // apply LinkedHashSet for enabling order
        this.to = new LinkedHashSet<>();
    }

    public Set<Node> process() { // process and return next prepared process nodes
        for (Node prev : this.from) {
            this.data += prev.data; // or *, -, /, |, &, ^, <<, >>, >>> ...
        }

        Set<Node> nexts = new LinkedHashSet<>();
        for (Node next : this.to) {
            next.ready++;
            if (next.ready == next.from.size()) {
                nexts.add(next);
            }
        }
        return nexts;
    }

    // impelemnt equals() and hashCode() for Node
}