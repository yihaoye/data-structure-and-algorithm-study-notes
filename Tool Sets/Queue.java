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
