/**
Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.

 

Example 1:

Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Explanation:
The first and second John's are the same person as they have the common email "johnsmith@mail.com".
The third John and Mary are different people as none of their email addresses are used by other accounts.
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
Example 2:

Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]
 

Constraints:

1 <= accounts.length <= 1000
2 <= accounts[i].length <= 10
1 <= accounts[i][j].length <= 30
accounts[i][0] consists of English letters.
accounts[i][j] (for j > 0) is a valid email.
 */



// Other's Solution:
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // Union Find - https://leetcode.com/problems/accounts-merge/discuss/109157/JavaC%2B%2B-Union-Find
        Map<String, String> owner = new HashMap<>(), parents = new HashMap<>();
        Map<String, TreeSet<String>> unions = new HashMap<>();
        
        // UF 的初始化函数
        for (List<String> acc : accounts) {
            for (int i = 1; i < acc.size(); i++) {
                owner.put(acc.get(i), acc.get(0));
                parents.put(acc.get(i), acc.get(i));
            }
        }
        
        // UF 的 合并（Union）函数：把两个不相交的集合合并为一个集合
        for (List<String> acc : accounts) {
            String p = find(acc.get(1), parents);
            for (int i = 2; i < acc.size(); i++) parents.put(find(acc.get(i), parents), p);
        }
        
        // 对合并好的集合排序（非 UF 成员函数）
        for (List<String> acc : accounts) {
            String p = find(acc.get(1), parents);
            if (!unions.containsKey(p)) unions.put(p, new TreeSet<>());
            for (int i = 1; i < acc.size(); i++) unions.get(p).add(acc.get(i));
        }
        
        // 把每个排好序的集合与其对应的 owner 拼接起来，然后作为其中一个元素放进 res（非 UF 成员函数）
        List<List<String>> res = new ArrayList<>();
        for (String p : unions.keySet()) {
            List<String> emails = new ArrayList(unions.get(p));
            emails.add(0, owner.get(p));
            res.add(emails);
        }
        return res;
    }
    
    // UF 的查询（Find）函数
    private String find(String s, Map<String, String> parents) {
        return parents.get(s) == s ? s : find(parents.get(s), parents);
    }
}
