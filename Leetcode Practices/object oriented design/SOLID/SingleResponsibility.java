// wrong example:
public class AreaCalculator {
    private float result;

    public float getResult() {
        return this.result;
    }

    public float calculateArea(Shape s) {
        this.result = s.getArea();
    }

    // wrong part
    public void printResultInJson() {
        jsonPrinter.initialize();
        jsonPrinter.print(this.result);
        jsonPrinter.close();
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

public class Printer {
    public printResultInJson(float number) {
        jsonPrinter.initialize();
        jsonPrinter.print(number);
        jsonPrinter.close();
    }
}
