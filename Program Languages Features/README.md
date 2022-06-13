# 概述
在一些编程语言里，如 Java，很容易因为语言特性如`引用`的认知、使用错误，导致算法与数据结构正确的情况下题目解不出来或总是通不过测试却又找不出原因，或不懂得善用语言特性而无法使用简单的算法、数据结构与语言特性搭配来轻松解决问题（例子如`引用`配合`队列`）。  
以下列出在算法解题及面试中的 3 种常用编程语言的特性及它们的一些不同点。  
  
## Java 特性
[Java Core](./Java%20Core.gif) 在算法解题及面试中比较常用、重要的知识点有：  
* [集合](../Tool%20Sets/Collections.java)
* [异常](../HackerRank%20Practises/java/easy/Java%20Exception%20Handling.java)
* [并发](../Computer%20System%20Layer/并发与并行(Java)/)
* [日期与时间](../HackerRank%20Practises/java/easy/Java%20Date%20and%20Time.java)
* [IO](../Tool%20Sets/IO(Serializable).java)
* [反射](../HackerRank%20Practises/java/easy/Java%20Reflection%20-%20Attributes.java)
* [泛型](../HackerRank%20Practises/java/easy/Java%20Generics.java)
* [JVM](../Computer%20System%20Layer/JVM/)  
  
另外，需熟记 [Java 的关键字](Java%20关键字.pdf)  
  
## Python 特性
ToDo ...  
  
## C++ 特性
ToDo ...  
  
## 编程语言间的差别
有的的语言有组合数计算的 API，比如 Python、Go，有的没有比如 Java、C++。  
  
### 内置工具对比
![](./C++%20Java%20Python%20内置数据结构比较.png)  
  
### Reference（引用）
错误例子：  
```java
void foo(String a) {
    a = new String('abc');
}

String a = new String('123');
foo(a); 
// Actually result to outside "a" is still '123' since java only support pass by value. 
// The "a" inside foo() is actually a copy (pass by value), 
// so foo(a) return 'abc' but ouside "a" still reference to '123', 
// inside "a" and outside "a" are different references.
```
  
**[重要] Pass By Value vs Pass By Reference:**  
* Java / Python always pass by value (比如 Java，即使传参是基础类对象如 Integer 也一样)
* C++ / C# support boths (比如 C++ 可通过设定参数为指针类型实现 Pass By Reference)  
  
对于 Java / Python 这类 always pass by value 的语言，如果想 pass by reference，可用以下方法：  
1. 使用 array / attribute（除了数组也许其他 collection 类型如队列也可以？）  
```java
void foo(String[] a) {
    a = new String[]{'abc'};
}

String[] a = new String[]{'123'};
foo(a); // then outside a[0] == 'abc'
```
2. 使用 class member（类成员）  
```java
public class A<T> {
    private T val;

    public A(T initVal) {
       val = initVal;
    }

    public void set(T newVal) {
       val = newVal;
    }

    public T get() {
       return val;
    }
}

public static void foo(A<String> a) {
   a.set('abc');
}

public static void main(String[] args) {
  A<String> a = new A<>('123');
  foo(a);
  System.out.println(a.get()); // a.get() => 'abc'
}
```  
  
以上参考：https://www.youtube.com/watch?v=BCIYdW73-kc  
  
### 运算符优先级
#### 基本规则
1. 一元运算符优于二元运算符
2. 乘除加减优于大部分运算符
3. 位运算优于逻辑运算

#### Java

[Java Operators](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html)

|  类别   | 列表  |
|  ----   | ----  |
|  括号  | ()     |
|  前后缀与一元运算符  | ++, --, !, ~, +, - |
|  乘除加减  | *, /, +, - |
|  位移  | >>, << |
|  判断与比较  | >, >=, <, <=, ==,  |
|  位运算  | &, ^, \|, &&, \|\| |
|  逻辑运算  | &&, \|\| |
|  三元运算符  | ? : |
|  赋值  | =, +=, -=, ^=, >>= |
