// wrong example
public class Shape {
    abstract public float calculateVolumn();
    abstract public float calculateArea();
}

// wrong: 子类 Rectangle 无法实现 calculateVolumn 方法，所以无法替换父类 Shape
public class Rectangle extends Shape {
    // ...
}

// wrong: 子类 Cube 无法实现 calculateArea 方法，所以无法替换父类 Shape
public class Cube extends Shape {
    // ...
}



// correct example
public class Human {
    abstract public void speak();
    abstract public void run();
}

// correct: 子类可完全实现父类
public class Asian extends Human {
    // ...
}

// correct: 子类可完全实现父类
public class African extends Human {
    // ...
}
