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
    private int[][] states; // 状态转移表，二维数组，第一维表示当前状态，第二维表示条件，值表示下一个状态，也可以换成 Map<Integer, Map<Integer, Integer>> 使得内存使用更紧凑，第一维表示当前状态，第二维表示条件，值表示下一个状态，如下方示例 2 所示
    private int state; // 当前状态，0 可以是指代一个合法状态也可以是一个非法状态，具体根据 rules 决定

    public FSM(int[][] rules, int init_state, int max_state, int max_condition) { // simple finite state machine example
        this.states = new int[max_state + 1][max_condition + 1];
        for (int[] rule : rules) {
            int curState = rule[0];
            int condition = rule[1];
            int nextState = rule[2];
            this.states[curState][condition] = nextState;
        }
        this.state = init_state; // 初始状态
    }

    public int nextState(int condition) {
        this.state = this.states[this.state][condition];
        return this.state;
    }

    public int getState() {
        return this.state;
    }
}





enum State {
    OFF,
    ON,
    UNKNOWN
}

enum Condition {
    TURN_ON,
    TURN_OFF,
    UNKNOWN
}

public class FSM {
    private Map<State, Map<Condition, State>> states;
    private State currentState;

    public FSM(int[][] rules, State initState) {
        states = new HashMap<>();
        for (int[] rule : rules) {
            State curState = State.values()[rule[0]];
            Condition condition = Condition.values()[rule[1]];
            State nextState = State.values()[rule[2]];
            states.computeIfAbsent(curState, k -> new HashMap<>());
            states.get(curState).put(condition, nextState);
        }
        currentState = initState;
    }

    public State nextState(Condition condition) {
        if (!states.containsKey(currentState) || !states.get(currentState).containsKey(condition)) {
            throw new IllegalArgumentException("Invalid condition or state");
        }
        currentState = states.get(currentState).get(condition);
        return currentState;
    }

    public State getCurrentState() {
        return currentState;
    }
}





// ToDo ...
