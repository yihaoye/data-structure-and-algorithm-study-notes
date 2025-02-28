// Date 和 Calendar 是 java.util.* 提供的旧的日期 API，不建议再使用


/*
https://www.liaoxuefeng.com/wiki/1252599548343744/1255943660631584

计算机存储的当前时间，本质上只是一个不断递增的整数。Java 提供的 System.currentTimeMillis() 返回的就是以毫秒表示的当前时间戳。

这个当前时间戳在 java.time 中以 Instant 类型表示，用 Instant.now() 获取当前时间戳，效果和 System.currentTimeMillis() 类似：
*/
import java.time.*;

public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.println(now.getEpochSecond()); // 秒
        System.out.println(now.toEpochMilli()); // 毫秒
    }
}



// sleep
public void process() {
    try {
        Thread.sleep(1000); // ms
    } catch(InterruptedException e) {
        // ...
    }
}



/*
LocalDateTime，ZoneId，Instant，ZonedDateTime 和 long 都可以互相转换：

┌─────────────┐
│LocalDateTime│────┐
└─────────────┘    │    ┌─────────────┐
                   ├───>│ZonedDateTime│
┌─────────────┐    │    └─────────────┘
│   ZoneId    │────┘           ▲
└─────────────┘      ┌─────────┴─────────┐
                     │                   │
                     ▼                   ▼
              ┌─────────────┐     ┌─────────────┐
              │   Instant   │<───>│    long     │
              └─────────────┘     └─────────────┘

转换的时候，只需要留意long类型以毫秒还是秒为单位即可。
*/


/*
LocalDateTime、ZonedDateTime 是 java.time.* 提供的替代 Date 和 Calendar 的新 API，
但是 LocalDateTime 不能转换成 epochtime 因为没提供 Zone，ZonedDateTime 可以先转换成 Instant 再转换成 epochtime
*/
import java.time.*;

public class Main {
    public static void main(String[] args) {
        LocalDate d = LocalDate.now(); // 当前日期
        LocalTime t = LocalTime.now(); // 当前时间
        LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
        System.out.println(d); // 严格按照ISO 8601格式打印
        System.out.println(t); // 严格按照ISO 8601格式打印
        System.out.println(dt); // 严格按照ISO 8601格式打印
    }
}

public class Main {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        ZonedDateTime zny = ZonedDateTime.now(ZoneId.of("America/New_York")); // 用指定时区获取当前时间
        System.out.println(zbj);
        System.out.println(zny);
        zny.toInstant().toEpochMilli();
    }
}
