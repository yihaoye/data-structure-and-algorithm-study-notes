/**
A concert hall has n rows numbered from 0 to n - 1, each with m seats, numbered from 0 to m - 1. You need to design a ticketing system that can allocate seats in the following cases:

If a group of k spectators can sit together in a row.
If every member of a group of k spectators can get a seat. They may or may not sit together.
Note that the spectators are very picky. Hence:

They will book seats only if each member of their group can get a seat with row number less than or equal to maxRow. maxRow can vary from group to group.
In case there are multiple rows to choose from, the row with the smallest number is chosen. If there are multiple seats to choose in the same row, the seat with the smallest number is chosen.
Implement the BookMyShow class:

BookMyShow(int n, int m) Initializes the object with n as number of rows and m as number of seats per row.
int[] gather(int k, int maxRow) Returns an array of length 2 denoting the row and seat number (respectively) of the first seat being allocated to the k members of the group, who must sit together. In other words, it returns the smallest possible r and c such that all [c, c + k - 1] seats are valid and empty in row r, and r <= maxRow. Returns [] in case it is not possible to allocate seats to the group.
boolean scatter(int k, int maxRow) Returns true if all k members of the group can be allocated seats in rows 0 to maxRow, who may or may not sit together. If the seats can be allocated, it allocates k seats to the group with the smallest row numbers, and the smallest possible seat numbers in each row. Otherwise, returns false.
 

Example 1:

Input
["BookMyShow", "gather", "gather", "scatter", "scatter"]
[[2, 5], [4, 0], [2, 0], [5, 1], [5, 1]]
Output
[null, [0, 0], [], true, false]

Explanation
BookMyShow bms = new BookMyShow(2, 5); // There are 2 rows with 5 seats each 
bms.gather(4, 0); // return [0, 0]
                  // The group books seats [0, 3] of row 0. 
bms.gather(2, 0); // return []
                  // There is only 1 seat left in row 0,
                  // so it is not possible to book 2 consecutive seats. 
bms.scatter(5, 1); // return True
                   // The group books seat 4 of row 0 and seats [0, 3] of row 1. 
bms.scatter(5, 1); // return False
                   // There is only one seat left in the hall.
 

Constraints:

1 <= n <= 5 * 104
1 <= m, k <= 109
0 <= maxRow <= n - 1
At most 5 * 104 calls in total will be made to gather and scatter.
 */



// Other's Solution:
class BookMyShow {
    int n, m;
    int[] min;
    long[] sum;

    public BookMyShow(int n, int m) {
        // 线段树+二分搜索 - https://leetcode.cn/problems/booking-concert-tickets-in-groups/solution/by-endlesscheng-okcu/
        // Time: 初始化线段树需要 O(N), Space: O(N)
        this.n = n;
        this.m = m;
        min = new int[n * 4]; // min[o] 区间 (i.e. 以 o 为根节点的区间 [down, up]) 排上的单排观众数（或称已占用位置数）的最小值 min
        sum = new long[n * 4]; // sum[o] 区间 (i.e. 以 o 为根节点的区间 [down, up]) 排上的总人数（总已占用位置数）
    }

    public int[] gather(int k, int maxRow) { // Time: O(logN)
        int i = index(1, 1, n, maxRow + 1, m - k);
        if (i == 0) return new int[]{}; // 不存在
        var used = (int) query_sum(1, 1, n, i, i); // 看这一排有多少个人了
        add(1, 1, n, i, k); // 在第 i 排占据 k 个座位
        return new int[]{i - 1, used};
    }

    public boolean scatter(int k, int maxRow) { // Time: O((N+q)*logN)，这里 q 为 gather 和 scatter 的调用次数之和
        if ((long) (maxRow + 1) * m - query_sum(1, 1, n, 1, maxRow + 1) < k) return false; // 剩余座位不足 k 个

        // 从第一个没有坐满的排开始占座，借助 index 找到最先符合的这一排，只要不满就都得查询，所以限制参数是 m - 1
        for (var i = index(1, 1, n, maxRow + 1, m - 1); ; ++i) {
            var left_seats = m - (int) query_sum(1, 1, n, i, i);
            if (k <= left_seats) { // 剩余人数不够坐后面的排
                add(1, 1, n, i, k);
                return true;
            }
            k -= left_seats;
            add(1, 1, n, i, left_seats);
        }
    }

    // 将 idx 排上的占用值增加 val
    void add(int o, int down, int up, int idx, int val) {
        if (down == up) {
            min[o] += val;
            sum[o] += val;
            return;
        }
        var mid = (down + up) / 2;
        if (idx <= mid) add(o * 2, down, mid, idx, val);
        else add(o * 2 + 1, mid + 1, up, idx, val);

        // 以下两者相当于 pushup，本题没有 pushdown，因为 pushdown 用于区间更新的时候才需要 lazy 标记
        min[o] = Math.min(min[o * 2], min[o * 2 + 1]);
        sum[o] = sum[o * 2] + sum[o * 2 + 1];
    }

