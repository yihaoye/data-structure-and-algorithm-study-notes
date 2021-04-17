// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319636041762
/*
定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。

模板模式（Template Pattern）是一个比较简单的模式。它的主要思想是，定义一个操作的一系列步骤，对于某些暂时确定不下来的步骤，就留给子类去实现好了，这样不同的子类就可以定义出不同的步骤。

因此，模板模式的核心在于定义一个“骨架”。
*/



// 假设开发了一个从数据库读取设置的类：
// 由于从数据库读取数据较慢，可以考虑把读取的设置缓存起来，这样下一次读取同样的 key 就不必再访问数据库了。但是怎么实现缓存，暂时没想好，但不妨碍先写出使用缓存的代码：
// 但是，lookupCache(key) 和 putIntoCache(key, value) 这两个方法还没确定如何实现（比如没有足够的 context），因此也就无法编译通过，但是可以通过声明为抽象方法解决这个问题：
public abstract class AbstractSetting {
    public final String getSetting(String key) {
        String value = lookupCache(key); // 先从缓存读取
        if (value == null) {
            value = readFromDatabase(key); // 在缓存中未找到, 从数据库读取
            System.out.println("[DEBUG] load from db: " + key + " = " + value);
            putIntoCache(key, value); // 放入缓存
        } else {
            System.out.println("[DEBUG] load from cache: " + key + " = " + value);
        }
        return value;
    }

	private String readFromDatabase(String key) {
        // TODO: 从数据库读取
    }

    protected abstract String lookupCache(String key);

    protected abstract void putIntoCache(String key, String value);
}
// 因为声明了抽象方法，自然整个类也必须是抽象类。
// 如何实现 lookupCache(key) 和 putIntoCache(key, value) 这两个方法就交给子类了。
// 子类其实并不关心核心代码 getSetting(key) 的逻辑，它只需要关心如何完成两个小小的子任务就可以了。

// 假设希望用一个 Map 做缓存，那么可以写一个 LocalSetting：
public class LocalSetting extends AbstractSetting {
    private Map<String, String> cache = new HashMap<>();

    protected String lookupCache(String key) {
        return cache.get(key);
    }

    protected void putIntoCache(String key, String value) {
        cache.put(key, value);
    }
}

// 如果要使用 Redis 做缓存，那么可以再写一个 RedisSetting：
public class RedisSetting extends AbstractSetting {
    private RedisClient client = RedisClient.create("redis://localhost:6379");

    protected String lookupCache(String key) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            return commands.get(key);
        }
    }

    protected void putIntoCache(String key, String value) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            commands.set(key, value);
        }
    }
}

// 客户端代码使用本地缓存的代码这么写：
AbstractSetting setting1 = new LocalSetting();
System.out.println("test = " + setting1.getSetting("test"));
System.out.println("test = " + setting1.getSetting("test"));
// 要改成 Redis 缓存，只需要把 LocalSetting 替换为 RedisSetting


/*
模板方法的核心思想是：父类定义骨架，子类实现某些细节。

为了防止子类重写父类的骨架方法，可以在父类中对骨架方法使用 final。对于需要子类实现的抽象方法，一般声明为 protected，使得这些方法对外部客户端不可见。

Java 标准库也有很多模板方法的应用，比如一些集合类中都定义了很多通用操作，子类只需要实现某些必要方法。

模板方法是一种高层定义骨架，底层实现细节的设计模式，适用于流程固定，但某些步骤不确定或可替换的情况。
*/
