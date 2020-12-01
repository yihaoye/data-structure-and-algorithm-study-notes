业务场景的逻辑、要求，有时单个数据结构无法满足，此时需要熟练组合多个数据结构（甚至指针、引用等工具）以满足要求且争取最佳性能。  
  
### 组合 1  
```java
class Node { int id; int data; Node prev; Node next; } // customize a linked list, apply as a Queue, use head node pointer to mock poll()，use tail node pointer to mock add()，use prev and next for remove() or update()，use queueLen to track queue length
Node head = new Node(); // always point to prev Node of first Node of customized linked list
Node tail = new Node(); // always point to last Node of customized linked list
int queueLen = 0; // always self increase/decrease if add/remove a Node, keep no change if update Node
// not use java.util.LinkedList<Node> directly above because try to optimize update operation performance to O(1)

HashMap<Integer, Node> map = new HashMap<Integer, Node>(); // key: Node id (or said Data id), value: Node object
```
  
### 组合 2  
```java
Map<String, String> bValueMap = new HashMap<>(); // key: b id, value: b value
Map<String, Set<String>> aToBIdMap = new HashMap<>(); // key: a id, value: set of b ids (since b id is unique)
```
  
