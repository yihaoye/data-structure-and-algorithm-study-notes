# Dynamo

## 目标
设计一个高度可用（即可靠）、高度可扩展、完全去中心化的分布式键值存储。

什么是 Dynamo？
Dynamo 是亚马逊为其内部使用而开发的一种高可用键值存储。许多亚马逊服务，如购物车、畅销书列表、销售排名、产品目录等，只需要对数据进行主键访问。一个多表的关系型数据库系统对于这类服务来说是一种过度，同时也会限制可扩展性和可用性。Dynamo 提供了一个灵活的设计，让应用程序选择他们想要的可用性和一致性水平。

## 背景
Dynamo - 不要与 DynamoDB 混淆，后者的设计灵感来自于 Dynamo - 是一个分布式键值存储系统，在大规模的情况下提供 "永远在线"（或高度可用）的体验。在 CAP 定理的术语中，Dynamo 属于 AP 系统的范畴（即可用和分区容忍），并且是以牺牲强一致性为代价来设计高可用性和分区容忍。将 Dynamo 设计成一个高可用系统的主要动机是观察到系统的可用性与所服务的客户数量直接相关。因此，主要目标是系统即使不完善，也应该对客户可用，因为它能带来更多的客户满意度。另一方面，不一致的问题可以在后台解决，而且大多数时候客户不会注意到它们。从这个核心原则出发，Dynamo 积极地对可用性进行了优化。

Dynamo 的设计具有很大的影响力，因为它启发了许多NoSQL数据库，如 Cassandra、Riak 和 Voldemort -- 更不用说亚马逊自己的 DynamoDB。

## 设计目标
如上所述，Dynamo 的主要目标是高度可用。下面是其其他设计目标的总结。

可扩展性。该系统应该是高度可扩展的。我们应该能够把一台机器扔进系统中，看到相应的改进。  
分散的。为了避免单点故障和性能瓶颈，不应该有任何中央/领导的过程。  
最终一致的。数据可以被乐观地复制，成为最终一致的。这意味着，与其为确保整个系统的数据正确性而产生写入时间成本（即强一致性），不如在其他时间（例如，在读取过程中）解决不一致的问题。最终的一致性被用来实现高可用性。  

![](./Dynamo%20High%20Level.png)  
分布式键值存储的高层次视图  

## Dynamo 的使用案例
默认情况下，Dynamo 是一个最终一致的数据库。因此，任何不关心强一致性的应用都可以利用 Dynamo。尽管 Dynamo 可以支持强一致性，但它会对性能产生影响。因此，如果强一致性是一个应用程序的要求，那么 Dynamo 可能不是一个好的选择。  

Dynamo 在亚马逊被用来管理那些对可靠性要求非常高的服务，并且需要严格控制可用性、一致性、成本效益和性能之间的权衡。亚马逊的平台有非常多样化的应用，有不同的存储要求。许多应用选择 Dynamo 是因为它可以灵活地选择适当的权衡，以最经济的方式实现高可用性和保证性能。  

亚马逊平台上的许多服务**只要求对数据存储的主键访问**。对于这样的服务，使用关系型数据库的常见模式会导致效率低下，并限制可扩展性和可用性。Dynamo 提供了一个简单的只有主键的接口，以满足这些应用程序的要求。  

## 系统应用程序接口
Dynamo 客户端使用 put() 和 get() 操作来写入和读取对应于指定键的数据。这个键唯一地标识了一个对象。  
* get(key) - get 操作找到与给定键相关的对象所在的节点，并返回一个单一的对象或一个具有冲突版本的对象列表以及一个上下文。上下文包含了关于对象的编码元数据，这些元数据对调用者没有意义，包括对象的版本等信息（下面会有更多的介绍）。
* put(key, context, object) - put 操作找到与给定键相关的对象应该被存储的节点，并将给定的对象写到磁盘上。上下文是一个值，通过 get 操作返回，然后通过 put 操作送回。上下文总是与对象一起存储，并像一个 cookie 一样用来验证在投放请求中提供的对象的有效性。

