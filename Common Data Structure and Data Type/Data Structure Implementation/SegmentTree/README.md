# 线段树 | Segment Tree

[Java Code](./SegmentTree4.java)  

线段树也被称为区间树，英文名为 Segment Tree 或者 Interval Tree，是一种高级的数据结构。这种数据结构更多出现在竞赛中，通常基础教材里较少介绍这种数据结构，但是是重要的需掌握的数据结构。  
  
其实这里的线段可以理解为区间，线段树就是为了解决区间问题的。  
有一个很经典的线段树问题是：区间染色。  
  
线段树是二叉树，其基本思想是基于分治法。  
  
## 原理与实现
线段树是一颗近似的完全二叉树，每个节点代表一个区间，节点的权值是该区间的求值（每个非叶子节点表示的是区间内元素的求值，叶子节点存储的就是元素本身。求值是指：区间内最大值、最小值、最值、GCD、LCM 或者这个区间的数字和、其他计算值等等）。根节点是整个区间。每个节点的左孩子是该节点所代表的的区间的左半部分，右孩子是右半部分。为方便起见，如果区间长度为奇数，则左孩子为较长的半部分。通过线段树，可以用 O(logn) 的时间复杂度完成查询和更新操作。当然，预处理的时间复杂度是 O(n)。  
假设要查询 `[4,7]` 区间内的求值，那么不用查到叶子节点，而是查到 `A[4, 7]` 这个节点就行了。当然，并不是所有的区间都恰好落在一个节点，比如要求 `[2, 5]` 区间内的求值。那么就要分别找到 `A[2, 3]` 和 `A[4,5]` 的求值，然后再进行计算。  
从更加抽象的角度来讲，线段树的使用场景就是，对于给定区间，进行更新区间和查询区间操作：  
* 更新区间：更新区间中的一个元素或者一个区间的值。
* 查询区间：查询一个区间 `[i, j]` 的最大值、最小值、或者区间的数字和。

注意，在大多数情况下，是不考虑区间里添加元素和删除元素的，假设区间的大小是固定的。  
  
### 线段树的单点更新
单点修改，需要找到该叶子节点，然后对其进行更新，叶子节点更新之后，它的父节点也需要更新，父节点仍然为左右子树中较小的那个。从根节点出发。找到当前区间的中点，判断中点和待更新的节点的大小，以此来判断是在当前节点的左子树还是右子树。然后递归更新，如果找到了叶子节点，说明当前的这个叶子节点就是需要改的点，直接对其更新即可。最后不要忘了更新父节点的权值。  
  
![](./Segment%20Tree%201.png)  
![](./Segment%20Tree%202.png)  
![](./Segment%20Tree%203.png)  
![](./Segment%20Tree%204.png)  
  
### 简易版实现
与数状数组类似但是比数状数组直观且强大  
[简易版线段树 Java 实现代码 实现1](./SegmentTree1.java)  
  
### 线段树的区间更新
上述是未优化区间更新操作的线段树实现，思考一下：区间修改，假设修改的值有 m 个，直接想到的一个办法就是执行 m 次单点更新，即朴素的想法是用递归的方式一层层修改（类似于线段树的建立），但这样的时间复杂度比较高为 O(mlogN) 这不是所想看到的，假设所有的元素都更新，那么还不如直接重新构建整颗线段树。所以在这里用了一个优化的思想，就是延迟更新，延迟更新就是，更新的时候，不进行操作，只是标记一下这个点需要更新，在真正使用的时候才去更新，这在进行一些数据库的业务的时候，也是很重要的一个思想。这个标记就是懒标记（或延迟标记），懒标记是线段树的精髓所在，使用懒标记后（后面示例代码里的成员变量 mark），对于那些正好是线段树节点的区间，不继续递归下去，而是打上一个标记并更新节点值，将来要用到它的子区间的时候，再向下传递。通过懒惰更新，时间复杂度降为了 O(logN)。在进行区间修改的时候，分 3 种情况：  
1. 当前区间（代表区间的节点）与目标区间没有交集，这时直接结束递归。
2. 当前区间（代表区间的节点）被包括在目标区间里，这时可以更新当前区间节点，别忘了乘上区间长度（比如该区间节点都加上 c，区间有 m 个节点，则该区间节点 val += c*m -- 这个例子的求值逻辑是求和如果线段树是其他求值逻辑则换成其他计算公式），然后打上懒标记（mark += c，这个标记表示`该区间上每一个点都要加上 c`，因为原来可能存在标记，所以是 += 而不是 =。叶子节点可以不打标记，因为不会再向下传递了）
3. 当前区间（代表区间的节点）与目标区间相交，但不被完全包含于其中，这时把当前区间一分为二，进行以下处理：
    1. 这里有个问题，当多次区间修改，且修改之后没有查询且两次修改的区间有交集但不被完全包含时，会造成的父子节点 mark 变量重叠错误。所以必须保证每时每刻线段树内被标记（懒标记不为零）的节点们都只能组成一个或多个无重叠的区间，这样就需要添加一个操作，如果当前区间（代表区间的节点）存在懒标记，要先把懒标记传递给子节点（如果求值逻辑是求和，则注意也是 +=，因为子节点原来可能存在懒标记不为零，这些子节点的值也需要相应的更新 += 传递的懒标记值乘以子节点区间长度）（这个过程并不是递归的，只往下传递一层，所以叫懒标记，以后要用再才继续传递），然后清零本节点的标记。
    2. 传递完标记后，再递归地去处理当前区间（代表区间的节点）的左右两个子节点（即递归重复区间修改的 3 种情况直到每次递归遇到 1、2 情况而停止）。
  
