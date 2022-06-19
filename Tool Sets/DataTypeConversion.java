/*
数字类型转换
不同的数字类型对应不同的范围，按照范围从小到大的顺序依次是：byte、short、int、long、float、double。

将小范围类型的变量转换为大范围类型称为拓宽类型，不需要显性声明类型转换。将大范围类型的变量转换为小范围类型称为缩窄类型，必须显性声明类型转换，否则会导致编译错误。
*/

// String to Integer
String str = "0012";
Integer.parseInt(str); // 12

// Integer to String
int digit = 12
String str = String.format("%04d", digit) // "0012" (0 <= digit <= 9999)
digit = 1234
str = String.format("%04d", digit) // "1234"
