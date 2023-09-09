/*
Table: Products

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| product_id  | int     |
| store       | varchar |
| price       | int     |
+-------------+---------+
(product_id, store) is the primary key for this table.
Each row of this table indicates the price of product_id in store.
There will be at most 30 different stores in the table.
price is the price of the product at this store.
 

Important note: This problem targets those who have a good experience with SQL. If you are a beginner, we recommend that you skip it for now.

Implement the procedure PivotProducts to reorganize the Products table so that each row has the id of one product and its price in each store. The price should be null if the product is not sold in a store. The columns of the table should contain each store and they should be sorted in lexicographical order.

The procedure should return the table after reorganizing it.

Return the result table in any order.

The query result format is in the following example.

 

Example 1:

Input: 
Products table:
+------------+----------+-------+
| product_id | store    | price |
+------------+----------+-------+
| 1          | Shop     | 110   |
| 1          | LC_Store | 100   |
| 2          | Nozama   | 200   |
| 2          | Souq     | 190   |
| 3          | Shop     | 1000  |
| 3          | Souq     | 1900  |
+------------+----------+-------+
Output: 
+------------+----------+--------+------+------+
| product_id | LC_Store | Nozama | Shop | Souq |
+------------+----------+--------+------+------+
| 1          | 100      | null   | 110  | null |
| 2          | null     | 200    | null | 190  |
| 3          | null     | null   | 1000 | 1900 |
+------------+----------+--------+------+------+
Explanation: 
We have 4 stores: Shop, LC_Store, Nozama, and Souq. We first order them lexicographically to be: LC_Store, Nozama, Shop, and Souq.
Now, for product 1, the price in LC_Store is 100 and in Shop is 110. For the other two stores, the product is not sold so we set the price as null.
Similarly, product 2 has a price of 200 in Nozama and 190 in Souq. It is not sold in the other two stores.
For product 3, the price is 1000 in Shop and 1900 in Souq. It is not sold in the other two stores.
*/



CREATE PROCEDURE PivotProducts()
BEGIN

-- https://leetcode.com/problems/dynamic-pivoting-of-a-table/solutions/1981948/mysql-using-group-concat/
-- Override GROUP_CONCAT length which has a default limit of 1024 - 设置了 group_concat_max_len 参数的值，将其扩大到 1,000,000，以便能够处理更大的 GROUP_CONCAT 结果。
SET SESSION group_concat_max_len = 1000000;

-- 这个查询用于生成一个动态的 SQL CASE 语句，该语句根据产品的店铺（store）动态生成列，每个店铺的平均价格都会成为一列。GROUP_CONCAT 函数用于将这些动态生成的列名连接在一起，DISTINCT 确保每个店铺只出现一次。
SET @case_stmt = NULL;
SELECT GROUP_CONCAT(DISTINCT CONCAT('AVG(CASE WHEN store = "', store, '" THEN price END) AS ', store))
INTO @case_stmt
FROM products;
 
-- Insert above statement (@case_stmt) in the following main query to frame final query - 这行代码生成了最终的 SQL 查询语句，该语句将产品按照 product_id 进行分组，并在结果中包括了上一步生成的动态列。
SET @sql_query = CONCAT('SELECT product_id, ', @case_stmt, ' FROM products GROUP BY product_id');

PREPARE final_sql_query FROM @sql_query; -- 这里使用 PREPARE 语句准备了最终的 SQL 查询语句，将其存储在一个名为 final_sql_query 的预处理语句中。PREPARE 的好处：SQL 注入防护、性能优化、可读性和维护性、可执行性
EXECUTE final_sql_query; -- 使用 EXECUTE 语句执行了预处理的 SQL 查询语句，从数据库中检索数据并按照店铺的平均价格旋转成列。
DEALLOCATE PREPARE final_sql_query; -- 通过 DEALLOCATE 释放了预处理的 SQL 查询语句，以释放相关资源。

END