    // 返回区间 [DOWN,UP] 排内的已有人数（已占用位置数）的和
    long query_sum(int o, int down, int up, int DOWN, int UP) { // DOWN 和 UP 在整个递归过程中均不变，将其大写，视作常量
        if (DOWN <= down && up <= UP) return sum[o];
        var sum = 0L;
        var mid = (down + up) / 2;
        if (DOWN <= mid) sum += query_sum(o * 2, down, mid, DOWN, UP);
        if (UP > mid) sum += query_sum(o * 2 + 1, mid + 1, up, DOWN, UP);
        return sum;
    }

    // 返回有 >= val 个空位置的最低一排，不存在时返回 0（本题解排数从 1 算起，故 0 为找不到有效排）
    int index(int o, int down, int up, int UP, int val) { // UP 在整个递归过程中均不变，将其大写，视作常量
        if (min[o] > val) return 0; // 说明整个区间的元素值都大于 val
        if (down == up) return down; // 二分终止条件
        var mid = (down + up) / 2;
        if (min[o * 2] <= val) return index(o * 2, down, mid, UP, val); // 如果左子树（下方的排）的最小值符合，往左子树搜，优先往左子树搜
        if (mid < UP) return index(o * 2 + 1, mid + 1, up, UP, val); // 否则往右子树（上方的排）搜
        return 0; // 其余情况，不合法，返回 0
    }
}
/**
 * Your BookMyShow object will be instantiated and called as such:
 * BookMyShow obj = new BookMyShow(n, m);
 * int[] param_1 = obj.gather(k,maxRow);
 * boolean param_2 = obj.scatter(k,maxRow);
 */



// My Solution (线段树+二分搜索): (大部分测试用例能通过，但当中间计算值大于 int 类型则无能为力，因为本方法通过数组实现线段树而非链式存储)
import java.lang.reflect.Array;

interface Handler<E> {
    E merge(E a, E b, E defaultVal);

    void pushDown(E[] tree, E[] data, E[] marks, E defaultVal, int treeIndex, int l, int r);

    void update(E[] tree, E[] data, E[] marks, E defaultVal, int treeIndex, int l, int r, int updateL, int updateR, E val);
}

class SegmentTree<E> {
    private E[] tree; // 线段树 - SegmentTreeNodes，存的是 val
    private E[] data; // 数据
    private E[] marks; // 每个树节点的懒标记
    private E defaultVal; // E 的空默认值（空值、0 或空字符串等等）
    private Handler<E> handler; // 处理器 - 求值方程、更新方程

    public SegmentTree(Class<E> clazz, E[] arr, E defaultVal, Handler<E> handler) {
        this.handler = handler;
        this.defaultVal = defaultVal;
        data = (E[]) Array.newInstance(clazz, arr.length);
        tree = (E[]) Array.newInstance(clazz, arr.length * 4); // 大小为 4 * arr.len
        marks = (E[]) Array.newInstance(clazz, arr.length * 4);
        Arrays.fill(data, defaultVal);
        Arrays.fill(tree, defaultVal);
        Arrays.fill(marks, defaultVal);
        for (int i = 0; i < arr.length; i++) data[i] = arr[i];
        buildSegmentTree(0, 0, data.length - 1); // 构建线段树
    }

    public int getSize() {
        return data.length;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private void buildSegmentTree(int treeIndex, int l, int r) {
        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        int mid = l + (r - l) / 2;
        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);
        tree[treeIndex] = handler.merge(tree[leftTreeIndex], tree[rightTreeIndex], defaultVal);
    }

    public E query(int queryL, int queryR) {
        if (queryL < 0 || queryL > data.length || queryR < 0 || queryR > data.length || queryL > queryR) {
            throw new IllegalArgumentException("Index is illegal");
        }
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (marks[treeIndex] != defaultVal) handler.pushDown(tree, data, marks, defaultVal, treeIndex, l, r);
        if (l == queryL && r == queryR) return tree[treeIndex];
        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if (queryR <= mid) return query(leftTreeIndex, l, mid, queryL, queryR);
        if (queryL > mid) return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
        E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
        return handler.merge(leftResult, rightResult, defaultVal);
    }

    public void update(int updateL, int updateR, E val) {
        if (updateL < 0 || updateL > data.length || updateR < 0 || updateR > data.length || updateL > updateR) {
            throw new IllegalArgumentException("Index is illegal");
        }

        handler.update(tree, data, marks, defaultVal, 0, 0, data.length - 1, updateL, updateR, val);
    }
}

class BookMyShow {
    SegmentTree<Integer> segTree;
    Integer[] seats;
    int n;
    int m;

