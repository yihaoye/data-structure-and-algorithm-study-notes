/*
Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks. Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.

However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.

You need to return the least number of intervals the CPU will take to finish all the given tasks.

 

Example:

Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 

Note:

The number of tasks is in the range [1, 10000].
The integer n is in the range [0, 100].
*/



// Other's Solution 1:
public class Solution {
    public int leastInterval(char[] tasks, int n) {
        // 数学 - https://leetcode.com/problems/task-scheduler/discuss/104496/concise-Java-Solution-O(N)-time-O(26)-space
        // (maxCharLen - 1) * (n + 1) + sameMaxCharLenCount is frame size
        // when inserting chars, the frame might be "burst", then tasks.length takes precedence
        // when sameMaxCharLenCount > n, the frame is already full at construction, the following is still valid.
        // Time: O(N), Space: O(1)
        int[] record = new int[26];
        for (char task : tasks) record[(task-'A')]++;

        int max = 0, count = 1;
        for (int num : record) {
            if (num == 0) continue;
            if (max < num) {
                max = num;
                count = 1;
            } else if (max == num) {
                count++;
            }
        }
        return Math.max(tasks.length, (n + 1) * (max - 1) + count);
    }
}



// Other's Solution 2:
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // count char freq, task name doesn't matter, only freq matters
        int[] freq = new int[26];
        for (char c: tasks) freq[c - 'A']++;
        // sort first, so we have max freq at freq[25]
        Arrays.sort(freq);
        int time = 0;
        while (freq[25] > 0) { // while we still have task to do, start from most freq task
            // GREEDY
            // each round/row, try to finish n tasks
            for (int i = 0, p = 25; i <= n; i++) { // n is the cooling down factor, p points to the next task to consume
                if (p >= 0 && freq[p] > 0) { // if there is still task to do
                    freq[p]--; // do task
                    p--;       // move p to next freq task
                    time++;    // take one cycle
                } else if (freq[25] != 0) { // if this is NOT last row, need to fill in idle cycle
                    time++;    // take one cycle
                } // else freq[25] == 0 .   no more task to do and last row. we WON'T fill in idle cycle
            }
            // sort again so next round we're going to start from most freq task and consume n task if possible
            Arrays.sort(freq);
        }
        return time;
    }
}



// My Solution 1: (not finished)
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int res = 0;
        Map<Character, Integer> taskMap = new TreeMap<>();
        for (char task : tasks) {
            if (taskMap.containsKey(task)) taskMap.put(task, taskMap.get(task)+1);
            else taskMap.put(task, 1);
        }
        boolean last = false;
        while (true) {
            if (taskMap.isEmpty()) break;
            res += (taskMap.size() > n+1 ? taskMap.size() : n+1);
            Iterator it = taskMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
                if (pair.getValue() > 1) {
                    taskMap.put(pair.getKey(), pair.getValue()-1);
                } else {
                    it.remove(); // avoids a ConcurrentModificationException, e.g. remove in for (Character key : taskMap.keySet()) {...}
                }
            }
        }
        
        return res;
    }
}



// My Solution 2 (Kind of Greedy):
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int res = 0;
        Arrays.sort(tasks);

        int len = countLetter(tasks);
        int[][] freq = new int[len][2]; // freq[i][0] is letter's count, freq[i][1] is last put in position index in resList (init -n-1)
        for (int[] f : freq) f[1] = -n-1;
        for (int i=0, j=0; i < tasks.length; i++) {
            if (i > 0 && tasks[i-1] != tasks[i]) j++;
            freq[j][0]++;
        }
        Arrays.sort(freq, (a, b) -> Integer.compare(a[0], b[0]));

        while(true) {
            if (freq[len-1][0] == 0) break;
            // for example: if freq = [2, 3, 5], n = 2
            // then need to take care of freq[-1] first, since it is most frequency,
            // which means every time, when we come to a resList new position, we always pickup the most frequency one and check if it is valid to put it in, if not check the second most frequency and so on ..., if finally non of them can put in this position, then we consider put in an idle
            for (int i=len-1; i >= 0; i--) {
                if (res - freq[i][1] > n) {
                    freq[i][0]--;
                    freq[i][1] = res;
                    break;
                }
            }
            res++;
            Arrays.sort(freq, (a, b) -> Integer.compare(a[0], b[0]));
        }
        
        return res;
    }

    // this method only works for sorted non-empty char array, so need sort and confirm non-empty before call the method.
    public int countLetter(char[] charArr) {
        int res = 1;
        char tempLetter = charArr[0];
        for (char c : charArr) {
            if (c != tempLetter) {
                res++;
                tempLetter = c;
            }
        }
        return res;
    }
}



// My Solution 3:
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // 贪婪 + 记录每个字符的上次使用时间（挑剩余次数更多的可选字符优先处理）
        // 需要先遍历一次进行记录，后续需要实时排序（首先根据剩余任务次数，如果最高次数有相同的就找更早前使用的），但因为字符数量不多就暴力找即可不用排序
        // Time: O(N), Space: O(1)
        int[][] dict = new int[26][2]; // [char: {lastUsedTime, taskLeftCount}, ...]
        for (char task : tasks) {
            dict[task-'A'][0] = -101;
            dict[task-'A'][1]++;
        }
        
        int k = 0, res = 0;
        while (!updateBestChar(dict, ++k, n)) res++;
        
        return res;
    }
    
    private boolean updateBestChar(int[][] dict, int k, int n) { // update best char's lastUsedTime and taskLeftCount
        boolean allUsed = true;
        int bestCharIndex = -1;
        for (int i=0; i<dict.length; i++) {
            if (dict[i][1] != 0) allUsed = false;
            if (dict[i][0] + n < k && dict[i][1] > 0) {
                if (bestCharIndex == -1 || dict[i][1] > dict[bestCharIndex][1] || (dict[i][1] == dict[bestCharIndex][1] && dict[i][0] < dict[bestCharIndex][0])) bestCharIndex = i;
            }
        }
        if (bestCharIndex != -1) {
            dict[bestCharIndex][0] = k;
            dict[bestCharIndex][1]--;
        }
        
        return allUsed;
    }
}
