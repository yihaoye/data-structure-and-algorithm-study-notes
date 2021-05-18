业务场景的逻辑、要求，有时单个数据结构无法满足，此时需要熟练组合多个数据结构（甚至指针、引用等工具）以满足要求且争取最佳性能。  
首先可以从 Java Collection 里找：List、Set、Map、Queue（包括实现了 Stack 的 Deque），如果都没有头绪的话则思考是否需要自制与以上类似的数据结构（但因自定义而更趁手），最后若仍不能满足业务场景则考虑高级数据结构如 Tree、Graph、Matrix 等等。  
  
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
  
### 组合 3
```java
// 编写一个根据 key 查找 list object 的程序，并利用 Map 充当缓存，以提高查找效率
class Obj { String key; String data; }
List<Obj> list = new ArrayList<>();
Map<String, Obj> cache = new HashMap<>();

public Obj getObj(String key) {
    Obj obj = cache.get(key);
    if (obj == null) {
        obj = findInList(key);
        cache.put(key, obj);
    }
    return obj;
}

public Obj findInList(String key) {
    for (Obj obj : list) {
        if (obj.key.equals(key)) return obj;
    }
    return null;
}
```
组合 3 为不严谨的大意展示，其实就是 [Leetcode Q146 LRU Cache](./../../Leetcode%20Practices/algorithms/medium/146%20LRU%20Cache.java)，具体请参考该题题解实现。  
