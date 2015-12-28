//Question:
/*
 You are playing the following Nim Game with your friend: There is a heap of stones on the table, 
 each time one of you take turns to remove 1 to 3 stones. The one who removes the last stone will be the winner. 
 You will take the first turn to remove the stones.

 Both of you are very clever and have optimal strategies for the game. 
 Write a function to determine whether you can win the game given the number of stones in the heap.

 For example, if there are 4 stones in the heap, then you will never win the game: no matter 1, 2, or 3 stones you remove, 
 the last stone will always be removed by your friend. 

*/



//Solution:
class Solution {
public:
    bool canWinNim(int n) {
        if(n%4 == 0)
            return false;
        else
            return true;
    }
};

// Explain:
// n = 4 + 1*x + 2*y + 3*z;
// 总之留给对方4的倍数，我方一定赢，即要求一开始的数字不是4的倍数（0除外）。因为无论对方拿走1、2、3中的哪个，我方都能凑够4然后继续留给对方4的倍数。
// 所有正整数都可以诠释为(4n,4n+1,4n+2,4n+3)中的一个。因为任何正整数对4求余都只有4种可能（0，1，2，3）。
// 当n小于4时，我方因先拿，所以一定赢，也符合一开始数字不是4的倍数（0除外）。
// 当n为0时，应该是出错了（无法判断谁赢）。但既然题目说有石头，应该不能为0。


//other people's solution:
class Solution {
public:
    bool canWinNim(int n) {
        return n%4 ;
		// or "return n%4!=0;"
    }
};