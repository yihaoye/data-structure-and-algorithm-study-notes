import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapRCExample {

    public static void main(String[] args) {
        Map<String, String> sharedMap = new ConcurrentHashMap<>();

        Thread thread1 = new Thread(getRunnable(sharedMap));
        Thread thread2 = new Thread(getRunnable(sharedMap));

        thread1.start();
        thread2.start();
    }

    private static Runnable getRunnable(Map<String, String> sharedMap) {
        return () -> {
            for (int i=0; i<1_000_000; i++) {
                synchronized(sharedMap) { // 虽然 ConcurrentHashMap 是线程安全的但那只是避免 HashMap 在多线程下操作时出现环形链表/死循环错误，对这里的多线程问题无补，所以这里需要对 if 块进行原子操作设置 synchronized，因为 ConcurrentHashMap 在这里并不保证线程2不会在线程1进入 if 条件后执行 remove 之前抢先把"key"删掉
                    if (sharedMap.containsKey("key")) {
                        String val = sharedMap.remove("key");
                        if (val == null) {
                            System.out.println("Iteration: " + i + ": Value for 'key' was null"); // 这里的打印本不应该发生，因为语句已判断存在 "key"，所以 remove 出来不应该是 null，但是在多线程下如果没有前面的 synchronized 保障则可能会在某线程符合进入 if 后 remove 之前被其他线程删了，所以多线程下可能会打印这一段出来
                        }
                    } else {
                        sharedMap.put("key", "value");
                    }
                }
            }
        };
    }
}