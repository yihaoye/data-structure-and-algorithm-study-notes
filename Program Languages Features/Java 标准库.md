来源：https://jiges.github.io/2017/11/15/%E4%BA%86%E8%A7%A3java%E6%A0%87%E5%87%86%E7%B1%BB%E5%BA%93/  

## 参考jdk8标准

在阅读java源码之前，首先要了解java各个类库有什么样的作用，各个类库之前又有什么样的关联。

### java.applet包
包括Applet类和几个接口，用于创建Java小程序，处理小程序与浏览器之间的相互作用，包括声音图像等多媒体的处理。扩展包javax.swing增加了JApplet，该类派生自Applet，是其扩展版。applet现在主流浏览器几乎不用，可以忽略。

### java.awt包
该包包括许多Java早期的图形界面设计相关的类与接口，包括定义字体、颜色、集合绘图和图像显示等。

### java.beans 包
包含与开发 beans 有关的类，即基于 JavaBeans 架构的组件。 java反射就用到了此包。

### java.io包
该包包括处理字节数据流(InputStream, OutputStream)，字符数据流(Reader, Writer)，文件操作类(File)等，实现各种不同的输入输出功能。

### java.lang包
是提供利用 Java 编程语言进行程序设计的基础类。编程用到该包中的类时，无需使用import语句引入他们，由编译器自动引入。
该类包含最重要的类Object、数据类型的包装类 (Boolean,Character, Double, Float, Integer, Long 等)、数学类 (Math)、系统类(System)和运行时类(Runtime)、字符串类 String, StringBuffer)、异常处理类(Throwable, Exception,Error)、线程类 (Thread, Runnable接口)、类操作类(Class)、annotation包等。

### java.math包
用来处理任意精度的整形或浮点型数据的。BigDecimal和BigInteger等。

### java.net包
用于网络通信，实现网络功能。

### java.nio包
Java NIO是java 1.4之后新出的一套IO接口，这里的的新是相对于原有标准的Java IO和Java Networking接口。NIO提供了一种完全不同的操作方式。

* Java NIO: Channels and Buffers - 标准的IO编程接口是面向字节流和字符流的。而NIO是面向通道和缓冲区的，数据总是从通道中读到buffer缓冲区内，或者从buffer写入到通道中。
* Java NIO: Non-blocking IO - Java NIO使我们可以进行非阻塞IO操作。比如说，单线程中从通道读取数据到buffer，同时可以继续做别的事情，当数据读取到buffer中后，线程再继续处理数据。写数据也是一样的。
* Java NIO: Selectors - NIO中有一个“slectors”的概念。selector可以检测多个通道的事件状态（例如：链接打开，数据到达）这样单线程就可以操作多个通道的数据。

### java.rmi包
提供远程方法调用接口。

### java.security包
为安全框架提供类和接口。

### java.sql包
用于数据库操作的一些类。

### java.text包
提供以与自然语言无关的方式来处理文本、日期、数字和消息的类和接口。典型的DateFormat就在该包内。

### java.time包
时间处理包。

### java.util包
Java提供一些常用的实用功能类，数据结构的Java实现类。日期时间类（Date，Calender）、随机数类（Random）、向量类（Vector）、堆栈类（Stack）、散列表类（Hashtable）、Java集合框架。

### javax包
javax是java的扩展包,如j2ee 中的类库，包括servlet，jsp，ejb，数据库相关的一些东西，xml等。
