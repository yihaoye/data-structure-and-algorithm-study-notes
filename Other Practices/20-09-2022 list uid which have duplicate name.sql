-- tiktok
SELECT uid, COUNT(name)
FROM user
GROUP BY name
HAVING COUNT(name) > 1;
