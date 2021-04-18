// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319214514210
/*
保证一个类仅有一个实例，并提供一个访问它的全局访问点。

单例模式（Singleton）的目的是为了保证在一个进程中，某个类有且仅有一个全局唯一实例。

因为这个类只有一个实例，因此，自然不能让调用方使用 new Xyz() 来创建实例了。
所以，单例的构造方法必须是 private，这样就防止了调用方自己创建实例，但是在类的内部，是可以用一个静态字段来引用唯一创建的实例的

Singleton 模式既可以严格实现，也可以以约定的方式把普通类视作单例。
*/



public class Singleton {
    // private static final Singleton INSTANCE = null; // 静态字段引用唯一实例，延迟加载，即在调用方第一次调用 getInstance() 时才初始化全局唯一实例

    // public synchronized static Singleton getInstance() { // 通过静态方法返回实例，这里如果没有 synchronized 关键字，会在多线程中在竞争条件下会创建出多个实例，所以必须对整个方法进行加锁。
    //     if (INSTANCE == null) { // 延迟加载
    //         INSTANCE = new Singleton();
    //     }
    //     return INSTANCE;
    // }

    // 非延迟加载写法
    private static final Singleton INSTANCE = new Singleton();
    public static Singleton getInstance() {
        return INSTANCE;
    }

    private Singleton() { // private 构造方法保证外部无法实例化
        // ...
    }
}
/*
所以，单例模式的实现方式很简单：
    1. 只有 private 构造方法，确保外部无法实例化；
    2. 通过 private static 变量持有唯一实例，保证全局唯一性；
    3. 通过 public static 方法返回此唯一实例，使外部调用方能获取到实例。

上面延迟加载的 getInstance 的加锁会严重影响并发性能。可能有人想因此使用双重检查，类似这样：
public static Singleton getInstance() {
    if (INSTANCE == null) {
        synchronized (Singleton.class) {
            if (INSTANCE == null) {
                INSTANCE = new Singleton();
            }
        }
    }
    return INSTANCE;
}
然而，由于 Java 的内存模型，双重检查在这里不成立。要真正实现延迟加载，只能通过 Java 的 ClassLoader 机制完成。如果没有特殊的需求，使用 Singleton 模式的时候，最好不要延迟加载，这样会使代码更简单。
*/



// 另一种实现 Singleton 的方式是利用 Java 的 enum，因为 Java 保证枚举类的每个枚举都是单例，所以只需要编写一个只有一个枚举的类即可：
public enum World {
	INSTANCE; // 唯一枚举

	private String name = "world";

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

// 枚举类也完全可以像其他类那样定义自己的字段、方法，这样上面这个 World 类在调用方看来就可以这么用：
String name = World.INSTANCE.getName();
// 使用枚举实现 Singleton 还避免了第一种方式实现 Singleton 的一个潜在问题：即序列化和反序列化会绕过普通类的 private 构造方法从而创建出多个实例，而枚举类就没有这个问题。



// 那什么时候应该用 Singleton 呢？实际上，很多程序，尤其是 Web 程序，大部分服务类都应该被视作 Singleton，如果全部按 Singleton 的写法写，会非常麻烦，
// 所以，通常是通过约定让框架（例如 Spring）来实例化这些类，保证只有一个实例，调用方自觉通过框架获取实例而不是 new 操作符：
@Component // 表示一个单例组件
public class MyService {
    // ...
}
// 因此，除非确有必要，否则 Singleton 模式一般以“约定”为主，不会刻意实现它。
