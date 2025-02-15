"""
You are given an array of unique strings words where words[i] is six letters long. One word of words was chosen as a secret word.

You are also given the helper object Master. You may call Master.guess(word) where word is a six-letter-long string, and it must be from words. Master.guess(word) returns:

-1 if word is not from words, or
an integer representing the number of exact matches (value and position) of your guess to the secret word.
There is a parameter allowedGuesses for each test case where allowedGuesses is the maximum number of times you can call Master.guess(word).

For each test case, you should call Master.guess with the secret word without exceeding the maximum number of allowed guesses. You will get:

"Either you took too many guesses, or you did not find the secret word." if you called Master.guess more than allowedGuesses times or if you did not call Master.guess with the secret word, or
"You guessed the secret word correctly." if you called Master.guess with the secret word with the number of calls to Master.guess less than or equal to allowedGuesses.
The test cases are generated such that you can guess the secret word with a reasonable strategy (other than using the bruteforce method).

 

Example 1:

Input: secret = "acckzz", words = ["acckzz","ccbazz","eiowzz","abcczz"], allowedGuesses = 10
Output: You guessed the secret word correctly.
Explanation:
master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
We made 5 calls to master.guess, and one of them was the secret, so we pass the test case.
Example 2:

Input: secret = "hamada", words = ["hamada","khaled"], allowedGuesses = 10
Output: You guessed the secret word correctly.
Explanation: Since there are two words, you can guess both.
 

Constraints:

1 <= words.length <= 100
words[i].length == 6
words[i] consist of lowercase English letters.
All the strings of wordlist are unique.
secret exists in words.
10 <= allowedGuesses <= 30
"""



# """
# This is Master's API interface.
# You should not implement it, or speculate about its implementation
# """
# class Master:
#     def guess(self, word: str) -> int:

class Solution:
    def findSecretWord(self, words: List[str], master: 'Master') -> None:
        # https://leetcode.com/problems/guess-the-word/solutions/160945/python-o-n-with-maximum-overlap-heuristic/?envType=study-plan-v2&envId=google-spring-23-high-frequency
        # 最大化信息增益（信息论、信息熵）+ 启发式 + 贪心 + 剪枝
        # 每次都猜测与其他候选词有最多重叠字符的单词，这样可以最大程度地缩小搜索范围
        def mock_guess(a, b): # pair_matches 统计同索引相同字符的数量
            return sum(c1 == c2 for c1, c2 in zip(a, b))

        def most_overlap_word(candidates: List[str]):
            counts = [[0 for _ in range(26)] for _ in range(6)] # 创建 6×26 的矩阵，记录每个位置上每个字母出现的次数
            for word in candidates: # 统计所有候选词中每个位置的字母频率
                for i, c in enumerate(word):
                    counts[i][ord(c) - ord("a")] += 1
            best_score = 0
            for word in candidates: # 找出与其他词重叠最多的单词
                score = 0
                for i, c in enumerate(word):
                     # 累加每个位置的字母在该位置出现的总次数，基于字符频率的启发式得分，不是严格意义上重叠统计，
                     # 得分高表示这个词用了很多"常见位置的常见字母"，采用启发式的原因是即使不一定最准确但计算效率远高于精准计算
                    score += counts[i][ord(c) - ord("a")]
                if score > best_score:
                    best_score = score
                    best_word = word
            return best_word

        candidates = words[:] # 复制原始单词列表
        while candidates:
            s = most_overlap_word(candidates) # 选择最佳猜测词
            matches = master.guess(s)
            if matches == 6:
                return
            # 筛选出与当前 guess 结果相同的候选词，假设如果目标词与候选词（当前猜测词）没有任何匹配，则只会挑选出匹配为 0 的候选词
            candidates = [w for w in candidates if mock_guess(s, w) == matches]
