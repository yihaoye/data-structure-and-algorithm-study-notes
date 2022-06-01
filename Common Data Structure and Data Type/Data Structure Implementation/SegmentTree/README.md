# 线段树

参考：  
https://www.youtube.com/watch?v=rYBtViWXYeI  
https://zxi.mytechroad.com/blog/sp/segment-tree-sp14/  
https://zhuanlan.zhihu.com/p/34150142  
https://cp-algorithms.com/data_structures/segment_tree.html  
https://oi-wiki.org/ds/seg/  
  
## 原理与实现
线段树是一颗近似的完全二叉树，每个节点代表一个区间，节点的权值是该区间的求值。根节点是整个区间。每个节点的左孩子是该节点所代表的的区间的左半部分，右孩子是右半部分。为方便起见，如果区间长度为奇数，则左孩子为较长的半部分。通过线段树，可以用 O(logn) 的时间复杂度完成查询和更新操作。当然，预处理的时间复杂度是 O(n)。  

![](./Segment%20Tree%201.png)  
![](./Segment%20Tree%202.png)  
![](./Segment%20Tree%203.png)  
![](./Segment%20Tree%204.png)  
  
与数状数组类似但是比数状数组直观且强大  
[线段树 Java 实现代码](./SegmentTree.java)  
  
## 例题
[Leetcode 307](./../../../Leetcode%20Practices/algorithms/medium/307%20Range%20Sum%20Query%20-%20Mutable.java)  
