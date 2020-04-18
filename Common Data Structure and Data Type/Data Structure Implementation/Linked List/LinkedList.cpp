// Author: Huahua
// https://zxi.mytechroad.com/blog/list/leetcode-707-design-linked-list/
class LinkedList {
public:  
  LinkedList(): head_(nullptr), tail_(nullptr), dummy_(0), size_(0) {}
  
  ~LinkedList() {
    Node* node = head_;
    while (node) {
      Node* cur = node;
      node = node->next;
      delete cur;
    }
    head_ = nullptr;
    tail_ = nullptr;
  }
  
  int get(int index) {
    if (index < 0 || index >= size_) return -1;
    return getNode(index)->val;
  }
 
  void addAtHead(int val) {    
    head_ = new Node(val, head_);
    if (size_++ == 0)
      tail_ = head_;   
  }
  
  void addAtTail(int val) {
    auto node = new Node(val);
    if (size_++ == 0) {
      head_ = tail_ = node;
    } else {    
      tail_->next = node;
      tail_ = tail_->next;
    }    
  }
 
  void addAtIndex(int index, int val) {
    if (index < 0 || index > size_) return;
    if (index == 0) return addAtHead(val);
    if (index == size_) return addAtTail(val);
    Node* prev = getNode(index - 1);
    prev->next = new Node(val, prev->next);
    ++size_;
  }
 
  void deleteAtIndex(int index) {
    if (index < 0 || index >= size_) return;
    Node* prev = getNode(index - 1);
    Node* node_to_delete = prev->next;
    prev->next = node_to_delete->next;
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
  
  Node* head_; // Does not own
  Node* tail_; // Does not own
  Node dummy_;
  int size_;
  
  Node* getNode(int index) {
    dummy_.next = head_;
    Node* n = &dummy_;
    for (int i = 0; i <= index; ++i)
      n = n->next;
    return n;
  }
};