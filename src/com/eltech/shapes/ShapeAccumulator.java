package com.eltech.shapes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShapeAccumulator {
    public interface OnChangeListener {
        void onChange();
    }

    private final List<Shape> shapes = new ArrayList<>();
    private final List<OnChangeListener> changeListeners = new ArrayList<>();

    public List<Shape> getShapes() {
        return shapes;
    }

    public void clear() {
        shapes.clear();
    }

    public void add(Shape shape) {
        shapes.add(shape);
        onChange();
    }

    public void remove(Shape shape) {
        shapes.remove(shape);
        onChange();
    }

    public void addAll(Collection<? extends Shape> shapes) {
        this.shapes.addAll(shapes);
        onChange();
    }

    private void onChange() {
        for (OnChangeListener listener : changeListeners) {
            listener.onChange();
        }
    }

    public void addListener(OnChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeListener(OnChangeListener listener) {
        changeListeners.remove(listener);
    }

    public Shape getMinAreaShape() {
        Shape result = null;
        for (Shape shape : shapes) {
            if (result == null || result.calcArea() > shape.calcArea()) {
                result = shape;
            }
        }
        return result;
    }

    public Shape getMaxAreaShape() {
        Shape result = null;
        for (Shape shape : shapes) {
            if (result == null || result.calcArea() < shape.calcArea()) {
                result = shape;
            }
        }
        return result;
    }

    public Shape getMinPerimeterShape() {
        Shape result = null;
        for (Shape shape : shapes) {
            if (result == null || result.calcPerimeter() > shape.calcPerimeter()) {
                result = shape;
            }
        }
        return result;
    }

    public Shape getMaxPerimeterShape() {
        Shape result = null;
        for (Shape shape : shapes) {
            if (result == null || result.calcPerimeter() < shape.calcPerimeter()) {
                result = shape;
            }
        }
        return result;
    }

    public double getTotalArea() {
        double result = 0;
        for (Shape shape : shapes) {
            result += shape.calcArea();
        }
        return result;
    }

    public double getTotalPerimeter() {
        double result = 0;
        for (Shape shape : shapes) {
            result += shape.calcPerimeter();
        }
        return result;
    }
}
