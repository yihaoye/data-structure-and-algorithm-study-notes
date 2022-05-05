/**
You are given an array of unique integers salary where salary[i] is the salary of the ith employee.

Return the average salary of employees excluding the minimum and maximum salary. Answers within 10-5 of the actual answer will be accepted.

 

Example 1:

Input: salary = [4000,3000,1000,2000]
Output: 2500.00000
Explanation: Minimum salary and maximum salary are 1000 and 4000 respectively.
Average salary excluding minimum and maximum salary is (2000+3000) / 2 = 2500
Example 2:

Input: salary = [1000,2000,3000]
Output: 2000.00000
Explanation: Minimum salary and maximum salary are 1000 and 3000 respectively.
Average salary excluding minimum and maximum salary is (2000) / 1 = 2000
 

Constraints:

3 <= salary.length <= 100
1000 <= salary[i] <= 106
All the integers of salary are unique.
 */



// Other's Solution:
class Solution {
    public double average(int[] salary) {
        // Time: O(N), Space: O(1)
        // 优化：3*N/2 次比较而非 2*(N-1) 次比较，算法导论 9.1 - https://leetcode-cn.com/problems/average-salary-excluding-the-minimum-and-maximum-salary/solution/tong-shi-zhao-chu-zui-da-zhi-he-zui-xiao-zhi-de-ji/
        int max, min, sum, len = salary.length;
        if ((len & 1) == 1) { // 奇数个元素
            max = min = sum = salary[0];
        } else { // 偶数个元素
            // 从 salary[0] 和 salary[1] 中找出最大最小值
            if (salary[0] > salary[1]) {
                max = salary[0]; min = salary[1];
            } else {
                max = salary[1]; min = salary[0];
            }
            sum = salary[0] + salary[1];
        }
        for (int i = 2 - (len & 1); i < len; i += 2) {
            sum += salary[i] + salary[i+1];
            if (salary[i] > salary[i+1]) {
                if (salary[i] > max) max = salary[i];
                if (salary[i+1] < min) min = salary[i+1];
            } else {
                if (salary[i+1] > max) max = salary[i+1];
                if (salary[i] < min) min = salary[i];
            }
        }
        return (double)(sum - max - min) / (len - 2);
    }
}
