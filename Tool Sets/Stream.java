// streams/StreamOf.java
import java.util.stream.*;
public class StreamOf {
    public static void main(String[] args) {
        Stream.of(new Bubble(1), new Bubble(2), new Bubble(3)) // 创建流
            .forEach(System.out::println); // 终端操作
        Stream.of("It's ", "a ", "wonderful ", "day ", "for ", "pie!")
            .forEach(s -> System.out::print(s));
        System.out.println();
        Stream.of(3.14159, 2.718, 1.618)
            .forEach(System.out::println);

        Stream.of("It's ", "a ", "wonderful ", "day ", "for ", "pie!")
            .filter(s -> s.length() > 3)
            .forEach(System.out::println);
        Stream.of("a", "a ", "b ", "aa", "b ", "c")
            .distinct()
            .forEach(System.out::println);
        Stream.of("a", "a ", "b ", "aa", "b ", "c")
            .map(s -> s.toUpperCase())
            .forEach(System.out::println);

        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
        random.ints().limit(10).sorted().forEach(System.out::println);

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        List<String> list = Arrays.asList("Geeks", "for", "gfg", "GeeksforGeeks", "GeeksQuiz"); 
        list.stream().mapToInt(str -> str.length()).forEach(System.out::println);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表: " + numbers);
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        /*
        列表: [1, 2, 13, 4, 15, 6, 17, 8, 19]
        列表中最大的数 : 19
        列表中最小的数 : 1
        所有数之和 : 85
        平均数 : 9.444444444444445
        */

        Stream<List<Integer>> ss = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));
        ss.flatMap(l -> l.stream()).forEach(System.out::println);
    }
}



// 声明式编程（流）VS 命令式编程
// streams/Randoms.java
import java.util.*;
public class Randoms {
    public static void main(String[] args) {
        new Random(47) // 创建流 - 随机数流
            .ints(5, 20) // 中间操作
            .distinct() // 中间操作
            .limit(7) // 中间操作
            .sorted() // 中间操作
            .forEach(System.out::println); // 终端操作
    }
}
// 上面 Randoms.java 中没有声明任何变量。流可以在不使用赋值或可变数据的情况下对有状态的系统建模，这非常有用。
// 以下是同义的命令式编程
// streams/ImperativeRandoms.java
import java.util.*;
public class ImperativeRandoms {
    public static void main(String[] args) {
        Random rand = new Random(47);
        SortedSet<Integer> rints = new TreeSet<>();
        while(rints.size() < 7) {
            int r = rand.nextInt(20);
            if(r < 5) continue;
            rints.add(r);
        }
        System.out.println(rints);
    }
}

// 流创建
// 可以通过 Stream.of() 很容易地将一组元素转化成为流
// 除此之外，每个集合都可以通过调用 stream() 方法来产生一个流。
// streams/CollectionToStream.java
import java.util.*;
import java.util.stream.*;
public class CollectionToStream {
    public static void main(String[] args) {
        List<Bubble> bubbles = Arrays.asList(new Bubble(1), new Bubble(2), new Bubble(3));
        System.out.println(bubbles.stream() // 创建流
            .mapToInt(b -> b.i) // 中间操作
            .sum()); // 终端操作

        Set<String> w = new HashSet<>(Arrays.asList("It's a wonderful day for pie!".split(" ")));
        w.stream()
         .map(x -> x + " ")
         .forEach(System.out::print);
        System.out.println();

        Map<String, Double> m = new HashMap<>();
        m.put("pi", 3.14159);
        m.put("e", 2.718);
        m.put("phi", 1.618);
        m.entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .forEach(System.out::println);
    }
}
/*
在创建 List<Bubble> 对象之后，只需要简单地调用所有集合中都有的 stream()。
中间操作 map() 会获取流中的所有元素，并且对流中元素应用操作从而产生新的元素，并将其传递到后续的流中。
通常 map() 会获取对象并产生新的对象，但在这里产生了特殊的用于数值类型的流。
例如，mapToInt() 方法将一个对象流（object stream）转换成为包含整型数字的 IntStream。
同样，针对 Float 和 Double 也有类似名字的操作。
*/

// 随机数流
// streams/RandomGenerators.java
import java.util.*;
import java.util.stream.*;
public class RandomGenerators {
    public static <T> void show(Stream<T> stream) {
        stream
        .limit(4)
        .forEach(System.out::println);
        System.out.println("++++++++");
    }

    public static void main(String[] args) {
        Random rand = new Random(47);
        show(rand.ints().boxed());
        show(rand.longs().boxed());
        show(rand.doubles().boxed());
        // 控制上限和下限：
        show(rand.ints(10, 20).boxed());
        show(rand.longs(50, 100).boxed());
        show(rand.doubles(20, 30).boxed());
        // 控制流大小：
        show(rand.ints(2).boxed());
        show(rand.longs(2).boxed());
        show(rand.doubles(2).boxed());
        // 控制流的大小和界限
        show(rand.ints(3, 3, 9).boxed());
        show(rand.longs(3, 12, 22).boxed());
        show(rand.doubles(3, 11.5, 12.3).boxed());
    }
}
/*
为了消除冗余代码，这里创建了一个泛型方法 show(Stream<T> stream) 。
类型参数 T 可以是任何类型，所以这个方法对 Integer、Long 和 Double 类型都生效。
但是 Random 类只能生成基本类型 int， long， double 的流。
幸运的是， boxed() 流操作将会自动地把基本类型包装成为对应的装箱类型，从而使得 show() 能够接受流。
Stream<Obj> boxed() - returns a Stream consisting of the elements of this stream, each boxed to an Obj.

可以使用 Random 为任意对象集合创建 Supplier。
*/