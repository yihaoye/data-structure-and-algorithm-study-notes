// Leetcode 289 变体进阶版，
// 比如已知 game of life 的初始化矩阵，求第 n 代的矩阵（或每一个下一版本的矩阵）
// 1. Any live cell with two or three live neighbours survives.
// 2. Any dead cell with three live neighbours becomes a live cell.
// 3. All other live cells die in the next generation. Similarly, all other dead cells stay dead.

// 本解题方案采用 BFS 思想只关心前 live cells 并通过它们获得下一次的 live cells（其实就是坐标离散化/坐标压缩优化）
// 时间复杂度相比暴力解法 O(mn) 有所提升，只与 live cells 有关，如果矩阵很大但是 live cells 很少，那么时间复杂度高效很多
// 相比之下空间复杂度有所增加，也只与 live cells 有关
// 而且如果在系统设计中此法也可以进一步利用多线程（或多服务器）提升性能，需要把 preLives 等成员变量换成线程安全的数据结构如 BlockingQueue (或消息队列 Kafka)、ConcurrentHashMap（或 Redis，但是要注意加锁避免同时多个 +1 时只实际执行一个）且即可

class Solution {
    private int[][] board;
    private Queue<int[]> preLives = new LinkedList<>();
    private int shift; // used for hash and unhash

    private Set<Integer> curWith1Lives = new HashSet<>(); // 这里的三个 set 可以用一个 map 代替 <count, <lives set...>>
    private Set<Integer> curWith2Lives = new HashSet<>();
    private Set<Integer> curLives = new HashSet<>();

    public static void main(String[] args) {
        //...
    }

    public void init(int[][] board) {
        this.board = board;
        for (int[] xy : board) {
            preLives.offer(xy);
        }
        shift = (int) Math.pow(10, (int) Math.log10(board[0].length) + 1);
    }

    public void nextGrid() { // 只由主线程/主服务调用
        while (!preLives.isEmpty()) { // 由子线程/子服务调用
            int[] preXy = preLives.poll();
            next(preXy);
        }

        while (!curLives.isEmpty()) {
            int hash = curLives.iterator().next();
            int[] xy = unhash(hash);
            board[xy[0]][xy[1]] = 1;
            preLives.offer(xy);
            curLives.remove(hash);
        }
        curWith2Lives.clear();
        curWith1Lives.clear();
    }

    public void next(int[] preXy) {
        for (int[] neighbor : neighbors(preXy)) { // 包括 xy 自己也需要循环
            int neighborHash = hash(neighbor);
            if (curLives.contains(neighborHash)) continue;

            if (curWith1Lives.contains(neighborHash)) {
                curWith1Lives.remove(neighborHash);
                curWith2Lives.add(neighborHash);
            } else if (curWith2Lives.contains(neighborHash)) {
                curWith2Lives.remove(neighborHash);
                curLives.add(neighborHash);
            } else {
                curWith1Lives.add(neighborHash);
            }
        }
        board[preXy[0]][preXy[1]] = 0;
    }

    private int hash(int[] xy) {
        return xy[0] * shift + xy[1];
    }

    private int[] unhash(int hash) {
        return new int[]{hash / shift, hash % shift};
    }

    private List<int[]> neighbors(int[] xy) { // 需要包括 xy 自己也添加进 result
        List<int[]> neighbors = new ArrayList<>();
        for (int i = xy[0] - 1; i <= xy[0] + 1; i++) {
            for (int j = xy[1] - 1; j <= xy[1] + 1; j++) {
                if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) continue;
                neighbors.add(new int[]{i, j});
            }
        }
        return neighbors;
    }
}
