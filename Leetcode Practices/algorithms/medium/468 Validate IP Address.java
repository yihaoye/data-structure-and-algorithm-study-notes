/*
Given a string queryIP, return "IPv4" if IP is a valid IPv4 address, "IPv6" if IP is a valid IPv6 address or "Neither" if IP is not a correct IP of any type.

A valid IPv4 address is an IP in the form "x1.x2.x3.x4" where 0 <= xi <= 255 and xi cannot contain leading zeros. For example, "192.168.1.1" and "192.168.1.0" are valid IPv4 addresses while "192.168.01.1", "192.168.1.00", and "192.168@1.1" are invalid IPv4 addresses.

A valid IPv6 address is an IP in the form "x1:x2:x3:x4:x5:x6:x7:x8" where:

1 <= xi.length <= 4
xi is a hexadecimal string which may contain digits, lowercase English letter ('a' to 'f') and upper-case English letters ('A' to 'F').
Leading zeros are allowed in xi.
For example, "2001:0db8:85a3:0000:0000:8a2e:0370:7334" and "2001:db8:85a3:0:0:8A2E:0370:7334" are valid IPv6 addresses, while "2001:0db8:85a3::8A2E:037j:7334" and "02001:0db8:85a3:0000:0000:8a2e:0370:7334" are invalid IPv6 addresses.

 

Example 1:

Input: queryIP = "172.16.254.1"
Output: "IPv4"
Explanation: This is a valid IPv4 address, return "IPv4".
Example 2:

Input: queryIP = "2001:0db8:85a3:0:0:8A2E:0370:7334"
Output: "IPv6"
Explanation: This is a valid IPv6 address, return "IPv6".
Example 3:

Input: queryIP = "256.256.256.256"
Output: "Neither"
Explanation: This is neither a IPv4 address nor a IPv6 address.
 

Constraints:

queryIP consists only of English letters, digits and the characters '.' and ':'.
*/



// My Solution:
class Solution {
    public Set<Character> hexs = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'));

    public String validIPAddress(String queryIP) {
        if (queryIP.length() == 0) return "Neither";
        char start =  queryIP.charAt(0); char end =  queryIP.charAt(queryIP.length() - 1);
        if (start == '.' || start == ':' || end == '.' || end == ':') return "Neither";

        // IPv4 check
        String[] ipv4 = queryIP.split("\\.");
        if (ipv4.length > 1) {
            if (ipv4.length == 4 && checkNum(ipv4[0]) && checkNum(ipv4[1]) && checkNum(ipv4[2]) && checkNum(ipv4[3])) {
                return "IPv4";
            }
            return "Neither";
        }

        // IPv6 check
        String[] ipv6 = queryIP.split(":");
        if (ipv6.length == 8) {
            for (String s : ipv6) {
                if (s.length() > 4 || s.length() == 0 || !checkHex(s)) return "Neither";
            }
            return "IPv6";
        }
        return "Neither";        
    }

    public boolean checkNum(String s) {
        try {
            if (Integer.parseInt(s) > 255) throw new RuntimeException();
            if (!s.equals("0") && s.charAt(0) == '0') throw new RuntimeException();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean checkHex(String s) {
        for (char c : s.toCharArray()) {
            if (!hexs.contains(c)) return false;
        }
        return true;
    }
}
