# 物理相关
  
## 索引
在关系数据库中，如果有上万甚至上亿条记录，在查找记录的时候，想要获得非常快的速度，就需要使用索引（Index）。  
索引是关系数据库中对某一列或多个列的值进行预排序的数据结构。通过使用索引，可以让数据库系统不必扫描整个表，而是直接定位到符合条件的记录，这样就大大加快了查询速度。  
索引问题就是一个查找问题。数据库索引，是数据库管理系统中一个排序的数据结构，以协助快速查询、更新数据库表中数据。索引的实现通常使用 B 树及其变种 B+ 树。在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据结构以某种方式引用（指向）数据，这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。为表设置索引要付出代价的：一是增加了数据库的存储空间，二是在插入、删除和修改数据时要花费较多的时间（因为索引也要随之变动）。  
  
### 索引实现
* B-Tree 索引 O(logN)：具体比如 B+ 树，可以参考 `MySQL 索引背后的数据结构及算法原理`
* Hash 索引：1. 仅仅能满足 "="，"IN" 和 "<=>" 查询，不能使用范围查询；2. 其检索效率非常高，索引的检索可以一次定位，不像 B-Tree 索引需要从根节点到枝节点，最后才能访问到页节点这样多次的 IO 访问，所以 Hash 索引的查询效率要远高于 B-Tree 索引；3. 只有 Memory 存储引擎显示支持 Hash 索引
* Full-Text 索引：现在 MyISAM 和 InnoDB 引擎都支持
* 位图索引：Bitmap Index
* 空间索引：具体比如 R-Tree 索引用于对 GIS 数据类型创建 SPATIAL 索引  
  
B-Tree 索引应该是 MySQL 里最广泛应用的索引实现。  
MySQL 默认存储引擎 InnoDB 默认使用 B-Tree（实际是 B+ Tree）索引。Innodb 存储引擎会监控对表上二级索引（即非主键索引）的查找，如果发现某二级索引被频繁访问，二级索引成为热数据，建立哈希索引可以带来速度的提升，经常访问的二级索引数据会自动被生成到 Hash 索引里面去（最近连续被访问三次的数据），自适应 Hash 索引通过缓冲池的 B+ 树构造而来，因此建立的速度很快。  
  
### 索引类型
* 主键索引：数据库表经常有一列或列组合，其值唯一标识表中的每一行。该列称为表的主键。在数据库关系图中为表定义主键将自动创建主键索引，主键索引是唯一索引的特定类型。该索引要求主键中的每个值都唯一。当在查询中使用主键索引时，它还允许对数据的快速访问。
* 唯一索引：索引列的值必须唯一，但允许有空值。
* 非唯一索引：索引列的值不是必须唯一。
* 普通索引：即一个索引只包含单个列，一个表可以有多个单列索引。
* 复合索引：即一个索引包含多个列。
* 聚集索引（物理）：并不是一种单独的索引类型，而是一种数据存储方式。表中行的物理顺序与键值的逻辑（索引）顺序相同。一个表只能包含一个聚集索引。与非聚集索引相比，聚集索引通常提供更快的数据访问速度。
* 非聚集索引（物理）：不是聚集索引的索引，表中行的物理顺序与键值的逻辑顺序不匹配。  
  
### 存储引擎
主要引擎：  
* MyISAM：不支持事务型查询，在 OLTP 类型业务场景中不建议使用。
* InnoDB：支持事务型查询，支持行级锁，对并发业务支持较好。  
  
更多：  
![](./storage%20engines.jpeg)  
  
## 建议阅读
https://blog.csdn.net/dong19891210/article/details/106969644  
  
# SQL 语法（进阶相关）
  
