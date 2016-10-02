//Question:
/*
Given a non-empty string s and an abbreviation abbr, return whether the string matches with the given abbreviation.

A string such as "word" contains only the following valid abbreviations:

["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]

Notice that only the above abbreviations are valid abbreviations of the string "word". Any other string is not a valid abbreviation of "word".

Note:
Assume s contains only lowercase letters and abbr contains only lowercase letters and digits.

Example 1:

Given s = "internationalization", abbr = "i12iz4n":

Return true.

Example 2:

Given s = "apple", abbr = "a2e":

Return false.
*/





//My Solution: (not checked by leetcode check machine yet)
public class Solution{
    public boolean validWordAbbreviation(String word, String abbr) {
        String[] abbr_split = abbr.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //把数字和字母分开，全都存在abbr_split数组
        /*
        for(int i=0; i<abbr_split.length; i++){
        	System.out.println(abbr_split[i]);
        }
        
        比如"i12iz4n"，你会看到abbr_split是:
        ["i","12","iz","4","n"]
        */
        for(int i=0; i<abbr_split.length; i++){
        	if(Character.isDigit(abbr_split[i].charAt(0))){
        		if(word.length()<Integer.parseInt(abbr_split[i]))
        			return false;
        		word = word.substring(Integer.parseInt(abbr_split[i]));
        	}else{
        		if(word.substring(0, abbr_split[i].length()).equals(abbr_split[i])){
        			word = word.substring(abbr_split[i].length());
        		}else{
        			return false;
        		}
        	}
        }
        
        return true;
    }
}