# 基础知识、面试八股文

## 如何回答基础知识问题？
1. 分点叙述：对于复杂的题目尝试分成几个要点，举个例子，题目：“从输入 URL 到展现页面的全过程” 涉及非常多细节和内容，面试回答的时候尝试分解成：  
   * URL 解析
   * 建立连接
   * 服务器处理请求
   * 客户端渲染
2. 先总后分：一个常见的错误是针对问题直接说出一大段内容，让面试官进行阅读理解找重点。正确的方式是应该根据总分结构，分点后先将要点的关键字说出来，让面试官对你接下来要讲的内容有一定的预期，然后再阐述对应的细节。
3. 选点阐述：要有侧重地从这些要点中选择一两个你比较熟悉的阐述细节，其他要点简单介绍即可。例如如果你最熟悉的是建立连接阶段（比如 TCP 连接以及 HTTP/S 连接，TCP 使用三次握手建立连接）

这种情况下，面试官会从你比较熟悉的点去进行延伸提问。  
  
## Java 高频
* Java 中垃圾回收机制中如何判断对象需要回收？常见的 GC 回收算法有哪些？ （中等）
* HashMap 与 ConcurrentHashMap 的实现原理是怎样的？ConcurrentHashMap 是如何保证线程安全的？ （中等）
* Synchronized 关键字底层是如何实现的？它与 Lock 相比优缺点分别是什么？ （中等）
* HashMap 实现原理，为什么使用红黑树？  （中等）
* 简述 Java 的反射机制及其应用场景  （简单）
* Synchronized，Volatile，可重入锁的不同使用场景及优缺点    （困难）
* 简述 BIO, NIO, AIO 的区别  （困难）
* Java 类的加载流程是怎样的？什么是双亲委派机制？  （简单）
* JMM 中内存模型是怎样的？什么是指令序列重排序？  （中等）
* JVM 内存是如何对应到操作系统内存的？  （中等）
* Java 怎么防止内存溢出  （简单）
* Java 线程间有多少通信方式？  （简单）
* Java 中接口和抽象类的区别  （简单）
* 简述 ArrayList 与 LinkedList 的底层实现以及常见操作的时间复杂度  （简单）
* String，StringBuffer，StringBuilder 之间有什么区别？
* hashcode 和 equals 方法的联系  （简单）
* == 和 equals() 的区别？
* Java 常见锁有哪些？ReetrantLock 是怎么实现的？  （中等）
* volatile 关键字解决了什么问题，它的实现原理是什么？  （中等）
* ThreadLocal 实现原理是什么？  （简单）
* 简述 CAS 原理，什么是 ABA 问题，怎么解决？  （简单）
* 简述 Spring 的初始化流程  （中等）
* 简述常见的工厂模式以及单例模式的使用场景  （中等）
* 简述 Netty 线程模型，Netty 为什么如此高效？  （中等）
* 线程池是如何实现的？简述线程池的任务策略  （中等）
* 简述生产者消费者模型  （简单）
* 产生死锁的必要条件有哪些？如何解决死锁？  （中等）
* 简述 Spring 的 IOC 机制  （简单）
* 简述 Spring bean 的生命周期  （中等）
* 简述 Spring AOP 的原理
* MVC 模型和 MVVM 模型的区别  （中等）
* 简述使用协程的优点
* Java 编译后的 .class 文件包含了什么内容？
* 什么是公平锁？什么是非公平锁？  （简单）
* 简述 Java 中 final 关键字的作用
* 简述动态代理与静态代理
* [Java IO](https://www.cnblogs.com/czwbig/p/10007201.html)

## Golang 高频
* [什么是 golang 内存逃逸](./../Computer%20System%20Layer/内存与程序.md#内存逃逸)
* [go routine 和 thread、process 区别](https://huweicai.com/process-thread-goroutine/)
* panic 相关 - [panic](https://www.yiibai.com/go/golang-panic.html)、[panic 和 recover](https://draveness.me/golang/docs/part2-foundation/ch05-keyword/golang-panic-recover/)
* [上下文 Context](https://draveness.me/golang/docs/part3-runtime/ch06-concurrency/golang-context/)

## MySQL 高频
* [为什么 InnoDB 表必须有主键，并且推荐使用整型的自增主键？](https://blog.csdn.net/weixin_41699562/article/details/104139458)
* ORM N+1 问题 - [解答一](https://segmentfault.com/a/1190000039421843)、[解答二](https://www.cnblogs.com/google4y/p/3455534.html)、[解答三](https://zhuanlan.zhihu.com/p/27323883)
* [MySQL 索引背后的数据结构及算法原理](https://blog.codinglabs.org/articles/theory-of-mysql-index.html)

## 操作系统（Linux & bash）高频
* [进程、线程、协程的区别](./../Computer%20System%20Layer/进程与线程.md)
* [Linux 目录结构](https://zhuanlan.zhihu.com/p/496602829)
* [Linux 使用的进程间通信方式](https://juejin.cn/post/7069591460730896414#heading-9)
* [Linux 有哪些系统日志文件](https://juejin.cn/post/7069591460730896414#heading-10)
* [什么是 root 帐户](https://juejin.cn/post/7069591460730896414#heading-13)
* [grep](https://juejin.cn/post/7069591460730896414#heading-21)
* [sed](https://juejin.cn/post/7069591460730896414#heading-22)
* [awk](https://juejin.cn/post/7069591460730896414#heading-23)
* [bash 文件管理命令](https://juejin.cn/post/7069591460730896414#heading-38)
* [bash 网络通讯命令](https://juejin.cn/post/7069591460730896414#heading-67)
* [bash 系统管理命令](https://juejin.cn/post/7069591460730896414#heading-73)

## 网络高频
* [计算机网络面试突击](https://juejin.cn/post/6932001161532669960)
* [TCP 三次握手](https://www.zhihu.com/question/24853633/answer/63668444)

## 安全高频
* [对称加密与非对称加密]()

## 系统设计、DevOps 高频
* [[微服務] 什麼是 gRPC，架構上為什麼要使用 gRPC](https://www.youtube.com/watch?v=qEB3yFzETVs)
* RPC 与 REST 区别与选择 - [解答一](https://www.cnblogs.com/wongbingming/p/11086773.html)、[解答二](https://zhuanlan.zhihu.com/p/102760613)
* [CI 服务有什么用途](https://www.modb.pro/db/404626)

## Redis 高频
* [redis 有哪些数据类型？可以应用在什么场景？](https://cloud.tencent.com/developer/article/1975464)
* [redis 的过期键的删除策略有哪些？](https://cloud.tencent.com/developer/article/1975464)
* [缓存穿透、缓存击穿、缓存雪崩是什么？怎么解决呢？](https://cloud.tencent.com/developer/article/1975464)

## Nginx 高频
* [Nginx epoll 模型]()
* [Nginx 多进程模型是如何实现高并发的]()

## Kafka 高频
* [kafka 架构](https://cloud.tencent.com/developer/article/1853417)
* [如何确定当前能读到哪一条消息](https://cloud.tencent.com/developer/article/1853417)

## Docker 高频
* [Dockerfile 配置文件中的 COPY 和 ADD 指令有什么不同](https://www.modb.pro/db/404626)
* [Docker Image 是什么](https://www.modb.pro/db/404626)
* [Docker Container 是什么](https://www.modb.pro/db/404626)
* [Docker Hub 什么概念](https://www.modb.pro/db/404626)
* [Docker 容器可能存在的运行阶段](https://www.modb.pro/db/404626)
* [Dockerfile 配置文件中最常用的指令](https://www.modb.pro/db/404626)
* [什么类型的应用（无状态性或有状态性）更适合 Docker 容器技术](https://www.modb.pro/db/404626)
* [基本 Docker 应用流程](https://www.modb.pro/db/404626)
* [Docker Image 和 Docker Layer 有什么不同](https://www.modb.pro/db/404626)
* [Docker Compose 是什么](https://www.cnblogs.com/sparkdev/p/9753793.html)
* [Docker 群（Swarm）是什么](https://www.modb.pro/db/404626)
* [在使用 Docker 技术的产品中如何监控其运行](https://www.modb.pro/db/404626)


以上部分参考：https://osjobs.net/topk/  
