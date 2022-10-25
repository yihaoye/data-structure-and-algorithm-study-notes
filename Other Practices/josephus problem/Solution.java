// https://www.youtube.com/watch?v=Yeh1_2GyS5s

class Solution {
    public int removeKth(int N, int K) {
        // f(1, K) = 1
        // f(N, K) = (f(N-1, K) + K) % N
        // 迭代而不是递归，可使 Time: O(N), Space: O(1)
    }
}
