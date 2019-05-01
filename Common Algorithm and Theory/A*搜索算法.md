## A*算法总结 (Summary of the A* Method)  

原文地址： http://www.gamedev.net/reference/articles/article2003.asp  
翻译： https://blog.csdn.net/hitwhylz/article/details/23089415  
  
步骤列表：
1. 把起点加入 open list。
  
2. 重复如下过程：  
    a. 遍历 open list ，查找 F 值最小的节点，把它作为当前要处理的节点。  
    b. 把这个节点移到 close list。  
    c. 对当前方格的 8 个相邻方格的每一个方格？  
    * 如果它是不可抵达的或者它在 close list 中，忽略它。否则，做如下操作。  
    * 如果它不在 open list 中，把它加入 open list ，并且把当前方格设置为它的父亲，记录该方格的 F ， G 和 H 值。  
    * 如果它已经在 open list 中，检查这条路径 ( 即经由当前方格到达它那里 ) 是否更好，用 G 值作参考。更小的 G 值表示这是更好的路径。如果是这样，把它的父亲设置为当前方格，并重新计算它的 G 和 F 值。如果你的 open list 是按 F 值排序的话，改变后你可能需要重新排序。  
    d. 停止，当你  
    * 把终点加入到了 open list 中，此时路径已经找到了，或者  
    * 查找终点失败，并且 open list 是空的，此时没有路径。
  
3. 保存路径。从终点开始，每个方格沿着父节点移动直至起点，这就是你的路径。
  
  
  
维护 Open List：这是 A* 中最重要的部分。每次访问 Open list，都要找出具有最小 F 值的方格。有几种做法可以做到这个。可以随意保存路径元素，当需要找到具有最小 F 值的方格时，遍历整个 open list。这个很简单，但对于很长的路径会很慢。这个方法可以通过维护一个排好序的表来改进，每次当需要找到具有最小 F 值的方格时，仅取出表的第一项即可。  
对于小地图，这可以很好的工作，但这不是最快的方案。追求速度的 A* 程序员使用了叫做二叉堆的东西，这种方法在多数场合下会快 2—3 倍，对于更长的路径速度成几何级数增长 (10 倍甚至更快) 。如果你想更多的了解二叉堆，请阅读 Using Binary Heaps in A* Pathfinding (http://www.policyalmanac.org/games/binaryHeaps.htm)。  