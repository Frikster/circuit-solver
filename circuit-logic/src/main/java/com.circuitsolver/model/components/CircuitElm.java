package model.components;

import model.CircuitNode;
import static util.Constants.*;


import java.awt.*;

/**
 * Created by Jennifer on 10/10/2016.
 */
public abstract class CircuitElm {

    protected CircuitNode[] nodes; //nodes that element is connected to
    protected Point[] points; //coordinates of either end of the component

    /**
     * Find the voltage difference across the element
     * @return
     */
    public abstract double getVoltageDiff();

    /**
     * Find the current flowing through the element
     * @return
     */
    public abstract double calculateCurrent();

    /**
     * Sets the value of the element (resistance for ResistorElm, voltage for VoltageElm, etc...)
     * @param value
     */
    public abstract void setValue(double value);

    /**
     * Returns element type
     * @return element type (resistor, wire, etc.)
     */
    public abstract ElmType getType();

}
