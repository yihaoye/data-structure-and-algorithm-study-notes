# Redis
参考：https://leetcode-cn.com/leetbook/detail/database-handbook/  
  
## Redis 缓存的特点
作为一个高性能的 Key-Value 数据库，Redis 与其他 Key-Value 缓存产品相比，有以下三个特点：  
* Redis 运行在内存中但是可以持久化到磁盘，重启的时候可以再次加载进行使用。
* Redis 不仅仅支持简单的 Key-Value 类型的数据，同时还提供 String，List，Set，Sorted Set，Hash 等数据结构的存储，Redis 还有更高级得数据结构比如：BitMap、HyperLogLog、Geo、BloomFilter 这几个数据结构。
* Redis 还支持数据的备份，即 Master-Slave 主从模式的数据备份。  

另外要注意 redis 的数据是 flat 的，比如不能 set 里又嵌套一个 set。  
如果需要存储复杂的嵌套结构，通常需要将嵌套的数据展开为单层结构，然后使用合适的数据类型来存储。以下是其中[两种可行的数据模型](https://redis.com/blog/real-time-trading-platform-with-redis-enterprise/)：  
* Using the HASH to model the entity structure, with each attribute of the entity being a field in the Redis HASH.
* Flatten the entity structure and to represent each entity attribute using a key identified by entity id — one such key for each attribute. The fields in each HASH will be the ID and a corresponding value.

## Redis 和 MySQL 的区别
* 类型上的不同。MySQL 是关系型数据库，采用表格的存储方式，数据以行列组织，并遵守了传统的数据库的 ACID 原则。Redis 是 NoSQL（非关系型数据库），不严格遵守 ACID 原则，其采用 key-value 存储组织数据，大量使用缓存来读写数据，周期性地把更新数据写入磁盘或在追加文件中写入修改操作，以及同步分布数据库等。这些特点为大大提高了 Redis 在极大并发量下的读写性能。由于不以表格方式组织数据，Redis 的数据扩展十分灵活方便。
* 功能不同。 MySQL 主要用于持久化的存储数据到硬盘，功能强大，但是读取速度较慢。而 Redis 将使用频繁的数据存储在缓存中，缓存的读取速度快，能够大大的提高运行效率，但是一般在使用中，Redis 缓存的数据保存时间是有限的。
* 运行机制不同。MySQL 作为典型的关系型数据库，为了严格遵守 ACID 规则必须采取保守的缓存策略，特别是为了保证数据的一致性，在保证数据可靠性的同时，牺牲了许多（特别是多并发情形下的）读写性能。而现在互联网许多应用场景往往面临了海量用户的访问请求，对数据库并不需要非常强的一致性 。如 Redis 等非关系型数据库（NoSQL），往往以不再满足 ACID 为代价求得更高的吞吐容量。NoSQL 通常采用极大的缓存、分布式服务来提高读写性能。  
  
## Redis 和 MongoDB 的区别
MongoDB 和 Redis 都是 NoSQL，采用结构型数据存储，二者之间的主要区别如下所示：  
* 性能方面，二者都很高，总体而言，TPS 方面 Redis 要大于 MongoDB；
* 可操作性上，MongoDB 支持丰富的数据表达、索引，最类似于关系数据库，支持丰富的查询语言，操作比 Redis 更为便利；
* 内存及存储方面，MongoDB 适合大数据量存储，依赖操作系统虚拟做内存管理，采用镜像文件存储，内存占有率比较高，Redis 2.0 后增加虚拟内存特性，突破物理内存限制，数据可以设置时效性；
* 对于数据持久化和数据恢复，MongoDB 1.8 后，采用 binlog 方式（同 MySQL）支持持久化，增加了可靠性，而 Redis 依赖快照进行持久化、AOF 增强可靠性，但是增强可靠性的同时，也会影响访问性能；
* 数据分析上，MongoDB 内置数据分析功能（mapreduce），而 Redis 不支持数据分析；
* 应用场景不同，MongoDB 适合海量数据，侧重于访问效率的提升，而 Redis 适合于较小数据量，侧重于性能。
  
## 将 Redis 作为持久化数据库使用
多数情况下不建议，如果一定要这样做，需要注意以下几点：
* 为 Redis 服务器配置足够大的内存容量，至少是比使用场景任意时刻可能的最大同时存储数据量更大的容量。同时应该设置一个合理的 maxmemory 值，确保有足够的空间来处理峰值负载。
* 设置合理的 TTL，比如每个、每种键应有合理的过期时间（有些可以不设置，只要不会持续增至超过内存容量或占用其他数据使用即可）。
* 合理的数据结构和键设计，使用合适的数据结构和键设计可以优化内存使用，减少不必要的内存压力。
* 内存策略合理设置，比如设置为 noeviction（不过通常只要内存仍有余量，则即使设置了 evict 策略如 LRU 等也不会实际触发 evict）。
* 持久化配置，如 RDB 和/或 AOF，主要为了避免意外宕机内存数据丢失，确保数据可以被可靠地保存和恢复，不过仍然可能丢失一小部分数据（定期备份、快照与实时数据有一定的间距时间）。
* 内存管理的改善，内存碎片整理，特别是在长时间运行后。又比如 BGSAVE 操作、持久化 AOF 重写过程可能会释放一些不再需要的内存页，尤其是在有大量过期或被覆盖的数据时，即进行内存重分配与紧凑化。
* Ops 操作：持续监控 Redis 的内存使用情况和性能指标、设置内存使用阈值警报以便在接近限制时及时响应、制定可靠的扩展策略（例如在需要时增加 Redis 实例或升级硬件）、在应用层实现适当的容错和重试机制，以应对可能的数据丢失情况。

但是内存比磁盘贵太多了，成本对比上通常每 GB 的价格是磁盘存储的 10-100 倍不等、而性能上内存的读写速度可以比磁盘快几个数量级。  
在许多情况下，纯粹依赖大容量内存可能不是最优解。更实际的方法是：仔细评估真正需要高性能访问的数据量，然后考虑混合存储策略，结合使用内存、SSD 和 HDD。  

## Redis 常用数据类型
![](./Redis-数据结构.webp)  
* String（字符串），是 Redis 最基本的数据类型，二进制安全的，可以包含任何数据，比如 JPG 图片或者序列化的对象，最大能存储 512 MB。在 Redis 中，STRING 用于存储三种类型的值：字节字符串值、整数值、浮点值。
* Hash（哈希），是一个键值对（key => value）集合，特别适合用于存储对象。可以有效地对单个字段进行读写操作，避免整个对象的序列化和反序列化开销。但是当 Hash 内部字段数量较少时，可能会造成内存浪费，因为 Hash 需要一定的额外空间来存储字段信息。
* List（列表），Redis 列表是简单的字符串列表，按照插入顺序排序，可以添加一个元素到列表的头部（左边）或者尾部（右边）。
* Set（集合），是 String 类型的无序集合，通过哈希表实现，添删查找操作的复杂度都是 O(1)。
* Sorted Set（有序集合），和 Set 一样也是 String 类型元素的集合，且不允许元素重复，不同的是每个元素都会关联一个 Double 类型的分数（可重复），通过此分数来为集合中的成员进行从小到大的排序（用于存储一组有序的元素，每个元素都带有权重）。  

[随着 Redis 版本的更新，后面又支持了四种数据类型：](https://www.xiaolincoding.com/redis/data_struct/command.html)
* BitMap，即位图，是一串连续的二进制数组（0 和 1），可以通过偏移量（offset）定位元素。本身是用 String 类型作为底层数据结构实现的一种统计二值状态的数据类型。
* HyperLogLog，是一种用于「统计基数」的数据集合类型，基数统计就是指统计一个集合中不重复的元素个数。但要 HyperLogLog 是基于概率的，所以有标准误算率约 0.81%。简单来说 HyperLogLog 提供不精确的去重计数。它的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的内存空间总是固定的、并且是很小的。
* GEO，主要用于存储地理位置信息，并对存储的信息进行操作。该类型使用 GeoHash 编码方法实现了经纬度到 Sorted Set 中元素权重分数的转换，这其中的两个关键机制就是「对二维地图做区间划分」和「对区间进行编码」。一组经纬度落在某个区间后，就用区间的编码值来表示，并把编码值作为 Sorted Set 元素的权重分数。
* Stream，专门为消息队列设计的数据类型。
  
### Redis 数据结构的实现
Redis 是基于内存存储实现的数据库，相对于数据存在磁盘的数据库，就省去磁盘磁盘 I/O 的消耗。  
![](./Redis-HashTable.webp)  

**SDS 简单动态字符串**  
```c
struct sdshdr { // SDS 简单动态字符串
   int len;    // 记录 buf 中已使用的空间
   int free;   // buf 中空闲空间长度
   char buf[]; // 存储的实际内容
}
```  
减少内存重新分配的次数：在 C 语言中，修改一个字符串，需要重新分配内存，修改越频繁，内存分配就越频繁，而分配内存是会消耗性能的。而在 Redis 中，SDS 提供了两种优化策略：空间预分配和惰性空间释放。  

**哈希**  
Redis 作为一个 KV 的内存数据库，它使用用一张全局的哈希来保存所有的键值对。这张哈希表，有多个哈希桶组成，哈希桶中的 entry 元素保存了 `*key` 和 `*value` 指针，其中 `*key` 指向了实际的键，`*value` 指向了实际的值。  

**数据编码**  
Redis 支持多种数据基本类型，每种基本类型对应不同的数据结构，每种数据结构对应不一样的编码。为了提高性能，Redis 设计者总结出，数据结构最适合的编码搭配。  
Redis 是使用对象（redisObject）来表示数据库中的键值，当在 Redis 中创建一个键值对时，至少创建两个对象，一个对象是用做键值对的键对象，另一个是键值对的值对象。  
```h
typedef struct redisObject {
   unsigned type:4;
   unsigned encoding:4;
   unsigned lru:22;        /* lru time (relative to server.lruclock) */
   int refcount;
   void *ptr;
} robj;
```  
redisObject中，type 对应的是对象类型，包含 String 对象、List 对象、Hash 对象、Set 对象、zset 对象。encoding 对应的是编码。  

以上转载自：https://juejin.cn/post/6978280894704386079  
  
## 如何实现 Redis 的定时机制
Redis 服务器是一个`事件驱动程序`，服务器需要处理以下两类事件：文件事件（服务器对套接字操作的抽象）和时间事件（服务器对定时操作的抽象）。Redis 的定时机制就是借助时间事件实现的。  
一个时间事件主要由以下三个属性组成：id，时间事件标识号；when，记录时间事件的到达时间；timeProc，时间事件处理器，当时间事件到达时，服务器就会调用相应的处理器来处理时间。一个时间事件根据时间事件处理器的返回值来判断是定时事件还是周期性事件。  
  
## TTL
在 Redis 中，TTL（Time To Live）是用来设置键（key）的生存时间的属性。通过设置 TTL，可以指定一个键在一定时间内保持有效，过了这个时间后，Redis 会自动将该键从数据库中删除。  
TTL 的工作方式如下：
1. 设置 TTL：当在 Redis 中创建一个键时，可以为该键设置一个 TTL。这个 TTL 可以是一个固定的时间（以秒为单位）或者一个 Unix 时间戳，表示该键的过期时间。
2. 定期检查：Redis 会定期检查所有键的 TTL，查看哪些键已经过期。这个检查是基于惰性删除（Lazy Expiration）的，即键只有在被访问时才会检查是否过期。过期的键不会立即被删除，而是在被访问时检查并删除。
3. 自动删除：当一个键的 TTL 过期后，Redis 会自动将该键从数据库中删除。删除操作是在键被访问时执行的，所以过期后的键可能会在访问时被删除，但在过期前仍然存在。

TTL 的应用场景包括：
- 缓存过期：在缓存中存储数据时，可以为数据设置 TTL，确保缓存中的数据不会长时间无效，从而避免过期数据的使用。
- 临时数据存储：有些数据只需要在一段时间内保持有效，例如验证码、临时会话等，可以使用 TTL 来管理这些临时数据。
- 统计数据：当需要定期更新统计数据时，可以使用  TTL来自动清除旧的统计数据，确保数据的及时更新。

需要注意的是，TTL 是一种近似的机制，不保证键会在 TTL 过期后立即被删除。此外，过多使用 TTL 可能会增加 Redis 的内存压力，因为过期键可能会一直存在于内存中，直到被访问并删除。  

## Pipeline
Redis Pipeline（管道）是一种将多个命令打包在一起发送给 Redis 服务器的机制。它可以 显著提高 客户端向 Redis 服务器发送命令的 性能，尤其是在需要执行大量命令的情况下。

原理:
1. 客户端不会立即向 Redis 服务器发送每个命令，而是将它们存储在一个缓冲区中。
2. 当缓冲区满或者达到一定的时间限制时，客户端会将缓冲区中的所有命令一次性发送给 Redis 服务器。
3. Redis 服务器批量执行缓冲区中的所有命令，并将结果一次性发送回客户端。

优势:
* 减少网络开销: 由于减少了客户端和服务器之间的往返次数，可以显著降低网络开销，从而提高性能。
* 降低 CPU 开销: 由于减少了客户端和服务器的命令解析和执行次数，可以降低 CPU 开销，提高资源利用率。
* 提高吞吐量: 在需要执行大量命令的情况下，Pipeline 可以显著提高吞吐量，即每秒处理的命令数量。

适用场景:
* 批量执行命令：例如，批量更新用户数据、批量读取缓存数据等。
* 高并发场景：例如，Web 应用中的缓存更新、消息队列等。

注意事项:
* Pipeline 中的命令必须是原子操作，不能包含跨事务的操作。
* Pipeline 中的命令不能包含需要立即返回结果的命令，例如 WATCH、MULTI、EXEC 等。
* 如果 Pipeline 中的命令出现错误，则整个 Pipeline 中的命令都将失败。

以上 by Gemini  

## 单线程 Redis 高效原理
虽然 Redis 文件事件处理器以单线程方式运行，但是通过使用 I/O 多路复用程序来监听多个套接字，文件事件处理器既实现了高性能的网络通信模型，又可以很好地与 Redis 服务器中其他同样以单线程运行的模块进行对接，这保持了 Redis 内部单线程设计的简单性。  
  
## Redis 数据淘汰策略
Redis 内存数据量达到一定限制的时候，就会实行数据淘汰策略（回收策略）。Redis 会根据 maxmemory-policy 配置策略，来决定具体的行为：  
* no-eviction：不删除策略，达到最大内存限制时刻，如果需要更多内存，直接返回错误信息；
* allkey-lru：从所有 Key 的哈希表（`server.db[i].dict`）中随机挑选多个 Key，然后在选到的 Key 中利用 lru 算法淘汰最近最少使用的数据；
* volatile-lru：从已设置过期时间的哈希表（`server.db[i].expires`）中随机挑选多个 Key，然后在选到的 Key 中用 lru 算法淘汰最近最少使用的数据；
* volatile-random：从已设置过期时间的哈希表（`server.db[i].expires`）中随机挑选 Key淘汰掉；
* allkey-random：从所有的 Key 的哈希表（`server.db[i].dict`）中随机挑选数据淘汰；
* volatile-ttl：从已设置过期时间的哈希表（`server.db[i].expires`）中随机挑选多个 Key，然后在选到的 Key 中选择剩余时间最短的数据淘汰掉。  
  
## 对 Redis 的理解
Redis，全称为 Remote Dictionary Server，本质上是一个 Key-Value 类型的内存数据库，整个数据库统统加载在内存当中进行操作，定期通过异步操作把数据库数据写入磁盘或把修改操作写入追加的记录文件，并且在此基础上实现 Master-Slave（主从）同步。它支持存储的 Value 类型多样，包括 String（字符串）、List（链表）、Set（集合）、zset（sorted set —— 有序集合）和 Hash（哈希类型），这些数据类型都支持 push/pop、add/remove 及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。  
Redis 的主要缺点是数据库容量受到物理内存的限制，不能用作海量数据的高性能读写，因此 Redis 适合的场景主要局限在较小数据量的高性能操作和运算上。  

## 批量加载
使用 Redis 协议批量写入数据。批量加载是将大量预先存在的数据加载到 Redis 的过程。  

## 原子操作
Redis 提供了一系列原子操作（Atomic Operations），这些操作是在单个命令中执行的，能够保证操作的原子性，即操作要么全部执行，要么全部不执行，不存在部分执行的情况。下面是一些常见的 Redis 原子操作：
1. **SETNX（SET if Not eXists）：** 将键设置为指定的值，但只有在键不存在时才执行操作。如果键已经存在，操作不执行。这是一个用于实现分布式锁的基本操作。
2. **INCR（Increment）和 DECR（Decrement）：** 对存储在键中的整数值进行增加或减少操作。这些操作是原子的，适用于计数器等场景。
3. **HSET（Hash Set）：** 设置哈希表中指定字段的值。如果字段不存在，创建一个新的字段。如果字段已经存在，值会被更新。
4. **HSETNX（Hash Set if Not eXists）：** 类似于 HSET，但只有在字段不存在时才执行操作。
5. **LPUSH（List Push）和 RPUSH（List Push）：** 在列表的头部或尾部插入一个或多个元素。这些操作可以用来构建队列或栈。
6. **LPOP（List Pop）和 RPOP（List Pop）：** 从列表的头部或尾部弹出一个元素并返回。这些操作可以用来处理队列或栈。
7. **SETBIT：** 在字符串值的指定偏移量上设置或清除位的值。这对于处理位图等数据结构非常有用。
8. **GETSET：** 获取键的旧值，并将键设置为新值。这可以在获取旧值的同时进行原子更新。
9. **MSET（Multi SET）和 MSETNX（Multi SET if Not eXists）：** 设置多个键值对。MSET 是原子的，而 MSETNX 只在所有键都不存在时才执行。
10. **INCRBY（Increment BY）和 DECRBY（Decrement BY）：** 类似于 INCR 和 DECR，但可以指定增加或减少的量。

这些原子操作允许在多个线程或客户端之间执行并发操作，同时保证数据的一致性和完整性。在编写具有高并发性要求的应用程序时，Redis 的原子操作能够帮助避免竞态条件和数据不一致的问题。  
Redis 的数据结构（string、list、set、zset、hash、bitmap、hyperloglog、stream 等）本身都是线程安全的。因为 Redis 是单线程执行命令的（主线程），不会同时处理两个命令，所以只要是原子命令（比如 INCR，LPUSH，SADD），天然就是并发安全的。多个客户端并发发送命令时，Redis 内部会顺序排队，一个个处理，不存在同时读写冲突。但是组合多个命令时（比如先 GET，再 INCR 根据 GET 的结果），中间是非原子的，需要手动处理。所以，Redis 才引入了事务（MULTI/EXEC）、乐观锁（WATCH）、还有 Lua 脚本 -- 这些可以看作单条命令，所以是并发安全的。  

## 第三方 API
一些库，比如 go-redis 还提供了[限流](https://redis.uptrace.dev/guide/go-redis-rate-limiting.html)以及 [Cuckoo Filter、Count-Min Sketch 与 Top-K 等高级算法功能的实现](https://redis.uptrace.dev/guide/bloom-cuckoo-count-min-top-k.html)。  

## 事务
Redis 事务通过 MULTI/EXEC 实现，但其实可以发现 Lua 脚本也同样可以实现事务且更强大方便。这是因为脚本功能是 Redis 2.6 才引入的，而事务功能则更早之前就存在了，所以 Redis 才会同时存在两种处理事务的方法。不过官方声明并不打算在短时间内就移除事务功能，因为事务提供了一种即使不使用脚本，也可以避免竞争条件的方法，而且事务本身的实现并不复杂。  
所以实际上如果需要事务，直接使用 Lua 脚本实现即可。  
参考：https://www.cnblogs.com/makemylife/p/17299566.html  
  
## 持久化
Redis 是一个内存存储系统，通常被用作缓存和快速数据存储，但它也提供了一些持久化选项，以便在重启或故障恢复时保留数据。以下是与 Redis 持久化相关的内容：
1. **RDB 持久化**：
   - Redis 使用 RDB（Redis DataBase）持久化来定期将数据快照写入磁盘。这个快照包含了某个时间点上的所有数据。
   - RDB 持久化是通过将内存中的数据定期保存到一个二进制文件（通常是一个 `.rdb` 文件）来实现的。
   - RDB 持久化通常用于备份数据，以便在重启时恢复数据，但它可能会导致一些数据丢失，因为数据只在特定时间点保存。
2. **AOF 持久化**：
   - Redis 还提供了 AOF（Append-Only File）持久化选项，它将每个写操作追加到一个文件中。这个文件包含了 Redis 服务器接收到的所有写命令。
   - AOF 文件以易于理解的文本格式存储写命令，因此它不会丢失数据。
   - AOF 持久化通常比 RDB 持久化更适合需要高数据可用性和不愿意丢失任何数据的场景。
3. **持久化配置**：
   - 可以在 Redis 配置文件中配置持久化选项。可以选择同时启用 RDB 和 AOF 持久化，也可以选择仅启用其中之一，具体取决于需求。
   - 可以配置 RDB 持久化的触发条件，例如在指定时间间隔内或在有一定数量的写操作后执行持久化。
   - 还可以配置 AOF 持久化的同步选项，例如每个写操作都同步到磁盘，或者定期同步。
4. **恢复和备份**：
   - 在数据丢失或服务器故障时，可以使用 RDB 或 AOF 文件来恢复数据。
   - 可以定期备份 RDB 文件或 AOF 文件，以确保数据的安全性和可恢复性。
5. **混合持久化**：
   - Redis 6.0 版本引入了混合持久化（Mixed Persistence）选项，允许同时使用 RDB 和 AOF 持久化。
   - 这种方式结合了 RDB 的性能和 AOF 的数据可用性。

**RDB vs AOF**  
紧凑性：由于 RDB 文件采用了二进制格式，并且只包含了某个时间点的数据快照，因此相比 AOF 文件，RDB 文件通常会更加紧凑，占用的存储空间更少。  
恢复速度快：由于 RDB 文件是一个二进制文件，读取和解析的速度相对较快，因此在恢复数据时通常会比 AOF 文件快速。  
备份和迁移：由于 RDB 文件是一个完整的数据快照，因此非常适用于备份和迁移操作。  
数据完整性：AOF 通常比 RDB 的数据完整性更可靠有保障。  

Redis 持久化选项的选择通常取决于应用程序的需求，包括对数据一致性、性能和可用性的要求。可以根据具体情况选择合适的持久化配置。  

## 哨兵机制
Redis 提供哨兵（Sentinel）机制，它的作用是实现主从节点故障转移。它会监测主节点是否存活，如果发现主节点挂了，它就会选举一个从节点切换为主节点，并且把新主节点的相关信息通知给从节点和客户端。  
为了减少误判的情况，哨兵在部署的时候不会只部署一个节点，而是用多个节点部署成哨兵集群（最少需要三台机器来部署哨兵集群），通过多个哨兵节点一起判断，就可以就可以避免单个哨兵因为自身网络状况不好，而误判主节点下线的情况。同时，多个哨兵的网络同时不稳定的概率较小，由它们一起做决策，误判率也能降低。  

## 架构
![](./Redis-Architecture.gif)  

# 其他高级用法
## Lua 脚本
Redis 支持 Lua 脚本，可以使用 Lua 脚本在 Redis 服务器端执行复杂的逻辑。这使得 Redis 可以用于更复杂的应用，例如数据分析、机器学习等。
```go
func main() {
    // 创建 Redis 客户端
    client := redis.NewClient(&redis.Options{
        Addr: "localhost:6379",
    })

    // 执行 Lua 脚本
    script := `
        local function calculate_average(key)
            local sum = 0
            local count = 0

            local values = redis.scan(key, "*", 0)
            for _, value in ipairs(values) do
                sum = sum + tonumber(value)
                count = count + 1
            end

            if count == 0 then
                return nil
            else
                return sum / count
            end
        end

        return calculate_average("user:scores")
    `

    result, err := client.Eval(script).Result()
    if err != nil {
        log.Fatal(err)
    }

    fmt.Println("Average score:", result)
}
```

## 延迟队列
Redis 的延迟队列功能可以用于在未来某个时间点执行任务。这对于需要异步执行任务的应用非常有用。
```go
func main() {
	// 创建 Redis 客户端
	client := redis.NewClient(&redis.Options{
		Addr: "localhost:6379",
	})

	// 添加任务到延迟队列
	err := client.ZAdd("delayed_queue", time.Now().Add(10*time.Second).Unix(), "send_email").Err()
	if err != nil {
		log.Fatal(err)
	}

	// 处理延迟队列中的任务
	for {
		// 获取当前时间戳
		now := time.Now().Unix()

		// 从延迟队列中获取分数小于或等于当前时间戳的任务
		score, task, err := client.ZRangeWithScores("delayed_queue", 0, 0, 0, 1).Result()
		if err != nil {
			log.Fatal(err)
		}

		if score != 0 {
			// 从延迟队列中移除任务
			err := client.ZRem("delayed_queue", score).Err()
			if err != nil {
				log.Fatal(err)
			}
			if task == "send_email" {
				sendEmail()
			} else {
				log.Printf("未知任务：%s", task)
			}
		}
		time.Sleep(1 * time.Second)
	}
}

func sendEmail() {
	// 模拟发送电子邮件
	fmt.Println("发送电子邮件...")
	time.Sleep(1 * time.Second)
	fmt.Println("电子邮件已发送。")
}
```

## 发布/订阅
Redis 的发布/订阅功能可以实现实时的消息通信。发布者可以将消息发布到一个频道，订阅者可以订阅该频道以接收消息。这种机制常用于构建聊天应用、实时数据推送等应用。
```go
func main() {
    // 创建 Redis 客户端
    client := redis.NewClient(&redis.Options{
        Addr: "localhost:6379",
    })

    // 发布消息
    err := client.Publish("chat:channel", "Hello, world!").Err()
    if err != nil {
        log.Fatal(err)
    }

    // 订阅消息
    pubsub := client.Subscribe("chat:channel")
    ch, err := pubsub.Channel()
    if err != nil {
        log.Fatal(err)
    }
    defer ch.Close()

    for msg := range ch.Messages() {
        fmt.Println("Received message:", msg.Payload)
    }
}
```

## 分布式锁
Redis 的分布式锁功能可以用于在多个 Redis 实例之间实现互斥访问。这对于需要协调多个节点操作的应用非常有用。
```go
func main() {
    // 创建 Redis 客户端
    client := redis.NewClient(&redis.Options{
        Addr: "localhost:6379",
    })

    // 获取锁
    lock, err := client.SetNX("my_lock", "1", 10*time.Second).Result()
    if err != nil {
        log.Fatal(err)
    }

    if lock {
        // 执行需要互斥访问的操作
        fmt.Println("Executing critical section")
        time.Sleep(2 * time.Second)

        // 释放锁
        err := client.Del("my_lock").Err()
        if err != nil {
            log.Fatal(err)
        }
        fmt.Println("Lock released")
    } else {
        fmt.Println("Failed to acquire lock")
    }
}
```

## 会话管理
Redis 可以用于存储用户会话信息，例如登录状态、购物车内容等。这使得 Redis 可以用于构建 Web 应用程序。
```go
func main() {
	// 创建 Redis 客户端
	client := redis.NewClient(&redis.Options{
		Addr: "localhost:6379",
	})

	// 创建会话存储
	store, err := redis.NewStore(client)
	if err != nil {
		log.Fatal(err)
	}

	// 创建新的会话
	session, err := store.New()
	if err != nil {
		log.Fatal(err)
	}

	// 设置会话值
	err = session.Set("user_id", 12345)
	if err != nil {
		log.Fatal(err)
	}

	// 保存会话
	err = session.Save()
	if err != nil {
		log.Fatal(err)
	}

	// 获取会话值
	user_id, err := session.Get("user_id")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("User ID:", user_id)

	// 销毁会话
	err = session.Destroy()
	if err != nil {
		log.Fatal(err)
	}
}
```

## 缓存预热
Redis 的缓存预热功能可以提前将数据加载到缓存中，以减少实际使用时的数据加载时间。这对于需要快速访问大量数据的应用非常有用。
```go
func main() {
    // 创建 Redis 客户端
    client := redis.NewClient(&redis.Options{
        Addr: "localhost:6379",
    })

    // 从数据库中加载数据
    data := load_data_from_database()

    // 将数据预热到缓存中
    err := client.Set("cached_data", data, 0).Err()
    if err != nil {
        log.Fatal(err)
    }
    fmt.Println("Data preheated successfully")
}

func load_data_from_database() []byte {
    // 模拟从数据库加载数据
    return []byte(`{"name": "John Doe", "age": 30, "city": "New York"}`)
}
```

## 地理空间数据
```go
func main() {
    // 创建 Redis 客户端
    client := redis.NewClient(&redis.Options{
        Addr: "localhost:6379",
    })

    // 添加地理空间数据
    err := client.GeoAdd("cities", 13.410340, 55.762330, "Copenhagen").Err()
    if err != nil {
        log.Fatal(err)
    }
    err = client.GeoAdd("cities", 48.856614, 2.352200, "Paris").Err()
    if err != nil {
        log.Fatal(err)
    }
    err = client.GeoAdd("cities", 51.507200, -0.127500, "London").Err()
    if err != nil {
        log.Fatal(err)
    }
    fmt.Println("GeoSpatial data added successfully")

    // 查询地理空间数据
    location := "London"
    radius := 50.0 // 公里
    members, err := client.GeoRadius("cities", location, radius).Result()
    if err != nil {
        log.Fatal(err)
    }
    for _, member := range members {
        fmt.Printf("City: %s, Distance: %f km\n", member.Name, member.Distance)
    }

    // 计算地理空间距离
    city1 := "Copenhagen"
    city2 := "Paris"
    distance, err := client.GeoDist("cities", city1, city2, "km").Result()
    if err != nil {
        log.Fatal(err)
    }
    fmt.Printf("Distance between %s and %s: %f km\n", city1, city2, distance)

    // 创建地理空间索引
    err := client.GeoIndex("cities", "latitude", "longitude").Err()
    if err != nil {
        log.Fatal(err)
    }
    fmt.Println("GeoSpatial index created successfully")

    // 查询地理空间索引
    bbox := [2]float64{12.0, 54.0} // 西北角坐标
    bbox := [2]float64{14.0, 56.0} // 东南角坐标
    members, err := client.GeoSearch("cities", bbox, "MATCH", "1000").Result()
    if err != nil {
        log.Fatal(err)
    }
    for _, member := range members {
        fmt.Printf("City: %s, Distance: %f km\n", member.Name, member.Distance)
    }
}
```



# 缓存三大故障

转载自：https://zhuanlan.zhihu.com/p/346651831  

Redis 作为目前使用最广泛的缓存，相信大家都不陌生。但是使用缓存并没有这么简单，还要考虑缓存雪崩，缓存击穿，缓存穿透的问题，什么是缓存雪崩，击穿，穿透呢，出现这些问题又怎么解决呢？  

## 缓存雪崩（Cache Avalanche）
什么是缓存雪崩？  
当某一个时刻出现大规模的缓存失效的情况，那么就会导致大量的请求直接打在数据库上面，导致数据库压力巨大，如果在高并发的情况下，可能瞬间就会导致数据库宕机。这时候如果运维马上又重启数据库，马上又会有新的流量把数据库打死。这就是缓存雪崩。  
![](./缓存雪崩.jpeg)  

分析：  
造成缓存雪崩的关键在于在同一时间大规模的 key 失效。为什么会出现这个问题呢，有 2 种可能
* 缓存宕机（Cache Crash）- Redis 宕机导致缓存不可用。
* 惊群效应（Thundering Herd Problem）- 可能是采用了相同的过期时间。

解决方案：  
1、在原有的失效时间上加上一个随机值，比如 1-5 分钟随机。这样就避免了因为采用相同的过期时间导致的缓存雪崩。  
  
如果真的发生了缓存雪崩，有没有什么兜底的措施？  
2、使用熔断机制。当流量到达一定的阈值时，就直接返回“系统拥挤”之类的提示，防止过多的请求打在数据库上。至少能保证一部分用户是可以正常使用，其他用户多刷新几次也能得到结果。  
3、提高数据库的容灾能力，可以使用分库分表，读写分离的策略。  
4、为了防止 Redis 宕机导致缓存雪崩的问题，可以搭建 Redis 集群，提高 Redis 的容灾性。  

## 缓存击穿（Cache Miss / Cache Breakdown）
什么是缓存击穿？  

其实跟缓存雪崩有点类似，缓存雪崩是大规模的 key 失效，而缓存击穿是一个热点的 Key，有大并发集中对其进行访问，突然间这个 Key 失效了，导致大并发全部打在数据库上，导致数据库压力剧增。这种现象就叫做缓存击穿。  

分析：  
关键在于某个热点的 key 失效了，导致大并发集中打在数据库上。所以要从两个方面解决，第一是否可以考虑热点 key 不设置过期时间，第二是否可以考虑降低打在数据库上的请求数量。  

解决方案：  
1、上面说过了，如果业务允许的话，对于热点的 key 可以设置永不过期的 key。  
2、使用互斥锁。如果缓存失效的情况，只有拿到锁才可以查询数据库，降低了在同一时刻打在数据库上的请求，防止数据库打死。当然这样会导致系统的性能变差。  

## 缓存穿透（Cache Penetration）
什么是缓存穿透？  
使用 Redis 大部分情况都是通过 Key 查询对应的值，假如发送的请求传进来的 key 是不存在 Redis 中的，那么就查不到缓存，查不到缓存就会去数据库查询。假如有大量这样的请求，这些请求像“穿透”了缓存一样直接打在数据库上，这种现象就叫做缓存穿透。  

分析：  
关键在于在 Redis 查不到 key 值，这和缓存击穿有根本的区别，区别在于缓存穿透的情况是传进来的 key 在 Redis 中是不存在的。假如有黑客传进大量的不存在的 key，那么大量的请求打在数据库上是很致命的问题，所以在日常开发中要对参数做好校验，一些非法的参数，不可能存在的 key 就直接返回错误提示，要对调用方保持这种“不信任”的心态。  
![](./缓存穿透%201.jpeg)  

解决方案：  
1、把无效的 Key 存进 Redis 中。如果 Redis 查不到数据，数据库也查不到，我们把这个 Key 值保存进 Redis，设置 value="null"，当下次再通过这个 Key 查询时就不需要再查询数据库。这种处理方式肯定是有问题的，假如传进来的这个不存在的 Key 值每次都是随机的，那存进 Redis 也没有意义。  
2、使用布隆过滤器。布隆过滤器的作用是某个 key 不存在，那么就一定不存在，它说某个 key 存在，那么很大可能是存在(存在一定的误判率)。于是我们可以在缓存之前再加一层布隆过滤器，在查询的时候先去布隆过滤器查询 key 是否存在，如果不存在就直接返回。  
![](./缓存穿透%202.jpeg)  

## 总结
这三个问题在使用 Redis 的时候是肯定会遇到的，而且是非常致命性的问题，所以在日常开发中一定要注意，每次使用 Redis 时，都要对其保持严谨的态度。还有一个需要注意的是要做好熔断，一旦出现缓存雪崩，击穿，穿透这种情况，至少还有熔断机制保护数据库不会被打死。  
![](./Cache-Penetration-and-Solution.png)  
![](./how-caches-can-go-wrong.png)  



# 使用 Redis 集群进行扩展
## 使用 Redis 集群进行横向扩展  
Redis 使用称为 Redis 集群的部署拓扑进行水平扩展。本主题将教如何在生产环境中设置、测试和操作 Redis 集群。将从最终用户的角度了解 Redis 集群的可用性和一致性特征。  
如果计划运行生产 Redis 集群部署或想要更好地了解 Redis 集群的内部工作原理，请参阅Redis 集群规范。要了解 Redis Enterprise 如何处理缩放，请参阅使用 Redis Enterprise 进行线性缩放。  

## Redis 集群 101
Redis Cluster 提供了一种运行 Redis 安装的方法，其中数据会自动跨多个 Redis 节点进行分片。Redis 集群还在分区期间提供一定程度的可用性——实际上，是在某些节点出现故障或无法通信时继续运行的能力。但是，如果发生较大的故障（例如，当大多数主节点不可用时），集群将变得不可用。  
因此，借助 Redis 集群，可以：  
* 在多个节点之间自动拆分数据集。
* 当节点的子集出现故障或无法与集群的其余部分通信时继续操作。

**Redis 集群 TCP 端口**  
每个 Redis 集群节点都需要两个打开的 TCP 连接：一个用于为客户端提供服务的 Redis TCP 端口，例如 6379，以及称为集群总线端口的第二个端口。默认情况下，集群总线端口设置为数据端口加 10000（如16379）；但是，可以在 cluster-port 配置中覆盖它。  
集群总线是一种使用二进制协议的节点到节点的通信通道，由于带宽和处理时间小，更适合节点之间交换信息。节点使用集群总线进行故障检测、配置更新、故障转移授权等。客户端不应该尝试与集群总线端口通信，而是使用 Redis 命令端口。但是，请确保在防火墙中打开这两个端口，否则 Redis 集群节点将不会无法通信。  
为了让 Redis 集群正常工作，需要为每个节点：  
1. 客户端通信端口（通常为 6379）用于与客户端通信，并向所有需要访问集群的客户端以及使用该客户端端口进行密钥迁移的所有其他集群节点开放。
2. 集群总线端口必须可以从所有其他集群节点访问。

如果不打开两个 TCP 端口，集群将不会按预期工作。  

**Redis 集群和 Docker**  
目前，Redis Cluster 不支持 NATted 环境和 IP 地址或 TCP 端口重新映射的一般环境。  
Docker 使用一种称为端口映射的技术：与程序认为使用的端口相比，在 Docker 容器内运行的程序可能会暴露出不同的端口。这对于在同一服务器上同时使用相同端口运行多个容器很有用。  
要使 Docker 兼容 Redis Cluster，需要使用 Docker 的主机网络模式。有关详细信息，请参阅 Docker 文档 --net=host 中的选项。  

**Redis 集群数据分片**  
Redis 集群不使用一致性哈希，而是使用一种不同形式的分片，其中每个键在概念上都是哈希槽的一部分。  
Redis 集群中有 16384 个哈希槽，要计算给定键的哈希槽，只需对键的 CRC16 取模 16384。  
Redis 集群中的每个节点都负责哈希槽的一个子集，因此，例如，可能有一个包含 3 个节点的集群，其中：  
* 节点 A 包含从 0 到 5500 的哈希槽。
* 节点 B 包含从 5501 到 11000 的哈希槽。
* 节点 C 包含从 11001 到 16383 的哈希槽。

这使得添加和删除集群节点变得容易。例如，如果想添加一个新的节点 D，需要将节点 A、B、C 的一些哈希槽移动到 D。同样，如果想从集群中删除节点 A，可以移动哈希槽由 A 提供给 B 和 C。一旦节点 A 为空，就可以将其从集群中完全移除。  
将哈希槽从一个节点移动到另一个节点不需要停止任何操作；因此，添加和删除节点，或更改节点持有的哈希槽的百分比，不需要停机。  
Redis Cluster 支持多键操作，只要单个命令执行（或整个事务，或 Lua 脚本执行）涉及的所有键都属于同一个哈希槽。用户可以使用称为散列标签的功能强制多个键成为同一散列槽的一部分。  
哈希标签记录在 Redis 集群规范中，但要点是如果键中 `{}` 括号之间有子字符串，则仅对字符串内部的内容进行哈希处理。例如，键 `user:{123}:profile` 和 `user:{123}:account` 保证在同一个散列槽中，因为它们共享相同的散列标签。因此，可以在同一个多键操作中对这两个键进行操作。  

**Redis 集群主从模型**  
为了在主节点子集出现故障或无法与大多数节点通信时保持可用，Redis 集群使用主副本模型，其中每个哈希槽都有 1 个（主节点本身）到 N 个副本（N-1 额外的副本节点）。  
在包含节点 A、B、C 的示例集群中，如果节点 B 发生故障，集群将无法继续，因为无法再提供 5501-11000 范围内的哈希槽。  
但是，在创建集群的时候（或者以后），我们给每个master添加一个replica节点，这样最终的集群就是由A、B、C为主节点，A1、B1、C1为master节点组成的。副本节点。这样，如果节点 B 发生故障，系统可以继续运行。  
节点 B1 复制 B，B 发生故障，集群将节点 B1 提升为新的主节点，并继续正常运行。  
但是需要注意的是，如果节点 B 和 B1 同时发生故障，Redis Cluster 将无法继续运行。  

**Redis 集群一致性保证**  
Redis Cluster 不保证强一致性。实际上，这意味着在某些情况下，Redis 集群可能会丢失系统向客户端确认的写入。  
Redis Cluster 会丢失写入的第一个原因是因为它使用异步复制。这意味着在写入期间会发生以下情况：  
* 客户发送消息给主节点 B。
* 主 B 回复 OK 给客户。
* 主节点 B 将写入传播到其副本 B1、B2 和 B3。

因此，B 在回复客户端之前不会等待 B1、B2、B3 的确认，因为这对 Redis 来说是一个令人望而却步的延迟惩罚，所以如果客户端写了一些东西，B 会确认写入，但在此之前崩溃能够将写入发送到它的副本，其中一个副本（没有收到写入）可以提升为主，永远失去写入。  
这与大多数配置为每秒将数据刷新到磁盘的数据库所发生的情况非常相似，因此根据过去不涉及分布式系统的传统数据库系统的经验，已经能够推断出这种情况。同样，可以通过强制数据库在回复客户端之前将数据刷新到磁盘来提高一致性，但这通常会导致性能极低。这相当于 Redis 集群中的同步复制。  
基本上，需要在性能和一致性之间进行权衡。  
Redis Cluster 在绝对需要时支持同步写入，通过 WAIT 命令实现。这使得丢失写入的可能性大大降低。但是，请注意，即使使用同步复制，Redis Cluster 也没有实现强一致性：在更复杂的故障场景下，总是有可能无法接收到写入的副本将被选为 master。  

还有另一个值得注意的场景，Redis 集群将丢失写入，这发生在网络分区期间，其中客户端与少数实例隔离，至少包括一个主实例。  
以 6 节点集群为例，由 A、B、C、A1、B1、C1 组成，有 3 个主节点和 3 个副本节点。还有一个客户端，称之为 Z1。  
发生分区后，有可能分区的一侧有 A、C、A1、B1、C1，另一侧有 B、Z1。  
Z1 仍然能够写入 B，B 将接受其写入。如果分区在很短的时间内恢复正常，集群将继续正常运行。但是，如果分区持续足够长的时间让 B1 在分区的多数端被提升为主节点，则 Z1 在此期间发送给 B 的写入将丢失。  

Note  
Z1 可以发送到 B 的写入量有一个最大窗口：如果经过足够的时间分区的多数方选择一个副本作为主节点，则少数方的每个主节点都将停止接受写入.  

这个时间量是 Redis Cluster 的一个非常重要的配置指令，称为节点超时时间。  
节点超时结束后，主节点被认为发生故障，可以由其副本之一替换。类似地，在节点超时过去但主节点无法感知大多数其他主节点的情况下，它会进入错误状态并停止接受写入。  

参考：https://redis.io/docs/management/scaling/  



# Redis 使用案例
转载自：https://twitter.com/alexxubyte/status/1610678713087295490/photo/1  
![](./Redis%20Use%20Cases.jpeg)  

Redis 不仅仅是缓存，还应用于以下场景：  
* Session - 可以使用 Redis 在不同的服务之间共享用户会话（Session）数据。如果需要存储多个 Session 的数据或者需要存储结构化 Session 数据，则建议使用哈希表。如果只需要简单存储 Session 数据、存储少量 Session 数据则可以使用字符串。
* Cache - 可以使用 Redis 来缓存对象或页面，尤其是热点数据。
* [Distributed Lock](../../../Leetcode%20Practices/system%20design/分布式锁.md) - 可以使用 Redis 字符串来获取分布式服务之间的锁。
* Counter - 基于字符串的计数器，可以计算文章的点赞数或阅读数。
* Rate Limiter - 可以对特定用户 IP 应用速率限制。
  * 需求记录较小、简单直观、空间成本低的实现是采用 List。
  * 需求记录较大（如指定时间设置更大的限流数额）时，实现建议采用 ZSet。
* Global ID Generator - 可以使用 Redis Int（基于字符串）作为全局 ID。
* Shopping Cart - 可以使用 Redis Hash 来表示购物车中的键值对。
* Calculate User Retention
  * 简单场景下可以用 Bitmap 来表示每天的用户登录，计算用户留存。
  * 数据量较大、考虑优化存储空间且不要求绝对精度的场景下可以使用 HyperLogLog。
* Message Queue - 可以将 List 用于消息队列。
  * 简单的易用的消息队列，例如将任务推送到工作队列，可以使用 List。
  * 高性能的消息队列，例如处理实时数据，则可以使用 Streams。
  * 具有持久化和多个消费者特性的消息队列，则可以使用 Streams。
  * 支持消息分组和过期的消息队列，则可以使用 Streams。
* Ranking - 可以使用 ZSet（跳表实现）对文章进行排序。
* Recommend - ZSet 可以用于实现推荐系统，例如个性化商品推荐、新闻推荐等。通过将用户与他们感兴趣的项目及其相关性（权重）存储在 ZSet 中，可以根据用户的兴趣为他们推荐相关项目。
* Real-Time Analytics - ZSet 可以用于存储和分析实时数据，例如股票价格、传感器数据等。通过将数据点和它们的时间戳（权重）存储在 ZSet 中，可以高效地分析数据的趋势和模式。
* Real-Time Geo Search and Update - 使用 Geo 数据类型；场景如地理围栏（创建地理围栏，并触发当用户进入或离开围栏时事件）、地理搜索（根据用户的地理位置进行搜索，例如查找附近的餐馆、商店或景点）、地理分析（分析地理数据，例如计算人口密度或绘制交通流量图）。
  * [通过 ZSet / Sorted Set 内置支持 GeoHash](https://redis.io/docs/latest/commands/geohash/)
