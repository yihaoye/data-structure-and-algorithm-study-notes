/*
Design an algorithm that accepts a stream of integers and retrieves the product of the last k integers of the stream.

Implement the ProductOfNumbers class:

ProductOfNumbers() Initializes the object with an empty stream.
void add(int num) Appends the integer num to the stream.
int getProduct(int k) Returns the product of the last k numbers in the current list. You can assume that always the current list has at least k numbers.
The test cases are generated so that, at any time, the product of any contiguous sequence of numbers will fit into a single 32-bit integer without overflowing.

 

Example:

Input
["ProductOfNumbers","add","add","add","add","add","getProduct","getProduct","getProduct","add","getProduct"]
[[],[3],[0],[2],[5],[4],[2],[3],[4],[8],[2]]

Output
[null,null,null,null,null,null,20,40,0,null,32]

Explanation
ProductOfNumbers productOfNumbers = new ProductOfNumbers();
productOfNumbers.add(3);        // [3]
productOfNumbers.add(0);        // [3,0]
productOfNumbers.add(2);        // [3,0,2]
productOfNumbers.add(5);        // [3,0,2,5]
productOfNumbers.add(4);        // [3,0,2,5,4]
productOfNumbers.getProduct(2); // return 20. The product of the last 2 numbers is 5 * 4 = 20
productOfNumbers.getProduct(3); // return 40. The product of the last 3 numbers is 2 * 5 * 4 = 40
productOfNumbers.getProduct(4); // return 0. The product of the last 4 numbers is 0 * 2 * 5 * 4 = 0
productOfNumbers.add(8);        // [3,0,2,5,4,8]
productOfNumbers.getProduct(2); // return 32. The product of the last 2 numbers is 4 * 8 = 32 
 

Constraints:

0 <= num <= 100
1 <= k <= 4 * 10^4
At most 4 * 104 calls will be made to add and getProduct.
The product of the stream at any point in time will fit in a 32-bit integer.
*/



// My Solution:
import java.math.BigInteger;

class ProductOfNumbers {
    BigInteger[] dp; // prefix_product
    int[] lastZeroIndex;
    int idx = 0; // dp 从 1 开始使用 dp[0] 用于初始设置

    public ProductOfNumbers() {
        // 前缀积 + 处理零值
        // Time: O(1)
        dp = new BigInteger[4 * (int)1e4 + 2];
        lastZeroIndex = new int[4 * (int)1e4 + 2];
        dp[0] = BigInteger.ONE; // 前缀积，初始化 pre 设为 1
    }
    
    public void add(int num) {
        BigInteger pre = dp[idx];
        if (num == 0) {
            dp[++idx] = pre;
            lastZeroIndex[idx] = idx;
        } else {
            dp[++idx] = pre.multiply(BigInteger.valueOf(num));
            lastZeroIndex[idx] = lastZeroIndex[idx-1];
        }
    }
    
    public int getProduct(int k) {
        if (lastZeroIndex[idx] > idx - k) return 0;
        return dp[idx].divide(dp[idx - k]).intValue();
    }
}
/**
 * Your ProductOfNumbers object will be instantiated and called as such:
 * ProductOfNumbers obj = new ProductOfNumbers();
 * obj.add(num);
 * int param_2 = obj.getProduct(k);
 */



// Other's Solution:
// https://leetcode.com/problems/product-of-the-last-k-numbers/solutions/510260/java-c-python-prefix-product/
class ProductOfNumbers {
    List<Integer> A;
    public ProductOfNumbers() {
        A = new ArrayList();
        A.add(1);
    }
    
    public void add(int a) {
        if (a > 0)
            A.add(A.get(A.size() - 1) * a);
        else {
            A = new ArrayList();
            A.add(1);
        }
    }
    
    public int getProduct(int k) {
        int n = A.size();
        return k < n ? A.get(n - 1) / A.get(n - k - 1) : 0;
    }
}
