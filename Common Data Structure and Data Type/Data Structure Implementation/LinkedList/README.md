### Design Linked List
  
[参考](https://zxi.mytechroad.com/blog/list/leetcode-707-design-linked-list/): [优化版实现代码 Code](./LinkedList.cpp)  
  
![](./Design%20Linked%20List.png)  
  
通过上面的实现代码构建 LinkedList 时，LinkedList 对象的 value 和 next 存储在内存 heap（堆）区里，head 和 tail 和 size 存储在内存 stack（栈）区里（参考上图）。  
在 heap（堆）区上申请空间存储数据要记得写上析构函数执行回收操作，以防止内存泄漏。  
