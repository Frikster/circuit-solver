package model;

import model.components.CircuitElm;
import java.util.List;

/**
 * Created by jen on 2016-10-13.
 */
public interface Circuit {

    /**
     * Replaces given element, "oldElm", with new element, "newElm".
     * New element has same coordinates as old element.
     * @param oldElm element to be replaced
     * @param newElm element that will replace old element
     *               requires: element-specific values already intialized in newElm
     */
    public void editElement(CircuitElm oldElm, CircuitElm newElm);

    /**
     * Modifies specified circuit element's value
     * @param elm
     * @param value
     */
    public void editElementValue(CircuitElm elm, double value);

    /**
     * Returns voltage difference across element
     * TODO: Do we return relative voltage for wire element? It might be useful to return voltage of nodes?
     * @param elm
     * @return
     */
    public double getVoltageDiff(CircuitElm elm);

    /**
     * Returns current running through element
     * @param elm
     * @return
     */
    public double getCurrent(CircuitElm elm);
}
