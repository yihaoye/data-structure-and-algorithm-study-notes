/*
https://zh.wikipedia.org/zh-hans/%E5%9B%BE%E7%81%B5%E6%9C%BA

基本思想是用机器来模拟人们用纸笔进行数学运算的过程，这样的过程看作下列两种简单的动作：
    * 在纸上写上或擦除某个符号；
    * 把注意力从纸的一处移动到另一处；
而在每个阶段，人要决定下一步的动作，依赖于此人当前所关注的纸上某个位置的符号和此人当前思维的状态。

图灵机由以下几个部分组成：
    1. 一条无限长的纸带 TAPE。纸带被划分为一个接一个的小格子，每个格子上包含一个来自有限字母表的符号，字母表中有一个特殊的符号表示空白。纸带上的格子从左到右依次被编号为 0, 1, 2, ...，纸带的右端可以无限伸展。
    2. 一个读写头 HEAD。它可以在纸带上左右移动，能读出当前所指的格子上的符号，并能改变它。
    3. 一套控制规则数量有限的 TABLE（A finite table of instructions）。它根据当前机器所处的状态以及当前读写头所指的格子上的符号来确定读写头下一步的动作，并改变状态寄存器的值，令机器进入一个新的状态。
        按照以下顺序告知图灵机命令：
        1. 写入（替换）或擦除当前符号；
        2. 移动 HEAD，'L' 向左，'R' 向右或者 'N' 不移动；
        3. 保持当前状态或者转到另一状态。
    4. 一个状态寄存器。它用来保存图灵机当前所处的状态。图灵机的所有可能状态的数目是有限的，并且有一个特殊的状态，称为停机状态。参见停机问题。
注意这个机器的每一部分都是有限的，但它有一个潜在的无限长的纸带，因此这种机器只是一个理想的设备。图灵认为这样的一台机器就能模拟人类所能进行的任何计算过程。
*/
import java.util.HashMap;
import java.util.Map;

public class TuringMachine { // by ChatGPT
    private static final char BLANK = '_'; // Blank symbol on the tape

    private static class Transition {
        char writeSymbol;
        char moveDirection; // 'L' for left, 'R' for right
        String nextState;

        public Transition(char writeSymbol, char moveDirection, String nextState) {
            this.writeSymbol = writeSymbol;
            this.moveDirection = moveDirection;
            this.nextState = nextState;
        }
    }

    private String currentState;
    private int headPosition;
    private Map<String, Map<Character, Transition>> transitionFunction; // Map<currentState, Map<readSymbol, Transition>>
    private char[] tape; // 如果希望支持动态扩展可以用 ArrayList

    public TuringMachine(String initialState, String input, int tapeLength) {
        this.currentState = initialState;
        this.headPosition = 0;
        this.transitionFunction = new HashMap<>();
        this.tape = new char[tapeLength];
        
        // Initialize tape with the input and blanks
        for (int i = 0; i < tape.length; i++) {
            if (i < input.length()) {
                tape[i] = input.charAt(i);
            } else {
                tape[i] = BLANK;
            }
        }
    }

    public void addTransition(String state, char readSymbol, char writeSymbol, char moveDirection, String nextState) { // 方法用于添加状态转移
        transitionFunction.putIfAbsent(state, new HashMap<>());
        transitionFunction.get(state).put(readSymbol, new Transition(writeSymbol, moveDirection, nextState));
    }

    public void run() {
        while (true) {
            char currentSymbol = tape[headPosition];
            if (!transitionFunction.containsKey(currentState) || !transitionFunction.get(currentState).containsKey(currentSymbol)) {
                break; // Halt if no transition is defined for the current state and symbol
            }
            
            Transition transition = transitionFunction.get(currentState).get(currentSymbol);
            tape[headPosition] = transition.writeSymbol;
            headPosition += (transition.moveDirection == 'R' ? 1 : 0);
            headPosition += (transition.moveDirection == 'L' ? -1 : 0);
            currentState = transition.nextState;
            
            if (headPosition < 0 || headPosition >= tape.length) {
                break; // Halt if the head moves out of the tape bounds
            }
        }
    }

    public void printTape() {
        for (char c : tape) {
            System.out.print(c);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Define the initial state, input, and tape length
        TuringMachine tm = new TuringMachine("q0", "1011", 20);

        // Define the transition function
        tm.addTransition("q0", '1', 'x', 'R', "q1"); // q0: 当前状态。如果图灵机的当前状态是 q0。'1': 当前读取的符号。如果图灵机磁带头下的符号是 1。'x': 要写入的符号。图灵机将 1 替换为 x，即将 x 写入磁带头当前位置。'R': 移动方向。图灵机的磁带头向右移动一格 (R 表示右移，L 表示左移)。q1: 下一状态。图灵机进入状态 q1。
        tm.addTransition("q0", '0', '0', 'R', "q0");
        tm.addTransition("q0", '_', '_', 'R', "q2");
        tm.addTransition("q1", '1', '1', 'R', "q1");
        tm.addTransition("q1", '0', '0', 'R', "q1");
        tm.addTransition("q1", '_', '_', 'L', "q2");
        tm.addTransition("q2", '1', '1', 'L', "q2");
        tm.addTransition("q2", '0', '0', 'L', "q2");
        tm.addTransition("q2", 'x', '1', 'R', "q3");

        // Run the Turing machine
        tm.run();

        // Print the tape after running the Turing machine
        tm.printTape();
    }
}
