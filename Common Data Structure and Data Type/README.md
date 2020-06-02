## 常用数据结构
  
- [顺序表/数组 | Array]()
- [链表 | Linked List](./Data%20Structure%20Implementation/Linked%20List/README.md)
- [队列 | Queue]()
  - [优先队列 | PriorityQueue]()
  - [双端队列 | Deque]()
  - [阻塞队列 | BlockingQueue]()
- [栈 | Stack]()
- [堆 | Heap]()
  - [二叉堆 | Binary Heap]()
  - [斐波那契堆 | Fibonacci Heap]()
- [映射表 | Map/Table]()
  - [散列表 | HashMap]()
  - [并发散列表 | ConcurrentHashMap]()
  - [树状映射表 | TreeMap]()
- [集合 | Set]()
  - [散列集 | HashSet]()
  - [树集 | TreeSet]()
- [树 | Tree]()
  - [二叉索搜树 | Binary Search Tree]()
  - [平衡二叉索搜树 | Self-Balanced Binary Search Tree]()
    - [AVL树 | AVL Tree]()
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
