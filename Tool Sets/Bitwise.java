// 参考：https://www.geeksforgeeks.org/bitwise-operators-in-java/

// Java program to illustrate
// bitwise operators
public class operators {
    public static void main(String[] args) {
        // Initial values
        int a = 5;
        int b = 7;
 
        // bitwise and
        // 0101 & 0111=0101 = 5
        System.out.println("a&b = " + (a & b));
 
        // bitwise or
        // 0101 | 0111=0111 = 7
        System.out.println("a|b = " + (a | b));
 
        // bitwise xor
        // 0101 ^ 0111=0010 = 2
        System.out.println("a^b = " + (a ^ b));
 
        // bitwise not
        // ~0101=1010
        // will give 2's complement of 1010 = -6
        System.out.println("~a = " + ~a);
 
        // can also be combined with
        // assignment operator to provide shorthand
        // assignment
        // a=a&b
        a &= b;
        System.out.println("a= " + a);

        // left shift operator
        // 0000 0101<<2 =0001 0100(20)
        // similar to 5*(2^2)
        System.out.println("a<<2 = " + (a << 2));
 
        // right shift operator
        // 0000 0101 >> 2 =0000 0001(1)
        // similar to 5/(2^2)
        System.out.println("a>>2 = " + (a >> 2));
 
        // unsigned right shift operator
        System.out.println("a>>>2 = " + (a >>> 2));
    }
}



class Solution {
    // 实现 changToOne(num, K), 将 num 的二进制的第 K 位设置为 1
    // changToOne(2, 1) -> 3，2 的二进制为 10，将其第一位设为 1 得到 3
    public int changToOne(int num, int K) {
        int bit = 1;
        bit = bit << k-1;
        return num | bit;
    }

    // 实现 changToZero(num, K), 将 num 的二进制的第 K 位设置为 0
    // changToOne(2, 2) -> 0，2 的二进制为 10，将其第二位设为 0 得到 0
    public int changToZero(int num, int K) {
        int bit = 1;
        bit = bit << k-1;
        bit = ~bit;
        return num & bit;
    }

    // 实现 reverseK(num, K), 将 num 的二进制的第 K 位取反
    // reverseK(2, 1) -> 3，2 的二进制为 10，将其第一位取反得到 3
    public int reverseK(int num, int K) {
        int bit = 1;
        bit = bit << k-1;
        return num ^ bit;
    }

    // 实现 cal(num)，计算 num 的二进制中 1 的数量
    // cal(2) -> 1，2 的二进制为 10，1 的数量为 1
    public int cal(int num) {
        int res = 0;
        while (num != 0) {
            if ((num & 1) == 1) res++;
            num = num >> 1;
        }

        return res;
    }

    // 实现 check(num)，判断 num 是否是 2 的非负整数次幂
    // check(8) -> True
    // check(7) -> False
    public boolean check(int num) {
        // if (num <= 0) return false;
        // if (num == 1 || num%2 == 0) return true;
        // return false;

        num &= (num-1);
        return num == 0;
    }

    // 实现 delete(num)，删除 num 最低有效位
    // delete(2) -> 0，2 的二进制为 10，删除最低有效位得到 0
    // delete(5) -> 4，5 的二进制为 101，删除最低有效位得到 4
    public int delete(int num) {
        // int bit = 1;
        // while ((num & bit) == 0) bit = bit << 1;
        // return num ^ bit;

        return num & (num-1);
    }

    // 实现 loo(num)，遍历 num 的所有非空子集合，
    // loo(5) -> [1, 4, 5]，5 的二进制为 101，其非空子集合为 [001, 100, 101]
    public Set<Integer> loo(int num) {
        Set<Integer> res = new HashSet<>();
        int i = 0;
        while (++i <= num) {
            if ((i & num) == i) res.add(i);
        }

        return res;
    }
}



// 求一个数的二进制的最高有效位（即最高的 1 在哪）
int n = (int) (Math.log(num) / Math.log(2));



// 计算一个整数的二进制表示有几个 1
int num = 3;
int bitCnt = Integer.bitCount(num);
