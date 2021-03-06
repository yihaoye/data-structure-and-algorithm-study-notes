// concurrent/ParallelPrime.java
import java.util.*;
import java.util.stream.*;
import static java.util.stream.LongStream.*;
import java.io.*;
import java.nio.file.*;
import onjava.Timer;

public class ParallelPrime {
    static final int COUNT = 100_000;

    public static boolean isPrime(long n) {
        return rangeClosed(2, (long)Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }

    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        List<String> primes =
            iterate(2, i -> i + 1)
                .parallel() // 当注释掉 parallel() 行时，结果用时大约是 parallel() 的三倍。
                .filter(ParallelPrime::isPrime)
                .limit(COUNT)
                .mapToObj(Long::toString)
                .collect(Collectors.toList());

        System.out.println(timer.duration());
        Files.write(Paths.get("primes.txt"), primes, StandardOpenOption.CREATE);
    }
}
// 来自 Streams 的 Prime.java，查找质数可能是一个耗时的过程。
/*
并行流似乎是一个甜蜜的交易。你所需要做的就是将编程问题转换为流，然后插入 parallel() 以加快速度。
实际上，有时候这很容易。但遗憾的是，有许多陷阱。
  parallel() 不是灵丹妙药
*/
