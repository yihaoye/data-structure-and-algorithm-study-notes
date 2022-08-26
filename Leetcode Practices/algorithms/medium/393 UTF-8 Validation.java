/**
Given an integer array data representing the data, return whether it is a valid UTF-8 encoding (i.e. it translates to a sequence of valid UTF-8 encoded characters).

A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:

For a 1-byte character, the first bit is a 0, followed by its Unicode code.
For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the most significant 2 bits being 10.
This is how the UTF-8 encoding would work:

     Number of Bytes   |        UTF-8 Octet Sequence
                       |              (binary)
   --------------------+-----------------------------------------
            1          |   0xxxxxxx
            2          |   110xxxxx 10xxxxxx
            3          |   1110xxxx 10xxxxxx 10xxxxxx
            4          |   11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
x denotes a bit in the binary form of a byte that may be either 0 or 1.

Note: The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.

 

Example 1:

Input: data = [197,130,1]
Output: true
Explanation: data represents the octet sequence: 11000101 10000010 00000001.
It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
Example 2:

Input: data = [235,140,4]
Output: false
Explanation: data represented the octet sequence: 11101011 10001100 00000100.
The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
The next byte is a continuation byte which starts with 10 and that's correct.
But the second continuation byte does not start with 10, so it is invalid.
 

Constraints:

1 <= data.length <= 2 * 10^4
0 <= data[i] <= 255
 */



// My Solution:
class Solution {
    public boolean validUtf8(int[] data) {
        // 位运算 + HashMap        
        Map<int[], List<int[]>> map = new HashMap<>();
        map.put(new int[]{0, 128}, Arrays.asList()); // [00000000, 10000000], <...>
        map.put(new int[]{192, 32}, Arrays.asList(new int[]{128, 64})); // [11000000, 00100000], <...>
        map.put(new int[]{224, 16}, Arrays.asList(new int[]{128, 64}, new int[]{128, 64})); // [11100000, 00010000], <...>
        map.put(new int[]{240, 8}, Arrays.asList(new int[]{128, 64}, new int[]{128, 64}, new int[]{128, 64})); // [11110000, 00001000], <...>
        
        int index = 0;
        while (index < data.length) {
            index = validUtf8(data, index, map);
            if (index == -1) return false;
        }
        return true;
    }
    
    public int validUtf8(int[] data, int index, Map<int[], List<int[]>> map) {
        for (Map.Entry<int[], List<int[]>> entry : map.entrySet()) {
            int[] matchHead = entry.getKey();
            if ((matchHead[0] & data[index]) == matchHead[0] && (matchHead[1] & data[index]) == 0) {
                int nextIndex = index + 1;
                if (data.length-index < entry.getValue().size()) return -1;
                while (nextIndex-index <= entry.getValue().size()) {
                    int[] matchBody = entry.getValue().get(nextIndex-index-1);
                    if ((matchBody[0] & data[nextIndex]) == matchBody[0] && (matchBody[1] & data[nextIndex]) == 0) nextIndex++;
                    else return -1;
                }
                return nextIndex;
            }
        }
        
        return -1;
    }
}
