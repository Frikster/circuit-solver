package com.circuitsolver.service;

import com.circuitsolver.model.CircuitNode;
import com.circuitsolver.model.components.CircuitElm;
import java.util.List;

/**
 * Created by jen on 2016-10-13.
 */
public class CircuitSim {

    private List<CircuitNode> nodes;
    private List<CircuitElm> elements;

    /**
     * Note that list of elements may be modified by CircuitSim instance in its other methods
     * @param elements
     */
    public CircuitSim(List<CircuitElm> elements){
        this.elements = elements;
    }

    /**
     * Initializes circuit nodes from elements
     */
    public void init(){

    }

    /**
     * Replaces given element, "oldElm", with new element, "newElm".
     * New element has same coordinates as old element.
     * @param oldElm element to be replaced
     * @param newElm element that will replace old element
     *               requires: element-specific values already intialized in newElm
     */
    public void editElement(CircuitElm oldElm, CircuitElm newElm){
        //TODO: implement this method
    }

    /**
     * Modifies specified circuit element's value
     * @param elm
     * @param value
     */
    public void editElementValue(CircuitElm elm, double value){
        //TODO: implement this method

    }

    /**
     * Returns voltage difference across element
     * TODO: Do we return relative voltage for wire element? It might be useful to return voltage of nodes?
     * @param elm
     * @return
     */
    public double getVoltageDiff(CircuitElm elm){
        //TODO: implement this method

        return 0;
    }

    /**
     * Returns current running through element
     * @param elm
     * @return
     */
    public double getCurrent(CircuitElm elm){
        //TODO: implement this method

        return 0;
    }
}
