package com.circuitsolver.model.components;

import com.circuitsolver.util.Constants;

import java.awt.*;

/**
 * Created by Jennifer on 10/12/2016.
 */
public class VoltageElm extends CircuitElm {

    private double voltage;

    public VoltageElm(Point p1, Point p2, double voltage){
        super(p1, p2);
        this.voltage = voltage;
    }

    public double getVoltageDiff() {
        return 0;
    }

    public double calculateCurrent() {
        return 0;
    }

    public void setValue(double value) {
        this.voltage = value;
    }

    @Override
    public String getType() {
        return Constants.DC_VOLTAGE;
    }

}