    public BookMyShow(int n, int m) {
        this.n = n;
        this.m = m;
        seats = new Integer[n*m];
        Integer defaultVal = 0;
        Arrays.fill(seats, defaultVal);
        segTree = new SegmentTree<>(Integer.class, seats, defaultVal, new Handler<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b, Integer defaultVal) {
                return a + b;
            }

            @Override
            public void pushDown(Integer[] tree, Integer[] data, Integer[] marks, Integer defaultVal, int treeIndex, int l, int r) {
                if (l > r) return;

                if (l == r) {
                    data[l] = tree[treeIndex];
                } else {
                    int leftTreeIndex = 2 * treeIndex + 1;
                    int rightTreeIndex = 2 * treeIndex + 2;
                    int mid = l + (r - l) / 2;
                    tree[leftTreeIndex] += marks[treeIndex] * (mid - l + 1);
                    marks[leftTreeIndex] += marks[treeIndex];
                    tree[rightTreeIndex] += marks[treeIndex] * (r - mid);
                    marks[rightTreeIndex] += marks[treeIndex];
                }
                marks[treeIndex] = defaultVal;
            }

            @Override
            public void update(Integer[] tree, Integer[] data, Integer[] marks, Integer defaultVal, int treeIndex, int l, int r, int updateL, int updateR, Integer val) { // 区间 [l, r] 全部自增 val（在这题里，编写调用逻辑只对区间全为 0 的区间自增 1）
                if (l >= updateL && r <= updateR) {
                    tree[treeIndex] += val * (r - l + 1); // 注意：此处计算基于 merge() 逻辑
                    marks[treeIndex] += val;
                    return;
                }
                if (marks[treeIndex] != defaultVal) pushDown(tree, data, marks, defaultVal, treeIndex, l, r);
                int leftTreeIndex = 2 * treeIndex + 1;
                int rightTreeIndex = 2 * treeIndex + 2;
                int mid = l + (r - l) / 2;
                if (updateR <= mid) update(tree, data, marks, defaultVal, leftTreeIndex, l, mid, updateL, updateR, val);
                if (updateL > mid) update(tree, data, marks, defaultVal, rightTreeIndex, mid + 1, r, updateL, updateR, val);
                if (updateL <= mid && mid < updateR) {
                    update(tree, data, marks, defaultVal, leftTreeIndex, l, mid, updateL, updateR, val);
                    update(tree, data, marks, defaultVal, rightTreeIndex, mid + 1, r, updateL, updateR, val);
                }

                tree[treeIndex] = merge(tree[leftTreeIndex], tree[rightTreeIndex], defaultVal);
            }
        });
    }

    public int[] gather(int k, int maxRow) {
        if (k > this.m) return new int[]{};
        // 2d binary search + segTree.query + segTree.update
        int startRow = 0, endRow = Math.min(this.n, maxRow+1), firstSeatIndex = -1;
        while (startRow < endRow) {
            int midRow = startRow + (endRow - startRow) / 2;
            firstSeatIndex = firstValidSeatInRow(midRow);
            if (firstSeatIndex != -1 && this.m*(midRow+1) - firstSeatIndex >= k) endRow = midRow;
            else startRow = midRow + 1;
        }
		if (startRow == Math.min(this.n, maxRow+1)) return new int[]{};
        firstSeatIndex = firstValidSeatInRow(startRow);
        segTree.update(firstSeatIndex, firstSeatIndex+k-1, 1);
        return new int[]{startRow, firstSeatIndex-this.m*startRow};
    }

    public boolean scatter(int k, int maxRow) {
        if (this.m*(maxRow+1) - segTree.query(0, this.m*(maxRow+1)-1) < k) return false;
        // 2d binary search first available row and its seat index
        while (k > 0) {
            int startRow = 0, endRow = this.n, firstSeatIndex = -1;
            while (startRow < endRow) {
                int midRow = startRow + (endRow - startRow) / 2;
                firstSeatIndex = firstValidSeatInRow(midRow);
                if (firstSeatIndex != -1) endRow = midRow;
                else startRow = midRow + 1;
            }
            firstSeatIndex = firstValidSeatInRow(startRow);
            if (firstSeatIndex+k-1 >= this.m*(startRow+1)) {
                segTree.update(firstSeatIndex, this.m*(startRow+1)-1, 1);
                k -= this.m*(startRow+1) - firstSeatIndex;
            } else {
                segTree.update(firstSeatIndex, firstSeatIndex+k-1, 1);
                k = 0;
            }
        }
        return true;
    }

    private int firstValidSeatInRow(int rowNumber) {
        int startSeat = this.m*rowNumber, endSeat = this.m*(rowNumber+1);
        while (startSeat < endSeat) {
            int midSeat = startSeat + (endSeat - startSeat) / 2;
            if (segTree.query(midSeat, midSeat) == 0) endSeat = midSeat;
            else startSeat = midSeat + 1;
        }
		if (startSeat == this.m*(rowNumber+1)) return -1;
        return startSeat;
    }
}
/**
 * Your BookMyShow object will be instantiated and called as such:
 * BookMyShow obj = new BookMyShow(n, m);
 * int[] param_1 = obj.gather(k,maxRow);
 * boolean param_2 = obj.scatter(k,maxRow);
 */
 