# Quad Tree
四叉树是一种树状数据结构，在每一个节点上会有四个子区块。四叉树常应用于二维空间资料的分析与分类。它将资料区分成为四个象限。资料范围可以是方形或矩形或其他任意形状。  
四叉树及其算法在图像压缩处理、二维地图近邻分析、碰撞监测等方面都有广泛应用（[参考资料](https://www.youtube.com/watch?v=lAfOLCk9S-4)）。  

[Leetcode 二维矩阵构造四叉树](./../Leetcode%20Practices/algorithms/medium/427%20Construct%20Quad%20Tree.java)  
[Leetcode 两个四叉树的或运算](./../Leetcode%20Practices/algorithms/medium/558%20Logical%20OR%20of%20Two%20Binary%20Grids%20Represented%20as%20Quad-Trees.java)

## OcTree
OcTree（八叉树）用于三维空间。  

# R Tree
R树是用来做空间数据存储的树状数据结构。例如给地理位置，矩形和多边形这类多维数据创建索引。  
在现实生活中，R树可以用来存储地图上的空间信息，例如地址，或者地图上用来构造街道，建筑，湖泊边缘和海岸线的多边形。然后可以用它来回答 “查找距离 xx 2千米以内的 xx”，“检索距离 xx 2千米以内的所有路段”（然后显示在导航系统中）或者“查找（直线距离）最近的 xxx”这类问题。R树还可以用来加速使用包括大圆距离在内的各种距离度量方式的最邻近搜索。  
搜索：平均 O($\log_M N$)，最差 O(N)  
插入：最差 O(N)  

https://zh.wikipedia.org/wiki/R%E6%A0%91  