Dynamo 将对象和键都视为一个任意的字节数组（通常小于 1 MB）。它在键上应用 MD5 散列算法，生成一个 128 位的标识符，用来确定负责提供键的存储节点。  

<br />
<br />

在高层次上，Dynamo 是一个分布式哈希表（DHT），它在集群中进行复制，以实现高可用性和容错。  

## 介绍 Dynamo 的架构
Dynamo 的架构可以概括为以下几点（将在下面的课程中详细讨论所有这些概念）。  

### 数据分布
Dynamo 使用一致哈希法在节点间分配数据。一致性散列也使得从 Dynamo 集群中添加或删除节点变得容易。  

### 数据复制和一致性
数据是乐观地复制的，即 Dynamo 提供最终的一致性。  

### 处理临时故障
为了处理临时故障，Dynamo 将数据复制到系统中其他节点的模糊法定人数，而不是严格的多数法定人数。  

### 节点间通信和故障检测
Dynamo 的节点使用 gossip 协议来跟踪集群状态。  

### 高可用性
Dynamo 通过使用暗示交接使系统 "永远可写"（或高可用性）。  

### 冲突解决和处理永久性故障
由于没有写入时间保证节点在数值上达成一致，Dynamo 使用其他机制来解决潜在冲突。  

使用矢量时钟来跟踪值的历史，并在读取时调和不同的历史。  
在后台，dynamo 使用像 Merkle 树这样的反熵机制来处理永久性故障。  
后面逐一讨论这些概念。  



## 什么是数据分区？
将数据分布在一组节点上的行为被称为数据分区。当试图分配数据时，有两个挑战。
1. 如何知道某个特定的数据将被存储在哪个节点上？
2. 当添加或删除节点时，如何知道哪些数据将从现有的节点转移到新的节点上？此外，当节点加入或离开时，怎样才能将数据移动降到最低？

一个初级的方法将是使用一个合适的哈希函数，将数据键映射到一个数字。然后，通过对这个数字和服务器的总数应用模数来找到服务器。比如说  
![](./Data%20partitioning%20through%20simple%20hashing.png)    
通过简单的散列法进行数据分区  

上图中描述的方案解决了寻找存储/检索数据的服务器的问题。但是，当增加或删除一个服务器时，必须重新映射所有的键，并根据新的服务器数量移动数据，这将是一个完全混乱的过程  

Dynamo 使用一致性散列来解决这些问题。一致性散列算法帮助 Dynamo 将行映射到物理节点上，也确保在增加或删除服务器时，只有一小部分键会移动。  

### 一致性散列：Dynamo 的数据分布
一致性散列将集群管理的数据表示为一个环。环中的每个节点都被分配一个数据范围。Dynamo 使用一致散列算法来确定什么行被存储到什么节点。下面是一致散列环的一个例子。  
![](./Consistent%20Hashing%20ring.png)  
一致性散列环  

通过一致性散列，环被划分为更小的预定义范围。每个节点被分配到这些范围中的一个。在 Dynamo 的术语中，范围的开始被称为 token。这意味着每个节点将被分配一个令牌 token。分配给每个节点的范围是按以下方式计算的。  

范围开始： token 值  
范围结束： 下一个 token 值 - 1  

下面是上图中描述的四个节点的令牌和数据范围。  
![](./Servers%20and%20token.png)  


每当 Dynamo 为 put() 或 get() 请求提供服务时，它执行的第一个步骤是对键应用 MD5 散列算法。这个散列算法的输出决定了数据位于哪个范围内，因此，数据将被存储在哪个节点上。正如在上面看到的，Dynamo 的每个节点都应该存储一个固定范围的数据。因此，从数据键生成的哈希值得知数据将被存储在哪个节点上。下面是一个例子，显示了数据如何在一致哈希环上分布。  
![](./Distributing%20data%20on%20the%20consistent%20hashing%20ring.png)  
在一致散列环上分布数据  

