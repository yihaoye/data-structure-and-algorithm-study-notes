// https://www.baeldung.com/cs/dag-applications#practical_applications_of_dag

import java.util.*;

// 通常实现了拓扑排序也就实现了 DAG
public class DAG {
    Set<Node> roots;
	Set<Node> nodes;

    public DAG() {
        roots = new HashSet<>();
        nodes = new HashSet<>();
    }

    public void addRoot(Node node) {
        roots.add(node);
        nodes.add(node);
    }

    public void addEdge(Node from, Node to) {
        if (from == null || to == null) throw new RuntimeException("Invalid null node");
        nodes.add(from); nodes.add(to);
        from.to.add(to); to.from.add(from);
        if (hasCycle(from)) throw new RuntimeException("Cycle detected");
    }

    public boolean hasCycle(Node node) {
        // DFS/BFS check ...
        return false;
    }

    public void run() {
        Set<Node> nexts = new LinkedHashSet<>();
        nexts.addAll(this.roots);
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
            node.result = node.data;
        }
    }

    public static void main(String[] args) {
        DAG dag = new DAG();
        Node node0 = new Node(0, 0), node1 = new Node(1, 1), node2 = new Node(2, 2); // node3, node4 ...
        dag.addRoot(node0);
        dag.addEdge(node0, node2);
        dag.addEdge(node0, node1);
        dag.addEdge(node1, node2);
        // add edge for node3, node4 ...

        dag.run();
        // Node: 0 processed with result: 0
        // Node: 1 processed with result: 1
        // Node: 2 processed with result: 3
    }
}

class Node {
    int key, data, ready, result;
    Set<Node> from, to;

    public Node(int key, int data) {
        this.key = key;
        this.data = data;
        this.result = data;
        this.from = new LinkedHashSet<>(); // apply LinkedHashSet for enabling order
        this.to = new LinkedHashSet<>();
    }

    public Set<Node> process() { // process and return next prepared process nodes
        // inputs / dependencies aggregation
        for (Node prev : this.from) {
            this.result += prev.result; // or *, -, /, |, &, ^, <<, >>, >>> ...
        }
        System.out.println("Node: " + this.key + " processed with result: " + this.result);

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