在已经实现区间更新的基础上，单点更新的实现可以直接复用区间更新，只需要令左右端点相等即可。  
  
**优化后线段树的区间查询**  
另外区间查询时也与区间修改完全类似分 3 种情况  
遇到情况 3 时，如果当前区间（代表区间的节点）存在懒标记，也要先把懒标记传递给子节点，完全相同的 pushDown 方法，然后进行同样的递归。  
  
```java
// 链式存储实现方式的线段树的懒更新的 pushDown 方法
private void pushDown(int index) { // 把当前节点的标志值传给子节点
    E node = nodes[index]; // 获取该下标的节点
    nodes[2 * index + 1].addMark(node.mark); // 更新左子树的标志
    nodes[2 * index + 2].addMark(node.mark); // 更新右子树的标志
    nodes[2 * index + 1].data += node.mark * (nodes[2 * index + 1].end - nodes[2 * index + 1].start + 1); // 左子树的值加上标志值乘以子节点区间长度
    nodes[2 * index + 2].data += node.mark * (nodes[2 * index + 2].end - nodes[2 * index + 2].start + 1); // 右子树的值加上标志值乘以子节点区间长度
    node.clearMark(); // 清除当前节点的标志值
}
```  

综上，首先需要判断数组的大小是否为 2^k，是则使用 `2*n` 的空间，否则使用的 `4*n` 空间。下面线段树的实现是基于数组来实现的，不过为了简便起见，下面的实现统一使用 `4*n` 空间来存储线段树。  
使用数组来存储线段树，会有一定的空间浪费，但是换来的时间复杂度的降低是可以接受的。有别于前面的链式存储的实现方式（简易版线段树）。  
  
### 进阶版实现
首先需要两个数组，其中 data 存放原来的数据，tree 就是存放线段树节点。如果要实现懒更新，还需要另一个懒标记数组 marks。  
基本的 API 有 getSize()：返回数组元素个数；query(int queryL, int queryR)：区间查询；update(int updateL, int updateR, E val)：区间更新。  
其中每个元素使用泛型 E 表示，这是为了考虑可扩展性：如果数组元素不是数字，而是自定义的类，那么使用泛型就是比较好的选择。  

[进阶版线段树 Java 实现代码 实现2](./SegmentTree2.java)  
  
### 动态开点线段树
用处：一般线段树开局直接 4*N 的空间，然而当 N 很大时，4 倍空间会消耗很多，这时考虑用动态开点线段树，用多少开多少，跟 c++ 的 new 差不多（[引用出处](https://www.acwing.com/blog/content/309/)）。  
以及[重点参考](https://leetcode.cn/problems/my-calendar-ii/solution/by-lfool-nodi/)。  

[动态开点线段树 - Java 实现代码 实现3](./SegmentTree3.java)  
  
[动态开点线段树 - Java 实现代码 实现4 推荐使用模版](./SegmentTree4.java)  
  
### 其他参考实现
https://github.com/EndlessCheng/codeforces-go/blob/master/copypasta/segment_tree.go  
  
## 二维线段树
[浅谈二维线段树](https://www.cnblogs.com/TheRoadToTheGold/p/8151375.html)  
[线段树套线段树](https://oi-wiki.org/ds/seg-in-seg/)  
[树套树学习笔记](https://www.cnblogs.com/Flying2018/p/13615844.html)  

二维线段树，即用线段树维护一个矩阵  
有两种实现方式：  
1. 原一维线段树的基础上，每一个节点都是一个线段树，代表第二维
2. 四分法转化为一维线段树

第一种方法单次操作的时间复杂度是 `logN^2`，第二种方法最差可以退化到 `N`。  
第二种方法本质上是四叉的一维线段树。  
时间复杂度第一种更优，建议第一种方法。空间复杂度两种都一样。  

ToDo...  

## zkw 线段树
ToDo...  
  
## 例题
* [Leetcode 307](./../../../Leetcode%20Practices/algorithms/medium/307%20Range%20Sum%20Query%20-%20Mutable.java)
* [Leetcode 2286](./../../../Leetcode%20Practices/algorithms/hard/2286%20Booking%20Concert%20Tickets%20in%20Groups.java)（线段树 + 二分搜索）
* [Leetcode 731](./../../../Leetcode%20Practices/algorithms/medium/731%20My%20Calendar%20II.java)
* [Leetcode 732](./../../../Leetcode%20Practices/algorithms/hard/732%20My%20Calendar%20III.java)
* [Leetcode 933](./../../../Leetcode%20Practices/algorithms/easy/933%20Number%20of%20Recent%20Calls.java)
* [Leetcode 715](./../../../Leetcode%20Practices/algorithms/hard/715%20Range%20Module.java)
* [Leetcode 699](./../../../Leetcode%20Practices/algorithms/hard/699%20Falling%20Squares.java)
* [Leetcode 327](./../../../Leetcode%20Practices/algorithms/hard/327%20Count%20of%20Range%20Sum.java)
* [Leetcode 230 follow up](./../../../Leetcode%20Practices/algorithms/medium/230%20Kth%20Smallest%20Element%20in%20a%20BST.java)（线段树 + 二分搜索）
  
## 参考
https://www.youtube.com/watch?v=rYBtViWXYeI  
https://zxi.mytechroad.com/blog/sp/segment-tree-sp14/  
https://zhuanlan.zhihu.com/p/106118909  
https://zhuanlan.zhihu.com/p/174810030  
https://zhuanlan.zhihu.com/p/34150142  
https://cp-algorithms.com/data_structures/segment_tree.html  
https://oi-wiki.org/ds/seg/  
https://algs4.cs.princeton.edu/99misc/SegmentTree.java.html  
