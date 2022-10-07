# Question:
/*
 Write a SQL query to get the second highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+

For example, given the above Employee table, the second highest salary is 200. If there is no second highest salary, then the query should return null.
*/





/* Others' Solution A: */
SELECT max(Salary) as SecondHighestSalary 
FROM Employee
WHERE Salary < (SELECT max(Salary) FROM Employee)
/* Explain:
Using max() will return a NULL if the value doesn't exist. So there is no need to UNION a NULL. Of course, if the second highest value is guaranteed to exist, using LIMIT 1,1 will be the best answer.
*/



/* Others' Solution B: */
select (
  select distinct Salary from Employee order by Salary Desc limit 1 offset 1
)as SecondHighestSalary
/* Explain: 
Change the number after 'offset' gives u n-th highest salary
*/
