// 引用：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319155793953
/*
将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
生成器模式（Builder）是使用多个“小型”工厂来最终创建出一个完整对象。
当使用 Builder 的时候，一般来说，是因为创建这个对象的步骤比较多，每个步骤都需要一个零部件，最终组合成一个完整的对象。

使用 Builder 模式时，适用于创建的对象比较复杂，最好一步一步创建出“零件”，最后再装配起来，比如下面的例 2。
*/

// 例 1：以 Markdown 转 HTML 为例
public class HtmlBuilder {
    private HeadingBuilder headingBuilder = new HeadingBuilder();
    private HrBuilder hrBuilder = new HrBuilder();
    private ParagraphBuilder paragraphBuilder = new ParagraphBuilder();
    private QuoteBuilder quoteBuilder = new QuoteBuilder();

    public String toHtml(String markdown) {
        StringBuilder buffer = new StringBuilder();
        markdown.lines().forEach(line -> {
            if (line.startsWith("#")) {
                buffer.append(headingBuilder.buildHeading(line)).append('\n');
            } else if (line.startsWith(">")) {
                buffer.append(quoteBuilder.buildQuote(line)).append('\n');
            } else if (line.startsWith("---")) {
                buffer.append(hrBuilder.buildHr(line)).append('\n');
            } else {
                buffer.append(paragraphBuilder.buildParagraph(line)).append('\n');
            }
        });
        return buffer.toString();
    }
}
/*
直接编写一个完整的转换器比较困难
把 Markdown 转 HTML 看作一行一行的转换，每一行根据语法，使用不同的转换器：
    如果以 # 开头，使用 HeadingBuilder 转换；
    如果以 > 开头，使用 QuoteBuilder 转换；
    如果以 --- 开头，使用 HrBuilder 转换；
    其余使用 ParagraphBuilder 转换。
*/
public class HeadingBuilder {
    public String buildHeading(String line) {
        int n = 0;
        while (line.charAt(0) == '#') {
            n++;
            line = line.substring(1);
        }
        return String.format("<h%d>%s</h%d>", n, line.strip(), n);
    }
}
//  注意：实际解析 Markdown 是带有状态的，即下一行的语义可能与上一行相关。这里简化了语法，把每一行视为可以独立转换。



// 例 2
// JavaMail 的 MimeMessage 就可以看作是一个 Builder 模式，只不过 Builder 和最终产品合二为一，都是 MimeMessage：
Multipart multipart = new MimeMultipart();
// 添加 text:
BodyPart textpart = new MimeBodyPart();
textpart.setContent(body, "text/html;charset=utf-8");
multipart.addBodyPart(textpart);
// 添加 image:
BodyPart imagepart = new MimeBodyPart();
imagepart.setFileName(fileName);
imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(input, "application/octet-stream")));
multipart.addBodyPart(imagepart);

MimeMessage message = new MimeMessage(session);
// 设置发送方地址:
message.setFrom(new InternetAddress("me@example.com"));
// 设置接收方地址:
message.setRecipient(Message.RecipientType.TO, new InternetAddress("xiaoming@somewhere.com"));
// 设置邮件主题:
message.setSubject("Hello", "UTF-8");
// 设置邮件内容为 multipart:
message.setContent(multipart);



// 例 3
// 很多时候，可以简化 Builder 模式，以链式调用的方式来创建对象。例如，经常编写这样的代码：
StringBuilder builder = new StringBuilder();
builder.append(secure ? "https://" : "http://")
       .append("www.test.com")
       .append("/")
       .append("?t=0");
String url = builder.toString();
// 由于经常需要构造 URL 字符串，可以使用 Builder 模式编写一个 URLBuilder，调用方式如下：
String url = URLBuilder.builder() // 创建 Builder
        .setDomain("www.test.com") // 设置 domain
        .setScheme("https") // 设置 scheme
        .setPath("/") // 设置路径
        .setQuery(Map.of("a", "123", "q", "K&R")) // 设置 query
        .build(); // 完成 build
