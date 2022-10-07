# Question:
/*
Given a Weather table, write a SQL query to find all dates' Ids with higher temperature compared to its previous (yesterday's) dates.

+---------+------------+------------------+
| Id(INT) | Date(DATE) | Temperature(INT) |
+---------+------------+------------------+
|       1 | 2015-01-01 |               10 |
|       2 | 2015-01-02 |               25 |
|       3 | 2015-01-03 |               20 |
|       4 | 2015-01-04 |               30 |
+---------+------------+------------------+

For example, return the following Ids for the above Weather table:

+----+
| Id |
+----+
|  2 |
|  4 |
+----+
*/





/* Other's Solution: */
select W1.Id 
from Weather W1, Weather W2 
where W1.Temperature > W2.Temperature and TO_DAYS(W1.Date)-TO_DAYS(W2.Date)=1



/* My Solution */
SELECT w2.id FROM
Weather w1 INNER JOIN Weather w2 ON DATEDIFF(w1.recordDate, w2.recordDate) = -1 AND w1.temperature < w2.temperature;
