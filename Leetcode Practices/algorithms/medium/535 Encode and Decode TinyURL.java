/**
Note: This is a companion problem to the System Design problem: Design TinyURL.
TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl and it returns a short URL such as http://tinyurl.com/4e9iAk. Design a class to encode a URL and decode a tiny URL.

There is no restriction on how your encode/decode algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be decoded to the original URL.

Implement the Solution class:

Solution() Initializes the object of the system.
String encode(String longUrl) Returns a tiny URL for the given longUrl.
String decode(String shortUrl) Returns the original long URL for the given shortUrl. It is guaranteed that the given shortUrl was encoded by the same object.
 

Example 1:

Input: url = "https://leetcode.com/problems/design-tinyurl"
Output: "https://leetcode.com/problems/design-tinyurl"

Explanation:
Solution obj = new Solution();
string tiny = obj.encode(url); // returns the encoded tiny url.
string ans = obj.decode(tiny); // returns the original url after deconding it.
 

Constraints:

1 <= url.length <= 104
url is guranteed to be a valid URL.
 */



// Other's Solution:
public class Codec {
    /*
        随机固定长度加密 - https://leetcode.cn/problems/encode-and-decode-tinyurl/solution/tinyurlde-jia-mi-yu-jie-mi-by-leetcode/
        可加密的 URL 数目非常大，几乎是 (10 + 26*2)^6 级别。
        加密 URL 的长度固定是 6，这相比于能加密的字符串数目是极大的缩减优化。
        这个方法的表现非常好，因为几乎不可能产生相同加密结果。
        也可以通过增加加密字符串的长度来增加加密结果的数目。因此，在加密字符串的长度和可加密的字符串数目之间我们需要做一个权衡。
        根据加密 URL 预测加密结果几乎是不可能的，因为使用了随机数。
    */
    String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    HashMap<String, String> map = new HashMap<>();
    Random rand = new Random();
    String key = getRand();

    public String encode(String longUrl) {
        while (map.containsKey(key)) {
            key = getRand();
        }
        map.put(key, longUrl);
        return "http://tinyurl.com/" + key;
    }

    public String decode(String shortUrl) {
        return map.get(shortUrl.replace("http://tinyurl.com/", ""));
    }
    
    private String getRand() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(alphabet.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }
}
// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(url));



// My Solution:
public class Codec {
    Map<String, String> s2l = new HashMap<>();
    Map<String, String> l2s = new HashMap<>();
    Long id = 0L;

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        if (!l2s.containsKey(longUrl)) {
            String shortUrl = idToShortURL(++id);
            s2l.put(shortUrl, longUrl);
            l2s.put(longUrl, shortUrl);
            return shortUrl;
        } else {
            return l2s.get(longUrl);
        }
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return s2l.get(shortUrl);
    }

    private String idToShortURL(long id) {
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
}
// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(url));
