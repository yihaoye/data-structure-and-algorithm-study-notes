# LSM 树

LSM 树 (Log-Structured-Merge-Tree) 的名字往往会给初识者一个错误的印象，事实上，LSM 树并不像 B+ 树、红黑树一样是一颗严格的树状数据结构，它其实是一种存储结构，目前 HBase, LevelDB, RocksDB 这些 NoSQL 存储都是采用的 LSM 树。  

LSM 树的核心特点是利用顺序写来提高写性能，但因为分层 (此处分层是指的分为内存和文件两部分) 的设计会稍微降低读性能，但是通过牺牲小部分读性能换来高性能写，使得 LSM 树成为非常流行的存储结构。  

## LSM 树的核心思想
![](./LSMTree%20Architecture.jpeg)  
如上图所示，LSM 树有以下三个重要组成部分：  
* MemTable
* Immutable MemTable
* SSTable (Sorted String Table)
  

## 参考
* https://zhuanlan.zhihu.com/p/181498475
* https://www.modb.pro/db/379790
