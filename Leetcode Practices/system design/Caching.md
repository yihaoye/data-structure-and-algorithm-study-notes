# 缓存
这里的缓存指的是多个应用服务之间的共享缓存，通常使用 Redis，每个应用服务自己本地也可以使用自己的内存（数据结构如数组、哈希表）作为本地缓存。  

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

![](./cache-related.jpeg)  

其他：[Ring Buffer / Circular Buffer](https://zh.wikipedia.org/wiki/%E7%92%B0%E5%BD%A2%E7%B7%A9%E8%A1%9D%E5%8D%80)  
