Data partitioning (aka sharding) is a technique to break up a big database into many smaller parts. It improve the manageability, performance, availability, load balancing of an application and less scale cost.  
* Partitioning Methods
  * Horizontal partitioning - same feature data distribute diff servers (e.g. design twitter db sharding)
  * Vertical Partitioning - divide data to store tables related to a specific feature in their own server (but this single server approach is not practical when feature data grow large, it will finally need to be Horizontal partitioning).
  * Directory Based Partitioning - create a lookup service which knows your current partitioning scheme and abstracts it away from the DB access code. Directory server holds the mapping between each tuple key to its DB server, loosely coupled that allow change partitioning scheme without affect application.
* [分区标准（Partitioning Criteria）](https://media.licdn.com/dms/image/D4E22AQGEA3o1RzYotA/feedshare-shrink_1280/0/1695051917383?e=1698278400&v=beta&t=9KRcbxPh_FDiBDe8luMwILBbIDfSh6BuZQt8IYHygwc)
  * 基于键或哈希的分区（Key or Hash-based Partitioning）
    * 范围分片（Range Sharding）：这种方法将数据按照某一范围进行分片，例如，按照用户 ID 范围将数据分布到不同的数据库或表中。这对于有序数据（如时间序列数据）非常适用。
    * 哈希分片（Hash Sharding）：这种方法将数据的哈希值用于决定数据所在的数据库或表。哈希分片通常能够均匀地分布数据，但不适用于需要范围查询的情况。
    * 取模分片（Modulo Sharding）
    * 时间分片（Time-based Sharding）：对于时间序列数据，可以使用时间分片，将数据按照时间周期（如每天、每月）分布到不同的数据库或表中。（当有合规要求或成本控制导致只保留一定时间的数据时，可以采取此方法，过期时可以直接删掉过期的分片们）
    * 地理位置分片（Geographical Sharding）：对于地理位置相关的数据，可以按照地理位置信息将数据分片，以提高查询效率。
    * 自定义：例如，根据业务或用户 ID 的最后几位数字将数据分片等等。
  * 列表分区（List Partitioning）基于一列的值将表数据划分为多个分区。每个分区包含具有相同或相似值的行。这种方法通常用于根据某个特定列的值将数据分开，以便更好地管理和查询这些数据
  * 轮询分区（Round-robin Partitioning）
  * 目录分片（Directory Sharding）：使用一个映射表，把数据的键（或其他值）到存储该数据的分片主机号的映射关系一一保存起来。
  * 组合分区（Composite Partitioning）- combine any of the above partitioning schemes to devise a new scheme.
* Common Problems of Sharding
  * Joins and [Denormalization](https://blog.csdn.net/zbuger/article/details/51026791) [[wiki]](https://en.wikipedia.org/wiki/Denormalization)
  * Referential integrity - foreign keys
  * Rebalancing - change sharding scheme

