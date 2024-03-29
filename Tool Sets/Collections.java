
/*
    https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html
    https://www.liaoxuefeng.com/wiki/1252599548343744/1299919855943714
    注意 Collections 和 Collection 不一样，
    Collection 是接口或称容器，
    Collections 是 JDK 提供的工具类，同样位于 java.util 包中。它提供了一系列静态方法，能更方便地操作各种集合。

    Collections 类提供了一组工具方法来方便使用集合类：    
*/

// 取极值、排序
List<Integer> list = new ArrayList<>(Arrays.asList(5, 10, 9, 3, 7));
Collections.min(list); // min(Collection, Comparator)
Collections.max(list); // max(Collection, Comparator)
Collections.sort(list); // sort(Collection, Comparator)

int[] arr = list.stream().mapToInt(i->i).toArray();

// 交换元素位置
int i = 1;
int j = 3;
Collections.swap(list, i, j);

// 洗牌、乱序
Collections.shuffle(list);

// 二分搜索
int index = Collections.binarySearch(list, 7);
// 对于非标准对象，可以自定义二分搜索的对比 binarySearch(List<? extends T> list, T key, Comparator<? super T> c)
// 如果 list 里找不到该元素，会对该元素应插入的位置索引取反再 -1，然后返回该值，例：Collections.binarySearch({1, 2, 3, 10, 20}, 13) => -5（参考：https://www.geeksforgeeks.org/collections-binarysearch-java-examples/）

// 反转顺序
Collections.reverse(list);

// 集合中的所有元素集体向后移 k 位，最后面的元素出现在集合开始位置
int k = 3;
Collections.rotate(list, k);

// 替换集合中所有元素为指定值
Collections.fill(list, 0);

// 将所有元素从一个列表复制到另一个列表。
// 操作后，目标列表中每个复制元素的索引将与源列表中其索引相同。目标列表必须至少与源列表一样长。如果更长，则目标列表中的其余元素不受影响。
// 时间复杂度 O(N)
Collections.copy(list1, list2);

// 将列表中一个指定值的所有出现替换为另一个
Collections.replaceAll(list3, "a", "b"); 

// ArrayList 是非线程安全的，不过，可以通过 Collections.synchronizedList() 静态方法返回一个线程安全的实例（SynchronizedList）
List list = Collections.synchronizedList(new ArrayList());
// Collections.synchronizedList 中很多方法，比如 equals、hasCode、get、set、add、remove、indexOf、lastIndexOf...... 都添加了锁，但是 iterator 没有加锁，不是线程安全的，所以如果要遍历，还是必须要在外面加一层锁。
synchronized (list) {
    Iterator i = list.iterator();
    while (i.hasNext()) foo(i.next());
}

// Collections.indexOfSubList(list, subList) 方法的使用(含义：查找 subList 在 list 中首次出现位置的索引)
// Collections.lastIndexOfSubList(list, subList) 方法的使用与上例方法的使用相同

// 创建空集合
List<String> list1 = List.of();
List<String> list2 = Collections.emptyList();
// Collections.emptyMap()
// Collections.emptySet()

// 创建单元素集合
List<String> list1 = List.of("a");
List<String> list2 = Collections.singletonList("a");
// Collections.singletonMap(K, V)
// Collections.singleton(T) // Set<T>

// 创建不可变集合
List<String> mutable = new ArrayList<>();
mutable.add("a");
mutable.add("b");
List<String> immutable = Collections.unmodifiableList(mutable);
// Collections.unmodifiableSet(set)
// Collections.unmodifiableMap(map)

// 检查两个指定的 collection 是否不相交。如果两个指定的集合没有相同的元素，则返回 true
Collections.disjoint(hashsetA, hashsetB);
Collections.disjoint(listA, listB);
