/* Question:
描述如何只用一个数组来实现三个栈
*/



// 固定分割法
public class Solution {
    int stackSize = 100;
    int[] buffer = new int[stackSize * 3];
    int[] stackPointer = {-1, -1, -1}; // 用于追踪栈顶元素的指针

    void push(int stackNum, int value) throws Exception {
        /* 检查有无空闲空间 */
        if (stackPointer[stackNum] + 1 >= stackSize) { // 最后一个元素
            throw new Exception("Out of space.");
        }
        /* 栈指针自增，然后更新栈顶元素的值 */
        stackPointer[stackNum]++;
        buffer[absTopOfStack(stackNum)] = value;
    }

    int pop(int stackNum) throws Exception {
        if (stackPointer[stackNum] == -1) {
            throw new Exception("Trying to pop an empty stack.");
        }
        int value = buffer[absTopOfStack(stackNum)]; // 获取栈顶元素的值
        buffer[absTopOfStack(stackNum)] = 0; // 清零指定索引元素的值
        stackPointer[stackNum]--; // 指针自减
        return value;
    }

    int peek(int stackNum) {
        int index = absTopOfStack(stackNum);
        return buffer[index];
    }

    boolean isEmpty(int stackNum) {
        return stackPointer[stackNum] == -1;
    }

    /* 返回栈"stackNum"栈顶元素的索引，绝对量 */
    int absTopOfStack(int stackNum) {
        return stackNum * stackSize + stackPointer[stackNum];
    }
}