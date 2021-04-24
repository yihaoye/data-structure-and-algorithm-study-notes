## 系统设计学习资源
  
### 针对面试
* [Grokking-System-Design](https://github.com/lei-hsia/grokking-system-design)
* [Systems Design Youtube Playlist](https://www.youtube.com/watch?v=ZgdS0EUmn70&list=PL73KFetZlkJSZ9vTDSJ1swZhe6CIYkqTL)
* [系统设计题怎么考怎么答](https://www.youtube.com/watch?v=28n0DVP3U14)
* [系統设计面试 6 个技巧](https://www.youtube.com/watch?v=zomYKjlvJGU)  
  
> 系统设计这块我最开始的时候也是无从下手，许多概念了解一点大概但是都没有深入学习，俗称“略懂”的状态，深究就暴露了。  
> 具体如何准备这块，地里的很多前辈都总结了很多好的帖子，我觉得比较好的资料包括如下：Grokking the system design Interview, Designing Data-Intensive Applications（https://vonng.gitbooks.io/ddia-cn/content/ ）, 以及一些 YouTube 视频（e.g., https://www.youtube.com/c/SystemDesignInterview/ )。资料可能大家都有，我的诀窍在于下面几点：  
> 1）重复直至真的理解。发现“书读百遍，其义自现”是真的（古人诚不我欺）。比如 DDIA 至少过一遍，Grokking 至少过5遍，第一遍可能过完什么印象都没有，后面好像每一遍都会有新的收获，然后可以逐渐自己思考如果自己遇到这种问题如何解决，会发现所有给的 design decision 都开始 make sense。跟刷题一样，与其看一大堆然后没啥印象不如多过几遍经典题目然后学会融会贯通。  
> 2）要做笔记并总结。很多系统设计题大概框架都是相似的，比如设计 facebook, twitter, instgram, pinterest，核心都是 feed generation，可以用 push（fanout/heavy write，fast read）也可以 pull（light write，slower read）模式，而且一般最优解都是两者结合。再比如如何处理 hot object问题，一遍情况下我们可以加 cache 直接从内存读取，或者使用不同的 sharding strategy 来平分流量。  
> 3）要学会抓关键得分点。这一点其实是目标公司 HR 在面试之前透露的系统设计的 evaluation standard，发现特别受用，具体有以下这些：（1）problem exploration，先问清楚设计的目的，有哪些功能性和非功能性的要求，怎么评价成功；（2）quantitative analysis，无非就是估算 QPS，内存，存储以及带宽需求；（3）completeness of the design，这个肯定是必须的，直接决定过不过；（4）ability to reason trade-offs，特别需要讲为什么选这个技术而不是其他的；（5）deep dive，重点关注数据/存储，以及如何解决 scale/bottleneck 等方面。面试过程要主动考虑这些点，整个流程如果每个点都有的话，应该不会有太大的偏差。  
> 透露下我自己的 timeline，全身心投入的时间可能有两个月，其中两周左右是在全天候学习没有上班，剩下的是正常上班晚上学习，可能 coding 和系统设计各占一半，轮流复习（今天系统设计明天 coding），最终拿到了两个 offer。顺便提下我没有参加任何 mock interview，我觉得大概的流程了解就可以了，而且计划总是赶不上变化，花相同的时间把问题真正搞懂是更有效的时间分配原则。PS，我考了个完全没准备过的题型但还是交出了一个还行的答案。  
  
以上来源：https://www.1point3acres.com/bbs/thread-692488-1-1.html  
  
#### 4S 分析法
* Scenario：场景 - 需要设计哪些（核心）功能（询问面试官、也可以自己想），需要承受多大的访问量？
* Service：服务 - 逻辑处理的整合，对于同一类问题的逻辑处理可以归并到一个服务中。这一步实际上就是将整个系统细分为若干个小的服务。
* Storage：存储 - 最重要的部分。根据每个服务的数据特性选择合适的存储结构，然后细化数据表结构。系统设计中可以选择的存储结构一般有三大类（数据库系统，文件系统，缓存系统）。其中数据库系统又分为关系型数据库（[SQL Database](./../../HackerRank%20Practises/sql/README.md)）和非关系型数据库（[NoSQL Database](./NoSQL.md)）。
* Scale：扩展 - 主要分两部分，一个是优化，包括解决设计缺陷，更多功能设计以及一些特殊情况如何处理；另一个是维护，包括系统的鲁棒性和扩展性，比如有一台服务器/数据库挂了怎么办？如果有流量暴增，如何扩展？  
  
最后，系统设计面试中需要注意的点：  
* Ask before design. 问清楚再动手设计，不要一上来就冲着一个巨牛的方案去设计；
* No more no less. 不要总想着设计最牛的系统，要设计够用的系统；
* Work solution first. 先设计一个基本能工作的系统，然后再逐步优化；
* Analysis is important than solution. 系统设计没有标准答案，记住答案是没用的，通过分析过程展示知识储备，权衡各种设计方式的利弊。  
  
系统设计面试主要考察以下几个方面：  
* 可行解 Work Solution、特定问题 Special Case、分析能力 Analysis、权衡 Tradeoff、知识储备 Knowledge Base  
  
以上参考[来源](https://juejin.cn/post/6863387461947506702)  
  
### 系统学习
* [Grok System Design Tutorial](https://github.com/yihaoye/data-structure-and-algorithm-study-notes/blob/master/Leetcode%20Practices/system%20design/grok_system_design_interview.pdf)
* [System-Design-Primer](https://github.com/donnemartin/system-design-primer)  
  
### 系统主要大类（其他系统皆可从中找到类似）
![](./System%20Category.png)  
![](./System%20Category%202.png)  
  
### 系统通用基础设施
摘录：https://www.cnblogs.com/ilinuxer/p/6697015.html  
* API 网关（负载均衡、身份验证、路由、缓存、速率限制、计费、监控、分析、安全防护）
* 业务应用和后端基础框架（MVC、IOC、ORM）
* 缓存（本地缓存即内存中的缓存机制：ConcurrentHashMap etc；分布式缓存即单独的缓存服务：Redis、Memcached etc）
* 数据库（SQL、NoSQL）
* 搜索引擎（全文搜索、Elasticsearch）
* 消息队列（RabbitMQ、Kafka）
* 文件存储（S3、Hadoop HDFS）（e.g. distributed file storage system for storing photos and videos）
* 统一认证中心（用户的注册、登录验证、token 鉴权；内部信息系统用户的管理和登录鉴权；应用管理，应用的 secret 生成，应用信息的验证 - 如验证接口签名等）
* 单点登录系统（Central Authentication Service - CAS）
* 统一配置中心（Config Server、propeties、yaml）
* 服务治理框架（REST API、RPC）
* 统一调度中心（定时调度 cron job，如定时抓取数据、刷新状态等）
* 统一日志服务（log4j、logback、Kibana、CloudWatch）
* 数据基础设施（大数据：数据仓库；数据管道：Kafka、Kinesis；数据分析）
* 故障监控（系统监控、业务监控；Datadog、故障定位、警报等级、IM 或 oncall）(Telemetry)
* 扩展（内部服务：包括大数据、构建交付工具、通用运行时服务类库、数据持久化、安全等）  
  
  
## Grokking System Design
### Standard Design Steps within Interview
* Step 1: Requirements clarifications
* Step 2: System interface definition - Define what APIs/Methods are expected from the system
* Step 3: Back-of-the-envelope estimation - estimate the scale of the system for scaling, partitioning, load balancing and caching
* Step 4: Defining data model - entities of service, database choose and schema and design
* Step 5: High-level design - block diagram with 5-6 boxes representing the core components of the system
* Step 6: Detailed design - dig deeper into two or three components
* Step 7: Identifying and resolving bottlenecks
  
### System Design Basics
During designing a large system, investing in scaling before it is needed is generally not a smart business proposition; however, some forethought into the design can save valuable time and resources in the future.  
Core scalable/distributed system concepts include: `Consistent Hashing`, `CAP Theorem`, `Load Balancing`, `Caching`, `Data Partitioning`, `Indexes`, `Proxies`, `Queues`, `Replication`, and choosing between `SQL vs NoSQL`.  
#### Key Characteristics of Distributed Systems
Include: 
* Scalability - the capability of a system, process, or a network to continuously evolve/grow and manage increased demand.
  * Horizontal Scaling - Add more servers (e.g. Cassandra and MongoDB easy to scale horizontally).
  * Vertical Scaling - Add more resource/power (CPU, RAM, Storage etc) to same server (e.g. MySQL easy to scale vertically) (scaling involves downtime).
* Reliability - the probability a system will fail in a given period.
* Availability - the time a system remains operational to perform its required function in a specific period.
  * Reliability Vs Availability - If a system is reliable, it is available. However, if it is available, it is not necessarily reliable. high reliability contributes to high availability, but it is possible to achieve a high availability even with an unreliable product by minimizing repair time and ensuring that spares are always available when they are needed.
* Efficiency - Two standard measures of efficiency are response time (or latency) and the throughput (or bandwidth). The two measures correspond to the following unit costs:
  * Number of messages globally sent by the nodes of the system regardless of the message size.
  * Size of messages representing the volume of data exchanges.
* Serviceability or Manageability - how easy to operate and maintain. simplicity and speed with which a system can be repaired or maintained.
  * If the time to fix a failed system increases, then availability will decrease.
  * Ease of diagnosing and understanding problems when they occur, ease of making updates or modifications, and how simple the system is to operate.

#### Load Balancing
LB helps to spread the traffic across a cluster of servers to improve responsiveness and availability of applications, websites or databases. It also keeps track of the status of all the resources while distributing requests. (Between the user and the web server; Between web servers and an internal platform layer, like application servers or cache servers; Between internal platform layer and database)  
* Benefits of Load Balancing
* Load Balancing Algorithms (Health Checks)
  * Least Connection Method
  * Least Response Time Method
  * Least Bandwidth Method
  * Round Robin Method
  * Weighted Round Robin Method
  * IP Hash
* Redundant Load Balancers (LB can be a single point of failure; to overcome this, a second LB can be connected to the first to form a cluster)

#### Caching
Caches are used in almost every layer of computing: hardware, operating systems, web browsers, web applications, and more (can exist at all levels in architecture), but are often found at the level nearest to the front end. It is like short-term memory and fast.  
* Application server cache - If it is not in the cache, the requesting node will query the data from disk (faster than going to network storage), However, if LB randomly distributes requests across nodes, same request will go to different nodes, thus increasing cache misses. Two overcoming choices are global caches and distributed caches.
* Content Distribution Network (CDN) - CDNs are a kind of cache that comes into play for sites serving large amounts of static media. If content not locally available, CDN will query back-end servers for the file, cache it locally and serve it to user. Above can be done by Nginx.
* Cache Invalidation - cache require some maintenance for keeping cache coherent with the source of truth (e.g., database).
  * Write-through cache - data is written into the cache and the corresponding database at the same time.
  * Write-around cache - data is written directly to permanent storage, bypassing the cache.
  * Write-back cache - data is written to cache alone and completion is immediately confirmed to the client. The write to the permanent storage is done after specified intervals or under certain conditions.
* Cache eviction policies
  * First In First Out (FIFO)
  * Last In First Out (LIFO)
  * Least Recently Used (LRU)
  * Most Recently Used (MRU)
  * Least Frequently Used (LFU)
  * Random Replacement (RR)

### Practice Examples
<details>
<summary>Design TinyURL</summary>

[Example Implementation](./example%20questions/Design%20a%20URL%20Shortener%20(TinyURL)%20System.md)  
* Step 1: Why do we need URL shortening? - save a lot of space when displayed, printed, messaged, or tweeted, and hiding affiliated original URLs.
* Step 2: Requirements and Goals of the System (Functional Requirements, Non-Functional Requirements, Extended Requirements).
* Step 3: Capacity Estimation and Constraints (Traffic estimates -> Bandwidth estimates -> Storage estimates, Memory/Cache estimates -> Summary/High Level estimates).
* Step 4: System APIs - SOAP or REST APIs to expose the functionality of the service, definitions of the APIs with functionality, method name, params and return.
* Step 5: Database Design - Defining the DB schema in the early stages would help to understand the data flow among various components and later would guide towards data partitioning. Choose NoSQL since no relationships between objects within requirement and easier to scale.
* Step 6: Basic System Design and Algorithm - Encoding algorithm (e.g. MD5, SHA256, KGS etc) (concurrency problems?).
* Step 7: Data Partitioning and Replication - come up with a partitioning scheme that would divide and store data to different DB servers (Range Based Partitioning, Hash-Based Partitioning).
* Step 8: Cache (cache eviction policy - e.g. Least Recently Used (LRU) with LinkedHashMap).
* Step 9: Load Balancer (Between Clients and Application servers; Between Application Servers and database servers; Between Application Servers and Cache servers) (policy - e.g. Round Robin LB).
* Step 10: Purging or DB cleanup.
* Step 11: Telemetry.
* Step 12: Security and Permissions (user permission).
</details>

<details>
<summary>Design Twitter</summary>

[花花酱讲解](https://www.youtube.com/watch?v=PMCdWr6ejpw)
* Step 1: What is Twitter?
* Step 2: Requirements and Goals of the System (Functional Requirements, Non-Functional Requirements, Extended Requirements).
* Step 3: Capacity Estimation and Constraints (XXXAction per day, Storage Estimates, Bandwidth Estimates)
* Step 4: System APIs
* Step 5: High Level System Design (read-heavy system, load balancers, database & file storage)
* Step 6: Database Schema (table: tweet, user, user_follow, favorite)
* Step 7: Data Sharding (Sharding based on UserID, Sharding based on TweetID, Sharding based on Tweet creation time, combine sharding by TweedID and Tweet creation time)
* Step 8: Cache, e.g. many Memcache servers store targeted users' whole tweet objects posted within partial time
  * Which cache replacement policy - LRU (Least Recently Used)
  * More intelligent cache - 80-20 rule, cache 20% of daily read volume from each shard
  * Cache the latest data - cache all the tweets from the past three days, cache would be like a hash table where ‘key’ would be ‘OwnerID’ and ‘value’ would be a doubly linked list containing all the tweets from that user in the past three days, can always insert new tweets at the head of the linked list and remove tweets from the tail
* Step 9: Timeline Generation
* Step 10: Replication and Fault Tolerance (secondary db servers for read replica and replace primary server if it fail)
* Step 11: Load Balancing, policy e.g. Round Robin approach (improvement - periodically check server traffic load and adjust it), Between Clients and Application servers; Application servers and database replication servers; Aggregation servers and Cache server
* Step 12: Monitoring, e.g. metrics
* Step 13: Extended Requirements
</details>

<br />
  
  
## DDIA（Designing Data-Intensive Applications）
### 数据密集型应用组件
现今很多应用程序都是数据密集型（data-intensive）的，而非计算密集型（compute-intensive）的。因此 CPU 很少成为这类应用的瓶颈，更大的问题通常来自数据量、数据复杂性、以及数据的变更速度。  
数据密集型应用通常由标准组件构建而成，标准组件提供了很多通用的功能；例如，许多应用程序都需要：  
* 存储数据，以便自己或其他应用程序之后能再次找到（数据库 database）
* 记住开销昂贵操作的结果，加快读取速度（缓存 cache）
* 允许用户按关键字搜索数据，或以各种方式对数据进行过滤（搜索索引 search indexes）
* 向其他进程发送消息，进行异步处理（流处理 stream processing）
* 定期处理累积的大批量数据（批处理 batch processing）  
  
### 非功能性需求
* 可靠性（Reliability）意味着即使发生故障，系统也能正常工作。故障可能发生在硬件（通常是随机的和不相关的），软件（通常是系统性的Bug，很难处理），和人类（不可避免地时不时出错）。容错技术可以对终端用户隐藏某些类型的故障。
* 可扩展性（Scalability）意味着即使在负载增加的情况下也有保持性能的策略。为了讨论可扩展性，首先需要定量描述负载和性能的方法（SLO、SLA）。简要了解了推特主页时间线的例子，介绍描述负载的方法，并将响应时间百分位点（中位数 p50 以及 p95，p99 和 p999 等等）作为衡量性能的一种方式。在可扩展的系统中可以添加处理容量（processing capacity）以在高负载下保持可靠。（监控相关：Telemetry / 遥测技术，一般是指从物理网元或者虚拟网元上远程实时高速采集数据，实现对网络实时、高速和更精细的监控技术。相比于传统的网络监控技术如 CLI、SNMP 等拉模式，Telemetry 通过推模式，主动向采集器上推动数据信息，提供更实时更高速更精确的网络监控功能。[更多参考](https://www.sdnlab.com/23887.html)）
* 可维护性（Maintainability）有许多方面，但实质上是关于工程师和运维团队的生活质量的。良好的抽象可以帮助降低复杂度，并使系统易于修改和适应新的应用场景。良好的可操作性意味着对系统的健康状态具有良好的可见性，并拥有有效的管理手段。  
  
