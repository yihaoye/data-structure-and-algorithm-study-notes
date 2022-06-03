class SegmentTree {
    /*
        Author: Huahua - https://zxi.mytechroad.com/blog/sp/segment-tree-sp14/
        时间复杂度 O(logN)，空间复杂度 O(N)
        这里代码实现 val 是 sum 值，可以根据情况需要改成 min、max、其他计算值/最值等等
    */
    private int[] arr;
    private SegmentTreeNode root;

    public SegmentTree(int[] arr) {
        this.arr = arr;
        if (this.arr != null) this.root = buildTree(0, arr.length-1);
    }

    public void update(int index, int val) {
        updateTree(this.root, index, val);
    }

    public int sumRange(int left, int right) {
        return sumRange(this.root, left, right);
    }

    private SegmentTreeNode buildTree(int start, int end) {
        if (start == end) return new SegmentTreeNode(start, end, this.arr[start], null, null);
        int mid = start + (end - start) / 2;
        SegmentTreeNode left = buildTree(start, mid);
        SegmentTreeNode right = buildTree(mid + 1, end);
        return new SegmentTreeNode(start, end, left.val + right.val, left, right);
    }

    private void updateTree(SegmentTreeNode root, int index, int val) {
        if (root.start == index && root.end == index) {
            root.val = val;
            return;
        }

        if (index <= root.mid) updateTree(root.left, index, val);
        else updateTree(root.right, index, val);

        root.val = root.left.val + root.right.val;
    }

    private int sumRange(SegmentTreeNode root, int left, int right) {
        if (root.start == left && root.end == right) return root.val;

        if (right <= root.mid) return sumRange(root.left, left, right);
        else if (left > root.mid) return sumRange(root.right, left, right);
        else return sumRange(root.left, left, root.mid) + sumRange(root.right, root.mid + 1, right);
    }

    class SegmentTreeNode {
        int start;
        int end;
        int mid;
        int val;
        SegmentTreeNode left;
        SegmentTreeNode right;

        public SegmentTreeNode(int start, int end, int val, SegmentTreeNode left, SegmentTreeNode right) {
            this.start = start;
            this.end = end;
            this.mid = start + (end - start) / 2;
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
