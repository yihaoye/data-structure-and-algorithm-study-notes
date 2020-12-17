// 摘录：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319577321505
/*
定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。

观察者模式（Observer）又称发布-订阅模式（Publish-Subscribe：Pub/Sub）。
它是一种通知机制，让发送通知的一方（被观察方）和接收通知的一方（观察者）能彼此分离，互不影响。
*/

// 假设一个电商网站，有多种Product（商品），同时，Customer（消费者）和Admin（管理员）对商品上架、价格改变都感兴趣，希望能第一时间获得通知。
// 于是，Store（商场）可以这么写：
public class Store {
    private List<ProductObserver> observers = new ArrayList<>();
    private Map<String, Product> products = new HashMap<>();

    // 注册观察者:
    public void addObserver(ProductObserver observer) {
        this.observers.add(observer);
    }

    // 取消注册:
    public void removeObserver(ProductObserver observer) {
        this.observers.remove(observer);
    }

    public void addNewProduct(String name, double price) {
        Product p = new Product(name, price);
        products.put(p.getName(), p);
        // 通知观察者:
        observers.forEach(o -> o.onPublished(p));
        // 各个观察者是依次获得的同步通知，如果上一个观察者处理太慢，会导致下一个观察者不能及时获得通知。
        // 此外，如果观察者在处理通知的时候，发生了异常，还需要被观察者处理异常，才能保证继续通知下一个观察者。
        // 改进：改成异步通知，使得所有观察者可以并发同时处理
    }

    public void setProductPrice(String name, double price) {
        Product p = products.get(name);
        p.setPrice(price);
        // 通知观察者:
        observers.forEach(o -> o.onPriceChanged(p));
    }
}

// 观察者的定义可以放到客户端:
// observer:
Admin a = new Admin();
Customer c = new Customer();
// store:
Store store = new Store();
// 注册观察者:
store.addObserver(a);
store.addObserver(c);

// 甚至可以注册匿名观察者：
store.addObserver(new ProductObserver() {
    public void onPublished(Product product) {
        System.out.println("[Log] on product published: " + product);
    }

    public void onPriceChanged(Product product) {
        System.out.println("[Log] on product price changed: " + product);
    }
});
/*
ProductObserver 是一个接口，类 Admin 和类 Customer 应实现这个接口

┌─────────┐      ┌───────────────┐
│  Store  │─ ─ ─>│ProductObserver│
└─────────┘      └───────────────┘
     │                   ▲
                         │
     │             ┌─────┴─────┐
     ▼             │           │
┌─────────┐   ┌─────────┐ ┌─────────┐
│ Product │   │  Admin  │ │Customer │ ...
└─────────┘   └─────────┘ └─────────┘
*/



// 有些观察者模式把通知变成一个Event对象，从而不再有多种方法通知，而是统一成一种：
public interface ProductObserver {
    void onEvent(ProductEvent event);
}
// 让观察者自己从Event对象中读取通知类型和通知数据。



/*
广义的观察者模式包括所有消息系统。所谓消息系统，就是把观察者和被观察者完全分离，通过消息系统本身来通知：

                 ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
                   Messaging System
                 │                       │
                   ┌──────────────────┐
              ┌──┼>│Topic:newProduct  │──┼─┐    ┌─────────┐
              │    └──────────────────┘    ├───>│ConsumerA│
┌─────────┐   │  │ ┌──────────────────┐  │ │    └─────────┘
│Producer │───┼───>│Topic:priceChanged│────┘
└─────────┘   │  │ └──────────────────┘  │
              │    ┌──────────────────┐         ┌─────────┐
              └──┼>│Topic:soldOut     │──┼─────>│ConsumerB│
                   └──────────────────┘         └─────────┘
                 └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
消息发送方称为Producer，消息接收方称为Consumer，Producer发送消息的时候，必须选择发送到哪个Topic。Consumer可以订阅自己感兴趣的Topic，从而只获得特定类型的消息。

使用消息系统实现观察者模式时，Producer和Consumer甚至经常不在同一台机器上，并且双方对对方完全一无所知，因为注册观察者这个动作本身都在消息系统中完成，而不是在Producer内部完成。
*/
