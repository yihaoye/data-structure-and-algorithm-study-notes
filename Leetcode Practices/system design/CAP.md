It is impossible for a distributed software system (especially data store) to simultaneously provide more than two of the following guarantees (CAP): Consistency, Availability, and Partition tolerance. When design a distributed system, trading off among CAP is almost the first thing to consider.  
* Consistency - All nodes see the same data at the same time. It is achieved by updating several nodes before allowing further reads.
* Availability - Every request gets a response on success/failure. It is achieved by replicating the data across different servers.
* Partition Tolerance - System continues to work despite message loss or partial failure.
  
Eric Brewer 提出了 CAP 理论。依据这个理论，在一个大规模分布式数据系统中，有三个需求是彼此循环依赖的：强一致性、可用性和分区耐受性。  
* Consistency (强一致性)：对某个指定的客户端来说，读操作能返回最新的写操作。对于数据分布在不同节点上的数据上来说，如果在某个节点更新了数据，那么在其他节点如果都能读取到这个最新的数据，那么就称为强一致性，如果有某个节点没有读取到，那就是分布式不一致。或者说对于所有的数据库客户端使用同样的查询都可以得到同样的结果，即使是有并发更新的时候也是如此。
* Availability (可用性)：非故障的节点在合理的时间内返回合理的响应 (不是错误和超时的响应，比如所有的数据库客户端总是可以读写数据)。可用性的两个关键一个是合理的时间，一个是合理的响应。合理的时间指的是请求不能无限被阻塞，应该在合理的时间给出返回。合理的响应指的是系统应该明确返回结果并且结果是正确的，这里的正确指的是比如应该返回 50，而不是返回 40。
* Partition Tolerance (分区容错性)：当出现网络分区后，系统能够继续工作。打个比方，这里个集群有多台机器，有台机器网络出现了问题，但是这个集群仍然可以正常工作（比如数据库集群读写）。

Brewer 理论是说，对于任意给定系统，只能强化这三个特性中的两个。这很类似于软件开发中的名言：“你可以让软件很好，让它很快，或者很便宜：不过三个里面你只能选择两个。”  
由于循环依赖关系，我们不得不在它们之中做出选择。比如，你期望获得更强的一致性，那可能就只能拥有较低的分区耐受性，除非你在可用性上做一些让步。  
CAP 理论在 2002 年被证明。不过，在分布式系统中将不得不面对网络分区的问题，而且在某些时候，机器也常常出现故障，从而导致某些节点不可达。丢包同样是与生俱来的问题。所以，可以得到结论，一个分布式系统必须尽力在网络发生分裂的情况下继续工作（具有分区耐受性），这样实际上只能在剩下的两个特性里二选一：可用性或是强一致性。  

* CA - 其实没有该选项，分布式系统理论上就不可能实现 CA，因为在分布式系统中，网络无法 100% 可靠，分区错误其实是一个必然现象，如果选择了 CA 而放弃了 P，那么当发生分区错误现象时，为了保证强一致性，这个时候必须拒绝请求，但是 A 又不允许，反过来如果总是接受请求那么也不可能保证强一致性，因此只能选择 CP 或者 AP 架构（非分布式系统因为不可能有分区错误所以可以是 CA 的，但本身这种场景也比较少见，通常是小型系统比如同一局域网内的单一服务端、数据库的：公司内部 OA 系统、部门级系统、工厂内部的生产控制系统等，但是需注意因为随着云原生的普及，纯 CA 系统越来越少）。
* CP - 放弃可用性，追求一致性和分区容错性，比如金融、支付、库存系统等等（涉及钱/资源分配，数据错误无法容忍，法律合规要求）以及一些软件如 zookeeper 就是追求的强一致。可以通过改进系统架构，设置数据分片来提升可扩展性。数据将保持强一致性，但如果有节点发生故障，仍然会有部分数据无法访问（不可用）。
* AP - 放弃强一致性（但可以保证弱一致性），追求分区容错性和可用性，这是很多分布式系统设计时的选择因为大部分产品和场景对强一致性的要求不高（体验优先），比如社交、内容、推荐系统等等，后面的 BASE 也是根据 AP 来扩展。系统可能返回不太精确的数据，但系统将始终可用，即使是网络发生分区错误的时候也是如此。另外 DNS 可能是这类系统中最为著名的例子了，这类系统可扩展性非常强，高可用，而且具有分区耐受性。

