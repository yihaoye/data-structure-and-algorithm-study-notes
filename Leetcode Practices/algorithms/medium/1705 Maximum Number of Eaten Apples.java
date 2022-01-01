/*
There is a special kind of apple tree that grows apples every day for n days. On the ith day, the tree grows apples[i] apples that will rot after days[i] days, that is on day i + days[i] the apples will be rotten and cannot be eaten. On some days, the apple tree does not grow any apples, which are denoted by apples[i] == 0 and days[i] == 0.

You decided to eat at most one apple a day (to keep the doctors away). Note that you can keep eating after the first n days.

Given two integer arrays days and apples of length n, return the maximum number of apples you can eat.

 

Example 1:

Input: apples = [1,2,3,5,2], days = [3,2,1,4,2]
Output: 7
Explanation: You can eat 7 apples:
- On the first day, you eat an apple that grew on the first day.
- On the second day, you eat an apple that grew on the second day.
- On the third day, you eat an apple that grew on the second day. After this day, the apples that grew on the third day rot.
- On the fourth to the seventh days, you eat apples that grew on the fourth day.
Example 2:

Input: apples = [3,0,0,0,0,2], days = [3,0,0,0,0,2]
Output: 5
Explanation: You can eat 5 apples:
- On the first to the third day you eat apples that grew on the first day.
- Do nothing on the fouth and fifth days.
- On the sixth and seventh days you eat apples that grew on the sixth day.
 

Constraints:

n == apples.length == days.length
1 <= n <= 2 * 104
0 <= apples[i], days[i] <= 2 * 104
days[i] = 0 if and only if apples[i] = 0.
*/



// My Solution:
class Solution {
    public int eatenApples(int[] apples, int[] days) {
        /*
            优先队列+贪心（先吃目前最先要坏的苹果）
            时间复杂度 O(N*logN)，空间复杂度 O(N)
        */
        int res = 0, date = 0;
        PriorityQueue<Apple> pq = new PriorityQueue<>((apple1, apple2) -> apple1.rotDate - apple2.rotDate);
        while (date < apples.length) {
            int rotDate = date + days[date];
            pq.add(new Apple(rotDate, apples[date]));
            Apple apple = null;
            while (!pq.isEmpty()) {
                apple = pq.peek();
                if (apple.rotDate <= date || apple.num <= 0) {
                    apple = null;
                    pq.poll();
                } else {
                    break;
                }
            }
            if (apple != null) {
                apple.num--;
                res++;
            }
            date++;
        }
        while (!pq.isEmpty()) {
            Apple apple = null;
            while (!pq.isEmpty()) {
                apple = pq.peek();
                if (apple.rotDate <= date || apple.num <= 0) {
                    apple = null;
                    pq.poll();
                } else {
                    break;
                }
            }
            if (apple == null) break;
            apple.num--;
            res++;
            date++;
        }
        
        return res;
    }
    
    private class Apple {
        int rotDate;
        int num;
        
        public Apple(int rotDate, int num) {
            this.rotDate = rotDate;
            this.num = num;
        }
    }
}



// Other's Solution:
// TreeMap - https://leetcode-cn.com/problems/maximum-number-of-eaten-apples/solution/wei-rao-li-lun-tan-xin-dui-mei-ci-zhao-z-txr2/

// class Solution {
//     public:
//         int eatenApples(vector<int>& apples, vector<int>& days) {
//             int ans = 0;
//             int d = 0;
    
//             map<int, int> m; // (expire, cnt)
    
//             while (d < days.size() || !m.empty()) {
//                 if (d < days.size()) m[days[d]+d-1] += apples[d];
                
//                 // 尝试从map中取出一个最接近过期但是没有过期的苹果
//                 while(!m.empty()) {
//                     if (m.begin()->first < d || m.begin()->second == 0) m.erase(m.begin()->first);
//                     else {
//                         // 如果找到了 我们就吃掉它
//                         ans++;
//                         // 苹果数要减1
//                         m.begin()->second--;
//                         break;
//                     }
//                 }
//                 d++;
//             }
    
//             return ans;
//         }
//     };
