//Question:
/*
Problem

The Constitution of a certain country states that the leader is the person with the name containing the greatest number of different 
alphabet letters. (The country uses the uppercase English alphabet from A through Z.) 
For example, the name GOOGLE has four different alphabet letters: E, G, L, and O. The name APAC CODE JAM has eight different letters. 
If the country only consists of these 2 persons, APAC CODE JAM would be the leader.

If there is a tie, the person whose name comes earliest in alphabetical order is the leader.

Given a list of names of the citizens of the country, can you determine who the leader is?
Input

The first line of the input gives the number of test cases, T. T test cases follow. Each test case starts with a line with an 
interger N, the number of people in the country. Then N lines follow. The i-th line represents the name of the i-th person. 
Each name contains at most 20 characters and contains at least one alphabet letter.

Output

For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the name of 
the leader.
Limits

1 ≤ T ≤ 100.
1 ≤ N ≤ 100.
Small dataset

Each name consists of at most 20 characters and only consists of the uppercase English letters A through Z.
Large dataset

Each name consists of at most 20 characters and only consists of the uppercase English letters A through Z and ' '(space).
All names start and end with alphabet letters.
Sample

Input:
	2
	3
	ADAM
	BOB
	JOHNSON
	2
	A AB C
	DEF

Output:
	Case #1: JOHNSON
	Case #2: A AB C


In sample case #1, JOHNSON contains 5 different alphabet letters('H', 'J', 'N', 'O', 'S'), so he is the leader.

Sample case #2 would only appear in Large data set. The name DEF contains 3 different alphabet letters, the name A AB C also contains 3 different alphabet letters. A AB C comes alphabetically earlier so he is the leader.
*/





//Other's Answer:
#include <cstdio>  
#include <cstring>  
#include <vector>  
#include <string>  
using namespace std;  
int T,n,m;  
struct node{  
    char s[25];  
    int t;  
}s[105];  
int flag[258];  
int cmp(node a,node b){  
    if(a.t == b.t)  
        return strcmp(a.s, b.s)<0;  
    return a.t>b.t;  
}  
int main(){  
    scanf("%d",&T);  
    for(int z = 1;z<=T;z++){  
        scanf("%d\n",&n);  
        for(int i = 0;i<n;i++){  
            gets(s[i].s);  
            memset(flag, 0, sizeof(flag));  
            for(int j = 0;s[i].s[j];j++)  
                if(s[i].s[j] !=' ')  
                    flag[s[i].s[j]-'A'] = 1;  
            s[i].t = 0;  
            for(int j = 0;j<26;j++)  
                if(flag[j])  
                    s[i].t++;  
        }  
        sort(s,s+n,cmp);  
        printf("Case #%d: %s\n",z,s[0].s);  
    }  
    return 0;  
}  
