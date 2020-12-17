// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319524892705
/*
提供一种方法顺序访问一个聚合对象中的各个元素，而又不需要暴露该对象的内部表示。

迭代器模式（Iterator）实际上在 Java 的集合类中已经广泛使用了。以 List 为例，要遍历 ArrayList，即使知道它的内部存储了一个 Object[] 数组，也不应该直接使用数组索引去遍历，因为这样需要了解集合内部的存储结构。
如果使用 Iterator 遍历，那么，ArrayList 和 LinkedList 都可以以一种统一的接口来遍历：

List<String> list = ...
for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
    String s = it.next();
}
实际上，因为 Iterator 模式十分有用，因此，Java允许直接把任何支持 Iterator 的集合对象用 foreach 循环写出来：

List<String> list = ...
for (String s : list) {

}
然后由 Java 编译器完成 Iterator 模式的所有循环代码。
*/
// 如何实现一个 Iterator 模式？以一个自定义的集合为例，通过 Iterator 模式实现倒序遍历：
// 实现 Iterator 模式的关键是返回一个 Iterator 对象，该对象知道集合的内部结构，因为它可以实现倒序遍历。使用 Java 的内部类实现这个 Iterator：
public class ReverseArrayCollection<T> implements Iterable<T> {
    // 以数组形式持有集合:
    private T[] array;

    public ReverseArrayCollection(T... objs) {
        this.array = Arrays.copyOfRange(objs, 0, objs.length);
    }

    public Iterator<T> iterator() {
        return new ReverseIterator();
    }

    class ReverseIterator implements Iterator<T> {
        // 索引位置:
        int index;

        public ReverseIterator() {
            // 创建 Iterator 时,索引在数组末尾:
            this.index = ReverseArrayCollection.this.array.length;
        }

        public boolean hasNext() {
            // 如果索引大于 0,那么可以移动到下一个元素(倒序往前移动):
            return index > 0;
        }

        public T next() {
            // 将索引移动到下一个元素并返回(倒序往前移动):
            index--;
            return array[index];
        }
    }
}
// 使用内部类的好处是内部类隐含地持有一个它所在对象的 this 引用，可以通过 ReverseArrayCollection.this 引用到它所在的集合。
// 上述代码实现的逻辑非常简单，但是实际应用时，如果考虑到多线程访问，当一个线程正在迭代某个集合，而另一个线程修改了集合的内容时，是否能继续安全地迭代，还是抛出 ConcurrentModificationException，就需要更仔细地设计。



// Iterator 模式常用于遍历集合，它允许集合提供一个统一的 Iterator 接口来遍历元素，同时保证调用者对集合内部的数据结构一无所知，从而使得调用者总是以相同的接口遍历各种不同类型的集合。

