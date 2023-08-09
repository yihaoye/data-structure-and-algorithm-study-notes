// 摘录：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319577321505
// https://refactoringguru.cn/design-patterns/observer
/*
观察者模式亦称：事件订阅者、监听者、Event-Subscriber、Listener、Observer，其允许定义一种订阅机制，可在对象事件发生时通知多个 “观察” 该对象的其他对象。

定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。

观察者模式（Observer）又称发布-订阅模式（Publish-Subscribe：Pub/Sub）。
它是一种通知机制，让发送通知的一方（被观察方）和接收通知的一方（观察者）能彼此分离，互不影响。
*/

// 以下 By ChatGPT
// 订阅者接口
interface Observer {
    void update(String message);
}

// 发布者接口
interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

// 具体订阅者实现
class ConcreteObserver implements Observer {
    private String name;
    
    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " 收到消息：" + message);
    }
}

// 具体发布者实现
class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }

    // 主题状态发生变化时，调用该方法通知所有订阅者
    public void changeState(String newState) {
        notifyObservers("主题状态变为：" + newState);
    }
}

public class Main {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();

        ConcreteObserver observer1 = new ConcreteObserver("观察者1");
        ConcreteObserver observer2 = new ConcreteObserver("观察者2");

        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.changeState("新状态1");
        subject.changeState("新状态2");

        subject.removeObserver(observer1);
        subject.changeState("新状态3");
    }
}



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