当一个节点被添加或从环中移除时，上面描述的一致散列方案非常有效；因为在这些情况下，只有下一个节点会受到影响。例如，当一个节点被移除时，下一个节点就会对存储在出站节点上的所有键负责。然而，这种方案会导致数据和负载分布不均匀。Dynamo 在虚拟节点的帮助下解决了这些问题。  

### 虚拟节点
在任何分布式系统中添加和删除节点是很常见的。现有的节点可能会宕机，可能需要退役。同样地，新的节点可能会被添加到现有的集群中，以满足不断增长的需求。Dynamo 通过使用虚拟节点（或 Vnodes）有效地处理这些情况。  

正如在上面看到的，基本的一致性哈希算法为每个物理节点分配了一个令牌 token（或一个连续的哈希范围）。这是一种静态的范围划分，需要根据给定的节点数量来计算令牌。这种方案使得添加或替换一个节点成为一个低效的操作，因为在这种情况下，希望重新平衡并将数据分配给所有其他节点，会导致移动大量数据。下面是一些与手动和固定划分范围相关的潜在问题。  
* 增加或删除节点。增加或删除节点将导致重新计算令牌，对一个大型集群来说，会造成很大的管理开销。
* 热点。由于每个节点被分配一个大范围，如果数据分布不均匀，一些节点会成为热点。
* 节点重建。由于每个节点的数据被复制在固定数量的节点上（后面会讨论），当需要重建一个节点时，只有它的复制节点可以提供数据。这给复制节点带来了很大的压力，并可能导致服务下降。

为了处理这些问题，Dynamo 引入了一种新的方案给物理节点分配令牌 token：与其给一个节点分配一个令牌，不如将哈希范围划分为多个较小的范围，每个物理节点被分配到这些较小范围中的多个。这些子范围中的每一个都被称为一个 Vnode。有了 Vnodes，而不是一个节点只负责一个令牌，而是负责许多令牌（或子范围）。  
![](./Comparing%20Consistent%20Hashing%20ring%20with%20and%20without%20Vnodes.png)  
比较有 Vnodes 和无 Vnodes 的一致性哈希环  

实际上，Vnodes 是随机分布在集群中的，通常是不连续的，所以没有两个相邻的 Vnodes 被分配到同一个物理节点。此外，节点确实携带着其他节点的副本以实现容错。另外，由于集群中可能存在异质机器，一些服务器可能比其他服务器拥有更多的 Vnodes。下图显示了物理节点 A、B、C、D、E 是如何使用一致哈希环的 Vnodes 的。每个物理节点被分配一组 Vnodes，每个 Vnode 被复制一次。  
![](./Mapping%20Vnodes%20to%20physical%20nodes%20on%20a%20Consistent%20Hashing%20ring.png)  
将 Vnodes 映射到一致哈希环的物理节点上  

### Vnodes 的优势
Vnodes 有以下优点。  
1. Vnodes 通过将哈希范围划分为较小的子范围，帮助将负载更均匀地分布在集群上的物理节点上。这加快了添加或删除节点后的再平衡过程。当一个新的节点被添加时，它从现有的节点中接收许多 Vnodes 以保持集群的平衡。同样，当一个节点需要重建时，不是从固定数量的副本中获取数据，而是许多节点参与重建过程。
2. Vnodes 使得维护一个包含异构机器的集群变得更加容易。这意味着，有了 Vnodes，可以给强大的服务器分配较多的范围，给不太强大的服务器分配较少的范围。
3. 由于 Vnodes 有助于为每个物理节点分配较小的范围，因此出现热点的概率比基本的一致哈希方案要小得多，因为该方案每个节点使用一个大范围。



## 什么是乐观复制？
为了确保高可用性和耐久性，Dynamo 将每个数据项在多 (N) 个节点上复制，其中数值 N 为复制因子，可在 Dynamo 的每个实例中配置。每个键被分配给一个协调者节点（在哈希范围内排在第一位的节点），它首先在本地存储数据，然后将其复制到 N-1 顺时针复制到环上的后续节点。这导致每个节点拥有它和它的第 N 个节点之间的环上的区域第 N 个前辈之间的区域。这种复制是异步进行的（在后台），Dynamo 提供了一个最终一致的模型。这种复制技术被称为乐观复制，这意味着不保证复制在任何时候都是相同的。  
![](./Replication%20in%20consistent%20hashing.png)  
一致性散列中的复制  

