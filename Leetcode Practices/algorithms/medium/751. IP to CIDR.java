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



// My Solution:
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