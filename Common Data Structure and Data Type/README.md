# 常用数据结构
  
- [数组 | Array]() (索引 O(1)，遍历 O(N)，插入/删除节点 O(N))
- [线性表 | Linear List]()
  - [顺序表 | ArrayList]() (索引 O(1)，遍历 O(N)，添加节点 O(1)，插入/删除节点 O(N))
  - [向量 | Vector](./Data%20Structure%20Implementation/Vector/) (索引 O(1)，遍历 O(N)，添加节点 O(1)，插入/删除节点 O(N))
  - [链表 | LinkedList](./Data%20Structure%20Implementation/LinkedList/) (索引 O(N)，遍历 O(N)，插入/删除节点 O(1))
    - [单链表 | Singly Linked List]()
    - [双链表 | Doubly Linked List]()
    - [循环链表 | Circular Linked List]()
    - [跳表 | Skip List]()
- [队列 | Queue]()
  - [链表 | LinkedList](./Data%20Structure%20Implementation/LinkedList/) (入队/出队 O(1)，首值 O(1))
  - [优先队列 | PriorityQueue]() (遍历 O(N)，入队/出队 O(logN)，首值 O(1))
  - [双端队列 | Deque]()
    - [链表 | LinkedList](./Data%20Structure%20Implementation/LinkedList/) (同上)
    - [数组双端队列 | ArrayDeque](./../HackerRank%20Practises/java/medium/Java%20Dequeue.java)
    - [基于链表的线程安全无界双端队列 | ConcurrentLinkedDeque]()
    - [基于链表的FIFO双端阻塞队列 | LinkedBlockingDeque]()
  - [基于链表的线程安全无界队列 | ConcurrentLinkedQueue]()
  - [阻塞队列 | BlockingQueue](./Data%20Structure%20Implementation/BlockingQueue/)
    - [基于数组的并发阻塞队列 | ArrayBlockingQueue](./Data%20Structure%20Implementation/ArrayBlockingQueue/ArrayBlockingQueue.java) (入队/出队 O(1)，首值 O(1))
    - [延期阻塞队列 | DelayQueue]() (入队/出队 O(logN)，首值 O(1))
    - [基于链表的FIFO双端阻塞队列 | LinkedBlockingDeque]()
    - [基于链表的FIFO阻塞队列 | LinkedBlockingQueue](./Data%20Structure%20Implementation/LinkedBlockingQueue/LinkedBlockingQueue.java) (入队/出队 O(1)，首值 O(1))
    - [并发同步阻塞队列 | SynchronousQueue]() (入队/出队 O(1)，首值 O(1))
    - [带优先级的无界阻塞队列 | PriorityBlockingQueue]() (入队/出队 O(logN)，首值 O(1))
    - [基于链表的FIFO无界阻塞队列 | LinkedTransferQueue](./Data%20Structure%20Implementation/LinkedTransferQueue/)
- [栈 | Stack]() (入栈/出栈 O(1))
- [堆 | Heap]()
  - [二叉堆 | Binary Heap](./Data%20Structure%20Implementation/BinaryHeap/) (构造 O(N)，遍历 O(N)，插入/删除节点 O(logN))
    - [优先队列 | PriorityQueue]()
  - [斐波那契堆 | Fibonacci Heap]()
- [映射表 | Map/Table]()
  - [散列表 | HashMap](./Data%20Structure%20Implementation/HashMap/) (索引搜索 O(1)，插入/删除节点 O(1))
  - [并发散列表 | ConcurrentHashMap]() (索引搜索 O(1)，插入/删除节点 O(1))
  - [链散列表 | LinkedHashMap]() (按插入顺序的散列表，索引搜索 O(1)，插入/删除节点 O(1))
  - [树状映射表 | TreeMap]() (索引搜索 O(logN)，插入/删除节点 O(logN))
  - [枚举表 | EnumMap](./Data%20Structure%20Implementation/EnumMap/) (索引搜索 O(1))
- [集合 | Set]()
  - [散列集 | HashSet]() (索引搜索 O(1)，插入/删除节点 O(1))
  - [链散列集 | LinkedHashSet]() (索引搜索 O(1)，插入/删除节点 O(1))
  - [树集 | TreeSet]() (索引搜索 O(logN)，插入/删除节点 O(logN))
  - [枚举集 | EnumSet]()
