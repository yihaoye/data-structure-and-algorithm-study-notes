/*
力扣团队买了一个可编程机器人，机器人初始位置在原点(0, 0)。小伙伴事先给机器人输入一串指令command，机器人就会无限循环这条指令的步骤进行移动。指令有两种：

U: 向y轴正方向移动一格
R: 向x轴正方向移动一格。
不幸的是，在 xy 平面上还有一些障碍物，他们的坐标用obstacles表示。机器人一旦碰到障碍物就会被损毁。

给定终点坐标(x, y)，返回机器人能否完好地到达终点。如果能，返回true；否则返回false。

 

示例 1：

输入：command = "URR", obstacles = [], x = 3, y = 2
输出：true
解释：U(0, 1) -> R(1, 1) -> R(2, 1) -> U(2, 2) -> R(3, 2)。
示例 2：

输入：command = "URR", obstacles = [[2, 2]], x = 3, y = 2
输出：false
解释：机器人在到达终点前会碰到(2, 2)的障碍物。
示例 3：

输入：command = "URR", obstacles = [[4, 2]], x = 3, y = 2
输出：true
解释：到达终点后，再碰到障碍物也不影响返回结果。
 

限制：

2 <= command的长度 <= 1000
command由U，R构成，且至少有一个U，至少有一个R
0 <= x <= 1e9, 0 <= y <= 1e9
0 <= obstacles的长度 <= 1000
obstacles[i]不为原点或者终点

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/programmable-robot
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*/



// Other's Solution:
class Solution {
    public boolean robot(String command, int[][] obstacles, int x, int y) {
        // 多次循环 找到模式
        // 新的存储坐标的方法 - 左坐标左移30 | 右坐标
        // https://leetcode-cn.com/problems/programmable-robot/solution/c-4ms-jie-da-by-yanghk/
        // 时间：O(m+n)，其中 m = command.size()，n = obstacles.size()
        // 空间：O(m)
        int xx=0, yy=0;
        Set<Long> ss = new HashSet<>();
        ss.add(((long)xx << 30) | yy);
        for (int i=0; i<command.length(); i++) {
            if (command.charAt(i) == 'U') yy++;
            else xx++;
            ss.add(((long)xx << 30) | yy);
        }
        int loopNum = Math.min(x/xx, y/yy);
        if (ss.contains(((long)(x-loopNum*xx) << 30) | (y-loopNum*yy)) == false) return false;

        for (int[] s : obstacles) {
            int x1 = s[0], y1 = s[1];
            if (x1>x || y1>y) continue; // 到达终点后，再碰到障碍物也不影响返回结果
            loopNum = Math.min(x1/xx, y1/yy);
            if (ss.contains(((long)(x1-loopNum*xx) << 30) | (y1-loopNum*yy)) == true) return false;
        }
        return true;
    }
}
