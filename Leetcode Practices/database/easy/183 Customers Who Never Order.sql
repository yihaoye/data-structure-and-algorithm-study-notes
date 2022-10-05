# Question: 
/*
Suppose that a website contains two tables, the Customers table and the Orders table. Write a SQL query to find all customers who never order anything.

Table: Customers.

+----+-------+
| Id | Name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+

Table: Orders.

+----+------------+
| Id | CustomerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+

Using the above tables as example, return the following:

+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+
*/



/* Other's Solution */
SELECT Name as Customers 
FROM Customers 
WHERE Customers.Id NOT IN (SELECT b.CustomerId FROM Orders b)



/* My Solution */
SELECT a.name AS Customers FROM
    (SELECT c.id, c.name, o.id AS order_id FROM Customers c LEFT JOIN Orders o ON c.id = o.customerId) AS a
WHERE a.order_id IS NULL;
