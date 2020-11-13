package com.eltech.ui;

import com.eltech.shapes.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private final ShapesContext shapesContext = ShapesContext.getInstance();

    @FXML
    private ListView<Shape> itemListView;

    public void initialize() {
        updateShapeList();
        shapesContext.getShapeAccumulator().addListener(this::updateShapeList);
    }

    private void updateShapeList() {
        List<Shape> shapeList = shapesContext.getShapeAccumulator().getShapes();
        itemListView.setItems(FXCollections.observableList(shapeList));
    }

    public void deleteSelectedShape() {
        ShapeAccumulator shapeAccumulator = shapesContext.getShapeAccumulator();
        List<Shape> shapeList = shapeAccumulator.getShapes();
        int deleteIndex = itemListView.getSelectionModel().getSelectedIndex();
        if (deleteIndex >= 0 && deleteIndex < shapeList.size()) {
            shapeAccumulator.remove(shapeList.get(deleteIndex));
            if (shapeList.size() >= deleteIndex) {
                itemListView.getSelectionModel().selectIndices(deleteIndex);
            }
        }
    }

    public void addRandomShape() {
        ShapeAccumulator shapes = shapesContext.getShapeAccumulator();
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                int side1 = random.nextInt(4) + 1;
                int side2 = random.nextInt(4) + 1;
                shapes.add(new Triangle(side1, side2, side1 + side2 - 1));
                break;
            case 1:
                shapes.add(new Circle(random.nextInt(4) + 1));
                break;
            case 2:
                shapes.add(new Rectangle(random.nextInt(4) + 1, random.nextInt(4) + 1));
                break;
        }
    }


    private List<Double> parseStringToDoubles(String data, int count) {
        StringBuilder patternBuilder = new StringBuilder("[\\s,]*");
        for (int i = 0; i < count; i++) {
            patternBuilder.append("(-?[0-9]+(.[0-9]+)?)");
            if (i < count - 1) {
                patternBuilder.append("[\\s,]+");
            }
        }
        patternBuilder.append("[\\s,]*");

        Matcher matcher = Pattern.compile(patternBuilder.toString()).matcher(data);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("you must input " + count + " numbers");
        }
        List<Double> result = new ArrayList<>();
        for (int i = 1; i < matcher.groupCount(); i += 2) {
            String number = matcher.group(i);
            try {
                result.add(Double.valueOf(number));
            } catch (NumberFormatException e) {
                 throw new IllegalArgumentException("not a number " + number);
            }
        }
        return result;
    }

    public void addTriangle() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add triangle");
        dialog.setContentText("Triangle Sides: ");
        dialog.showAndWait().ifPresent(data -> {
            try {
                List<Double> sides = parseStringToDoubles(data, 3);
                shapesContext.getShapeAccumulator().add(new Triangle(sides.get(0), sides.get(1), sides.get(2)));
            } catch (IllegalArgumentException err) {
                reportError("Failed to add triangle: " + err.getMessage());
            }
        });
    }

    public void addCircle() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Circle");
        dialog.setContentText("Circle Sides: ");
        dialog.showAndWait().ifPresent(data -> {
            try {
                List<Double> sides = parseStringToDoubles(data, 1);
                shapesContext.getShapeAccumulator().add(new Circle(sides.get(0)));
            } catch (IllegalArgumentException err) {
                reportError("Failed to add circle: " + err.getMessage());
            }
        });
    }

    public void addRectangle() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Rectangle");
        dialog.setContentText("Rectangle Sides: ");
        dialog.showAndWait().ifPresent(data -> {
            try {
                List<Double> sides = parseStringToDoubles(data, 2);
                shapesContext.getShapeAccumulator().add(new Rectangle(sides.get(0), sides.get(1)));
            } catch (IllegalArgumentException err) {
                reportError("Failed to add rectangle: " + err.getMessage());
            }
        });
    }

    private void reportError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.show();
    }
}
