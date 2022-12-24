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

