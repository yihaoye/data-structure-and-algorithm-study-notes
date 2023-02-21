// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319659110433
// https://refactoringguru.cn/design-patterns/visitor
/**
访问者模式（Visitor）是一种操作一组对象的操作，它的目的是不改变对象的定义，但允许新增不同的访问者，来定义新的操作。
访问者模式能将算法与其所作用的对象隔离开来。

访问者模式的设计比较复杂
这里只介绍简化的访问者模式。假设要递归遍历某个文件夹的所有子文件夹和文件，然后找出.java文件，正常的做法是写个递归
void scan(File dir, List<File> collector) {
    for (File file : dir.listFiles()) {
        if (file.isFile() && file.getName().endsWith(".java")) {
            collector.add(file);
        } else if (file.isDir()) {
            // 递归调用:
            scan(file, collector);
        }
    }
}
上述代码的问题在于，扫描目录的逻辑和处理 .java 文件的逻辑混在了一起。如果下次需要增加一个清理 .class 文件的功能，就必须再重复写扫描逻辑。

因此，访问者模式先把数据结构（这里是文件夹和文件构成的树型结构）和对其的操作（查找文件）分离开，
以后如果要新增操作（例如清理 .class 文件），只需要新增访问者，不需要改变现有逻辑。
 */

public interface Visitor {
    // 访问文件夹:
    void visitDir(File dir);
    // 访问文件:
    void visitFile(File file);
}

public class FileStructure {
    // 根目录:
    private File path;
    public FileStructure(File path) {
        this.path = path;
    }
    // ...

    public void handle(Visitor visitor) {
		scan(this.path, visitor);
	}

	private void scan(File file, Visitor visitor) {
		if (file.isDirectory()) {
            // 让访问者处理文件夹:
			visitor.visitDir(file);
			for (File sub : file.listFiles()) {
                // 递归处理子文件夹:
				scan(sub, visitor);
			}
		} else if (file.isFile()) {
            // 让访问者处理文件:
			visitor.visitFile(file);
		}
	}
}

public class JavaFileVisitor implements Visitor {
    public void visitDir(File dir) {
        System.out.println("Visit dir: " + dir);
    }

    public void visitFile(File file) {
        if (file.getName().endsWith(".java")) {
            System.out.println("Found java file: " + file);
        }
    }
}

public class ClassFileCleanerVisitor implements Visitor {
	public void visitDir(File dir) {
	}

	public void visitFile(File file) {
		if (file.getName().endsWith(".class")) {
			System.out.println("Will clean class file: " + file);
		}
	}
}

public class Solution {
    public static void main(String[] args) {
        FileStructure fs = new FileStructure(new File("."));
        fs.handle(new JavaFileVisitor());
    }
}

// 可见，访问者模式的核心思想是为了访问比较复杂的数据结构，不去改变数据结构，而是把对数据的操作抽象出来，在“访问”的过程中以回调形式在访问者中处理操作逻辑。如果要新增一组操作，那么只需要增加一个新的访问者。
// 实际上，Java 标准库提供的 Files.walkFileTree() 已经实现了一个访问者模式
// 练习：使用访问者模式递归遍历文件夹 
