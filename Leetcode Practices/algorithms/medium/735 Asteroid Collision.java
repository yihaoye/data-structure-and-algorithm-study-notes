/*
We are given an array asteroids of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

 

Example 1:

Input: asteroids = [5,10,-5]
Output: [5,10]
Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
Example 2:

Input: asteroids = [8,-8]
Output: []
Explanation: The 8 and -8 collide exploding each other.
Example 3:

Input: asteroids = [10,2,-5]
Output: [10]
Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.
 

Constraints:

2 <= asteroids.length <= 10^4
-1000 <= asteroids[i] <= 1000
asteroids[i] != 0
*/



// My Solution:
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        // 栈
        Deque<Integer> stack = new LinkedList<>();
        for (int asteroid : asteroids) {
            if (asteroid > 0) { // asteroids[i] != 0
                stack.push(asteroid);
                continue;
            }
            while (!stack.isEmpty()) {
                int left = stack.peek();
                if (left < 0) break;
                
                if (left <= -asteroid) stack.pop();
                if (left >= -asteroid) {
                    asteroid = 0;
                    break;
                }
            }
            if (asteroid != 0) stack.push(asteroid);
        }
        int[] res = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) res[i++] = stack.pollLast();
        return res;
    }
}
