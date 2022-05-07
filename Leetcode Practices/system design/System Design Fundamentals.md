# System Design Fundamentals

https://www.algoexpert.io/systems/fundamentals  
[相关代码链接](https://github.com/yihaoye/stem-notes/tree/master/e-software-architecture/Systems-Design)  
* Client—Server Model
* Network Protocols
  * IP, TCP, HTTP/S
* Storage
  * Disk
  * Memory
* Latency And Throughput
  * ![](./Latency%20And%20Throughput.png)
* Availability
  * SLA/SLO, SLA is agreement about `How many Nines` availability guarantee between system provider (e.g. Cloud Provider AWS) and customer (e.g. Cloud User), SLO is component of SLA.
  * How many Nines? Availability = 99.999% is High Availability / HA System. Which part of system require High Availability?
  * Redundancy, avoid single point of failure.
  * ![](./High%20Availability.png)
* Caching
  * Can be each level of the system (e.g. client, server, db etc)
  * Write through caching / write back caching
  * Be careful with stale data in cache
  * LRU
* Proxies
  * Reverse Proxy - Agent represent/hide Server，对所代理的服务器负责。（例子：网关、静态资源缓存、负载均衡、限流器、防火墙等等）
  * (Forward) Proxy - Agent represent/hide Client，对客户端负责。（例子：VPN 等等）
* Load Balancers
* Hashing
* Relational Databases
* Key-Value Stores
* Specialized Storage Paradigms
* Replication And Sharding
* Leader Election
  * [共识机制/算法（Consensus Algorithm）](https://draveness.me/consensus/)如 Paxos、Raft。
  * 难点在于多个计算节点同时达成共识/一致性（如拜占庭问题、非拜占庭问题），因为分布式系统有各种非常复杂的情况：如节点失效或网络通信收到干扰甚至阻断以及运行速度的差异
  * CAP 理论 - 不能同时保证强一致性、高可用性和分区容错性，每一个系统只能在这三种特性中选择两种
  * 第三方实现、自定义共识机制/算法的工具：Zookeeper、Etcd（KV 数据库，保证强一致性、高可用性）。[基于 Etcd 的 Leader Election 简易实现代码示例](./example%20questions/Leader%20Election.md)
* Peer-To-Peer Networks
  * 几种 P2P 核心算法及其优缺点（[带中心化 Tracker 服务器的 BitTorrent 协议](https://paaatrick.com/2019-07-07-network-protocol-p2p/)、[DHT 的 Kademlia 协议](https://zhuanlan.zhihu.com/p/40286711)、[Gossip 协议 - 又称流行病协议](https://zhuanlan.zhihu.com/p/41228196)）
  * ![](./Peer-To-Peer%20Networks.png)
* Polling And Streaming
  * Polling - 客户端定时请求服务端（缺点在于用户多且需要实时获取事件更新的场景下，服务端可能承担过多无效负载）
  * Streaming / Pushing - [Server-Sent Events (SSE)](https://stackoverflow.com/questions/5195452/websockets-vs-server-sent-events-eventsource)、websocket、long lived connection, i.e. 无间断地监听 socket 是否有其他机器（如服务器主动发送数据）传输过来的数据 (可供两个机器互相通讯，socket 原理上是计算机里的一个可读写文件)，场景如 Polling 的缺点场景、IM 等
* Configuration
  * 配置即系统或应用需用到的存放在某个独立文件中（而不是代码程序逻辑里）的参数（parameters）或常量（constants）的集合
  * 格式通常是 JSON 或 YAML
  * 根据实际情况权衡使用静态配置或动态配置
    * 静态配置（与项目本地源代码放一起，通常只适合不敏感、低风险的数据，比如版本号、前端依赖）
    * 动态配置（完全与源代码分离的，比如是另外一个服务，有时可以通过 UI 界面供工程师动态地更新配置，更安全，比如存放重要的密钥，缺点是不容易测试或及时检测到变更需更复杂的设计）
* Rate Limiting
  * Attacks Protect
    * DoS - 海量请求导致 Server cannot handle real request any more。
    * DDoS - 和 DoS 不同的是第一个 D 是分布式（Distributed）的意思 - 即无法鉴别为某个组织或用户的 DoS 攻击
  * Function/Feature
    * VIP - identify by user, some may have higher request threshold
  * IP, Region etc
  * 可以通过 Redis 来实现分布式系统的 Rate Limiting（某个微服务有多个实例，单个实例通过自己的内存实现 Rate Limiting 无法保证，因为下一相同来源请求可能被负载均衡分配到另一个实例，而该实例的内存此时未记录该请求的 header、ID 和相关次数、时间，所以不会 Rate Limiting）
  * Rate Limiting 可以根据需要设计得非常复杂，比如 指定时间里可以接指定数量请求、With Tiers 等等
* Logging And Monitoring
  * Logging
    * 记录系统的真实经历事件（系统操作或用户操作），比如需要 debug 一些生产上的不好重现的事故
    * 日志系统，会收集所有日志信息（比如 fmt.print() 或 log.info()、log.error() 这类编程语言打印的日志或报错）到一个数据库，可供工程师稍后进行回顾
    * 日志的两种主要格式
      * Syslog
      * JSON
    * 辅助日志平台如 Google Stackdriver（现更名为 Google Cloud Operations）、AWS CloudWatch 等等
  * Monitoring
    * 可视化地（比如 Metric）监控系统（整体或某个服务）健康、性能、状态
    * 按不同的时间范围展示可视化
    * 设置阀值、频率报警以及通知系统（比如 Slack）
    * 使用数据库（比如 Time-series 数据库）
    * 可视化工具如 Grafana
    * 保留时间
* Publish/Subscribe Pattern
* MapReduce
  * 大规模数据集的运算处理场景 - 此时需要 horizontally scale system，即引入大量机器、服务器并行进行运算处理分割的子任务/子数据然后归并结果以降低总处理时间，这非常困难，因为分布式（文件）系统的服务器有的可能会遭遇机器宕机或网络卡顿等等一系列问题，所以设计系统时还要考虑该分布式系统的容错性。[MapReduce](./mapreduce-osdi04.pdf) 就是为了解决这个问题的。MapReduce 是一种编程模型，是一种编程方法，抽象理论。
  * 可以使用 MapReduce 处理的大数据需满足 2 个条件：数据处理任务可以通过 Map 步骤和 Reduce 步骤进行分割和归并（归约、重组）。MapReduce 的主要思想，都是从函数式编程语言借鉴的，还有从矢量编程语言借来的特性。其实现是指定一个 Map（映射）函数，用来把一组键值对映射成一组新的键值对，指定并发的 Reduce（归约）函数，用来保证所有映射的键值对中的每一个共享相同的键组。 
  * 有一个中央控制器通讯、监控整个系统里所有的 Map 和 Reduce 及其他工作机器，知道哪部分分割子数据/子任务在哪个机器，以及输出的终点。
  * 因为数据集太大，所以一般不迁移数据，而是把每块分割的子数据留在相应的机器中，并把 Map 程序迁移到那些机器进行本地运算并生成 MapReduce 模型的中间键值对。MapReduce 模型的中间键值对非常重要，因为归并过程仰赖于它们。MapReduce 的思想就是 “分而治之”：
    * Mapper 负责“分”，即把复杂的任务分解为若干个“简单的任务”来处理。“简单的任务”包含三层含义：一是数据或计算的规模相对原任务要大大缩小；二是就近计算原则，即任务会分配到存放着所需数据的节点上进行计算；三是这些小任务可以并行计算，彼此间几乎没有依赖关系。
    * Reducer 负责对 Map 阶段的结果进行汇总。至于需要多少个 Reducer，用户可以根据具体问题，比如 Hadoop 通过在 mapred-site.xml 配置文件里设置参数 mapred.reduce.tasks 的值，缺省值为 1。
    * 一个比较形象的语言解释 MapReduce：两个人要数图书馆中的所有书。一个数 1 号书架，一个数 2 号书架。这就是 Map。如果人越多，数书就更快。完成后人们回到一起，把所有人的统计数加在一起。这就是 Reduce。
  * 可靠且容错 - 当一个 Map 或 Reduce 操作失败时，只需要重新将其执行一次（中央控制器会去监控执行），但是要注意一个 Map 或 Reduce 操作应保证幂等。
  * 整个 MapReduce 实际使用中最重要最需要关心的是要执行的 Map、Reduce 程序以及它们的输入输出，因为其他具体的分布式系统的问题由 MapReduce 框架（比如 Hadoop）或库去解决。
  * 以下是应用 MapReduce 统计各字母出现次数的简单示例： ![](./MapReduce%20Example.png)
  * [Node.js 代码示例 MapReduce 编程模型](https://github.com/yihaoye/stem-notes/tree/master/e-software-architecture/Systems-Design/map_reduce)，其中的 `run.sh` 脚本扮演着`中央控制器`的角色。
* Security And HTTPS
* API Design
  * 与系统设计并肩而非其子集（通常企业面试系统设计或 API 设计）
  * 与系统设计共享许多共性
  * 网站系统、产品功能都是通过 API 构建的，有的公司提供的 API 本身就是产品（比如 Stripe），所以 API 很重要
  * API 任意细节的设计都要谨慎（比如参数的顺序），因为依赖该 API 的用户增长后，更改和删除 API 将变得困难、麻烦，所以一些大企业（如 Google）对 API 设计会做许多 review、检查
  * 背景知识：HTTP、JSON、YAML、ACL（Access-Control List）
  * 关键知识：Pagination、CRUD Operations
  * 考核过程
    * 询问关于系统的哪个功能、部分，询问系统规模，询问 API 消费细节
    * 列出每个 API 概要 / outline（列出 API 所依赖的 entity、资源，比如 Twitter API 依赖 tweet 资源或 entity，又比如 Stripe 依赖支付、客户等资源或 entity。entity 的细节要包括需要哪些属性、字段及其数据类型），列出 API 的 endpoint、请求参数（必要或可选）、返回响应，但是不需要实现逻辑细节（或者只需要写出一些关键逻辑）
    * 或者写下 API in swagger format（前提是询问面试官可接受的格式与写法），[代码示例](https://github.com/yihaoye/stem-notes/tree/master/e-software-architecture/Systems-Design/api_design/example)
    * 讲出 API 设计方案、决定的原因（没有对错，重在沟通）
    * API 版本、路径等设计的最佳实践
  * 练习方式
    * 看大公司的 API 文件，学习如何设计（比如 [Stripe](https://stripe.com/docs/api)、[Google](https://cloud.google.com/apis)、[Twitter](https://developer.twitter.com/en/docs/twitter-api)）
    * 尝试写常见公司（Google、Stripe、Twitter、Uber、Youtube 等等）的 API，然后对照实际的 API 询问自己的设计是否更好或决策想法
  
  
## 补充
[其他人的笔记参考](https://github.com/Gaitz/Note/blob/master/raw-note/items/SystemDesign_1.md)  
