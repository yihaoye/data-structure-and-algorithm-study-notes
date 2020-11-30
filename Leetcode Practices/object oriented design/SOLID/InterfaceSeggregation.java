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
public class Shape2D {
    abstract public float calculateArea();
}

public class Shape3D {
    abstract public float calculateVolumn();
}

public class Rectangle extends Shape2D {
    // ...
}

public class Cube extends Shape3D {
    // ...
}



// correct example
public interface Shape2D {
    public float calculateArea();
}

public interface Shape3D {
    public float calculateVolumn();
}

public class Rectangle implements Shape2D {
    // ...
}

public class Cube implements Shape3D {
    // ...
}