顺便一提，CAP 理论中是忽略网络延迟，也就是当事务提交时，从节点 A 复制到节点 B，但是在现实中这个是明显不可能的，所以总会有一定的时间是不一致。同时 CAP 中选择两个，比如选择了 CP，并不是放弃 A。因为 P 出现的概率实在是太小了，大部分的时间仍然需要保证 CA。就算分区出现了也要为后来的 A 做准备，比如通过一些日志的手段，是其他机器回复至可用。  

![](./The-CAP-Theorem-Many-of-the-NOSQL-databases-have-loosened-up-the-requirements-on.png)  
![](./CAP%20Databases.jpeg)  

对于设计分布式系统来说（通过[分布式事务](./分布式事务.md)能很好理解 CAP，但是不仅仅是分布式事务）的架构师来说，CAP 就是入门理论。  

[Ref](https://javaguide.cn/distributed-system/protocol/cap-and-base-theorem.html#%E7%AE%80%E4%BB%8B)  

### BASE  
BASE 是 Basically Available（基本可用）、Soft State（软状态）和 Eventually Consistent（最终一致性）三个短语的缩写。BASE 理论是对 CAP 中一致性 C 和可用性 A 权衡的结果，其来源于对大规模互联网系统分布式实践的总结，是基于 CAP 定理逐步演化而来的，它大大降低了我们对系统的要求。  

* 基本可用：分布式系统在出现故障时，允许损失部分可用功能，保证核心功能可用。基本可用是指分布式系统在出现不可预知故障的时候，允许损失部分可用性。但是，这绝不等价于系统不可用。什么叫允许损失部分可用性呢？
  * 响应时间上的损失：正常情况下，处理用户请求需要 0.5s 返回结果，但是由于系统出现故障，处理用户请求的时间变为 3s。
  * 系统功能上的损失：正常情况下，用户可以使用系统的全部功能，但是由于系统访问量突然剧增，系统的部分非核心功能无法使用。
* 软状态：软状态指允许系统中的数据存在中间状态（CAP 理论中的数据不一致），并认为该中间状态的存在不会影响系统的整体可用性，即允许系统在不同节点的数据副本之间进行数据同步的过程存在延时。
* 最终一致性：最终一致性强调的是系统中所有的数据副本，在经过一段时间的同步后，最终能够达到一个一致的状态。因此，最终一致性的本质是需要系统保证最终数据能够达到一致，而不需要实时保证系统数据的强一致性。分布式一致性的 3 种级别：
  * 强一致性：系统写入了什么，读出来的就是什么。
  * 弱一致性：不一定可以读取到最新写入的值，也不保证多少时间之后读取到的数据是最新的，只是会尽量保证某个时刻达到数据一致的状态。
  * 最终一致性：弱一致性的升级版，系统会保证在一定时间内达到数据一致的状态。
  
BASE 解决了 CAP 中理论没有网络延迟，在 BASE 中用软状态和最终一致，保证了延迟后的一致性。BASE 和 ACID 是相反的，它完全不同于 ACID 的强一致性模型，而是通过牺牲强一致性来获得可用性，并允许数据在一段时间内是不一致的，但最终达到一致状态。  
  
#### BASE 理论的核心思想
即使无法做到强一致性，但每个应用都可以根据自身业务特点，采用适当的方式来使系统达到最终一致性。  
也就是牺牲数据的一致性来满足系统的高可用性，系统中一部分数据不可用或者不一致时，仍需要保持系统整体主要可用。  
BASE 理论本质上是对 CAP 的延伸和补充，更具体地说，是对 CAP 中 AP 方案的一个补充。  

AP 方案只是在系统发生分区的时候放弃一致性，而不是永远放弃一致性。在分区故障恢复后，系统应该达到最终一致性。这一点其实就是 BASE 理论延伸的地方。  

参考：
* https://javaguide.cn/distributed-system/protocol/cap-and-base-theorem.html#base-%E7%90%86%E8%AE%BA
* https://juejin.cn/post/6844903647197806605

### FLP
* [FLP 不可能定理](https://zhuanlan.zhihu.com/p/384511433)
* [参考](https://alexstocks.github.io/html/alg.html)
* [Wiki](https://en.wikipedia.org/wiki/Consensus_(computer_science)#The_FLP_impossibility_result_for_asynchronous_deterministic_consensus)
