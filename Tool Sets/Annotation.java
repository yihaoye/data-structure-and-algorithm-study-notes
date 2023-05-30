// 以下来源：https://cloud.tencent.com/developer/article/1009299

// 在使用SpringBoot作为Web敏捷开发的框架之后，SpringBoot除了自动装配配置的便捷之外，在很多时候需要基于注解来开发。注解不仅增加了代码的可读性，还增加了开发的速度。这篇文章主要讲述Java 注解。

// 元注解
// 元注解用于注解其他注解的。Java 5.0定义了4个标准的元注解，如下：

// @Target
// @Retention
// @Documented
// Inherited
// 现在来说说这四个元注解有什么作用。

// @Target
// 　@Target注解用于声明注解的作用范围，例如作用范围为类、接口、方法等。它的取值以及值所对应的范围如下：
// CONSTRUCTOR:用于描述构造器
// FIELD:用于描述域
// LOCAL_VARIABLE:用于描述局部变量
// METHOD:用于描述方法
// PACKAGE:用于描述包
// PARAMETER:用于描述参数
// TYPE:用于描述类、接口(包括注解类型) 或enum声明

// @Retention
// 该注解声明了注解的生命周期，即注解在什么范围内有效。
// SOURCE:在源文件中有效
// CLASS:在class文件中有效
// RUNTIME:在运行时有效（即运行时保留）
// 大多数注解都为RUNTIME

// @Documented
// 是一个标记注解，有该注解的注解会在生成 java 文档中保留。

// @Inherited
// 该注解表明子类是有继承了父类的注解。比如一个注解被该元注解修饰，并且该注解的作用在父类上，那么子类有持有该注解。如果注解没有被该元注解修饰，则子类不持有父类的注解。

// 自定义注解
// 在Java开发者，JDK自带了一些注解，在第三方框架Spring  带了大量的注解，这些注解称为第三方注解。在很多实际开发过程中，我们需要定义自己的注解。那么现在以案例的方式来讲解自定义注解。

// 在注解中，需要使用四种元注解来声明注解的作用范围、生命周期、继承，是否生成文档等。另外在注解中也可以有自己的成员变量，如果一个注解没有成员变量则称为标记注解。注解的成员变量，只支持原始类型、Class、Enumeration、Annoation。

// 现在定义一个@Writer注解，该注解被Retention、Documented、Inherited、Target修饰，表明该注解的作用范围为类、接口和方法，生命周期为运行时、该注解生成文档，并且子类可继承该注解。该注解有2个成员变量，一个为name一个为 age,代码如下：
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Writer {

    String name();

    int age();

}

// 那么有了该注解，怎么用呢？
// 该注解的作用范围为类、方法，写一个WriterTest，代码如下：
@Writer(name = "forezp", age = 12)
public class WriterTest {

    @Writer(name = "miya", age = 10)
    public void writeBlog() {
        System.out.println("writing blog");
    }

}

// 该类有了这个注解有何用？
// 一般来说，用该类修饰的类，需要通过反射来做一下逻辑的开发的工作，可广泛用于AOP、程序的配置等。现在写一个方法通过反射来解析该注解：

public static void main(String[] args) throws ClassNotFoundException {
    Class c = Class.forName("com.forezp.annotation.WriterTest");
    if (c.isAnnotationPresent(Writer.class)) {
        Writer w = (Writer) c.getAnnotation(Writer.class);
        System.out.println("name:" + w.name() + "   age:" + w.age());
    }
    Method[] methods = c.getMethods();
    for (Method method : methods) {
        if (method.isAnnotationPresent(Writer.class)) {
            Writer w = method.getAnnotation(Writer.class);
            System.out.println("name:" + w.name() + "   age:" + w.age());
        }
    }
}

// 这些代码基本为反射的内容，因为反射在另一篇文章已经详细讲述过，不再重复，运行该Main方法,控制台打印出如下内容：

//  name:forezp   age:12
//  name:miya   age:10

// 案例实战
// 有了上述的讲解，你可能对注解有所了解，但是对注解的具体应用并不是很深刻。现在以一个案例来详细讲述。

// 大家都对ORM框架Mybitis都非常的熟悉，在这个框架中用了大量的注解。现在模仿这个框架，通过自定义注解，来解析sql 的查询语句。实现过程大概如下：

// 定义@Table @Colum注解
// 定义一个实体User，定义一些基本的字段，并用注解修饰
// 用User类new对象，给对象的某些字段赋值
// 通过反射和注解来生成sql 的查询语句
// 首先定义个一个Table注解，它的作用范围为类，代码如下：
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Table {
    String value() default "";
}

// 定义一个Column注解，作用范围为字段，代码如下：
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {
    String value();
}

// 定义一个User类，在该类的加上@Table注解，在具体的字段上 @Column注解，代码如下：
@Table("user")
public class User {
    @Column("id")
    private int id;
    @Column("name")
    private String name;
    @Column("age")
    private int age;
    @Column("address")
    private String address;
    ..//省略getter setter
}

// 写一个生成sql语句的类，它是通过反射来获取表名、字段名，加上判断实体对象的字段值来生成 查询的 sql 语句的。代码如下：
public class GenUserSql {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        User u1 = new User();
        User u2 = new User();
        u1.setId(1);
        u2.setName("forezp");
        genSql(u2);
        genSql(u1);
    }

    private static void genSql(User user) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c = user.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from ");
        if (c.isAnnotationPresent(Table.class)) {
            Table table = (Table) c.getAnnotation(Table.class);
            String tableName = table.value();
            stringBuilder.append(tableName).append(" where 1=1 and ");
        }
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String columnName;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.value();
            } else {
                continue;
            }
            String fieldName = field.getName();
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                       Method method = c.getMethod(getMethodName);
            Object fieldValue = method.invoke(user);
            if (fieldValue == null || ((fieldValue instanceof Integer) && (Integer) fieldValue == 0)) {
                continue;
            }
            if (fieldValue instanceof Integer) {
                stringBuilder.append(columnName + "=" + fieldValue);
            }
            if (fieldValue instanceof String) {
                stringBuilder.append(columnName + "=" + "'" + fieldValue + "'");
            }

        }
        System.out.println(stringBuilder.toString());

    }
}

// 运行程序，控制台打印如下：
// >
//  select * from user where 1=1 and name=’forezp’
//  select * from user where 1=1 and id=1 

// 参考资料
// http://www.cnblogs.com/peida/archive/2013/04/24/3036689.html

// 源码下载
// https://github.com/forezp/java-study



// 更多参考：https://cloud.tencent.com/developer/article/1011184
