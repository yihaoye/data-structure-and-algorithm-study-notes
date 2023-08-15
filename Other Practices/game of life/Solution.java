// Leetcode 289 变体进阶版，
// 比如已知 game of life 的初始化矩阵，求第 n 代的矩阵（或每一个下一版本的矩阵）
// 1. Any live cell with two or three live neighbours survives.
// 2. Any dead cell with three live neighbours becomes a live cell.
// 3. All other live cells die in the next generation. Similarly, all other dead cells stay dead.

// 本解题方案采用 BFS 思想只关心前 live cells 并通过它们获得下一次的 live cells（其实就是坐标离散化/坐标压缩优化）
// 时间复杂度相比暴力解法 O(mn) 有所提升，只与 live cells 有关，如果矩阵很大但是 live cells 很少，那么时间复杂度高效很多
// 相比之下空间复杂度有所增加，也只与 live cells 有关

class Solution {
    private int[][] board;
    private Set<Integer> preLives = new HashSet<>();
    private int shift;

    private Set<Integer> curWith1Lives = new HashSet<>();
    private Set<Integer> curWith2Lives = new HashSet<>();
    private Set<Integer> curLives = new HashSet<>();

    public static void main(String[] args) {
        //...
    }

    public void init(int[][] board) {
        this.board = board;
        for (int[] xy : board) {
            preLives.add(hash(xy));
        }
        shift = (int) Math.pow(10, (int) Math.log10(board[0].length) + 1);
    }

    public void next() {
        curWith1Lives.addAll(preLives);
        while (!preLives.isEmpty()) {
            int preHash = preLives.iterator().next();
            int[] preXy = unhash(preHash);
            for (int[] neighbor : neighbors(preXy)) {
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
            preLives.remove(preHash);
            board[preXy[0]][preXy[1]] = 0;
        }
        while (!curLives.isEmpty()) {
            int hash = curLives.iterator().next();
            int[] xy = unhash(hash);
            board[xy[0]][xy[1]] = 1;
            preLives.add(hash);
            curLives.remove(hash);
        }
        curWith2Lives.clear();
        curWith1Lives.clear();
    }

    private int hash(int[] xy) {
        return xy[0] * shift + xy[1];
    }

    private int[] unhash(int hash) {
        return new int[]{hash / shift, hash % shift};
    }

    private List<int[]> neighbors(int[] xy) {
        List<int[]> neighbors = new ArrayList<>();
        for (int i = xy[0] - 1; i <= xy[0] + 1; i++) {
            for (int j = xy[1] - 1; j <= xy[1] + 1; j++) {
                if (i == xy[0] && j == xy[1]) continue;
                if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) continue;
                neighbors.add(new int[]{i, j});
            }
        }
        return neighbors;
    }
}
