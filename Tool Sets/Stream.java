/**
https://www.liaoxuefeng.com/wiki/1252599548343744/1322402873081889

Stream 不同于 java.io 的 InputStream 和 OutputStream，它代表的是任意 Java 对象的序列。两者对比如下：

        |java.io	                |java.util.stream
存储	 |顺序读写的 byte 或 char	   |顺序输出的任意 Java 对象实例
用途	 |序列化至文件或网络	        |内存计算／业务逻辑

Stream 和 Collections/List 不一样，Collections/List 存储的每个元素都是已经存储在内存中的某个 Java 对象，而 Stream 输出的元素可能并没有预先存储在内存中，而是实时计算出来的。

换句话说，Collections 的用途是操作一组已存在的 Java 对象，而 Stream 实现的是惰性计算，两者对比如下：
        |java.util.List	            |java.util.stream
元素	|已分配并存储在内存	            |可能未分配，实时计算
用途	|操作一组已存在的 Java 对象	    |惰性计算

Stream 看上去有点不好理解，但举个例子就明白了。

如果要表示一个全体自然数的集合，显然，用 Collections/List 是不可能写出来的，因为自然数是无限的，内存再大也没法放到 Collections/List 中：
List<BigInteger> list = ??? // 全体自然数?
但是，用 Stream 可以做到。写法如下：
Stream<BigInteger> naturals = createNaturalStream(); // 全体自然数
 */



// 总结一下 Stream 的特点：它可以“存储”有限个或无限个元素。这里的存储打了个引号，是因为元素有可能已经全部存储在内存中，也有可能是根据需要实时计算出来的。
// Stream 的另一个特点是，一个 Stream 可以轻易地转换为另一个 Stream，而不是修改原 Stream 本身。
// 最后，真正的计算通常发生在最后结果的获取，也就是惰性计算。
Stream<BigInteger> naturals = createNaturalStream(); // 不计算
Stream<BigInteger> s2 = naturals.map(BigInteger::multiply); // 不计算
Stream<BigInteger> s3 = s2.limit(100); // 不计算
s3.forEach(System.out::println); // 计算
// 惰性计算的特点是：一个 Stream 转换为另一个 Stream 时，实际上只存储了转换规则，并没有任何计算发生。
// 以上代码也可以写成链式，效果也是一样的。
// 因此，Stream API 的基本用法就是：创建一个 Stream，然后做若干次转换，最后调用一个求值方法获取真正计算的结果。



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

// Stream.generate() 的用法，它可以把任意 Supplier<T> 用于生成 T 类型的流。