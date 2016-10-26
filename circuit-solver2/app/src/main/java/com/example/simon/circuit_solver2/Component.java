package com.example.simon.circuit_solver2;

/**
 * Created by Simon on 24.10.2016.
 */

public class Component extends Element {
    protected String type;
    public Component(double x, double y, String type){
        super(x,y);
        this.type = type;
    }
}