Dynamo 的每个节点都作为不同范围数据的副本。由于 Dynamo 存储了 N 分布在不同节点上的数据副本，如果一个节点发生故障，其他副本可以响应对该范围数据的查询。如果客户端无法联系到协调者节点，它将请求发送到持有副本的节点。  

## 偏好列表
负责存储特定键的节点的列表被称为偏好列表。Dynamo 的设计是，系统中的每个节点都可以确定哪些节点应该在这个列表中的任何特定键（后面讨论）。这个列表包含超过 N 节点，以考虑故障和跳过环上的虚拟节点，这样列表中只包含不同的物理节点。  

## 宽松的法定人数 (Sloppy Quorum) 和临时故障处理
按照传统的法定人数方法（Quorum 算法），任何分布式系统在服务器故障或网络分区时都会变得不可用，即使在简单的故障条件下也会降低可用性。为了提高可用性，Dynamo 不执行严格的法定人数要求，而是使用一种叫做 "宽松法定人数" 的方法。通过这种方法，所有的读/写操作都在第一个 N 健康的节点上进行，而这些节点可能并不总是第一个 N 节点，而这些节点不一定是在一致散列环上顺时针移动时遇到的第一 N 个节点。  

考虑下图中给出的 Dynamo 配置的例子，N = 3。在这个例子中，如果服务器 1 在写操作过程中暂时宕机或无法到达，其数据现在将被存储在服务器 4 上。因此，Dynamo 将存储在故障节点（即服务器 1）上的副本转移到一致哈希环中没有该副本的下一个节点（即服务器 4）。这样做是为了避免由短期机器或网络故障引起的不可用性，并保持所需的可用性和耐久性保证。发送给服务器 4 的副本在其元数据中会有一个提示，表明哪个节点是该副本的预期接收者（在这种情况下，服务器 1）。收到提示副本的节点将把它们保存在一个单独的本地数据库中，并定期进行扫描。在检测到服务器 1 已经恢复后，服务器4将尝试将副本传递给服务器 1。一旦传送成功，服务器 4 可以从其本地存储中删除该对象，而不减少系统中的副本总数。  
![](./Sloppy%20quorum.png)  
宽松法定人数  

## Hinted Handoff
上面描述的提高可用性的有趣技巧被称为 Hinted handoff，即当一个节点无法访问时，另一个节点可以代表它接受写请求。然后，写内容被保存在本地缓冲区中，一旦目标节点可重新访问，就发送出去。这使得 Dynamo "永远可写"。因此，即使在只有一个节点在线的极端情况下，写请求仍然会被接受并最终被处理。  

主要的问题是，由于宽松法定人数不是严格的多数，数据可以而且会出现分歧，也就是说，对同一个 Key 的两个并发写入有可能被不重叠的节点集所接受。这意味着系统中可能存在针对同一 Key 的多个冲突值，在读取时可能得到陈旧或冲突的数据。Dynamo 允许这种情况，并使用矢量时钟解决这些冲突。  



## 矢量时钟和冲突数据
Dynamo 如何使用矢量时钟来跟踪数据历史？并在读取时调和不同的历史/版本？

正如上面所述，宽松法定人数意味着系统中可能存在针对同一键的多个冲突值，必须以某种方式加以解决。先了解一下这种情况是如何发生的。  

### 什么是时钟偏移？
在一台机器上，需要知道的是绝对时间或挂钟时间：假设对时间戳为 t1 的键 k 进行了一次写入，然后对时间戳为 t2 的 k 进行了另一次写入。由于 t2 > t1，第二次写入的时间肯定比第一次写入的时间新，因此数据库可以安全地覆盖原始值。  

