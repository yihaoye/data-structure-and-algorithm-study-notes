// https://longngn.medium.com/introducing-dag-a-simple-way-to-design-backend-application-4a6b3a9e4382

import java.util.*;

public class DAG {
    static Node root;
	static Set<Node> nodes;

    public DAG(Node node) {
        root = node;
        nodes = new HashSet<>();
        nodes.add(node);
        // ...
    }

    public static void addEdge(Node from, Node to) {
        nodes.add(from); nodes.add(to);
        from.to.add(to);
        to.from.add(from);
        if (hasCycle()) {
            throw new RuntimeException("Cycle detected");
        }
    }

    public static boolean hasCycle() {
        // topoSort check ...
        return false;
    }

    public static void main(String[] args) {
        DAG dag = new DAG(new Node(0, 0));
        Node node1 = new Node(1, 1); Node node2 = new Node(2, 2); // node3, node4 ...
        dag.addEdge(root, node2);
        dag.addEdge(root, node1);
        dag.addEdge(node1, node2);
        // add edge for node3, node4 ...

        Set<Node> nexts = new LinkedHashSet<>();
        nexts.add(dag.root);
        while (!nexts.isEmpty()) {
            Set<Node> tmp = new LinkedHashSet<>();
            for (Node node : nexts) {
                tmp.addAll(node.process());
            }
            nexts = tmp;
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
        // inputs / dependencies aggregation
        for (Node prev : this.from) {
            this.data += prev.data; // or *, -, /, |, &, ^, <<, >>, >>> ...
        }
        System.out.println("Node: " + this.key + " processed with data: " + this.data);

        // outputs
        Set<Node> nexts = new LinkedHashSet<>(); // apply LinkedHashSet to keep order
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