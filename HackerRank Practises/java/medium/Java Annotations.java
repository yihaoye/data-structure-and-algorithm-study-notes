/*
Question description: https://www.hackerrank.com/challenges/java-annotations/problem
*/

// Other's Solution:
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FamilyBudget {
	String userRole() default "GUEST";
	int budgetLimit() default 0;
}

class FamilyMember {
	@FamilyBudget(userRole = "SENIOR", budgetLimit = 100)
	public void seniorMember(int budget, int moneySpend) {
		System.out.println("Senior Member");
		System.out.println("Spend: " + moneySpend);
		System.out.println("Budget Left: " + (budget - moneySpend));
	}

	@FamilyBudget(userRole = "JUNIOR", budgetLimit = 50)
	public void juniorUser(int budget, int moneySpend) {
		System.out.println("Junior Member");
		System.out.println("Spend: " + moneySpend);
		System.out.println("Budget Left: " + (budget - moneySpend));
	}
}

public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int testCases = Integer.parseInt(in.nextLine());
		while (testCases > 0) {
			String role = in.next();
			int spend = in.nextInt();
			try {
				Class annotatedClass = FamilyMember.class;
				Method[] methods = annotatedClass.getMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(FamilyBudget.class)) {
						FamilyBudget family = method
								.getAnnotation(FamilyBudget.class);
						String userRole = family.userRole();
						int budgetLimit = family.budgetLimit();
						if (userRole.equals(role)) {
							if(budgetLimit >= spend){
								method.invoke(FamilyMember.class.newInstance(),
										budgetLimit, spend);
							}else{
								System.out.println("Budget Limit Over");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			testCases--;
		}
	}
}



/* https://www.liaoxuefeng.com/wiki/1252599548343744/1265102413966176

注解是一种用作标注的“元数据”。

注解的作用
从 JVM 的角度看，注解本身对代码逻辑没有任何影响，如何使用注解完全由工具决定。

Java 的注解可以分为三类：

第一类是由编译器使用的注解，例如：
	@Override：让编译器检查该方法是否正确地实现了覆写；
	@SuppressWarnings：告诉编译器忽略此处代码产生的警告。

这类注解不会被编译进入 .class 文件，它们在编译后就被编译器扔掉了。

第二类是由工具处理 .class 文件使用的注解，比如有些工具会在加载 class 的时候，对 class 做动态修改，实现一些特殊的功能。
这类注解会被编译进入 .class 文件，但加载结束后并不会存在于内存中。这类注解只被一些底层库使用，一般开发者不必自己处理。

第三类是在程序运行期能够读取的注解，它们在加载后一直存在于 JVM 中，这也是最常用的注解。
例如，一个配置了 @PostConstruct 的方法会在调用构造方法后自动被调用（这是 Java 代码读取该注解实现的功能，JVM 并不会识别该注解）。

定义一个注解时，还可以定义配置参数。配置参数可以包括：

所有基本类型；
	* String；
	* 枚举类型；
	* 基本类型、String、Class 以及枚举的数组。

因为配置参数必须是常量，所以，上述限制保证了注解在定义时就已经确定了每个参数的值。

注解的配置参数可以有默认值，缺少某个配置参数时将使用默认值。

此外，大部分注解会有一个名为 value 的配置参数，对此参数赋值，可以只写常量，相当于省略了 value 参数。

如果只写注解，相当于全部使用默认值。
*/

/*
Java 使用 @interface 定义注解：

可定义多个参数和默认值，核心参数使用 value 名称；

元注解
有一些注解可以修饰其他注解，这些注解就称为元注解（meta annotation）。Java 标准库已经定义了一些元注解，开发者只需要使用元注解，通常不需要自己去编写元注解。
	@Target - 最常用的元注解是 @Target。使用 @Target 可以定义 Annotation 能够被应用于源码的哪些位置：
		类或接口：ElementType.TYPE；
		字段：ElementType.FIELD；
		方法：ElementType.METHOD；
		构造方法：ElementType.CONSTRUCTOR；
		方法参数：ElementType.PARAMETER。
	@Retention - 另一个重要的元注解 @Retention 定义了 Annotation 的生命周期（如果 @Retention 不存在，则该 Annotation 默认为 CLASS）：
		仅编译期：RetentionPolicy.SOURCE；
		仅 class 文件：RetentionPolicy.CLASS；
		运行期：RetentionPolicy.RUNTIME。
	@Repeatable - 使用 @Repeatable 这个元注解可以定义 Annotation 是否可重复（这个注解应用不是特别广泛）。
	@Inherited - 使用 @Inherited 定义子类是否可继承父类定义的 Annotation。@Inherited 仅针对 @Target(ElementType.TYPE) 类型的 annotation 有效，并且仅针对 class 的继承，对 interface 的继承无效。


定义 Annotation 的步骤：第一步，用 @interface 定义注解；第二步，添加参数、默认值；第三步，用元注解配置注解；
必须设置 @Target 来指定 Annotation 可以应用的范围；
应当设置 @Retention(RetentionPolicy.RUNTIME) 便于运行期读取该 Annotation。
*/
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
// 注解的参数类似无参数方法，可以用 default 设定一个默认值（强烈推荐）。最常用的参数应当命名为 value。

/*
可以在运行期通过反射读取 RUNTIME 类型的注解，注意千万不要漏写 @Retention(RetentionPolicy.RUNTIME)，否则运行期无法读取到该注解。

可以通过程序处理注解来实现相应的功能：
	对 JavaBean 的属性值按规则进行检查；
	JUnit 会自动运行 @Test 标记的测试方法。
*/
