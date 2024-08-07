# ElasticSearch
该数据库引擎是基于跳表支持倒排索引（[Ref](https://stackoverflow.com/a/43203339/6481829)）。  

ES 的设计是针对查询搜索的，对写的性能一般，应对 CRUD 就是 Create 性能可接受、Read 查询良好、Update 或 Delete 性能勉强（因为涉及之前的分词数据等，所以实际比如分词是通过标记删除来实现的）。ES 是为数据不经常变化的日志分析而设计的。  

![](./ElasticSearch%20Usage%20Case.gif)  

## 1. API
Elasticsearch (ES) 提供了丰富的 API 用于搜索和数据操作。以下是一些主要的 API 命令和功能：
1. 索引操作
   - 创建索引：PUT /index_name
   - 删除索引：DELETE /index_name
   - 查看索引：GET /index_name
2. 文档操作
   - 添加/更新文档：PUT /index_name/_doc/id
   - 获取文档：GET /index_name/_doc/id
   - 删除文档：DELETE /index_name/_doc/id
3. 搜索 API
   - 基本搜索：GET /index_name/_search | 请求体搜索：POST /index_name/_search
     - 查询 DSL
       - match 查询：精确匹配
       - match_phrase 查询：短语匹配
       - term 查询：词条查询
       - range 查询：范围查询
       - bool 查询：组合多个查询条件
     - 聚合 DSL
       - terms 聚合：分组计数
       - avg, sum, min, max 聚合：统计计算
       - date_histogram：时间柱状图
     - 组合上面一起使用，示例参考下面的对应段落 2
4. 映射管理
   - 获取映射：GET /index_name/_mapping
   - 更新映射：PUT /index_name/_mapping
5. 分词 API
   - _analyze API：测试分词器，示例参考下面的段落 3
6. 集群健康
   - 集群健康状态：GET /_cluster/health
7. 别名管理
   - 创建/更新别名：POST /_aliases
8. 索引模板
   - 创建模板：PUT /_template/template_name

以上 by Claude  

## 2. 组合使用
```json
// GET /index_name/_search
{
    "size": 10,  // 返回的文档数量
    "query": {
        // 查询部分
    },
    "sort": [
        // 排序部分
    ],
    "aggs": {
        // 聚合部分
    }
}
```

## 3. 分词
https://www.cnblogs.com/codeshell/p/14389403.html  
```json
// GET _analyze
{
    "analyzer": "AnalyzerName",
    "text": "内容"
}
```

## 重点：索引、分片、集群、节点、并发
ElasticSearch 中的索引对应 MySQL 中的 Database ，也就说 ElasticSearch 中的索引更像是一种数据存储集合，即用于存储文档。早期ElasticSearch 中的数据根据业务以索引为单位进行划分，Type（类型）就像 MySQL 中的 Table 一样，用于区分同一业务中不同的数据集合。不过在 6.x 版本后，就废弃了 Type，因为设计者发现 ElasticSearch 这种与关系型数据类比的设计方式有缺陷。  
**所以 ES 的索引就是一库一表的概念。**  

单个节点由于物理机硬件限制，存储的文档是有限的，如果一个索引包含海量文档，则不能在单个节点存储。ES 提供分片机制，同一个索引可以存储在不同分片（数据容器）中。分片分为主分片 (primary shard) 以及从分片 (replica shard)。主分片会被尽可能平均地 (rebalance) 分配在不同的节点上。分片是独立的，对于一个 Search Request 的行为，每个分片都会执行这个 Request。  

一个 ES 节点就是一个运行的 ES 实例，可以实现数据存储并且搜索的功能。每个节点都有一个唯一的名称作为身份标识，如果没有设置名称，默认使用 UUID 作为名称。最好给每个节点都定义上有意义的名称，在集群中区分出各个节点。  
一个机器可以有多个实例，所以并不能说一台机器就是一个 node，大多数情况下每个 node 运行在一个独立的环境或虚拟机上，有自己的 CPU 核数、内存、存储等。  
节点通过设置集群名称，在同一网络中发现具有相同集群名称的节点，组成集群。每个集群都有一个 cluster name 作为标识，默认的集群名称为 elasticsearch。如果在同一网络中只有一个节点，则这个节点成为一个单节点集群。  

以上参考：https://juejin.cn/post/6844904056347951117  

### 总结
索引和分片关系（一个分片只属于一个索引）
* 索引：index_1：
  * 主分片：
    * 主分片 1 (P1)
    * 主分片 2 (P2)
    * 主分片 3 (P3)
  * 副本分片：
    * 副本分片 1 (R1)
    * 副本分片 2 (R2)
    * 副本分片 3 (R3)

节点和分片关系（每个节点可以存储多个分片，但一个分片只能存储在一个节点上）
* 节点 1：
  * 主分片 1 (P1)
  * 副本分片 2 (R2)
* 节点 2：
  * 主分片 2 (P2)
  * 副本分片 3 (R3)
* 节点 3：
  * 主分片 3 (P3)
  * 副本分片 1 (R1)

索引和节点是多对多关系（一个索引的分片可以分布在多个节点上，确保数据的分布式存储和高可用性）
* 索引：index_1：
  * 节点 1：
    * 主分片 1 (P1)
    * 副本分片 2 (R2)
  * 节点 2：
    * 主分片 2 (P2)
    * 副本分片 3 (R3)
  * 节点 3：
    * 主分片 3 (P3)
    * 副本分片 1 (R1)

**并发优化查询在服务端主要在节点和分片层面进行，通过节点间并行处理分片上的查询请求，协调节点汇总结果。**  
所以多节点部署并增加索引的分片数量（主要是考多节点，多分片只是保证单索引被尽可能地分配到多个节点），可以通过并发处理显著提高单个索引的查询性能。[Ref](https://www.easyice.cn/archives/355)  
分片越多可以提高并发查询性能，但分片过多会增加管理和协调开销。最佳做法是合理平衡分片数量，以充分利用资源而不过度增加协调复杂性。  

那么既然已经有多节点和多分片，为什么还要多索引呢？  
在多节点和多分片的基础上，多索引的主要优势和用途包括：
* 数据隔离和组织：不同索引用于存储不同类型的数据，便于管理和查询。例如，按日期、客户、产品等维度分割数据。
* 访问控制：不同索引可以有不同的访问权限，确保数据安全。
* 性能优化：针对不同数据集创建索引，有助于优化特定查询的性能，避免索引过大带来的性能问题。
* 生命周期管理：多索引有助于实现数据生命周期管理，比如冷、热数据分离。旧数据可以存储在性能较低的节点上，新数据存储在高性能节点上。


## Golang 操作
https://www.topgoer.com/%E6%95%B0%E6%8D%AE%E5%BA%93%E6%93%8D%E4%BD%9C/go%E6%93%8D%E4%BD%9Celasticsearch/%E6%93%8D%E4%BD%9Celasticsearch.html  
