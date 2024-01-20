Go语言提供了一些内置的常用数据结构，这些数据结构在编写各种类型的程序时非常有用。以下是Go语言中常用的一些数据结构：

1. **数组（Array）：** 固定长度的、具有相同数据类型的元素集合。数组的长度在声明时就已经确定，不可变。
    ```go
    var arr [5]int
    ```
2. **切片（Slice）：** 动态数组，可以根据需要动态扩展或缩小。切片是对数组的引用，更灵活。
    ```go
    var slice []int
    slice = append(slice, 1)
    slice = append(slice, 2, 3, 4)
    ```
3. **映射（Map）：** 用于存储键值对的集合。映射提供了一种快速查找和检索数据的方式。
    ```go
    m := make(map[string]int)
    ```
4. **结构体（Struct）：** 允许用户定义多个字段的类型。结构体可以包含不同类型的数据。
    ```go
    type Person struct {
        Name string
        Age  int
    }
    ```
5. **队列（Queue）：** 队列是一种先进先出（FIFO）的数据结构。Go中可以使用切片来实现队列。
    ```go
    queue := []int{1, 2, 3}
    // poll
    head := queue[0]
    queue = queue[1:]
    ```
6. **栈（Stack）：** 栈是一种后进先出（LIFO）的数据结构。在Go中，可以使用切片来实现栈。
    ```go
    stack := []int{1, 2, 3}
    // pop
    head := stack[len(stack)-1]
    stack = stack[:len(stack)-1]
    ```
7. **链表（Linked List）：** 由节点组成的数据结构，每个节点包含一个数据元素和指向下一个节点的引用。
    ```go
    type Node struct {
        Data int
        Next *Node
    }
    ```
8. **集合（Set）：** 无序且元素不重复的数据结构。Go语言中没有内置的Set类型，但可以使用映射模拟。
    ```go
    set := make(map[int]bool)
    ```
9. **堆（Heap）：** 优先队列的一种实现方式，Go语言中可以使用`heap`包来操作堆。
    ```go
    import "container/heap"

    type PriorityQueue []int
    func (pq PriorityQueue) Len() int { return len(pq) }
    func (pq PriorityQueue) Less(i, j int) bool { return pq[i] < pq[j] }
    func (pq PriorityQueue) Swap(i, j int) { pq[i], pq[j] = pq[j], pq[i] }
    func (pq *PriorityQueue) Push(x interface{}) { *pq = append(*pq, x.(int)) }
    func (pq *PriorityQueue) Pop() interface{} {
        old := *pq
        n := len(old)
        x := old[n-1]
        *pq = old[0 : n-1]
        return x
    }

    func main() {
        priorityQueue := &PriorityQueue{3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5}
        heap.Init(priorityQueue)
        heap.Pop(priorityQueue)
    }
    ```
10. **字符串（String）：** 在Go中，字符串是不可变的字节序列。字符串类型是内置的。
    ```go
    str := "Hello, Go!"
    ```
11. **接口（interface）：**
    ```go
    type I interface {
        M()
    }

    type T struct {
        S string
    }

    func (t T) M() {
        fmt.Println(t.S)
    }

    func main() {
        var i I = T{"hello"}
        i.M()
    }
    ```
