https://www.liaoxuefeng.com/wiki/1252599548343744/1300277678309410  
  
因为HashMap是一种通过对key计算hashCode()，通过空间换时间的方式，直接定位到value所在的内部数组的索引，因此，查找效率非常高。  
  
如果作为key的对象是enum类型，那么，还可以使用Java集合库提供的一种EnumMap，它在内部以一个非常紧凑的数组存储value，并且根据enum类型的key直接定位到内部数组的索引，并不需要计算hashCode()，不但效率最高，而且没有额外的空间浪费。如果Map的key是enum类型，推荐使用EnumMap，既保证速度，也不浪费空间。  
  
```java
import java.time.DayOfWeek;
import java.util.*;

public static void main(String[] args) {
    Map<DayOfWeek, String> map = new EnumMap<>(DayOfWeek.class);
    map.put(DayOfWeek.MONDAY, "星期一");
    map.put(DayOfWeek.TUESDAY, "星期二");
    map.put(DayOfWeek.WEDNESDAY, "星期三");
    map.put(DayOfWeek.THURSDAY, "星期四");
    map.put(DayOfWeek.FRIDAY, "星期五");
    map.put(DayOfWeek.SATURDAY, "星期六");
    map.put(DayOfWeek.SUNDAY, "星期日");
    System.out.println(map);
    System.out.println(map.get(DayOfWeek.MONDAY));
}
```
