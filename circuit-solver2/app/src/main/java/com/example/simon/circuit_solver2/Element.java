package com.example.simon.circuit_solver2;

/**
 * Created by Simon on 24.10.2016.
 */

public abstract class Element {
    protected double positionX;
    protected double positionY;

    public Element(double x, double y){
        positionX = x;
        positionY = y;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Element)) return false;
        else{
            return ((Element)o).positionY == positionY && ((Element)o).positionX == positionX;
        }
    }
}
