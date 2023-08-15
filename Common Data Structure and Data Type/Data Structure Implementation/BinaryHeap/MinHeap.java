// https://www.youtube.com/watch?v=mnSMdTPBG1U
// ![](./Min%20Heap.png)

// 堆是一个完全二叉树，所以可以用数组来实现，数组的下标就是二叉树的层序遍历的顺序
// 但是堆不是二叉搜索树，因为堆的左右子节点的大小关系没有要求，只要求父节点的值总是小于等于子节点的值（最小堆，反之则为最大堆）
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
/*
注意：在每次插入或删除元素时，堆的自动调整并不能总是会保证上一层的最大值小于下一层的最小值（最小堆），反例如下：
                      0
                 /        \
              1            2
           /    \        /   \
          5       6     3      7
       /   \    /   \   
      8    9   10   11
这个时候插入 2.5，就会与 3 调换，从而导致第三层的最小值小于第二层的 5、6、7

问：
如果是这样的话，为什么（最小）堆总是能保证当前获取的最小值总在根节点？有没有可能一些复杂的操作之后导致原本应该在根节点的最小值如上例一样被调到下层？

答：
在堆数据结构中，无论是最小堆还是最大堆，都保证了一个重要性质：根节点的值总是满足堆的性质，
也就是说，在最小堆中，根节点的值小于或等于其子节点的值，而在最大堆中，根节点的值大于或等于其子节点的值。
这个性质在堆的构建过程中通过“自上而下”的调整（heapifyDown）和“自下而上”的调整（heapifyUp）来维持，确保每次插入、删除等操作之后，根节点的值总是满足堆的性质。
前面提供的反例中确实出现了一个特殊情况，即插入一个新的元素后，可能导致某些子树的节点值不满足上述性质。然而，这种情况是短暂的，而且通过“自上而下”的调整，堆会重新恢复其性质。
这是因为堆调整操作始终会将较大（或较小）的值沿着树的路径上升（或下降），最终将根节点的值调整到正确的位置。
要强调的是，虽然一些操作可能会导致某些节点暂时不满足堆的性质，但堆调整操作会在不断进行的插入、删除操作中重新恢复这个性质。
这就是为什么堆总是能保证当前获取的最小（或最大）值总在根节点的原因。
总结起来，堆的性质确保了根节点的值满足堆的定义，即使在某些操作后可能会暂时被打破，但通过堆调整操作，这个性质会被恢复，确保了根节点的值始终是堆中的最小（或最大）值。
*/
