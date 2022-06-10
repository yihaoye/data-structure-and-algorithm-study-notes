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
