## 阻塞队列
java.util.concurrent 包提供的用于解决并发生产者/消费者问题的最有用的类 - BlockingQueue。将介绍 BlockingQueue 接口的 API 以及如何使用该接口的方法使编写并发程序更容易。  
java.util.concurrent 提供了两种类型的 BlockingQueue：  
* 无限队列 （ unbounded queue ） - 几乎可以无限增长
* 有限队列 （ bounded queue ） - 定义了最大容量  
  
### 无限队列
创建一个无限队列的方法很简单  
```java
BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
```
上面这段代码中，blockingQueue 的容量将设置为 Integer.MAX_VALUE。  
向无限队列添加元素的所有操作都将永远不会阻塞，因此它可以增长到非常大的容量。  
使用无限 BlockingQueue 设计生产者 - 消费者模型时最重要的是 消费者应该能够像生产者向队列添加消息一样快地消费消息。否则，内存可能会填满，然后就会得到一个 OutOfMemory 异常。  
  
### 有限队列
第二种类型的队列是有限队列。可以通过将容量作为参数传递给构造函数来创建这样的队列  
```java
BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(10);
```
上面这句代码中，设置了 blockingQueue 的容量为 10。这意味着当消费者尝试将元素添加到已经满了的队列时，结果取决于添加元素的方法（ offer() 、add() 、put() ) ，它将阻塞，直到有足够的空间可以插入元素。否则，添加操作将会失败。  
使用有限队列是设计并发程序的好方法，因为当将元素插入到已经满了的队列时，这些操作需要等到消费者赶上并在队列中提供一些空间。这种机制可以让那个我们不做任何其它更改就可以实现节流。  
  
### BlockingQueue API
BlockingQueue 接口的所有方法可以分为两大类：负责向队列添加元素的方法和检索这些元素的方法。  
在队列满/空的情况下，来自这两个组的每个方法的行为都不同。  
#### 添加元素
BlockingQueue 提供了以下方法用于添加元素:  
* add()	如果插入成功则返回 true，否则抛出 IllegalStateException 异常
* put()	将指定的元素插入队列，如果队列满了，那么会阻塞直到有空间插入
* offer() 如果插入成功则返回 true，否则返回 false
* offer(E e, long timeout, TimeUnit unit) 尝试将元素插入队列，如果队列已满，那么会阻塞直到有空间插入  
#### 检索元素
BlockingQueue 提供了以下方法用于检索元素:  
* take() 获取队列的头部元素并将其删除，如果队列为空，则阻塞并等待元素变为可用
* poll(long timeout, TimeUnit unit)	检索并删除队列的头部，如有必要，等待指定的等待时间以使元素可用，如果超时，则返回 null  
  
