// https://www.youtube.com/watch?v=mnSMdTPBG1U
// ![](./Min%20Heap.png)

// 堆是一个完全二叉树，所以可以用数组来实现，数组的下标就是二叉树的层序遍历的顺序
// 但是堆不是二叉搜索树，因为堆的左右子节点的大小关系没有要求，只要求父节点的值总是小于等于子节点的值（最小堆，反之则为最大堆）
// 在每次插入或删除元素时，堆的自动调整总是会保证上一层的最大值小于下一层的最小值（最小堆，反之则为最大堆）
// 这里的 heapifyUp、heapifyDown 是递归实现（也可以用迭代实现），因为是完全二叉树，所以递归最深层数为 log(N) 不会爆栈，也因此时间复杂度为 log(N)
public class MinHeap {
    private List<Integer> data_;

    // Create an empty heap
    public MinHeap() {
        data_ = new ArrayList<Integer>();
    }

    // Create a heap from a ArrayList/array（该参数集合为乱序未排序）
    public MinHeap(ArrayList<Integer> list) {
        // 原理为从倒数第二层往上（因为倒数第一层不会往下 heapifyDown，整个过程类似广度优先）遍历地对所有元素调用 heapifyDown(element)，
        // 时间复杂度看似为 log(N)*(N/2) = N*log(N)，
        // 但实际为 O(N)，证明看 ![](./Min%20Heap%204.png)
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
}
