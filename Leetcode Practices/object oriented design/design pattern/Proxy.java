// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319432618017
// https://www.cnblogs.com/qlqwjy/p/7550609.html
/*
为其他对象提供一种代理以控制对这个对象的访问。

代理模式，即 Proxy，它和 Adapter 模式很类似。
回顾 Adapter 模式，它用于把 A 接口转换为 B 接口
public BAdapter implements B {
    private A a;
    public BAdapter(A a) {
        this.a = a;
    }
    public void b() {
        a.a();
    }
}
*/
// 而 Proxy 模式不是把 A 接口转换成 B 接口，它还是转换成 A 接口
public AProxy implements A {
    private A a;
    public AProxy(A a) {
        this.a = a;
    }
    public void a() {
        if (getCurrentUser().isRoot()) {
            this.a.a();
        } else {
            throw new SecurityException("Forbidden");
        }
    }
}
/*
这样一来，通过 Proxy 模式给 A 接口再包一层，就实现了权限检查。（上面的例子：只有符合要求的用户，才会真正调用目标方法，否则，会直接抛出异常）

那为啥不把权限检查的功能直接写到目标实例 A 的内部？
因为编写代码的原则有：
    职责清晰：一个类只负责一件事；
    易于测试：一次只测一个功能。

用 Proxy 实现这个权限检查，可以获得更清晰、更简洁的代码：
    A 接口：只定义接口；
    ABusiness 类：只实现 A 接口的业务逻辑；
    APermissionProxy 类：只实现 A 接口的权限检查代理。

当希望编写其他类型的代理，可以继续增加类似 ALogProxy，而不必对现有的 A 接口、ABusiness 类进行修改。

Proxy 模式的好处是：可以在目标对象实现的基础上，增强额外的功能操作，即扩展目标对象的功能。
这里使用到编程中的一个思想：不要随意去修改别人已经写好的代码或者方法，如果需改修改，可以通过代理的方式来扩展该方法
*/



/*
权限检查只是代理模式的一种应用。Proxy 还广泛应用在：
    远程代理：如 Java RMI
    虚代理：如 JDBC 连接池返回的连接（Connection 对象）
    保护代理
    智能引用

代理模式分两种：
    静态代理
    动态代理

Spring AOP 的底层原理默认是使用 JDK 动态代理
*/



/*
Proxy 模式和 Decorator 模式有些类似。
区别在于：Decorator 模式让调用者自己创建核心类，然后组合各种功能，而 Proxy 模式决不能让调用者自己创建再组合，否则就失去了代理的功能。
Proxy 模式让调用者认为获取到的是核心类接口，但实际上是代理类。
*/



// 应用代理模式编写一个 JDBC 连接池（DataSource）。
// 首先来编写一个虚代理，即如果调用者获取到 Connection 后，并没有执行任何 SQL 操作，那么这个 Connection Proxy 实际上并不会真正打开 JDBC 连接。
public abstract class AbstractConnectionProxy implements Connection { // 这个 AbstractConnectionProxy 代理类的作用是把 Connection 接口定义的方法全部实现一遍，因为 Connection 接口定义的方法太多了，后面要编写的 LazyConnectionProxy 只需要继承 AbstractConnectionProxy，就不必再把 Connection 接口方法挨个实现一遍。
    // 抽象方法获取实际的 Connection:
    protected abstract Connection getRealConnection();

    // 实现 Connection 接口的每一个方法:
    public Statement createStatement() throws SQLException {
        return getRealConnection().createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getRealConnection().prepareStatement(sql);
    }

    // ...其他代理方法...
}

// 如果调用者没有执行任何 SQL 语句，那么 target 字段始终为 null。只有第一次执行 SQL 语句时（即调用任何类似 prepareStatement() 方法时，触发 getRealConnection() 调用），才会真正打开实际的 JDBC Connection。
public class LazyConnectionProxy extends AbstractConnectionProxy {
    private Supplier<Connection> supplier;
    private Connection target = null;

