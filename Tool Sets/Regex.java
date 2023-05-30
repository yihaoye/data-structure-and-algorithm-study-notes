// 正则表达式
import java.util.regex.*;
 

String content = "I am ab " + "from abc.xxx.";
String pattern = ".*abc.*";
boolean isMatch = Pattern.matches(pattern, content);
System.out.println("if string contains 'abc' sub string? " + isMatch);



// 按指定模式在字符串查找
String line = "This order was placed for QT3000! OK?";
String pattern = "(\\D*)(\\d+)(.*)";
// 创建 Pattern 对象
Pattern r = Pattern.compile(pattern);
// 现在创建 matcher 对象
Matcher m = r.matcher(line);
if (m.find( )) {
    System.out.println("Found value: " + m.group(0) );
    System.out.println("Found value: " + m.group(1) );
    System.out.println("Found value: " + m.group(2) );
    System.out.println("Found value: " + m.group(3) ); 
} else {
    System.out.println("NO MATCH");
}



// UUID 的正则表达式 (https://stackoverflow.com/a/6640851/6481829)
// \b[a-fA-F0-9]{8}\b-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-\b[a-fA-F0-9]{12}\b



// 在一些数学公式或复杂但有格式规律的字符串处理里，正则表达式配合 Scanner 使用有时比较方便
