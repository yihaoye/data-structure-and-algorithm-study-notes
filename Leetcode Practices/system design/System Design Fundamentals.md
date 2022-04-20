# System Design Fundamentals

https://www.algoexpert.io/systems/fundamentals  
* Client—Server Model
* Network Protocols
  * IP, TCP, HTTP/S
* Storage
  * Disk
  * Memory
* Latency And Throughput
* Availability
  * SLA/SLO, SLA is agreement about `How many Nines` availability guarantee between system provider (e.g. Cloud Provider AWS) and customer (e.g. Cloud User), SLO is component of SLA.
  * How many Nines? Availability = 99.999% is High Availability / HA System. Which part of system require High Availability?
  * Redundancy, avoid single point of failure.
* Caching
  * Can be each level of the system (e.g. client, server, db etc)
  * Write through caching / write back caching
  * Be careful with stale data in cache
  * LRU
* Proxies
* Load Balancers
* Hashing
* Relational Databases
* Key-Value Stores
* Specialized Storage Paradigms
* Replication And Sharding
* Leader Election
* Peer-To-Peer Networks
* Polling And Streaming
* Configuration
* Rate Limiting
* Logging And Monitoring
* Publish/Subscribe Pattern
* MapReduce
* Security And HTTPS
* API Design

![](./Latency%20And%20Throughput.png)  
![](./High%20Availability.png)  
