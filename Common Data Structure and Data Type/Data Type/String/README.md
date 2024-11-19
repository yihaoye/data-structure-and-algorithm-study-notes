# 字符串
在 Java 中，常见的字符串类型包括 String、StringBuffer 和 StringBuilder。  
从 String 的源码可以看到，String 使用数组存储字符串的内容，该数组使用关键词 final 修饰，因此数组内容不可变，使用 String 定义的字符串的值也是不可变的。  
由于 String 类型的值不可变，因此每次对 String 的修改操作都会创建新的 String 对象，导致效率低下且占用大量内存空间。  

## StringBuffer 和 StringBuilder
StringBuffer 和 StringBuilder 都是 AbstractStringBuilder 的子类，同样使用数组存储字符串的内容，由于数组没有使用关键词 final 修饰，因此数组内容可变，StringBuffer 和 StringBuilder 都是可变类型，可以对字符串的内容进行修改，且不会因为修改而创建新的对象。

在需要经常对字符串的内容进行修改的情况下，应使用 StringBuffer 或 StringBuilder，在时间和空间方面都显著优于 String。

StringBuffer 和 StringBuilder 有哪些区别呢？从源码可以看到，StringBuffer 对定义的方法或者调用的方法使用了关键词 synchronized 修饰，而 StringBuilder 的方法没有使用关键词 synchronized 修饰。由于 StringBuffer 对方法加了同步锁，因此其效率略低于 StringBuilder，但是在多线程的环境下，StringBuilder 不能保证线程安全，因此 StringBuffer 是更优的选择。
