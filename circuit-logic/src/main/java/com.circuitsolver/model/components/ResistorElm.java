package com.circuitsolver.model.components;


/**
 * Created by Jennifer on 10/12/2016.
 */
public class ResistorElm extends CircuitElm {

    private double resistance;

    public ResistorElm(double resistance){
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
        return null;
    }

}
