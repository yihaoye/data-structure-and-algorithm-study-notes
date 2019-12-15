/* This is tagged as easy in leetcode but actually it is medium or even harder, so put it in medium dir here.
Given a start IP address ip and a number of ips we need to cover n, return a representation of the range as a list (of smallest possible length) of CIDR blocks.

A CIDR block is a string consisting of an IP, followed by a slash, and then the prefix length. For example: "123.45.67.89/20". That prefix length "20" represents the number of common prefix bits in the specified range.

Example 1:
Input: ip = "255.0.0.7", n = 10
Output: ["255.0.0.7/32","255.0.0.8/29","255.0.0.16/32"]
Explanation:
The initial ip address, when converted to binary, looks like this (spaces added for clarity):
255.0.0.7 -> 11111111 00000000 00000000 00000111
The address "255.0.0.7/32" specifies all addresses with a common prefix of 32 bits to the given address,
ie. just this one address.

The address "255.0.0.8/29" specifies all addresses with a common prefix of 29 bits to the given address:
255.0.0.8 -> 11111111 00000000 00000000 00001000
Addresses with common prefix of 29 bits are:
11111111 00000000 00000000 00001000
11111111 00000000 00000000 00001001
11111111 00000000 00000000 00001010
11111111 00000000 00000000 00001011
11111111 00000000 00000000 00001100
11111111 00000000 00000000 00001101
11111111 00000000 00000000 00001110
11111111 00000000 00000000 00001111

The address "255.0.0.16/32" specifies all addresses with a common prefix of 32 bits to the given address,
ie. just 11111111 00000000 00000000 00010000.

In total, the answer specifies the range of 10 ips starting with the address 255.0.0.7 .

There were other representations, such as:
["255.0.0.7/32","255.0.0.8/30", "255.0.0.12/30", "255.0.0.16/32"],
but our answer was the shortest possible.

Also note that a representation beginning with say, "255.0.0.7/30" would be incorrect,
because it includes addresses like 255.0.0.4 = 11111111 00000000 00000000 00000100 
that are outside the specified range.
Note:
1. ip will be a valid IPv4 address.
2. Every implied address ip + x (for x < n) will be a valid IPv4 address.
3. n will be an integer in the range [1, 1000].
*/



// Other's Solution 1:
class Solution {
    public List<String> ipToCIDR(String ip, int n) {
        long x = 0;
        String[] ips = ip.split("\\.");
        for (int i = 0; i < ips.length; ++i) { // transfer binary represented ip "xxxx.xxxx.xxxx.xxxx" (String) to long data type representation
            x = Integer.parseInt(ips[i]) + x * 256;
        }

        List<String> res = new ArrayList<>();
        while (n > 0) {
            /*
                (-x) is the two’s complement of x. (-x) will be equal to one’s complement of x plus 1.
                x & (-x) can mean for x (e.g. 4278190087 which is long data type representation of 255.0.0.7), how many more ips we can represent in CIDR. In this case it's only 1.(because x & (-x) of 7 is 1)
                for example, 255.0.0.8, x & (-x) of it is 00001000, it's 8, we can represent 8 more ips which start from it.
                for 255.0.0.9, x & (-x) of it is 00000001, 1 again.
            */
            long lowestIndexCount = x & -x; 

            while (lowestIndexCount > n) lowestIndexCount /= 2;

            // But the specification of CIDR asks us to use a mask instead of count. So we transfer it to mask, it's the function "longToCIDR" has done.
            res.add(longToCIDR(x, (int)lowestIndexCount));
            x += lowestIndexCount; // Get next available/startable base IP whose CIDR will never conflict/overlap existing added CIDR or less/incompatible than given initial IP.
            n -= lowestIndexCount;
        }

        return res;
    }

    String longToCIDR(long x, int lowestIndexCount) {
        int[] res = new int[4];
        res[0] = (int) (x & 255); x >>= 8;
        res[1] = (int) (x & 255); x >>= 8;
        res[2] = (int) (x & 255); x >>= 8;
        res[3] = (int) x;
        int len = 33;
        while (lowestIndexCount > 0) {
            len --;
            lowestIndexCount /= 2;
        }
        return res[3] + "." + res[2] + "." + res[1] + "." + res[0] + "/" + len;
    }
}
/*
reference:
https://leetcode.com/problems/ip-to-cidr/discuss/110222/Very-Simple-Java-Solution-(beat-100)
https://leetcode.com/problems/ip-to-cidr/discuss/228131/Java-Solution-with-Explanation
https://zhuanlan.zhihu.com/p/35541808
https://leetcode.com/problems/ip-to-cidr/discuss/151348/Java-Solution-with-Very-Detailed-Explanation-8ms
https://www.cnblogs.com/grandyang/p/8440087.html

https://stackoverflow.com/questions/2604296/twos-complement-why-the-name-two
https://stackoverflow.com/questions/10178980/how-to-convert-a-binary-string-to-a-base-10-integer-in-java
https://zh.wikipedia.org/wiki/%E4%BA%8C%E8%A3%9C%E6%95%B8
*/



