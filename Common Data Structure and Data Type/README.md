## 常用数据结构
  
- [顺序表/数组 | Array]() (查询 O(N)，插入/删除节点 O(N))
- [链表 | Linked List](./Data%20Structure%20Implementation/Linked%20List/README.md) (查询 O(N)，插入/删除节点 O(1))
- [队列 | Queue]() (查询 O(N)，插入/删除节点 O(N)，入队/出队 O(1))
  - [优先队列 | PriorityQueue]() (查询 O(N)，插入/删除节点 O(logN)，入队/出队 O(1))
  - [双端队列 | Deque](./../HackerRank%20Practises/java/medium/Java%20Dequeue.java)
  - [阻塞队列 | BlockingQueue](./Data%20Structure%20Implementation/Blocking%20Queue/README.md) (offer O(1)，poll O(1))
- [栈 | Stack]() (入栈/出栈 O(1))
- [堆 | Heap]()
  - [二叉堆 | Binary Heap]() (查询 O(N)，插入/删除节点 O(logN))
  - [斐波那契堆 | Fibonacci Heap]()
- [映射表 | Map/Table]() (查询 O(1)，插入/删除节点 O(1))
  - [散列表 | HashMap](./Data%20Structure%20Implementation/Hash%20Map/README.md) (查询 O(1)，插入/删除节点 O(1))
  - [并发散列表 | ConcurrentHashMap]()
  - [树状映射表 | TreeMap]() (查询 O(logN)，插入/删除节点 O(logN))
- [集合 | Set]() (查询 O(1)，插入/删除节点 O(1))
  - [散列集 | HashSet]() (查询 O(1)，插入/删除节点 O(1))
  - [树集 | TreeSet]() (查询 O(logN)，插入/删除节点 O(logN))
- [树 | Tree]() (查询 O(N)，插入/删除节点 O(1))
  - [二叉搜索树 | Binary Search Tree]() (查询 O(logN) - O(N)，插入/删除节点 O(1))
  - [平衡二叉搜索树 | Self-Balanced Binary Search Tree]() (查询 O(logN)，插入/删除节点 O(logN))
    - [AVL树 | AVL Tree](./Data%20Structure%20Implementation/AVL%20Tree/)
    - [红黑树 | Red-Black Tree]()
  - [平衡搜索树 | Self-Balanced Search Tree]()
    - [B树 | B Tree]()
    - [B+树 | B Tree]()
    - [2–3树 | 2-3 Tree]()
  - [字典树 | Trie](./Data%20Structure%20Implementation/Trie/README.md)
  - [并查集 | Disjoint-Set/Union-Find Forest]()
- [图 | Graph]()
  - [有向图 | Directed Graph]()
  - [无向图 | Undirected Graph]()
  - [加权图 | Weighted Graph]()
  
  
以上总结未完待续...  
  
### 常用数据结构的一些细节补充
* HashMap 与 Hashtable 有[区别](https://stackoverflow.com/questions/40471/differences-between-hashmap-and-hashtable)，Map、Dictionary、Table 的[异同](https://www.zhihu.com/question/27581780)。
* Java 的数组/array 是语言本身提供的，而 ArrayList、LinkedList、Vector(向量) 等等都是 Java Util 包基于数组实现的，参见[图解](./Java%20Util%20Collections.png)。Java 中 ArrayList、LinkedList、Vector 的[区别](https://www.cnblogs.com/wanlipeng/archive/2010/10/21/1857791.html)。
* Java 里，枚举（Enumeration）接口虽然它本身不属于数据结构,但它在其他数据结构的范畴里应用很广。枚举（The Enumeration）接口定义了一种从数据结构中取回连续元素的方式。
* 堆/Heap 是计算机科学中的一种特别的树状数据结构（比如堆排序/heap sort，是基于二叉堆树作为此算法的数据结构）。
* Java 中，Queue 是通过 LinkedList 实现的而不是 ArrayList，[原因](https://stackoverflow.com/questions/41665425/why-arraylist-doesnt-implements-queue)。  
  
### 非常用的专用数据结构
- [参考阅读](https://stackoverflow.com/questions/500607/what-are-the-lesser-known-but-useful-data-structures)
  
  
  
## 常用数据类型
- [布尔型 | Boolean]()
- [字节 | Byte]()
- [整型 | Integer]()
  - [有符号整型 | Signed Integer]()
  - [无符号整型 | Unsigned Integer]()
- [浮点型 | Float]()
- [字符串 | String]()
- [字符 | Character]()
  
  
以上总结未完待续...  
  
### 常用数据类型的一些细节补充
* 有些编程语言（比如 Java 早期版本）不支持 unsigned（无符号）数据类型（如 unsigned char, unsigned short, unsigned int 和 unsigned long 等等），因此在一些与 Bitwise 或数据类型符号相关的题目中需要注意，参见[例子](./../Leetcode%20Practices/algorithms/easy/190%20Reverse%20Bits.java)。
  
  
  
## 图示
![](./Big-O%20Complexity%20Chart.png)  
![](./Common%20Data%20Structure%20Operations.png)  
![](./Array%20Sorting%20Algorithms.png)  
![](./BIG-O-CHEATSHEET.png)  
  
