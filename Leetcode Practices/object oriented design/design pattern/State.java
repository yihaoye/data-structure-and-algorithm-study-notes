// https://www.liaoxuefeng.com/wiki/1252599548343744/1281319592001569
// https://refactoringguru.cn/design-patterns/state
/*
状态模式能在一个对象的内部状态变化时改变其行为，使其看上去就像改变了自身所属的类一样。状态模式与有限状态机的概念紧密相关。

允许一个对象在其内部状态改变时改变它的行为。对象看起来似乎修改了它的类。

状态模式（State）经常用在带有状态的对象中。
如何表示状态？定义一个 enum 就可以表示不同的状态。但不同的状态需要对应不同的行为，比如收到消息时：
if (state == ONLINE) {
    // ...
} else if (state == BUSY) {
    reply("busy, sorry");
} else {
    // ...
}
状态模式的目的是为了把上述一大串 if...else... 的逻辑给分拆到不同的状态类中，使得将来增加状态比较容易。
*/
public class DisconnectedState implements State {
    public String init() {
        return "Bye!";
    }

    public String reply(String input) {
        return "";
    }
}

public class ConnectedState implements State {
    public String init() {
        return "Hello, I'm Bob.";
    }

    public String reply(String input) {
        if (input.endsWith("?")) {
            return "Yes. " + input.substring(0, input.length() - 1) + "!";
        }
        if (input.endsWith(".")) {
            return input.substring(0, input.length() - 1) + "!";
        }
        return input.substring(0, input.length() - 1) + "?";
    }
}

public class BotContext { // 状态模式的关键设计思想在于状态切换，引入一个 BotContext 完成状态切换
	private State state = new DisconnectedState();

	public String chat(String input) {
		if ("hello".equalsIgnoreCase(input)) {
            // 收到 hello 切换到在线状态:
			state = new ConnectedState();
			return state.init();
		} else if ("bye".equalsIgnoreCase(input)) {
            // 收到 bye 切换到离线状态:
			state = new DisconnectedState();
			return state.init();
		}
		return state.reply(input);
	}
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BotContext bot = new BotContext();
        for (;;) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String output = bot.chat(input);
            System.out.println(output.isEmpty() ? "(no reply)" : "< " + output);
        }
    }
}
/*
状态模式的设计思想是把不同状态的逻辑分离到不同的状态类中，从而使得增加新状态更容易；

状态模式的实现关键在于状态转换。简单的状态转换可以直接由调用方指定，复杂的状态转换可以在内部根据条件触发完成。
*/
