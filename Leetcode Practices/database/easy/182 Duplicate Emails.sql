# Question:
/*
Write a SQL query to find all duplicate emails in a table named Person.

+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+

For example, your query should return the following for the above table:

+---------+
| Email   |
+---------+
| a@b.com |
+---------+

Note: All emails are in lowercase.
*/





# My Solution:
# Write your MySQL query statement below
select distinct P1.Email 
from Person as P1, Person as P2 
where P1.Email = P2.Email and P1.Id <> P2.Id



# Other's Solution:
select Email
from Person
group by Email
having count(*) > 1