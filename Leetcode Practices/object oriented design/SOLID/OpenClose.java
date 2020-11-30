// wrong example:
public class AreaCalculator {
    private float result;

    public float getResult() {
        return this.result;
    }

    // wrong part
    public float calculateArea(Triangle t) {
        this.result = t.h * t.b / 2;
    }

    // wrong part
    public float calculateArea(Rectangle r) {
        this.result = r.l * r.w;
    }
}



// correct example:
public class AreaCalculator {
    private float result;

    public float getResult() {
        return this.result;
    }

    public float calculateArea(Shape s) {
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

public class Rectangle implements Shape {
    private float l;
    private float w;
    
    public float getArea() {
        this.result = this.l * this.w;
    }
}