在一个分布式系统中，这个假设并不成立。因为时钟偏移，即不同的时钟往往以不同的速度运行，所以不能假设节点 a 上的时间 t 发生在节点 b 上的时间 t+1 之前。最实用的帮助时钟同步的技术，如 NTP，仍然不能保证分布式系统中的每个时钟在任何时候都是同步的。因此，如果没有像 GPS 装置和原子钟这样的特殊硬件，仅仅使用挂钟的时间戳是不够的。  

### 什么是矢量时钟？
Dynamo 不采用严格的同步机制，而是使用一种叫做矢量时钟的东西，以捕捉同一对象的不同版本之间的因果关系。矢量时钟实际上是一个（节点, 计数器）对的列表。一个向量时钟与存储在 Dynamo 中的每个对象的每个版本相关联。可以通过检查一个对象的两个版本的向量时钟来确定它们是否在平行分支上或有因果顺序。如果第一个对象的时钟上的计数器小于或等于第二个时钟的所有节点，那么第一个对象是第二个对象的祖先，可以被覆盖。否则，这两个变化被认为是冲突的，需要调和。Dynamo 在读取时解决了这些冲突。通过一个例子来理解这一点。  
1. 服务器 A 接收到向键 k1 的一个写请求，其值为 foo。它给它分配的版本是 `[A:1]`。这个写被复制到服务器 B。
2. 服务器 A 接收到向键 k1 的一个写请求，其值为 bar。它给它分配的版本是 `[A:2]`。这个写也被复制到服务器 B。
3. 一个网络分区发生了。A 和 B 不能互相交谈。
4. 服务器 A 接收到向键 k1 的一个写请求，其值为 baz。它给它分配了一个 `[A:3]` 的版本。它不能把它复制到服务器 B，但它被存储在另一个服务器上的 hinted handoff 缓冲区。
5. 服务器 B 接收到向键 k1 的一个写请求，其值为 bax。它给它分配了一个 `[B:1]` 的版本。它不能把它复制到服务器 A，但它被存储在另一台服务器上的 hinted handoff 缓冲区。
6. 网络恢复了。服务器 A 和 B 可以再次相互交谈。
7. 任何一个服务器都会收到一个对键 k1 的读取请求。它看到相同的键有不同的版本 (`[A:3]`) 和 (`[A:2]`, `[B:1]`)，但它不知道哪一个是较新的。它返回这两个版本，并告诉客户端找出版本并将较新的版本写回系统中。

![](./Conflict%20Resolution%20Using%20Vector%20Clocks.png)  

### 使用矢量时钟解决冲突
正如在上面的例子中所看到的，大多数时候，新版本会取代以前的版本，系统本身可以确定正确的版本（例如，`[A:2]` 比 `[A:1]` 新）。然而，版本分支可能发生在故障与并发更新相结合的情况下，导致一个对象的版本冲突。在这些情况下，系统无法调和同一对象的多个版本，客户端必须执行调和，将数据演化的多个分支折叠成一个（这个过程称为语义调和）。折叠操作的一个典型例子是 "合并" 一个客户的购物车的不同版本。使用这种调和机制，一个添加操作（即向购物车添加一个项目）永远不会丢失。然而，被删除的项目可以重新出现。  

解决冲突类似于 Git 的工作方式。如果 Git 可以将不同的版本合并成一个，那么合并就会自动完成。如果不能，客户（即开发者）必须手动协调冲突。  

当向量钟增长过大时，Dynamo 会截断向量钟（最老的先）。如果 Dynamo 最终删除了调节对象状态所需的较早的向量时钟，Dynamo 将无法实现最终的一致性。Dynamo 的作者注意到这是一个潜在的问题，但没有说明如何解决这个问题。他们确实提到，这个问题还没有在他们的任何生产系统中出现。  