- [树 | Tree]()
  - [二叉搜索树 | Binary Search Tree]() (搜索 O(logN) - O(N)，插入/删除节点 O(1))
  - [平衡二叉搜索树 | Self-Balanced Binary Search Tree]()
    - [AVL树 | AVL Tree](./Data%20Structure%20Implementation/AVL%20Tree/) (搜索 O(logN)，插入/删除节点 O(logN))
    - [红黑树 | Red-Black Tree]() (搜索 O(logN)，插入/删除节点 O(logN))
  - [平衡搜索树 | Self-Balanced Search Tree]()
    - [B树 | B Tree]()
    - [B+树 | B Tree]()
    - [2–3树 | 2-3 Tree]()
  - [字典树 | Trie](./Data%20Structure%20Implementation/Trie/)
  - [并查集 | Disjoint-Set/Union-Find Forest]()
- [图 | Graph]()
  - [有向图 | Directed Graph]()
  - [无向图 | Undirected Graph]()
  - [加权图 | Weighted Graph]()
  
以上有些数据结构可能只有 Java 提供了官方实现 [more](https://www.zhihu.com/question/325814788) ，以上总结未完待续...  
  
*术语表：索引 - get | 搜索 - search | 遍历 - traversal | 添加 - add | 插入 - insert | 删除 - remove | 入队 - offer | 出队 - poll | 首值 - peek*  
  
### 常用数据结构的一些细节补充
* 在 Java，以上大部分数据结构属于 Collection / 集合类，参见[图解](./Java%20Collection.png)。以下是线程安全集合类与非线程安全集合类（《Java concurrency in practice》中定义：一个不论运行时/Runtime 如何调度线程都不需要调用方提供额外的同步和协调机制还能正确地运行的类是线程安全的；但线程安全的类/数据结构通常仅指的是其独立的方法或数据是原子化/加锁的，参考[链接](https://blog.csdn.net/a158123/article/details/84948046)，另外可参考代码[案例](../Computer%20System%20Layer/并发与并行(Java)/Jenkov/RaceConditions.java)；线程不安全就是不提供数据访问保护，有可能出现多个线程先后更改数据造成所得到的数据是混乱数据）。
  * LinkedList、ArrayList、HashSet 是非线程安全的，Vector 是线程安全的（Vector 类中的方法很多有 synchronied 进行修饰，这样就导致了 Vector 在效率上无法与 ArrayLst 相比），Java 中 ArrayList、LinkedList、Vector 的[区别](https://www.cnblogs.com/wanlipeng/archive/2010/10/21/1857791.html)。
  * HashMap 是非线程安全的，HashTable 是线程安全的，它俩的[详细区别](https://stackoverflow.com/questions/40471/differences-between-hashmap-and-hashtable)。
  * StringBuilder 是非线程安全的，StringBuffer 是线程安的。
* Map、Dictionary、Table 的[异同](https://www.zhihu.com/question/27581780)。
* Java 的数组/array 是语言本身提供的，而 ArrayList、LinkedList、Vector(向量) 等等都是 Java Util 包基于数组实现的，参见[图解](./Java%20Util%20Collections.png)。
* Java 的 Vector、Stack 已过时（JDK 1.0）、不建议使用（性能不佳、继承了被弃用的父类、应用了不佳的旧设计和 API），相应的可以使用 ArrayList、Deque（具备 Stack 的 LIFO 功能、相关方法，但注意只调用 push()/pop()/peek() 方法，避免调用 Deque 的其他方法，具体比如 ArrayDeque）替代。同理 Java 的 Hashtable 也被弃用，原因也是性能不佳以及继承了被弃用的父类。
* List 是按索引顺序访问的长度可变的有序表，优先使用 ArrayList 而不是 LinkedList；当需要对数据进行多此访问的情况下选用 ArrayList，当要对数据进行多次增加删除修改时采用 LinkedList。LinkedList 是一个双向链表，没有初始化大小，也没有扩容的机制，就是一直在前面或者后面新增。
* 使用 Iterator 访问 List 的代码比使用索引在写法上更复杂，但是通过 Iterator 遍历 List 永远是最高效的方式，并且，由于 Iterator 遍历是如此常用，所以，Java 的 for each 循环本身就使用 Iterator 遍历。实际上，只要实现了 Iterable 接口的集合类都可以直接用 for each 循环来遍历，Java 编译器本身并不知道如何遍历集合对象，但它会自动把 for each 循环变成 Iterator 的调用，原因就在于 Iterable 接口定义了一个 `Iterator<E> iterator()` 方法，强迫集合类必须返回一个 Iterator 实例。
* 在 List 中查找元素时，List 的实现类通过元素的 equals() 方法比较两个元素是否相等，因此，放入的元素必须正确覆写 equals() 方法，Java 标准库提供的 String、Integer 等已经覆写了 equals() 方法；编写 equals() 方法可借助 Objects.equals() 判断。如果不在 List 中查找元素，就不必覆写 equals() 方法。
* Java 里，枚举（Enumeration）接口虽然它本身不属于数据结构,但它在其他数据结构的范畴里应用很广。枚举（The Enumeration）接口定义了一种从数据结构中取回连续元素的方式。
* 堆/Heap 是计算机科学中的一种特别的树状数据结构（比如堆排序/heap sort，是基于二叉堆树作为此算法的数据结构）。
* Java 中，Queue 是通过 LinkedList 实现的而不是 ArrayList，[原因](https://stackoverflow.com/questions/41665425/why-arraylist-doesnt-implements-queue)。
* LinkedList 比较全能，它即是 List，又是 Queue，还是 Deque。但是在使用的时候，总是用特定的接口来引用它（比如应该写 `Deque<String> d = new LinkedList<>();` 而不是 `LinkedList<String> d = new LinkedList<>();`。同理适用于其他实现了多个接口的数据结构/类），这是因为持有接口说明代码的抽象层次更高，而且接口本身定义的方法代表了特定的用途。面向抽象编程的一个原则就是：尽量持有接口，而不是具体的实现类。
* TreeSet 是二叉树（红黑树的树据结构）实现的，TreeSet 中的数据是自动排好序的，不允许放入 null 值；HashSet 是哈希表实现的，HashSet 中的数据是无序的可以放入 null，但只能放入一个 null，两者中的值都不重复，就如数据库中唯一约束。HashSet 是基于哈希算法实现的，其性能通常都优于 TreeSet。为快速查找而设计的 Set，通常都应该使用 HashSet，在需要排序的功能时，才使用 TreeSet。
* 使用 TreeSet 和使用 TreeMap 的要求一样，添加的元素必须正确实现 Comparable 接口，如果没有实现 Comparable 接口，那么创建 TreeSet 时必须传入一个 Comparator 对象。TreeSet 实现了 SortedSet 接口，而 SortedSet 接口继承 Set 接口，HashSet 直接实现 Set 接口，Set 接口并不保证有序，而 SortedSet 接口才保证元素是有序的。
  * TreeMap、TreeSet 只提供了一般（或按元素所属的类的默认/自定义 compareTo 逻辑或传入的 Comparator 对象的 compare 逻辑）排序功能（而且每次插入新元素后会自动触发集合按元素的类的 compareTo 排序逻辑或传入的 Comparator 对象的 compare 逻辑来重新排一次序），并不能保证集合里的元素顺序按插入顺序，能保证集合里的元素顺序按插入顺序的是 LinkedHashMap、LinkedHashSet。
* 放入 PriorityQueue 的元素，必须实现 Comparable 接口，PriorityQueue 会根据元素的排序顺序决定出队的优先级。如果要放入的元素并没有实现 Comparable 接口的话，也可以提供一个 Comparator 对象来判断两个元素的顺序。PriorityQueue 在每次插入新元素后会自动触发排序。
* Java 没有内置专门的二叉堆数据结构，因为可以使用 PriorityQueue（最小堆）来当二叉堆用。
* LinkedBlockingQueue 与 ArrayBlockingQueue 对比：1. ArrayBlockingQueue 入队出队采用一把锁，导致入队出队相互阻塞，效率低下；2. LinkedBlockingQueue 入队出队采用两把锁，入队出队互不干扰，效率较高；3. 二者都是有界队列，如果长度相等且出队速度跟不上入队速度，都会导致大量线程阻塞；4. LinkedBlockingQueue 如果初始化不传入初始容量，则使用最大 int 值，如果出队速度跟不上入队速度，会导致队列特别长，占用大量内存。
* JDK 为什么没有实现图（Graph）数据结构？因为一般更倾向于根据具体算法需求和数据的特性来自己实现图，就说最常用的两种实现 —— 邻接矩阵和邻接表。通常可以简单的使用二维数组做邻接矩阵，如果图连通性很低，也可以用 HashMap 做邻接矩阵。邻接表可以用链表的数组做，也可以 TreeMap 的数组做，也可以 HashMap 里放 TreeMap 或者其他数据结构来做。这要看具体的算法需要。这也是为什么通常说图不常用，其实不是不常用，是图的通用性不值得 JDK 去做一个 build-in 的实现给它（树 Tree 同理）。而且选择第三方实现的时候也要慎重，要结合具体的算法需要和数据情况。（[摘录来源](https://www.zhihu.com/question/275694305/answer/392905791)）  
  
## 更多性能展示
<details>
<summary>表格示</summary>

List                 | Add  | Remove | Get  | Contains | Next | Data Structure
---------------------|------|--------|------|----------|------|---------------
ArrayList            | O(1) |  O(n)  | O(1) |   O(n)   | O(1) | Array
LinkedList           | O(1) |  O(1)  | O(n) |   O(n)   | O(1) | Linked List
CopyOnWriteArrayList | O(n) |  O(n)  | O(1) |   O(n)   | O(1) | Array
  
Set                   |    Add   |  Remove  | Contains |   Next   | Size | Data Structure
----------------------|----------|----------|----------|----------|------|-------------------------
HashSet               | O(1)     | O(1)     | O(1)     | O(h/n)   | O(1) | Hash Table
LinkedHashSet         | O(1)     | O(1)     | O(1)     | O(1)     | O(1) | Hash Table + Linked List
EnumSet               | O(1)     | O(1)     | O(1)     | O(1)     | O(1) | Bit Vector
TreeSet               | O(log n) | O(log n) | O(log n) | O(log n) | O(1) | Red-black tree
CopyOnWriteArraySet   | O(n)     | O(n)     | O(n)     | O(1)     | O(1) | Array
ConcurrentSkipListSet | O(log n) | O(log n) | O(log n) | O(1)     | O(n) | Skip List
  
Queue                   |  Offer   | Peak |   Poll   | Remove | Size | Data Structure
------------------------|----------|------|----------|--------|------|---------------
PriorityQueue           | O(log n) | O(1) | O(log n) |  O(n)  | O(1) | Priority Heap
LinkedList              | O(1)     | O(1) | O(1)     |  O(1)  | O(1) | Array
ArrayDequeue            | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | Linked List
ConcurrentLinkedQueue   | O(1)     | O(1) | O(1)     |  O(n)  | O(n) | Linked List
ArrayBlockingQueue      | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | Array
PriorirityBlockingQueue | O(log n) | O(1) | O(log n) |  O(n)  | O(1) | Priority Heap
SynchronousQueue        | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | None!
DelayQueue              | O(log n) | O(1) | O(log n) |  O(n)  | O(1) | Priority Heap
LinkedBlockingQueue     | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | Linked List
  
Map                   |   Get    | ContainsKey |   Next   | Data Structure
----------------------|----------|-------------|----------|-------------------------
HashMap               | O(1)     |   O(1)      | O(h / n) | Hash Table
LinkedHashMap         | O(1)     |   O(1)      | O(1)     | Hash Table + Linked List
IdentityHashMap       | O(1)     |   O(1)      | O(h / n) | Array
WeakHashMap           | O(1)     |   O(1)      | O(h / n) | Hash Table
EnumMap               | O(1)     |   O(1)      | O(1)     | Array
TreeMap               | O(log n) |   O(log n)  | O(log n) | Red-black tree
ConcurrentHashMap     | O(1)     |   O(1)      | O(h / n) | Hash Tables
ConcurrentSkipListMap | O(log n) |   O(log n)  | O(1)     | Skip List
  
以上参考自：https://gist.github.com/psayre23/c30a821239f4818b0709  
</details>
  
<details>
<summary>图示</summary>

![](./Big-O%20Complexity%20Chart.png)  
![](./Common%20Data%20Structure%20Operations.png)  
![](./Array%20Sorting%20Algorithms.png)  
![](./BIG-O-CHEATSHEET.png)  
</details>
<br>
  
## 更多常用的高级数据结构
  
* [高级数据结构 | Advanced Data Structures]()
  * [MIT Course](https://courses.csail.mit.edu/6.851/fall17/)
  * [Geeksforgeeks](https://www.geeksforgeeks.org/advanced-data-structures/)
  
## 非常用的专用数据结构
- [参考阅读](https://stackoverflow.com/questions/500607/what-are-the-lesser-known-but-useful-data-structures)  
  
  
  
# 常用数据类型
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
* 虽然功能一样，但 StringBuffer 是线程安全的，而 StringBuilder 不是线程安全的，在单线程下 StringBuilder 性能比 StringBuffer 高。
  
  
  
# 数据结构组合
[重定向](./数据结构组合/)  
