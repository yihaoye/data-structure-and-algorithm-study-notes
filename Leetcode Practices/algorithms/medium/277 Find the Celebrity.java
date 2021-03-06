/*
Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.

Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).

You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int findCelebrity(n). There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.

 
Example 1:
![](https://assets.leetcode.com/uploads/2019/02/02/277_example_1_bold.PNG)

Input: graph = [
  [1,1,0],
  [0,1,0],
  [1,1,1]
]
Output: 1
Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.


Example 2:
![](https://assets.leetcode.com/uploads/2019/02/02/277_example_2.PNG)

Input: graph = [
  [1,0,1],
  [1,1,0],
  [0,1,1]
]
Output: -1
Explanation: There is no celebrity.
 

Note:

The directed graph is represented as an adjacency matrix, which is an n x n matrix where a[i][j] = 1 means person i knows person j while a[i][j] = 0 means the contrary.
Remember that you won't have direct access to the adjacency matrix.
*/



// Other's Solution 1:

/* The knows API is defined in the parent class Relation. boolean knows(int a, int b); */
public class Solution extends Relation {
  public int findCelebrity(int n) {
      int candidate = 0;
      for(int i = 1; i < n; i++){
          if(knows(candidate, i))
              candidate = i;
      }
      // celebrity 只能有一个，最后 candidate 之前的肯定都不是，因为他们不是认识了另一个人就是另一个人不认识他，
      // 而前面的人除了上一个 candidate 都没和最后的 candidate 有过双向检查，而最后一个 candidate 后面的所有人也肯定不是，
      // 因为最后 candidate 不认识他们，但是并没有检查他们是否认识最后 candidate。

      // 因此需要第 2 个 for 循环，用来双向检查最后 candidate 和最后 candidate 之前的人（上一个 candidate 和最后 candidate 已有一次单向检查，但是这里重复一次没关系对性能影响可以忽略不计），
      // 以及检查最后 candidate 之后的人是否认识最后 candidate。
      for(int i = 0; i < n; i++){
          if(i != candidate && (knows(candidate, i) || !knows(i, candidate))) return -1;
      }
      return candidate;
  }
}
// It you have a hard time understanding this code, check out the other Java code using stack. 
// It's a lot easier to understand, they used the same approache. 
// First you assume everyone to be a candidate, then you just randomly pick two, check whether a knows b, if a knows b, we know for sure, a is not a cele, if a does not know b, we know for sure b is not cele. 
// Either way, one person is gonna get eliminated. At the end, you have one person left that might be a cele. You confirm that by do a walk through again.



// My Solution:
public class Solution extends Relation {
  public int findCelebrity(int n) {
    // ToDo...
  }
}