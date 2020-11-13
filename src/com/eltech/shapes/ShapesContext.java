package com.eltech.shapes;

import java.io.File;

public class ShapesContext {
    private static final ShapesContext instance = new ShapesContext();

    public static ShapesContext getInstance() {
        return instance;
    }


    private final ShapeAccumulator shapeAccumulator = new ShapeAccumulator();
    private ShapeSaver shapeSaver = null;

    private ShapesContext() {

    }

    public ShapeAccumulator getShapeAccumulator() {
        return shapeAccumulator;
    }

    public ShapeSaver getShapeSaver() {
        return shapeSaver;
    }

    public void setSavesFile(File file) {
        if (shapeSaver != null) {
            shapeSaver.save();
            shapeSaver.detach();
            shapeSaver = null;
        }
        if (file != null) {
            shapeSaver = new ShapeSaver(shapeAccumulator, file);
            shapeSaver.read();
        }
    }
}
