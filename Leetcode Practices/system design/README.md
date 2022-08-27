# 系统设计学习资源
  
## 针对面试
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
  
### 4S 分析法
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
  
## 系统学习
* [Grok System Design Tutorial](https://github.com/yihaoye/data-structure-and-algorithm-study-notes/blob/master/Leetcode%20Practices/system%20design/grok_system_design_interview.pdf)
* [System Design Fundamentals](./System%20Design%20Fundamentals.md)
* [System-Design-Primer](https://github.com/donnemartin/system-design-primer)  
  
## 系统主要大类（其他系统皆可从中找到类似）
<details>
<summary>System Category</summary>

参考：https://interview-science.org/%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1/%E5%BA%94%E7%94%A8%E5%88%86%E7%B1%BB  
|类型	|例子	|业务特点	|核心需求	|解决方案
|---  |---  |---  |---  |---
|News Feed	|Twitter<br/>Instagram<br/>TikTok<br/>Pinterest<br/>Flickr<br/>RSS 类系统<br/>其他涉及订阅，关注的多对多信息流系统  |读远多于写<br/>差异化的多对多关系  |读：响应时间<br/>写：高效上传  |根据用户情况使用混合策略（Fanout，Native）<br/>异步上传，分块上传
|IM	|WhatsApp<br/>Facebook Messenger<br/>WeChat<br/>LINE<br/>Telegram |读写比例均衡<br/>小范围（固定）的一对一/多对多关系 |低延迟<br/>消息有序性<br/>持久化 |特定的应用协议：MQTT, AMQP, XMPP<br/>消息序号<br/>日志追加型数据库
|Live	|Facebook Live<br/>Twitch<br/>Zoom<br/>Google Meets<br/>Microsoft Teams |读写比例均衡<br/>无法预缓存，无法预料流量<br/>流式数据<br/>惊群效应  |低延迟<br/>自动扩容与收缩<br/>支撑热门 Live  |特定的应用协议：RTMPS, WebRTC<br/>基础组件自动伸缩<br/>热门 Live 使用多级缓存
|Video	|YouTube<br/>Netflix<br/>Disney Plus<br/>HBO Max<br/>Hulu |读远多于写<br/>流式数据  |读：响应时间<br/>自适应网络速度<br/>支撑热门视频<br/>高可用  |边缘节点与 CDN 内容分发<br/>应用协议网络自适应<br/>热门视频使用多级缓存，缓存预热，缓存淘汰策略<br/>Replication 与 Failover
|GEO Base	|Uber<br/>Uber Eats<br/>Grab<br/>Lyft<br/>Waymo |多边关系（用户，服务者，平台）<br/>区域搜索<br/>双方匹配 |合理一对一匹配<br/>维护在线状态  |Geohash 算法，S2 算法，后台定期更新排名<br/>状态维护：heartbeat, Websocket
|Shopping	|Amazon<br/>eBay<br/>Etsy<br/>AliExpress<br/>Snapdeal |下单的事务流程复杂<br/>秒杀与拍卖场景<br/>库存管理 |高一致性，避免重复下单，避免超卖少卖<br/>秒杀下的高可用  |幂等与事务，消息队列，乐观锁与悲观锁<br/>服务降级与限流
|File Sync	|Google Drive<br/>Microsoft One Drive<br/>Dropbox<br/>Sync.Com<br/>iDrive |多端/多用户文件同步<br/>频繁更新<br/>文件层级与数量多<br/>循环依赖 |高可用与一致性<br/>高效读写  |Rsync 算法<br/>本地文件与云端对比，只获取所需更新。异步上传，分片上传，增量更新
|Search	|Google Search<br/>Bing<br/>Airbnb Search<br/>Twitter Search  |无法预料搜索类型<br/>搜索关键字分散<br/>内容多 |读：响应时间<br/>匹配与排名  |缓存热门搜索<br/>Trie Tree 与 Reverse Index<br/>语义分析，PageRank, Panda algorithm<br/>图数据库
|Share	|TinyURL<br/>Bitly<br/>Shorby<br/>Pastebin  |一对一关系<br/>权限管理<br/>过期时间 |读：响应时间 |预生成 URL<br/>缓存策略
|Ranking	|Twitter Top Hashtags<br/>Spotify Top K Music |实时排序耗时<br/>数据动态变化  |读：响应时间 |每隔一段时间预计算<br/>近似统计算法
|Trading	|Robinhood<br/>Webull<br/>TradeStation  |下单的事务流程复杂<br/>高频率  |读：响应时间<br/>写：写入时间<br/>高一致性 |消息队列，乐观锁与悲观锁<br/>幂等与事务
|CPU-bound	|LeetCode<br/>Video Editor  |依赖 CPU 资源  |任务处理时间 |分治策略<br/>MapReduce<br/>异步处理通知
|Infrastructure	|Message queue<br/>Object Storage<br/>Logging<br/>Monitoring  |使用方便<br/>功能适用度高  |高可用性<br/>高扩展性  |冗余备份以及快速恢复

![](./System%20Category.png)  
![](./System%20Category%202.png)  

</details>
<br />
  
## 系统设计模式
系统设计也有设计模式（类似 OOD 的设计模式），就像算法需要掌握数据结构，系统设计也需要掌握系统设计模式，因为这些都是最佳实践、通常比自己创造想象的要来得稳健。  
<details>
<summary>主要的系统设计模式：https://docs.microsoft.com/zh-cn/azure/architecture/patterns/</summary>

* [代表/大使模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/ambassador) - 创建代表客户服务或应用程序发送网络请求的帮助程序服务。
* [防损层/反腐层模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/anti-corruption-layer) - 在现代应用程序与旧系统之间实施外观或适配器层。
  * 在不共享相同语义的不同子系统之间实施外观或适配器层。此层转换一个子系统向另一个子系统发出的请求。
  * 大多数应用程序依赖于其他系统的某些数据或功能。例如，旧版应用程序迁移到新式系统时，可能仍需要现有的旧的资源。新功能必须能够调用旧系统。逐步迁移尤其如此，随着时间推移，较大型应用程序的不同功能迁移到新式系统中。
* [异步 Request-Reply 模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/async-request-reply) - 在后端处理需要是异步处理但前端仍需要明确响应的情况下，将后端处理与前端主机分离。
  * 解决方法类似使用 HTTP 轮询。
* [用于前端的后端模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/backends-for-frontends) - 创建单独的后端服务，供特定的前端应用程序或接口使用。
* [隔层模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/bulkhead) - 将应用程序的元素隔离到池中，这样，如果一个元素发生故障，其他元素可继续工作。
  * 一个使用者可以使用每个请求的资源同时向多个服务发送请求。当使用者向配置不当或无响应的服务发送请求时，可能无法及时释放客户端请求所用的资源。随着不断地向服务发送请求，这些资源可能会耗尽。例如，客户端的连接池可能会耗尽。此时，使用者向其他服务发出的请求会受到影响。最终，使用者不再能够向其他服务（而不仅仅是原始的无响应服务）发送请求。
* [缓存端模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/cache-aside) - 将数据按需从数据存储加载到缓存（如 Redis）中。
* [协调/编舞模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/choreography) - 让每项服务都参与决定业务运营的处理时间和处理方式，而不是依赖于一个中心型业务流程协调程序。
  * 实现协调/编舞的一种解决方案是使用异步消息传送模式（消息队列）协调业务运营。客户端请求将消息发布到消息队列，消息到达时，它们会推送到对该消息感兴趣的订阅者或服务，每个订阅的服务按消息指示执行其操作，并响应操作成功或失败的消息队列，如果成功，服务可以将消息推送回同一队列或其他消息队列，以便另一个服务根据需要继续工作流，如果操作失败，消息总线可以重试该操作。由于没有点到点通信，因此此模式有助于减少服务之间的耦合。
  * 如果希望经常更新、删除或添加新服务，请使用编舞模式。可以修改整个应用，工作量较小，对现有服务造成最少的中断。如果在中央业务流程协调程序中遇到性能瓶颈，请考虑此模式。此模式是无服务器体系结构的自然模型，其中所有服务都可以短生存期或事件驱动。服务可能会因为事件而启动，执行其任务，并在任务完成后被删除。
* [断路器模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/circuit-breaker) - 连接到远程服务或资源时处理故障，此类故障所需修复时间不定。这可以提高应用程序的稳定性和复原能力。但在处理对应用程序中的本地私有资源的访问，例如内存中数据结构，不推荐使用断路器模式，因为在此环境中使用断路器会增加系统开销。
  * 断路器模式的目的与重试模式不同。重试模式在预期操作将成功的情况下让应用程序重试操作。断路器模式则防止应用程序执行很可能失败的操作。应用程序可以使用重试模式通过断路器调用操作，来组合这两种模式。但重试逻辑应该对断路器返回的任何异常保持敏感，并且在断路器指示故障为非临时性的情况下放弃重试尝试。
  * 针对可能失败的操作，断路器充当其代理。代理应监视最近发生的失败次数，并使用此信息来决定是允许操作继续进行，还是立即返回异常。通过模仿电力断路器的功能，可将代理作为具有多个状态的状态机来实现（状态包括`关闭`、`打开`、`半开`。在打开状态中，断路器可定期执行对远程服务或资源的 ping 操作来决定其是否变得再次可用，或使用计时器来判断何时应切换至半开状态，此种 ping 操作可以尝试调用之前失败的操作，或使用由远程服务提供的专门用于测试服务运行状况的特殊操作，如运行状况终结点监视模式中所述）。
  * 如果服务限制客户端，则返回 HTTP 429（请求过多）；如果服务当前不可用，则返回 HTTP 503（服务不可用）。 响应可包括附加信息，如延迟的预期持续时间。
* [声明检查模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/claim-check) - 将大型消息拆分成声明检查和有效负载，将声明检查发送到消息传送平台，并将有效负载存储到外部服务（Blob 存储）。此模式允许处理大型消息，同时保护消息总线和客户端不被压倒或减慢。此模式还有助于降低成本，因为存储通常比消息平台使用的资源单位便宜。此模式也称为 Reference-Based 消息传送。
  * 某些时候，基于消息的体系结构必须能够发送、接收和操作大型消息。此类消息可能包含任何内容，包括图像 (例如，MRI 扫描) 、声音文件 (例如呼叫中心呼叫) 、文本文档或任意大小的任何类型的二进制数据。不建议直接将此类大型消息发送到消息总线，因为它们需要消耗更多的资源和带宽。
  * 解决方案：将整个消息有效负载存储在外部服务（例如数据库）中。获取对存储有效负载的引用，并仅发送对消息总线的引用 引用的行为类似于用于检索一件行李的声明检查，因此模式的名称。对处理特定消息感兴趣的客户端可以使用获取的引用来检索有效负载（如果需要）。一些云平台可以使用 Blob 存储 和事件网格自动声明检查生成（发送方将消息有效负载拖放到指定的云 Blob 存储容器中，云事件网格自动生成标记/引用，并将其发送到受支持的消息总线，例如云存储队列，接收方可以轮询队列、获取消息，然后使用存储的引用数据直接从 Blob 存储下载有效负载）。
  * 如果只有有权查看有效负载的服务才能访问有效负载，也可以使用该模式。通过将有效负载卸载到外部资源，可以制定更严格的身份验证和授权规则，以确保在有效负载中存储敏感数据时强制实施安全性。
* [事件溯源模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/event-sourcing) - 使用只追加存储来记录对数据采取的完整系列操作，而不是仅存储域中数据的当前状态。该存储可作为记录系统，可用于具体化域对象。
  * 大多数应用程序会使用数据，而典型的方法（比如 CRUD 模型）是用户使用数据时通过更新数据使应用程序保持数据的当前状态。但是这往往有一些限制：
    * CRUD 系统直接对数据存储执行更新操作，其所需的处理工作开销会降低性能和响应能力，并会限制可扩展性。
    * 在包含多个并发用户的协作域中，由于会对数据单个项进行更新操作，因此出现数据更新冲突的可能性更大。
    * 除非存在记录单独日志中每个操作详细信息的其他审核机制，否则历史记录会丢失。
  * 有点像日志系统，常用于需要记录每一次数据的改变，数据溯源的系统。需要关注每次操作的完整数据更新流程，例如 Shopping、File Sync（除了记录当前文件信息，还需要记录每次同步的信息）、Trading（除了记录当前持仓信息，还需要记录每次交易的信息）等系统。其他典型用途还包括在应用程序中的操作更改实体时保持实体的具体化视图（通过执行响应事件的数据管理任务和具体化存储事件的视图，事件溯源通常与 CQRS 模式结合）以及用于与外部系统集成。
  * 问题和注意事项：事件存储是信息的永久源，因此请勿更新事件数据，更新实体以撤销更改的唯一方式是将补偿事件添加到事件存储，比如需要使用补偿事件来取消更改。只有通过重播事件创建具体化视图或生成数据投影时，系统才可实现最终一致性，应用程序将事件添加到事件存储作为处理请求的结果、发布事件和事件使用者处理事件之间存在一定程度的延迟，在此期间，描述实体的进一步更改的新事件可能已到达事件存储。事件溯源不需要直接更新数据存储中的对象，因而有助于防止并发更新造成冲突，但是，域模型必须仍然设计为避免可能导致不一致状态的请求：
    * 多线程应用程序和应用程序的多个实例可能将事件存储在事件存储中。事件存储中的事件一致性至关重要，影响特定实体的事件的顺序（实体更改发生的顺序会影响当前状态）同样至关重要。将时间戳添加到每个事件有助于避免出现问题。另一常见做法是使用增量标识符注释请求引起的每个事件。如果两个操作尝试同时为同一实体添加事件，则事件存储可拒绝与现有实体标识符和事件标识符相匹配的事件。
  * 读取事件以获取信息并没有标准方法或现有机制，例如 SQL 查询。可提取的唯一数据是将事件标识符用作条件的事件流。事件 ID 通常会映射到各个实体。仅可根据实体原始状态通过重播与其关联的所有事件来确定实体的当前状态。
  * 事件发布可能至少为一次，因此事件使用者必须是幂等的。如果事件处理次数大于 1，则使用者不得重新应用该事件中描述的更新。
* [网关聚合模式](https://docs.microsoft.com/zh-cn/azure/architecture/patterns/gateway-aggregation) - 使用网关可将多个单独请求聚合成一个请求。当客户端必须向不同的后端系统发出多个调用来执行某项操作时，此模式非常有用。实际应用例子：网关聚合 NGINX 服务等等。
  * 当客户端可能需要向不同的后端服务发出多个调用时，客户端与后端之间的这种频繁通信可能会对应用程序的性能和规模产生不利影响（此问题在微服务体系结构中更常见，因为围绕许多小型服务构建的应用程序原生就包含更多的跨服务调用）。尽管可以并行执行每个请求，但应用程序必须发送、等待并处理每个请求的数据，而所有这些操作都要通过单独的连接完成，因此增大了故障可能性。
  * 解决方案 - 使用网关减少客户端与服务之间的通信频率。网关（客户端与后端/服务端之间）会接收客户端请求，将请求分派到不同的后端系统，然后聚合结果并将其返回给请求客户端。此模式可以减少应用程序向后端服务发出的请求数，并通过高延迟网络改进应用程序的性能。过程示例：应用程序向网关发送一个请求（该请求包含其他一些请求）-> 网关分解其他这些请求，并通过将每个请求发送到相关的服务来处理每个请求 -> 每个服务向网关返回响应 -> 网关组合来自每个服务的响应，并向应用程序发送响应 -- 整个过程应用程序发出单个请求，并仅接收来自网关的单个响应。
* To Be Continue ...

</details>
<br />
  
## 系统通用基础设施
摘录：https://www.cnblogs.com/ilinuxer/p/6697015.html  
* API 网关（负载均衡、身份验证、路由、缓存、速率限制、计费、监控、分析、安全防护）
* 业务应用和后端基础框架（MVC、IOC、ORM）
* 缓存（本地缓存即内存中的缓存机制：ConcurrentHashMap etc；分布式缓存即单独的缓存服务：Redis、Memcached etc）
* 数据库（SQL、NoSQL）
* 搜索引擎（全文搜索、ElasticSearch）
* 消息队列（RabbitMQ、Kafka）[阅读材料](http://www.52im.net/thread-1979-1-1.html)
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
* 分布式一致性与选举算法/共识机制
* 点对点网络（Peer-to-peer Networks，如 VPC Peering、指数增长的高效的针对大规模目标对象的数据拷贝传输、区块链项目如 Bitcoin、等等）  
  
  
# Grokking System Design
## Standard Design Steps within Interview
* Step 1: Requirements clarifications
* Step 2: System interface definition - Define what APIs/Methods are expected from the system
* Step 3: Back-of-the-envelope estimation - estimate the scale of the system for scaling, partitioning, load balancing and caching
* Step 4: Defining data model - entities of service, database choose and schema and design
* Step 5: High-level design - block diagram with 5-6 boxes representing the core components of the system
* Step 6: Detailed design - dig deeper into two or three components
* Step 7: Identifying and resolving bottlenecks
  
<details>
<summary>System Design Basics</summary>
  
During designing a large system, investing in scaling before it is needed is generally not a smart business proposition; however, some forethought into the design can save valuable time and resources in the future.  
Core scalable/distributed system concepts include: `Consistent Hashing`, `CAP Theorem`, `Load Balancing`, `Caching`, `Data Partitioning`, `Indexes`, `Proxies`, `Queues`, `Replication`, and choosing between `SQL vs NoSQL`.  
### Key Characteristics of Distributed Systems
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
  
### Consistent Hashing
[一致性哈希详解](./一致性哈希.md)  
  
### Load Balancing
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

### Caching
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

### Sharding or Data Partitioning
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

### Indexes
Databases operations performance, faster to search through the table and find the row or rows wanted.  
* Example: A library catalog
* How do Indexes decrease write performance? - index speed up data retrieval but slow down data insertion/update/delete (update the index). (unnecessary indexes should be avoided and indexes no longer used should be removed. Adding indexes is to improving read query performance, so if table is write heavy and rarely read, index may not be added)

### Proxies
Software/Hardware acts as intermediary for requests from clients seeking resources from other servers. Typically used to filter, log, transform requests (by adding/removing headers, encrypting/decrypting, or compressing a resource). Another advantage of a proxy server is its cache can serve many similar requests.  
* Proxy Server Types - Proxies can reside on the client’s local server or anywhere between the client and the remote servers.
  * Open Proxy
    * Anonymous Proxy
    * Trаnspаrent Proxy - able to cаche the websіtes.
  * Reverse Proxy - retrieves resources from one or more servers then returned to client as originated from the proxy server itself

### Redundancy and Replication
* Redundancy - backup or failover to avoid single points of failure
* Replication - sharing information to ensure consistency between redundant resources (e.g. db master-slave relationship)

### SQL vs NoSQL
* SQL - structured and predefined schemas
* NoSQL
  * 子分类
    * Key-Value Stores - The 'key' is an
  attribute name which is linked to a 'value' (e.g. Redis and Dynamo)
    * Document Databases - different structured data is stored in documents in collections (instead of rows in a table) (e.g. MongoDB)
    * Wide-Column Databases - each row doesn’t have to have the same number of columns. e.g. Cassandra and HBase, Wide-Column DB best suited for analyzing large datasets
    * Graph Databases - data saved in graph structures with nodes (entities), properties (information about the entities), and lines (connections between the entities). e.g. Neo4J
  * 缓存数据库、KV 或 Column 数据库、文档数据库的区别：
    * 缓存数据库用于 read heavy 且读性能高且不需要持久化存储
    * KV 或 Column 数据库用于 read heavy 且读性能高且需要持久化存储需要简单 schema（与文档数据库的主要区别是实际上该类数据库更新 Value/Columns 时把 Value 的数据 -- 字符串读出来转换成对象然后再更新后再写回去）
    * 文档数据库用于需要持久化存储且灵活 schema 且支持 ACID 操作且支持相当复杂的 Query、Operation、比 Column 数据库更好的二级索引支持等等
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
  * ![](./How%20to%20Select%20SQL%20DB.jpeg)
  * Reasons to use NoSQL database (Big data contributes to NoSQL databases' succeed)
    * Storing large volumes of data that often have little to no structure
    * Making the most of cloud computing and storage, requires data to be easily spread across multiple servers to scale up
    * Rapid development - quick iterations of system which require making frequent updates to the data structure without much downtime between versions
  * ![](./How%20to%20Select%20NoSQL%20DB.jpeg)

参考：https://pingcap.medium.com/how-to-efficiently-choose-the-right-database-for-your-applications-20a109abced3  

### CAP Theorem
It is impossible for a distributed software system (especially data store) to simultaneously provide more than two of the following guarantees (CAP): Consistency, Availability, and Partition tolerance. When design a distributed system, trading off among CAP is almost the first thing to consider.  
* Consistency - All nodes see the same data at the same time. It is achieved by updating several nodes before allowing further reads.
* Availability - Every request gets a response on success/failure. It is achieved by replicating the data across different servers.
* Partition tolerance - System continues to work despite message loss or partial failure.
  
### Service Meltdown, Service Downgrade and Service Circuit Breaker
[服务降级与服务熔断](./服务降级与服务熔断.md)  
  
</details>

<br />
  
## Practice Examples
<details>
<summary>Design TinyURL</summary>

* [系统设计 TinyURL 完整版](./example%20questions/Design%20a%20URL%20Shortener%20(TinyURL)%20System.md)  
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


<details>
<summary>设计 Yelp</summary>

设计 Yelp 或附近的朋友  
设计一个类似 Yelp 的服务，用户可以搜索附近的地方，如餐馆、剧院或购物中心等，还可以添加/查看地方的评论。类似的服务；近距离（Proximity）服务器。难度等级：难  
1. 为什么是 Yelp 或 Proximity 服务器？Proximity 服务器用于发现附近的景点，如地方、活动等。
2. 系统的要求和目标 - 服务将存储关于不同地方的信息，以便用户可以对它们进行搜索。在查询时，服务将返回用户周围的地方列表。  
  
### Scenario 场景 + Service 服务  
类似 Yelp 的服务应该满足以下要求：  
* 功能要求：
  1. 用户应该能够添加/删除/更新地方。
  2. 给定他们的位置（经度/纬度），用户应该能够找到附近所有在给定半径内的地方。
  3. 用户应该能够添加关于一个地方的反馈/评论。反馈可以有图片、文字和评价。
* 非功能要求：
  1. 用户应该有一个最低延迟的实时搜索体验。
  2. 服务应该支持大量的搜索负载。与增加一个新地方相比，会有大量的搜索请求。
  3. 规模估计 - 建立系统，假设有 5 亿个地方和每秒 10 万次查询（QPS）。也假设每年有 20% 的地方和 QPS 的增长。
  4. 数据库 schema - 
     1. 每个位置可以有以下字段。
        1. 地点ID（8 字节）。独一无二地识别一个地点。（尽管一个 4 字节的数字足以唯一地识别 500M 的地点，但考虑到未来的增长，将采用 8 字节的 LocationID。）
        2. 名称（256 字节）
        3. 纬度（8 个字节）
        4. 经度（8 个字节）
        5. 描述（512 字节）
        6. 类别（1 字节）。例如，咖啡店、餐馆、剧院等。
        7. 总大小：8 + 256 + 8 + 8 + 512 + 1 => 793 字节
     2. 还需要存储一个地方的评论、照片和评级。可以有一个单独的表来存储地方的评论。
        1. 地点ID（8 字节）
        2. ReviewID（4 字节）。唯一标识一个评论，假设任何地点不会有超过 2^32 条评论。
        3. ReviewText (512 bytes)
        4. 评价（1字节）：一个地方得到多少颗星（满分 10 分）。

系统 API  
可以有 SOAP 或 REST APIs 来暴露服务的功能。以下可以是搜索的 API 的定义。  
```search(api_dev_key, search_terms, user_location, radius_filter, max_results_to_return, category_filter, sort, page_token)```  
参数：  
* api_dev_key（字符串）一个注册账户的 API 开发者密钥。这将被用于根据分配的配额来节制用户。
* search_terms（字符串）一个包含搜索条件的字符串。
* user_location（字符串）执行搜索的用户的位置。
* radius_filter（数字）可选的搜索半径，以米为单位。
* maximum_results_to_return（数字）要返回的业务结果的数量。
* category_filter（字符串）用于过滤搜索结果的可选类别，例如餐馆、购物中心等。
* sort（数字）可选的排序模式。最佳匹配（0-默认），最小距离（1），最高评价（2）。
* page_token（字符串）这个标记将指定结果集中的一个页面，应该被返回。
  
API 返回数据 (JSON)  
一个包含符合搜索查询的企业列表信息的 JSON。每个结果条目将有企业名称、地址、类别、评级和缩略图。  

### Storage 存储
基本系统设计和算法  
在高层次上，需要存储和索引上述的每个数据集（地方 - place、评论等）。鉴于一个地方的位置并不经常变化，不需要担心数据的频繁更新。作为对比，如果打算建立一个对象确实经常改变其位置的服务，例如，人或出租车，那么可能会想出一个非常不同的设计。  
* （不可行方案）传统 SQL 解决方案
  * 每个地方将被存储在一个单独的行中，由 LocationID 唯一标识。每个地方的经度和纬度将分别存储在两个不同的列中，为了进行快速搜索，应该对这两个字段建立索引。要找到一个给定位置（X，Y）在半径 `D` 内的所有附近的地方，可以这样查询：```Select * from Places where Latitude between X-D and X+D and Longitude between Y-D and Y+D```（更严格的应使用勾股定理）
  * 这个查询的效率会有多高？估计有 5 亿个地方要存储在服务中。由于有两个独立的索引，每个索引都可以返回一个巨大的地方列表，在这两个列表上执行交集是没有效率的。另一种看待这个问题的方式是，在 `X-D` 和 `X+D` 之间可能有太多的位置，同样，在 `Y-D` 和 `Y+D` 之间也是如此。如果能以某种方式缩短这些列表，就能提高查询的性能。
* （可行方案一）是使用 QuadTree，就是说把地图上任意一个区域都划分成四个子区域，每个区域如果节点超过一个阈值，就继续划分。
  * 即把整个地图划分成更小的网格。每个网格将存储所有在特定经度和纬度范围内的地点。该方案将只需查询几个网格就可以找到附近的地方。给定一个位置和半径，就可以找到所有邻近的网格，然后查询这些网格来找到附近的地方（place）。GridID（一个 4 字节的数字）是地图中唯一的。![](./Grid%20Map.png)
  * 合理的网格大小：因为想减少网格的数量，可以把网格大小设置等于想查询的距离。如果网格大小等于要查询的距离，那么只需要在包含给定位置和邻近 8 个网格的网格内进行搜索。
  * 在数据库中，可以将 GridID 与每个位置一起存储，并在其上建立索引，以加快搜索速度。```Select * from Places where Latitude between X-D and X+D and GridID in (GridID0, GridID1, ... GridID8)``` 现在查询将会改善查询的运行时间
  * 应该在内存中保留索引吗？将索引保持在内存中会提高服务性能。可以将索引保存在一个哈希表中，其中 `key` 是 GridID，`value` 是该网格中包含的地方列表。需要多少内存来存储该索引？假设搜索半径是 10 英里；鉴于地球的总面积约为 2 亿平方英里，将有 2000 万个网格。将需要一个 4 字节的数字来唯一标识每个网格，由于 LocationID（或称 PlaceID）是 8 字节，将需要 4GB 的内存（忽略哈希表的开销）来存储索引。(4 * 20m) + (8 * 500m) ~= 4GB
  * 解决方案仍然会运行缓慢，因为 Place 并不是均匀地分布在网格中，有的区域稠密有的相反。动态地调整网格大小，当有一个有很多地方的网格时，就把它分解成更小的网格，这个问题就可以得到解决。该方法的几个挑战可能是：1 如何将这些网格映射到地点；2 如何找到一个网格的所有相邻的网格。
  * 假设不希望在一个网格中拥有超过 500 个位置，这样就可以有一个更快的搜索。因此，每当一个网格达到这个极限时，就把它分解成四个同等大小的网格，并在其中分配位置。
  * 什么数据结构可以保存这些信息？一个每个节点有 4 个子节点的树可以达到该目的。每个节点将代表一个网格，并包含该网格中所有地方的信息。如果一个节点达到了 500 个位置的上限，将把它分解，在它下面创建 4 个子节点，并在它们之间分配位置。这样一来，所有的叶子节点将代表不能进一步分解的网格。所以叶子节点将保留一个地方的列表与他们。这种每个节点可以有 4 个孩子的树状结构被称为 QuadTree，```Select * from Places where Latitude between X-D and X+D and GridID in (GridID0, GridID1, ... GridID8)```![](./QuadTree.png)![](./PR%20QuadTree.png)
  * 将如何建立一个 QuadTree？将从一个根节点（在网格中代表整个世界）开始。由于它将有超过 500 个位置，将把它分解成 4 个节点并在它们之间分配位置。将在每个子节点上不断重复这个过程，直到不再有超过 500 个位置的节点。
  * 如何为一个给定的位置找到网格？将从根节点开始，向下搜索以找到需要的节点/网格。在每一步，将查看正在访问的当前节点是否有子节点。如果有，将移动到包含所需位置的子节点，并重复这个过程。如果该节点没有任何子节点，那么该节点就是想要的节点。
  * 将如何找到一个给定网格的相邻网格？由于只有叶子节点包含位置列表，可以用一个双链表连接所有的叶子节点。这样就可以在相邻的叶子节点中向前或向后迭代，找出想要的位置。另一种寻找相邻网格的方法是通过父节点。可以在每个节点中保留一个指针来访问其父节点，由于每个父节点都有指向其所有子节点的指针，可以很容易地找到一个节点的兄弟姐妹。可以通过上升到父节点的指针来不断扩大对邻近网格的搜索。
  * 搜索的工作流程将是什么？将首先找到包含用户位置的节点。如果该节点有足够多的想要的地方，可以将它们返回给用户。如果没有，将继续扩展到邻近的节点（通过父指针或双链表），直到找到所需数量的地方或基于最大半径的搜索。
  * 存储 QuadTree 需要多少内存？对于每个地方，如果只缓存 LocationID 和 Lat/Long，将需要 12GB 来存储所有地方。24 * 500m => 12 GB 由于每个网格最多可以有 500 个地方，当有 500M 个地点，那么总共会有多少个网格？500M / 500 => 1M 网格。这意味着将有 1M 个叶子节点，它们将保存 12GB 的位置数据。一个有 100 个叶子节点的 QuadTree 将有大约 1/3 的内部节点，每个内部节点将有 4 个指针（用于其子节点）。如果每个指针是 8 个字节，那么需要存储所有内部节点的内存将是 - 1m * 1/3 * 4 * 8 = 10 mb。因此，容纳整个 QuadTree 所需的总内存将是 12.01GB。这可以很容易地装入服务器。
  * 如何将一个新的地方插入到系统中？每当用户添加了一个新的地方，就需要把它插入到数据库和 QuadTree 中。如果树居住在一台服务器上，添加一个新的地方很容易，但如果 QuadTree 分布在不同的服务器上，首先需要找到新地方的网格/服务器，然后将其添加到那里（在下一节讨论）。
* （可行方案二）是使用 Geohash，本质上就是降维。降维的原因是，一维的数据管理和查找起来要容易得多，二维的数据要做到高效查找比较困难。查找条件是基于经纬度的，而不是一个单值；存储的数据也都是一个个经纬度形成的点，因此，Geohash 的办法就是把查找条件和存储的数据全部都变成一个个单值，这样就可以利用开发中熟悉的一维数组区域查找的技术来高效实现（比如把它索引化，而索引化其实是可以通过 B+ 树来实现的，因此 Geohash 的查询时间复杂度和 QuadTree 是可以在同一个数量级的，都是 logN）。
* （可行方案三）Google S2 希尔伯特曲线。  
  
PS：问：一些教程在教 Yelp、Uber 时使用 QuadTree 而一些使用 Geohash，有什么不同？哪个更好？答：QuadTree 是更精准更慢，Geohash 是不精准会有突变但更快，微软用 QuadTree，很多开源的如 ElasticSearch、MongoDB 用 Geohash，还有谷歌用 Hilbertcurve。[参考](https://www.1point3acres.com/bbs/thread-537353-1-1.html)  
  
### Scale 扩展
#### 数据分片  
如果有大量的地方，以至于索引无法容纳在一台机器的内存中，怎么办？随着每年 20% 的增长，将在未来达到服务器的内存极限。另外，如果一台服务器不能提供所需的读取流量怎么办？为了解决这些问题，必须对 QuadTree 进行分区  
将在此探讨 2 种解决方案（这 2 种分区方案也可以应用于数据库）  
* 方案一，基于区域的分片。可以将 Places 划分为区域（如邮政编码），这样，属于一个区域的所有地方将被存储在一个固定节点上。为了存储一个地方，将通过其区域找到服务器，同样地，在查询附近的地方时，将询问包含用户位置的区域服务器。这种方法有几个问题。
   1. 如果一个区域变得很热怎么办？持有该区域的服务器上会有大量的查询，使它的性能变慢。这将影响服务的性能。
   2. 随着时间的推移，与其他区域相比，一些区域最终会储存大量的地方。因此，在区域不断增长的情况下，保持地方的均匀分布是相当困难的。为了从这些情况中恢复，必须重新划分数据，或者使用一致性散列（方案二）。
* 方案二，基于 LocationID 的分片：散列函数将把每个 LocationID 映射到将存储该地点的服务器。在构建 QuadTree 时，将遍历所有的地方，并计算每个 LocationID 的哈希值，以找到它将被存储的服务器。为了找到一个地点附近的地方。必须查询所有的服务器，每个服务器将返回一组附近的地方。一个集中式的服务器将汇总这些结果，并将其返回给用户。系统会在不同的分区上有不同的 QuadTree 结构吗？是的，这可能发生，因为不能保证在所有分区上的任何给定网格都有相同数量的地方。然而，确实会确保所有服务器上的位置数量大致相等。不过，不同服务器上的这种不同的树状结构不会造成任何问题，因为系统将在所有分区上搜索给定半径内的所有邻近网格。  

![](./Yelp%20System%20Architecture.png)  
  
#### Replicas 和容错  
为 QuadTree 数据服务器设置主从（副本）机制，并采用读写分离也可有效缓解问题（分压、failover）。从机可能没有一些最近插入的 Place（会有几毫秒的延迟），但这可以接受。  
如果主次服务器同时 fail 则必须分配一个新的服务器，并在其上重建相同的 QuadTree。由于不知道这台服务器上存储了哪些 Places，如何才能做到这一点？Naive 的解决方案是遍历整个数据库，并使用哈希函数过滤 LocationIDs，以找出将被保存在这个服务器上的所有需要的 Places。这是低效和缓慢的；此外，在服务器重建期间，将无法从它那里提供任何查询，从而错过了一些应该被用户看到的 Place。  
如何才能有效地检索 place 和 QuadTree 服务器之间的映射？必须建立一个反向索引，将所有的 Places 映射到它们的 QuadTree 服务器。可以有一个单独的 QuadTree 索引服务器来保存这些信息。需要建立一个 HashMap，其中 Key 是 QuadTree 服务器的编号，Value 是一个 HashSet，包含 QuadTree 服务器上保存的所有 Places。需要在每个 Place 存储 LocationID 和 Lat/Long，因为信息服务器可以通过这个建立他们的 QuadTree。请注意，将 Places 的数据保存在一个 HashSet 中，这将能够快速从索引中添加/删除 Places。所以现在，每当 QuadTree 服务器需要重建自己的时候，它可以简单地要求 QuadTree 索引服务器提供它需要存储的所有 Places。这种方法肯定会相当快。还应该有一个 QuadTree 索引服务器的副本，以实现容错。如果一个 QuadTree 索引服务器 fail 了，它总能从数据库的迭代中重建它的索引。  
  
#### 缓存
为了处理热门位置，可以在数据库前面引入一个缓存。可以使用像 Memcache 这样的现成的解决方案，它可以存储所有关于热点地区的数据。应用服务器在访问后端数据库之前，可以快速检查缓存中是否有那个 space。根据客户的使用模式，可以调整需要多少个缓存服务器。对于缓存的驱逐策略，LRU 似乎适合 Yelp 系统。  
  
#### 负载平衡（LB）
可以在系统中的两个地方添加负载平衡层：1 在客户和应用服务器之间；2 在应用服务器和后端服务器之间。最初，可以采用一个简单的 Round Robin 方法；这将在后端服务器之间平均分配所有传入的请求。这种方法实现起来很简单，而且不会引入任何开销。这种方法的另一个好处是，如果一个服务器宕机了，负载平衡器将把它从轮流中剔除，并停止向它发送任何流量。  
Round Robin LB 的一个问题是，它不会把服务器的负载考虑在内。如果一个服务器过载或缓慢，负载平衡器将不会停止向该服务器发送新的请求。为了处理这个问题，需要一个更智能的 LB 解决方案，定期查询后端服务器的负载，并在此基础上调整流量。  
  
#### 排名
如果想对搜索结果进行排名，而不仅仅是根据距离的远近，同时也根据受欢迎程度或相关性进行排名，那会怎么样？  
怎样才能在一个给定的半径内返回最受欢迎的地方呢？假设跟踪每个地方的总体受欢迎程度。在系统中，一个汇总的数字可以代表这种受欢迎程度，例如，一个地方在十颗星中得到多少颗星（这将是用户给出的不同排名的平均值）？将把这个数字存储在数据库以及 QuadTree 中。在搜索给定半径内的前 100 个地方时，可以要求 QuadTree 的每个分区返回最受欢迎的前 100 个地方。  
然后，聚合器服务器可以在不同分区返回的所有地方中确定前 100 个地方。  
请记住，建立的系统并不是为了经常更新地方的数据。在这种设计下，如何修改 QuadTree 中一个地方的受欢迎程度？虽然可以搜索一个地方并更新它在 QuadTree 中的受欢迎程度，但这需要大量的资源，而且会影响搜索请求和系统的吞吐量。假设一个地方的人气不会在几个小时内反映在系统中，可以决定每天更新一次或两次，特别是在系统负载最小的时候。  
  
</details>


<details>
<summary>设计 Uber</summary>

设计一个像 Uber 一样的乘车共享服务，将需要乘车的乘客与有车的司机联系起来。类似的服务。Lyft, Didi, Sidecar 等。难度：困难。  
前置知识：设计 Yelp  
  
基本系统设计和算法  
可以采用`设计 Yelp` 中讨论的解决方案，并对其进行修改，使其适用于 Uber 用例。最大的区别是，QuadTree 在构建时没有考虑到会有频繁的更新。因此，动态网格解决方案有两个问题。
* 由于所有活跃的司机都是每 3 秒报告一次他们的位置，需要更新系统的数据结构来反映这一点。如果必须为司机位置的每一次变化更新 QuadTree，就会花费大量的时间和资源。为了将司机更新到新的位置，必须根据司机之前的位置找到合适的网格。如果新的位置不属于当前的网格，必须将司机从当前的网格中移除，并将用户移动/重新插入到正确的网格中。在这次移动之后，如果新的网格达到了司机的最大限制，就必须重新划分。
* 需要有一个快速的机制，将附近所有司机的当前位置传播给该区域的任何活跃客户。另外，当乘车正在进行时，我们的系统需要通知司机和乘客关于汽车的当前位置。
  
**办法是每次有司机报告他们的位置时，并不立即修改 QuadTree。** 如果不在司机每次更新时更新 QuadTree，它就会有一些旧数据，不能正确反映司机的当前位置。但是，建立 QuadTree 的目的是为了有效寻找附近的司机（或地方）。由于所有活跃的司机每三秒钟报告一次他们的位置，因此树上发生的更新将比查询附近的司机多得多。那么可以把所有司机报告的最新位置保存在一个哈希表中，并减少更新 QuadTree 的频率 - 保证司机的当前位置将在 15 秒内反映在 QuadTree 上，同时，维护一个哈希表（DriverLocationHT），该表将存储司机报告的当前位置。

这里将不再继续讨论 Grok System Design 的 QuadTree 方案，采用 Geohash 方案将可以更简洁地实现 Uber 系统设计。想更深入理解可以参考[Uber 实际方案](https://medium.com/@buckhx/unwinding-uber-s-most-efficient-service-406413c5871d) 和 [高效的多维空间点索引算法 — Geohash 和 Google S2](https://halfrost.com/go_spatial_search/)  
  
**下面是基于 Geohash 的 Uber / Uber Eat 系统设计**  
引用：https://jiayi797.github.io/2018/01/21/%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1-%E8%AE%BE%E8%AE%A1Uber/  
  
Uber 单一地区的系统架构:  
![](./Uber%20Single%20Region%20Services%20Architecture.png)  
注意，如果是 Uber Eat，架构大致类似，但是多一个 Restaurant 的客户端，而匹配最佳 Driver 时是根据 Restaurant 和 Driver 的位置来进行（不需要考虑 Customer 的位置），另外也因此匹配程序可以进行进一步优化（Driver 更新位置时更新 Restaurant 附近的 Driver 队列/池，匹配时可直接从队列/池里取出一个 Driver - 不一定是最近但是匹配效率更好）。  

Uber 多地区的系统架构概况:  
![](./Uber%20Multi%20Regions%20Services%20Architecture.png)  

**得分点 Checklist：**  
* Weak（完成以下）
    * 分析出 Uber 是写密集型应用（与大部分应用是读密集型不一样）
    * 解决 LBS 类应用的核心问题 - Geohash（字符串或二分优化） / Google S2（希尔伯特曲线）/ QuadTree
        * 如何存储 Driver 的地理位置
        * 如何查询乘客周边的车
* Hired（完成以下）
  * 分析出一个 Work Solution，说明整个打车流程
  * 分析出按照城市进行 Sharding
  * 知道如何做 Geo Fence 的查询
  * 通过分析选择合适的数据库
  * 考虑到单点失效（多机）与数据故障（备份）如何解决
* Strong Hired（完成以下）
  * 深入理解 NoSQL DB 的实现以及 Ring Pop 的实现（读论文 http://bit.ly/1mDs0Yh ）
  * 设计 Uber Pool / Uber East
  
### Scenario 场景
需要设计哪些功能？设计到什么地步？  
* 第一阶段
    * Driver report locations 司机报告自己的位置（司机需要定期通知服务，告诉他们当前的位置和他们可以接载乘客） —— heart beat 模式
    * Rider request Uber, match a driver with rider 乘客叫车，匹配一辆车（乘客可以看到附近所有可用的司机）
* 第二阶段
    * Driver deny/accept a request 司机取消/启动 接单
    * Driver cancel a match request 司机取消订单
    * Ride cancel a request 乘客取消请求
    * Driver pick up a ride/ start a trip 司机接人
    * Driver drop off a rider/end a trip 司机送到人
* 第三阶段
    * Uber Pool
    * Uber Eat

**QPS**  
假设 20w 司机同时在线：  
Driver QPS = 200k/4 = 50k，每次汇报 200k 个请求，每 4 秒汇报一次；这个占大头  
Peek Driver QPS = 50k x 3 = 150k  
Rider QPS 可以忽略：不用随时汇报位置，一定远小于 Driver QPS  
  
### Service 服务
Uber 主要干的事情就两件：  
* 记录车的位置：GeoService
* 匹配打车请求：DispatchService

Driver 如何获得打车请求？—— Report location 的同时，服务器顺便返回匹配上的打车请求，基本纲要：  
* 司机向 dispatch service 发送位置信息，同时返回匹配的乘客信息  
* 乘客向 dispatch service 发送打车请求，同时返回请求  

**具体打车流程 Work Solution（重点）**  
1. 乘客发出打车请求，服务器创建一次 Trip
    1. 将 trip_id 返回给用户
    2. 乘客每隔几秒询问一次服务器是否匹配成功
2. 服务器（Match Engine）根据 Location Table 和 Geohash 算法找到匹配的司机，写入 Trip，状态为等待司机回应
3. 同时修改 Driver Table 中的司机状态为不可用，并存入对应的 trip_id
4. 司机汇报自己的位置（更新 Location Table）
5. 顺便在 Driver Table 中发现有分配给自己的 trip_id
6. 去 Trip Table 查询对应的 Trip，返回给司机
7. 司机接受打车请求
8. 修改 Driver Table, Trip 中的状态信息
9. 乘客发现自己匹配成功，获得司机信息
10. 司机拒绝打车请求
11. 修改 Driver Table，Trip 中的状态信息，标记该司机已经拒绝了该 trip
12. 重新匹配一个司机，重复第 2 步

![](./Uber%20Services.png)  

数据库设计纲要（具体看下一节 `Stroage 数据`）：  
* Location Table -（存储 Driver - driver id 的实时位置，以及最后更新时间） - 写多  
* Driver Table -（Driver 是否可用 - 检查是否有分配 trip id）
* Trip Table（匹配 Driver 和 Rider 的表） — 读多（司机每 4 秒 requst 时会去读一下有没有匹配的）  
* Rider/User Table - 乘客/用户数据  
  
### Stroage 数据
**Geohash（前置知识）**  
经纬度可通过数学办法（多种）转换成字符串哈希，前缀共同字符越多说明越相近。  
![](./Geohash.jpeg)  
比如当一个 Rider（打车者）的位置的 Geohash 为 9q9hvu7wbq2s 时，如何找到位置开头以 9q9hv 开头的车辆？数据库怎么存？  
* SQL 数据库
    * 首先需要对 geohash 建索引
        * CREATE INDEX on geohash;
        * 使用 Like Query: SELECT * FROM location WHERE geohash LIKE "9q9hv%";
* NoSQL - Cassandra
    * 将 geohash 设为 column key
    * 使用 range query (9q9hv0, 9q9hvz)
* NoSQL - Redis / Memcached
    * Driver 的位置分级存储（小技巧）
        * 如 Driver 的位置如果是 9q9hvt，则存储在 9q9hvt， 9q9hv， 9q9h 这 3 个 key 中，而这三个类似于不同的经度；检索时，就依次减小经度进行查询看看有没有车
        * 6 位 geohash 的精度已经在 1 公里以内，对于 Uber 这类应用足够了
        * 4 位 geohash 的精度在 20 公里以上了，再大就没意义了，你不会打 20 公里以外的车
    * key = 9q9hvt, value = set of drivers in this location

以上不同数据库比较  
能够熟悉每种数据存储结构的特性，面试 checklist 得分点：  
* SQL 可以，但相对较慢
    * 原因 1：Like query 很慢，应该尽量避免；即便有 index，也很慢
    * 原因 2：Uber 的应用中，Driver 需要实时 Update 自己的地理位置
    * 被 index 的 column 并不适合经常被修改 -- B+ 树不停变动，效率低
* NoSQL – Cassandra 可以，但相对较慢
    * 原因： Driver 的地理位置信息更新频次很高
    * Column Key 是有 index 的，被 index 的 column 不适合经常被“修改”
* NoSQL – Memcached 并不合适
    * 原因 1：Memcached 没有持久化存储，一旦挂了，数据就丢失
    * 原因 2：Memcached 并不原生支持 set 结构
    * 需要读出整个 set，添加一个新元素，然后再把整个 set 赋回去

因此这个题，可以考虑用 Redis
* Redis
    * 数据可持久化
    * 原生支持 list，set 等结构
    * 读写速度接近内存访问速度 >100k QPS

用 Redis 怎么做这个呢？（[实际上 Uber 主要使用 Spanner 结合 Redis](https://www.youtube.com/watch?v=DY2AR8Wzg3Y)）  
用户打车角度  
用户发出打车请求，查询给定位置周围的司机  
* (lat,lng) → geohash → [driver1, driver2, …]
    * 先查 6 位的 geohash 找 0.6 公里以内的
    * 如果没有༌再查 5 位的 geohash 找 2.4 公里以内的
    * 如果没有༌再查 4 位的 geohash 找 20 公里以内的
* 上述的过程的数据库：
![](./Uber%20Location%20Table.png)  
* 匹配司机成功后，用户查询（Driver Table 和 Location Table）司机所在位置及详情
    * driver1_id → (lat, lng, status, updated_at, trip_id)

司机角度  
![](./Driver%20with%20Match%20Flow.png)  

### Scale 拓展
**数据库优化**  
隐患：  
* 需求是 150k QPS。Redis 的读写效率 > 100 QPS。那么是不是 1-2 台就可以了？
* 万一 Redis 挂了就损失很大。
* 解决方式—— DB Sharding
    * 目的 1：分摊流量
    * 目的 2：防止单点失败 Avoid Single Point Failure
* 按照城市 Sharding
    * 难点 1：如何定义城市？
    * 难点 2：如何根据位置信息知道用户在哪个城市？——用多边形代表城市的范围，问题本质变为：求一个点是否在多边形内，属于计算几何问题。
    * 城市数目：400 个
    * 万一乘客在两个城市边界怎么办？
        * 找到乘客周围的 2-3 个城市
        * 这些城市不能隔太远以至于车太远
        * 汇总多个城市的查询结果

**应对 QPS 增长的系统优化**  
当请求越来越多的时候，就需要一个消息队列（最大的请求：司机地理位置的更新）  

可以根据地区（比如 Northeast、Northwest 等等）分割集群（网关、后端应用服务、消息队列、数据库）  
各区信息需要同步吗？  
一般不需要，但是用户数据：可能有人非经常性地跨区、跨国。  

</details>


<details>
<summary>设计 Youtube</summary>

[花花酱 Youtube 系统设计](https://www.youtube.com/watch?v=mp-OSK6jm1c)  
[Video Streaming System Design](https://medium.com/double-pointer/system-design-interview-video-streaming-service-e-g-netflix-or-youtube-design-adc2402e54a1)  
[Design Youtube](https://systeminterview.com/design-youtube.php)  
  
主要步骤：  
1. 明确需求（功能性与非功能性）- 面试时间有限，所以挑 2-3 个重点功能进行设计（需要先和面试官达成一致）
   1. Feature 需求（功能性需求）
      1. 上传视频（重点功能）
      2. 观看/播放视频（重点功能）
      3. 搜索（一般重点功能）
      4. 分享
      5. Like/Dislike
      6. 评论
      7. 推荐等等...
   2. 流量/用户量（日活用户）
      1. Consistency（一致性）
         1. 得到的数据永远是最新的
         2. Tradeoff with Availability: Eventual Consistency（最终一致性）
      2. Availability（高可用）
         1. 系统流量比较大的时候的扩展性与低延迟性能（重点非功能）
         2. 每个请求可得非报错响应，即不严格要求一定返回的是最新写数据（最终一致性）
      3. Fault Tolerance（系统容错性）
         1. 即使网络节点的原因导致随机的（arbitrary）数据丢失或延迟，依然不影响系统正常运转
2. 带宽存储估算
   * 带宽存储估计分析在实践中也需要，当一个新项目开始前，需要根据此类分析估算基建资源的需求（基建资源一般要提前几个月时间申请）
   * 了解系统大致规模，从而采用相应的设计方案（扩展性相关）
   * 2 Billion（20 亿）总用户量，1.5M 日活用户（DAU）
   * 上传用户（假设占总用户的 1%）上传发布视频的频率为 1 video/week
   * 观看用户（DAU）每天平均看 50 min
   * 视频长度，假设每个 10 min、分辨率 1080p（假设观看此分辨率的带宽是 5Mbps）
   * 存储估算
     * 新视频 - 2B * 1% / 7 days = 33 videos/sec（33 videos/sec 指的是每秒有 33 个视频被完整上传）
     * 每分钟文件大小 - 1080p 视频要转码到低分辨率（720p、480p、360p）以满足不同的设备与带宽需求，即 100 MB/min
     * 每天新增存储 - 33 videos/s * 86400s * 10min (视频长度) * 100 MB/min = 2.7 PB/day
     * 数据的备份 - 冗余：一个 region（数据中心）做 3 个备份；可用性：跨 region（数据中心）做 3 个备份；总共是 9 倍
   * 带宽估算
     * 上传带宽 - 33 videos/sec * (10 * 60s) * 5Mbps = 99Gbps - 这里使用 5Mbps 而不是上面的数据是因为此时不用考虑分辨率转换，10 是视频均长为 10 分钟，60 是一分钟有 60秒
     * 下载/观看带宽 - 同时用户数量： 150M DAU * 50mins / (24 * 60 mins/day) = 5.2M 个用户；带宽：5.2M * 5Mbps = 26Tbps；读写比例：26Tbps / 99Gbps = 263 : 1（即读 heavy 系统 - 决定后续 High Level 设计与扩展）
3. System APIs（可以使用 SOAP 或 REST API）
   * uploadVideo(string apiToken, string videoTitle, string videDesc, stream videoContents) - 因为视频的处理需要比较长的时间，所以服务器会采用异步处理，用户此时去做其他事情没必要傻等，处理完系统平台会通过邮件或平台 UI 上通知上传完毕。上传过程细节如下：
     1. 客户端发起 HTTP 请求以建立信道，此时服务端开始：a. 创建 videoID、b. 准备存储空间位置（上传目标地址如 S3）、c. 等等
     2. 服务端返回上传目标地址，客户端开始读取 video 并分块上传到对应的地址，Youtube 实际上采用 HTTP 协议来分块上传视频文件 ![](./Youtube%20Client%20and%20Server.png)
   * streamVideo(string apiToken, string videoId, int offset, int codec, int resolution) - codec 是视频的编码格式（与用户设备是否支持有关），resolution（分辨率）可以自动根据当时带宽大小决定以优化观影体验（参考：[基于 HTTP 的动态自适应流](https://zh.wikipedia.org/wiki/%E5%9F%BA%E4%BA%8EHTTP%E7%9A%84%E5%8A%A8%E6%80%81%E8%87%AA%E9%80%82%E5%BA%94%E6%B5%81)）。该 API 将返回一个已经位移了 offset（时间戳）的视频流，使用的流媒体协议可以是 RTMP 之类的（关于更多流媒体协议的细节，可以看下面的单独章节）。
   * searchVideo(string apiToken, string searchKey, string userLocation, string pageToken)
4. High Level 整体系统设计（架构图）
   * 上传架构：视频转码比较耗时所以需要一个消息队列进行异步处理。过程如下：当视频上传后，Upload Service 会往队列里添加一个视频处理任务，下游会有一个 Video Processing Service 从队列里取任务，然后从文件系统（Distributed Media Storage - 比如 S3、HDFS、GlusterFS）下载相应视频进行处理（转码、提取缩略图等）完后把新的视频和缩略图存放到文件系统，同时在 metadata 数据库中更新视频与缩略图的存放地址。系统对用户播放延时要求比较高，所以会把视频 push 到离用户比较近的服务器（CDN），Video Distributed Service 就是负责把视频和图片分发到 CDN 各个节点上，因为比较耗时所以也是异步进行（从 Completion Queue 取任务） ![](./Youtube%20Upload%20Architecture.png)![](./Netflix%20Upload%20Architecture.png)
     * Video Processing Service 从文件系统下载视频并分割成多个小块/小片段，然后并行同时地（更快）对它们进行解码然后再编码（变成不同的设备或文件格式和分辨率），除此之外还会进行缩略图获取以及机器学习对视频内容分析等等处理。（上传的视频不会存储为单个大文件，而是存储在多个小块中，因为可能会上传大视频，处理或流式传输单个大文件会非常耗时。并且当视频以块的形式存储后，当观看者使用时无需在播放之前下载整个视频，它将从服务器请求第一个块，当该块正在播放时，客户端请求下一个块，因此块之间的延迟最小，用户可以在观看视频时获得无缝体验）![](./Youtube%20Upload%20Video%20Stored%20in%20Chunks.png)
       * 原始视频通常是一种高清格式，大小可以以 TB 为单位。Netflix 系统为支持多种设备和不同的网络速度，为每部电影或节目生成并存储了 1200 多种格式。需要将每个视频转换成不同的格式（mp4、avi、flv 等），以及每种格式的不同分辨率（例如 1080p、480p、240p 等），假设支持 i 种格式和 j 种分辨率，系统将为上传到平台的每个视频生成 i*j 个视频。这些块中的每一个的条目都将被写入元数据数据库，包括保存实际文件的分布式存储的路径。![](./Netflix%20Convert%20Formats.png)
       * 不同的内容创作者可能有不同的视频处理要求。例如，一些需要在视频添加水印，一些自己提供缩略图，一些上传高清视频，而另一些则不需要。为了支持不同的视频处理流水线并保持高并行性，可以添加某种抽象级别并让客户端程序定义要执行的任务。例如，Facebook 的流媒体视频引擎使用有向无环图 (DAG) 编程模型，该模型分阶段定义任务，以便它们可以按顺序或并行执行。视频转码过程可以采用类似的 DAG 模型来实现灵活性和并行性。![](./Video%20Process%20DAG.png)![](./Video%20Process%20DAG%202.png)
     * CDN 是内容分发系统（视频、图片是静态内容，非常适合 CDN 的分发），在不同地区都有服务器，CDN 通常重点使用缓存技术，通过缓存内存来服务绝大部分的内容。并不是所有视频都会分发到 CDN（CDN 容量也有限），比如冷门视频（每天只有 1-20 次观看量）会从原数据中心 stream 给用户（热门视频由 CDN stream 给用户）。
   * 下行（播放）架构：用户客户端播放请求通过 LB 转发给 Video Playback Service，该服务主要负责视频流的播放，与该服务协作还有一个 Host Identify Service（给定一个 video 和用户 IP 地址、设备信息，然后查找离用户最近的最适合设备与分辨率的视频的 CDN 地址，然后用户可以通过该 CDN 直接观看该视频了，如果没找到任何 CDN 地址则给出原数据中心地址），其他视频元数据如标题、描述等从 metadata 数据库中获取。![](./Youtube%20Watch%20Video%20Architecture.png)
   * 搜索架构 ![](./Youtube%20Search%20Architecture.png)
   * 另外需要注意的是实际中上面每个架构的每一个 Service（比如 Upload Service）可以是同一个微服务的集群，并不是单独一个机器、节点、应用。
5. 数据存储设计
   * 数据库表设计：
     * User: {VARCHAR(32) userID, VARCHAR(255) name, VARCHAR(255) email, BIGINT(20) numSubscribe...}
     * Video Metadata: {VARCHAR(32) videoID, VARCHAR(32) userID, VARCHAR(100) title, VARCHAR(255) desc, VARCHAR(255) videoAddr, VARCHAR(255) thumbnailAddr, BIGINT(20) numLike, BIGINT(20) numDislike, BIGINT(20) numView...} - 其中 numLike、numDislike、numView 应该拆分到另外的表或数据库，因为它们是大数据而且写频率非常高，在做这类数据的统计时，需要用到一些相关的数据结构，比如 [HyperLogLog](./../../Common%20Algorithm%20and%20Theory/HyperLogLog.md)，[Redis 里有内置的 HyperLogLog 数据结构及相关工具供使用](https://mp.weixin.qq.com/s/MVxmH5r0tP6sQ9WKVLoDbw)。
     * Comment: {VARCHAR(32) commentID, VARCHAR(32) userID, VARCHAR(32) videoID, VARCHAR(255) content, TIMESTAMP time...}
   * 数据存储选择：
     * SQL - 适合存储 User、Video Metadata 表
     * NoSQL - 适合非结构化数据，比如在 BigTable 里存储视频缩略图往往可以优化性能（不过实际上许多企业还是放在 File System 里）
     * File System / Blob Storage - 特别适合存储多媒体文件（图片、音频、视频等等）。因为视频平台的数据量很大，所以 File System 需要使用分布式的文件系统比如 HDFS、GlusterFS，又或者使用 Blob Storage / Object Storage 比如 Netflix 使用 AWS S3（S3 是 Object Storage，但也是通过类似 GFS2 之类的 Blob Storage 配合缓存、元数据存储等等组件、API 服务实现的，[参考](https://www.youtube.com/watch?v=9_vScxbIQLY)）。
6. 扩展性与优化
   * 找出系统的潜在瓶颈，然后提出解决方案、优缺点分析、然后做取舍
     * 数据分片 - 按 videoID 分片并且使用一致性哈希的方法。优势：按 videoID 而不是 userID 的好处是避免热门 up 主的问题。劣势：查询一个 user 的 videos 需要查询所有的 shards，以及单个热门视频会导致大流量集中在单个数据库（解决方案 - 使用缓存存储热门视频或拷贝视频到多个服务器上）。
     * 数据拷贝 - Primary-secondary 配置，数据写入 Primary 然后 propagate 到所有 secondaries 节点。从 secondary 读取。优势：可用性，随时可读不影响写压力。劣势：影响了数据一致性 - 只能是最终一致性，即用户不一定能读到最新数据（在本系统可接受这个取舍）。
     * 负载均衡 - 服务或者数据拷贝到多个机器，来服务大流量以提高系统可用性与降低延迟。
     * 自动扩展 - 当流量激增时，通过监控微服务的资源使用率（如 CPU 或内存）高于阀值时，增添或减少更多的服务副本。
     * 缓存 - Redis 以及 CDN 等。特别适合 read heavy 系统（视频系统读写比例是 200 : 1，是典型的 read heavy）。在任何读数据的地方都可以放缓存以减少对数据库的 hit，策略可使用最近最少使用（LRU）。
       * 需要缓存多少？采用二八原则，缓存 20% 每天读取的数据（daily read videos）。
       * 缓存会很大，如数据库一般也需要分布到多台机器上，分布机制可采用一致性哈希方法。
       * CDN 优化，通过分析预测，提前把客户会观看的视频推送到离用户最近的 CDN 上（可以在 off-peak 如半夜时段进行视频分发推送，降低单一时段的带宽压力）。
       * 通过和 ISP 合作进一步优化延时（Netflix Open Connect - OC Server/Appliance in ISP networks），把 Cache 放到 ISP 里，当用户请求某个视频且 ISP 发现它的 Cache 里有，就可以从它的 Cache stream 给用户，好处是相比 CDN（访问 CDN 仍需通过 ISP）少一步。实例：据报道，90% 的 Netflix 视频流量都是从 ISP 里的 Cache 走的。这种办法比较适合点播网站，即视频数量较少、观看比较集中。![](./Netflix%20Caching%20Optimization%20Architecture.png)
     * 上传速度优化
       * 并行视频上传 - 将视频作为一个整体上传是低效的，可以通过 GOP alignment 将视频分割成更小的块，这允许在上一次上传失败时快速恢复上传。客户端可以实现通过 GOP 分割视频文件以提高上传速度。![](./Youtube%20Upload%20GOP%20Alignment.png)![](./Youtube%20Upload%20GOP%20Alignment%20Client.png)
       * 上传中心靠近用户 - 另一种提高上传速度的方法是在全球范围内建立多个上传中心，比如美国人可以上传视频到北美上传中心，中国人可以上传视频到亚洲上传中心。为此，系统使用 CDN 作为上传中心。
     * 消息队列
       * 另一个优化是构建一个松耦合的系统并启用高并行性。系统设计需要一些修改来实现高并行度。以下是视频从原始存储传输到 CDN 的原始流程，如图 1，表明输出取决于上一步的输入，这种依赖性使并行性变得困难。为了使系统更松耦合，引入了消息队列，在引入消息队列之前，编码模块必须等待下载模块的输出，引入消息队列后，编码模块不再需要等待下载模块的输出，如果消息队列中有事件，编码模块可以并行执行这些作业，如图 2。![](./Youtube%20System%20Design%20Couple.png)![](./Youtube%20System%20Design%20Decouple.png)
     * 错误处理 - 构建一个高度容错的系统，可以高效处理错误并快速从错误中恢复。
       * 可恢复的错误。对于视频片段转码失败等可恢复的错误，一般的思路是重试几次操作。如果任务继续失败并且系统认为它不可恢复，它会向客户端返回相关的错误代码。
       * 不可恢复的错误。对于视频格式错误等不可恢复的错误，系统会停止与视频相关的正在运行的任务，并将相关的错误代码返回给客户端。
       * Typical errors for each system component are covered by the following playbook:
         * Upload error: retry a few times.
         * Split video error: if older versions of clients cannot split videos by GOP alignment, the entire video is passed to the server. The job of splitting videos is done on the server-side.
         * Transcoding error: retry
         * Preprocessor error: regenerate DAG diagram
         * DAG scheduler error: reschedule a task
         * Resource manager queue down: use a replica
         * Task worker down: retry the task on a new worker
         * API server down: API servers are stateless so requests will be directed to a different API server.
         * Metadata cache server down: data is replicated multiple times. If one node goes down, you can still access other nodes to fetch data. We can bring up a new cache server to replace the dead one.
         * Metadata DB server down: Master is down. If the master is down, promote one of the slaves to act as the new master. Slave is down. If a slave goes down, you can use another slave for reads and bring up another database server to replace the dead one.
  

**视频去重**  
拥有大量用户，上传大量视频数据，服务将不得不处理许多重复的视频。重复的视频通常在宽高比或编码方面有所不同，可能包含叠加层或额外的边框，或者可能是较长的原始视频的摘录。重复视频过多会在多个层面产生影响：  
* 数据存储：保留同一视频的多个副本会导致浪费存储空间。
* 缓存：重复的视频会占用可用于独特内容的空间，从而导致缓存效率下降。
* 网络使用：增加必须通过网络发送到网络内缓存系统的数据量。
* 能源消耗：更高的存储、低效的缓存和网络使用会导致能源浪费。
  
对于用户而言，这些情况将导致：重复搜索结果、更长的视频启动时间和视频流传输中断。  
对于系统而言，相比于在稍后再查找处理重复视频，在用户上传视频的早期就删除重复数据更有意义。内联重复数据删除将节省大量可用于编码、传输和存储视频副本的资源。一旦任何用户开始上传视频，服务就可以运行视频匹配算法（例如，[块匹配 | Block Matching Algorithm](https://en.wikipedia.org/wiki/Block-matching_algorithm)、[相位相关 | Phase Correlation](https://en.wikipedia.org/wiki/Phase_correlation)等）来查找重复项，如果发现已经有正在上传的视频的副本，可以停止上传并使用现有的副本，或者使用新上传的视频（如果它的质量更高）。如果新上传的视频是已有视频的子部分（反之亦然），可以智能地将视频分成更小的块，这样只上传那些不重复的部分。  
  
**视频流式传输协议**  
当用户在 Youtube 上观看视频时，通常视频流会立刻开始播放而不是等待整个视频下载好后再播放。下载整个视频意味着把视频复制到客户端，而流式传输（streaming）意味着客户端连续接收来自远程源视频的视频流，当用户观看流媒体视频时，客户端一次会加载一点数据，因此可以立即、连续地观看视频。  
在讨论视频流之前，先看一个重要的概念：流协议（streaming protocol）。这是一种控制视频流数据传输的标准化方法。流行的流媒体协议有：  
* MPEG–DASH：MPEG 代表 "Moving Picture Experts Group"，DASH 代表 "Dynamic Adaptive Streaming over HTTP"，中文名是**基于 HTTP 的动态自适应流**，它是一种自适应比特率流技术，也是一项国际标准，使高质量流媒体可以通过传统的 HTTP 网络服务器以互联网传递。类似苹果公司的 HTTP Live Streaming（HLS）方案，MPEG-DASH 会将内容分解成一系列小型的基于 HTTP 的文件片段，每个片段包含很短长度的可播放内容，而内容总长度可能长达数小时（例如电影或体育赛事直播）。内容将被制成多种比特率的备选片段，以提供多种比特率的版本供选用。当内容被 MPEG-DASH 客户端回放时，客户端将根据当前网络条件自动选择下载和播放哪一个备选方案。客户端将选择可及时下载的最高比特率片段进行播放，从而避免播放卡顿或重新缓冲事件。也因如此，MPEG-DASH 客户端可以无缝适应不断变化的网络条件并提供高质量的播放体验，拥有更少的卡顿与重新缓冲发生率。
* Apple HLS：HLS 代表 "HTTP Live Streaming"
* Microsoft Smooth Streaming
* Adobe HTTP Dynamic Streaming (HDS)
  
不需要完全理解甚至记住这些流协议名称，因为它们是需要特定领域知识的底层细节。重要的是要了解不同的流协议支持不同的视频编码和播放器。当设计视频流服务系统时，必须选择正确的流协议来支持面对的用例。[流媒体协议更多细节](https://archerzdip.github.io/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C%E5%8D%8F%E8%AE%AE%E7%B3%BB%E5%88%97-%E6%B5%81%E5%AA%92%E4%BD%93%E5%8D%8F%E8%AE%AE%E7%AF%87-%E5%B8%B8%E8%A7%81%E7%9A%84%E6%B5%81%E5%AA%92%E4%BD%93%E5%8D%8F%E8%AE%AE%E4%BB%8B%E7%BB%8D/)  
  
视频系统的其他拓展功能：  
* 推荐
* 搜索
* 直播（Live Stream）
* 收藏
  
[关于 Netflix](http://highscalability.com/blog/2017/12/11/netflix-what-happens-when-you-press-play.html)  
在用户点击播放之前发生的所有事情都发生在后端，该后端在 AWS 中运行。这包括准备所有新传入的视频和处理来自所有应用程序、网站、电视和其他设备的请求。（任何不涉及提供视频的内容都在 AWS 中处理。这包括可扩展计算 EC2、可扩展存储 S3、业务逻辑、可扩展分布式数据库 DynamoDB 和 Cassandra、大数据处理和分析、推荐、转码以及数百个其他功能。）  
在用户点击播放后发生的所有事情都由 Open Connect 处理。Open Connect 是 Netflix 的自定义全球内容交付网络 (CDN)。Open Connect 将 Netflix 视频存储在世界各地的不同位置。用户客户端的视频流主要来自 Open Connect（CDN）。  
Netflix 在三个 AWS 区域运营：一个在北弗吉尼亚州，一个在俄勒冈州波特兰市，一个在爱尔兰都柏林。在每个区域内，Netflix 在三个不同的可用区运营。拥有三个区域的好处是任何一个区域都可以发生故障，而其他区域将介入并服务故障区域中的所有用户。  
分布式。分布式意味着数据库不是在一台大计算机上运行，​​而是在多台计算机上运行。用户的数据被复制到多台计算机，因此如果一台甚至两台保存用户数据的计算机发生故障，用户的数据将是安全的。事实上，用户的数据会复制到所有三个区域。这样，如果一个区域出现故障，当新区域准备好开始使用它时，用户的数据就会在那里。  
可扩展。可扩展意味着数据库可以处理系统想要放入的尽可能多的数据。这是分布式数据库的主要优势之一。可以根据需要添加更多计算机以处理更多数据。  
  
Netflix 视频分发  
分发意味着视频文件通过网络从中央位置复制并存储在世界各地的计算机上。对于 Netflix，存储视频的中心位置是 S3。CDN 背后的想法很简单：通过在全球范围内传播计算机，让视频尽可能靠近用户。当用户想要观看视频时，找到最近的带有视频的计算机并从那里流式传输到设备。每个有计算机存储视频内容的位置称为 PoP 或入网点。每个 PoP 都是提供互联网访问的物理位置。它包含服务器、路由器和其他电信设备。  
Netflix 开发了自己的视频存储计算机系统。Netflix 称它们为 Open Connect 设备或 OCA。每个 OCA 都是一个快速的服务器，经过高度优化，可用于传输大文件，并带有大量用于存储视频的硬盘或闪存驱动器。从硬件的角度来看，OCA 没有什么特别之处。它们基于商用 PC 组件，并由各种供应商组装在定制机箱中。从软件的角度来看，OCA 使用 FreeBSD 操作系统和 NGINX 作为 Web 服务器。是的，每个 OCA 都有一个 Web 服务器。通过 NGINX 提供视频流。其他视频服务，如 YouTube 和亚马逊，在他们自己的骨干网络上提供视频。这些公司实际上建立了自己的全球网络，用于向用户提供视频。这样做非常复杂且非常昂贵。Netflix 采用了完全不同的方法来构建其 CDN。Netflix 不运营自己的网络；它也不再运营自己的数据中心。相反，互联网服务提供商 (ISP) 同意将 OCA 放入其数据中心。OCA 免费提供给 ISP 以嵌入到他们的网络中。Netflix 还将 OCA 放置在互联网交换位置 (IXP) 中或附近。ISP 是用户的互联网提供商，它可能是 Verizon、Comcast、AT&T 或数千种其他服务。OCA 放置在 ISP 数据中心里可使得 Netflix 和 ISP 共赢（降低 ISP 的网络资源成本）。   


</details>


<details>
<summary>设计 Facebook Live</summary>

参考来源：  
https://www.youtube.com/watch?v=IO4teCbHvZw  
https://osjobs.net/system/posts/facebook-live/  
https://www.infoq.cn/article/kkylzqazdomqdkwxgxgv  
https://zh.wikipedia.org/zh-hans/%E6%83%8A%E7%BE%A4%E9%97%AE%E9%A2%98  

#### High Level Architecture
![](./Facebook%20Live%20Architecture.png)  
1. 直播端使用 RTMPS 协议发送直播数据到边缘节点（POP）
2. POP 发送数据到数据中心（DC）
3. DC 将数据编码成不同的清晰度并进行持久化存储
4. 播放端通过 MPEG-DASH / RTMPS 协议接收直播数据

#### 高并发挑战
* 直播数据是实时生成的，所以不能够进行预缓存
* 直播随时会发生，所以无法通过预计直播数量来提前分配资源
* 无法预计某个直播会不会观看者暴增

#### 传输协议以及编码
RTMPS 协议介绍  
RTMPS 协议就是为视频直播设置的，1) 它能够保证低延迟。2) 它在工业上已经被广泛应用，所以能够重用已有的客户端和服务端的库。3) 它基于 TCP 协议能够与大部分企业的网络架构兼容。4) 协议导致的应用大小占用较小。  

编码  
解析度为 400x400 和 720x720. 如果用户网络不好会自动转换成低分辨率  

#### 数据获取以及处理
* 直播流程![](./Facebook%20Live%20Broadcast.png)
  1. 直播端使用 RTMPS 协议发送直播流数据到 POP 内的随机一个代理服务器
  2. 代理服务器发送直播流数据到数据中心
  3. 数据中心的代理服务器使用直播/Stream ID 与一致性哈希算法发送直播数据到合适的编码服务器（在此之前曾尝试来源 IP 地址进行一致性哈希，但是会导致负载不均衡）
  4. 编码服务器有几项职责：
     1. 验证直播数据的格式是否正确。
     2. 关联直播 ID 以及编码服务器，保证即使连接中断，在重新连接的时候依然能够连接到相同的编码服务器。
     3. 使用直播数据编码成不同解析度的输出数据。
     4. 使用 DASH 协议输出数据。
     5. 把输出数据进行持久化存储。
* 播放流程![](./Facebook%20Live%20Playback.png)
  1. 播放端使用 HTTP DASH 协议向 POP 请求直播数据
  2. POP 里面的代理服务器会检查数据是否已经在 POP 的缓存内。如果是的话，缓存会返回数据给播放端，否则，代理服务器会向 DC 请求直播数据
  3. DC 内的代理服务器会检查数据是否在 DC 的缓存内，如果是的话，缓存会返回数据给 POP，并更新 POP 的缓存，再返回给播放端。不是的话，代理服务器会使用一致性哈希算法向对应的编码服务器请求数据，并更新 DC 的缓存，返回到 POP，再返回到播放端。

[什么是 MPEG-DASH?](https://www.cloudflare.com/en-gb/learning/video/what-is-mpeg-dash/)  

#### 可靠性挑战
网络因素  
* 根据网络连接速度来自动调整视频质量（ABR - Adaptive bitrate streaming，可应用于上传端和播放端）
* 使用短时间的数据缓存（Buffer）来解决直播端不稳定，瞬间断线的问题
* 根据网络质量自动降级为音频直播以及播放

**惊群效应 (Thundering Herd)**  
当多个播放端向同一个 POP 请求直播数据的时候，如果数据不在缓存中，这时候只有一个请求 A 会到 DC 中请求数据，其他请求会等待结果。但是如果请求 A 超时没有返回数据的话，所有请求会一起向 DC 访问数据，这时候就会加大 DC 的压力。解决这个问题的方法就是通过实际的情况来调整请求超时的时间。这个时间如果太长的话会带来直播的延迟，太短的话会经常触发惊群效应。  

在其他计算机科学语境中，惊群效应是指多进程（多线程）在同时阻塞等待同一个事件的时候（休眠状态），如果等待的这个事件发生，那么他就会唤醒等待的所有进程（或者线程），但是最终却只能有一个进程（线程）获得这个时间的“控制权”，对该事件进行处理，而其他进程（线程）获取“控制权”失败，只能重新进入休眠状态，这种现象和性能浪费（严重的系统上下文切换代价）就叫做惊群效应。  
解决办法还包括：  
* 不希望把所有进程都唤醒，就采用定点唤醒某一个进程的做法。
* 尽量避免进程上下文切换。

C10K 问题（Concurrent 10,000 Connections），指服务器同时支持上万个客户端连接的问题。可用 Linux 的 epoll，FreeBSD 的 kqueue，Solaris 的 /dev/poll 来解决。  
C10M 问题，是千万级并发实现。Linux 上通常用 epoll 实现。  

</details>


<details>
<summary>设计 Dropbox</summary>

参考：  
Grok System Design - Design Dropbox  
https://dropbox.tech/infrastructure/rewriting-the-heart-of-our-sync-engine  
https://dropbox.tech/infrastructure/asynchronous-task-scheduling-at-dropbox  
https://dropbox.tech/infrastructure/dropbox-traffic-infrastructure-edge-network  
https://dropbox.tech/infrastructure/optimizing-web-servers-for-high-throughput-and-low-latency  

云文件存储使用户能够在远程服务器上存储他们的数据。通常，这些服务器由云存储供应商维护，并通过网络（通常是互联网）提供给用户使用。用户按月为他们的云数据存储付费。类似的服务。OneDrive, Google Drive。难度等级 - 中等。  
云文件存储服务简化了数字资源在多种设备之间的存储和交换。从使用单一的个人电脑到使用具有不同平台和操作系统的多种设备，如智能手机和平板电脑，每个设备都可以在任何时候从不同的地理位置进行便携访问。以下是此类服务的一些主要优势：  
* 可用性。云存储服务的宗旨是随时随地提供数据。用户可以随时随地从任何设备访问他们的文件/照片。  
* 可靠性和耐用性。云存储的另一个好处是，它提供 100% 的数据可靠性和耐久性。云存储通过在不同地理位置的服务器上保存多个数据副本，确保用户永远不会丢失他们的数据。  
* 可扩展性。用户永远不必担心存储空间不足的问题。有了云存储，只要付费，就有无限的存储空间。  
  
  
### 系统的要求和目标
希望从一个云存储系统中实现什么？以下是系统的最高要求。
 1. 用户应该能够从任何设备上传和下载他们的文件/照片。
 2. 用户应该能够与其他用户分享文件或文件夹。
 3. 服务应该支持设备之间的自动同步，即在一个设备上更新一个文件后，它应该在所有设备上得到同步。即在一个设备上更新一个文件后，它应该在所有设备上得到同步。
 4. 该系统应支持存储高达 1GB 的大文件。
 5. 需要 ACID-ity。所有文件操作的原子性、一致性、隔离性和持久性应得到保证。
 6. 系统应该支持离线编辑。用户应该能够在离线状态下添加/删除/修改文件。而一旦他们上线，他们所有的改变应该被同步到远程服务器和其他在线设备上。
 7. 扩展要求 - 系统应该支持数据的快照，这样用户就可以回到文件的任何版本。
  
#### 一些设计上的考虑
* 预计有巨大的读和写量。
* 读取和写入的比例预计几乎是一样的。
* 在内部，文件可以被存储在小部分或小块中（比如 4MB）；这可以提供很多好处，即，所有失败的操作只对文件的小部分进行重试，如果一个用户未能上传一个文件，那么只有失败的那块才会被重试。
* 可以通过只传输更新的块来减少数据交换的数量。
* 通过删除重复的块，可以节省存储空间和带宽使用。
* 在客户端保留一份元数据（文件名、大小等）的本地副本可以减少大量服务器的 round trips。
* 对于小的变化，客户端可以智能地上传差异，而不是整个块。
  
#### 容量估计和限制条件
* 假设有 5 亿总用户，和 1 亿日活跃用户（DAU）。
* 假设每个用户平均从三个不同的设备连接。
* 平均而言，如果一个用户有 200 个文件/照片，将有 1000 亿个总文件。
* 假设平均文件大小为 100KB，这将带来 10PB 的总存储量。100b * 100kb => 10pb
* 还假设，每分钟将有一百万个活跃的连接。
  
### High Level Design
* 用户将指定一个文件夹作为他们设备上的工作空间。放在这个文件夹里的任何文件/照片/文件夹都会被上传到云端，每当一个文件被修改或删除，都会以同样的方式反映在云存储中。用户可以在他们所有的设备上指定类似的工作空间，在一个设备上做的任何修改都会传播到其他所有的设备上，以便在任何地方都有相同的工作空间视图。
* 在 High Level 上，需要存储文件和它们的元数据信息，如文件名、文件大小、目录等，以及这个文件与谁共享。因此，需要一些能够帮助客户上传/下载文件到云存储的服务器和一些能够促进更新文件和用户元数据的服务器。还需要一些机制来通知所有的客户，只要有更新发生，他们就可以同步他们的文件。
* 如下图所示，块服务器将与客户端合作，从云存储上传/下载文件，元数据服务器将在 SQL 或 NoSQL 数据库中保持文件的元数据更新。同步服务器将处理通知所有客户端不同变化的工作流程，以便进行同步。
![](./Dropbox%20High%20Level%20Design.png)  
  
#### 组件设计
逐一浏览一下系统的主要组成部分。  
* 客户端 - 客户端应用程序监控用户机器上的工作区文件夹，并将其中的所有文件/文件夹与远程云存储进行同步。客户端应用程序将与存储服务器合作，向后端云存储上传、下载和修改实际文件。客户端还与远程同步服务来处理任何文件元数据的更新，例如，文件名称、大小、修改日期等的变化。
  * 以下是客户端的一些基本操作。
    1. 上传和下载文件。
    2. 检测工作区文件夹中的文件变化。
    3. 处理因脱机或同时更新而产生的冲突。
  * 如何有效地处理文件传输？ - 如上所述，可以把每个文件分成小块，这样就只传输那些被修改的小块而不是整个文件。比方说，把每个文件分成固定大小的 4MB 的小块。可以根据以下情况静态地计算什么可能是最佳的块大小：
    1. 在云中使用的存储设备，以优化空间利用率和每秒的输入/输出操作（IOPS）
    2. 网络带宽
    3. 存储中的平均文件大小等。在系统的元数据中，还应该保留每个文件和构成文件的块的记录。
  * 应该在客户端保留一份元数据的副本吗？ - 保持元数据的本地拷贝不仅使系统能够进行离线更新，而且还可以节省大量的往返以更新远程元数据。  客户端如何有效地监听其他客户端发生的变化？一个解决方案是，客户端定期检查服务器是否有任何变化。这种方法的问题是，在本地反映变化时将会有延迟，因为与服务器在有变化时就通知相比，客户端将定期检查变化。如果客户端经常检查服务器的变化，不仅会浪费带宽，因为服务器大部分时间都要返回一个空的响应，而且也会让服务器很忙。以这种方式拉取信息是不可扩展的。解决上述问题的方法是使用 HTTP 长轮询。通过长轮询，客户端向服务器请求信息，并期望服务器不会立即响应。如果服务器在收到轮询的时候没有新的数据给客户，那么服务器就不会发送一个空的响应，而是保持请求开放，等待响应信息的出现。一旦有了新的信息，服务器会立即向客户端发送 HTTP/S 响应，完成开放的 HTTP/S 请求。在收到服务器的响应后，客户端可以立即发出另一个服务器请求，以获得未来的更新。基于上述考虑，可以将客户端分为以下 4 个部分。  
    1. 内部元数据数据库（Internal Metadata Database） - 将跟踪所有的文件、块、它们的版本，以及它们在文件系统中的位置。
    2. Chunker - 将把文件分割成更小的块，称为 chunk。它还将负责从其块中重建一个文件。分块算法将检测文件中被用户修改过的部分，并只将这些部分传输到云存储中；这将节省带宽和同步时间。
    3. 观察者（Watcher） - 将监视本地工作区文件夹，并通知索引器（下文讨论）用户执行的任何行动，例如，当用户创建、删除或更新文件或文件夹时。观察者还监听由同步服务广播的其他客户端上发生的任何变化。
    4. 索引器（Indexer） - 将处理从观察者那里收到的事件，并以修改过的文件块的信息更新内部元数据数据库。一旦这些块被成功地提交/下载到云存储，索引器将与远程同步服务进行通信，以向其他客户端广播变化，并更新远程元数据数据库。![](./Dropbox%20Client%20Architecture.png)
  * 客户端应该如何处理缓慢的服务器？ - 如果服务器很忙/不响应，客户端应该指数化地退后。意思是说，如果一个服务器响应太慢，客户端应该延迟重试，而且这种延迟应该以指数形式增加。
  * 移动客户端应该立即同步远程变化吗？ - 与桌面或网络客户端不同，移动客户端通常按需同步，以节省用户的带宽和空间。
* 元数据数据库 - 元数据数据库负责维护关于文件/群组、用户和工作空间的版本和元数据信息。元数据数据库可以是一个关系型数据库，如 MySQL，或一个 NoSQL 数据库服务，如 DynamoDB。无论数据库的类型如何，同步服务应该能够使用数据库提供一致的文件视图，特别是在多个用户同时处理同一个文件的情况下。由于 NoSQL 数据存储不支持 ACID 属性，以利于扩展性和性能，需要在同步服务的逻辑中以编程方式纳入对 ACID 属性的支持，以防选择这种数据库。然而，使用关系型数据库可以简化同步服务的实现，因为它们本身就支持 ACID 属性。元数据数据库应该存储以下对象的信息。
   1. 分块（Chunks）
   2. 文件
   3. 用户
   4. 设备
   5. 工作区（同步文件夹）
* 同步服务（器）
  * 同步服务（器）是处理客户端所做的文件更新并将这些变化应用到其他订阅的客户端的组件。它还将客户的本地数据库与存储在远程元数据数据库中的信息进行同步。由于同步化服务在管理元数据和同步用户文件方面的关键作用，它是系统架构中最重要的部分。桌面客户端与同步服务进行通信，以便从云存储中获得更新，或将文件和更新发送到云存储以及可能的其他用户。如果一个客户端离线了一段时间，它一上线就会轮询系统的新更新。当同步服务收到更新请求时，它与元数据数据库检查是否一致，然后继续进行更新。随后，向所有订阅的用户或设备发送通知，报告文件的更新情况。
  * 同步服务的设计方式应该是在客户端和云存储之间传输较少的数据，以实现更好的响应时间。为了达到这个设计目标，同步服务可以采用差分算法来减少需要同步的数据量。可以不把整个文件从客户端传输到服务器，或者反过来，只传输文件的两个版本之间的差异。因此，只有文件中被改变的部分被传输。这也减少了终端用户的带宽消耗和云数据存储。如上所述，将把文件分成 4MB 的小块，并且只传输修改过的小块。服务器和客户端可以计算一个哈希值（例如 SHA-256）来查看是否要更新一个块的本地副本。在服务器上，如果已经有一个具有类似哈希值的块（甚至来自另一个用户），就不需要再创建一个副本，可以使用同一个块。
  * 为了能够提供一个高效和可扩展的同步协议，可以考虑在客户端和同步服务之间使用一个通信中间件。通信中间件应该提供可扩展的消息队列和变化通知，以支持使用拉或推送策略的大量客户端。这样，多个同步服务实例可以从一个全局请求队列中接收请求，而通信中间件将能够平衡其负载。
* 消息队列服务
  * 架构的一个重要部分是一个消息中间件，它应该能够处理大量的请求。一个可扩展的消息队列服务，支持客户端和同步服务（器）之间基于异步消息的通信，最符合应用的要求。消息队列服务支持系统的分布式组件之间的异步和松散耦合的基于消息的通信。消息队列服务应该能够在一个高度可用、可靠和可扩展的队列中有效地存储任何数量的消息。
  * 消息队列服务将在系统中实现两种类型的队列。请求队列是一个全局队列，所有客户将共享它。客户端更新元数据数据库的请求将首先被发送到请求队列，同步服务将从那里获取更新元数据。对应于单个订阅客户的响应队列负责将更新消息传递给每个客户。由于消息一旦被客户端收到就会从队列中删除，系统需要为每个订阅的客户端创建单独的响应队列来共享更新消息。![](./Dropbox%20MQ%20and%20Sync%20Service.png)
* 云/块存储
  * 云/块存储存储了用户上传的文件块。客户端直接与存储互动，进行发送、接收对象操作。元数据与存储的分离使系统能够使用云中或 in-house 的存储数据。
  
![](./Dropbox%20System%20Design%20Architecture.png)  
  
#### 文件处理工作流程
下面的序列显示了在一个场景中应用程序的组件之间的互动，当客户端 A 更新一个与客户端 B 和 C 共享的文件，所以它们也应该收到更新。如果其他客户端在更新时不在线，消息队列服务将更新通知保存在单独的响应队列中，直到它们稍后上线。  
1. 客户端 A 将文件块（chunks）上传到云存储。
2. 客户端 A 更新元数据并提交更改。
3. 客户端 A 得到确认，并向客户端 B 和 C 发送有关更改的通知。
4. 客户端 B 和 C 收到元数据变化并下载更新的块。
  
#### 重复数据删除
重复数据删除是一种用于消除数据的重复副本以提高存储利用率的技术。它也可以应用于网络数据传输，以减少必须发送的字节数。对于每个新传入的块，可以计算它的哈希值，并将该哈希值与现有块的所有哈希值进行比较，看系统存储中是否已经有相同的块。  
在系统中可以通过 2 种方式实现重复数据删除：  
* 后处理去重 - 通过后处理重复数据删除，新的块首先被存储在存储设备上，随后一些程序会分析数据，寻找重复的部分。这样做的好处是，客户不需要在存储数据之前等待哈希计算或查找完成，从而确保存储性能不会下降。这种方法的缺点是：1）将不必要地存储重复的数据，虽然时间很短，2）重复的数据将被传输，消耗带宽。
* In-line 去重 - 重复数据删除的哈希计算可以在客户在其设备上输入数据时实时完成。如果系统识别出一个已经存储的数据块，那么在元数据中只添加对现有数据块的引用，而不是该数据块的完整副本。这种方法将带来最佳的网络和存储使用体验。
  
#### 元数据分区
为了扩大元数据数据库的规模，需要对它进行分区，以便它能够存储关于数百万用户和数十亿文件/块的信息。需要一个分区方案，将数据划分并存储在不同的 DB 服务器中。  
1. 垂直分区 - 在一个服务器上存储与一个特定功能相关的表。例如，可以在一个数据库中存储所有与用户相关的表，而在另一个数据库中存储所有与文件/群组相关的表。虽然这种方法实现起来很简单，但也有一些问题。
   1. 会有规模问题。如果有数万亿的块需要存储，而数据库不能支持存储如此巨大数量的记录，怎么办？将如何进一步划分这样的表？
   2. 在两个不同的数据库中 Join 两个表会导致性能和一致性问题。比如要多频繁地 Join 用户表和文件表？
2. 基于范围的分区 - 如果根据文件路径的第一个字母将文件/块存储在不同的分区中，会怎么样？在这种情况下，将所有以字母 "A" 开头的文件保存在一个分区中，将以字母 "B" 开头的文件保存在另一个分区中，依此类推。这种方法被称为基于范围的分区。甚至可以将某些出现频率较低的字母合并到一个数据库分区中。应该静态地提出这种分区方案，这样就可以始终以一种可预测的方式存储/查找文件。这种方法的主要问题是，它可能导致不平衡的服务器。例如，如果决定把所有以字母 "E" 开头的文件放到一个 DB 分区中，后来发现有太多以字母 "E" 开头的文件，以至于无法把它们放到一个 DB 分区中。
3. 基于哈希值的分区。在这个方案中，对要存储的对象进行哈希运算，根据这个哈希运算，找出这个对象应该去的 DB 分区。在例子中，可以以正在存储的文件对象的 "FileID" 的哈希值来确定该文件将被存储的分区。散列函数将随机地将对象分配到不同的分区，例如，散列函数总是可以将任何 ID 映射到 `[1...256]` 之间的数字，而这个数字将是将存储对象的分区。这种方法仍然会导致分区过载，但可以通过使用一致性散列来解决。
  
#### 缓存
系统中可以有两种缓存。为了处理热文件/块，可以为块存储引入一个缓存。可以使用现成的解决方案，如 Memcached，它可以存储整个块及其各自的 ID/Hash，块服务器在进入块存储之前可以快速检查缓存是否有想要的块。根据客户的使用模式，可以确定需要多少个缓存服务器。一个高端的商业服务器可以有 144GB 的内存；一个这样的服务器可以缓存 36K 的块。  
对于系统来说，最近使用最少的策略（LRU）是合理的策略。首先丢弃最近使用最少的块。类似地，可以为元数据数据库设置一个缓存。  
  
#### 负载平衡器（LB）
可以在系统中的两个地方添加负载平衡层：1）在客户和块服务器之间；2）在客户和元数据服务器之间。最初，可以采用一个简单的 Round Robin 方法，在后端服务器之间平均分配传入的请求。这种方法实现起来很简单，而且不会引入任何开销。这种方法的另一个好处是，如果一个服务器宕机了，LB 会把它从轮流中剔除，并停止向它发送任何流量。Round Robin LB 的一个问题是，它不会把服务器的负载考虑在内。如果一个服务器过载或缓慢，LB 不会停止向该服务器发送新请求。为了处理这个问题，可以放置一个更智能的 LB 解决方案，定期查询后端服务器的负载，并在此基础上调整流量。  
  
#### 安全、权限和文件共享
当用户在云端存储他们的文件时，最关心的问题之一是他们数据的隐私和安全，特别是在类 Dropbox 系统中，用户可以与其他用户分享他们的文件，甚至公开与所有人分享。为了处理这个问题，将在元数据数据库中存储每个文件的权限，以反映哪些文件可以被任何用户看到或修改。  
  
### 其他
Dropbox 异步任务框架 ATF：  
![](./Dropbox-ATF.png)  


</details>


<details>
<summary>设计 WhatsApp / Slack（IM）</summary>

设计 IM 系统  
参考：https://interview-science.org/%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1/IM%20%E7%B3%BB%E7%BB%9F  

### 功能性需求与核心指标
**功能性需求**  
1. 用户与用户收发信息
2. 用户与群组收发信息
3. 用户多客户端同步信息

**核心指标**  
1. 获取与发布信息的响应时间
2. 信息的有序性以及一致性

### 基础架构
读阶段  
![](./IM%20System%201.svg)  
1. 客户端向服务端请求消息
2. 服务端从数据库查询消息
3. 服务端返回消息给客户端

写阶段  
![](./IM%20System%202.svg)  
1. 客户端向服务端发送消息
2. 服务端更新消息到数据库
3. 服务端返回结果

瓶颈分析  
核心问题：读阶段需要主动或定期查询新消息  
解决方案：使用专门为实时通信设计的应用层协议，如 XMPP 或者 MQTT，它们能够主动推送消息给客户端。  
  
次要问题：客户端没有存储消息列表，无法在离线情况下获取之前的消息  
解决方案：使用 SQLite 等本地数据库保存聊天记录  
  
### 进阶架构
读阶段  
![](./IM%20System%203.svg)  
1. 客户端上线
2. 聊天服务器发布用户上线事件
3. 服务端，状态服务器都订阅该事件
4. 服务端向日志数据库查询该用户的新消息
5. 服务端将用户新消息发布到消息队列
6. 聊天服务端订阅了新消息发布事件，将其推送给客户端

写阶段  
![](./IM%20System%204.svg)  
1. 客户端使用实时通信协议与聊天服务端连接
2. 客户端发送消息给聊天服务器，客户端本地存储消息列表
3. 聊天服务端发送消息给消息队列
4. 服务端更新对应接受者的数据库

瓶颈分析
核心问题：不同实时通信协议的优缺点  
解决方案：浏览：[8 Most Popular Instant Messaging & Chat Protocols](https://www.cometchat.com/blog/popular-chat-and-instant-messaging-protocols)
  
次要问题：对于群组来说，发送消息可能会造成写放大，因为要写到所有群组成员的消息列表中  
解决方案：使用混合策略，当群组人数少于某个阈值时，使用原方案。反之，群组成员只会收到群组有消息的通知，收到通知后需要主动拉取消息。  
  
</details>


<details>
<summary>设计 Amazon（电商/秒杀网站）</summary>

转载自：https://osjobs.net/system/posts/spike-system/  
其他参考资料：https://www.youtube.com/watch?v=2BWr0fsDSs0  

现在的电商系统功能繁多，除了最基本的购买商品功能，还有物流跟踪，订单管理，社区交互等功能。不过面试中关注的主要是购买商品功能，这里将其他次要功能归类为其他业务功能，购买商品流程如下：  
![](./购买商品.svg)  
1. 客户通过客户端下单
2. 如果下单成功则进入支付阶段，否则返回购买失败
3. 进入支付阶段后，如果在一定时间内支付成功则返回购买成功，否则返回购买失败

### 从 0 到 1000
想象自己从零搭建一个电商平台，一开始平台里的商品种类以及日订单量都较少，商品种类有 100 款，日订单量只有 1000 条。根据以上信息，可以设计出架构 1：  
![](./电商架构1.svg)  
1. 客户端发送下单请求给服务端
2. 服务端查询数据库
     * 若该商品库存大于零，将库存减一并且返回下单成功
     * 若该商品库存等于零的话，返回下单失败

架构 1 简单直观，它忽略了系统可用性以及可扩展性，但在日订单量少，不会出现多位客户对同一件商品同时下单的情况下，它很好地完成了需要的功能。  

### 从 1000 到 100万
经过一段时间后，电商平台商品种类增加到 1 万款，日订单量飙升到 100 万条，而且在高峰期，例如晚饭后，睡觉前的订单量会特别多。这是一个好的消息，不过同时发现了一个问题，某些商品的成功下单量要大于库存量，也就是说出现了商品超卖的情况。这可是个严重的问题，因为没办法及时交货给客户对电商平台的信誉有极大影响。仔细分析架构 1 后，发现了问题的根源：当商品库存只剩下 1 件而有多位客户同时下单的时候，每个下单请求在查询的时候都发现库存大于零，并且将库存减 1 返回下单成功。下图中，在库存只有 1 件的时候，两个请求却都返回下单成功。  
![](./电商并发安全.svg)  

幸运的是，大部分并发问题都可以通过锁机制或者队列服务来解决：  
#### 锁机制
**悲观锁**  
可以观察到，超卖问题的原因在于事务查询和更新库区期间，库存已经被其他事务修改了。在学习悲观锁之前，先了解下什么是两阶段加锁，两阶段加锁是一个典型的悲观锁策略：  
> 两阶段加锁方法类似，但锁的强制性更高。多个事务可以同时读取同一对象，当只要出现任何写操作（包括修改或删除），则必须加锁以独占访问。—《数据密集型应用系统设计》

这里电商系统中可以应用两阶段加锁，由于下单请求涉及到修改库存，可以先使用排他锁锁定记录，防止被其他事务所修改。大部分关系型数据库都提供这种功能（在 MySQL 和 PostgresSQL 里面的语法是 SELECT … FOR 下UPDATE）。流程如下图：  
![](./电商悲观锁.svg)  
1. 蓝色请求先获取排他锁，查询和更新库存，在此期间黑色请求等待获取排他锁。
2. 蓝色请求更新库存后释放排他锁，返回下单成功
3. 黑色请求获取排他锁，发现库存为 0，释放排他锁，返回下单失败

可以看到悲观锁成功解决了商品超卖问题，不过它的缺点也比较明显：1）处理性能不高，当一件商品有多位客户同时下单的时候，每个请求需要等待排他锁，也要较长才知道是否下单成功。2）容易发生死锁：在实际工程中，下单操作不只涉及了库存修改，还可能涉及其他业务功能，由于悲观锁下每个请求都轮流持有锁，应用层的代码处理不好的话会更容易发生死锁。  

**乐观锁**  
和悲观锁不同，乐观锁策略下事务会记录下查询时的版本号，当事务准备更新库存的时候，如果此时的版本号与查询时的版本号不同，则代表库存被其他事务修改了，这时候就会回滚事务，流程如下图：  
![](./电商乐观锁.svg)  
1. 蓝色请求与黑色请求查询库存，并记录库存版本号
2. 蓝色请求先更新库存为 0，返回下单成功
3. 黑色请求更新前发现版本与之前版本号不同，回归事务，返回下单失败

乐观锁因为并不需要等待锁，所以在事务竞争较少的情况下比悲观锁有更好的性能，缺点是事务竞争较多的情况下，由于经常需要回滚事务导致性能反而较差。  

**分布式锁**  
分布式锁在服务端以及数据库之间加上分布式组件来保证请求的并发安全，国内较常使用 Redis 或者 ZooKeeper。和悲观锁类似，每个请求需要先从组件中获取分布式锁之后才可以继续执行。流程如下图：  
![](./电商分布式锁.svg)  
1. 蓝色请求先获取分布式锁，查询和更新库存，在此期间黑色请求等待获取分布式锁
2. 蓝色请求更新库存后释放分布式锁，返回下单成功
3. 黑色请求获取分布式锁，查询库存，发现库存为 0，释放分布式锁，返回下单失败

分布式锁的优点是将功能进行分离，分布式组件负责解决并发安全的问题，数据库负责数据存储。不过缺点在于 1）分布式锁的正确实现并不简单，错误的实现方式容易引起其他一致性的问题。2）分布式锁在高并发下也会产生锁竞争的问题，性能不佳。3）由于引入了新的组件，要考虑分布式组件的可靠性，以及崩溃之后的恢复机制。  

**消息队列**  
另一个直观的解决方法就是使用消息队列，确保每个商品每个时刻只有一个请求，流程如下图：  
![](./电商消息队列.svg)  
1. 蓝色请求进入队列，黑色请求进入队列，数据库订阅下单请求
2. 数据库处理蓝色请求，蓝色请求查询和更新库存，返回下单成功
3. 数据库处理黑色请求，查询库存，发现库存为 0，返回下单失败

消息队列的优点对业务进行了解耦，除了数据库之外，其他对下单请求感兴趣的业务系统，例如数据分析，日志记录等都可以订阅下单请求的消息。缺点在于 1）因为消息队列可能会崩溃，消息发送也可能失败，所以要考虑消息只消费一次，不会因为重复消费导致重复下单。2）由于引入了新的组件，要考虑消息队列的可靠性，以及崩溃之后的恢复机制。  

对比两个方案的优缺点之后，队列服务更适合这里的电商系统，架构升级后，架构 2 如下：  
![](./电商架构2.svg)  
1. 客户端发送下单请求给服务端
2. 服务端将请求发送到消息队列
3. 数据库每次从消息队列取出请求
     * 若该商品库存大于零，将库存减一
     * 若该商品库存等于零的话，不做操作
4. 服务端根据消息队列里的消息状态返回下单结果

**从电商系统到秒杀系统**  
秒杀系统和电商系统有两个核心区别：  
1. 双十一也有极大的流量，但是双十一的商品种类很多，所以流量会分布到不同的商品中。而秒杀系统中，商品的种类和库存都比较少，导致大部分流量集中在少量商品中。
2. 秒杀系统由于商品稀缺，价值高。同一位客户可能会对同一商品多次提交下单请求，而且恶意刷单的请求比较多，所以系统接收到的无效请求及非法请求较多。

针对这两个区别，发现架构 2 有 3 个潜在问题：  
1. 当一款商品库存只有 10 件却有 1 万名用户下单的时候，只有前 10 名客户可以下单成功，其他用户都浪费时间在队列等待以及无意义地查询库存，既牺牲了用户体验也增加了消息队列以及数据库的压力。
2. 由于库存过少，有大量的请求（例如非法用户的请求，超过秒杀活动开始一定时间的请求）其实是没有机会抢到商品的，所以没有必要到达服务器，更不用说数据库了。
3. 大量的客户端在下单前同时请求同一个商品的秒杀页面，导致服务器压力骤升。

针对这三个问题可以考虑两个方案：流量控制和资源隔离。  

**流量限制**  
第三个问题相对简单，可以将秒杀页面使用 CDN 缓存起来，客户端就可以直接从 CDN 获取到秒杀页面，不需要重复请求服务器。另外两个问题可以通过流量限制来解决，可以通过限流器，负载均衡以及安全验证组件实现：  
* 限流器分为前端限流与后端限流：
  * 前端限流包括验证答题，防止重复点击按钮等常见机制。
  * 后端限流使用限流算法进行流量限制，简单情况下可以使用固定限流算法，例如秒杀商品的库存是 10 件，只要限流器接收到 10 * k（k 可以根据业务进行调整）个请求之后，就停止接受该商品的所有请求。这样无论有多少个下单请求，最终到达服务器的单个商品请求数量都不超过 10 * k。实际工程中，因为有客户可能会出现支付超时导致释放库存的情况，系统需要通知限流器接受新的请求。
* 负载均衡负责将下单请求通过负载均衡算法转发到最合适的服务器。
* 安全验证组件分为前端安全验证以及后端安全验证：
  * 后端安全验证包括黑名单校验，IP 地址校验等机制。
  * 前端安全验证包括：客户端账户验证（确保客户有资格参考秒杀活动），客户端版本安全验证（防止反编译以及修改客户端代码），秒杀接口动态生成（防止使用刷单脚本）等机制。

这时候系统的整体架构如下：  
![](./电商流量限制.svg)  

**热门资源隔离**  
既然大部分流量集中在少量商品中，能不能针对这些商品进行特殊处理呢？这样既可以防止秒杀活动影响其他业务功能，也可以针对热门商品进行资源分配，答案是可以的，首先我们需要识别出热门商品，这里有两种常见的方法：  
* 静态识别：包括京东在内的一些电商平台，客户在参加秒杀活动之前需要先进行预约，只有预约过的客户才能参考秒杀活动。这样系统可以提早识别热门商品以及进行流量预估。
* 动态识别：通过实时数据分析系统在秒杀活动前统计出现在较多客户浏览的热门商品，针对预估结果进行资源分配。

识别出热门商品之后，可以将热门商品的资源进行隔离，并且设置独立的策略，例如  
* 使用特殊的限流器，由于秒杀系统的库存很少，在下单请求开始阶段就可以随机丢弃大部分请求。
* 使用单独的数据库，在架构 2 中，下单请求的处理速度受限于消费者的处理速度，也就是数据库的处理速度。可以对热门商品进行分库分表，这样可以将请求处理的压力分摊到多个数据库中。下图中，将秒杀系统的一些组件独立开来：
![](./电商资源隔离.svg)  

根据以上两个方案，可以设计出最后的架构 3：  
![](./电商架构3.svg)  

1. 客户端从 CDN 获取到秒杀页面
2. 客户端发送下单请求给网关
3. 在网关或者服务器前进行流量控制以及负载均衡等策略
4. 服务端将请求发送到消息队列
5. 数据库每次从消息队列取出请求
     * 若该商品库存大于零，将库存减一
     * 若该商品库存等于零的话，不做操作
6. 服务端根据消息队列里的消息状态返回下单结果

</details>


<details>
<summary>设计 Twitter Search（搜索系统）</summary>

Twitter 是最大的社交网络服务之一，这里设计一个可以存储和搜索用户推文的服务 - 即推文搜索。  
  
1. 什么是推特搜索？
     * Twitter 用户可以随时更新他们的状态（对应 ElasticSearch 里的 Document）。每条状态（称为 tweet）由纯文本组成，目标是设计一个可以搜索所有用户 tweet 的系统。
2. 系统的要求和目标
     * 读比写多，虽然推特搜索场景要求实时搜索，但索引更新仍不是立刻发生的，有一定的时间延迟（虽然这个延迟时间非常短）类似于最终一致性。
     * 假设 Twitter 有 15 亿用户，每天有 8 亿活跃用户。平均而言，Twitter 每天有 4 亿条推文。一条推文的平均大小为 300 字节。假设每天会有 5 亿次搜索。搜索查询将由多个词与 AND/OR 组合而成。需要设计一个能够有效存储和查询推文的系统。
3. 容量估计和限制条件
     * 存储容量。由于每天有 4 亿条新推文，每条推文平均为 300B/字节，需要的总存储量将是 400 Million * 300B => 120GB/day
     * 每秒总存储量：120GB / 24hours / 3600sec ~= 1.38MB/second
4. 系统 API
     * 可以由 SOAP 或 REST API 来提供系统服务的功能；下面是搜索 API 的定义。
     * search(api_dev_key, search_terms, maximum_results_to_return, sort, page_token) 
       * 参数：
         * api_dev_key（string）- 一个注册账户的 API 开发者密钥。这将主要被用于根据分配的配额来节制客户端用户请求。
         * search_terms（string）- 一个包含搜索关键词的字符串。
         * maximum_results_to_return（number）- 要返回的推文的数量。
         * sort（number）- 可选的排序模式。最新的优先（0 - 默认），最佳匹配（1），最喜欢（2）。
         * page_token（string）- 这个 token 将指定应该被返回的结果的页面。
       * 返回：（JSON）一个包含符合搜索查询的推文列表信息的 JSON。每个结果条目可以有用户 ID 和姓名、推文文本、推文 ID、创建时间、点赞数量等。
       * 逻辑：（should follow high level design）
         * ![](./Twitter%20Search%20API%20Logic.png)
5. 高层设计
     * 在高层次上，需要将所有的状态存储在数据库中，同时建立一个索引服务，可以跟踪哪个词出现在哪条推文中。这个索引将帮助快速找到用户试图搜索的推文。![](./Twitter%20Search%20Status.png)
6. 组件细节设计
   1. 存储
      * 每天需要存储 120GB 的新数据。鉴于这个巨大的数据量，需要想出一个数据分区方案，将数据有效地分配到多个服务器上。如果对未来 5 年进行规划，将需要以下存储 120GB * 365天 * 5年 ~= 200TB。如果在任何时候都不希望超过 80% 的容量，大约需要 250TB 的总存储量。假设想为所有的推文保留一个额外的副本作为容错；那么，总存储需求将是 500TB。如果假设一台现代服务器可以存储 4TB 的数据，将需要 125 台这样的服务器来保存未来 5 年的所有所需数据。从一个简单的设计开始，将推文存储在一个 MySQL 数据库中。可以假设将推文存储在一个有两列的表中，TweetID 和 TweetText。假设根据 TweetID 来划分系统的数据。如果 TweetID 是全系统唯一的，可以定义一个哈希函数，将 TweetID 映射到可以存储该 Tweet 对象的存储服务器。
      * 怎样才能创建全系统唯一的 TweetID？如果每天都有 4 亿条新推文，那么 5 年内可以预计有多少推文对象 - 400 Million * 365天 * 5年 => 7300亿。这意味着需要一个 5 字节的数字来唯一地识别 TweetID。假设有一个服务，可以在需要存储一个对象时产生一个唯一的 TweetID（这里讨论的 TweetID 将与《设计 Twitter》中讨论的 TweetID 相似）。可以使用 TweetID 反馈给的哈希函数以找到存储服务器并将 Tweet 对象存储在那里。
   2. 索引
      * 索引应该是什么样子的？这里的索引又叫[反向索引](https://mednoter.com/inverted-index.html)（下面的延伸章节也有概略介绍）。由于 tweet 查询将由单词组成，因此需要建立反向索引，告诉系统哪个单词出现在哪个 tweet 对象中。首先估计一下系统的索引会有多大。如果想为所有的英语单词和一些著名的名词如人名、城市名等建立一个索引，假设有大约 30 万个英语单词和 20 万个名词，那么索引里将有 50 万个单词。假设一个词的平均长度是 5 个字符。如果在内存中保存索引，需要 2.5MB 的内存来存储所有的单词 = 500k * 5 => 2.5MB。假设，想在内存中保存仅过去 2 年的所有推文的索引，由于系统将在 5 年内得到 730 Billion 条推文，这将使系统在 2 年内得到 292 Billion 条推文。鉴于每个 TweetID 将是 5 个字节，需要多少内存来存储所有的 TweetID = 292 Billion * 5B => 1460 GB。因此，索引就像一个大的分布式哈希表，"键" 是单词，"值" 是包含该单词的所有推文的 TweetID 列表。假设每条推文中平均有 40 个词，由于不会对介词和其他小词如 'the'、'an'、'and' 等进行索引，假设每条推文中大约有 15 个词需要被索引。这意味着每个 TweetID 将在索引中被存储 15 次。因此，将需要总的内存来存储系统的索引为 (1460GB * 15) + 2.5MB ~= 21TB。假设一个高端服务器有 144GB 的内存，将需要 152 个这样的服务器来保存系统的索引。可以根据两个标准来分拣系统的数据：
        * 基于单词的分片。在建立索引时，将遍历一条推文中的所有单词，并计算每个单词的哈希值，以找到它将被索引的服务器。为了找到包含某个特定单词的所有推文，必须只查询包含这个单词的服务器。这种方法有几个问题：1. 如果一个词变得很热，怎么办？那么持有该词的服务器上就会有大量的查询，这种高负荷会影响系统的服务性能。2. 随着时间的推移，一些词与其他词相比，最终会存储大量的 TweetID，因此，在推文不断增长的同时，保持词的均匀分布是相当棘手的。为了从这些情况中恢复过来，必须重新划分数据或使用一致哈希。
        * 基于推文对象的分片。在存储时，将把 TweetID 传递给系统的哈希函数，以找到对应的索引服务器，并在该服务器上对该 Tweet 的所有单词进行索引。当查询一个特定的词时，必须查询所有的索引服务器，而每个索引服务器将返回一组 TweetID。会有一个中心服务器将汇总这些结果，将其返回给用户。![](./Twitter%20Search%20Index.png)
   3. 排名
      * 无论是专用系统的还是通用的搜索引擎，排名处理可以发生在接收搜索请求后以及返回结果之前，但是排名模型的训练可以是在此之前。具体可以看下面的延伸章节对 Blender 和 Earlybird 服务器的介绍。![](./Search%20Engine%20Ranker.png)![](./MLR%20Search%20Engine.png)
      * 也可以对关键词提前做好排名（在查询、搜索它们之前）- 如果想通过社交图谱的距离、受欢迎程度、相关性等对搜索结果进行排名：假设想通过流行度对推文进行排名，比如一条推文得到了多少个喜欢或评论等等。在这种情况下，排名算法可以计算出一个 "人气数字"（基于喜欢的数量等），并将其存储在索引中。在将结果返回给聚合器服务器之前，每个分区都可以根据这个流行数对结果进行排序。聚合器服务器将所有这些结果结合起来，根据流行数进行排序，并将最重要的结果发送给用户。
      * 如果是通用的搜索引擎则还会使用特定的排名算法，如[机器学习排名算法](https://en.wikipedia.org/wiki/Learning_to_rank) 或 [PageRank](https://en.wikipedia.org/wiki/PageRank)（*本质上是一种以网页之间的超链接个数和质量作为主要因素粗略地分析网页的重要性的算法。其基本假设是：更重要的页面往往更多地被其他页面引用，或称其他页面中会更多地加入通向该页面的超链接。其将从 A 页面到 B 页面的链接解释为 “A 页面给 B 页面投票”，并根据投票来源、甚至来源的来源和投票对象的等级来决定被投票页面的等级。简单的说，一个高等级的页面可以提升其他低等级的页面*）、又或[找到需要关注的指标，每个指标给定指定权重，然后计算一个综合分数再返回结果](https://www.google.com/search/howsearchworks/how-search-works/ranking-results/)（注意：由于最后结果，除了和被搜索内容有关，也会和搜索者相关，例如搜索者的地域与过往数据，所以也要结合搜索者的数据进行查询）等等。
7. 故障容错
     * 当一个索引服务器宕机时，会发生什么？可以在每个服务器上都有一个次要的副本，如果主服务器宕机，它可以在故障转移后接管控制权。主服务器和次服务器都将拥有相同的索引副本。如果主服务器和次服务器同时宕机怎么办？这时必须分配一个新的服务器并在其上重建相同的索引。怎样才能做到这一点呢？因为不知道这台服务器上保存了哪些词/推文。如果系统使用 "基于推文对象的分片"，那么粗暴的解决方案就是遍历整个数据库，并使用系统的哈希函数过滤推文 ID，以找出所有需要的推文，这些推文将被保存在这台服务器上，这么做是因为[一致性哈希](./一致性哈希.md)只是提供了推文 ID 到服务器编号的映射，没有提供反方向的服务器编号到其负责的推文集合的映射。这将是低效的，而且在服务器重建期间，系统将无法提供任何查询，因此用户也会错过一些本应可以看到的推文。
     * 如何才能有效地检索到索引服务器编号和所负责推文集合之间的映射？必须建立另一个索引 - 将所有的索引服务器编号映射到其负责的 TweetID 们（类似一致性哈希的反向索引）。系统的 Index-Builder 服务器可以保存这些信息。将需要建立一个 HashTable，其中的 "键" 是索引服务器的编号，"值" 将是一个 HashSet，包含所有保存在该索引服务器的 TweetID。请注意，将所有的 TweetID 保存在一个 HashSet 中：这将使系统能够快速地从索引中添加/删除 Tweet。因此，现在每当索引服务器需要重建自己时，它可以简单地要求索引生成器服务器提供它需要存储的所有推文，然后获取这些推文来建立索引。这种方法肯定会很快。另外还应该有一个索引生成器服务器的副本，以实现容错。
8. 缓存
     * 为了处理热门推文，可以在数据库前面引入一个缓冲区。可以使用 Memcached，它可以在内存中存储所有这些热门推文。应用服务器在访问后端数据库之前，可以快速检查缓存中是否有该推文。根据客户的使用模式，可以调整需要多少个缓存服务器。对于缓存的驱逐策略，最近使用最少的（LRU）似乎适合本系统。
9. 负载平衡
     * 可以在系统中的两个地方增加一个负载平衡层：1）在客户和应用服务器之间；2）在应用服务器和后端服务器之间。最初，可以采用一个简单的 Round Robin 方法，将传入的请求平均分配给后端服务器。这种方法很容易实现，而且不会引入任何开销。这种方法的另一个好处是，LB 将在轮询中把宕机的服务器剔除出去，并停止向其发送任何流量。Round Robin LB 的一个问题是它不会考虑到服务器的负载。如果一个服务器过载或缓慢，LB 不会停止向该服务器发送新请求。为了处理这个问题，可以放置一个更智能的 LB 解决方案，定期查询后端服务器的负载，并在此基础上调整流量。
    
### 延伸
**反向索引，且为什么称之为反向索引**  
谈论搜索引擎的索引时，会涉及到两个概念：正向索引 (forward index) 和反向索引 (inverted index)。  
* 正向索引：从文档到单词
* 反向索引：从词到文档

索引 (index) 从来都不是一个新发明，从 19 世纪的图书分类法，到 21 世纪的数据库索引，一直在用。而且当大家提起索引这个术语时，脑海中都是按 word -> document 方向来建立。反向索引，其实就是普通的索引。为什么有人仍然坚持创建出一个新的术语「反向索引」？  
> 索引是个很老的概念。但是搜索引擎横空出世之后，却把这个概念包装了一下，称之为「反向索引」。并不是有什么本质性的区别，乃是因为搜索引擎在创建会先解析文档的关键词，创建一个正向索引，然后再基于正向索引，把方向逆转过来，创建反向索引，仅此而已。  

**搜索引擎如何创建索引**  
如果要进一步理解正向索引，需要了结搜索引擎 analyzer 的基本工作步骤。所有的文档在建立索引之前都要经过一个 pipeline。  
![](./Search%20Engine%20Analyzer%20Pipeline.svg)  
1. character filter，过滤一些词，比如 html 标签。
2. tokenization。分词，转换时态，转换单复数等。
3. token filter。过滤一些token，比如 the，a，an，this，these 等[停用词](https://zh.wikipedia.org/zh-hans/%E5%81%9C%E7%94%A8%E8%AF%8D)。

最终 pipeline 的输出就是一个单词列表。猜测这个单词列表就用来创建正向索引。  
  
**Google or Bing**  
通用搜索引擎与专用系统搜索引擎会有所不同，其中还需要包括爬虫系统/写系统（在下一例题介绍）。另外用户搜索一些特定词时如天气、地理、算数等等特殊信息时，首页置顶会有专属区域直接给出相关针对性信息，再往下才是一系列匹配搜索的网页链接。这些特定词可以通过人工智能如 NLP 技术进行收集处理分析并最终按需生成它们的专属区信息展示。  
[更多关于谷歌搜索引擎系统设计的细节](https://book.douban.com/subject/24529132/)。  
  
**Twitter 搜索服务重构**  
Twitter 为重构其搜索服务而收购了 Summize 搜索引擎，其基础架构在前端使用 Ruby on Rails，在后端使用 MySQL（后来被替换为 Earlybird - 一个基于 Lucene 的实时反向索引）并在 MySQL 中构建了反向索引、利用 MySQL 的并发事务和 B 树数据结构来支持并发索引和搜索。而新的搜索服务会显示与特定用户最相关的搜索结果，所以搜索结果是个性化的，为了支持相关过滤和个性化，需要三种类型的信号：  
* 静态信号，在索引时添加
* 回响信号，随时间动态更新
* 有关搜索者的信息，在搜索时提供
  
将所有这些信号输入到索引系统需要更改 twitter 的 ingestion pipeline、Earlybird（反向索引）和 Blender（前端）。另外还创建了一个新的 Updater（更新器）组件，不断将回响信号推送到 Earlybird。在 ingestion pipeline 中，添加了一个管道阶段，该阶段使用静态信息注解推文，例如，有关用户的信息和推文文本的语言。然后将推文复制到 Earlybird 索引（实时），在那里扩展了 Lucene 的内部数据结构以支持对任意注解的动态更新。随着时间的推移，Updater 持续带来动态更新，例如用户与推文的交互。Earlybird 和 Updater 一起支持高且不规则的更新率，而无需锁定或减慢搜索速度。  
在查询时，Blender 服务器解析用户的查询请求并将其与用户的 social graph（社交图）一起传递到多个 Earlybird 服务器。这些服务器使用专门的排名功能，结合相关性信号和社交图来计算每个推文的针对该用户的（个性化）相关性分数。排名最高、最近的推文将返回到 Blender，在将结果返回给用户之前，它会合并并重新排列结果。  
![](./Twitter%20New%20Search%20System.png)  
  
移除重复的搜索结果  
（接近）重复的推文在 Twitter 搜索结果中通常不是特别有用。在流行、重要事件期间，即搜索对用户最重要时，相同、类似的推文数量会增加，即使这些重复的内容质量很高，用户还是会从更多样化的结果中更受益。为了删除重复，系统可使用基于 [MinHashing](https://en.wikipedia.org/wiki/MinHash) 的技术，其中每个推文计算多个签名，共享同一组签名的两个推文被视为重复。  
  
个性化  
Twitter 可以让用户通过选择要关注的感兴趣的帐户来对其进行个性化，比如搜索返回结果的排名功能会访问用户的社交图并在排名期间使用有关搜索者和推文作者之间关系的知识。尽管社交图会非常大，但 Twitter 会将其对用户有意义的内容部分压缩到一个布隆过滤器中，从而可以有效节省空间并进行恒定时间的集合成员操作。当 Earlybird 扫描候选搜索结果时，它使用推文作者在用户社交图中的存在作为其排名算法中的相关性信号。即使是很少关注或不关注账户的用户也可以受益于其他个性化机制：例如，使用该搜索者的首选语言和位置作为个性化所需的信号。  
  
搜索中的图像和视频  
搜索图像或视频时情况与推文有所不同。例如，同一张图片可能会被多次发布推文，每条推文都包含不同的关键词，这些关键词都描述了该图片。如果推文文本中的某个术语/关键词重复描述了图像，则图像很可能与该术语/关键词有关。  
  
索引延迟  
搜索系统的关键指标之一是索引延迟，即新信息在搜索索引中可用所需的时间。这个指标很重要，因为它决定了新结果出现的速度。并非所有搜索系统都需要快速更新其内容。例如，在仓库库存系统中，每天更新一次搜索索引可能是可以接受的。在 Twitter 这类社交网络系统上，用户总是查询正在发生的事情，所以实时搜索是必须的。  
  
**ElasticSearch**  
前面讲到 Twitter 使用 MySQL 和 Lucene 构建自己的搜索引擎，实际上 Lucene 还是一个库，必须要懂一点搜索引擎原理的人才能用的好，所以后来又有人基于 Lucene 进行封装，写出了 ElasticSearch 这一开源（分布式）搜索引擎。通过 ElasticSearch，开发者可以更容易、高效地搭建、自定义属于自己的高性能搜索服务（ElasticSearch 把操作都封装成了 HTTP 的 API，只要给 ElasticSearch 发送 HTTP 请求就行。并且 ElasticSearch 支持、实现了分布式以支持海量数据、跨区的场景）。  
ElasticSearch 类比关系型数据库：  
|SQL Database	|ElasticSearch  |
|---          |---            |
|Database     |Index          |
|Table	      |Type           |
|Row	        |Document       |
|Column	      |Field          |
|Schema	      |Mapping        |
|SQL	        |DSL            |

关于 Mapping：Mapping 主要用于定义索引（Index）的字段名称和数据类型以及倒排索引等相关配置，Mapping 可以系统自动推断生成，也可以由用户自己定义。  
关于 Type：Every Index can have multiple Types for example “user” and “blogpost”, and every Type could have its own field. A field define the name of the field itself and the type that could be for example text, numeric (integer, long, short…), keyword, array and many many others.（这里要注意 ES 新的版本已经准备移除 Type，可以认为以后一个 Index 一个 Type，上面的表格的这种类比就不太准确了）  
这里重点注意两个数据类型：  
- Text：存入 ElasticSearch 的时候默认情况下会进行分词，然后根据分词后的内容建立反向索引
- Keyword：不会进行分词，直接根据字符串内容建立反向索引，全文本匹配

关于 Document（文档）：文档是 ElasticSearch 中可被搜索的最小单位，文档由多个字段的值组成，通过序列化 JSON 格式保存在 ElasticSearch 中，每一个文档都有唯一的 ID。例如个人的简历、歌曲的详情等等都可以存储在文档中。  

ElasticSearch 内置分词器（Analyzer）有:  
* Standard Analyzer：默认分词器，按词切换，小写处理
* Simple Analyzer: 按照非字母切分，小写处理
* Stop Analyzer: 小写处理，停用词过滤
* Whitespace Analyzer：按照空格切分，不转换小写
* Keyword Analyzer: 不分词，直接将输入当作输出
* Patter Analyzer: 正则表达式分词
* Language Analyzer：提供 30 多种常见语言的分词器
* Customer Analyzer：自定义分词器

Index Template  
```json
{
  "index_patterns": ["test*"],  // 什么样的 index 会使用这个模板
  "order": 1,                   // 设置模板的优先级
  "settings": { 
    "number_of_shards": 1,      // shard 的数量
    "number_of_replicas": 2     // replication 的数量
  },
  "mappings": {
    "date_detection": false,   // 字符串的日期类型是否自动转换
    "numeric_detection": true  // 字符串的数字类型是否自动转换
  }
}
```

Mapping Example  
```json
// 该索引包含三个字段（field），注意这里 properties 之外已经没有了 type，是新的版本
// id，类型是 long，不支持索引搜索
// phone，类型是 keyword，对于值为空的情况可以使用 "NULL" 字符串来搜索
// name，类型是 text，并定义了索引级别，以及自定义的分词器
{
  "mappings" : {
      "properties" : {
        "id" : {
          "type" : "long",
          "index": false
        },
        "name" : {
          "type" : "text",
          "index_options": "positions",
          "copy_to": "fullName",
          "fields": {
            "english_comment":{
              "type": "text",
              "analyzer": "english",
              "search_analyzer": "english"
            }
          }
        },
        "phone" : {
          "type" : "keyword",
          "null_value": "NULL"
        }
      }
    }
}
```  
旧版本 create an Index, in ElasticSearch 5.6, of name products, with 2 types : item and item_price.  
```json
{
 "products": {
  "aliases": {},
  "mappings": {
   "item": {
    "properties": {
     "color": {
      "type": "text"
     },
     "country": {
      "type": "integer"
     },
     "country_name": {
      "type": "text"
     },
     "date": {
      "type": "date"
     },
     "model_id": {
      "type": "integer"
     },
     "name": {
      "type": "text"
     }
    }
   },
   "item_price": {
    "properties": {
     "currency": {
      "type": "text"
     },
     "model_discount": {
      "type": "float"
     },
     "model_id": {
      "type": "integer"
     },
     "price": {
      "type": "float"
     }
    }
   }
  },
  "settings": {
   "index": {
    "creation_date": "1506334542346",
    "number_of_shards": "5",
    "number_of_replicas": "1",
    "uuid": "VgmCm2CbQZyS0ZtDz4GpTA",
    "version": {
     "created": "5060199"
    },
    "provided_name": "products"
   }
  }
 }
}
```  
  
**Algolia**  
比较简单的系统还可以直接使用、集成已有的关于搜索 SaaS 服务，比如 Algolia 等等。  
  
**参考链接及延伸阅读**  
https://medium.com/airbnb-engineering/contextualizing-airbnb-by-building-knowledge-graph-b7077e268d5a  
https://blog.twitter.com/engineering/en_us/a/2011/the-engineering-behind-twitter-s-new-search-experience  
https://blog.twitter.com/engineering/en_us/topics/infrastructure/2020/reducing-search-indexing-latency-to-one-second  
https://zh.wikipedia.org/zh-hans/%E5%80%92%E6%8E%92%E7%B4%A2%E5%BC%95  
https://mednoter.com/inverted-index.html  
https://zhuanlan.zhihu.com/p/62892586  
https://zhuanlan.zhihu.com/p/104215274  
https://medium.com/expedia-group-tech/getting-started-with-elastic-search-6af62d7df8dd  
https://medium.com/@federicopanini/elasticsearch-6-0-removal-of-mapping-types-526a67ff772  
https://blog.csdn.net/m0_38030719/article/details/109103650  
https://www.youtube.com/watch?v=OnjmdpxpEv0&list=PLbhaS_83B97vSWVslD63vjIi5OTYmSVrk&index=58  
https://web.eecs.umich.edu/~nham/EECS398F19/  
https://en.wikipedia.org/wiki/Learning_to_rank  
https://medium.com/double-pointer/system-design-interview-search-engine-edb66b64fd5e  

</details>


<details>
<summary>设计（高频）Trading（交易）系统</summary>

高频交易是指从那些人们无法利用的极为短暂的市场变化中寻求获利的计算机化交易。比如，某种证券买入价和卖出价差价的微小变化，或者某只股票在不同交易所之间的微小价差。这种交易的速度如此之快，以至于有些交易机构将自己的服务器群组 (server farms) 安置到了离交易所的计算机很近的地方，以缩短交易指令到达交易所的距离。  

**高频交易系统定义**：1. 交易指令完全由电脑发送，对市场数据的响应延时在微秒级。2. 系统由专用的软硬件组成，研发时需要大量计算机专家级的工作。3. 系统的硬件需要放在离交易所主机很近的位置上，所谓 co-location，并且得到专门的准入许可证，交易指令直接发送至交易所（而不是通过券商中转）。符合以上三点的，就可以叫做高频交易系统。  
  
**重要的前置知识：[FIX 协议](./FIX协议.md)**  

参考资料（How do I design high-frequency trading systems and its architecture）：  
https://www.linkedin.com/pulse/how-do-i-design-high-frequency-trading-systems-its-part-silahian/  
https://www.linkedin.com/pulse/how-do-i-design-high-frequency-trading-systems-its-part-silahian-1/  
https://www.linkedin.com/pulse/how-do-i-design-high-frequency-trading-systems-its-part-silahian-2/  
https://www.onixs.biz/fix-protocol.html  
https://www.zhihu.com/question/19839828/answer/28434795  
  
为了拥有一个完整的系统，还需要涵盖其他部分，它们可能是像网络通信、交换机/路由器、专门的服务器、FPGA、系统操作的调整如内核绕过等。但是这里将更多地关注软件设计和它的架构。  
这类交易系统的最佳实践会根据不同的因素：如市场，合规性等等而可能会有所不同，但对于任何交易系统来说，一些基本要素将保持不变。  

### 系统要求与目标
* 功能性与非功能性需求
  * 能够处理大量的传入信息，对外部事件的响应时间，内部响应时间，以及提供最高吞吐量和最低延迟的能力。
  * 该系统必须能够处理多种策略，确保在增加更多策略时，系统不会表现不佳。
  * 来自不同场所的订单簿重建。如果以外汇市场为例，有大量不同的场所和 ECN，他们都使用不同的 API 和连接标准。系统必须能够处理这些不同的来源，并将它们汇总到一个内部的、定义明确的数据结构中。

### High Level Design
#### 系统组件设计
* Data Feed Handler
  * Feed Handler 是任何算法交易系统中最重要的组成部分之一。没有准确的市场数据，任何高频或算法策略都无法做出正确的决定。因此，这个模块的目标是捕捉来自不同场所的市场数据，以使每个策略，产生正确的决策，并保持场所的限价订单簿的代表性。
  * 对于每个市场场所，每秒可以收到大量的市场更新，对每个限价订单簿的内部表示应该相应地改变。
  * ![](./Trading%20System%20Data%20Feed.jpeg)
  * venues 和你的系统之间的联系通常是使用 FIX 协议，所以必须放置一个 FIX 引擎，以便与他们沟通（接收和发送消息），并提供核心基础设施，使用 FIX 协议接口和管理连接到 venues。接收信息将是接收市场更新，而发送则是发送由系统的策略产生的订单。实现 FIX 引擎有两种选择：定制的，或商业选择。要实现一个定制的 FIX 引擎，将需要投入许多的工时，但可以确保将优化通信的低延迟环境。像大型银行和机构这样的公司会更喜欢自己的 FIX 引擎，这样他们可以拥有整个代码的所有权。建议选择商业方案，因为已有太多好的选择，只需要插入他们的库就可以开始使用。请注意，除了网络和通信延迟外，还会有一个解码/解析延迟，所以也要照顾到它。解析是一种字符串操作，因此在 CPU 周期和内存管理方面非常昂贵。有一些 venues 也开始尝试新的协议，因为 FIX 对于现在的算法来说已经变得太慢。
* 限价订单簿（Limit Order Book）
  * 一旦系统在各 venues 之间有了连接，将需要更新他们报告的所有事件：订单更新/增加或删除（如果需要也可以进行交易）。对于收到的每个事件，系统将进入系统的内部订单簿（internal order book），并进行它需要的修改。通常情况下，所有 venues 都会使用唯一的标识符（EntryID）和更新类型（更新是指插入、更新或删除）来发送这些更新，所以你可以准确地复制他们的限价订单簿。
  * 限价订单簿的重建工作是如何进行的。![](./Trading%20System%20Limit%20Order%20Book%20Reconstruction.jpeg)
  * 一件重要的事情是要用什么类型的数据结构来保存订单簿数据。因为每秒钟可以得到几百万次的更新，所以数据结构应该有足够的速度来找到一个条目或删除它。目前最好的数据结构是使用普通数组：一个用于出价，一个用于要价，它们提供了最好的性能，但是要预先分配尽可能多的内存，因为动态分配是昂贵的。
    * 在系统初始化阶段，预先分配这些数组：bidsArray 和 askArrays，假设要存储一个 10 级深度的 book，因此需要预先分配 10 元素的 bid/ask 数组。而且要移动/替换元素，而不是删除和创建，因为数组的重分配过程会消耗太多时间。定义有 10 个元素的数组，系统将重复使用所有这些元素，而且不删除元素，而是清除它的值，一旦排序，就把它们送到数组的开头。[C++ 代码示例](./Trading%20System%20Order%20Pre-Allocate%20Add%20and%20Remove.jpeg)
* 订单管理系统（OMS）
  * 这个模块将根据你的策略产生的信号，管理所有发送到 venues 的订单。它将处理发送、取消和替换订单，以及访问已执行订单的信息，包括待定和未结订单。必须以一种非常有效和经济的方式发送这些订单，根据以下一个或多个因素对每个订单进行路由：信号策略、venues 成本、venues 之间的延迟、可用的最佳价格、每个 venues 上的股票或合约。此外还需要知道一个订单是：什么时候被 venues 拒绝或取消了、什么时候部分成交、什么时候完全填满，并因此根据收到的上述状态，订单管理系统执行不同的路径。
* 策略（决策器）
  * 作为整个系统的大脑，策略将从每个 venues 获取限价订单簿，并根据不同的参数和价值做出明确的决定。有些策略需要分析整个账面深度，有些则只分析账面价格的顶部，也就是最佳出价和最佳要价。在这里，可以应用几乎无限的策略类型，当然，要确保它们是有利可图的想法。一个可能是简单的延迟套利策略：每个 venues 在不同的时间收到市场信息，如果我们的系统可以足够快，可以利用这个价格差距。通常情况下，这些差异持续时间不超过 500 微秒，之后，所有市场参与者都会努力平衡自己，而且价格差距通常很小，但如果每天这样做几千次，每个交易日就会增加到几百万美元，每年几十亿美元。又或者在外汇市场的特殊情况下，大额订单可能导致货币A/货币B和货币A/货币C之间的差异略有偏差。即使只是偏离几分之一，如果速度够快，这也能带来数百万的利润。
* 仓位与风险管理模块
  * 策略发出的所有订单都应合并为头寸，这样就可以跟踪你的开仓/平仓订单，最重要的是，对市场的敞口如何。理想情况下，策略应该保持平稳的风险敞口，但在某些策略中，如做市商，可能允许有可控的风险敞口（如果持有库存）。从每个头寸或整体风险暴露到投资组合管理，风险模块是一个重要的部分，它将与你的策略互动，并将实时监控所有未结头寸和对市场的整体风险暴露。以下是一些常用的风险管理规则：
    * 仓位限制：控制指定工具的仓位上限，或指定产品的所有工具的仓位之和。
    * 单一订单限制：控制单一订单的数量上限。有时，控制单个订单的数量下限，这意味着你的订单数量必须是它的倍数。
    * 资金控制：控制所有头寸的保证金不超过账户的余额。
    * 非法价格检测：确保价格在合理的范围内，比如不超过价格限制，或不离当前价格太远。
    * 自我交易检测：确保来自不同策略的订单不会导致它们之间有任何交易的可能性。
    * 订单取消率：计算订单取消情况，并确保其不超过交易所的限制。
  * 另外，在这个模块中，你可能想分析策略或交易上的不同分配。已经有很多研究证明，拥有一个分配策略可以使你的收益波动性降低，如果出了问题，也是一个很好的保险。
* 监控系统
  * 由于正在建立一个完全自动化的系统，必须能够在几毫秒内打开和关闭仓位，必须确保适当的监测系统来控制整个操作。想象一下，如果人类意识到某些策略没有做它应该做的事，或者任何 venues 没有提供它应该提供的价格，会发生什么。当必须停止系统时，无法挽回的损失可能已经发生。这个人要花多少分钟来关闭系统？5分钟？1分钟？在这个时间范围内，可能有超过数千个错误的开仓订单。这就是为什么需要建立监控系统，以检查以下一些情况：
    * 整体 PnL：如果有，比方说，闪电般的崩溃，外面的系统必须能够关闭所有未结头寸并自行关闭。
    * venues 之间的连接：确保没有人被断开连接，启动到位的重新连接系统。
    * 监测延迟：假设一些开关开始出现故障，开始收到一些延迟的数据。直到开始分析一些日志，才会意识到这一点。需要监测各 venues 之间的延迟，以确保数据交付，并在出现任何问题时提醒我们。
  
#### 软件架构模式
当想把所有这些部件放在一起时，有趣的事情就开始了，在进程之间以尽可能低的延迟进行并发交互。  
基本架构看起来是这样的：  
![](./Trading%20System%20Architecture.jpeg)  

并发性是交易系统的关键，为了让并发正常工作，需要使用同步方法（并发访问内存中的数据）和适合低延迟需求的设计架构。这就是为什么需要谈论 "软件设计模式"，并选择最适合需求的模式。以下有几种选择筛选：  
* 观察者设计模式：其中一个被称为主体的对象维护一个被称为观察者的依赖者的列表，并将任何状态变化自动通知它们。
  * 通常情况下该模式适用，但如果有多个策略在同一个系统上运行，通知过程将被逐一处理。意思是说，观察者将首先通知 "策略 1"，进行计算，如果符合某些标准就发送订单，然后继续通知 "策略 2"，再次进行计算，看是否符合某些标准。这听起来像是一个顺序的过程。订单簿模块将是主体，而策略（决策器）是观察者。一旦任何价格发生变化，订单簿将发送通知，所有的策略都可以接收到它并采取行动。如果策略符合其特定标准，将触发订单到交易所。这个过程，即断言是否满足标准的过程，可能更复杂或更不复杂，因此可能需要更多或更少的时间。但这是一个串行过程，如果因为任何原因，"策略 1" 消耗几毫秒的时间来做一些复杂的计算，那么当 "策略 2" 得到通知时就太晚了，以此类推....。直到得到最后一个策略的通知，而这个策略已经在根据过去的信息做决定了。如果想在每个通知上抛出线程，或以异步方式进行，那样会更糟糕，实际开销将比按顺序做更差。**所以不能使用这种设计模式**。
* 信号和槽模式：用于对象或进程之间的通信。其底层实现类似于观察者模式，其概念是观察者可以发送包含事件信息的信号，这些信号可以被其他人使用称为槽的特殊函数接收。在 C++ 里类似的东西叫回调（函数指针），但信号/槽系统确保回调参数的类型正确性。
  * 因为这种模式也是由观察者模式衍生出来的，所以大同小异 -- 事实上**所有类型的事件/消息/信号模式都是观察者模式的一种，所以不适合系统的低延迟的目的**。
* 环形缓冲区模式（Ring Buffer Pattern）：现在越来越接近了。这种模式的性能非常有效，它在许多低级别的应用程序中都有实现。环形缓冲区是一个循环队列数据结构。它有一个先入先出（FIFO）的特性。它也有两个索引，表明你的进程可以从哪里读取，以及可以从哪里写入。因此，不会出现碰撞，这将转化为不需要同步。这种结构被称为 "无锁"，其性能超越了其他模式。![](./Ring%20Buffer%20Pattern.jpeg)
  * 该模式有被业界采用，比如 LMAX 公司的 disruptor。这种模式非常适合于需要管理串行数据的套接字通信，**所以也不适合这次的高频交易架构**。
* **忙碌/等待或旋转模式**：它没有被归类为一种模式，实际上，它被认为是 "反模式"，通常不被推荐。但是，当在设计低延迟系统时，并不需要关心它有多好，也不关心它是否遵循了良好的做法，因为只需要关心延迟问题。进程将在一个紧密的循环中等待着什么，而这个循环将消耗 100% 的 CPU 周期。在高频交易系统的案例中，系统将从限价订单模块中读取市场数据，如果满足某些策略标准，将发送特定订单来执行该交易。这是迄今为止，从其他模块获取数据的最快方式。但不仅如此，有了这样的过程，系统就可以避免缓存缺失和 CPU 的上下文切换。[C++ 代码示例](./Busy%20Wait%20or%20Spinning%20Pattern.jpeg)
  * 超低延迟系统的最佳方法。尽管这里专注于交易系统，但这也可以应用于任何低延迟系统：通信、音频、视频等。
  * 但是，并不是所有的东西都像它看起来那么好，忙碌/等待过程是很难设计的，而且对整体性能来说太危险了，因为它可能会占用整个 CPU 的功率，从而降低整个系统的性能。在系统中使用忙碌/等待的关键部分是使用指定的那些个 CPU 核心的线程集。这意味着对系统来说，只在一个 CPU 核心（可能是核心 1、2、3，等等）运行这个忙/等待进程。而且我们将能够 "钉住" 与我们拥有的 CPU 核数一样多的进程。如果不这样做，这些线程将使用、耗尽整个 CPU 的算力。使用这种类型的方法，线程模型、I/O 模型和内存管理应该被设计成相互协作，以实现最佳的整体性能。这违背了松散耦合的 OOP 概念，但为了避免动态多态性的运行时成本，这是必要的。仍然需要在需要的地方注意同步方法（锁），这里有一个参考方案 -- 在设计数据结构时，尽量减少使用的同步方式。

结论是，这部分是系统中最敏感的东西，使用**忙碌/等待或旋转模式**这种技术会给系统带来最好的延迟。  
  
架构草图（未定案）：  
![](./Tarding%20System%20Architecture%20Draft%20Diagram.png)  
  
#### 数据模型
应该与 C2C 的 ecommerce 类似：  
表设计：  
* User 表：ID, Balance, ...
* Order 表：OrderID, UserID, StockID(ProductID), Type/Action, Price, Status, ...
* Transaction 表：BuyOrderID, SellOrderID, ...
* Stock（Product）表：StockID(ProductID), StockName(ProductName), LastPrice(AvgPrice), ...
  
#### 进阶阅读
* [CME Group Trading Services](https://www.cmegroup.com/confluence/display/EPICSANDBOX/Trading+Services)  

</details>


<details>
<summary>设计 Web Crawler</summary>

参考来源：https://www.youtube.com/watch?v=_NyVaxEIYGo  

### Scenario 场景 + Service 服务
* 功能性要求
  * 只爬取/Crawl HTML 内容 - 延伸再包含图片、视频等多媒体内容
  * 只支持 HTTP 协议 - 延伸再包含 FTP 等其他协议
* 非功能性要求
  * Scalability - 爬取整个 web，将要获取 millions 文件
  * Extensibility - 最好模块化，容易拓展支持其他新的功能比如支持新的文件类型

### 带宽与存储
* Assumption
  * 30 billion 网页 (URL) - 30 天内爬完
  * 每个网页有 100 KB 大小（纯 HTML），另外还需要 0.5 KB 存元数据
* 存储估计
  * 30B * (100KB + 0.5KB) = 3.02PB
* 带宽估计
  * Crawl 速度 - 30B / (30 days * 86400 s/day) = 11574 pages/second
  * 下载带宽 - 11574 pages/second * (100KB + 0.5KB) / page = 1.16GB/s (i.e. 9.3Gbps)

### High Level Design
整个网络就像一张图，爬取的过程就如图的遍历过程。而首先需要给定一些起始爬取的点（seed URLs）  
然后执行以下步骤：  
1. Pick 一个 URL，爬取对应的网页
2. 处理下载下来的网页，比如存到数据库并添加索引
3. 从下载下来的网页里解析抽取未访问过的 URL，加到待爬取的队列里（URL Frontier）
4. 重新回到第 1 个步骤循环

![](./Web%20Crawler%20High%20Level%20Design.png)  

#### 文件去重
* 上面步骤的文件去重不是 URL 去重，相同的内容可能出现在不同的 URL 里面（比如一样的新闻被不同的网页转载）。
* 具体方法为使用文件指纹（哈希）- 为每个处理的文件的内容计算出一个 64-bit checksum（MD5 或 SHA），然后存入数据库，当下一次检查到爬取的新网页的文件指纹在数据库里已有，就不需要再重复地存该文件内容了。
  * 其他的哈希算法还有 Google 的 SimHash，该算法可以兼容内容有微小的不同（更模糊地对比）
* 存储空间 - 30B * 64bit = 240 GB（可以存到内存里面，也可以通过硬盘+缓存实现）

#### URL 过滤
* URL Filters
  * 黑名单
  * 机器人屏蔽 - 因为有的网站不希望被爬虫消耗资源。
    * 网站会在 robots.txt 文件里声明、定规则，哪些 URL 可爬取、哪些 URL 不可爬取。
    * 本地可以缓存一份 robots.txt 文件，这样可以减少重复地下载该文件
  * URL 地址去重
    * URL 标准化（比如大写变小写、相对路径变成绝对路径等等）
    * 可以使用 URL 指纹来去重

#### DNS Resolver
* DNS 解析
  * 把 URL 地址转换成服务器的 IP 地址以访问
* 如果有大量的 URL 需要去爬取，则 DNS 解析部分可能带来性能瓶颈，解决方案是在本地建立一个 DNS 服务器缓存以前的查询结果，这样就不用每次都去外部 DNS 解析器处查询，减少其压力。

#### Detailed Component Design
* Crawling Policies（爬取策略）
  * 4 个基本策略
    * 选择策略（互联网资源海量，因此要决定哪些内容先爬取哪些后爬取）
    * 重复访问策略（以前爬取过的也要重新访问检查是否有更新）
    * 礼貌性策略/规则（在爬取过程中不要让被爬取网站过载而宕机）- 爬虫比真人访问快，即使是单机也可以有多线程爬虫并发地对一个网站做请求，如此可能会造成目标网站过载、或触发网站 Rate Limiter 从而 IP 被列入网站的黑名单导致以后无法爬取。所以一般是尽量避免对同一个网站并发地发请求。同时两个请求之间也需要有一定的时间间隔，比如 1 秒或几秒。
    * 分布式爬取策略（分布式爬取该怎么合作爬取，并怎么设计才能保证上面那些规则）

**URL Frontier Design**  
* 选择策略
  * 通过决定 URL 的下载优先级实现
* 礼貌性策略
  * 同一时间内对同一个网站只保持一个链接请求
  * 对同一个网站的两次请求之间要有一定的时间间隔

URL Frontier 主要是存储一堆待访问的 URL。它有 2 个接口：  
1. 插入新的 URL 到队列里
2. 给下游爬取网页的爬虫 workers 提供目标 URL 以访问、下载

![](./Web%20Crawler%20URL%20Frontier.png)  

上图中 Front Queue 实现了选择策略，为 URL 优先级进行了排序（Prioritizer 根据 URL 重要性或上次被访问距今间隔时间等等来评定，然后根据评定的优先级插到对应的队列里面）。  
Back Queue 则实现了礼貌性策略，控制了对一个网站访问的频率（politeness router 协同一个 mapping table 把同一个网站 / Host Name 的子网页 / URL 只插入到特定的一个 Queue 中，比如 Amazon 的 URLs 只放进 B1、Facebook 的 URLs 只放进 B2 等等，然后后续的 politeness selector 协同一个以上次访问时间戳排序的堆 / heap 可以控制读取队列的速度）。  
![](./Web%20Crawler%20Mapping%20Table.png)  
  
#### Fault Tolerance（容错性） & Scalability（扩展性）
* 扩展性
  * Database sharding
  * Consistent hashing
* 容错性
  * Server replacement

</details>



<br />
  
  
# DDIA（Designing Data-Intensive Applications）
## 数据密集型应用组件
现今很多应用程序都是数据密集型（data-intensive）的，而非计算密集型（compute-intensive）的。因此 CPU 很少成为这类应用的瓶颈，更大的问题通常来自数据量、数据复杂性、以及数据的变更速度。  
数据密集型应用通常由标准组件构建而成，标准组件提供了很多通用的功能；例如，许多应用程序都需要：  
* 存储数据，以便自己或其他应用程序之后能再次找到（数据库 database）
* 记住开销昂贵操作的结果，加快读取速度（缓存 cache）
* 允许用户按关键字搜索数据，或以各种方式对数据进行过滤（搜索索引 search indexes）
* 向其他进程发送消息，进行异步处理（流处理 stream processing）
* 定期处理累积的大批量数据（批处理 batch processing）  
  
## 非功能性需求
* 可靠性（Reliability）意味着即使发生故障，系统也能正常工作。故障可能发生在硬件（通常是随机的和不相关的），软件（通常是系统性的Bug，很难处理），和人类（不可避免地时不时出错）。容错技术可以对终端用户隐藏某些类型的故障。
* 可扩展性（Scalability）意味着即使在负载增加的情况下也有保持性能的策略。为了讨论可扩展性，首先需要定量描述负载和性能的方法（SLO、SLA）。简要了解了推特主页时间线的例子，介绍描述负载的方法，并将响应时间百分位点（中位数 p50 以及 p95，p99 和 p999 等等）作为衡量性能的一种方式。在可扩展的系统中可以添加处理容量（processing capacity）以在高负载下保持可靠。（监控相关：Telemetry / 遥测技术，一般是指从物理网元或者虚拟网元上远程实时高速采集数据，实现对网络实时、高速和更精细的监控技术。相比于传统的网络监控技术如 CLI、SNMP 等拉模式，Telemetry 通过推模式，主动向采集器上推动数据信息，提供更实时更高速更精确的网络监控功能。[更多参考](https://www.sdnlab.com/23887.html)）
* 可维护性（Maintainability）有许多方面，但实质上是关于工程师和运维团队的生活质量的。良好的抽象可以帮助降低复杂度，并使系统易于修改和适应新的应用场景。良好的可操作性意味着对系统的健康状态具有良好的可见性，并拥有有效的管理手段。  
  
To Be Continue ...  

# 一些面试小技巧
## QPS 计算
* 1 million per day == 12 per second
* 100GB per day ~= 1MB per second

## Storage 计算
以下为常用存储单位，每个之间为 1024（~1000）倍的比例，比如 1KB = 1024B，如此类推（除了第一个 Byte 可缩写成 B，它代表、等于 8 个比特/Bit，Bit 可缩写成 b，Bit 即一位二进制位仅表示 0 或 1，为最小单元）  
|Name	|Equal To	|Size(In Bytes) |
|--   |--       |--             |
|Byte	|8 Bits	  |1              |
|KiloByte	|1024 Bytes	|1024     |
|MegaByte	|1,024 KiloBytes	|1,048,576  |
|GigaByte	|1,024 MegaBytes	|1,073,741,824  |
|TeraByte	|1,024 GigaBytes	|1,099,511,627,776  |
|PetaByte	|1,024 TeraBytes	|1,125,899,906,842,624  |
|ExaByte	|1,024 PetaBytes	|1,152,921,504,606,846,976  |
|ZettaByte	|1,024 ExaBytes	|1,180,591,620,717,411,303,424  |
|YottaByte	|1,024 ZettaBytes	|1,208,925,819,614,629,174,706,176  |

参考：https://www.geeksforgeeks.org/understanding-file-sizes-bytes-kb-mb-gb-tb-pb-eb-zb-yb/  

* 1 Million * 1B => 10^6 * 1B => 1MB
* 1000 Million * 1B => 1 Billion * 1B => 10^9 * 1B => 1 Million * 1KB => 1GB
* 1000 Billion * 1B => 1 Trillion * 1B => 10^12 * 1B => 1 Billion * 1KB => 1TB
* 1000 Trillion * 1B => 10^15 * 1B => 1 Trillion * 1KB => 1PB

### ID 设计
* [UUID 是 16B/字节](https://blog.csdn.net/qq_41655115/article/details/106885421)
* 8B/字节 ID（纯数字）可以表示 2^8^8 => 2^64 => 1.8*10^19 => 18 Quintillion
* 5B/字节 ID（纯数字）可以表示 2^8^5 => 2^40 => 10^12 => 1000 Billion => 10000 亿 => 1 兆
* 4B/字节 ID（纯数字）可以表示 2^8^4 => 2^32 => 4 Billion => 40 亿
* 3B/字节 ID（纯数字）可以表示 2^8^3 => 2^24 => 15 Million => 1500 万

## 数量单位中英对照
![](./数量单位中英对照.jpeg)  
