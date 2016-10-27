package com.circuitsolver.model.components;


import com.circuitsolver.util.Constants;

import java.awt.*;

/**
 * Created by Jennifer on 10/12/2016.
 */
public class ResistorElm extends CircuitElm {

    private double resistance;

    public ResistorElm(Point p1, Point p2, double resistance){
        super(p1, p2);
        this.resistance = resistance;
    }

    public double getVoltageDiff() {
        return 0;
    }

    public double calculateCurrent() {
        return 0;
    }

    public void setValue(double value) {
        this.resistance = value;
    }

    @Override
    public String getType() {
        return Constants.RESISTOR;
    }

}
