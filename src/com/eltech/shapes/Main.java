package com.eltech.shapes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Triangle(2, 2, 3));
        shapes.add(new Triangle(4, 8, 6));
        shapes.add(new Rectangle(4, 4));
        shapes.add(new Circle(1));
        shapes.add(new Circle(2));

        double areaSum = 0;
        double perimeterSum = 0;
        for (Shape shape : shapes) {
            areaSum += shape.calcArea();
            perimeterSum += shape.calcPerimeter();
        }
        System.out.println("area sum: " + areaSum + ", perimeter sum: " + perimeterSum);

        shapes.sort(Comparator.comparingDouble(Shape::calcArea));
        Shape minAreaShape = shapes.get(0);
        Shape maxAreaShape = shapes.get(shapes.size() - 1);

        shapes.sort(Comparator.comparingDouble(Shape::calcPerimeter));
        Shape minPerimeterShape = shapes.get(0);
        Shape maxPerimeterShape = shapes.get(shapes.size() - 1);

        System.out.println("min area shape: " + minAreaShape + " with area " + minAreaShape.calcArea());
        System.out.println("max area shape: " + maxAreaShape + " with area " + maxAreaShape.calcArea());
        System.out.println("min perimeter shape: " + minPerimeterShape + " with perimeter " + minPerimeterShape.calcPerimeter());
        System.out.println("max perimeter shape: " + maxPerimeterShape + " with perimeter " + maxPerimeterShape.calcPerimeter());
    }
}
