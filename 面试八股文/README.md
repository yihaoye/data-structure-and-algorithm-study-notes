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
* HashMap 与 ConcurrentHashMap 的实现原理是怎样的？HashMap 为什么使用红黑树？[ConcurrentHashMap 是如何保证线程安全的？](https://github.com/JasonGaoH/KnowledgeSummary/blob/master/Docs/Java/ConcurrentHashMap%E6%98%AF%E5%A6%82%E4%BD%95%E4%BF%9D%E8%AF%81%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E7%9A%84.md) （中等）
* ConcurrentHashMap 的写操作会对读操作产生性能影响吗？[1](https://stackoverflow.com/a/71805283/6481829)、[2](https://stackoverflow.com/questions/68968305/how-can-i-block-concurrenthashmap-get-operations-during-a-put)
* Synchronized 关键字底层是如何实现的？它与 Lock 相比优缺点分别是什么？ （中等）
* 简述 Java 的反射机制及其应用场景  （简单）
* Synchronized，Volatile（及其实现原理），可重入锁的不同使用场景及优缺点    （困难）
* 简述 BIO, NIO, AIO 的区别  （困难）
* Java 类的加载流程是怎样的？什么是双亲委派机制？  （简单）
* JMM 中内存模型是怎样的？什么是指令序列重排序？  （中等）
* JVM 内存是如何对应到操作系统内存的？  （中等）
* Java 怎么防止内存溢出  （简单）
* Java 线程间有多少通信方式？  （简单）
* [Java 中接口和抽象类的区别](../Leetcode%20Practices/object%20oriented%20design/README.md#何时使用抽象类，何时使用接口)  （简单）
* hashcode 和 equals 方法的联系  （简单）
* Java 常见锁有哪些？ReetrantLock 是怎么实现的？  （中等）
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
* 什么是公平锁？什么是非公平锁？  （简单）
* 简述动态代理与静态代理
* [Java IO](https://www.cnblogs.com/czwbig/p/10007201.html)
* [Java 注解原理](../Tool%20Sets/Annotation.java)

## Golang 高频
* [什么是 golang 内存逃逸](./../Computer%20System%20Layer/内存与程序.md#内存逃逸)
* [go routine 和 thread、process 区别](https://huweicai.com/process-thread-goroutine/)
* panic 相关 - [panic](https://www.yiibai.com/go/golang-panic.html)、[panic 和 recover](https://draveness.me/golang/docs/part2-foundation/ch05-keyword/golang-panic-recover/)
* [上下文 Context](https://draveness.me/golang/docs/part3-runtime/ch06-concurrency/golang-context/)

## 数据库高频
* [为什么 InnoDB 表必须有主键，并且推荐使用整型的自增主键？](https://blog.csdn.net/weixin_41699562/article/details/104139458)
* ORM N+1 问题 - [解答一](https://segmentfault.com/a/1190000039421843)、[解答二](https://www.cnblogs.com/google4y/p/3455534.html)、[解答三](https://zhuanlan.zhihu.com/p/27323883)
* [MySQL 索引背后的数据结构及算法原理 / B+树](https://blog.codinglabs.org/articles/theory-of-mysql-index.html)
* [LSM 树](./../Common%20Data%20Structure%20and%20Data%20Type/Data%20Structure%20Implementation/LSMTree/README.md)

## 操作系统（Linux & bash）高频
* [进程、线程、协程的区别](./../Computer%20System%20Layer/进程与线程.md)
* [Linux for Programmers](https://www.youtube.com/watch?v=ebHX9c75H8I&list=PLzMcBGfZo4-nUIIMsz040W_X-03QH5c5h)
  * cat、rm、cd、mv、cp
  * ps、top、kill
  * ls -l、chmod (permission)
  * ssh、ssh-keygen
  * ifconfig、netstat
  * crontab (i.e. cronjob)、[sleep](https://linuxize.com/post/how-to-use-linux-sleep-command-to-pause-a-bash-script/)
* [Linux 目录结构](https://zhuanlan.zhihu.com/p/496602829)
* [Linux 网络命令大全](https://juejin.cn/post/6844903844267180039)
* [Linux 使用的进程间通信方式](https://juejin.cn/post/7069591460730896414#heading-9)
* [Linux 有哪些系统日志文件](https://juejin.cn/post/7069591460730896414#heading-10)
* [什么是 root 帐户](https://juejin.cn/post/7069591460730896414#heading-13)
* [Linux 中最重要的 3 个命令，均为文本操作（因为 Linux 一切皆文本）](https://zhuanlan.zhihu.com/p/110983126)
  * [grep（更适合单纯的查找或匹配）](https://juejin.cn/post/7069591460730896414#heading-21)
  * [sed（更适合编辑匹配到的文本，取行和替换）](https://juejin.cn/post/7069591460730896414#heading-22)
  * [awk（功能最强大，但也最复杂，简单来说就是把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分再进行各种分析处理）](https://juejin.cn/post/7069591460730896414#heading-23)
* [bash 文件管理命令](https://juejin.cn/post/7069591460730896414#heading-38)
* [bash 网络通讯命令](https://juejin.cn/post/7069591460730896414#heading-67)
* [bash 系统管理命令](https://juejin.cn/post/7069591460730896414#heading-73)
* [Linux 常用 bash 命令](https://www.cnblogs.com/savorboard/p/bash-guide.html)
* [Bash 脚本教程](https://wangdoc.com/bash/)
* [The 50 Most Popular Linux & Terminal Commands](https://www.youtube.com/watch?v=ZtqBQ68cfJc)
  * ln (create hard/soft link)、open、date、piping (i.e. |)
* [硬链接和软链接](https://blog.csdn.net/LEON1741/article/details/100136449)
* [实时监控日志文件](https://blog.csdn.net/rlnLo2pNEfx9c/article/details/122852275)
* [Systemd](https://www.youtube.com/watch?v=Kzpm-rGAXos)

## 网络高频
* [计算机网络面试突击](https://juejin.cn/post/6932001161532669960)
* [TCP 三次握手](https://www.zhihu.com/question/24853633/answer/63668444)

## 安全高频
* [CORS（跨域访问）及其工作流程](./CORS.PNG)
  * [为什么浏览器要限制跨域访问](https://www.zhihu.com/question/26379635/answer/534866558)
  * [CORS 为什么能保障安全？为什么只对复杂请求做预检？](https://www.51cto.com/article/705584.html)
* [CSRF](https://tech.meituan.com/2018/10/11/fe-security-csrf.html)
  * [CSRF 与 CORS](https://b1ngz.github.io/csrf-and-cors/)
* 加密解密
  * [对称加密](https://www.youtube.com/watch?v=uD_zCOKuYPc) - 最简单的一次性安全加密可以采用异或运算即可
  * [非对称加密](https://github.com/yihaoye/stem-notes/blob/master/e-computer-network/https_and_ssl_tls.md)
* [实际工程项目中应用密匙如何存取](https://zh.wikipedia.org/zh-hant/%E5%AF%86%E9%92%A5%E7%AE%A1%E7%90%86)
* [Security for Programmer](https://www.youtube.com/watch?v=qjrkV4RjgIU)
* OWASP top 10 : [1](https://owasp.org/www-pdf-archive/OWASP_Top_10_2017_%E4%B8%AD%E6%96%87%E7%89%88v1.2.pdf), [2](https://owasp.org/www-project-top-ten/)
  * 代码注入 (SQL、[跨站脚本 XSS](https://www.cnblogs.com/54chensongxia/p/11643787.html)) - [PrepareStatement 如何防御代码注入](https://cloud.tencent.com/developer/article/2092203)
  * 失效的识别、身份认证
  * 敏感信息泄露、加密失败
  * 失效的访问控制
  * 安全配置错误
  * 软件和数据完整性故障（不安全的反序列化）
  * 使用含有已知漏洞的、易受攻击或过期的组件
  * 失效或不足的日志记录和监控
  * 服务端请求伪造（SSRF）
  * 不安全的设计（需要更多地使用威胁建模、安全设计模式和原则以及参考架构）
* [Cookie、Session、Token、JWT 区别](https://juejin.cn/post/6844904034181070861)
* [Password, Session, Cookie, Token, JWT, SSO, OAuth - Authentication Explained](https://blog.bytebytego.com/p/password-session-cookie-token-jwt)
* [JSON Web Token 入门教程](https://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)
* [Session 是怎么实现的？存储在哪里？](https://juejin.cn/post/6942852054847062053)
* 安全清单
  * 反 SQL/代码注入
  * SSL / OpenSSL 更新
  * 密码加盐
  * 多重要素验证（应用在例如后勤办公室等）
  * 对敏感数据进行 AES 加密
  * 避免非加密信息/数据在非安全处（比如邮件）存放/发送

## 系统设计、DevOps 高频
* [[微服務] 什麼是 gRPC，架構上為什麼要使用 gRPC](https://www.youtube.com/watch?v=qEB3yFzETVs)
* RPC 与 REST 区别与选择 - [解答一](https://www.cnblogs.com/wongbingming/p/11086773.html)、[解答二](https://zhuanlan.zhihu.com/p/102760613)、[服务调用 / API 设计](../Leetcode%20Practices/system%20design/System%20Design%20Fundamentals.md#API%20Design)
* [CI 服务有什么用途](https://www.modb.pro/db/404626)
* [服务降级、服务熔断](./../Leetcode%20Practices/system%20design/%E6%9C%8D%E5%8A%A1%E9%99%8D%E7%BA%A7%E4%B8%8E%E6%9C%8D%E5%8A%A1%E7%86%94%E6%96%AD.md)

## Redis 高频
* [redis 有哪些数据类型？可以应用在什么场景？](https://cloud.tencent.com/developer/article/1975464)
* [redis 的过期键的删除策略有哪些？](https://cloud.tencent.com/developer/article/1975464)
* [缓存穿透、缓存击穿、缓存雪崩是什么？怎么解决呢？](https://cloud.tencent.com/developer/article/1975464)

## Nginx 高频
* [Nginx epoll 模型](./../Leetcode%20Practices/system%20design/IO%E6%A8%A1%E5%9E%8B%E4%B8%8EWeb%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%B7%A5%E4%BD%9C%E6%A8%A1%E5%9E%8B.md)
* [Nginx 多进程模型是如何实现高并发的](./../Leetcode%20Practices/system%20design/IO%E6%A8%A1%E5%9E%8B%E4%B8%8EWeb%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%B7%A5%E4%BD%9C%E6%A8%A1%E5%9E%8B.md)

## Kafka 高频
* [kafka 架构](https://cloud.tencent.com/developer/article/1853417)
* [如何确定当前能读到哪一条消息](https://cloud.tencent.com/developer/article/1853417)
* [Kafka consumer group](https://www.cnblogs.com/huxi2b/p/6223228.html)

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

## 前端高频
* [reactjs interview questions](https://github.com/sudheerj/reactjs-interview-questions)
* [什么是 React 及其基本设计理念](https://zh-hans.react.dev/learn/thinking-in-react)
* [React Hooks](https://github.com/yihaoye/stem-notes/tree/master/t-frameworks/react.js#react-hooks)
* [什么是 JSX](https://legacy.reactjs.org/docs/introducing-jsx.html)
* [什么是 Virtual DOM 以及为什么使用它](https://juejin.cn/post/6844903953499422727)
* [React Virtual DOM 的 diff 算法](https://juejin.cn/post/7005846199479566344)
* [什么是 Fiber](https://juejin.cn/post/7106148927606358030)
* [JavaScript 异步、非阻塞](https://developer.mozilla.org/zh-CN/docs/Learn/JavaScript/Asynchronous/Introducing)
* [React build 输出](https://handsonreact.com/docs/build-deploy)
* [什么是 SSR](https://zhuanlan.zhihu.com/p/90746589)


以上部分参考：https://osjobs.net/topk/  
