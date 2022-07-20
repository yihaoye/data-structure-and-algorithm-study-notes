class DLinkedNode {
    int id;
    T value;
    DLinkedNode prev;
    DLinkedNode next;
    public DLinkedNode() {}
    public DLinkedNode(int _id, T _value) {id = _id; value = _value;}
}

public class DLinkedList {
    private int size;
    private DLinkedNode head, tail;

    public DLinkedList() {
        this.size = 0;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        size++;
    }

    public void addToTail(DLinkedNode node) {
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    public void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    public void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    public void moveToTail(DLinkedNode node) {
        removeNode(node);
        addToTail(node);
    }

    public DLinkedNode removeHead() {
        DLinkedNode res = head.next;
        removeNode(res);
        return res;
    }

    public DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}
