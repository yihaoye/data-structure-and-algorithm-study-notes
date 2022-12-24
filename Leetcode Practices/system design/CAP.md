It is impossible for a distributed software system (especially data store) to simultaneously provide more than two of the following guarantees (CAP): Consistency, Availability, and Partition tolerance. When design a distributed system, trading off among CAP is almost the first thing to consider.  
* Consistency - All nodes see the same data at the same time. It is achieved by updating several nodes before allowing further reads.
* Availability - Every request gets a response on success/failure. It is achieved by replicating the data across different servers.
* Partition tolerance - System continues to work despite message loss or partial failure.
  
Eric Brewer 提出了 CAP 理论。依据这个理论，在一个大规模分布式数据系统中，有三个需求是彼此循环依赖的：一致性、可用性和分区耐受性。  
* 一致性（Consistency）对于所有的数据库客户端使用同样的查询都可以得到同样的结果，即使是有并发更新的时候也是如此。
* 可用性（Availability）所有的数据库客户端总是可以读写数据。
* 分区耐受性（Partition Tolerance）数据库可以分散到多台机器上，即使发生网络故障，被分成多个分区，依然可以提供服务。

Brewer 理论是说，对于任意给定系统，只能强化这三个特性中的两个。这很类似于软件开发中的名言：“你可以让软件很好，让它很快，或者很便宜：不过三个里面你只能选择两个。”  
由于循环依赖关系，我们不得不在它们之中做出选择。比如，你期望获得更强的一致性，那可能就只能拥有较低的分区耐受性，除非你在可用性上做一些让步。  
CAP 理论在 2002 年被证明。不过，在分布式系统中你将不得不面对网络分区的问题，而且在某些时候，机器也常常出现故障，从而导致某些节点不可达。丢包同样是与生俱来的问题。所以，我们可以得到结论，一个分布式系统必须尽力在网络发生分裂的情况下继续工作（具有分区耐受性），这样我们实际上只能在剩下的两个特性里二选一：可用性或是一致性。  

* CA - “主要支持一致性和可用性，这意味着你很可能使用了两阶段提交的分布式事务。也就是说，如果网络发生分裂，那么系统可能会停止响应，这也意味着你的系统很可能被限制在一个数据中心集群以降低网络分区发生的可能性。如果你只需要这个级别的规模扩展，那么可以选择CA取向的系统，它较易于管理，允许你使用简单而且熟悉的结构。”
* CP - “主要支持一致性和分区耐受性，你可以通过改进系统架构，设置数据分片来提升可扩展性。你的数据将保持一致性，但如果有节点发生故障，仍然会有部分数据无法访问（不可用）。”
* AP - “主要支持可用性和分区耐受性，你的系统可能返回不太精确的数据，但系统将始终可用，即使是网络发生分区的时候也是如此。DNS 可能是这类系统中最为著名的例子了，这类系统可扩展性非常强，高可用，而且具有分区耐受性。”

![](./The-CAP-Theorem-Many-of-the-NOSQL-databases-have-loosened-up-the-requirements-on.png)  
![](./CAP%20Databases.jpeg)  
