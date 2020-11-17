/*
在一个线程中，横跨若干方法调用，需要传递的对象，我们通常称之为上下文（Context），它是一种状态，可以是用户身份、任务信息等。

给每个方法增加一个context参数非常麻烦，而且有些时候，如果调用链有无法修改源码的第三方库，需要传递的对象就传不进去了。

Java标准库提供了一个特殊的ThreadLocal，它可以在一个线程中传递同一个对象。

配合参考：../ThreadLocal.png
*/

public class ThreadLocalBasicExample {

    public static void main(String[] args) {

        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Thread thread1 = new Thread(() -> {
            threadLocal.set("Thread 1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // threadLocal.remove();
            String value = threadLocal.get();
            System.out.println(value);
        });

        Thread thread2 = new Thread(() -> {
            threadLocal.set("Thread 2");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String value = threadLocal.get();
            System.out.println(value);
        });

        thread1.start();
        thread2.start();
    }
}
// 多个线程运行，线程间不会互相覆盖地写对方在 ThreadLocal 里的数据



public class ThreadLocalInitialValueExample {

    public static void main(String[] args) {

        ThreadLocal<MyObject> threadLocal1 = new ThreadLocal<>() {
            @Override
            protected MyObject initialValue () {
                return new MyObject();
            }
        };

        ThreadLocal<MyObject> threadLocal2 = 
            ThreadLocal.withInitial(() -> {
                return new MyObject();
            });

        Thread thread1 = new Thread(() -> {
            System.out.println("threadLocal1: " + threadLocal1.get());
            System.out.println("threadLocal2: " + threadLocal2.get());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("threadLocal1: " + threadLocal1.get());
            System.out.println("threadLocal2: " + threadLocal2.get());
        });

        thread1.start();
        thread2.start();
    }
}
// 以上程序会打印出 4 个不同的 MyObject 对象



public class ThreadLocalLazyInitExample {

    public static void main(String[] args) {

        ThreadLocal<MyObject> threadLocal = new ThreadLocal<>();

        String value = threadLocal.get();
        if (value == null) {
            threadLocal.set("Lazy set value");
            value = threadLocal.get();
        }

        System.out.println(value);
    }
}