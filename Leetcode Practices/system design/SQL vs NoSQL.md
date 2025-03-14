其实这个问题本身是不对的，目前业界的做法并不是根据简单的场景来看应该选择哪个大类的数据库（因为关系和非关系数据库功能互相融合，都各自有朝着对方靠拢地发展），而是根据具体场景选择具体的数据库（Postgre、MongoDB etc）  

[主要关注 3 点：](https://www.youtube.com/shorts/tIvCjH2ETzo)
* 数据模型 - 是否有复杂的关系，还是仅简单的 lookup
* 事务（即 ACID） - 是否支持
* 查询样式（query pattern）- 复杂 JOIN（是否需要支持范式化）、范围查询、分析查询（analytics）、全文搜索 etc
* 可扩展性和数据一致性 - 分布式友好（以及数据最终一致性）
  * RDBMS 在大规模场景下需要额外架构设计，才能支持水平扩展，使用时也更复杂（分库分表 Sharding 拆分数据使得 JOIN 查询、事务支持变复杂）
  
## 数据库类型特征
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
    * 在 KV 数据库中，只能通过 key 查找到整个 value，数据库并不知道 value 里面存的内容到底是什么，而是通过应用程序将 value 里面的东西进行翻译解析；在文档数据库中，不仅可以通过 key 查找 value，也可以通过 document 中对应的 key 找到具体内容，value 对数据库来说是透明的。**即文档数据库还可以对 value 里的内容片段进行索引（因为实际业界的关系数据库与文档数据库也在渐渐地互相融合）而 KV 数据库不行。**
* High level differences between SQL and NoSQL
  * Storage
    * SQL row represents an entity and each column represents a data point about that entity
  * Schema
    * SQL each record conforms to a fixed schema
    * NoSQL schemas are dynamic, 'columns' can be added on the fly
  * Querying
    * SQL apply structured query language for defining and manipulating the data
    * NoSQL apply UnQL (Unstructured Query Language), different databases have different syntax for using UnQL.
    * 聚合 - NoSQL 数据库可以进行聚合操作，但其聚合的方式、功能和效率通常与传统的关系型数据库（SQL）不同（通常是通过数据库的内置 API），且聚合能力有强有弱（比如 ES、MongoDB 内置聚合能力极强，Cassandra、DynamoDB、HBase、Redis 等内置仅提供基础聚合通常需要将复杂聚合操作放到应用层）
  * Scalability
    * SQL, in most common situations, are vertically scalable, horizontal scale is challenging and time-consuming
      * 多台数据库间保持事务一致性代价高
      * 数据拆分后，跨节点的 JOIN 查询性能差
      * 数据分片（Sharding）难度大（主键分布、查询优化等问题）
    * NoSQL, horizontally scalable, many NoSQL tech also distribute data across servers automatically.
  * Reliability or ACID Compliancy (Atomicity, Consistency, Isolation, Durability)
    * SQL mostly are ACID compliant.
    * NoSQL mostly sacrifice ACID compliance for performance and scalability.
* SQL vs NoSQL Which one to use? - there’s no one-size-fits-all solution
  * Reasons to use SQL database
    * CP or CA scenario
    * 需要事务或 need to ensure ACID compliance reduces anomalies and protects the integrity of db (for many e-commerce and financial applications, an ACID-compliant database remains the preferred option) - 但实际上有些 NoSQL 也较好地支持事务和强一致性，比如 MongoDB 和 Spanner（Google）
    * data is structured (or have any relational data) and unchanging
    * need JOIN operation (通常情况下，NoSQL 数据库不太支持传统的 SQL 中的 JOIN 操作，因为在 NoSQL 中，数据冗余、重复存储是常见的做法，即其本身设计思路就是鼓励通过反范式的设计来避免、减少关联操作)，因此也意味着如果希望系统更范式化（整洁、减少重复数据维护的工作量或不容易出错出现数据不一致、低空间资源成本等）则应选择 SQL 数据库
    * ![](./How%20to%20Select%20SQL%20DB.jpeg)
  * Reasons to use NoSQL database (Big data contributes to NoSQL databases' succeed)
    * AP or CP scenario
    * Storing large volumes of data that often have little to no structure, or very complex / uncertain structure
    * Making the most of cloud computing and storage, requires data to be easily spread across multiple servers to scale up
    * Rapid development - quick iterations of system which require making frequent updates to the data structure without much downtime between versions
    * 高性能、侧重写操作（LSM 树）
    * 只需要序列化/反序列化数据
    * ![](./How%20to%20Select%20NoSQL%20DB.jpeg)

参考：https://pingcap.medium.com/how-to-efficiently-choose-the-right-database-for-your-applications-20a109abced3  

