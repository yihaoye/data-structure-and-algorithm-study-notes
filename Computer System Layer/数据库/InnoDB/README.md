# MySQL InnoDB 实现原理   

MySQL InnoDB 引擎现在广为使用，它提供了事务，行锁，日志等一系列特性，本文分析下 InnoDB 的内部实现机制，MySQL 版本为 5.7.24，操作系统为 Debian 9。  

## InnoDB 架构
![](./InnoDB%20架构.jpeg)  
Innodb 架构图  

InnoDB 的架构分为两块：内存中的结构和磁盘上的结构。InnoDB 使用日志先行策略，将数据修改先在内存中完成，并且将事务记录成重做日志(Redo Log)，转换为顺序 IO 高效的提交事务。  

这里日志先行，说的是日志记录到数据库以后，对应的事务就可以返回给用户，表示事务完成。但是实际上，这个数据可能还只在内存中修改完，并没有刷到磁盘上去。内存是易失的，如果在数据落地前，机器挂了，那么这部分数据就丢失了。  

InnoDB 通过 redo 日志来保证数据的一致性。如果保存所有的重做日志，显然可以在系统崩溃时根据日志重建数据。  

当然记录所有的重做日志不太现实，所以 InnoDB 引入了检查点机制。即定期检查，保证检查点之前的日志都已经写到磁盘，则下次恢复只需要从检查点开始。  

## InnoDB 内存中的结构
### Buffer Pool  
缓冲池缓存的数据包括 Page Cache、Change Buffer、Data Dictionary Cache 等，通常 MySQL 服务器的 80% 的物理内存会分配给 Buffer Pool。  

基于效率考虑，InnoDB 中数据管理的最小单位为页，默认每页大小为 16KB，每页包含若干行数据。  

为了提高缓存管理效率，InnoDB 的缓存池通过一个页链表实现，很少访问的页会通过缓存池的 LRU 算法淘汰出去。  

InnoDB 的缓冲池页链表分为两部分：New sublist(默认占5/8缓存池) 和 Old sublist(默认占3/8缓存池，可以通过 innodb_old_blocks_pct 修改，默认值为 37)，其中新读取的页会加入到 Old sublist 的头部，而 Old sublist 中的页如果被访问，则会移到 New sublist 的头部。  

缓冲池的使用情况可以通过 show engine innodb status 命令查看。其中一些主要信息如下：  
![](./Show%20Engine%20InnoDB%20Status.png)  

### Change Buffer
TBC...  
