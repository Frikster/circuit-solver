package model;

/**
 * Created by Jennifer on 10/10/2016.
 */
public class CircuitNode {

    private boolean isValidVoltage;
    private double voltage;

    public CircuitNode(){
        isValidVoltage = false;
    }

    public CircuitNode(double voltage){
        isValidVoltage = true;
        this.voltage = voltage;
    }

    public double getVoltage(){
        return voltage;
    }

    public void setVoltage(double voltage){
        isValidVoltage = true;
        this.voltage = voltage;
    }
}
