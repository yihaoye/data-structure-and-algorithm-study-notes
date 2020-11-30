// wrong example:
public class AreaCalculator {
    private float result;
    private Triangle t; // wrong: AreaCalculator 是一个抽象实现/类，不应该依赖于一个具体实现/类 - 比如这里的 Triangle

    public float getResult() {
        return this.result;
    }

    public float calculateArea() {
        this.result = t.h * t.b / 2;
    }
}



// correct example:
public class AreaCalculator {
    private float result;

    public float getResult() {
        return this.result;
    }

    public float calculateArea(Shape s) { // correct: AreaCalculator 是一个抽象实现/类，应该依赖于一个抽象实现/类 - 比如这里的 Shape。
        this.result = s.getArea();
    }
}

public interface Shape {
    public float getArea();
}

public class Triangle implements Shape {
    private float h;
    private float b;

    public float getArea() {
        this.result = this.h * this.b / 2;
    }
}
