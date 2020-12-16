/*
JAVA reflection is a very powerful tool to inspect the attributes of a class in runtime. For example, we can retrieve the list of public fields of a class using getDeclaredMethods().

In this problem, you will be given a class Solution in the editor. You have to fill in the incompleted lines so that it prints all the methods of another class called Student in alphabetical order. We will append your code with the Student class before running it. The Student class looks like this:

class Student{
    private String name;
    private String id;
    private String email;

    public String getName() {
        return name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void anothermethod(){  }
    ......
    ......
    some more methods
    ......
}
You have to print all the methods of the student class in alphabetical order like this:

anothermethod
getName
setEmail
setId
......
......
some more methods
......
There is no sample input/output for this problem. If you press "Run Code", it will compile it, but it won't show any outputs.

Hint: See the oracle docs for more details about JAVA Reflection Methods and Fields
*/



// other's solution:
public class Solution {
    public static void main(String[] args){
        Class student = Student.class;
        Method[] methods = student.getDeclaredMethods();

        ArrayList<String> methodList = new ArrayList<>();
        for (Method m : methods) {
            methodList.add(m.getName());
        }
        Collections.sort(methodList);
        for (String name : methodList) {
            System.out.println(name);
        }
    }
}



// 什么是反射？
// 反射就是在运行时才知道要操作的类是什么，并且可以在运行时获取类的完整构造，并调用对应的方法。
// 简单例子：
package com.test.api;

public class Apple {
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static void main(String[] args) throws Exception{
        // 正常的调用
        Apple apple = new Apple();
        apple.setPrice(5);
        System.out.println("Apple Price:" + apple.getPrice());
        // 使用反射调用
        Class clz = Class.forName("com.test.api.Apple");
        Method setPriceMethod = clz.getMethod("setPrice", int.class);
        Constructor appleConstructor = clz.getConstructor();
        Object appleObj = appleConstructor.newInstance();
        setPriceMethod.invoke(appleObj, 14);
        Method getPriceMethod = clz.getMethod("getPrice");
        System.out.println("Apple Price:" + getPriceMethod.invoke(appleObj));
    }
}
// 摘录自：https://www.cnblogs.com/chanshuyi/p/head_first_of_reflection.html



// 反射源码解析：https://www.cnblogs.com/chanshuyi/p/head_first_of_reflection.html



// https://www.liaoxuefeng.com/wiki/1252599548343744/  
/*
## Class 类
  
    * JVM 为每个加载的 class 及 interface 创建了对应的 Class 实例来保存 class 及 interface 的所有信息；
    * 获取一个 class 对应的 Class 实例后，就可以获取该 class 的所有信息；
    * 通过 Class 实例获取 class 信息的方法称为反射（Reflection）；
    * JVM 总是动态加载 class，可以在运行期根据条件来控制加载 class。  
*/
Class cls1 = String.class;
String s = "Hello";
Class cls2 = s.getClass();
boolean sameClass = cls1 == cls2; // true


/*
## 访问字段
  
    * Java 的反射 API 提供的 Field 类封装了字段的所有信息：
    * 通过 Class 实例的方法可以获取 Field 实例：getField()，getFields()，getDeclaredField()，getDeclaredFields()；
    * 通过 Field 实例可以获取字段信息：getName()，getType()，getModifiers()；
    * 通过 Field 实例可以读取或设置某个对象的字段，如果存在访问限制，要首先调用 setAccessible(true) 来访问非 public 字段。
    * 通过反射读写字段是一种非常规方法，它会破坏对象的封装。  
*/
Field f = String.class.getDeclaredField("value");
f.getName(); // "value"
f.getType(); // class [B 表示byte[]类型
int m = f.getModifiers();
Modifier.isPublic(m); // false
Modifier.isPrivate(m); // true

Object p = new Person("Xiao Ming");
Class c = p.getClass();
Field f = c.getDeclaredField("name");
f.setAccessible(true); // 调用 Field.setAccessible(true) 的意思是，别管这个字段是不是 public，一律允许访问。
Object value = f.get(p);
System.out.println(value); // "Xiao Ming"
// 上述代码先获取 Class 实例，再获取 Field 实例，然后，用 Field.get(Object) 获取指定实例的指定字段的值。
f.set(p, "Xiao Hong");
System.out.println(p.getName()); // "Xiao Hong"
/*
如果使用反射可以获取 private 字段的值，那么类的封装还有什么意义？  
答案是正常情况下，我们总是通过 p.name 来访问 Person 的 name 字段，编译器会根据 public、protected 和 private 决定是否允许访问字段，这样就达到了数据封装的目的。  
而反射是一种非常规的用法，使用反射，首先代码非常繁琐，其次，它更多地是给工具或者底层框架来使用，目的是在不知道目标实例任何信息的情况下，获取特定字段的值。  
此外，setAccessible(true) 可能会失败。如果JVM运行期存在 SecurityManager，那么它会根据规则进行检查，有可能阻止 setAccessible(true)。例如，某个 SecurityManager 可能不允许对 java 和 javax 开头的 package 的类调用 setAccessible(true)，这样可以保证 JVM 核心库的安全。  
*/


