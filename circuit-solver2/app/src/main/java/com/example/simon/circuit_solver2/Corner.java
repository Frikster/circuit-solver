package com.example.simon.circuit_solver2;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Simon on 24.10.2016.
 */

public class Corner extends Element {
    //n = north, w = west, s = south, e = east
    public Set<Character> exploredDirections;
    public Corner(double x, double y){
        super(x,y);
        exploredDirections = new HashSet<>();
    }
    public void setNewDirection(char dir){
        exploredDirections.add(dir);
    }
}
