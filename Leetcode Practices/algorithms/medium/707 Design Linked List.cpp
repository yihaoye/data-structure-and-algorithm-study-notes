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



// Other's Solution:
class MyLinkedList {
public:
    /** Initialize your data structure here. */
    MyLinkedList(): head_(nullptr), tail_(nullptr), size_(0) {}
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    int get(int index) {
        if (index < 0 || index >= size_) return -1;
        auto node = head_;
        while (index--)
          node = node->next;    
        return node->val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    void addAtHead(int val) {
        head_ = new Node(val, head_);
        if (size_++ == 0)
          tail_ = head_;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    void addAtTail(int val) {
        auto node = new Node(val);
        if (size_++ == 0) {
          head_ = tail_ = node;
        } else {    
          tail_->next = node;
          tail_ = tail_->next;
        }
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    void addAtIndex(int index, int val) {
        if (index < 0 || index > size_) return;
        if (index == 0) return addAtHead(val);
        if (index == size_) return addAtTail(val);
        Node dummy(0, head_); // 通过 dummy 函数在内存的栈区创建 prev Node，因为若直接 new 一个 Node 是在堆区创建，在堆区创建需要记得写删除释放内存，在栈区则不用因为系统自动回收。（这是 C/C++ 才需要注意的，Java 等有自动垃圾回收机制的语言则无需注意）
        Node* prev = &dummy;
        while (index--) prev = prev->next;
        prev->next = new Node(val, prev->next);    
        ++size_;
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    void deleteAtIndex(int index) {
        if (index < 0 || index >= size_) return;
        Node dummy(0, head_);
        Node* prev = &dummy;
        for (int i = 0; i < index; ++i)
          prev = prev->next;
        Node* node_to_delete = prev->next;
        prev->next = prev->next->next;
        if (index == 0) head_ = prev->next;
        if (index == size_ - 1) tail_ = prev;
        delete node_to_delete;
        --size_;
    }
private:
    struct Node {
        int val;
        Node* next;
        Node(int _val): Node(_val, nullptr) {}
        Node(int _val, Node* _next): val(_val), next(_next) {}
    };
    Node* head_;
    Node* tail_;
    int size_;
};

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList* obj = new MyLinkedList();
 * int param_1 = obj->get(index);
 * obj->addAtHead(val);
 * obj->addAtTail(val);
 * obj->addAtIndex(index,val);
 * obj->deleteAtIndex(index);
 */
// https://www.youtube.com/watch?v=dmezFFv522I&list=PLLuMmzMTgVK6a-2aAwPieEIIuIJY6JTSq&index=4