## SQL GROUP BY 语句
GROUP BY 作用的[直观图示](./README.md#在-select-中嵌套)  
GROUP BY 语句可结合一些聚合函数来使用  
  
GROUP BY 语句  
GROUP BY 语句用于结合聚合函数（包括：AVG、COUNT、MIN、MAX 和 SUM），根据一个或多个列对结果集进行分组。  
按列名分组，搭配聚合函数十分实用。  
例，统计每个班的人数:  
```SELECT student_class,COUNT(ALL student_name) AS 总人数 FROM t_student GROUP BY (student_class);```  
AS为定义别名，别名的使用在组合及联接查询时会有很好的效果。 
分组中也可以加入筛选条件WHERE，不过这里一定要注意的是，执行顺序为：WHERE过滤→分组→聚合函数。  
  
SQL GROUP BY 语法  
```sql
SELECT column_name, aggregate_function(column_name)
FROM table_name
WHERE column_name operator value
GROUP BY column_name;
```  
  
## SQL HAVING 子句
在 SQL 中增加 HAVING 子句原因是，WHERE 关键字无法与聚合函数一起使用。  
HAVING 子句可以让我们筛选分组后的各组数据。  
  
SQL HAVING 语法  
```sql
SELECT column_name, aggregate_function(column_name)
FROM table_name
WHERE column_name operator value
GROUP BY column_name
HAVING aggregate_function(column_name) operator value;
```  
  
## 子查询
为什么要子查询？  
现有一数据表如下：  
![](./270010117894289.png)  
根据之前的知识我们可以查出每门科目的最高分，但是要想查出取得最高分的学生信息就做不到了。这时就需要用到子查询来取得完整的信息。  
  
什么是子查询？子查询就是嵌套在主查询中的查询。  
子查询可以嵌套在主查询中所有位置，包括 SELECT、FROM、WHERE、GROUP BY、HAVING、ORDER BY。  
但并不是每个位置嵌套子查询都是有意义并实用的。  
  
### 在 SELECT 中嵌套
现有表两张：一张学生表、一张班表。id 相关联  
![](./281716293203159.png)  
![](./281719351646668.png)  
  
学生信息和班级名称位于不同的表中，要在同一张表中查出学生的学号、姓名、班级名称：  
```sql
SELECT s.student_id,s.student_name,(SELECT class_name FROM t_class c WHERE c.class_id=s.class_id) FROM t_student s GROUP BY s.student_id;
```  
子查询中查出的是，班级表中的班级 id 与学生表中的班级 id 相同的行，注意 `WHERE c.class_id=s.class_id` 这里就是别名用法的一个很好的体现，区分开了两张表中同样列名的列。
结果：  
![](./281742028675199.png)  
最后的 GROUP BY 可以理解为对重复行的去重，如果不加：  
![](./281743487892192.png)  
  
### 在 WHERE 中嵌套
查出C语言成绩最高的学生的信息：  
```sql
SELECT * FROM t_student WHERE student_subject='C语言' AND student_score>=ALL (SELECT student_score FROM t_student WHERE student_subject='C语言');
```  
查出每门课最高成绩的学生的信息：  
```sql
SELECT * FROM t_student s1 WHERE s1.student_score >= ALL(SELECT s2.student_score FROM t_student s2 WHERE s1.`student_subject`=s2.student_subject);
```  
这里别名的用法情况就是在子查询或联接查询时，主查询及子查询均为对同一张表进行操作，为主、子查询中的表加上不同的别名能够很好的区分哪些列的操作是在主查询中进行的，哪些列的操作是在子查询中进行的，即主、子查询对同一张表操作，区分开位于内外表中相同的列名。  
  
### 子查询运算符分类
* ALL 运算符 - 和子查询的结果逐一比较，必须全部满足时表达式的值才为真。
* ANY 运算符 - 和子查询的结果逐一比较，其中一条记录满足条件则表达式的值就为真。
* EXISTS/NOT EXISTS 运算符 - EXISTS 判断子查询是否存在数据，如果存在则表达式为真，反之为假。NOT EXISTS 相反。  
  
在子查询或相关查询中，要求出某个列的最大值，通常都是用 ALL 来比较，大意为比其他行都要大的值即为最大值。  
  
### 子查询的分类
* 相关子查询
    * 执行依赖于外部查询的数据。
    * 外部查询返回一行，子查询就执行一次。
* 非相关子查询
    * 独立于外部查询的子查询。
    * 子查询总共执行一次，执行完毕后后将值传递给外部查询。  
  
相关子查询不推荐使用，因为相关子查询主查询执行一回，子查询就执行一回，十分耗费时间，尤其是当数据多的时候（前面第一个例子 SELECT 嵌套求学生对应班级名的即为相关子查询）。  
  
## 组合查询
UNION 操作符用于合并两个或多个 SELECT 语句的结果集。  
请注意，UNION 内部的每个 SELECT 语句必须拥有相同数量的列。列也必须拥有相似的数据类型。同时，每个 SELECT 语句中的列的顺序必须相同。  
![](./union1.png)
![](./union2.jpg)  
  
通过 UNION 运算符来将两张表纵向联接，基本方式为：  
```
SELECT 列1 , 列2 FROM 表1
UNION
SELECT 列3 , 列4 FROM 表2;
```  
UNION ALL 为保留重复行：  
```
SELECT 列1 , 列2 FROM 表1
UNION ALL
SELECT 列3 , 列4 FROM 表2;
```  
组合查询并不是太实用，用的少之又少。  
  
## 表联接与联接查询
相关子查询效率低下，那怎么能将不同表的信息一起查询出来呢？这就需要用到表联接。  
和之前的 UNION 组合查询不同，UNION 是将不同的表组合起来，也就是纵向联接，说白了就是竖着拼起来。  
而表联接是通过笛卡尔乘积将表进行横向联接，所谓的通过笛卡尔乘积简单说就是两表的行依次相联再相加。  
  
Union VS Join:  
![](./SQL-UNION-vs-JOIN.png)  
  
通过外键的匹配就得到了一张完美的联接之后的表，它可以看做一张新表，想要任何数据均可以从此表中查询，这就是表联接的强大之处。  
  
### 表联接的分类
现在有如下两张表：  
![](./281926560709208.png)  
![](./281926198981725.png)  
内联接：  
内联接是指两个表中某一行相关的列值匹配时，这一行才会出现在表中。比如 t_student._fk 与 t_class._infor 相同时才会出行该行，其他的行剔除。  
```sql
SELECT * FROM t_student INNER JOIN t_class ON t_class._infor = t_student._fk;
```
语法为 INNER JOIN 其中 INNER 可以省略。  
内联接的简写：  
```sql
SELECT * FROM t_student s,t_class c WHERE c._infor = s._fk 
```
此写法也是用的最多的。  
  
外联接：  
分为左外联接与右处联接。  
外联接是指不管有没有匹配，被定义了外联接的表数据都要出现在结果中。比如左外联接，那么在 JOIN 左边的表就被定义为外联接，那么此表中所有数据都会出现在查询结果中。  
注意班级表中的四班是没有学生的，所以在内联接之后理所当然的被剔除了。现在以外联接做示例：  
```sql
SELECT * FROM t_student s RIGHT JOIN t_class c ON s._fk=c._infor;
```
上面 SQL 中表 t_class 在写在 JOIN 的右边，所以用 RIGHT JOIN 来进行外联接。  
![](./281954257733735.png)  
这就是外联接的用法，通常用在想要的数据匹配不上时。  
  
自联接：  
自联接属于内联接或外联接的一种特例，自联接所联接的表均是来自同一张。  
![](./282008167895581.png)  
上表中，6个人均属于某公司的员工。区别是李四为张三和王五的领导，张八为赵六和孙七的领导。leader_id与work_id相关联。  
现在可以通过自联接巧妙的将一张表分为员工部分和领导部分：  
```sql
SELECT w.work_name,l.work_name 领导姓名 FROM t_emp w,t_emp l WHERE w.leader_id=l.work_id;
```  
结果：  
![](./282011580541647.png)  
  
  
## SQL 的执行顺序
第一步：执行 FROM  
第二步：WHERE 条件过滤  
第三步：GROUP BY 分组  
第四步：执行 SELECT 投影列  
第五步：HAVING 条件过滤  
第六步：执行 ORDER BY 排序  
  
  
参考链接：  
https://www.cnblogs.com/ghost-xyx/p/3811036.html  
https://www.cnblogs.com/ghost-xyx/p/3813688.html  
  
  
# 优化
根据现网环境优化执行的难易度，在优化顺序可以按照：SQL 语句 -> 数据库表设计（比如范式、关系映射、索引、分表、数据类型等等）-> 数据库参数配置 -> 数据库存储引擎 -> 服务器硬件。通过编写高效的 SQL 语句，并以合适的方式创建表和索引，使系统始终保持良好的性能。  
务必学好树，特别是 B+ Tree，就会明白索引何时生效或失效。  
  
