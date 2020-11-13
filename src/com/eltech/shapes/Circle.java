package com.eltech.shapes;

public class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("invalid circle radius: " + radius);
        }
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }

    @Override
    public double calcArea() {
        return radius * radius * Math.PI;
    }

    @Override
    public double calcPerimeter() {
        return radius * Math.PI * 2;
    }
}
