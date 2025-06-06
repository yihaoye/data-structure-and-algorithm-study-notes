## Tool Sets
  
Tool Sets 是 Online / Take Home assessment、算法解题、编码过程中常用的一些经常复用的 Java 和 Python 原子操作的模版（如 Java 数据类型如 String 或 Collection/Collections 等等的操作、Java 数据类型转换、Java 内置工具如 util 数据结构及内置算法、Java 语法如 Comparator 或 compare 或 Iterator 等等）或几个语句的复用模块，包括但不限于常常需要上 StackOverflow 查的语法或算法解题中经常重复使用的一些（预）处理或（未及算法级别的）逻辑实现（如排序、二维 matrix 的 directions 数组及使用、自制的一些常用计算函数、自制/组合的一些特用数据结构、PriorityQueue 初始化及使用、代表 26 个字母的数组初始化等等）。  
  
这里的模版集可用作算法解题中的工具集查询手册。  
  
以下是模版：  
* [数学相关操作与套路](./Math.java) | [Python Math](./math_operation.py)
* [数组与矩阵](./Arrays(Matrix).java)
* [Java 大数操作 (以及 Java 内置高级数学函数)](./BigNumber.java)
* [位操作](./Bitwise.java)
* Java 容器
  * [Collections 进阶操作](./Collections.java)
  * [Map 进阶操作](./Map.java)
* [Clone 与 Copy](./Clone(Copy).java) | [Python Copy](./clone_copy_operation.py)
* [Comparable](./Comparable.java)
* [Comparator](./Comparator.java)
* [数据类型转换](./DataTypeConversion.java)
* 状态机相关
  * [DFA](./FSM(DFA).java)
  * [NFA](./FSM(NFA).java)
* [HashCode](./HashCode.java)
* [I/O 与 Serializable (序列化与反序列化)](./IO(Serializable).java)
  * [管道](./Pipe.java)
  * [Java 文件操作 (URL 与 File)](./File.java) | [Python 文件操作](./file_operation.py)
  * Java 网络操作
    * Java Net lib [Ref 1](https://jenkov.com/tutorials/java-networking/index.html)、[Ref 2](https://www.cnblogs.com/czwbig/p/10018118.html)
      * [HTTP/HTTPS (URL 与 HttpClient)](./HTTP.java) | [Python HTTP/HTTPS](./http_operation.py)
      * [TCP/IP (Socket 与 ServerSocket)](./TCP.java)
      * UDP/IP (DatagramSocket + DatagramPacket)
    * [Java NIO lib Channels](https://jenkov.com/tutorials/java-nio/channels.html)
      * [TCP/IP (SocketChannel 与 ServerSocketChannel)](./TCP.java)
      * UDP/IP (DatagramChannel)
    * [JavaX WebSocket](./WebSocket.java)
  * [Java NIO 与 AIO](./NIO.java) | [Python AIO](./async_io_operation.py)
* 多线程 | [Python Concurrency](./concurrency_operation.py)
* [Scanner](./Scanner.java)
* [流](./Stream.java)
* [JDBC 操作](./JDBC.java)
* [状态压缩](./StateCompression.java)
* [Iterator](./Iterator.java)
* JSON 操作 | [Python JSON](./json_operation.py)
  * JSON 解析器 [完整版](./JSONParser.java)、[简单版](./../Other%20Practices/nested%20data/Solution.java)
* [链表节点](./LinkedListNode.java)
* [Java Time](./Time.java) | [Python Time](./time_operation.py)
* [Java 对象泛操作](./Object.java)
* [队列](./Queue.java)
* [正则表达式](./Regex.java)
* [字符串操作](./String.java)
  * [Unicode](./Unicode.java)
* [元组](./Tuple.java)
* [Java 注解、自定义及其原理](./Annotation.java)
* [更多其他](../Program%20Languages%20Features/README.md)
  
[其他 Java 常用库](https://zhuanlan.zhihu.com/p/54716716)如 SLF4J（比 Log4j 好）、Jackson、JMS、javax.mail、Apache Commons、Netty 等等。  

### Java 数据类型转换
http://interviewquestionjava.blogspot.com/2014/01/java-data-types-conversion-chart.html  
![](./Java%20Data%20Type%20Conversion%20Chart.jpeg)  
