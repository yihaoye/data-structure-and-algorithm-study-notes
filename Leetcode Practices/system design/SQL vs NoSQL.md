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