    public LazyConnectionProxy(Supplier<Connection> supplier) {
        this.supplier = supplier;
    }

    // 覆写 close 方法：只有 target 不为 null 时才需要关闭:
    public void close() throws SQLException {
        if (target != null) {
            System.out.println("Close connection: " + target);
            super.close();
        }
    }

    @Override
    protected Connection getRealConnection() {
        if (target == null) {
            target = supplier.get();
        }
        return target;
    }
}

public class LazyDataSource implements DataSource {
    private String url;
    private String username;
    private String password;

    public LazyDataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return new LazyConnectionProxy(() -> {
            try {
                Connection conn = DriverManager.getConnection(url, username, password);
                System.out.println("Open connection: " + conn);
                return conn;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    // ...
}
// 调用者代码如下：
public class Main {
    public static void main(String[] args) {
        DataSource lazyDataSource = new LazyDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
        System.out.println("get lazy connection...");
        try (Connection conn1 = lazyDataSource.getConnection()) {
            // 并没有实际打开真正的 Connection
        }
        System.out.println("get lazy connection...");
        try (Connection conn2 = lazyDataSource.getConnection()) {
            try (PreparedStatement ps = conn2.prepareStatement("SELECT * FROM students")) { // 打开了真正的 Connection
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("name"));
                    }
                }
            }
        }
    }
}



/*
代理模式通过封装一个已有接口，并向调用方返回相同的接口类型，能让调用方在不改变任何代码的前提下增强某些功能（例如，鉴权、延迟加载、连接池复用等）。

使用 Proxy 模式要求调用方持有接口，作为 Proxy 的类也必须实现相同的接口类型。
*/



// 使用连接池的时候，更希望能重复使用连接。如果调用方编写这样的代码：
public class Main {
    public static void main(String[] args) {
        DataSource pooledDataSource = new PooledDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
        try (Connection conn = pooledDataSource.getConnection()) {
        }
        try (Connection conn = pooledDataSource.getConnection()) {
            // 获取到的是同一个Connection
        }
        try (Connection conn = pooledDataSource.getConnection()) {
            // 获取到的是同一个Connection
        }
    }
}
// 调用方并不关心是否复用了 Connection，但从 PooledDataSource 获取的 Connection 确实自带这个优化功能。如何实现可复用 Connection 的连接池？答案仍然是使用代理模式。

public class PooledConnectionProxy extends AbstractConnectionProxy {
    // 实际的Connection:
    Connection target;
    // 空闲队列:
    Queue<PooledConnectionProxy> idleQueue;

    public PooledConnectionProxy(Queue<PooledConnectionProxy> idleQueue, Connection target) {
        this.idleQueue = idleQueue;
        this.target = target;
    }

    public void close() throws SQLException {
        System.out.println("Fake close and released to idle queue for future reuse: " + target);
        // 并没有调用实际 Connection 的 close() 方法,
        // 而是把自己放入空闲队列:
        idleQueue.offer(this);
    }

    protected Connection getRealConnection() {
        return target;
    }
}
// 复用连接的关键在于覆写 close() 方法，它并没有真正关闭底层 JDBC 连接，而是把自己放回一个空闲队列，以便下次使用。

// 空闲队列由 PooledDataSource 负责维护
public class PooledDataSource implements DataSource {
    private String url;
    private String username;
    private String password;

    // 维护一个空闲队列:
    private Queue<PooledConnectionProxy> idleQueue = new ArrayBlockingQueue<>(100);

    public PooledDataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        // 首先试图获取一个空闲连接:
        PooledConnectionProxy conn = idleQueue.poll();
        if (conn == null) {
            // 没有空闲连接时，打开一个新连接:
            conn = openNewConnection();
        } else {
            System.out.println("Return pooled connection: " + conn.target);
        }
        return conn;
    }

    private PooledConnectionProxy openNewConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Open new connection: " + conn);
        return new PooledConnectionProxy(idleQueue, conn);
    }
    // ...
}

