// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319195639841
// https://refactoringguru.cn/design-patterns/prototype
/**
原型模式，即 Prototype，是指创建新对象的时候，根据现有的一个原型来创建。

如果有一个对象，并希望生成与其完全相同的一个复制品，该如何实现呢？ 首先，必须新建一个属于相同类的对象。然后，必须遍历原始对象的所有成员变量，并将成员变量值复制到新对象中。
但有个问题。并非所有对象都能通过这种方式进行复制，因为有些对象可能拥有私有成员变量，它们在对象本身以外是不可见的。
直接复制还有另外一个问题。因为必须知道对象所属的类才能创建复制品，所以代码必须依赖该类。即使可以接受额外的依赖性，那还有另外一个问题：
有时只知道对象所实现的接口，而不知道其所属的具体类，比如可向方法的某个参数传入实现了某个接口的任何对象。

原型模式将克隆过程委派给被克隆的实际对象。模式为所有支持克隆的对象声明了一个通用接口，该接口让你能够克隆对象，同时又无需将代码和对象所属类耦合。
通常情况下，这样的接口中仅包含一个 clone() 方法。
 */
// 举个例子：如果已经有了一个 String[] 数组，想再创建一个一模一样的 String[] 数组，怎么写？

// 实际上创建过程很简单，就是把现有数组的元素复制到新数组。如果把这个创建过程封装一下，就成了原型模式。用代码实现如下：
// 原型:
String[] original = { "Apple", "Pear", "Banana" };
// 新对象:
String[] copy = Arrays.copyOf(original, original.length);
// 对于普通类，如何实现原型拷贝？Java 的 Object提供了一个 clone() 方法，它的意图就是复制一个新的对象出来，需要实现一个 Cloneable 接口来标识一个对象是 “可复制” 的：

public class Student implements Cloneable {
    private int id;
    private String name;
    private int score;

    // 复制新对象并返回:
    public Object clone() {
        Student std = new Student();
        std.id = this.id;
        std.name = this.name;
        std.score = this.score;
        return std;
    }
}
// 使用的时候，因为 clone() 的方法签名是定义在Object中，返回类型也是 Object，所以要强制转型，比较麻烦：
Student std1 = new Student();
std1.setId(123);
std1.setName("Bob");
std1.setScore(88);
// 复制新对象:
Student std2 = (Student) std1.clone();
System.out.println(std1);
System.out.println(std2);
System.out.println(std1 == std2); // false

// 实际上，使用原型模式更好的方式是定义一个 copy() 方法，返回明确的类型：
public class Student {
    private int id;
    private String name;
    private int score;

    public Student copy() {
        Student std = new Student();
        std.id = this.id;
        std.name = this.name;
        std.score = this.score;
        return std;
    }
}
// 原型模式应用不是很广泛，因为很多实例会持有类似文件、Socket 这样的资源，而这些资源是无法复制给另一个对象共享的，只有存储简单类型的 “值” 对象可以复制。
