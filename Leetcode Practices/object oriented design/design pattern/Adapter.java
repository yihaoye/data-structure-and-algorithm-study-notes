// 引用：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319245971489
/**
适配器模式是 Adapter，也称 Wrapper，是指如果一个接口需要 B 接口，但是待传入的对象却是 A 接口，怎么办？

举个例子。如果去美国，我们随身带的电器是无法直接使用的，因为美国的插座标准和中国不同，所以，需要一个适配器
 */
public class Task implements Callable<Long> {
    private long num;
    public Task(long num) {
        this.num = num;
    }

    public Long call() throws Exception {
        long r = 0;
        for (long n = 1; n <= this.num; n++) {
            r = r + n;
        }
        System.out.println("Result: " + r);
        return r;
    }
}

public class RunnableAdapter implements Runnable {
    // 引用待转换接口:
    private Callable<?> callable;

    public RunnableAdapter(Callable<?> callable) {
        this.callable = callable;
    }

    // 实现指定接口:
    public void run() {
        // 将指定接口调用委托给转换接口调用:
        try {
            callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public void main(String[] args) {
        Callable<Long> callable = new Task(123450000L);
        Thread thread = new Thread(new RunnableAdapter(callable));
        thread.start();
    }
}

/*
编写一个Adapter的步骤如下：

1. 实现目标接口，这里是Runnable；
2. 内部持有一个待转换接口的引用，这里是通过字段持有Callable接口；
3. 在目标接口的实现方法内部，调用待转换接口的方法。
*/
