/**
You are given an array trees where trees[i] = [xi, yi] represents the location of a tree in the garden.

Fence the entire garden using the minimum length of rope, as it is expensive. The garden is well-fenced only if all the trees are enclosed.

Return the coordinates of trees that are exactly located on the fence perimeter. You may return the answer in any order.

 

Example 1:
https://assets.leetcode.com/uploads/2021/04/24/erect2-plane.jpg

Input: trees = [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
Output: [[1,1],[2,0],[4,2],[3,3],[2,4]]
Explanation: All the trees will be on the perimeter of the fence except the tree at [2, 2], which will be inside the fence.

Example 2:
https://assets.leetcode.com/uploads/2021/04/24/erect1-plane.jpg

Input: trees = [[1,2],[2,2],[4,2]]
Output: [[4,2],[2,2],[1,2]]
Explanation: The fence forms a line that passes through all the trees.
 

Constraints:

1 <= trees.length <= 3000
trees[i].length == 2
0 <= xi, yi <= 100
All the given positions are unique.
*/



// Other's Solution:
class Solution {
    public int[][] outerTrees(int[][] trees) {
        // 二维凸包（Andrew 算法）- https://leetcode.cn/problems/erect-the-fence/solution/by-ac_oier-4xuu/
        // Time: O(logN), Space: O(N)
        Arrays.sort(trees, (a, b) -> {
            return a[0] != b[0] ? a[0] - b[0] : a[1] - b[1];
        });
        // 为了方便取得栈顶的前两位元素，使用数组实现栈，stk 代表栈容器，tp 代表栈顶元素下标
        int n = trees.length, tp = 0;
        int[] stk = new int[n + 2];
        boolean[] vis = new boolean[n + 2]; // vis 的作用仅是为了处理凸包第二部分的时候，不要使用到凸包第一部分的点而已。含义并非是处理过的点，或者当前凸包上的点，比如下面的「不标记起点」
        stk[++tp] = 0; // 不标记起点

        for (int i = 1; i < n; i++) { // 上凸壳
            int[] c = trees[i];
            while (tp >= 2) {
                int[] a = trees[stk[tp - 1]], b = trees[stk[tp]];
                if (getArea(a, b, c) > 0) vis[stk[tp--]] = false;
                else break;
            }
            stk[++tp] = i;
            vis[i] = true;
        }
        int size = tp;

        for (int i = n - 1; i >= 0; i--) { // 下凸壳，逻辑同理，唯一需要注意的是，第一部分的凸包边不能删去，假定处理完第一部分凸包，栈内有 m 个元素，需要将上述「栈顶元素不少于 2 个」的逻辑替换为「栈顶元素大于 m 个」，同时已参与到凸包第一部分的点，不能再考虑，因此需要额外使用一个 vis 数组来记录使用过的点
            if (vis[i]) continue;
            int[] c = trees[i];
            while (tp > size) {
                int[] a = trees[stk[tp - 1]], b = trees[stk[tp]];
                if (getArea(a, b, c) > 0) vis[stk[tp--]] = false; // 其实 tp--; 即可，不用设置 vis，因为这是最后一次遍历
                else break;
            }
            stk[++tp] = i;
            vis[i] = true; // 非必须，同样因为这是最后一次遍历
        }

        int[][] ans = new int[tp - 1][2];
        for (int i = 1; i < tp; i++) ans[i - 1] = trees[stk[i]];
        return ans;
    }

    /**
     * 将两个点转换为一个向量，例如点 a(x1, y1), b(x2, y2) 则转化为向量 a ——> b = (x2-x1, y2-y1)
     *
     * @param b 向量终点
     * @param a 向量起始点
     * @return 返回向量 a ——> b
     */
    public int[] subtraction(int[] a, int[] b) { // 亦即向量相减
        return new int[]{a[0] - b[0], a[1] - b[1]};
    }

    /**
     * 求向量叉积，输入向量 a，向量 b，其叉积几何定义等于 axb = |a||b|sinθ，数学定义为 axb = (x1*y2 - y1*x2)
     *
     * @param a 输入向量 a
     * @param b 输入向量 b
     * @return 返回向量 axb 的叉积（axb = -bxa）
     */
    public int cross(int[] a, int[] b) { // 叉乘
        return a[0] * b[1] - a[1] * b[0];
    }

    /**
     * 输入三个点，a、b、c，其中 a 作为起始点，b、c 分别为两个向量的终点，将 abc 三点转化为两个向量 ab、ac，
     * 然后返回 ab 旋转到 ac 这条向量上的面积 s，对于面积 s
     * 1、若 s < 0，表明 ab 到 ac 需顺时针旋转；
     * 2、若 s > 0，表明 ab 到 ac 需逆时针旋转；
     * 3、若 s = 0，表明 ab 和 ac 同处于一个水平线上。
     * 可以使用右手定则自个判断一下（大拇指朝上为正，朝下为负，以大拇指为轴，四个手指垂直于起始向量 ab 然后往 ac 旋转）
     *
     * @param a 点 a
     * @param b 点 b
     * @param c 点 c
     * @return 返回向量 ab 和向量 ac 的叉积
     */
    public int getArea(int[] a, int[] b, int[] c) { // 向量 ab 转为 向量 ac 过程中扫过的面积
        return cross(subtraction(b, a), subtraction(c, a));
    }
}
