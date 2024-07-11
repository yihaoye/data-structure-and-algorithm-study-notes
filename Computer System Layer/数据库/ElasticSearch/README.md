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

## Golang 操作
https://www.topgoer.com/%E6%95%B0%E6%8D%AE%E5%BA%93%E6%93%8D%E4%BD%9C/go%E6%93%8D%E4%BD%9Celasticsearch/%E6%93%8D%E4%BD%9Celasticsearch.html  