## 无冲突复制的数据类型（CRDTs）
处理冲突的一个更直接的方法是通过使用 CRDTs (Conflict-free replicated data types)。为了利用 CRDTs，需要对数据进行建模，使并发的变化可以以任何顺序应用于数据，并产生相同的最终结果。这样一来，系统就不需要担心任何排序保证。亚马逊的购物车是 CRDT 的一个很好的例子。当用户向购物车添加两件物品（A 和 B）时，添加 A 和 B 的这两个操作可以在任何节点上以任何顺序进行，因为最终的结果是这两件物品被添加到购物车中。(从购物车中删除被模拟为负添加)。任何两个收到相同更新的节点都会看到相同的最终结果，这个想法被称为强最终一致性。Riak 有一些内置的 CRDTs。  

## 最后写入胜利 (LWW)
不幸的是，将数据建模为 CRDTs 并不容易。在许多情况下，这需要许多的投入。因此，具有客户端分辨率的矢量时钟被认为足够好。  

除了矢量时钟，Dynamo 也提供了在服务器端自动解决冲突的方法。Dynamo（和 Apache Cassandra）经常使用一个简单的冲突解决策略：基于挂钟的时间戳的最后写入赢（LWW）。LWW 很容易导致数据丢失。例如，如果两个冲突的写入同时发生，就相当于抛硬币决定扔掉哪个写入。  


## Dynamo put() & get() 操作生命周期
### 选择协调节点的策略
Dynamo 客户端可以使用以下两种策略中的一种，为他们的 get() 和 put() 请求选择一个节点。  
* 客户端可以通过一个通用的负载均衡器路由他们的请求。
* 客户端可以使用一个分区感知的客户端库，将请求路由到延迟较低的适当协调器节点。
  
在第一种情况下，负载均衡器决定请求将被路由到哪里，而在第二种策略中，客户端选择连接的节点。这两种方法都有各自的好处。  

在第一种策略中，客户端不知道 Dynamo 环，这有助于扩展性，并使 Dynamo 的架构松散耦合。然而，在这种情况下，由于负载均衡器可以将请求转发给环中的任意节点，它所选择的节点有可能不在偏好列表中。这将导致一个额外的跳数，因为请求将被中间节点转发到偏好列表中的一个节点。  

第二种策略有助于实现更低的延迟，因为在这种情况下，客户端维护一个环形副本，并将请求转发到偏好列表中的适当节点。因为这个选项，Dynamo 也被称为零跳 DHT，因为客户端可以直接连接有目标数据的节点。然而，在这种情况下，Dynamo 对负载分配和请求处理没有太多的控制。  

![](./How%20Clients%20connect%20to%20Dynamo.png)  



## 一致性协议
Dynamo 使用一个类似于法定人数系统的一致性协议 (Quorum Systems)。如果 R/W 是必须分别参与成功的读/写操作的最小节点数。  
* 那么 R + W > N 就会产生一个类似于法定人数的系统
* 一个共同的（N, R, W) 配置，Dynamo 使用的是 (3, 2, 2)。
  * (3, 3, 1): 快 W，慢 R，不持久化
  * (3, 1, 3): 快 R，慢 W，持久化
* 在这个模型中，get() 或 put() 操作的延迟取决于最慢的副本。由于这个原因，R 和 W 通常被配置为小于 N 以提供更好的延时。
* 一般来说，W 的低值和 R 的低值会增加不一致的风险，因为即使大多数副本没有处理，写请求也会被视为成功并返回给客户端。这也为持久性引入了一个脆弱的窗口，即使只在少数节点上被持久化，写请求仍被成功返回给客户端。
* 对于读和写操作，请求被转发到第一个 N 健康的节点。

### put() 过程
Dynamo 的 put() 请求将经历以下步骤：  
1. 协调器生成一个新的数据版本和矢量时钟组件。
2. 将新数据保存在本地。
3. 从偏好列表中向排名最高 N-1 的健康节点发送写请求。
4. 在收到 W-1 确认后，put() 操作可被认为是成功了。

### get() 过程
Dynamo 的 get() 请求将经历以下步骤：  
1. 协调器从偏好列表中的 N-1 个排名最高的健康节点请求数据版本。
2. 等待直到 R-1 回应。
3. 协调员通过一个矢量时钟处理因果数据版本。
4. 将所有相关的数据版本返回给调用者。

