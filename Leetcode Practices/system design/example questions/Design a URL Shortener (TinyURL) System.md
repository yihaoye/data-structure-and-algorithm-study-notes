**Problem:** Design a service like TinyURL, a URL shortening service, a web service that provides short aliases for redirection of long URLs.  
  
**Solution:** If you don't know about TinyURL, just check it. Basically we need a one to one mapping to get shorten URL which can retrieve original URL later. This will involve saving such data into database.  
We should check the following things:  
  
What's the traffic volume / length of the shortened URL?  
What's the mapping function?  
Single machine or multiple machines?  
**Traffic:** Let's assume we want to serve more than 1000 billion URLs. If we can use 62 characters [A-Z, a-z, 0-9] for the short URLs having length n, then we can have total 62^n URLs. So, we should keep our URLs as short as possible given that it should fulfill the requirement. For our requirement, we should use n=7 i.e the length of short URLs will be 7 and we can serve 62^7 ~= 3500 billion URLs.  
  
**Basic solution:**  
To make things easier, we can assume the alias is something like `http://tinyurl.com/<alias_hash>` and alias_hash is a fixed length string.  
To begin with, let’s store all the mappings in a single database. A straightforward approach is using alias_hash as the ID of each mapping, which can be generated as a random string of length 7.  
  
Therefore, we can first just store `<ID, URL>`. When a user inputs a long URL `“http://www.google.com”`, the system creates a random 7-character string like `“abcd123”` as ID and inserts entry `<“abcd123”, “http://www.google.com”>` into the database.  
  
In the run time, when someone visits http://tinyurl.com/abcd123, we look up by ID `“abcd123”` and redirect to the corresponding URL `“http://www.google.com”`.  
  
**Problem with this solution:**  
We can't generate unique hash values for the given long URL. In hashing, there may be collisions (2 long urls map to same short url) and we need a unique short url for every long url so that we can access long url back but hash is one way function.  
  
**Better Solution:**  
  
One of the most simple but also effective one, is to have a database table set up this way:  
```
Table Tiny_Url(  
  ID : int PRIMARY_KEY AUTO_INC,  
  Original_url : varchar,  
  Short_url : varchar  
)
```  
Then the auto-incremental primary key ID is used to do the conversion: `(ID, 10)` <==> `(short_url, BASE)`. Whenever you insert a new original_url, the query can return the new inserted ID, and use it to derive the short_url, save this short_url and send it to cilent. (这里是数据库实时创建新的 ID，系统还可以提前创建一批 ID 与对应的 short_url 对来提升性能，创建好后，把这些提前准备的 `ID-short_url Pair` 放入消息队列中，可供高并发场景下安全不冲突地消费 -- 拿到 `ID-short_url Pair` 后与 `Original_url` 一起写入数据库中，数据库可采用 NoSQL，因为其缓存性能比 SQL 更好且可以保证数据可持久化以及主键 ID 索引等功能)  
  
**Code for methods** (that are used to convert ID to short_url and short_url to ID):  
```java
String idToShortURL(long id) {
    // Map to store 62 possible characters
    String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    char[] charMap = validChars.toCharArray();

    StringBuilder shorturl = new StringBuilder();

    // Convert given integer id to a base 62 number
    while (id > 0L) {
        shorturl.append(charMap[(int) (id % 62)]);
        id = id / 62;
    }

    // Reverse shortURL to complete base conversion
    shorturl.reverse();

    return shorturl.toString();
}

// Function to get integer ID back from a short url
long shortURLtoID(String shortURL) {
    long id = 0L; // initialize result

    // A simple base conversion logic
    for (int i=0; i < shortURL.length(); i++) {
        char c = shortURL.charAt(i);
        if ('a' <= c && c <= 'z') id = id*62 + c - 'a';
        if ('A' <= c && c <= 'Z') id = id*62 + c - 'A' + 26;
        if ('0' <= c && c <= '9') id = id*62 + c - '0' + 52;
    }
    return id;
}
```
  
**Multiple machines:**  
  
If we are dealing with massive data of our service, distributed storage can increase our capacity. The idea is simple, get a hash code from original URL and go to corresponding machine then use the same process as a single machine. For routing to the correct node in cluster, Consistent Hashing is commonly used.  
  
Following is the pseudo code for example,  
  
**Get shortened URL**  
  
* hash original URL string to 2 digits as hashed value hash_val
* use hash_val to locate machine on the ring
* insert original URL into the database and use getShortURL function to get shortened URL short_url
* Combine hash_val and short_url as our final_short_url (length=8) and return to the user  
  
**Retrieve original from short URL**  
  
* get first two chars in final_short_url as hash_val
* use hash_val to locate the machine
* find the row in the table by rest of 6 chars in final_short_url as short_url
* return original_url to the user  
  
**Other factors:**  
  
* 快速检查 URL 是否已有、已处理过 - 用布隆过滤器（用于高效检索一个元素是否在一个集合中）。如果这个 URL 已被移除（比如长久未用），则可以 Count–Min Sketch 算法替代布隆过滤器或使用变种 Counting Bloom Filter，解决传统布隆过滤器不能删除记录的缺陷。
* Design Distributed ID Generator（[雪花算法](https://zh.wikipedia.org/wiki/%E9%9B%AA%E8%8A%B1%E7%AE%97%E6%B3%95)）
* One thing I’d like to further discuss here is that by using GUID (Globally Unique Identifier) as the entry ID, what would be pros/cons versus incremental ID in this problem? If you dig into the insert/query process, you will notice that using random string as IDs may sacrifice performance a little bit. More specifically, when you already have millions of records, insertion can be costly. Since IDs are not sequential, so every time a new record is inserted, the database needs to go look at the correct page for this ID. However, when using incremental IDs, insertion can be much easier – just go to the last page.  
  
```
You can connect with original author here: https://www.linkedin.com/in/shashi-bhushan-kumar-709a05b5/
```  
References:  
http://blog.gainlo.co/index.php/2016/03/08/system-design-interview-question-create-tinyurl-system/  
https://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/  