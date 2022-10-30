/**
You have a function printNumber that can be called with an integer parameter and prints it to the console.

For example, calling printNumber(7) prints 7 to the console.
You are given an instance of the class ZeroEvenOdd that has three functions: zero, even, and odd. The same instance of ZeroEvenOdd will be passed to three different threads:

Thread A: calls zero() that should only output 0's.
Thread B: calls even() that should only output even numbers.
Thread C: calls odd() that should only output odd numbers.
Modify the given class to output the series "010203040506..." where the length of the series must be 2n.

Implement the ZeroEvenOdd class:

ZeroEvenOdd(int n) Initializes the object with the number n that represents the numbers that should be printed.
void zero(printNumber) Calls printNumber to output one zero.
void even(printNumber) Calls printNumber to output one even number.
void odd(printNumber) Calls printNumber to output one odd number.
 

Example 1:

Input: n = 2
Output: "0102"
Explanation: There are three threads being fired asynchronously.
One of them calls zero(), the other calls even(), and the last one calls odd().
"0102" is the correct output.
Example 2:

Input: n = 5
Output: "0102030405"
 

Constraints:

1 <= n <= 1000
 */



// Other's Solution:
class ZeroEvenOdd {
    private int n;
    // https://leetcode.com/problems/print-zero-even-odd/discuss/332943/Java-Basic-semaphore-solution-4ms-35.8MB-Updated/380377
    Semaphore zero = new Semaphore(0);
    Semaphore even = new Semaphore(0);
    Semaphore odd = new Semaphore(0);
    
    public ZeroEvenOdd(int n) {
        this.n = n;
        zero.release(); // zero semaphore 许可 +1，或可以在前面写成 Semaphore zero = new Semaphore(1);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i=1; i<=n; i++) {
            zero.acquire(); // Semaphore 中包含初始化时固定个数的许可，在进行操作的时候，需要先 acquire 获取到许可，才可以继续执行任务，如果获取失败，则进入阻塞
            printNumber.accept(0);
            if (i % 2 == 0) even.release();
            else odd.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i=2; i<=n; i+=2) {
            even.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i=1; i<=n; i+=2) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }
}
