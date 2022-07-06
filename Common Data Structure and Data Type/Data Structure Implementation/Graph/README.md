# 图的二维数组（或嵌套顺序表）表示法

借助一个二维数组表示图，该二维数组的第 i 行，第 j 列的值表示从 `Node[i]` 到 `Node[j]`：  

无向图(网)：是否有边 / 权值，`arr[i][j] == arr[j][i]`，无向图(网)的特性，矩阵关于对角线对称。  

有向图(网)：是否有弧 / 权值。  

https://www.cnblogs.com/cuish/p/3760041.html  
  
或者也可以采用嵌套顺序表（两层） `ArrayList<ArrayList<Integer>>` 的形式表示：[拓扑排序代码示例](./../../../Common%20Algorithm%20and%20Theory/拓扑排序.md#dfs)  
  
或者采用对象的形式表示：[树图遍历代码示例](./../../../Common%20Algorithm%20and%20Theory/树图遍历.md#图遍历)  