// Other's Solution 2:
class Solution {
    public List<String> ipToCIDR(String ip, int n) {
      long x = 0;
      String[] parts = ip.split("\\."); // we need \\ here because '.' is a keyword in regex.
      for(int i = 0; i < 4; i++) {
        x = x * 256 + Long.parseLong(parts[i]);
      }
      
      List<String> res = new ArrayList();
      while(n > 0) {
        long count = x & -x; 
        // this count value here means if we don't change the current start ip, how many
        // more ips we can represent with CIDR
        
        while(count > n) { // for example count is 7 but we only want to 2 more ips
          count /= 2;
        }
        
        res.add(oneCIDR(x, count));
        n = n - (int)count;
        x = x + (int)count;
      }
      return res;
    }
    
    private String oneCIDR(long x, long count) {
      int d, c, b, a;
      d = (int) x & 255; // Compute right-most part of ip
      x >>= 8; // throw away the right-most part of ip
      c = (int) x & 255;
      x >>= 8;
      b = (int) x & 255;
      x >>= 8;
      a = (int) x & 255;
      
      int len = 0;
       // this while loop to know how many digits of count is in binary
       // for example, 00001000 here the len will be 4.
      while(count > 0) {
        count /= 2;
        len++;
      }
      int mask = 32 - (len - 1);
      // Think about 255.0.0.7 -> 11111111 00000000 00000000 00000111
      // x & -x of it is 00000001, the mask is 32. (which is 32 - (1 - 1))
      
      return new StringBuilder()
        .append(a)
        .append(".")
        .append(b)
        .append(".")
        .append(c)
        .append(".")
        .append(d)
        .append("/")
        .append(mask)
        .toString();
    }
}



// My Solution 1 (still in thinking process):
class Solution {
    public List<String> ipToCIDR(String ip, int n) {
        /*
            This question should be about: bit operation like complement(反码) and two's complement(补码) and data type convert between others (e.g. string) and bit.
            Notice: the IP of CIDR should not less than the given IP, and given IP should be contained in any one CIDR. And also notice each CIDR should not has duplicate with another one, for example: "255.0.0.16/29" and "255.0.0.16/32", "255.0.0.8/29" (11111111 00000000 00000000 00001xxx) and "255.0.0.12/30" (11111111 00000000 00000000 000011xx), in order to avoid that, the last  fix number should be equals or larger than the given ip last 1 number and start to calculate the max matches based on it.
        */
        Map<Integer, ArrayList<String>> validCIDRs = new HashMap<>(); // Key Integer will be the matches for its value's CIDRs matches, so Integer should not less than 'x' which Math.pow(2, 32-x) <= n;.
        int minMatches = 32 - (int) Math.floor(Math.log((double) n));
        for (int i=32; i >= minMatches; i--) {
            List<String> list = new ArrayList<>();
            // bit initial/setup/convert job here... (e.g. unchange part confirm, if minMatches = 28, then given ip '255.0.0.7' unchange part is 255.0.0.x)
            for (;;) {
                if (true) list.add();
            }
            validCIDRs.put(i, list);
        }
        
        // loop through all to find out all valid CIDRs and put into map for later combination calculate (find least map key integer combination which equals n).
        for (;;) { // loop validCIDRs
            // todo...
        }
    }
    
//     public boolean hasDuplicate(String cidr1, String cidr2) {
//         String[] cidr1Arr = cidr1.split("/");
//         String[] cidr2Arr = cidr2.split("/");
//         if (cidr1Arr[1] == cidr2Arr[1]) return false; // performance improvement, if cidr1Arr[0] == cidr2Arr[0]should be removed
        
//         cidr1Arr[0] = toBitFormat(cidr1Arr[0]);
//         cidr2Arr[0] = toBitFormat(cidr2Arr[0]);
//         int shortMatches = cidr1Arr[1] <= cidr2Arr[1] ? cidr1Arr[1] : cidr2Arr[1];
//         if (cidr1Arr[0].substring(0, shortMatches).equals(cidr1Arr[1].substring(0, shortMatches))) return true;
//         return false;
//     }
    
    public String toBitFormat(String ip) {
        String res = "";
        String[] ddNs = ip.split("."); // Dot-decimal notations
        for (String ddN : ddNs) {
            res += Integer.toBinaryString(Integer.parseInt(ddN));
        }
        return res;
    }
}