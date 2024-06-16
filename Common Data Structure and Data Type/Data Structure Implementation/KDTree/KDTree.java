class KDTree {
    class Node {
        int[] point; Node left, right; int count; // count == 0 means no longer exist

        Node(int[] point) {
            this.point = point;
        }
    }

    private Node root;
    private Map<int[], Node> nodes = new HashMap<>(); // point -> node
    private int k;

    public KDTree() {
        this.root = null;
    }

    public void buildTree(int[][] points) { // ToDo 动态添加节点，采用类似线段树的动态开点以及可能需要改用 TreeMap 并添加进 Node 类中（wiki：在动态插入删除点且不允许预处理插入操作的情况下，一种平衡的方法是使用类似替罪羊树的方法重构整棵树）？
        this.k = points[0].length;
        for (int[] point : points) {
            nodes.computeIfAbsent(point, v -> new Node(point)).count++;
        }
        int[][] uniqPoints = new int[nodes.size()][k];
        int i = 0;
        for (int[] point : nodes.keySet()) {
            uniqPoints[i++] = point;
        }
        root = buildTree(uniqPoints, 0, uniqPoints.length - 1, 0);
    }

    private Node buildTree(int[][] points, int start, int end, int depth) {
        if (start > end) return null;

        int axis = depth % k; // axis（轴）用于决定节点在哪个维度上进行划分。KD 树是一种多维空间的数据结构，每个节点根据当前的深度（depth）选择一个维度（axis），并按照该维度上的值对数据进行划分。
        Arrays.sort(points, start, end + 1, Comparator.comparingInt(a -> a[axis])); // ToDo 由 buildTree 参数提供具体的 Comparator 排序实现而不是在此处写死
        
        int mid = start + (end - start) / 2;
        int[] point = points[mid];
        Node node = nodes.get(point);
        node.left = buildTree(points, start, mid - 1, depth + 1);
        node.right = buildTree(points, mid + 1, end, depth + 1);
        return node;
    }

    public Node getRoot() {
        return root;
    }

    public void rangeSearch(Node node, int[] minBound, int[] maxBound, int depth, List<Node> list, PriorityQueue<Node> pq) { // 这里 PriorityQueue 并不是必须的，只是在部分使用场景下通常还要求额外排序，放在此处可以最大优化性能，可以视情况删除相关逻辑
        if (node == null) return;
        if (node.count > 0 && isWithinBounds(node.point, minBound, maxBound)) {
            if (list != null) list.add(node);
            if (pq != null) pq.add(node);
        }
        int axis = depth % k;
        if (minBound[axis] <= node.point[axis]) rangeSearch(node.left, minBound, maxBound, depth + 1, list, pq);
        if (maxBound[axis] >= node.point[axis]) rangeSearch(node.right, minBound, maxBound, depth + 1, list, pq);
    }

    private boolean isWithinBounds(int[] point, int[] minBound, int[] maxBound) {
        for (int i = 0; i < k; i++) {
            if (point[i] < minBound[i] || point[i] > maxBound[i]) return false;
        }
        return true;
    }

    public boolean delete(int[] point) { // ToDo 在支持动态添加节点后，也许也可以支持硬删除节点（当 count == 0 时）
        if (!nodes.containsKey(point)) return false;
        Node node = nodes.get(point);
        node.count--;
        return true;
    }

    public static void main(String[] args) {
        KDTree kdTree = new KDTree();
        int[][] points = new int[][]{{3, 6}, {17, 15}, {13, 15}, {6, 12}, {9, 1}, {2, 7}, {10, 19}};
        kdTree.buildTree(points);
        int[] minBound = new int[]{5, 5}; int[] maxBound = new int[]{15, 15};
        List<Node> list = new ArrayList<>();
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> b.point[1] - a.point[1]);
        kdTree.rangeSearch(kdTree.getRoot(), minBound, maxBound, 0, list, pq);
        System.out.println(list); System.out.println(pq);
    }
}