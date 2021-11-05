## Exception 和 Error 有什么区别
Exception 泛指的是异常，Exception 主要分为两种异常，一种是编译期出现的异常，称为 checkedException，一种是程序运行期间出现的异常，称为 uncheckedException，常见的 checkedException 有 IOException，uncheckedException 统称为 RuntimeException，常见的 RuntimeException 主要有 NullPointerException、IllegalArgumentException、ArrayIndex0utofBoundException 等，Exception 可以被捕获。  

Error 是指程序运行过程中出现的错误，通常情况下会造成程序的崩溃，Error 通常是不可恢复 的，Error 不能被捕获。  
