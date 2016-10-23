package com.circuitsolver.model.components;

import com.circuitsolver.model.CircuitNode;

import java.awt.*;

/**
 * Created by Jennifer on 10/10/2016.
 */
public abstract class CircuitElm {

    protected CircuitNode n1, n2;
    protected Point p1, p2;

    public CircuitElm(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Initializes circuitNodes
     */
    public void initNodes(CircuitNode n1, CircuitNode n2){
        this.n1 = n1;
        this.n2 = n2;
    }

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
    public abstract String getType();

    /*    Getters and Setters     */

    public CircuitNode getN1() {
        return n1;
    }

    public void setN1(CircuitNode n1) {
        this.n1 = n1;
    }

    public CircuitNode getN2() {
        return n2;
    }

    public void setN2(CircuitNode n2) {
        this.n2 = n2;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

}
