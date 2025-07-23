-- CREATE TABLE friendships (
--     user_id INT,
--     friend_id INT
-- );

-- INSERT INTO friendships (user_id, friend_id) VALUES
-- (1, 2), -- User 1 is friend with User 2
-- (2, 1), -- User 2 is friend with User 1 (双向关系)
-- (1, 3),
-- (3, 1),
-- (2, 4),
-- (4, 2),
-- (3, 5),
-- (5, 3),
-- (4, 6),
-- (6, 4),
-- (5, 7),
-- (7, 5);
-- 假设 User 1 和 User 6 之间没有直接或间接关系（通过我们定义的最大深度）

WITH RECURSIVE Friends AS (
    -- 锚成员 (Anchor Member): 递归的起始点，即 User 1 的直接好友
    SELECT
        user_id AS start_user,          -- 原始查询的用户
        friend_id AS current_friend,    -- 当前层级的好友
        1 AS depth,                     -- 关系深度（1 表示直接好友）
        CAST(user_id AS VARCHAR(MAX)) + ' -> ' + CAST(friend_id AS VARCHAR(MAX)) AS path -- 关系路径
    FROM
        friendships
    WHERE
        user_id = 1

    UNION ALL

    -- 递归成员 (Recursive Member): 查找当前好友的好友
    SELECT
        rf.start_user,
        f.friend_id AS current_friend,
        rf.depth + 1 AS depth,
        rf.path + ' -> ' + CAST(f.friend_id AS VARCHAR(MAX)) AS path
    FROM
        friendships f
    JOIN
        Friends rf ON f.user_id = rf.current_friend -- 当前好友的 friend_id 是下一轮的 user_id
    WHERE
        rf.depth < 2                        -- 我们只查找二度好友（即深度最大为 2）
        AND f.friend_id != rf.start_user    -- 避免回到起始用户（可选，取决于具体需求）
        AND f.friend_id NOT IN (            -- 避免循环或重复已访问的用户（重要）
            SELECT current_friend FROM Friends WHERE start_user = rf.start_user
        )
)
SELECT DISTINCT -- 使用 DISTINCT 避免重复路径，或者可以根据需求保留所有路径
    current_friend AS user_id,
    depth,
    path
FROM
    Friends
WHERE
    current_friend != start_user -- 排除起始用户自己
ORDER BY
    depth, user_id;