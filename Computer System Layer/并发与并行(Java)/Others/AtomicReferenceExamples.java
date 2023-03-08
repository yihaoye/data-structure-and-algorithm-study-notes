
// https://stackoverflow.com/questions/8567596/how-to-make-updating-bigdecimal-within-concurrenthashmap-thread-safe
// https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/atomic-reference.html
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

class Solution {
    public static void main(String[] args) {
        AtomicReference<BigDecimal> sum = new AtomicReference<>();
        sum.set(BigDecimal.valueOf(100.0));
        BigDecimal amount = BigDecimal.valueOf(45.0);
        for (;;) {
            BigDecimal oldVal = sum.get();
            if (sum.compareAndSet(oldVal, oldVal.add(amount))) return;
        }
    }
}
