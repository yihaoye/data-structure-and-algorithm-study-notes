# 组合数学

https://zhuanlan.zhihu.com/p/41855459  

理解「排列」「组合」这两个概念到底有何区别。  
排列英文名叫 Arrangement 或者 Permutation，这里采用 Permutation 来表示排列，下文统称为 P。  
组合英文名叫 Combination，下文统称为 C。  
P 和 C 的本质区别在于：决策的顺序对结果有没有影响。  
  
如果要想在 n 个事物中，选择 k 个事物出来，选择的顺序无所谓，那么选择的方式总共有这么多种：  
```
C(n, k) = n! / ((n-k)! * k!)
```  
具体的 Java 实现可参考 [Math - combination](./../Tool%20Sets/Math.java)  
