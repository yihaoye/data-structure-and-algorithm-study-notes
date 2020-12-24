https://leetcode.com/discuss/interview-question/system-design/625918/Amazon-or-System-Design-or-Design-a-real-time-gaming-ranking-system  
  
Q: Design a real time ranking system for a gaming company , count of entities can be multimillion.  
  
A: https://aws.amazon.com/cn/blogs/database/building-a-real-time-gaming-leaderboard-with-amazon-elasticache-for-redis/  
  
Gaming leaderboards enable players to gauge their performance against each other. This important social feature increases players’ engagement and encourages competition. Leaderboard data can also inform in-game algorithms that match players against competitors of similar skill levels.

In this post, I explore the challenges around building and scaling gaming leaderboards using traditional relational databases. I also examine how to leverage modern in-memory data stores such as Redis to provide a highly efficient and scalable solution.

This proposed solution pushes leaderboard storage and querying away from the relational database toward the more versatile Amazon ElastiCache for Redis. The approach outlined here applies not only to gaming leaderboards, but generally to any situation for generating ranking within an application.

Background
Using traditional relational databases, the steps to build a basic leaderboard are straightforward. Typically, these include:

Create a table.
Insert or update scores as they change.
Query the table to retrieve the ranking by score in descending order.
Here’s a basic relational database leaderboard implementation:

+---------+---------+------+-----+---------+-------+
| Field   | Type    | Null | Key | Default | Extra |
+---------+---------+------+-----+---------+-------+
| user_id | int(11) | NO   | MUL | NULL    |       |
| score   | int(11) | NO   | MUL | NULL    |       |
+---------+---------+------+-----+---------+-------+
This table exemplifies the most basic leaderboard schema. Some implementations would add more information, such as a game_id value to reference a different game, or a timestamp to break a tied score. But the underlying concept of how to query and update the leaderboard remains the same.

Here’s the create table script to execute a basic leaderboard:

