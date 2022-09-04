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
* HashMap 和 HashTable 的区别是什么？   （简单）
* Synchronized 关键字底层是如何实现的？它与 Lock 相比优缺点分别是什么？ （中等）
* HashMap 实现原理，为什么使用红黑树？  （中等）
* 简述 Java 的反射机制及其应用场景  （简单）
* Synchronized，Volatile，可重入锁的不同使用场景及优缺点    （困难）
* 简述 BIO, NIO, AIO 的区别  （困难）
* HashMap 1.7 / 1.8 的实现区别  （简单）
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

## Golang 高频
* [什么是 golang 内存逃逸](./../Computer%20System%20Layer/内存与程序.md#内存逃逸)

## MySQL 高频
* [为什么 InnoDB 表必须有主键，并且推荐使用整型的自增主键？](https://blog.csdn.net/weixin_41699562/article/details/104139458)
* ORM N+1 问题 - [解答一](https://segmentfault.com/a/1190000039421843)、[解答二](https://www.cnblogs.com/google4y/p/3455534.html)、[解答三](https://zhuanlan.zhihu.com/p/27323883)

## 操作系统（Linux & bash）高频

## 网络高频

## 安全高频

## 系统设计高频

## Redis 高频

## Nginx 高频

## Kafka 高频

## Docker 高频



以上部分参考：https://osjobs.net/topk/  
