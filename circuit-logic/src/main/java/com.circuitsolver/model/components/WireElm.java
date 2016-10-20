package model.components;

import static util.Constants.*;

/**
 * Created by Jennifer on 10/12/2016.
 */
public class WireElm extends CircuitElm {

    public double getVoltageDiff() {
        //TODO: implement this method
        return 0;
    }

    public double calculateCurrent() {
        //TODO: implement this method
        return 0;
    }

    public void setValue(double value) {
        //TODO: throw an IllegalEditWireValueException
    }

    public ElmType getType() {
        return ElmType.WIRE;
    }
}
