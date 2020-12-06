// https://www.youtube.com/watch?v=mnSMdTPBG1U
// ![](./Min%20Heap.png)
public class MinHeap {

    private List<Integer> data_;

    // 堆内部维护 ![](./Min%20Heap%202.png)
    private void heapifyUp(int index) {
        // 类似冒泡排序，虽然排序本身性能较差，但因为是在 Heap 里冒泡，所以时间复杂度也只是 log(N)
        if (index == 0) return;
        int parent = (index - 1) / 2;
        if (data_.get(index) >= data_.get(parent)) return;
        Collections.swap(data_, index, parent);
        heapifyUp(parent);
    }

    // 堆内部维护 ![](./Min%20Heap%203.png)
    private void heapifyDown(int index) {
        int smallerIndex = index;
        for (int childIndex : new int[]{index * 2 + 1, index * 2 + 2}) {
            if (childIndex < data_.size() && data_.get(childIndex) < data_.get(smallerIndex)) smallerIndex = childIndex;
        }
        if (smallerIndex == index) return;
        Collections.swap(data_, index, smallerIndex);
        heapifyDown(smallerIndex);
    }

    // Create an empty heap
    public MinHeap() {
        data_ = new ArrayList<Integer>();
    }

    // Create a heap from a ArrayList/array（该参数集合为乱序未排序）
    public MinHeap(ArrayList<Integer> list) {
        // 原理为从倒数第二层往上（类似广度优先）遍历地对所有元素调用 heapifyDown(element)，时间复杂度为 log(N)*(N/2) = N*log(N) -> 为 Upper bound，Tighter bound 为 O(N)
        data_ = new ArrayList<>(list);
        for (int index = (data_.size() - 1) / 2; index >= 0; index--)
            heapifyDown(index);
    }

    // return the min element
    public int peek() {
        return data_.get(0);
    }

    // extract the min element
    public int pop() {
        Collections.swap(data_, data_.size()-1, 0); // O(1)
        int root = data_.remove(data_.size()-1); // O(1)，https://stackoverflow.com/questions/43145395/time-complexity-while-deleting-last-element-from-arraylist-and-linkedlist
        heapifyDown(0); // 因为是在 Heap 里向下冒泡，所以时间复杂度也只是 log(N)
        return root;
    }

    // add a new element to the heap
    public void push(int e) {
        data_.add(e);
        heapifyUp(data_.size()-1);
    }

    // return the size of the heap
    public int size() {
        return data_.size();
    }
}
