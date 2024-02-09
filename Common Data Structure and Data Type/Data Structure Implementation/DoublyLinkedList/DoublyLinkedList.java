class DoublyLinkedList {
    Node dummyHead, dummyTail;
    int size;

    DoublyLinkedList() {
        dummyHead = new Node();
        dummyTail = new Node();
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
        size = 0;
    }

    public void insert(Node prev, Node node) { // prev or node should be get by an external HashMap<Key, Node>, prev could be also dummyHead
        if (prev == dummyTail) throw new IllegalArgumentException("Cannot insert after dummyTail");
        Node next = prev.next;
        prev.next = node;
        node.prev = prev;
        node.next = next;
        next.prev = node;
        size++;
    }

    public void remove(Node node) { // node should be get by an external HashMap<Key, Node>
        if (node == dummyHead || node == dummyTail) throw new IllegalArgumentException("Cannot remove dummyHead or dummyTail");
        Node prev = node.prev, next = node.next;
        prev.next = next;
        next.prev = prev;
        size--;
    }
}

class Node {
    int key;
    int value;

    public Node() {}
    
    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
