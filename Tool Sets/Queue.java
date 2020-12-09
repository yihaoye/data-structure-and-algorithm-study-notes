// 双端队列
Deque deque = new LinkedList<>();
// or
Deque deque = new ArrayDeque<>();



// 优先队列
// example
PriorityQueue<Obj> pQueue = new PriorityQueue(
    Comparator.comparing(Obj::getVal1).reversed()
    .thenComparing(Obj::getVal2)
    .thenComparing(Obj::getVal3));

pQueue.add(obj1);
Obj firstObj = pQueue.poll();



// 优先队列 with customized Object element and customized Comparator (lc q692)
Queue<Node> pq = new PriorityQueue<>(new MyComparator());

public class MyComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if (n1.val != n2.val) return n1.val.compareTo(n2.val);
        return n2.key.compareTo(n1.key);
    } 
}

public class Node {
    public String key;
    public Integer val;
}
