// 以下来源：
// https://cloud.tencent.com/developer/article/1009299
// https://cloud.tencent.com/developer/article/1013521
// https://cloud.tencent.com/developer/article/1014708

// 在使用SpringBoot作为Web敏捷开发的框架之后，SpringBoot除了自动装配配置的便捷之外，在很多时候需要基于注解来开发。注解不仅增加了代码的可读性，还增加了开发的速度。这篇文章主要讲述Java 注解。
// 注解本身不会起到任何作用，需要配合注解处理器（https://www.cnblogs.com/wellcherish/p/17087711.html）和反射才能发挥一定的作用，自己本身其实更像是一种特殊的注释。注解处理器实际上也是类，所以它只有在被加载到jvm中才能生效，但是如果注解的生命周期范围到不了jvm的话，注解处理器也是没用的。

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
// 首先什么是注解？
// 最常见的是，在我们使用Eclipse等工具编写java代码的时候，有时候会出现一些比如@Deprecated，@Override，@SuppressWarnings等东东。这个就是常见的几种注解。
// 在开发Java程序，尤其是Java EE应用的时候，总是免不了与各种配置文件打交道。
// 以Java EE中典型的S(pring)S(truts)H(ibernate)架构来说，Spring、Struts和Hibernate这 三个框架都有自己的XML格式的配置文件。
// 这些配置文件需要与Java源代码保存同步，否则的话就可能出现错误。而且这些错误有可能到了运行时刻才被发现。
// 把同一份信息保存在两个地方，总是个坏的主意。理想的情况是在一个地方维护这些信息就好了。其它部分所需的信息则通过自动的方式来生成。
// JDK 5中引入了源代码中的注解（annotation）这一机制。注解使得Java源代码中不但可以包含功能性的实现代码，还可以添加元数据。
// 注解的功能类似于代码中的注释，所不同的是注解不是提供代码功能的说明，而是实现程序功能的重要组成部分。Java注解已经在很多框架中得到了广泛的使用，用来简化程序中的配置。
// 而最常见的可能就是上面提到的这三个注解了，简单的介绍一下上面的这三个注解的作用：
// 1、@Override:只能用在方法之上的，用来告诉别人这一个方法是改写父类的。 
// 2、@Deprecated:建议别人不要使用旧的API的时候用的,编译的时候会用产生警告信息,可以设定在程序里的所有的元素上. 
// 3、@SuppressWarnings:这一个类型可以来暂时把一些警告信息消息关闭. 

// 在jdk自带的java.lang.annotation包里,打开如下几个源文件:
// Target.java
// Retention.java
// RetentionPolicy.java
// ElementType.java

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
   ElementType[] value();
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Retention {
   RetentionPolicy value();
}

public enum RetentionPolicy {
 SOURCE,
 CLASS,
 RUNTIME
}

public enum ElementType {
 TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR,
 LOCAL_VARIABLE, ANNOTATION_TYPE,PACKAGE
}

// 在设计annotations的时候必须把一个类型定义为@interface。我们需要注意的是：SOURCE,CLASS 和 RUNTIME.这三个级别。

// SOURCE代表的是这个Annotation类型的信息只会保留在程序源码里，源码如果经过了编译之后，Annotation的数据就会消失,并不会保留在编译好的.class文件里面。 

// ClASS的意思是这个Annotation类型的信息保留在程序源码里,同时也会保留在编译好的.class文件里面,在执行的时候，并不会把这一些 信息加载到虚拟机(JVM)中去.注意一下，当你没有设定一个Annotation类型的Retention值时，系统默认值是CLASS. 

// RUNTIME,表示在源码、编译好的.class文件中保留信息，在执行的时候会把这一些信息加载到JVM中去的．

// ＠Target里面的ElementType是用来指定Annotation类型可以用在哪一些元素上的.说明一下：TYPE(类型), FIELD(属性), METHOD(方法), PARAMETER(参数), CONSTRUCTOR(构造函数),LOCAL_VARIABLE(局部变量), ANNOTATION_TYPE,PACKAGE(包),其中的TYPE(类型)是指可以用在Class,Interface,Enum和 Annotation类型上.

// 另外,@Target自己也用了自己来声明自己。如果一个Annotation类型没有指明@Target使用在哪些元素上,那么它可以使用在任何元素之上,这里的元素指的是上面的八种类型.

// 举几个正确的例子:
@Target(ElementType.METHOD) 
@Target(value=ElementType.METHOD) 
@Target(ElementType.METHOD,ElementType.CONSTRUCTOR)

// @Documented的目的就是让这一个Annotation类型的信息能够显示在javaAPI说明文档上;没有添加的话，使用javadoc生成API文档的时候就会找不到这一个类型生成的信息.

// 另外一点，如果需要把Annotation的数据继承给子类，那么就会用到@Inherited这一个Annotation类型.

// 本文只是简单的说了一下注解的常规用法，至于更加深入的注解学习，请参见文章末尾的参考资料。下面我们来看自定义一个注解：源代码有如下几个：


// 源码分别为：
package com.java.annotation;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * 类注解
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyAnnotationClass {
    public String msg();
}


package com.java.annotation;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * 方法注解
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotationMethod {
    public String common();
}


package com.java.annotation;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAnnotationField {
    boolean request();
}


package com.java.annotation;
 
@MyAnnotationClass(msg = "这个是一个类注解")
public class MyAnnotationDemo {
 
    public MyAnnotationDemo() {
    }
 
    public MyAnnotationDemo(String hello) {
        this.hello = hello;
    }
 
    @MyAnnotationMethod(common = "这个是一个方法注解")
    public void method() {
        System.out.println(hello);
    }
 
    @MyAnnotationField(request = true)
    private String hello;
}


package com.java.annotation;
 
import java.lang.reflect.Field;
import java.lang.reflect.Method;
 
public class MyAnnotationTest {
    public static void main(String[] args) {
        MyAnnotationDemo demo = new MyAnnotationDemo("hello rollen");
        MyAnnotationClass annotationClass = demo.getClass().getAnnotation(MyAnnotationClass.class);
        System.out.println(annotationClass.msg());
 
        Method method = null;
        try {
            method = demo.getClass().getMethod("method",new Class[0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MyAnnotationMethod annotationMethod = method.getAnnotation(MyAnnotationMethod.class);
        System.out.println(annotationMethod.common());
         
        Field field = null;
        try {
            field = demo.getClass().getDeclaredField("hello");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        MyAnnotationField annotationField = field.getAnnotation(MyAnnotationField.class);
        System.out.println(annotationField.request());
 
    }
}

