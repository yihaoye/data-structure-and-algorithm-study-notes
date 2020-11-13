## LinkedTransferQueue
TransferQueue 是一个继承了 BlockingQueue 的接口，并且增加若干新的方法。LinkedTransferQueue 是 TransferQueue 接口的实现类，其定义为一个无界的队列，具有先进先出(FIFO)的特性。  
  
有人这样评价它："TransferQueue是是ConcurrentLinkedQueue、SynchronousQueue (公平模式下)、无界的LinkedBlockingQueues等的超集。"  
  
LinkedTransferQueue实现了一个重要的接口TransferQueue，该接口含有下面几个重要方法：  
1. transfer(E e)：若当前存在一个正在等待获取的消费者线程，即立刻移交之；否则，会插入当前元素e到队列尾部，并且等待进入阻塞状态，到有消费者线程取走该元素。
2. tryTransfer(E e)：若当前存在一个正在等待获取的消费者线程（使用take()或者poll()函数），使用该方法会即刻转移/传输对象元素e；若不存在，则返回false，并且不进入队列。这是一个不阻塞的操作。
3. tryTransfer(E e, long timeout, TimeUnit unit)：若当前存在一个正在等待获取的消费者线程，会立即传输给它；否则将插入元素e到队列尾部，并且等待被消费者线程获取消费掉；若在指定的时间内元素e无法被消费者线程获取，则返回false，同时该元素被移除。
4. hasWaitingConsumer()：判断是否存在消费者线程。
5. getWaitingConsumerCount()：获取所有等待获取元素的消费线程数量。　　
6. size()：因为队列的异步特性，检测当前队列的元素个数需要逐一迭代，可能会得到一个不太准确的结果，尤其是在遍历时有可能队列发生更改。
7. 批量操作：类似于addAll，removeAll, retainAll, containsAll, equals, toArray等方法，API不能保证一定会立刻执行。因此，我们在使用过程中，不能有所期待，这是一个具有异步特性的队列。  
  
LinkedTransferQueue采用的一种预占模式。意思就是消费者线程取元素时，如果队列为空，那就生成一个节点（节点元素为null）入队，然后消费者线程park住，后面生产者线程入队时发现有一个元素为null的节点，生产者线程就不入队了，直接就将元素填充到该节点，唤醒该节点上park住线程，被唤醒的消费者线程拿货走人。这就是预占的意思：有就拿货走人，没有就占个位置等着，等到或超时。  
  
### TransferQueue
LinkedTransferQueue 实现了 TransferQueue 接口，这个接口继承了 BlockingQueue。之前 BlockingQueue 是队列满时再入队会阻塞，而这个接口实现的功能是队列不满时也可以阻塞，实现一种有阻塞的入队功能。而这个接口在之前 SynChronousQueue 内种也有体现。  