/*
## 调用方法
  
    * Java 的反射 API 提供的 Method 对象封装了方法的所有信息：
    * 通过 Class 实例的方法可以获取 Method 实例：getMethod()，getMethods()，getDeclaredMethod()，getDeclaredMethods()；
    * 通过 Method 实例可以获取方法信息：getName()，getReturnType()，getParameterTypes()，getModifiers()；
    * 通过 Method 实例可以调用某个对象的方法：Object invoke(Object instance, Object... parameters)；
    * 通过设置 setAccessible(true) 来访问非 public 方法；
    * 通过反射调用方法时，仍然遵循多态原则。  
  
能通过 Class 实例获取所有 Field 对象，同样的，可以通过 Class 实例获取所有 Method 信息。Class 类提供了以下几个方法来获取 Method  
*/
Class stdClass = Student.class;
// 获取 public 方法 getScore，参数为 String:
System.out.println(stdClass.getMethod("getScore", String.class));
// 获取继承的 public 方法 getName，无参数:
System.out.println(stdClass.getMethod("getName"));
// 获取 private 方法 getGrade，参数为 int:
System.out.println(stdClass.getDeclaredMethod("getGrade", int.class));

// String 对象:
String s = "Hello world";
// 获取 String substring(int) 方法，参数为 int:
Method m = String.class.getMethod("substring", int.class);
// 在 s 对象上调用该方法并获取结果: 对 Method 实例调用 invoke 就相当于调用该方法，invoke 的第一个参数是对象实例，即在哪个实例上调用该方法，后面的可变参数要与方法参数一致，否则将报错。
String r = (String) m.invoke(s, 6);
// 打印调用结果:
System.out.println(r);

// 如果获取到的 Method 表示一个静态方法，调用静态方法时，由于无需指定实例对象，所以 invoke 方法传入的第一个参数永远为 null。以 Integer.parseInt(String) 为例：
// 获取 Integer.parseInt(String) 方法，参数为 String:
Method m = Integer.class.getMethod("parseInt", String.class);
// 调用该静态方法并获取结果:
Integer n = (Integer) m.invoke(null, "12345");
// 打印调用结果:
System.out.println(n);

// 和 Field 类似，对于非 public 方法，虽然可以通过 Class.getDeclaredMethod() 获取该方法实例，但直接对其调用将得到一个 IllegalAccessException。
// 为了调用非 public 方法，通过 Method.setAccessible(true) 允许其调用
// 此外，setAccessible(true) 可能会失败。如果 JVM 运行期存在 SecurityManager，那么它会根据规则进行检查，有可能阻止 setAccessible(true)。
// 例如，某个 SecurityManager 可能不允许对 java 和 javax 开头的 package 的类调用 setAccessible(true)，这样可以保证 JVM 核心库的安全。

// 多态
// 一个 Person 类定义了 hello() 方法，并且它的子类 Student 也覆写了 hello() 方法，
// 那么，从 Person.class 获取的 Method，作用于 Student 实例时，调用的反射调用方法时，仍然遵循多态原则：即总是调用实际类型的覆写方法（如果存在）- Student:hello


/*
## 调用构造方法

    * Constructor 对象封装了构造方法的所有信息；
    * 通过 Class 实例的方法可以获取 Constructor 实例：getConstructor()，getConstructors()，getDeclaredConstructor()，getDeclaredConstructors()；
    * 通过 Constructor 实例可以创建一个实例对象：newInstance(Object... parameters)； 通过设置 setAccessible(true) 来访问非 public 构造方法。

*/
// 通过反射来创建新的实例，可以调用 Class 提供的 newInstance() 方法：
Person p = Person.class.newInstance();
// 调用 Class.newInstance() 的局限是，它只能调用该类的 public 无参数构造方法。如果构造方法带有参数，或者不是 public，就无法直接通过 Class.newInstance() 来调用。
// 为了调用任意的构造方法，Java 的反射API提供了 Constructor 对象，它包含一个构造方法的所有信息，可以创建一个实例。Constructor 对象和 Method 非常类似，不同之处仅在于它是一个构造方法，并且，调用结果总是返回实例
// 获取构造方法 Integer(int):
Constructor cons1 = Integer.class.getConstructor(int.class);
// 调用构造方法:
Integer n1 = (Integer) cons1.newInstance(123);
System.out.println(n1);
// 获取构造方法 Integer(String)
Constructor cons2 = Integer.class.getConstructor(String.class);
Integer n2 = (Integer) cons2.newInstance("456");
System.out.println(n2);