CREATE TABLE `leaderboard` (
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  KEY `idx_score` (`score`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `leaderboard_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
In this instance, I’ve added indexes on user_id as well as score for faster joins and ordering. Scores are incremented or decremented using atomic SQL statements such as the following:

update leaderboard set score=score+1 where user_id=<user_id>;
As long as the number of concurrent game users remains low to moderate, this design provides sufficient leaderboard functionality. But as concurrent game access increases, this schema becomes difficult to scale:

Rank calculations grow computationally expensive for larger tables.
Specific player rankings require computation of all relative leaderboard positions.
Leaderboard batch mode generation and result caching reduce recurring computation impact but affect overall user experience.
Rank computation for specific players can be expensive, especially for larger tables. For instance, consider the following query:

SELECT *,(SELECT COUNT(*) FROM leaderboard l2 WHERE l2.score>=l1.score) RANK FROM leaderboard l1 WHERE l1.user_id=<user_id>;
Given the nested query to compute the user’s ranking, the time complexity is quadratic. It takes an average of 35 seconds to run the query for a table with 50 million records even with indexes. Because the scores and rankings are constantly changing, the results can’t be easily cached.

An ideal solution provides predictable performance even as the number of users increases. Also, such a solution allows easy access to common leaderboard-related operations (for example, retrieving lowest scores or retrieving similar players by score), without having to resort to slow and complex queries.

Redis offers one highly efficient and scalable solution. Redis is an in-memory data store that supports basic key-value functionality. It also supports a variety of data structures such as hashes, lists, sets, sorted sets, range queries, and geospatial indexes. This suggests the potential advantages of storage and querying through ElastiCache for Redis instead of through relational databases.

The ElastiCache for Redis approach has the following benefits:

Frees up database resources for other types of requests.
Delivers high request rates suitable for data that’s not easily cached.
Supports a data structure optimized to handle leaderboard use cases.
Introduction to ElastiCache
ElastiCache is a fully managed, low latency, in-memory data store that supports either Memcached or Redis. With ElastiCache, management tasks such as provisioning, setup, patching, configuration, monitoring, backup, and failure recovery are taken care of, so you can focus on application development.

Specific to Redis, ElastiCache lets you “scale in” or “scale out” both reads and writes. Cluster mode offers added shard support, enabling write scaling. It supports up to 250 shards, giving you up to 170.6 TB of in-memory data. Online cluster resizing enables zero downtime scaling in or out of shards. You can also automate scaling in response to Amazon CloudWatch alarms. To scale reads, you only need to add more read replicas.

Introduction to sorted sets
Sorted sets contain a list of members with associated scores. Although set members are unique, score values are permitted to repeat. Scores are used to rank the list in ascending order.

One key difference between sorted sets and relational databases is when the tool sorts the list. During insert or update operations, sorted sets automatically position the item in the right order. Because of this pre-sorting, queries run significantly faster. You can quickly and efficiently query the middle of the list or get the rank of a specific member. Sorted sets finds specific rankings logarithmically, proportional to the number of members.

By contrast, relational databases order items during querying, adding computational burden to the database. Obtaining the rank of a specific player involves quadratic time complexity.

Here are some key commands that you can run with sorted sets to build leaderboards:

ZADD—Add a new member to the sorted set or update the score of a member already in the list.
ZRANGE—Obtain a range of members sorted in ascending order and filtered by using rank range (zero indexed). For reverse ordering, use ZREVRANGE.
ZRANK—Obtain the rank (zero indexed) of the member. This rank is in ascending order. For descending order, use ZREVRANK.
ZRANGEBYSCORE—Like ZRANGE, but filters the score. For reverse ordering, use ZREVRANGEBYSCORE.
ZSCORE—Retrieve the score of a member.
ZREM—Remove a member from the leaderboard.
Any sorted set command that contains the word “rev” means reverse or descending order.

Putting it all together
To demonstrate how these features interact, I’ve created the following demo application. The high-level architecture is shown in the following diagram.
![](https://d2908q01vomqb2.cloudfront.net/887309d048beef83ad3eabf2a79a64a389ab1c9f/2019/06/20/A-1.jpg)  


To test how all these elements work together, launch the AWS CloudFormation stack. The stack creates the following resources:

AWS Lambda functions
Amazon API Gateway API actions
Parameters
Lambda functions
The following table lists the functions created by the AWS CloudFormation stack for the demo application.

Function	Description
LeaderboardRetrievePlayerInfo	Retrieves the player’s rank and score from Redis.
LeaderboardUpsertScore	Updates or inserts the score of an existing player in Redis.
LeaderboardSearchUser	Searches against the users table in the MySQL database.
LeaderboardRetrieveTop10	Retrieves the top 10 users from Redis and merges the result with the user details from the MySQL database.
LeaderboardLoadFrontend	Used by the AWS CloudFormation template as a custom resource to properly initialize the S3 bucket used to host the frontend.
API Gateway API actions
The AWS CloudFormation template also creates APIs that act as proxy to the Lambda functions. The structure of the APIs is as follows:

/leaderboard
  /player-info
    GET
  /top10
    GET
/users
  /score
    POST
  /search
    GET
Parameters
The following table lists the parameters created by the AWS CloudFormation stack for the demo application.

Path	Usage
/leaderboard/player-info	Pass the user_id parameter through the query string to indicate the user for which to retrieve rank and score information.
/users/score	
Pass the following JSON in the request body:

{score: "<score>", user_id: "<user_id>"}

/users/search	Pass the username parameter through the query string to search for existing users. The username can be incomplete to do a wildcard search.
Redis and MySQL
I’ve preloaded both databases with 1 million users/scores. For MySQL, the database has two tables: users and leaderboard. For Redis, the sorted set key is leaderboard. Data for both MySQL and Redis are similar, so you can quickly do a comparison of the query response times.

Accessing the frontend and backend
After AWS CloudFormation finishes building the stack, you can find two URLs in the Output tab of your stack. The following screenshot shows an example.



The APIGatewayInvokeURL is the URL for the REST API backend. To see the actual payloads that return, make API calls directly to this endpoint. For more information, see the previous API Gateway section.

The FrontendURL can be directly opened in the browser to see the demo application. The following screenshot shows an example.



You can search for user names, see their current rank and score, and change their current score. Updating the score refreshes the Top 10 list.

Interacting with Redis
Before the demo application can send commands to the Redis instance, you must complete the following tasks:

Install the node_redis
Initialize the client object.
Install the node_redis library
Because the demo application uses Node.js, install the library by running the following command:

npm install --save redis

Initialize the client object
After the library is installed, you can connect to the Redis instance by creating a client object with the following commands:

var redis = require("redis");

var redisClient = redis.createClient(<port>, <endpoint url>);

Common leaderboard operations via Redis commands
The following are common leaderboard operations and their corresponding Redis commands:

Insert/update user score
To insert or update player score, use the following command:

redisClient.zadd([key, score, userId], callback);

Retrieve user score
The following command retrieves the score for the given user ID. I used user_id as the member identifier when I inserted the member with their score using the zadd command:

redisClient.zscore([key, userId], callback);

Retrieve list of users
There are two commands to retrieve the range of users: zrange and zrevrange. For the demo application, use zrevrange because the players with the highest score are higher up in the ranking:

redisClient.zrevrange([key, min, max, “WITHSCORES”], callback);

Sorted set bases min or max parameters on a zero-indexed array, where min of 0 would be rank #1. The parameter “WITHSCORES” means that the result includes the scores of the respective players. This parameter affects the resulting array, for example:[<user1>, <score>, <user2>, <score>, <user3>, <score>…]. Without the “WITHSCORES” parameter, it would just be an array of users.

Retrieve user rank
As with retrieving the list of users, there are two commands to retrieve the rank of a user: zrank and zrevrank. For this demo application, use the zrevrank because the players with the highest score have a higher rank:

redisClient.zrevrank([key, userId], callback);

Scaling
ElastiCache supports online cluster resizing, which enables zero downtime scaling in or out of shards. Resizing can be done in response to a CloudWatch alarm to accommodate peak and non-peak traffic.

The API that does the resizing is ModifyReplicationGroupShardConfiguration, which can add additional shards, remove shards, or rebalance the key spaces among existing shards.

Conclusion
Using the right data storage for the right use cases and accounting for data access patterns can reap not only significant performance gains, but also can provide a cost-efficient solution.

Aside from sorted sets, Redis provides other data structures that are useful in the context of the gaming industry. The Redis documentation also provides the time complexity for each command, so you have an idea how the performance scales as your users increase.