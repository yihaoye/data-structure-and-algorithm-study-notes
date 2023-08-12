/*
通过 hashCode() 方法返回的是 hashcode 码。这样一来，当集合要添加新的元素时，先调用这个元素的 hashCode 方法，就一下子能定位到它应该放置的物理位置上。
如果这个位置上没有元素，它就可以直接存储在这个位置上，不用再进行任何比较了；如果这个位置上已经有元素了，就调用它的 equals 方法与新元素进行比较，相同的话就不存了，不相同就散列其它的地址。
所以这里存在一个冲突解决的问题。这样一来实际调用 equals 方法的次数就大大降低了，几乎只需要一两次。

https://www.cnblogs.com/0201zcr/p/4810813.html
https://www.cnblogs.com/0201zcr/p/4769108.html
当程序试图将一个 key-value 对放入 HashMap 中时，程序首先根据该 key 的 hashCode() 返回值决定该 Entry 的存储位置：如果两个 Entry 的 key 的 hashCode() 返回值相同，
那它们的存储位置相同。如果这两个 Entry 的 key 通过 equals 比较返回 true，新添加 Entry 的 value 将覆盖集合中原有 Entry 的 value，但 key 不会覆盖。
如果这两个 Entry 的 key 通过 equals 比较返回 false，新添加的 Entry 将与集合中原有 Entry 形成 Entry 链，而且新添加的 Entry 位于 Entry 链的头部。

如果重写了 equals() 方法一定要写重写 hashcode() 方法，也就是说为什么要保证 equals() 方法比较相等的对象，其 hashcode() 方法返回值也要一样才可以。
但是反过来，hashCode() 相等时，equals() 不一定相等（也不需要一定相等）。
*/

int val1 = 10;
Integer.hashCode(val1);

String val2 = "test";
val2.hashCode();



public class Obj {
    private long id;
    private String val1;
    private String val2;
 
    // standard getters/setters/constructors
        
    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (int) id;
        hash = 31 * hash + (val1 == null ? 0 : val1.hashCode());
        hash = 31 * hash + (val2 == null ? 0 : val2.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return id == obj.id 
          && (val1.equals(obj.val1) 
          && val2.equals(obj.val2));
    }
}



/*
如果以对象作为 HashMap 的 Key 时，hashCode 在当中会起到什么样的作用？
如下示例代码，Book 对象 b1 和 b2 的传参是一样的，但是它们的 hashCode 不同。
这是因为 b1 和 b2 是通过两次 new 得到的对象，那么它们在堆内存中属于两个不同的对象，对应的堆内存地址自然不同。
因此，由 hashCode 标识的内存地址也就不可能相同了。

如果将 key=b1, value="test" 的键值对存放在 HashMap 中，使用 b1 可以得到 "test"，而使用 b2 得到的是 null。
这是因为存放 b1 时，HashMap 内部的 put() 会根据对象的 hashCode 计算得到它在内部 table 中的索引，然后将其存放在 table 对应索引的位置上。
由于 b1 和 b2 两者的 hashCode 不同，那么计算得到的索引位置不同，自然使用 b2 就无法获取到 "test"。

原文链接：https://blog.csdn.net/Forlogen/article/details/108000662
*/
HashMap<Book, String> map = new HashMap<>();

Book b1 = new Book(10);
Book b2 = new Book(10);

System.out.println("b1 hashCode is: " + b1.hashCode() + " and b2 hashCode is: " + b2.hashCode());
// b1 hashCode is: 1265094477 and b2 hashCode is: 2125039532

map.put(b1, "test");
System.out.println(map.get(b1)); // test
System.out.println(map.get(b2)); // null



// 对数组进行哈希计算
import java.util.*;
Arrays.sort(array);
int hashCode = Objects.hash(array);
