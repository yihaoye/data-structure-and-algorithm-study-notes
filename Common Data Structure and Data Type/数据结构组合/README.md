业务场景的逻辑、要求，有时单个数据结构无法满足，此时需要熟练组合多个数据结构（甚至指针、引用等工具）以满足要求且争取最佳性能。  
  
组合 1 ?  
```java
LinkedList<Node> queue = new LinkedList<Node>(); // apply Queue with poll()、remove(obj o)
HashMap<Integer, Node> map = new HashMap<Integer, Node>(); // key: Node id, value: Node object
class Node { int id; int data; Node prev; Node next; }
```
  
组合 2  
```java
Map<String, String> bValueMap = new HashMap<>(); // key: b id, value: b value
Map<String, Set<String>> aToBIdMap = new HashMap<>(); // key: a id, value: set of b ids (since b id is unique)
```
  
