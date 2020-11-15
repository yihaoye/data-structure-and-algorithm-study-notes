熟悉 Java 并发编程的都知道，JMM(Java 内存模型) 中的 happen-before(简称 hb)规则，该规则定义了 Java 多线程操作的有序性和可见性，防止了编译器重排序对程序结果的影响。按照官方的说法：  
当一个变量被多个线程读取并且至少被一个线程写入时，如果读操作和写操作没有 HB 关系，则会产生数据竞争问题。要想保证操作 B 的线程看到操作 A 的结果（无论 A 和 B 是否在一个线程），那么在 A 和 B 之间必须满足 HB 原则，如果没有，将有可能导致重排序。当缺少 HB 关系时，就可能出现重排序问题（重排序是对程序执行效率的优化 - 除了 CPU 会对指令重排序来优化性能之外，Java JIT 也会对指令进行重排序。重排序也会发生在单线程程序，而只是单线程程序的重排显然不会出问题但多线程程序会出问题）。  
![](./Happens-Before%20Relation.png)  
  
### HB 有哪些规则
这个大家都非常熟悉了应该，大部分书籍和文章都会介绍，这里稍微回顾一下：  
1. 程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；
2. 锁定规则：在监视器锁上的解锁操作必须在同一个监视器上的加锁操作之前执行。
3. volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作；
4. 传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C；
5. 线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作；
6. 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生；
7. 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行；
8. 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始；  
  
