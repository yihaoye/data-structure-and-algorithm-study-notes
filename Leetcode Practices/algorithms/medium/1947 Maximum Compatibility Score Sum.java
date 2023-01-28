/**
There is a survey that consists of n questions where each question's answer is either 0 (no) or 1 (yes).

The survey was given to m students numbered from 0 to m - 1 and m mentors numbered from 0 to m - 1. The answers of the students are represented by a 2D integer array students where students[i] is an integer array that contains the answers of the ith student (0-indexed). The answers of the mentors are represented by a 2D integer array mentors where mentors[j] is an integer array that contains the answers of the jth mentor (0-indexed).

Each student will be assigned to one mentor, and each mentor will have one student assigned to them. The compatibility score of a student-mentor pair is the number of answers that are the same for both the student and the mentor.

For example, if the student's answers were [1, 0, 1] and the mentor's answers were [0, 0, 1], then their compatibility score is 2 because only the second and the third answers are the same.
You are tasked with finding the optimal student-mentor pairings to maximize the sum of the compatibility scores.

Given students and mentors, return the maximum compatibility score sum that can be achieved.

 

Example 1:

Input: students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
Output: 8
Explanation: We assign students to mentors in the following way:
- student 0 to mentor 2 with a compatibility score of 3.
- student 1 to mentor 0 with a compatibility score of 2.
- student 2 to mentor 1 with a compatibility score of 3.
The compatibility score sum is 3 + 2 + 3 = 8.
Example 2:

Input: students = [[0,0],[0,0],[0,0]], mentors = [[1,1],[1,1],[1,1]]
Output: 0
Explanation: The compatibility score of any student-mentor pair is 0.
 

Constraints:

m == students.length == mentors.length
n == students[i].length == mentors[j].length
1 <= m, n <= 8
students[i][k] is either 0 or 1.
mentors[j][k] is either 0 or 1.
 */



// My Solution: improved by - https://leetcode.com/problems/maximum-compatibility-score-sum/solutions/1360853/java-backtracking-easy-to-understand/?orderBy=most_votes
class Solution {
    int res = 0;

    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        // 排列组合（回溯法 + 剪枝） + 记忆化搜索
        // improved by - https://leetcode.com/problems/maximum-compatibility-score-sum/solutions/1360853/java-backtracking-easy-to-understand/?orderBy=most_votes
        int n = students[0].length, m = students.length;
        int[][] mem = new int[m][m]; // mem[i][j] means score with student i and mentor j
        for (int i=0; i<m; i++) 
            for (int j=0; j<m; j++) 
                mem[i][j] = score(students[i], mentors[j]);

        backtrack(mem, 0, new boolean[m], 0);
        return res;
    }

    private int score(int[] student, int[] mentor) {
        int m = student.length, res = 0;
        for (int i=0; i<m; i++) 
            if (student[i] == mentor[i]) res++;

        return res;
    }

    private void backtrack(int[][] mem, int nextStudent, boolean[] assignedM, int curSum) {
        int m = mem.length;
        if (nextStudent == m) {
            res = Math.max(res, curSum);
            return;
        }
        for (int nextMentor=0; nextMentor<m; nextMentor++) {
            if (assignedM[nextMentor]) continue;
            assignedM[nextMentor] = true;
            backtrack(mem, nextStudent + 1, assignedM, curSum + mem[nextStudent][nextMentor]);
            assignedM[nextMentor] = false;
        }
    }
}
