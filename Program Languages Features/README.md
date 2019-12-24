## 概述
在一些编程语言里，如 Java，很容易因为语言特性如`引用`的认知、使用错误，导致算法与数据结构正确的情况下题目解不出来或总是通不过测试却又找不出原因，或不懂得善用语言特性而无法使用简单的算法、数据结构与语言特性搭配来轻松解决问题（例子如`引用`配合`队列`）。  
  
### Reference（引用）
错误例子：  
```java
void foo(String a) {
    a = new String('abc');
}

String a = new String('123');
foo(a); // Actually result to outside "a" is still '123' since java only support pass by value. The "a" inside foo() is actually a copy (pass by value), so foo(a) return 'abc' but ouside "a" still reference to '123', inside "a" and outside "a" are different references.
```
  
Pass By Value vs Pass By Reference:  
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

public static void foo(A<String> a)
{
   a.set('abc');
}

public static void main(String[] args)
{
  A<String> a = new A<>('123');
  foo(a);
  System.out.println(a.get()); // a.get() => 'abc'
}
```
  
  
参考：https://www.youtube.com/watch?v=BCIYdW73-kc  
