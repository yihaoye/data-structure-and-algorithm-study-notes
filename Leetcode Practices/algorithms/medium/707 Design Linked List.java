/*
Design your implementation of the linked list. You can choose to use the singly linked list or the doubly linked list. A node in a singly linked list should have two attributes: val and next. val is the value of the current node, and next is a pointer/reference to the next node. If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node in the linked list. Assume all nodes in the linked list are 0-indexed.

Implement these functions in your linked list class:

get(index) : Get the value of the index-th node in the linked list. If the index is invalid, return -1.
addAtHead(val) : Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
addAtTail(val) : Append a node of value val to the last element of the linked list.
addAtIndex(index, val) : Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
deleteAtIndex(index) : Delete the index-th node in the linked list, if the index is valid.
 

Example:

Input: 
["MyLinkedList","addAtHead","addAtTail","addAtIndex","get","deleteAtIndex","get"]
[[],[1],[3],[1,2],[1],[1],[1]]
Output:  
[null,null,null,null,2,null,3]

Explanation:
MyLinkedList linkedList = new MyLinkedList(); // Initialize empty LinkedList
linkedList.addAtHead(1);
linkedList.addAtTail(3);
linkedList.addAtIndex(1, 2);  // linked list becomes 1->2->3
linkedList.get(1);            // returns 2
linkedList.deleteAtIndex(1);  // now the linked list is 1->3
linkedList.get(1);            // returns 3
 

Constraints:

0 <= index,val <= 1000
Please do not use the built-in LinkedList library.
At most 2000 calls will be made to get, addAtHead, addAtTail,  addAtIndex and deleteAtIndex.
*/



// My Solution:
class Node {
    int val;
    Node next;
    Node(int val) { this.val = val; }
    Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }
}

class MyLinkedList {
    Node head;
    Node tail;
    int size;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index >= this.size) return -1;
        Node cur = this.head;
        for (int i=0; i<index; i++) cur = cur.next;
        return cur.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        if (this.head == null) {
            this.head = new Node(val, null);
            this.tail = this.head;
        } else {
            this.head = new Node(val, this.head);
        }
        this.size++;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if (this.tail == null) {
            this.tail = new Node(val, null);
            this.head = this.tail;
        } else {
            this.tail.next = new Node(val, null);
            this.tail = this.tail.next;
        }
        this.size++;
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index < 0 || index > this.size) return;
        if (index == 0) {
            addAtHead(val);
            return;
        }
        if (index == this.size) {
            addAtTail(val);
            return;
        }
        Node cur = this.head;
        for (int i=0; i<index-1; i++) cur = cur.next;
        Node in = new Node(val, cur.next);
        cur.next = in;
        this.size++;
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= this.size || this.size == 0) return;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size--;
            return;
        }
        if (index == 0) {
            this.head = this.head.next;
            this.size--;
            return;
        }
        Node cur = this.head;
        for (int i=0; i<index-1; i++) cur = cur.next;
        cur.next = cur.next.next;
        if (index == this.size-1) this.tail = cur;
        this.size--;
    }
}
/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */



// Other's Solution:
class MyLinkedList {
    class Node {
      public int val;
      public Node next;
      public Node(int val) { this.val = val; this.next = null; }
      public Node(int val, Node next) { this.val = val; this.next = next; }
    }
    
    private Node head;
    private Node tail;
    private int size;
    
    public MyLinkedList() {
      this.head = this.tail = null;
      this.size = 0;
    }
    
    private Node getNode(int index) {
      Node n = new Node(0, this.head);
      while (index-- >= 0) {
        n = n.next;
      }
      return n;
    }
   
    public int get(int index) {
      if (index < 0 || index >= size) return -1;
      return getNode(index).val;
    }
   
    public void addAtHead(int val) {
      this.head = new Node(val, this.head);
      if (this.size++ == 0)
        this.tail = this.head;    
    }
   
    public void addAtTail(int val) {
      Node n = new Node(val);
      if (this.size++ == 0)
        this.head = this.tail = n;
      else
        this.tail = this.tail.next = n;
    }
   
    public void addAtIndex(int index, int val) {
      if (index < 0 || index > this.size) return;
      if (index == 0)  { this.addAtHead(val); return; }
      if (index == size) { this.addAtTail(val); return; }
      Node prev = this.getNode(index - 1);
      prev.next = new Node(val, prev.next);
      ++this.size;
    }
   
    public void deleteAtIndex(int index) {
      if (index < 0 || index >= this.size) return;
      Node prev = this.getNode(index - 1);
      prev.next = prev.next.next;
      if (index == 0) this.head = prev.next;
      if (index == this.size - 1) this.tail = prev;
      --this.size;
    }
  }
  // https://zxi.mytechroad.com/blog/list/leetcode-707-design-linked-list/