// 注意 Constructor 总是当前类定义的构造方法，和父类无关，因此不存在多态的问题。
// 调用非 public 的 Constructor 时，必须首先通过 setAccessible(true) 设置允许访问。setAccessible(true) 可能会失败。


/*
## 获取继承关系

通过 Class 对象可以获取继承关系：
    * Class getSuperclass()：获取父类类型；
    * Class[] getInterfaces()：获取当前类实现的所有接口。

通过 Class 对象的 isAssignableFrom() 方法可以判断一个向上转型是否可以实现。
*/
// Integer 的父类类型是 Number，Number 的父类是 Object，Object 的父类是 null。除 Object 外，其他任何非 interface 的 Class 都必定存在一个父类类型。
// 获取 interface。由于一个类可能实现一个或多个接口，通过 Class 我们就可以查询到实现的接口类型。例如，查询 Integer 实现的接口
Class s = Integer.class;
Class[] is = s.getInterfaces(); // 如果一个类没有实现任何 interface，那么 getInterfaces() 返回空数组
for (Class i : is) System.out.println(i);
// 要特别注意：getInterfaces() 只返回当前类直接实现的接口类型，并不包括其父类实现的接口类型
// 此外，对所有 interface 的 Class 调用 getSuperclass() 返回的是 null，获取接口的父接口要用 getInterfaces()

// 当我们判断一个实例是否是某个类型时，正常情况下，使用 instanceof 操作符：
Object n = Integer.valueOf(123);
boolean isDouble = n instanceof Double; // false
boolean isInteger = n instanceof Integer; // true
boolean isNumber = n instanceof Number; // true
boolean isSerializable = n instanceof java.io.Serializable; // true
// 如果是两个 Class 实例，要判断一个向上转型是否成立，可以调用 isAssignableFrom()：
Integer.class.isAssignableFrom(Integer.class); // true，因为 Integer 可以赋值给 Integer
Number.class.isAssignableFrom(Integer.class); // true，因为 Integer 可以赋值给 Number
Object.class.isAssignableFrom(Integer.class); // true，因为 Integer 可以赋值给 Object
Integer.class.isAssignableFrom(Number.class); // false，因为 Number 不能赋值给 Integer


/*（重要）
## 动态代理

    * Java 标准库提供了动态代理功能，允许在运行期动态创建一个接口的实例；
    * 动态代理是通过 Proxy 创建代理对象，然后将接口方法“代理”给 InvocationHandler 完成的。

*/
// 所谓动态代理，是和静态相对应的。来看静态代码写法
// 定义接口：
public interface Hello {
    void morning(String name);
}
// 编写实现类：
public class HelloWorld implements Hello {
    public void morning(String name) {
        System.out.println("Good morning, " + name);
    }
}
// 创建实例，转型为接口并调用：
Hello hello = new HelloWorld();
hello.morning("Bob");
// 这种方式就是通常编写代码的方式。

// 还有一种方式是动态代码，仍然先定义了接口 Hello，但是并不去编写实现类，而是直接通过 JDK 提供的一个 Proxy.newProxyInstance() 创建了一个 Hello 接口对象。
// 这种没有实现类但是在运行期动态创建了一个接口对象的方式，称为动态代码。JDK 提供的动态创建接口对象的方式，就叫动态代理。
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Hello {
    void morning(String name);
}

InvocationHandler handler = new InvocationHandler() {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // System.out.println(proxy);
        System.out.println(method);
        if (method.getName().equals("morning")) {
            System.out.println("Good morning, " + args[0]);
        }
        return null;
    }
};
Hello hello = (Hello) Proxy.newProxyInstance(
    Hello.class.getClassLoader(), // 传入 ClassLoader
    new Class[] { Hello.class }, // 传入要实现的接口
    handler); // 传入处理调用方法的 InvocationHandler
hello.morning("Bob");
/*
在运行期动态创建一个 interface 实例的方法如下：
    1. 定义一个 InvocationHandler 实例，它负责实现接口的方法调用；
    2. 通过 Proxy.newProxyInstance() 创建 interface 实例，它需要 3 个参数：
        1. 使用的 ClassLoader，通常就是接口类的 ClassLoader；
        2. 需要实现的接口数组，至少需要传入一个接口进去；
        3. 用来处理接口方法调用的 InvocationHandler 实例。
    3. 将返回的 Object 强制转型为接口。
动态代理实际上是 JDK 在运行期动态创建 class 字节码并加载的过程。
*/

// 把上面的动态代理改写为静态实现类大概长这样：
public class HelloDynamicProxy implements Hello {
    InvocationHandler handler;
    public HelloDynamicProxy(InvocationHandler handler) {
        this.handler = handler;
    }
    public void morning(String name) {
        handler.invoke(
           this,
           Hello.class.getMethod("morning", String.class),
           new Object[] { name });
    }
}
// 其实就是 JDK 帮我们自动编写了一个上述类（不需要源码，可以直接生成字节码），并不存在可以直接实例化接口的黑魔法。
