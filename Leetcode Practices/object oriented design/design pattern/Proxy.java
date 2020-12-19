// 
/*
为其他对象提供一种代理以控制对这个对象的访问。

代理模式，即 Proxy，它和 Adapter 模式很类似。
回顾 Adapter 模式，它用于把 A 接口转换为 B 接口
public BAdapter implements B {
    private A a;
    public BAdapter(A a) {
        this.a = a;
    }
    public void b() {
        a.a();
    }
}
*/
// 而 Proxy 模式不是把 A 接口转换成 B 接口，它还是转换成 A 接口
public AProxy implements A {
    private A a;
    public AProxy(A a) {
        this.a = a;
    }
    public void a() {
        if (getCurrentUser().isRoot()) {
            this.a.a();
        } else {
            throw new SecurityException("Forbidden");
        }
    }
}
/*
这样一来，通过 Proxy 模式给 A 接口再包一层，就实现了权限检查。（上面的例子：只有符合要求的用户，才会真正调用目标方法，否则，会直接抛出异常）

那为啥不把权限检查的功能直接写到目标实例 A 的内部？
因为编写代码的原则有：
    职责清晰：一个类只负责一件事；
    易于测试：一次只测一个功能。

用 Proxy 实现这个权限检查，可以获得更清晰、更简洁的代码：
    A 接口：只定义接口；
    ABusiness 类：只实现 A 接口的业务逻辑；
    APermissionProxy 类：只实现 A 接口的权限检查代理。

当希望编写其他类型的代理，可以继续增加类似 ALogProxy，而不必对现有的 A 接口、ABusiness 类进行修改。
*/



/*
权限检查只是代理模式的一种应用。Proxy 还广泛应用在：
    远程代理：如 Java RMI
    虚代理：如 JDBC 连接池返回的连接（Connection 对象）
    保护代理
    智能引用
*/



/*
Proxy 模式和 Decorator 模式有些类似。
区别在于：Decorator 模式让调用者自己创建核心类，然后组合各种功能，而 Proxy 模式决不能让调用者自己创建再组合，否则就失去了代理的功能。
Proxy 模式让调用者认为获取到的是核心类接口，但实际上是代理类。
*/


