# Quality Assurance
在面试的 Take Home Project 或 OOD Exam 环节里经常会或多或少地考核代码质量以及测试相关，所以也需要特别注意。  
![](./tests.gif)  

## 代码质量
### <代码整洁之道>
* 函数
  * 短小
  * 只做一件事
  * 每个函数一个抽象层级
  * 无副作用（比如封装、访问控制、输入输出参数 Immutable、时序性耦合等等）
  * 分隔指令与询问 - 函数要么做什么事（比如修改对象状态），要么回答什么事（比如返回对象的信息），两样都干通常会导致混乱（其实与下面的使用异常而非返回码一个意思）
  * 避免重复
  * 结构化编程
* 错误处理
  * 使用异常而非返回（错误）码
  * 给出异常发生的环境说明
  * 依调用者需要定义异常类
* 系统
  * 将系统的构造与使用分开

### Google Java Style Guide
* [资料](https://google.github.io/styleguide/javaguide.html)
  * [翻译版](https://github.com/fantasticmao/google-java-style-guide-zh_cn)

### [Javadoc](https://www.cnblogs.com/linj7/p/14339381.html)
Javadoc 是一款能根据源代码中的文档注释来产生 HTML 格式的 API 文档的工具。  
只要在 java 源码中按一定的格式写注释，就可以利用 javadoc 这款工具自动生成配套的 API 文档。  

* Javadoc 注释分类 - Javadoc 注释根据写法和位置的不同，主要分为以下：
  * 写在类/方法上面的 Javadoc 注释
  * 写在字段上面的 Javadoc
  * 写在包上面的 Javadoc
* 文档标记 (block tags) - 常用的标签。文档标记常放于包/类/方法的 Javadoc 注释的第三段
  * `@author, @since, @version, @code, @return, @param, @value, @throws @exception, @link @linkplain @see, @deprecated, <pre>`, etc

## 测试
### <代码整洁之道>
单元测试：
* 可读性（精简、足具表达力、避免重复 -- 比如如生产代码一样合理使用函数）
* 每个测试一句断言
  * 每个测试一个概念（比如一个测试一个独立功能）
* F.I.R.S.T
  * Fast - 测试代码可以快速运行
  * Independent - 测试之间相互独立（可以单独运行单个测试），一个测试不应为下一个测试设定条件
  * Repeatable - 测试应当可在任何环境中重复通过
  * Self Validating - 测试应该有布尔值输出（不应该依赖查看日志来确认是否通过）
  * Timely - 测试应及时编写（在生产代码之前编写）
* 测试覆盖度

### <软件测试>
* 测试基础
  * 黑盒测试和白盒测试
  * 静态测试和动态测试
* 测试文档

### <软件测试的艺术>
* 功能测试
* 系统测试
  * 性能测试（除逻辑正确性测试之外还需保证性能，比如压力测试）
  * 安全性测试
  * 可靠性测试
  * 兼容性测试

需要考虑的问题是：软件测试是否应先独立地测試每个模块，然后再将这些模块組装成完整的程序？还是先将下一步要测试的模块组装到测试完成的模块集合中，然后再进行测试？第一种方法称为非增量测试或 “崩溃 (big-bang)” 测试，而第二种方法称为增量测试或集成。不同于独立地测试每个模块，增量测试首先將下一个要测试的模块组装到前面已经测试过的模块集合中去。  
增量测试要优于非增量测试，主要原因如下：
* 非增量测试所需的工作量要多一些。
* 如果使用了增量测试，可以较早地发现模块中与不匹配接口、不正确假设相关的编程错误。这是由于尽早地对模块组合进行了集成测试。然而，如果采用非增量测试，只有到了测试过程的最后阶段，模块之间才能 “互相看到”。
* 增量测试，调试会进行得容易一些。
* 增量测试会将测试进行得更彻底。

非增量测试的优势则是如下：
* 非增量测试所占用的机器时间显得少一些。
* 模块测试阶段开始时，如果使用的是非增量测试，就会有更多的机会进行并行操作 (也就是说，所有的模块可以同时测试)。

### Modern Best Practices for Testing in Java
* [资料](https://phauer.com/2019/modern-best-practices-testing-java/)

### Best Practices for Unit Testing in Java
* [资料 1](https://www.developer.com/java/best-practices-unit-testing-java/)
* [资料 2](https://www.baeldung.com/java-unit-testing-best-practices)
