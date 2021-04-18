// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319170474017
/*
定义一个用于创建对象的接口，让子类决定实例化哪一个类。Factory Pattern 使一个类的实例化延迟到其子类。

工厂模式即 Factory Pattern，是一种对象创建型模式。

工厂模式的目的是使得创建对象和使用对象是分离的，并且客户端总是引用抽象工厂和抽象产品

工厂模式可以隐藏创建产品的细节，且不一定每次都会真正创建产品，完全可以返回缓存的产品，从而提升速度并减少内存消耗（例如 Java 标准库 Integer.valueOf(xxx)）。
*/



// 实例，假设希望实现一个解析字符串到 Number 的 Factory
public interface NumberFactory {
    Number parse(String s); // 创建方法

    static NumberFactory getFactory() { // 获取工厂实例，供客户端使用
        return impl;
    }

    static NumberFactory impl = new NumberFactoryImpl();
}

public class NumberFactoryImpl implements NumberFactory {
    public Number parse(String s) {
        return new BigDecimal(s);
    }
}

// 在客户端中，只需要和工厂接口 NumberFactory 以及抽象产品 Number 打交道：
NumberFactory factory = NumberFactory.getFactory();
Number result = factory.parse("123.456");



// 实际上大多数情况下并不需要抽象工厂，而是通过静态方法直接返回产品，即实际更常用的静态工厂方法（Static Factory Method）（静态工厂方法广泛地应用在 Java 标准库中例如 Integer.valueOf(xxx)，又或 Java 标准库和第三方库的许多 getInstance(String) 等等）：
public class NumberFactory {
    public static Number parse(String s) {
        return new BigDecimal(s);
    }
}
