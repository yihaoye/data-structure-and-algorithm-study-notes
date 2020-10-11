private class LinkedListNode {
    int id;
    String val;
    LinkedListNode prev;
    LinkedListNode next;

    LinkedListNode(int id, String val) {
        this.id = id;
        this.val = val;
    }
}



// use linked list node
LinkedListNode node = new LinkedListNode(id, val);
node.next = new LinkedListNode(id, val);
node.next.val = val;

// remove linked list node
if(node.next != null) node.next = node.next.next;