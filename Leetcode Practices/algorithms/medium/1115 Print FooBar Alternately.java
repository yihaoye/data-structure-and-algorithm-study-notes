/**
Suppose you are given the following code:

class FooBar {
  public void foo() {
    for (int i = 0; i < n; i++) {
      print("foo");
    }
  }

  public void bar() {
    for (int i = 0; i < n; i++) {
      print("bar");
    }
  }
}
The same instance of FooBar will be passed to two different threads:

thread A will call foo(), while
thread B will call bar().
Modify the given program to output "foobar" n times.

 

Example 1:

Input: n = 1
Output: "foobar"
Explanation: There are two threads being fired asynchronously. One of them calls foo(), while the other calls bar().
"foobar" is being output 1 time.
Example 2:

Input: n = 2
Output: "foobarfoobar"
Explanation: "foobar" is being output 2 times.
 

Constraints:

1 <= n <= 1000
 */



// Other's Solution:
// https://leetcode.com/problems/print-foobar-alternately/discuss/348546/Java-4-Java-threading-solutions-(SynchronizedLockVolatileCAS)

// Synchronized(monitor exit happens-before monitor enter)
public class FooBarSynchronized {
    private int n;
    // flag 0->foo to be print  1->foo has been printed
    private int flag = 0;

    public FooBarSynchronized(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (flag == 1) {
                    this.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                flag = 1;
                this.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (flag == 0) {
                    this.wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                flag = 0;
                this.notifyAll();
            }
        }
    }
}



// Lock(volatile write happens-before volatile read)
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {
    private int n;
    // flag 0->foo to be print  1->foo has been printed
    private int flag = 0;
    ReentrantLock reentrantLock = new ReentrantLock();
    Condition fooPrintedCondition = reentrantLock.newCondition();
    Condition barPrintedCondition = reentrantLock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            reentrantLock.lock();
            while (flag == 1) barPrintedCondition.await();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            flag = 1;
            fooPrintedCondition.signalAll();
            reentrantLock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            reentrantLock.lock();
            while (flag == 0) fooPrintedCondition.await();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            flag = 0;
            barPrintedCondition.signalAll();
            reentrantLock.unlock();
        }
    }
}



// CAS (volatile write happens-before volatile read)
import java.util.concurrent.atomic.AtomicInteger;

public class FooBarCAS {
    private int n;
    // flag 0->foo to be print  1->foo has been printed
    private AtomicInteger flag = new AtomicInteger(0);

    public FooBarCAS(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (!flag.compareAndSet(0, 1)) {
                Thread.sleep(1);
            }
            printFoo.run();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (!flag.compareAndSet(1, 0)) {
                Thread.sleep(1);
            }
            printBar.run();
        }
    }
}



// My Solution after inspired:
class FooBar {
    private int n;
    private volatile boolean lock; // when lock = false, can only print foo, otherwise can only print bar. Voatile (volatile write happens-before volatile read)

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            while (lock);
        	// printFoo.run() outputs "foo". Do not change or remove this line.
        	printFoo.run();
            lock = true;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            while (!lock);
            // printBar.run() outputs "bar". Do not change or remove this line.
        	printBar.run();
            lock = false;
        }
    }
}
