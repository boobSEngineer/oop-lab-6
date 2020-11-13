package com.eltech.shapes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeSaver implements ShapeAccumulator.OnChangeListener {
    private final ShapeAccumulator accumulator;
    private final File file;

    private boolean isAttached = true;

    public ShapeSaver(ShapeAccumulator accumulator, File file) {
        this.accumulator = accumulator;
        this.file = file;
        accumulator.addListener(this);
    }

    private boolean isWorking = false;

    private void assertAttached() {
        if (!isAttached) {
            throw new IllegalStateException("ShapeSaver was detached");
        }
    }

    public synchronized boolean read() {
        assertAttached();
        isWorking = true;
        if (!file.exists()) {
            accumulator.clear();
            isWorking = false;
            return true;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            //noinspection unchecked
            List<Shape> shapes = (List<Shape>) inputStream.readObject();
            accumulator.clear();
            accumulator.addAll(shapes);
            isWorking = false;
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException exception) {
            exception.printStackTrace();
            isWorking = false;
            return false;
        }
    }

    public synchronized boolean save() {
        assertAttached();
        isWorking = true;
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(new ArrayList<>(accumulator.getShapes()));
            isWorking = false;
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            isWorking = false;
            return false;
        }
    }

    @Override
    public synchronized void onChange() {
        if (!isWorking) {
            save();
        }
    }

    public synchronized void detach() {
        assertAttached();
        accumulator.removeListener(this);
        isAttached = false;
    }
}
