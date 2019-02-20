//Question:
/*
 Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

 For example:
 Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

 Follow up:
 Could you do it without any loop/recursion in O(1) runtime? 
*/


//Solution:
class Solution {
public:
    int addDigits(int num) {	
		if(num<10)
            return num;
        else
        {
            if(num%9==0)
                return 9;
            else
                return num%9;
        }
    }
};

// Explain:
// 数根为何等于所求数模9（求余）？
/* 
假设所求数n=12345，则
12,345 = 1 × (9,999 + 1) + 2 × (999 + 1) + 3 × (99 + 1) + 4 × (9 + 1) + 5.
12,345 = (1 × 9,999 + 2 × 999 + 3 × 99 + 4 × 9) + (1 + 2 + 3 + 4 + 5).
12,345 = k*9 + (1 + 2 + 3 + 4 + 5).

如果第2个括号内和小于10，则n的数根直接等于n%9，因为n=(k*9+(n%9))

若第2个括号内和大于10，则该和可设为n2，因为n2本身就是n的各位上数值的和，按题目要求如果n2>10，
则继续对n2各位上的数值求和，即求n2数根，则可重蹈覆辙，n2 = k2*9 + (n2各位上数值相加) = k2*9 + n3,
若n3小于10，则n2数根即为n3亦即n的数根，若n3大于10，则将n3同上继续分解求数根，
因n,n2,n3 - ni数根均相等（依题意得），则直接n = ni的数根，(ni为极限值，即第一个小于10的数，是最终答案)
n = (k*9 + n2) = (k*9 + (k2*9 + n3)) = (k+k2+k3+...+k(i-1))*9 + ni，
n%9 = ni。(ni为极限值，即第一个小于10的数，是最终答案)
又因本题结果应在1-9之间取值，当ni为9时，n%9=0，所以n%9=0取ni为9。

*/

//other's solution: (better performance)
class Solution {
public:
    int addDigits(int num) {	
        if(num<10){
            return num;
        }
        else{
            return 1+((num-1)%9);
        }
    }
};