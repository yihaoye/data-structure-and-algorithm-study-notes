/*
Table: Customers

+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| customer_name | varchar |
+---------------+---------+
customer_id is the primary key for this table.
Each row of this table contains the name and the id customer.
 

Write an SQL query to find the missing customer IDs. The missing IDs are ones that are not in the Customers table but are in the range between 1 and the maximum customer_id present in the table.

Notice that the maximum customer_id will not exceed 100.

Return the result table ordered by ids in ascending order.

The query result format is in the following example.

 

Example 1:

Input: 
Customers table:
+-------------+---------------+
| customer_id | customer_name |
+-------------+---------------+
| 1           | Alice         |
| 4           | Bob           |
| 5           | Charlie       |
+-------------+---------------+
Output: 
+-----+
| ids |
+-----+
| 2   |
| 3   |
+-----+
Explanation: 
The maximum customer_id present in the table is 5, so in the range [1,5], IDs 2 and 3 are missing from the table.
*/



/* My Solution */
WITH RECURSIVE Nums AS (
    SELECT 1 AS num
    UNION ALL
    SELECT num + 1 FROM Nums WHERE num + 1 <= 100
)
SELECT a.num AS ids
FROM 
    (SELECT * 
    FROM Customers c RIGHT JOIN Nums n
    ON c.customer_id = n.num) AS a
WHERE a.customer_id IS NULL 
AND a.num < (SELECT customer_id FROM Customers ORDER BY customer_id DESC LIMIT 1)
ORDER BY a.num;
