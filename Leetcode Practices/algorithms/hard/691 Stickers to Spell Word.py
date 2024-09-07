'''
We are given n different types of stickers. Each sticker has a lowercase English word on it.

You would like to spell out the given string target by cutting individual letters from your collection of stickers and rearranging them. You can use each sticker more than once if you want, and you have infinite quantities of each sticker.

Return the minimum number of stickers that you need to spell out target. If the task is impossible, return -1.

Note: In all test cases, all words were chosen randomly from the 1000 most common US English words, and target was chosen as a concatenation of two random words.

 

Example 1:

Input: stickers = ["with","example","science"], target = "thehat"
Output: 3
Explanation:
We can use 2 "with" stickers, and 1 "example" sticker.
After cutting and rearrange the letters of those stickers, we can form the target "thehat".
Also, this is the minimum number of stickers necessary to form the target string.
Example 2:

Input: stickers = ["notice","possible"], target = "basicbasic"
Output: -1
Explanation:
We cannot form the target "basicbasic" from cutting letters from the given stickers.
 

Constraints:

n == stickers.length
1 <= n <= 50
1 <= stickers[i].length <= 10
1 <= target.length <= 15
stickers[i] and target consist of lowercase English letters.
'''



# Other's Solution:
class Solution:
    def minStickers(self, stickers: List[str], target: str) -> int:
        # backtracking + memory - https://leetcode.com/problems/stickers-to-spell-word/solutions/4095051/python-accepted-dfs-memo-solution-with-explanation-easy-to-understand/
        result = self.backtracking(stickers, target, 0, {})
        return result if result != float("inf") else -1

    def backtracking(self, stickers, target, idx, memo):
        # if target is empty then we don't need to take any sticker
        if target == "":
            return 0
        # if we've searched through all stickers and haven't completed the target
        # then there is no solution
        if idx == len(stickers):  # idx 是一个整数，表示当前正在处理的 sticker 的索引
            return float("inf")

        # lookup the answer in the cache
        key = (idx, target)  # 创建一个元组，因为结构是键值，所以 {} 会是字典而不是 set
        if key in memo:
            return memo[key]

        # choice 1 don't take the current sticker
        result = self.backtracking(stickers, target, idx + 1, memo)

        # choice 2 try to take the current sticker
        currentSticker = stickers[idx]
        newTarget = target
        somethingRemoved = False
        for c in currentSticker:
            idxToRemove = newTarget.find(c)
            if idxToRemove != -1:
                newTarget = newTarget[:idxToRemove] + newTarget[idxToRemove + 1:]
                somethingRemoved = True

        # only try this sticker again if we were able to remove something from
        # the target string
        if somethingRemoved:
            result = min(result, 1 + self.backtracking(stickers, newTarget, idx, memo))

        # cache the result
        memo[key] = result
        return result
