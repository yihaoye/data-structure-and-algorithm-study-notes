# Consistent Hash

实现了：
* 集群增添节点及其相关数据转移
* 集群删除节点及其相关数据转移
* 虚拟节点实现
* 线程（并发）安全

未实现：
* 复制节点及其 P2P 协议/算法实现

参考 - https://mp.weixin.qq.com/s/eCxGPqrfIeFY_E_CnFRfMw  

主要类：
* [ConsistentHashCluster](./src/main/java/com/example/ch/model/ConsistentHashCluster.java)
* [Node](./src/main/java/com/example/ch/model/Node.java)

To run the code (test), please run by IDE like intelliJ or run the following command: 
`./gradlew test`

Unusually it main fail since hash collision, in this case please rerun it and it should be fine.
