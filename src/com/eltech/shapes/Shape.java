package com.eltech.shapes;

import java.io.Serializable;

public interface Shape extends Serializable {
    double calcArea();
    double calcPerimeter();
}