## 通过状态机处理请求
每个客户端请求都会在接收客户端请求的节点上创建一个状态机。状态机包含用于识别负责 Key 的节点、发送请求、等待响应、可能进行重试、处理响应以及为客户端打包响应的所有逻辑。每个状态机实例只处理一个客户端请求。例如，读取操作实现以下状态机：
1. 向节点发送读取请求。
2. 等待所需响应的最少数量。
3. 如果在给定的时间限制内收到的回复太少，则请求失败。
4. 否则，收集所有数据版本并确定要返回的数据版本。
5. 如果启用了版本控制，则执行语法协调并生成一个不透明的写入上下文，其中包含包含所有剩余版本的向量时钟。

在读取响应返回给调用者后，状态机会等待一小段时间以接收任何未完成的响应。如果在任何响应中返回过时版本，协调器将使用最新版本更新这些节点。此过程称为读取修复，因为它修复错过了最近更新的副本。  

如上所述，put() 请求由首选项列表中的前 N ​​个节点之一协调。尽管总是希望前 N 个节点中的第一个节点来协调写入，从而在一个位置序列化所有写入，但这种方法导致 Dynamo 的负载分布不均匀。这是因为请求负载不是在对象之间均匀分布的。为了解决这个问题，允许首选项列表中的前 N ​​个节点中的任何一个来协调写入。特别是，由于每个写操作通常都在读操作之后，因此写操作的协调器被选为对前一个读操作响应最快的节点，该节点存储在请求的上下文信息中。这种优化使 Dynamo 能够选择具有由先前读取操作读取的数据的节点，从而增加获得 "read-your-writes" 一致性的机会。  


# Dynamo: Amazon’s Highly Available Key-value Store
[Original Paper](./amazon-dynamo-sosp2007.pdf)  

## 重点
### 简介
* the reliability and scalability of a system is dependent on how its application state is managed.
  * Amazon uses a highly decentralized, loosely coupled, service oriented architecture consisting of hundreds of services. In this environment there is a particular need for storage technologies that are always available. 
* Dynamo is used to manage the state of services that have very high reliability requirements and need tight control over the tradeoffs between availability, consistency, cost-effectiveness and performance. 
  * Amazon’s platform has a very diverse set of applications with different storage requirements. A select set of applications requires a storage technology that is flexible enough to let application designers configure their data store appropriately based on these tradeoffs to achieve high availability and guaranteed performance in the most cost effective manner. 
* Some service only need primary-key access to a data store, the common pattern of using a relational database would lead to inefficiencies and limit scale and availability.
* With Dynamo, the consistency among replicas during updates is maintained by a quorum-like technique and a decentralized replica synchronization protocol (gossip based distributed failure detection and membership protocol).
* The main contribution of this work for the research community is the evaluation of how different techniques can be combined to provide a single highly-available system.

### 背景
* Traditionally production systems store their state in relational databases. For many of the more common usage patterns of state persistence, however, a relational database is a solution that is far from ideal. Most of these services only store and retrieve data by primary key and do not require the complex querying and management functionality offered by an RDBMS.
#### System Assumptions and Requirements
* Query Model: simple read and write operations to a data item that is uniquely identified by a key. State is stored as binary objects (i.e., blobs) identified by unique keys. 
  * Dynamo targets applications that need to store objects that are relatively small (usually less than 1 MB). 
* ACID: Dynamo does not provide any isolation guarantees and permits only single key updates. 
#### Service Level Agreements (SLA)
* To guarantee that the application can deliver its functionality in a bounded time, each and every dependency in the platform needs to deliver its functionality with even tighter bounds. Clients and services engage in a Service Level Agreement (SLA), a formally negotiated contract where a client and a service agree on several system-related characteristics. 
* In Amazon’s decentralized service oriented infrastructure, SLAs play an important role. For example a page request to one of the e-commerce sites typically requires the rendering engine to construct its response by sending requests to over 150 services. These services often have multiple dependencies, which frequently are other services ![](./Service-oriented%20architecture%20of%20Amazon's%20platform.png)  
#### Design Considerations


