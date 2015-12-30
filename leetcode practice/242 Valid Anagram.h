//Question:
/*
Given two strings s and t, write a function to determine if t is an anagram of s.

For example,
s = "anagram", t = "nagaram", return true.
s = "rat", t = "car", return false.

Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?
*/



//Other's Good Solution: (time complex is O(n), better than mine)
class Solution {
public:
    bool isAnagram(string s, string t) {
        //if(s==NULL && t==NULL) 
            //return true;
        if(s=="" && t=="") // 原答主写的是上面这个if语句，但leetcode编译不通过，因此尝试用这个语句代替
            return true;
        if(s.size() != t.size()) 
            return false;
    
        int a[26]={0};
        for(int i=0;i<s.size();i++){
            a[s[i]-'a']++; // 两字符的的ASCLL码值相减，码值相减，最后应得出类似int的数（数据类型） 
            a[t[i]-'a']--;
        }
    
        for(int i=0;i<26;i++){
            if(a[i]!=0) return false; // 原答主设条件为a[i]<0，经过思考认为!=0可能更快也有助于理解更准确
        }
        return true;
    }
};




//My original solution:(problem with time limit exceed), use bubble sort, time complex is O(n^2).
class Solution {
public:
    bool isAnagram(string s, string t) {
        int s_length = s.size();
        int t_length = t.size();
        //sizeof(s)/sizeof(string); 
        if(s_length!=t_length)
            return false;
        else
        {
            //sort the two string
            for(int j=0; j<s_length-1; j++)
            {
                for(int i=0; i<s_length-1-j; i++)
                {
                    if(s[i]>s[i+1])
                        swap(s[i], s[i+1]);
                }
            }
            for(int j=0; j<t_length-1; j++)
            {
                for(int i=0; i<t_length-1-j; i++)
                {
                    if(t[i]>t[i+1])
                        swap(t[i], t[i+1]);
                }
            }
            //compare the two string
            if(s == t)
                return true;
            else
                return false;
        }
    }
};