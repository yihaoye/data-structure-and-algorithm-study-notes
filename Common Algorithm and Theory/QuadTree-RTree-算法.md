# Quad Tree
四叉树是一种树状数据结构，在每一个节点上会有四个子区块。四叉树常应用于二维空间资料的分析与分类。它将资料区分成为四个象限。资料范围可以是方形或矩形或其他任意形状。  
四叉树及其算法在图像压缩处理、二维地图近邻分析、碰撞监测等方面都有广泛应用（[参考资料](https://www.youtube.com/watch?v=lAfOLCk9S-4)）。  

代码实例：  
* [Leetcode 二维矩阵构造四叉树](./../Leetcode%20Practices/algorithms/medium/427%20Construct%20Quad%20Tree.java)
* [Leetcode 两个四叉树的或运算](./../Leetcode%20Practices/algorithms/medium/558%20Logical%20OR%20of%20Two%20Binary%20Grids%20Represented%20as%20Quad-Trees.java)

## OcTree
OcTree（八叉树）用于三维空间。  

# R Tree
R树是用来做空间数据存储的树状数据结构。例如给地理位置，矩形和多边形这类多维数据创建索引。  
在现实生活中，R 树可以用来存储地图上的空间信息，例如地址，或者地图上用来构造街道，建筑，湖泊边缘和海岸线的多边形。然后可以用它来回答 “查找距离 xx 2千米以内的 xx”，“检索距离 xx 2千米以内的所有路段”（然后显示在导航系统中）或者“查找（直线距离）最近的 xxx”这类问题。R 树还可以用来加速使用包括大圆距离在内的各种距离度量方式的最邻近搜索。  
搜索：平均 O($\log_M N$)，最差 O(N)  
插入：最差 O(N)  
通常用于 2D、3D 的空间索引，所以理论上不如 KD 树更通用，也更不如 Hierarchical Navigable Small World（HNSW，通用更大维度且性能优异）。  

## MBR
MBR 的本质：最小外接矩形  
MBR 的全称是 Minimum Bounding Box。
* 不论目标的几何体是一个圆、一个五角星，还是一条弯弯曲曲的河流，R 树都会用一个紧贴其边缘的矩形把它包起来。![](./mbr.png)
* 在索引树里，R 树只存储这个矩形的坐标（左下角和右上角），而不存储复杂的几何细节。  

为什么 R 树要这么做？（过滤-精化模型）  
这是 GIS（地理信息系统）中最高效的 “两阶段查询” 策略：  
第一阶段：过滤 (Filtering) —— 靠 MBR 当搜索方圆 5 公里时：
1. R 树先看搜索圆的 MBR，和索引里存的那些 MBR 有没有交集。
2. 因为矩形和矩形的相交判定极其简单（只需要 4 次数值比较：$x1 < x2$ 等），CPU 可以在毫秒内排除掉 99% 不可能的形状。
3. 这一步是在索引树上完成的，速度极快。

第二阶段：精化 (Refinement) —— 靠真实几何
1. 对于剩下的 1% 的 MBR 满足条件的形状，数据库会把它们的**真实坐标（那些复杂的点集）**从磁盘读出来。
2. 进行精确的几何运算（比如：这个点真的在圆内吗？这两个复杂多边形真的重叠吗？）。
3. 这一步开销大，但因为第一阶段过滤了绝大多数数据，所以整体性能依然很高。

[总结就是，对于数量庞大的数据表，这种索引先行，然后局部精确计算的 “两遍法” 可以在根本上减少查询计算量](https://postgis.net/workshops/zh_Hans/postgis-intro/indexing.html#how-spatial-indexes-work)  

## 整体详解
![](./R树详解.png)  
转载自：https://www.cnblogs.com/yanghh/p/14141407.html  

https://zh.wikipedia.org/wiki/R%E6%A0%91  
