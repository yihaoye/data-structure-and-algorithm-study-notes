// 引用：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319155793953
// https://zhuanlan.zhihu.com/p/58093669
/*
当一个类的构造函数参数个数超过4个，而且这些参数有些是可选的参数，考虑使用构造者模式。
*/

// 很多时候，可以简化 Builder 模式，以链式调用的方式来创建对象。


// 例 1：
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


// 例 2：
public class Computer {
    private final String cpu; // 必须
    private final String ram; // 必须
    private final int usbCount; // 可选
    private final String keyboard; // 可选
    private final String display; // 可选

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usbCount = builder.usbCount;
        this.keyboard = builder.keyboard;
        this.display = builder.display;
    }

    public static class Builder {
        private String cpu; // 必须
        private String ram; // 必须
        private int usbCount; // 可选
        private String keyboard; // 可选
        private String display; // 可选

        public Builder(String cup, String ram) {
            this.cpu = cup;
            this.ram = ram;
        }

        public Builder setUsbCount(int usbCount) {
            this.usbCount = usbCount;
            return this;
        }
        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }
        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }      

        public Computer build() {
            return new Computer(this);
        }
    }
    
    // 省略 getter 方法
}

// 如何使用
// 在客户端使用链式调用，一步一步的把对象构建出来。
Computer computer = new Computer.Builder("因特尔", "三星")
                .setDisplay("三星24寸")
                .setKeyboard("罗技")
                .setUsbCount(2)
                .build();
