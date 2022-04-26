# System Design Fundamentals

https://www.algoexpert.io/systems/fundamentals  
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
  * Streaming / Pushing - long lived connection, i.e. 无间断地监听 socket 是否有其他机器（如服务器主动发送数据）传输过来的数据 (可供两个机器互相通讯，socket 原理上是计算机里的一个可读写文件)，场景如 Polling 的缺点场景、IM 等
* Configuration
* Rate Limiting
* Logging And Monitoring
* Publish/Subscribe Pattern
* MapReduce
* Security And HTTPS
* API Design
