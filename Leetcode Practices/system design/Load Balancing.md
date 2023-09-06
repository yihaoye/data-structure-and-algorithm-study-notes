LB helps to spread the traffic across a cluster of servers to improve responsiveness and availability of applications, websites or databases. It also keeps track of the status of all the resources while distributing requests. (Between the user and the web server; Between web servers and an internal platform layer, like application servers or cache servers; Between internal platform layer and database)  
* Benefits of Load Balancing
* Load Balancing Algorithms (Health Checks)
  * Least Connection Method - 最少连接数法
  * Least Response Time Method - 最快响应法
  * Least Bandwidth Method - 最小带宽法
  * Round Robin Method - 轮询法
  * Weighted Round Robin Method - 加权轮询法
  * IP Hash - IP/源地址哈希法
  * Random Method - 随机法
  * Weighted Random Method - 加权随机法
* Redundant Load Balancers (LB can be a single point of failure; to overcome this, a second LB can be connected to the first to form a cluster)
