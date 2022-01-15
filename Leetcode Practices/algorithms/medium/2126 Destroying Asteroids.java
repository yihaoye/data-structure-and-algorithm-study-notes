/*
You are given an integer mass, which represents the original mass of a planet. You are further given an integer array asteroids, where asteroids[i] is the mass of the ith asteroid.

You can arrange for the planet to collide with the asteroids in any arbitrary order. If the mass of the planet is greater than or equal to the mass of the asteroid, the asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the planet is destroyed.

Return true if all asteroids can be destroyed. Otherwise, return false.

 

Example 1:

Input: mass = 10, asteroids = [3,9,19,5,21]
Output: true
Explanation: One way to order the asteroids is [9,19,5,3,21]:
- The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
- The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
- The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
- The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
- The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
All asteroids are destroyed.
Example 2:

Input: mass = 5, asteroids = [4,9,23,4]
Output: false
Explanation: 
The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
This is less than 23, so a collision would not destroy the last asteroid.
 

Constraints:

1 <= mass <= 105
1 <= asteroids.length <= 105
1 <= asteroids[i] <= 105
*/



// My Solution:
class Solution {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        /*
            排序+贪心 O(N*logN)，先与最小值相撞
        */
        Arrays.sort(asteroids);
        long lMass = (long) mass;
        for (int asteroid : asteroids) {
            if (lMass >= asteroid) lMass += asteroid;
            else return false;
        }
        
        return true;
    }
}



// Other's Solution:
class Solution {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        /*
            线性时间复杂度解 O(N)：https://leetcode-cn.com/problems/destroying-asteroids/solution/xian-xing-shi-jian-javayu-yan-miao-shu-b-edel/
        */
        int n = asteroids.length;
        int M = 17, W = 100001;
        int[] v = new int[M];
        int[] s = new int[M];
        for (int i=0; i<M; ++i) v[i] = W; // 初始化
        for(int x : asteroids) {
            int p = func(x); // 获取该桶下标
            v[p] = Math.min(x, v[p]); // 数据最小值放入
            s[p] = Math.min(W, s[p]+x); // 该桶内的所有数据，如果该桶数据和大于 100000，那么如果吃下了这个桶就可以畅通无阻了
        }
        for (int i=0; i<M; ++i){
			if (v[i] < W && v[i] > mass) return false; // 判断
            // v[i]<W 是未来跳过没有放入数据的桶
			mass += s[i];
		}
        return true;
    }
    
    public int func(int n) {
        // 计算该数属于哪个桶
        // 因为最多遍历16，所以时间复杂度是O(1)
        for (int i = 16; i>=0; --i) {
            if (((n>>i) & 1) == 1)
            return i;
        }
        return 0;
    }
}
