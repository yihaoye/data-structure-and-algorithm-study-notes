## HashMap 实现
[Simple Version Java Code 实现](./../../../Leetcode%20Practices/algorithms/easy/706%20Design%20HashMap.java#L91-L151)。  
代码模仿大致原理，但是尚不严谨。  
官方版在[此](https://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/util/HashMap.java)。  

哈希表实现有 2 种经典和 1 种新方式 [Ref](https://www.cnblogs.com/apocelipes/p/17562468.html)：
1. 链表法（经典）- 中心思想在于如果 key 映射到了相同的位置，那么就把这些 key 和 value 存在一张链表里。查找的时候先用 hash 函数计算得到位置，然后再从存放在该位置上的链表里一个个遍历找到 key 相等的条目。
2. 线性探测法（经典）- 在遇到 hash 冲突的时候，它会从冲突的位置开始向后一个个查找，直到找到一个空位，或者没找到然后索引回到了冲突发生的位置，这时会进行扩容然后再重新执行上述插入流程。查找也是一样的，首先通过 hash 函数计算得到元素应该在的位置，然后从这个位置开始一个个比较 key 是否相等，遇到被删除的元素就跳过，遇到空的 slot 就停止搜索，这表示元素不在这个哈希表中。
3. SwissTable（新式）- 核心思想是大部分哈希表操作都是围绕哈希函数计算得到的结果和 slot 的状态（是否能存数据是否有目标数据在 slot 里）进行的，而要知道这些信息不需要整个 slot 的数据，因此把这些操作需要的信息提取出来作为元信息进行操作，内存效率和 CPU 效率都可以优于直接操作存放完整数据的 slot。
  
## HashMap HashTable 区别
https://stackoverflow.com/questions/40471/what-are-the-differences-between-a-hashmap-and-a-hashtable-in-java  