以上参考自[链接](https://ifeve.com/java-%E4%BD%BF%E7%94%A8-happen-before-%E8%A7%84%E5%88%99%E5%AE%9E%E7%8E%B0%E5%85%B1%E4%BA%AB%E5%8F%98%E9%87%8F%E7%9A%84%E5%90%8C%E6%AD%A5%E6%93%8D%E4%BD%9C/)  
  
另外，虽然同一个线程的同一个代码块内的语句虽然均可能重排序，但写好的volatile变量读操作语句之后的普通变量读操作语句均不会被排序到volatile变量读操作语句之前（参考下图的 Thread 2 中的 takeFrame 方法/代码块的 while(!hasNewFrame) 检测），而类似的，volatile变量写操作语句之前的普通变量操作语句均不会被排序到volatile变量写操作语句之后（参考下图的 Thread 1 中的 storeFrame 方法/代码块，this.frame 和 this.framesStoredCount 的操作语句顺序虽然可能会被重排序但必然皆在 this.hasNewFrame 的操作语句之前）。  
![](./Happens-Before%20Relation%202.png)  
以上规则同样适用于synchronized代码块，比如synchronized代码块结尾之前的任意内存变量（无论Heap还是Thread Stack）的写操作语句都不会被重排序到synchronized代码块结尾之后，而synchronized代码块开头之后任意内存变量的读操作语句皆不会被重排序到synchronized代码块开头之前。另外值得一提的是每次程序进入synchronized代码块开头时都会从Heap而不是Thread Stack中读取变量，且在synchronized代码块结尾时都会保证把任何变量的写操作写入Heap而不是只是写入Thread Stack（但是synchronized代码块之外的变量写操作不保证能同时写入Heap，因此当另一个Thread即使从Heap读取相关共享变量时很有可能读到的也是未更新的数据）。以上规则参考下图。  
![](./Happens-Before%20Relation%203.png)  
其他 HB 规则：  
![](./Happens-Before%20Relation%204.png)  
  
## happens-before 俗解
happens-before偏序关系。  
![](./Happens-Before%20Relation%205.png)  
  
synchronized、大部分锁，众所周知的一个功能就是使多个线程互斥/串行的（共享锁允许多个线程同时访问，如读锁）访问临界区，但他们的第二个功能 —— 保证变量的可见性 —— 常被遗忘。  
  
为什么存在可见性问题？简单介绍下。相对于内存，CPU的速度是极高的，如果CPU需要存取数据时都直接与内存打交道，在存取过程中，CPU将一直空闲，这是一种极大的浪费，所以，现代的CPU里都有很多寄存器，多级cache，他们比内存的存取速度高多了。某个线程执行时，内存中的一份数据，会存在于该线程的工作存储中（working memory，是cache和寄存器的一个抽象，这个解释源于《Concurrent Programming in Java: Design Principles and Patterns, Second Edition》§2.2.7，原文：Every thread is defined to have a working memory (an abstraction of caches and registers) in which to store values. 有不少人觉得working memory是内存的某个部分，这可能是有些译作将working memory译为工作内存的缘故，为避免混淆，这里称其为工作存储，每个线程都有自己的工作存储），并在某个特定时候回写到内存。单线程时，这没有问题，如果是多线程要同时访问同一个变量呢？内存中一个变量会存在于多个工作存储中，线程1修改了变量a的值什么时候对线程2可见？此外，编译器或运行时为了效率可以在允许的时候对指令进行重排序，重排序后的执行顺序就与代码不一致了，这样线程2读取某个变量的时候线程1可能还没有进行写入操作呢，虽然代码顺序上写操作是在前面的。这就是可见性问题的由来。  
  
我们无法枚举所有的场景来规定某个线程修改的变量何时对另一个线程可见。但可以制定一些通用的规则，这就是happens-before。它是一个偏序关系，Java内存模型中定义了许多Action，有些Action之间存在happens-before关系（并不是所有Action两两之间都有happens-before关系）。“ActionA happens-before ActionB”这样的描述很扰乱视线，是不是？OK，换个描述，如果ActionA happens-before ActionB，我们可以记作hb(ActionA,ActionB)或者记作ActionA < ActionB，这货在这里已经不是小于号了，它是偏序关系，是不是隐约有些离散数学的味道，下面都用hb(ActionA,ActionB)这种方式来表述。  
  
从Java内存模型中取两条happens-before关系来看：  
* An unlock on a monitor happens-before every subsequent lock on that monitor.  
* A write to a volatile field happens-before every subsequent read of that volatile.  
  
“对一个monitor的解锁操作happens-before后续对同一个monitor的加锁操作”、“对某个volatile字段的写操作happens-before后续对同一个volatile字段的读操作”……不知所云……就是这个心情。是不是说解锁操作要先于锁定操作发生？这有违常规啊。确实不是这么理解的。happens-before规则不是描述实际操作的先后顺序，它是用来描述可见性的一种规则，下面我给上述两条规则换个说法：  
* 如果线程1解锁了monitor a，接着线程2锁定了a，那么，线程1解锁a之前的写操作都对线程2可见（线程1和线程2可以是同一个线程）。  
* 如果线程1写入了volatile变量v（这里和后续的“变量”都指的是对象的字段、类字段和数组元素），接着线程2读取了v，那么，线程1写入v及之前的写操作都对线程2可见（线程1和线程2可以是同一个线程）。  
  
是不是很简单，瞬间觉得这篇文章弱爆了，说了那么多，其实就是在说“如果hb(a,b)，那么a及之前的写操作在另一个线程t1进行了b操作时都对t1可见（同一个线程就不会有可见性问题，下面不再重复了）”。虽然弱爆了，但还得有始有终，是不是，继续来，再看两条happens-before规则：  
* All actions in a thread happen-before any other thread successfully returns from a join() on that thread.  
* Each action in a thread happens-before every subsequent action in that thread.  

通俗版：  
* 线程t1写入的所有变量（所有action都与那个join有hb关系，当然也包括线程t1终止前的最后一个action了，最后一个action及之前的所有写入操作，所以是所有变量），在任意其它线程t2调用t1.join()成功返回后，都对t2可见。
* 线程中上一个动作及之前的所有写操作在该线程执行下一个动作时对该线程可见（也就是说，同一个线程中前面的所有写操作对后面的操作可见）  

大致都是这个样子的解释。  
  
happens-before关系有个很重要的性质，就是传递性，即，如果hb(a,b),hb(b,c)，则有hb(a,c)。  
Java内存模型中只是列出了几种比较基本的hb规则，在Java语言层面，又衍生了许多其他happens-before规则，如ReentrantLock的unlock与lock操作，又如AbstractQueuedSynchronizer的release与acquire，setState与getState等等。  
  
[以上参考](http://ifeve.com/easy-happens-before/)  
  
## synchronized 关键字
synchronized 代码块/方法一次只能由一个线程执行。  
  

