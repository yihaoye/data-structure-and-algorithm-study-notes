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

#### Sharding or Data Partitioning
Data partitioning (aka sharding) is a technique to break up a big database into many smaller parts. It improve the manageability, performance, availability, load balancing of an application and less scale cost.  
* Partitioning Methods
  * Horizontal partitioning - same feature data distribute diff servers (e.g. design twitter db sharding)
  * Vertical Partitioning - divide data to store tables related to a specific feature in their own server (but this single server approach is not practical when feature data grow large, it will finally need to be Horizontal partitioning).
  * Directory Based Partitioning - create a lookup service which knows your current partitioning scheme and abstracts it away from the DB access code. Directory server holds the mapping between each tuple key to its DB server, loosely coupled that allow change partitioning scheme without affect application.
* Partitioning Criteria
  * Key or Hash-based partitioning
  * List partitioning
  * Round-robin partitioning
  * Composite partitioning - combine any of the above partitioning schemes to devise a new scheme.
* Common Problems of Sharding
  * Joins and [Denormalization](https://blog.csdn.net/zbuger/article/details/51026791) [[wiki]](https://en.wikipedia.org/wiki/Denormalization)
  * Referential integrity - foreign keys
  * Rebalancing - change sharding scheme

#### Indexes
Databases operations performance, faster to search through the table and find the row or rows wanted.  
* Example: A library catalog
* How do Indexes decrease write performance? - index speed up data retrieval but slow down data insertion/update/delete (update the index). (unnecessary indexes should be avoided and indexes no longer used should be removed. Adding indexes is to improving read query performance, so if table is write heavy and rarely read, index may not be added)

#### Proxies
Software/Hardware acts as intermediary for requests from clients seeking resources from other servers. Typically used to filter, log, transform requests (by adding/removing headers, encrypting/decrypting, or compressing a resource). Another advantage of a proxy server is its cache can serve many similar requests.  
* Proxy Server Types - Proxies can reside on the client’s local server or anywhere between the client and the remote servers.
  * Open Proxy
    * Anonymous Proxy
    * Trаnspаrent Proxy - able to cаche the websіtes.
  * Reverse Proxy - retrieves resources from one or more servers then returned to client as originated from the proxy server itself

#### Redundancy and Replication
* Redundancy - backup or failover to avoid single points of failure
* Replication - sharing information to ensure consistency between redundant resources (e.g. db master-slave relationship)

#### SQL vs NoSQL
* SQL - structured and predefined schemas
* NoSQL
  * Key-Value Stores - The 'key' is an
attribute name which is linked to a 'value' (e.g. Redis and Dynamo)
  * Document Databases - different structured data is stored in documents in collections (instead of rows in a table) (e.g. MongoDB)
  * Wide-Column Databases - each row doesn’t have to have the same number of columns. e.g. Cassandra and HBase, Wide-Column DB best suited for analyzing large datasets
  * Graph Databases - data saved in graph structures with nodes (entities), properties (information about the entities), and lines (connections between the entities). e.g. Neo4J
* High level differences between SQL and NoSQL
  * Storage
    * SQL row represents an entity and each column represents a data point about that entity
  * Schema
    * SQL each record conforms to a fixed schema
    * NoSQL schemas are dynamic, 'columns' can be added on the fly
  * Querying
    * SQL apply structured query language for defining and manipulating the data
    * NoSQL apply UnQL (Unstructured Query Language), different databases have different syntax for using UnQL.
  * Scalability
    * SQL, in most common situations, are vertically scalable, horizontal scale is challenging and time-consuming
    * NoSQL, horizontally scalable, many NoSQL tech also distribute data across servers automatically.
  * Reliability or ACID Compliancy (Atomicity, Consistency, Isolation, Durability)
    * SQL mostly are ACID compliant.
    * NoSQL mostly sacrifice ACID compliance for performance and scalability.
* SQL vs NoSQL Which one to use? - there’s no one-size-fits-all solution
  * Reasons to use SQL database
    * need to ensure ACID compliance reduces anomalies and protects the integrity of db (for many e-commerce and financial applications, an ACID- compliant database remains the preferred option)
    * data is structured and unchanging
  * Reasons to use NoSQL database (Big data contributes to NoSQL databases' succeed)
    * Storing large volumes of data that often have little to no structure
    * Making the most of cloud computing and storage, requires data to be easily spread across multiple servers to scale up
    * Rapid development - quick iterations of system which require making frequent updates to the data structure without much downtime between versions

#### CAP Theorem
It is impossible for a distributed software system (especially data store) to simultaneously provide more than two of the following guarantees (CAP): Consistency, Availability, and Partition tolerance. When design a distributed system, trading off among CAP is almost the first thing to consider.  
* Consistency - All nodes see the same data at the same time. It is achieved by updating several nodes before allowing further reads.
* Availability - Every request gets a response on success/failure. It is achieved by replicating the data across different servers.
* Partition tolerance - System continues to work despite message loss or partial failure.

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
<summary>Design Pastebin</summary>

* Step 1: Pastebin enable users to store plain text or images over the network (Internet) and generate unique URLs to access the uploaded data. Such services are also used to share data over the network quickly, as users would just need to pass the URL to let other users see it.
* Step 2: Requirements and Goals of the System.
* Step 3: Some Design Considerations. Pastebin shares some requirements with TinyURL, but there are some additional considerations.
  * What should be the limit on the amount of text user can paste at a time.
  * Should we impose size limits on custom URLs.
* Step 4: Capacity Estimation and Constraints (Traffic estimates -> Storage estimates -> Bandwidth estimates -> Memory estimates).
* Step 5: System APIs - SOAP or REST APIs
  * addPaste(api_dev_key, paste_data, custom_url=None user_name=None, paste_name=None, expire_date=None)
  * getPaste(api_dev_key, api_paste_key)
  * deletePaste(api_dev_key, api_paste_key)
* Step 6: Database Design
  * Paste {URLHash: varchar(16), ContentKey: varchar(512), ExpirationDate: datatime, CreationDate: datetime}
  * User {UserID: int, Name: varchar(20), Email: varchar(32), CreationDate: datetime, LastLogin: datatime}
* Step 7: High Level Design
  * Client -> Application -> Object Storage (like Amazon S3: paste contents) and Metadata Storage (databse: metadata related to each paste, users, etc)
  * Above division of data will also allow us to scale them individually
* Step 8: Component Design
  * Application layer (根据粘贴内容创建一个随机 6 字符的 Key，将 Key 和粘贴内容一对一存在数据库，如果 Key 重复了就重创建直到没有重复；另一种办法是使用 KGS - Key Generation Service 并使用一个 Key 数据库作为 Key 池子 -- 一个已使用 Key 表一个未使用 Key 表，KGS 还可以用内存缓存未使用 Key -- 使用后则移除并存入已使用 Key 表，KGS 可能成为单点故障，所以需要为其准备一个 replica KGS，另外每个应用服务也可以缓存一些 Key 数据库的 Key)
    * ![](./Pastebin.png)
  * Datastore layer
    * Metadata database: 可以是 RDBMS 如 MySQL 或 Distributed Key-Value store 如 Dynamo 或 Cassandra
* Step 9: Purging or DB Cleanup (参考 TinyURL)
* Step 10: Data Partitioning and Replication (参考 TinyURL)
* Step 11: Cache and Load Balancer (参考 TinyURL)
* Step 12: Security and Permissions (参考 TinyURL)


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


<details>
<summary>Designing an API Rate Limiter</summary>

* Step 1: Rate Limiter 限制用户发送的请求数量。单个服务每秒可处理的请求是有限的，因此需要机制限制实体（用户、设备、IP 等）单个时间内的请求、事件执行数量。
  * 比如用户每秒可发 1 个消息、用户每天允许 3 次失败的信用卡交易、同一 IP 每天最多可创建 20 个账户
* Step 2: 为什么需要 API Rate Limiter？在应用层面上避免 DDoS 攻击、暴力破解密码的尝试、暴力大数量的信用卡交易等等，此类攻击通常难以预判、并会使服务、应用、API 宕机。另外该机制还可以用于防止收入损失、降低基础设施成本、阻止垃圾邮件和阻止在线骚扰，以下是细节好处：
  * 过滤行为不端的客户端/脚本或低优先级但量大的请求
  * 安全（暴力破解密码）
  * 防止滥用行为和不良设计实践
  * 控制成本和资源使用
  * 收入模型（某些服务不同套餐有不同的 API 限制）
  * 消除流量高峰
* Step 3: 系统的需求与目标。
  * 功能需求：限制实体在一个时间窗口内可以向 API 发送的请求数量，例如每秒 15 个请求。API 服务可能是集群，因此应考虑跨服务器的限制，即每当在单个服务器或服务器组合中超过定义的阈值时，用户都会收到错误消息。
  * 非功能需求：系统应该是高可用的，速率限制器应该始终有效，因为它可以保护服务免受外部攻击。速率限制器不应引入影响用户体验的大量延迟。
* Step 4: 如何 Rate Limiting？节流是在给定时间段内控制客户对 API 使用的过程，可以在应用层级 和/或 API 层级上定义限制，当超过节流限制时，服务器返回 HTTP 状态 “429 - 请求过多”。
* Step 5: 不同类型的节流
  * 硬节流: API 请求数绝不能超过节流数限制。
  * 软节流: 可以将 API 请求限制设置为超过一定百分比。例如，如果速率限制为每分钟 100 条消息以及允许超过 10% 的限制，速率限制器将允许每分钟最多 110 条消息。
  * 弹性或动态节流: 在弹性节流下，如果系统有一些可用资源，请求数可以超过阈值。例如，如果用户每分钟只允许发送 100 条消息，当系统中有可用资源时，可以让用户每分钟发送 100 多条消息。
* Step 6: 不同的 Rate Limiting 算法
  * Fixed Window Algorithm: 在下图中，第 0-1 秒之间有 2 条消息，第 1-2 秒之间有 3 条消息。如果有每秒 2 条消息的速率限制，则该算法将仅限制第 5 条消息（m5）。
  * Sliding Window Algorithm: 例如，如果在第 300 毫秒和第 400 毫秒发送了 2 条消息，会将它们从该秒的第 300 毫秒到下一秒的第 300 毫秒算作 2 条消息。在图中，每秒保留 2 条消息，将限制 “m3” 和 “m4”。
  * ![](./API%20Rate%20Limiting%20Algorithms.png)
* Step 7: High level design for Rate Limiter
  * 新的请求到达，Web 服务器首先询问 Rate Limiter 来决定它是被服务还是被节流。如果请求没有被限制，那么它将被传递到 API 服务器。
  * ![](./API%20Rate%20Limiter%20High%20Level%20Design.png)
* Step 8: 基本系统设计与算法
  * 举个例子，想限制每个用户的请求数量。在这种情况下，对于每个唯一用户，将保留一个计数，表示用户发出的请求数以及开始计算请求时的时间戳。可以将它保存在一个哈希表中，其中 “键” 将是 UserID，“值” 将是一个包含 Count 整数和 Epoch time 整数的结构体（UserID: {Count, StartTime}）。
    1. 如果哈希表中不存在 UserID，则插入它，将 Count 设置为 1，将 StartTime 设置为当前时间（标准化为一分钟），并允许请求。
    2. 否则，查找 UserID 的记录，如果 CurrentTime – StartTime >= 1 min，将 StartTime 设置为当前时间，Count 设置为 1，并允许请求。
    3. 如果 CurrentTime - StartTime <= 1 分钟并且：如果 Count < 限制数，增加 Count 并允许请求；如果 Count >= 限制数，拒绝请求。
  * Fixed Window 算法的问题
    * 因为在每分钟结束时重置 StartTime，这意味着它可能允许每分钟两倍的请求数。想象一下，如果 A 在一分钟的最后一秒发送了三个请求（假设每分钟限制数为 3），那么他可以在下一分钟的第一秒立即再发送三个请求，从而在两秒内产生 6 个请求。这个问题的解决方案是 Sliding Window 算法。
    * 原子性：分布式环境中，先读后写行为会产生竞争条件。比如，如果 A 当前的 Count 是 2，并且再发出两个请求，如果两个单独的进程处理这些请求中的每一个，并在其中任何一个更新它之前同时读取 Count，则每个进程都会认为 A 可以再有一个请求并且没有达到 Rate Limit。
      * 如果使用 Redis 来存储这些键值，解决原子性问题的一种解决方案是在读取更新操作期间使用 [Redis 锁](https://redis.io/topics/distlock)。然而，这将减慢来自同一用户的并发请求并引入另一层复杂性。可以使用 Memcached，但它会有类似的复杂性。
      * 如果使用一个简单的哈希表，可以有一个自定义实现来锁定每条记录来解决原子性问题。一个键值占 8+2+2=12 字节，假设哈希表每条记录有 20 字节的开销，如果需要随时跟踪一百万用户，需要的总内存将是 32MB，假设需要一个 4 字节的数字来锁定每个用户的记录来解决原子性问题，那么总共需要 36MB 内存。这可以容易地在单个服务器上实现，但实际并不希望通过一台机器路由所有的流量，此外如果假设每秒 10 个请求的 Rate Limit，这将转化为 Rate Limiter 服务的 1000 万 QPS！这对于单个服务器来说太多了。实际上，可以假设将在该分布式系统中使用 Redis 或 Memcached 这类的解决方案，将所有数据存储在远程 Redis 服务器中，所有速率限制器服务器将在服务或限制任何请求之前读取（和更新）这些服务器。
* Step 9: 滑动窗口（Sliding Window）算法
  * 如果可以跟踪每个用户的每个请求，就可以维护一个滑动窗口。可以将每个请求的时间戳存储在 Redis 的[排序 Set](https://redis.io/topics/data-types)，该 Set 在哈希表的 “值” 字段中，“键” 为 UserID。
  * 假设速率限制器允许每个用户每分钟三个请求，因此，每当有新请求进来时，速率限制器将执行以下步骤：
    1. 根据 UserID 在哈希表中获取其排序 Set。
    2. 从排序 Set 中删除所有早于 "CurrentTime - 1 分钟" 的时间戳。
    3. 统计排序 Set 的元素总数。如果此计数大于限制数 3，则拒绝请求。
    4. 在排序 Set 中插入当前时间并接受请求。
  * 需要多少内存来存储滑动窗口的所有用户数据？假设 UserID 占用 8 个字节，时间戳需要 4 个字节，假设需要每小时 500 个请求的速率限制，假设哈希表的开销为 20 字节，排序 Set 的开销为 20 字节。最后最多总共需要 12KB 来存储一个用户的数据：8 + (4 + 20 (sorted set overhead)) * 500 + 20 (hash-table overhead) = 12KB。
  * 这里为每个元素保留了 20 字节的开销。在有序集合中，可以假设至少需要两个指针来维护元素之间的顺序 —— 一个指向前一个元素的指针和一个指向下一个元素的指针。在 64 位机器上，每个指针将花费 8 个字节，所以需要 16 个字节的指针。添加了一个额外的字（4 个字节）来存储其他开销。如果需要随时跟踪一百万用户，需要的总内存将是 12GB：12KB * 1 million ~= 12GB。
  * 与固定窗口相比，滑动窗口算法需要大量内存；这会是一个关于可扩展性的问题。如果可以结合以上两种算法来优化内存使用呢？
* Step 10: 带计数器的滑动窗口（Sliding Window）
  * 如果使用多个 Fixed time windows 来跟踪每个用户的请求计数会怎样？例如，速率限制时间窗口大小的 1/60。如果有每小时的速率限制，可以计算每分钟的计数，然后合计过去一小时内所有计数器的总和来计算节流限制，这将减少内存占用。举一个例子，将速率限制为每小时 500 个请求，额外限制为每分钟 10 个请求。这意味着当过去一小时内带有时间戳的计数器的总和超过请求阈值 (500) 时，用户已超出速率限制。除此之外，每分钟不能发送超过 10 个请求。这将是一个合理且实际的考虑，因为没有一个真正的用户会频繁发送请求。即使他们这样做了，他们也会在重试中成功，因为他们的限制都会每分钟重置一次。
  * 可以将计数器存储在 [Redis Hash](https://redis.io/topics/data-types) 中，因为它为少于 100 个键提供了非常高效的存储。当每个请求增加散列中的计数器时，它还会将散列设置为在一小时后[到期](https://redis.io/commands/ttl)。将每个 “时间” 标准化为一分钟。
  * 需要多少内存来存储带有计数器的滑动窗口（sliding window）的所有用户数据？假设 UserID 占用 8 个字节，每个时间需要 4 个字节，而 Counter 需要 2 个字节。假设限制为每小时 500 个请求，假设哈希表的开销为 20 字节，Redis Hash 的开销为 20 字节。由于要对每分钟进行计数，因此最多需要为每个用户提供 60 个 entries。总共需要 1.6KB 来存储一个用户的数据：8 + (4 + 2 + 20 (Redis hash overhead)) * 60 + 20 (hash-table overhead) = 1.6KB
  * 如果需要随时跟踪一百万用户，需要的总内存将是 1.6GB：1.6KB * 100 万 ~= 1.6GB。因此，“带计数器的滑动窗口（sliding window）” 算法使用的内存比简单的滑动窗口算法少 86%。
* Step 11: 数据分片（Data Sharding）和缓存
  * 可以根据 UserID 进行分片来分发用户的数据。对于容错和复制，应该使用[一致性哈希](https://en.wikipedia.org/wiki/Consistent_hashing)。如果想对不同的 API 有不同的节流限制，可以选择按用户每个 API 进行分片。以 URL Shortener 为例；可以为每个用户或 IP 的 createURL() 和 deleteURL() API 设置不同的速率限制器。
  * 如果 API 是分区（partitioned）的，一个实际的考虑可能是为每个 API 分片也有一个单独的（稍微小一些）速率限制器。以 URL Shortener 为例，希望限制每个用户每小时创建的短 URL 不超过 100 个。假设在 createURL() API 使用`基于哈希的分区`，可以对每个分区进行速率限制，以允许用户除了每小时创建 100 个短 URL 之外，每分钟创建不超过三个短 URL。
  * 系统可以从缓存最近的活跃用户中获得巨大的好处。应用服务器可以在访问后端服务器之前快速检查缓存是否有所需的记录。通过仅更新缓存中的所有计数器和时间戳，速率限制器可以显着受益于回写缓存（Write-back cache）。可以以固定的时间间隔写入永久存储。通过这种方式，可以确保速率限制器添加到用户请求中的延迟最小。读取总是可以先命中缓存；一旦用户达到最大限制并且速率限制器将仅读取数据而不进行任何更新，这将非常有用。
  * Least Recently Used (LRU) 是系统的合理缓存驱逐策略。
* Step 12: 应该按 IP 还是按用户限速？
  * 不同方案的利弊：
    * IP：在这个方案中，限制每个 IP 的请求；尽管在区分 “好” 和 “坏” 请求者方面不是最佳选择，但总比没有速率限制要好。基于 IP 的节流的最大问题是当多个用户共享一个公共 IP 时，例如在使用同一网关的网吧或智能手机用户中。一个不良用户可能会导致其他用户受到限制。缓存基于 IP 的限制时可能会出现另一个问题，因为即使是一台计算机，黑客也可以使用大量 IPv6 地址，让服务器耗尽内存跟踪 IPv6 地址不是难事。
    * 用户：可以在用户认证后对 API 进行速率限制。一旦通过身份验证，用户将获得一个令牌，用户将在每个请求中传递该令牌。这将确保对具有有效身份验证令牌的特定 API 进行速率限制。但是如果必须限制的是登录本身的 API 则怎么办？这种速率限制的弱点是黑客可以通过输入错误的凭据来对用户进行拒绝服务攻击，如此，实际用户将无法登录。
  * 如果将上述两种方案结合起来怎么样？混合：正确的方法可能是对每个 IP 和每个用户进行速率限制，因为它们在单独实施时都有弱点，但是，这将导致更多的缓存 entries 和每个 entries 的更多详细信息，因此需要更多的内存和存储。
* PS: 漏桶法（Leaky Bucket）和令牌桶法（Token Bucket）、滑动日志
  * Leaky Bucket - 可以使用 FIFO 队列实现来实现简单的漏桶算法。它将请求转换为先进先出格式，以正常速率处理队列中的项目。
  * Token Bucket - 这是最简单的速率限制器算法，仅仅跟踪在设定的时间间隔内发出的请求数量。使用 Redis 的作为桶，设计一个 5QPM 速率限制，对于每个新的唯一用户请求，会在 userId 上创建一个哈希 key entry，其中包含请求时间戳和令牌计数。对于后续请求，将获取该特定 userId 的映射散列，并使用当前请求时间戳和剩余的可用令牌计数更新它，如果用户在第一次请求的同一分钟窗口内用完令牌，则来自该用户的所有其他请求都将被丢弃，当速率限制时间间隔过去时，令牌被重置为原始计数。警告：对于上面的 Redis 实现，由于先读后写行为，操作不是原子性的，因此在分布式环境中，可能会遇到竞争问题。![](./API%20Rate%20Limiter%20Token%20Bucket.png)（用户令牌计数和请求时间戳作为 Redis hash keys）
  * 关于单机版的限流器，比较著名的基本上都是基于令牌桶实现的，比如 Google 的 Guava，Go 语言的官方库中也有 rate。(https://guanhonly.github.io/2020/05/30/distributed-rate-limiter/, https://medium.com/swlh/rate-limiting-fdf15bfe84ab)

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
  
