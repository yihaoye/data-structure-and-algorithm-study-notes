// Reference: https://www.baeldung.com/java-concurrency-interview-questions



/* Methods to create thread */
/* 
Method 1
To create an instance of a thread, you have two options. 
First, pass a Runnable instance to its constructor and call start(). 
Runnable is a functional interface, so it can be passed as a lambda expression:
*/
Thread thread1 = new Thread(() ->
  System.out.println("Hello World from Runnable!"));
thread1.start();
/* 
Method 2
Thread also implements Runnable, so another way of starting a thread is to create an anonymous subclass, 
override its run() method, and then call start():
*/
Thread thread2 = new Thread() {
    @Override
    public void run() {
        System.out.println("Hello World from subclass!");
    }
};
thread2.start();



/* Describe the Different States of a Thread and When Do the State Transitions Occur. */
/*
The state of a Thread can be checked using the Thread.getState() method. 
Different states of a Thread are described in the Thread.State enum. They are:

NEW — 
  a new Thread instance that was not yet started via Thread.start()

RUNNABLE — 
  a running thread. It is called runnable because at any given time it could be either running or waiting for the next quantum of time from the thread scheduler. 
  A NEW thread enters the RUNNABLE state when you call Thread.start() on it

BLOCKED — 
  a running thread becomes blocked if it needs to enter a synchronized section but cannot do that due to another thread holding the monitor of this section

WAITING — 
  a thread enters this state if it waits for another thread to perform a particular action. 
  For instance, a thread enters this state upon calling the Object.wait() method on a monitor it holds, 
  or the Thread.join() method on another thread

TIMED_WAITING — 
  same as the above, but a thread enters this state after calling timed versions of Thread.sleep(), 
  Object.wait(), Thread.join() and some other methods

TERMINATED — 
  a thread has completed the execution of its Runnable.run() method and terminated
*/



/* What Is Java Memory Model (Jmm)? Describe Its Purpose and Basic Ideas. */
/*
Java Memory Model is a part of Java language specification described in Chapter 17.4. 
It specifies how multiple threads access common memory in a concurrent Java application, 
and how data changes by one thread are made visible to other threads. 
While being quite short and concise, JMM may be hard to grasp without strong mathematical background.

The need for memory model arises from the fact that the way your Java code is accessing data is not how it actually happens on the lower levels. 
Memory writes and reads may be reordered or optimized by the Java compiler, JIT compiler, and even CPU, 
as long as the observable result of these reads and writes is the same.

This can lead to counter-intuitive results when your application is scaled to multiple threads because 
most of these optimizations take into account a single thread of execution (the cross-thread optimizers are 
still extremely hard to implement). Another huge problem is that the memory in modern systems is multilayered: 
multiple cores of a processor may keep some non-flushed data in their caches or read/write buffers, 
which also affects the state of the memory observed from other cores.

To make things worse, the existence of different memory access architectures would break the Java's promise of “write once, 
run everywhere”. Happily for the programmers, the JMM specifies some guarantees that you may rely upon when designing multithreaded applications. 
Sticking to these guarantees helps a programmer to write multithreaded code that is stable and portable between various architectures.


The main notions of JMM are:

Actions - 
  these are inter-thread actions that can be executed by one thread and detected by another thread, 
  like reading or writing variables, locking/unlocking monitors and so on

Synchronization actions - 
  a certain subset of actions, like reading/writing a volatile variable, or locking/unlocking a monitor

Program Order (PO) - 
  the observable total order of actions inside a single thread

Synchronization Order (SO) - 
  the total order between all synchronization actions — 
  it has to be consistent with Program Order, that is, if two synchronization actions come one before another in PO, 
  they occur in the same order in SO

Synchronizes-with (SW) - 
  relation between certain synchronization actions, 
  like unlocking of monitor and locking of the same monitor (in another or the same thread)

Happens-before Order — 
  combines PO with SW (this is called transitive closure in set theory) to create a partial ordering of all actions between threads. 
  If one action happens-before another, then the results of the first action are 
  observable by the second action (for instance, write of a variable in one thread and read in another)

Happens-before consistency — 
  a set of actions is HB-consistent if every read observes either the last write to that location in the happens-before order, 
  or some other write via data race

Execution — 
  a certain set of ordered actions and consistency rules between them

For a given program, we can observe multiple different executions with various outcomes. 
But if a program is correctly synchronized, then all of its executions appear to be sequentially consistent, 
meaning you can reason about the multithreaded program as a set of actions occurring in some sequential order. 
This saves you the trouble of thinking about under-the-hood reorderings, optimizations or data caching.
*/



/* What Is a Volatile Field and What Guarantees Does the Jmm Hold for Such Field? */
/*
A volatile field has special properties according to the Java Memory Model (see Q9). 
The reads and writes of a volatile variable are synchronization actions, 
meaning that they have a total ordering (all threads will observe a consistent order of these actions). 
A read of a volatile variable is guaranteed to observe the last write to this variable, 
according to this order.

If you have a field that is accessed from multiple threads, with at least one thread writing to it, 
then you should consider making it volatile, or else there is a little guarantee to what a certain thread would read from this field.

Another guarantee for volatile is atomicity of writing and reading 64-bit values (long and double). 
Without a volatile modifier, a read of such field could observe a value partly written by another thread.
*/



/* Which of the Following Operations Are Atomic? */
/*
- writing to a non-volatile int;
- writing to a volatile int;
- writing to a non-volatile long;
- writing to a volatile long;
- incrementing a volatile long?

A write to an int (32-bit) variable is guaranteed to be atomic, whether it is volatile or not. 
A long (64-bit) variable could be written in two separate steps, for example, on 32-bit architectures, 
so by default, there is no atomicity guarantee. However, if you specify the volatile modifier, a long variable is guaranteed to be accessed atomically.

The increment operation is usually done in multiple steps (retrieving a value, changing it and writing back), 
so it is never guaranteed to be atomic, wether the variable is volatile or not. 
If you need to implement atomic increment of a value, you should use classes AtomicInteger, AtomicLong etc.
*/



/* What Is the Meaning of a Synchronized Keyword in the Definition of a Method? of a Static Method? Before a Block? */
/*
The synchronized keyword before a block means that any thread entering this block has to acquire the monitor (the object in brackets). 
If the monitor is already acquired by another thread, the former thread will enter the BLOCKED state and wait until the monitor is released.
*/
synchronized(object) {
    // ...
}
// A synchronized instance method has the same semantics, but the instance itself acts as a monitor.
synchronized void instanceMethod() {
    // ...
}
// For a static synchronized method, the monitor is the Class object representing the declaring class.
static synchronized void staticMethod() {
    // ...
}
