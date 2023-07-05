import java.math.BigDecimal;
import java.math.BigInteger;

// BigDecimal
BigDecimal bd1 = new BigDecimal("10");
BigDecimal bd2 = new BigDecimal("11");

bd1 = bd1.add(bd2);

BigDecimal bd3 = bd1.pow(3);
BigDecimal bd4 = bd3.divide(bd2);
double d1 = bd4.doubleValue()




// BigInteger
BigInteger bigInteger = new BigInteger("1");   
int n = 3;  
for (int i = 1; i <= n; i++){  
    bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
}
bigInteger = bigInteger.add(new BigInteger("10"));


// BigInteger 与数论
// BigInteger 是 Java 提供的高精度计算类，可以很方便地解决高精度问题。

// 基本运算
// 以下均用 this 代替当前 BigIntger :
// 函数名	功能
// abs()	返回 this 的绝对值
// negate()	返回 - this
// add(BigInteger val)	返回 this + val
// subtract(BigInteger val)	返回 this - val
// multiply(BigInteger val)	返回 this * val
// divide(BigInteger val)	返回 this / val
// remainder(BigInteger val)	返回 this % val
// mod(BigInteger val)	返回 this mod val
// pow(int e)	返回 this^e
// and(BigInteger val)	返回 this & val
// or(BigInteger val)	返回 this | val
// not()	返回 ~ this
// xor(BigInteger val)	返回 this ^ val
// shiftLeft(int n)	返回 this << n
// shiftRight(int n)	返回 this >> n
// max(BigInteger val)	返回 this 与 val 的较大值
// min(BigInteger val)	返回 this 与 val 的较小值
// bitCount()	返回 this 的二进制中不包括符号位的 1 的个数
// bitLength()	返回 this 的二进制中不包括符号位的长度
// getLowestSetBit()	返回 this 的二进制中最右边的位置
// compareTo(BigInteger val)	比较 this 和 val 值大小
// toString()	返回 this 的 10 进制字符串表示形式
// toString(int radix)。	返回 this 的 raidx 进制字符串表示形式


// 数学运算
// 以下均用 this 代替当前 BigIntger :

// 函数名	功能
// gcd(BigInteger val)	返回 this 的绝对值与 val 的绝对值的最大公约数
// isProbablePrime(int val)	返回一个表示 this 是否是素数的布尔值
// nextProbablePrime()	返回第一个大于 this 的素数
// modPow(BigInteger b, BigInteger p)	返回 this ^ b mod p
// modInverse(BigInteger p)	返回 a mod p 的乘法逆元
