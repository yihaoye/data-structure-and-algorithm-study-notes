/*
You have an array of logs.  Each log is a space delimited string of words.

For each log, the first word in each log is an alphanumeric identifier.  Then, either:

Each word after the identifier will consist only of lowercase letters, or;
Each word after the identifier will consist only of digits.
We will call these two varieties of logs letter-logs and digit-logs.  It is guaranteed that each log has at least one word after its identifier.

Reorder the logs so that all of the letter-logs come before any digit-log.  The letter-logs are ordered lexicographically ignoring identifier, with the identifier used in case of ties.  The digit-logs should be put in their original order.

Return the final order of the logs.

 

Example 1:

Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
 

Constraints:

0 <= logs.length <= 100
3 <= logs[i].length <= 100
logs[i] is guaranteed to have an identifier, and a word after the identifier.
*/



// My Solution:
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        int len = logs.length;
        String[] letterLogs = new String[len];
        String[] digitLogs = new String[len];
        int i=0, j=0;
        for (;i+j<len;) {
            if (isLetterLog(logs[i+j])) {
                letterLogs[i] = logs[i+j];
                i++;
            } else {
                digitLogs[j] = logs[i+j];
                j++;
            }
        }
        Arrays.sort(letterLogs, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1 == null) return 1;
                if (s2 == null) return -1;
                if ((s1.split(" ", 2))[1].equals((s2.split(" ", 2))[1])) return java.util.Arrays.asList(logs).indexOf(s2)-java.util.Arrays.asList(logs).indexOf(s1);
                return (s1.split(" ", 2))[1].compareTo((s2.split(" ", 2))[1]);
            }
        });
        while (j > 0) letterLogs[--len] = digitLogs[--j];
        
        return letterLogs;
    }
    
    public boolean isLetterLog(String s) {
        char[] cArray = s.toCharArray();
        for (int i=0; i<cArray.length; i++) {
            if (cArray[i] == ' ') return ((cArray[i+1]-'a')>=0 && ('z'-cArray[i+1])>=0);
        }
        return false;
    }
}