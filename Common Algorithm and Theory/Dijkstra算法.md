Dijkstra (迪杰斯特拉) 算法通常用于解决图数据的最值权重路径问题。  
**[被证明是解决有权单源最短路径问题普遍最优算法](https://arxiv.org/abs/2311.11793)**  

它是典型的单源最短路径算法，用于计算一个节点到其他所有节点的最短路径。主要特点是以起始点为中心向外层层扩展 (广度优先搜索思想)，直到扩展到终点为止。Dijkstra 算法是很有代表性的最短路径算法，此算法运用在许多专业中（如数据结构，图论，运筹学等等）。注意该算法要求图中不存在负权边。  
问题描述：在无向图 G=(V,E) 中，假设每条边 E[i] 的长度为 w[i]，找到由顶点 V0 到其余各点的最短路径。（单源最短路径）  

![](./Dijkstra_Animation.gif)  

## 前置知识
[图数据结构](./../Common%20Data%20Structure%20and%20Data%20Type/Data%20Structure%20Implementation/Graph/README.md)  

### 为什么不采用 BFS
首先确认的是，对于无权图（边的权重都是 1）来说，BFS 确实是找最短路径的最佳算法，但是对于有权图情况则不一样：  
Q：假设先按每个节点访问下一个节点不管权重，计入一个哈希表 `<节点，总成本>`，如果访问总成本（权重总数）不减少就扔掉，如果新路径减少总成本则更新是否可行？  
A：仍有一个问题，当发现新路径总成本减少时，除了该节点需要更新，实际还需要更新其后续节点的，所以最坏情况下所有路径都要走一遍。  

## Code and Example
[Leetcode Q743](./../Leetcode%20Practices/algorithms/medium/743%20Network%20Delay%20Time.java)  

## 进阶
### Bellman Ford 算法
https://zh.wikipedia.org/wiki/%E8%B4%9D%E5%B0%94%E6%9B%BC-%E7%A6%8F%E7%89%B9%E7%AE%97%E6%B3%95  

[代码实现示例](https://leetcode.cn/problems/network-delay-time/solution/gong-shui-san-xie-yi-ti-wu-jie-wu-chong-oghpz/)  
