# 线段树

参考：  
https://www.youtube.com/watch?v=rYBtViWXYeI  
https://zxi.mytechroad.com/blog/sp/segment-tree-sp14/  
https://zhuanlan.zhihu.com/p/34150142  
https://cp-algorithms.com/data_structures/segment_tree.html  
https://oi-wiki.org/ds/seg/  
  
## 原理与实现
线段树是一颗近似的完全二叉树，每个节点代表一个区间，节点的权值是该区间的求值（最值、计算值等等）。根节点是整个区间。每个节点的左孩子是该节点所代表的的区间的左半部分，右孩子是右半部分。为方便起见，如果区间长度为奇数，则左孩子为较长的半部分。通过线段树，可以用 O(logn) 的时间复杂度完成查询和更新操作。当然，预处理的时间复杂度是 O(n)。  
  
### 线段树的单点更新
单点修改，需要找到该叶子节点，然后对其进行更新，叶子节点更新之后，它的父节点也需要更新，父节点仍然为左右子树中较小的那个。从根节点出发。找到当前区间的中点，判断中点和待更新的节点的大小，以此来判断是在当前节点的左子树还是右子树。然后递归更新，如果找到了叶子节点，说明当前的这个叶子节点就是需要改的点，直接对其更新即可。最后不要忘了更新父节点的权值。  
  
![](./Segment%20Tree%201.png)  
![](./Segment%20Tree%202.png)  
![](./Segment%20Tree%203.png)  
![](./Segment%20Tree%204.png)  
  
与数状数组类似但是比数状数组直观且强大  
[线段树 Java 实现代码](./SegmentTree.java)  
  
### 线段树的区间更新
上述是未优化区间更新操作的线段树实现，思考一下：区间修改，假设修改的值有 m 个，直接想到的一个办法就是执行 m 次单点更新，这时候的复杂度为 O(mlogn) 这不是所想看到的，假设所有的元素都更新，那么还不如直接重新构建整颗线段树。所以在这里用了一个优化的思想，就是延迟更新，延迟更新就是，更新的时候，不进行操作，只是标记一下这个点需要更新，在真正使用的时候才去更新，这在进行一些数据库的业务的时候，也是很重要的一个思想。在封装节点的时候，有一个成员变量前面一直没有使用，那就是 mark，现在就是使用这个成员变量的时候了。在进行区间修改的时候，把这个组成这个待修改区间的所有子区间都标记上。查找组成当前待修改区间的所有子区间的方法和查询方法是一样的，也是分三种情况。  
为防止多次区间修改，且修改之后没有查询而造成的父子节点 mark 变量重叠错误。必须保证每时每刻被标记的节点都组成一个或多个无重叠的区间，这样就需要在添加一个操作，就是在对某个节点的子节点进行标记的时候，把本节点的已经被标记过的部分扩展到子节点中，并把本节点的权值更新为其子节点的权值的求值（最值、计算值等等），然后去除本节点的标记。  
```java
private void pushDown(int index) { // 把当前节点的标志值传给子节点
    Node node = nodes[index]; // 获取该下标的节点
    if (node.mark!=0) {
        nodes[(index << 1) + 1].addMark(node.mark); // 更新左子树的标志
        nodes[(index << 1) + 2].addMark(node.mark); // 更新右子树的标志
        nodes[(index << 1) + 1].data += node.mark; // 左子树的值加上标志值
        nodes[(index << 1) + 2].data += node.mark; // 右子树的值加上标志值
        node.clearMark(); // 清除当前节点的标志值
    }
}

// 然后更新操作的代码也要改
public void update(int index, int start, int end, int data) {
    Node node = nodes[index]; // 获取当前的节点
    if (node.start>end || node.end<start) return; // 如果当前的节点代表的区间和待更新的区间毫无交集，则返回不处理。
    if (node.start>=start && node.end<=end) { // 如果当前的区间被包含在待查询的区间之内，则当前区间需要被标记上
        node.data += data;
        node.addMark(data);
        return;
    }
    pushDown(index); //注意这里加了一句 在更新左右子树之前进行扩展操作
    update((index<<1)+1, start, end, data);
    update((index<<1)+2, start, end, data);
    node.data = Math.min(nodes[(index<<1)+1].data, nodes[(index<<1)+2].data);
}
```  

延迟更新，那么具体什么时候更新呢，就是在查询的时候。在查询的时候更新，查询操作用到什么就更新什么，这就是延迟更新。  
```java
public int query(int index,int start,int end) {
    Node node = nodes[index];
    if (node.start>end || node.end<start) return Integer.MAX_VALUE; // 当前区间和待查询区间没有交集，那么返回一个极大值
    if (node.start>=start && node.end<=end) return node.data; // 如果当前的区间被包含在待查询的区间内则返回当前区间的最小值
    pushDown(index); // 注意加了这一句 在返回左右子树的最小值之前，进行扩展操作
    return Math.min(query((index<<1)+1,start,end), query((index<<1)+2,start,end)); // 递归查询左子树和右子树
}
```  
  
## 例题
[Leetcode 307](./../../../Leetcode%20Practices/algorithms/medium/307%20Range%20Sum%20Query%20-%20Mutable.java)  
