/*
Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
Example:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.
*/



// My Solution (bad performance):
class MinStack {
    List<Integer> list;
    List<Integer> sortedList;

    /** initialize your data structure here. */
    public MinStack() {
        list = new ArrayList<>();
    }
    
    public void push(int x) {
        list.add(x);
        sortedList = new ArrayList<>(list);
        Collections.sort(sortedList);
    }
    
    public void pop() {
        list.remove(list.size() - 1);
        sortedList = new ArrayList<>(list);
        Collections.sort(sortedList);
    }
    
    public int top() {
        return list.get(list.size() - 1);
    }
    
    public int getMin() {
        return sortedList.get(0);
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */