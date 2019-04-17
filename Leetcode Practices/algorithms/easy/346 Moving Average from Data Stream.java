/*
Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

Example:

MovingAverage m = new MovingAverage(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3
*/



// My Solution:
class MovingAverage {
    Queue<Integer> q;
    int s;
    
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        q = new LinkedList<Integer>();
        s = size;
    }
    
    public double next(int val) {
        int sum = 0;
        if (q.size() >= s) q.remove();
        q.add(val);
        for (int e : q) {
             sum += e;
        }
        
        return ((double)sum)/(q.size());
    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */