这里只列举比较重要的常用的设计模式，主要为 Java 实现或 Go 实现。  

一些设计模式的出现是为了弥补特定语言的限制（如 Java 的设计遗留问题），提供更灵活、可扩展、可维护的代码结构。而一些新兴的语言如 Go 等，其设计哲学鼓励简单的代码和少使用复杂的设计模式，因此设计和编写 Go 代码时通常会更注重简单性、直观性和实用性。Go 提供了一些内置工具，如接口和类型组合，可以在不使用传统设计模式的情况下实现相似的功能。  
总的来说，Go 的设计理念和语言特性可能会减少对某些设计模式的需求，但并不意味着设计模式在 Go 中变得无关紧要。在实际编码中，还是需要根据具体情况灵活运用设计模式或其他编程范式，下面实现了 Go 代码的设计模式是对 Go 实用的其余则较少。  

推荐阅读材料：  
* https://refactoringguru.cn/design-patterns/catalog
* [设计模式二三事](https://tech.meituan.com/2022/03/10/interesting-talk-about-design-patterns.html)

![](./设计模式目录：22种设计模式.png)  

# 常用模式

## 创建型模式
创建型模式关注点是如何创建对象，其核心思想是要把对象的创建和使用相分离，这样使得两者能相对独立地变换。  
* [建造者 | Builder](./Builder.java)
  * ([Go 示例](./Builder.go))
* [单例 | Singleton](./Singleton.java) - 进阶的为对象池模式、多例模式
  * ([Go 示例](./Singleton.go)) - Go sync.Once 且适用于并发场景
* 对象池 | Object Pool - [信号量模式 | Semaphore](./Semaphore.java) 与其十分类似
  * ([Go 示例 1](./ObjectPool.go))
  * ([Go 示例 2](./Semaphore.go)) - 可用于应对下游限流调用，同时比串行调用快一些
* [工厂方法 | Factory](./Factory.java) - 可与注册器合用，语言如 Go 不推荐使用工厂模式而是鼓励使用 NewXXX() 函数即可
* 函数选项模式 | Functional Options Pattern - Go 语言推荐模式
  * ([Go 示例 1](./Options.go))
  * ([Go 示例 2](./Options2.go))
  
## 结构型模式
结构型模式主要涉及如何组合各种对象以便获得更好、更灵活的结构。虽然面向对象的继承机制提供了最基本的子类扩展父类的功能，但结构型模式不仅仅简单地使用继承，而更多地通过组合与运行期的动态组合来实现更灵活的功能。  
* [适配器 | Adapter](./Adapter.java)
  * ([Go 示例](./Adapter.go))
* [组合 | Composite](./Composite.java) - Go 默认推荐采用组合而非继承
  * ([Go 示例](./Composite.go))
* [代理 | Proxy](./Proxy.java) - 类似 Middleware、Wrapper、[装饰器 | Decorator](./Decorator.java)
  * ([Go 示例](./Middleware.go)) - Web 中间件 Middleware 加日志、鉴权等等
  
## 行为型模式
行为型模式主要涉及算法和对象间的职责分配。通过使用对象组合，行为型模式可以描述一组对象应该如何协作来完成一个整体任务。  
* [观察者 | Observer(Listener)](./Observer.java)
  * ([Go 示例](./Observer.go))
* [策略 | Strategy](./Strategy.java) - 包括命令模式，在 Go 中有其他更直接的方式来达到相同的目的
  * [Go 示例](./Strategy.go) - 使用函数闭包或接口组合
* 分发 | Dispatch - 类似策略模式，用于将来自多个来源的请求调度到不同的处理程序，它通常用于构建事件驱动系统或 GUI 应用程序。优点：提高了代码的可维护性、可扩展性、灵活性
  *  ([Go 示例](./Dispatch.go))
  *  ([Go 示例 2](./Router.go)) Web 路由器 | Router - [Command Dispatch Pattern](https://colobu.com/2024/06/03/command-dispacher-pattern/#more)
* 重试 | Retry - 通常配合退避（Backoff）策略
  * ([Go 示例](./Retry.go))
* 断路器 | Circuit Breaker
  * ([Go 示例](./CircuitBreaker.go))
* [迭代器 | Iterator](./Iterator.java)
  * (在 Go 更多直接使用内置的 slice 和 for range 结构，因其足够简洁而有效的迭代功能)
* [状态 | State](./State.java)

## 并发型模式
* 生产消费 | Producer Consumer
  * ([Go 示例](./ProducerConsumer.go))
* 扇入 | Fan-In
  * ([Go 示例](./FanIn.go))
* 扇出 | Fan-Out
  * ([Go 示例](./FanOut.go))
* 管道和过滤器 | Pipeline and Filters - [Ref](https://learnku.com/docs/go-patterns/1.0.0/guan-dao-he-guo-lv-qi-mo-shi-pipeline-and-filters-pattern/14762)
  * ([Go 示例](./PipelineFilters.go))
* 互斥锁 | Mutex
  * ([Go 示例](./Mutex.go))
* 工作池（协程池）| Worker Pool - 是前面对象池模式在并发场景下的使用
  * ([Go 示例](./WorkerPool.go))
  * ([Go 示例 2](./WorkerPool2.go))
* 定时器与超时 | Tick Timeout
  * ([Go 示例](./TickTimeout.go))
  * ([Go 示例 2](./TickTimeout2.go))

## 其他复杂少用的
* [原型 | Prototype](./Prototype.java) - 即 copy / clone
* [桥接 | Bridge](./Bridge.java)
* [模板 | Template](./Template.java)
* [访问者 | Visitor](./Visitor.java)
* [责任链 | Chain of Responsibility](./CoR.java) - 衍生的有过滤器模式


以上引用自
* https://www.liaoxuefeng.com/wiki/1252599548343744/1264742167474528
* https://learnku.com/docs/go-patterns/1.0.0
  
## 更多
### 单例模式与工厂模式的区别
参考：https://stackoverflow.com/a/2094231/6481829  
> **A singleton pattern ensures that you always get back the same instance of whatever type you are retrieving, whereas the factory pattern generally gives you a different instance of each type.**  
> The purpose of the singleton is where you want all calls to go through the same instance. An example of this might be a class that manages a disk cache, or gets data from a static dictionary; wherever it is important only one known instance interacts with the resource. This does make it less scalable.  
> The purpose of the factory is to create and return new instances. Often, these won't actually be the same type at all, but they will be implementations of the same base class. However, there may be many instances of each type.  
