package com.circuitsolver.model.components;

/**
 * Created by Jennifer on 10/12/2016.
 */
public class VoltageElm extends CircuitElm {

    private double voltage;

    public VoltageElm(double voltage){
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
        return null;
    }

}
