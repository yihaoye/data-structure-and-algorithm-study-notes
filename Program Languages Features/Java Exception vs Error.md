## Exception 和 Error 有什么区别
Exception 泛指的是异常，Exception 主要分为两种异常，一种是编译期出现的异常，称为 checkedException，一种是程序运行期间出现的异常，称为 uncheckedException，常见的 checkedException 有 IOException，uncheckedException 统称为 RuntimeException，常见的 RuntimeException 主要有 NullPointerException、IllegalArgumentException、ArrayIndex0utofBoundException 等，Exception 可以被捕获。  

Error 是指程序运行过程中出现的错误，通常情况下会造成程序的崩溃，Error 通常是不可恢复 的，Error 不能被捕获。  

### Exception vs RuntimeException
Exception 和 RuntimeException 都是异常，它们有什么区别呢？这是面试的时候经常被问道的。  

概念  
* Exception：受检查的异常，这种异常是强制开发者 catch 或 throw 的异常。遇到这种异常必须进行 catch 或 throw，如果不处理，编译器会报错。比如：IOException。
* RuntimeException：运行时异常，这种异常开发者不需要处理，完全由虚拟机接管。比如常见的 NullPointerException，开发者在写程序时不会进行 catch 或 throw。

RuntimeException 也是继承自 Exception 的，只是虚拟机对这两种异常进行了区分。  

参考：https://www.jianshu.com/p/3a93b03c0885  
