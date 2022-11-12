# 红黑树
* [圖解演算法教學 平衡樹 課程介紹影片 入門|介紹|教學|LeetCode|資料結構](https://www.youtube.com/watch?v=_hha1J_Ru-E&list=PLVVMQF8vWNCLW_QXfQasdGvzK8aSGnf3-)  
  
![](./红黑树规则.png)  

## 原理及实现
https://ivanzz1001.github.io/records/post/data-structure/2018/06/24/ds-red-black-tree  
https://tech.meituan.com/2016/12/02/redblack-tree.html  

```java
class Node<T> {
   public  T value;
   public  Node<T> parent;
   public  boolean isRed;
   public  Node<T> left;
   public  Node<T> right;
}


```

