https://leetcode.com/discuss/interview-question/system-design/548816/Amazon-or-System-Design-or-Web-Crawler-Detector  
  
Q:  
Imagine you have a website operating at the scale of Amazon or flipkart. We have to design a system that helps detect the bots or crawlers crawling our website. How can we do this?

A (others):  
Designing a distributed detected service that runs separately for a region. This service when running will save the incoming IP addresses and timestamp in a multi-map kind of data structure. The IP-address can be extracted from the IP Header of the incoming packet.

For every new request that comes over a particular duration (say a window of 15 minutes), we will save those requests. While saving those requests in the hash table we will check for:

if we can find a pattern by looking into the time-stamps that can help identify whether it is a bot crawling our website
I proposed finding the time differences between adjacent entries. In case the time difference is less than a threshold value, then it can be determined that it is a bot

Also, check the webpages visited as well. In case the webpages that are visited do not contain any page related to shopping cart or payment then it can be a bot.

Along with a hash-table we can keep an augmented trie in memory . By augmented trie I meant that each node before the "/" symbol of the URL, we will keep one such hash table.

The hash table at the top will keep the collective information for each of its children.

I explained this solution on these lines. The interviewer was interested in knowing:

how can we distinguish between an actual user and a bot? The pattern detection algorithm which I have described above in (1), can mark an actual user as well as a bot. In such a case, if we think about a corrective action (like showing a captcha), then the experience may be bad.

the bot may not visit pages which are children of a top level page, but may visit random pages in the website. will the algorithm work in this case

how will we handle this problem at large scale. I suggested using a separate service for each of the regions.

we can keep a db as well to back-up the trie at few intervals. This db can be a no-sql db. Interviewer was interested in knowing which no-sql would work for this? I suggested to use a columnar based db for this purpose

