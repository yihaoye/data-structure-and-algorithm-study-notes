// https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html
// https://www.liaoxuefeng.com/wiki/1252599548343744/1299919855943714
// 注意 Collections 和 Collection 不一样，
// Collection 是接口或称容器，
// Collections 是 JDK 提供的工具类，同样位于 java.util 包中。它提供了一系列静态方法，能更方便地操作各种集合。

List<Integer> list = new ArrayList<>(Arrays.asList(5,10,9,3,7));
Collections.min(list);
Collections.max(list);
Collections.sort(list);

int[] arr = list.stream().mapToInt(i->i).toArray();


int i = 1;
int j = 3;
Collections.swap(list, i, j);


/*
Collections 类提供了一组工具方法来方便使用集合类：
    创建空集合；
    创建单元素集合；
    创建不可变集合；
    排序／洗牌等操作。
*/
