https://leetcode.com/discuss/interview-question/system-design/542455/Amazon-On-site-Systems-Design  
  
## Solution architecture design
Q: How would you design a pub-sub architecture and how would you scale it?  
A: AWS SQS or Kafka or other message queue/broker, web server (EC2 or ECS container or Lambda) as publisher and consumer.  
![](https://d1.awsstatic.com/product-marketing/Messaging/sns_img_topic.e024462ec88e79ed63d690a2eed6e050e33fb36f.png)  
![](https://d2908q01vomqb2.cloudfront.net/0716d9708d321ffb6a00818614779e779925365c/2017/03/28/Diagram2.png)  
  
Q: Follow up let's say there is a sudden spike in traffic how would your design handle such cases?  
A: Auto Scaling add publisher for handle income traffic, integrate Auto Scaling Group with SQS, when message in SQS spike add consumer to consume SQS message faster, for Kafka can add Kafka's partition for throughput, add broker per cluster for high availability.  
  
  
  
## Pub-Sub design pattern
https://medium.com/easyread/create-your-own-java-pubsub-library-fbee21d0bb44  
* Channel: represent the idea of an invokable collection of subscribers.
* Subscriber: represent the receiver of the message.
* Publisher: represent the sender of the message.  
```java
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

public class Event {
    static {
        init();
    }

    static Operation operation;

    static ConcurrentHashMap<String, ConcurrentHashMap<Integer, WeakReference<Object>>> channels;

    static void init() {
        channels = new ConcurrentHashMap<>();
        operation = new Operation();
    }
}
```

```java
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Operation extends Event {
    void subscribe(String channelName, Object subscriber) {
        if (!channels.containsKey(channelName)) {
            channels.put(channelName, new ConcurrentHashMap<>());
        }

        channels.get(channelName).put(subscriber.hashCode(), new WeakReference<>(subscriber));
    }

    void publish(String channelName, Post message) {
        for(Map.Entry<Integer, WeakReference<Object>> subs : channels.get(channelName).entrySet()) {
            WeakReference<Object> subscriberRef = subs.getValue();

            Object subscriberObj = subscriberRef.get();

            for (final Method method : subscriberObj.getClass().getDeclaredMethods()) {
                Annotation annotation = method.getAnnotation(OnMessage.class);
                if (annotation != null) {
                    deliverMessage(subscriberObj, method, message);
                }
            }
        }
    }

    <T, P extends Post> boolean deliverMessage(T subscriber, Method method, Post message) {
        try {
            boolean methodFound = false;
            for (final Class paramClass : method.getParameterTypes()) {
                if (paramClass.equals(message.getClass())) {
                    methodFound = true;
                    break;
                }
            }
            if (methodFound) {
                method.setAccessible(true);
                method.invoke(subscriber, message);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
```

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnMessage {
}
```

```java
abstract class Post {
    String message;

    Post(String message) {
        this.message = message;
    }
}

class Subscriber {
    int id;
    public Subscriber(int id) {
        this.id = id;
    }

    @OnMessage
    private void onMessage(Message message) {
        System.out.println(message.message);
    }
}

class Message extends Post {
    String message;

    public Message(String message) {
        super(message);
    }
}
```

```java
Subscriber subscriber = new Subscriber(1);
Subscriber subscriber2 = new Subscriber(2);

Subscriber subscriber3 = new Subscriber(3);
Subscriber subscriber4 = new Subscriber(4);


Event.operation.subscribe("action#create", subscriber);
Event.operation.subscribe("action#create", subscriber2);

Event.operation.subscribe("action#update", subscriber3);
Event.operation.subscribe("action#delete", subscriber4);


Message message = new Message("Create Action");
Message message2 = new Message("Update Action");

Event.operation.publish("action#create", message);
Event.operation.publish("action#update", message2);
```
  
![](https://miro.medium.com/max/942/1*yL442HcOzp72L6hBrEIYFA.png)  
![](https://miro.medium.com/max/942/1*C4ZfsoZmAFIa0uL0Ig3Nag.png)  
![](https://miro.medium.com/max/1400/1*cnlIFMEjaeIG-9_QK0JQcg.png)  
  

### More details to be concern
Multithreading and Message Queue.  
