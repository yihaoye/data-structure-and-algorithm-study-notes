# Kadane 算法
参考：https://zh.wikipedia.org/wiki/%E6%9C%80%E5%A4%A7%E5%AD%90%E6%95%B0%E5%88%97%E9%97%AE%E9%A2%98  
在计算机科学中，最大子数列问题的目标是在数列的一维方向找到一个连续的子数列，使该子数列的和最大。例如，对一个数列 −2, 1, −3, 4, −1, 2, 1, −5, 4，其连续子数列中和最大的是 4, −1, 2, 1, 其和为 6。  

展示 `[2, 3, -1, -20, 5, 10]` 子数列之和随着子序列起始位置变化而改变，图中每条线的一点都代表一种可能的连续子序列，y 轴代表所选序列之和，x 轴代表所选序列的终点，每种颜色开始都从 x 轴的不同位置开始，代表所选序列的起点位置  
该问题最初由布朗大学的 Ulf Grenander 教授于 1977 年提出，当初他为了展示数字图像中一个简单的最大似然估计模型。不久之后卡内基梅隆大学的 Jay Kadane 提出了该问题的线性算法。  

Kadane 算法扫描一次整个数列的所有数值，在每一个扫描点计算以该点数值为结束点的子数列的最大和（正数和）。该子数列由两部分组成：以前一个位置为结束点的最大子数列、该位置的数值。因为该算法用到了“最优子结构”（以每个位置为终点的最大子数列都是基于其前一位置的最大子数列计算得出），该算法可看成动态规划的一个例子。  
```python
def max_subarray(A):
    max_ending_here = max_so_far = A[0]
    for x in A[1:]:
        max_ending_here = max(x, max_ending_here + x)
        max_so_far = max(max_so_far, max_ending_here)
    return max_so_far
```  
该问题的一个变种是：如果数列中含有负数元素，允许返回长度为零的子数列。该问题可用如下代码解决：  
```python
def max_subarray(A):
    max_ending_here = max_so_far = 0
    for x in A:
        max_ending_here = max(0, max_ending_here + x)
        max_so_far = max(max_so_far, max_ending_here)
    return max_so_far
```  
这种算法稍作修改就可以记录最大子数列的起始位置。Kadane 算法时间复杂度为 O(N)，空间复杂度为 O(1)。  
  
## 类似例题
[Leetcode 152](./../Leetcode%20Practices/algorithms/medium/152%20Maximum%20Product%20Subarray.java)  
