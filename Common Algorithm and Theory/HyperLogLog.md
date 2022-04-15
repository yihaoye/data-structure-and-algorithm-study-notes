# HyperLogLog

如果要实现这么一个功能：  
统计 APP 或网页的一个页面，每天有多少用户点击进入的次数。同一个用户的反复点击进入记为 1 次。  
  
可能很多人想到，用 HashMap 这种数据结构就可以了，也满足了去重。的确，这是一种解决方法，除此之外还有其它的解决方案。  
问题虽不难，但当参与问题中的变量达到一定数量级的时候，再简单的问题都会变成一个难题。假设 APP 中日活用户达到百万或千万以上级别的话，采用 HashMap 的做法，就会导致程序中占用大量的内存。下面尝试估算下 HashMap 的在应对上述问题时候的内存占用。假设定义 HashMap 中 Key 为 string 类型，value 为 bool。key 对应用户的 Id, value 是是否点击进入。明显地，当百万不同用户访问的时候。此 HashMap 的内存占用空间为：100万 * (string + bool)。  
  
参考：  
https://www.modb.pro/db/43389  
https://juejin.cn/post/6844903785744056333  
https://cloud.tencent.com/developer/article/1479465  
