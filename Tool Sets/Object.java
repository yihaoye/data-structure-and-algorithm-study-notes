public class MyClass {
    int data;

    public MyClass() {
        this.data = 0;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getData() {
        return this.data;
    }
}

MyClass obj1 = new MyClass();
MyClass obj2 = new MyClass();

// protected Object	clone() 对象复制
obj1.clone();

// String toString()  converts an object into the string
obj1.toString();

// Class<?> getClass()  returns the class name of the object
obj1.getClass();

// int hashCode()  returns the hashcode value of the object
obj1.hashCode();

// boolean equals(Object obj)  checks if two objects are equal
obj1.equals(obj2);

/**
protected void	finalize()
Called by the garbage collector on an object when garbage collection determines that there are no more references to the object.

void	notify()
Wakes up a single thread that is waiting on this object's monitor.

void	notifyAll()
Wakes up all threads that are waiting on this object's monitor.

void	wait()
Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object.

void	wait(long timeout)
Causes the current thread to wait until either another thread invokes the notify() method or the notifyAll() method for this object, or a specified amount of time has elapsed.

void	wait(long timeout, int nanos)
Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object, or some other thread interrupts the current thread, or a certain amount of real time has elapsed.
 */
