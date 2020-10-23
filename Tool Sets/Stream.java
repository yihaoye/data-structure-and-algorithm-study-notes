// streams/StreamOf.java
import java.util.stream.*;
public class StreamOf {
    public static void main(String[] args) {
        Stream.of(new Bubble(1), new Bubble(2), new Bubble(3))
            .forEach(System.out::println);
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
