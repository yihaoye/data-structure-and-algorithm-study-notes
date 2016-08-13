//Question:
/*
Given a string, find the length of the longest substring without repeating characters.

Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.

Given "bbbbb", the answer is "b", with the length of 1.

Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
*/




//Other's Answer:
class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        vector<int> dict(256, -1);
        int maxLen = 0, start = -1;
        for (int i = 0; i != s.length(); i++) {
            if (dict[s[i]] > start)
                start = dict[s[i]];
            dict[s[i]] = i;
            maxLen = max(maxLen, i - start);
        }
        return maxLen;
    }
};


//Official Answer:
class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        int n = s.length();
        int ans = 0, i = 0, j = 0;
        unordered_set<char> set;
        while(i<n && j<n)
        {	// try to extend the range[i,j]
        	if(!(set.find(s.at(j)) != set.end()))
        	{
        		set.insert(s.at(j++));
        		ans = max(ans, j-i);
        	}
        	else
        	{
        		set.erase(s.at(i++));
        	}
        }

        return ans;
    }
};



//Official Answer 2:
class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        int n = s.length(), ans = 0;
        map<char,int> the_map; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (the_map.find(s[j]) != the_map.end()) {
                i = max(the_map[s[j]], i);
            }
            ans = max(ans, j - i + 1);
            the_map[s[j]] = j + 1;
        }
        return ans;
    }
};
