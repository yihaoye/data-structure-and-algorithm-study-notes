/*
有限状态机，缩写为 FSM (Finite State Machine)

* 基于 Switch 语句实现的有限状态机
* 基于 State 模式实现的有限状态机
* 基于状态集合实现的有限状态机
* 基于枚举实现的状态机

参考 https://zhuanlan.zhihu.com/p/97442825
参考 Leetcode Q309，KMP 算法 DFA (Deterministic Finite Automaton - 确定有限状态自动机) 实现

*/

public class FSM {
    private int[][] states; // 状态转移表，二维数组，第一维表示当前状态，第二维表示条件，值表示下一个状态，也可以换成 Map<Integer, Map<Integer, Integer>>，第一维表示当前状态，第二维表示条件，值表示下一个状态
    private int state; // 当前状态，0 可以是指代一个合法状态也可以是一个非法状态，具体根据 rules 决定

    public FSM(int[][] rules, int start_state, int max_state, int max_condition) { // simple finite state machine example
        this.states = new int[max_state + 1][max_condition + 1];
        for (int[] rule : rules) {
            int curState = rule[0];
            int condition = rule[1];
            int nextState = rule[2];
            this.states[curState][condition] = nextState;
        }
        this.state = start_state; // 初始状态
    }

    public int nextState(int condition) {
        this.state = this.states[this.state][condition];
        return this.state;
    }
}


// ToDo ...
