package com.eltech.shapes;

public class Rectangle implements Shape {
    private final double side1, side2;

    public Rectangle(double side1, double side2) {
        if (side1 <= 0 || side2 <= 0) {
            throw new IllegalArgumentException(String.format("invalid rectangle sides: %f, %f", side1, side2));
        }
        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    public String toString() {
        return "Rectangle{" + side1 + ", " + side2 + '}';
    }

    @Override
    public double calcArea() {
        return side1 * side2;
    }

    @Override
    public double calcPerimeter() {
        return (side1 + side2) * 2;
    }
}
