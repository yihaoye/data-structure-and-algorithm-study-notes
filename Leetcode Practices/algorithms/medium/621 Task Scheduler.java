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



// Other's Solution:
public class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] storage = new int[26];
        for (char c : tasks) {
            storage[(c - 'A')]++;
        }
        int max = 0;
        int count = 1;
        for (int num : storage) {
            if (num == 0) {
                continue;
            }
            if (max < num) {
                max = num;
                count = 1;
            } else if (max == num) {
                count++;
            }
        }
        int space = (n + 1) * (max - 1) + count;
        return (space < tasks.length) ? tasks.length : space;
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



// My Solution 2: (not finished)
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int res = 0;
        Arrays.sort(tasks);
        
        char tempLetter = tasks[0];
        int count = 0;
        for (char task : tasks) {
            if (tempLetter != task) {
                // arrList.add(count);
                count = 1;
                tempLetter = task;
            } else {
                count++;
            }
        }
        
        return res;
    }
}