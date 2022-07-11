# 图的二维数组（或嵌套顺序表）表示法

借助一个二维数组表示图，该二维数组的第 i 行，第 j 列的值表示从 `Node[i]` 到 `Node[j]`：  

无向图(网)：是否有边 / 权值，`arr[i][j] == arr[j][i]`，无向图(网)的特性，矩阵关于对角线对称。  

有向图(网)：是否有弧 / 权值。  

https://www.cnblogs.com/cuish/p/3760041.html  
  
或者也可以采用嵌套顺序表（两层） `ArrayList<ArrayList<Integer>>` 的形式表示：[拓扑排序代码示例](./../../../Common%20Algorithm%20and%20Theory/拓扑排序.md#dfs)  
  
或者采用对象的形式表示：[树图遍历代码示例](./../../../Common%20Algorithm%20and%20Theory/树图遍历.md#图遍历)  
  
# 存图方式
在开始讲解最短路之前，先来学习三种「存图」方式。  

## 邻接矩阵
这是一种使用二维矩阵来进行存图的方式。  

适用于边数较多的稠密图使用，当边数量接近点的数量的平方，即 m≈n^2 时，可定义为稠密图。  
```java
// 邻接矩阵数组：w[a][b] = c 代表从 a 到 b 有权重为 c 的边
int[][] w = new int[N][N];

// 加边操作
void add(int a, int b, int c) {
    w[a][b] = c;
}
```

## 邻接表
这也是一种在图论中十分常见的存图方式，与数组存储单链表的实现一致（头插法）。  

这种存图方式又叫链式前向星存图。  

适用于边数较少的稀疏图使用，当边数量接近点的数量，即 m≈n 时，可定义为稀疏图。  
```java
int[] he = new int[N], e = new int[M], ne = new int[M], w = new int[M];
int idx;

void add(int a, int b, int c) {
    e[idx] = b;
    ne[idx] = he[a];
    he[a] = idx;
    w[idx] = c;
    idx++;
}
```
首先 idx 是用来对边进行编号的，然后对存图用到的几个数组作简单解释：  
* he 数组：存储是某个节点所对应的边的集合（链表）的头结点；
* e 数组：由于访问某一条边指向的节点；
* ne 数组：由于是以链表的形式进行存边，该数组就是用于找到下一条边；
* w 数组：用于记录某条边的权重为多少。

因此当我们想要遍历所有由 a 点发出的边时，可以使用如下方式：
```java
for (int i = he[a]; i != -1; i = ne[i]) {
    int b = e[i], c = w[i]; // 存在由 a 指向 b 的边，权重为 c
}
```

## 类
这是一种最简单，但是相比上述两种存图方式，使用得较少的存图方式。  

只有当需要确保某个操作复杂度严格为 O(m) 时，才会考虑使用。  

具体的，建立一个类来记录有向边信息：  
```java
class Edge {
    // 代表从 a 到 b 有一条权重为 c 的边
    int a, b, c;
    Edge(int _a, int _b, int _c) {
        a = _a; b = _b; c = _c;
    }
}
```
通常会使用 List 存起所有的边对象，并在需要遍历所有边的时候，进行遍历：  
```java
List<Edge> es = new ArrayList<>();

// ...

for (Edge e : es) {
    // ...
}
```

摘录自：https://leetcode.cn/problems/network-delay-time/solution/gong-shui-san-xie-yi-ti-wu-jie-wu-chong-oghpz/
