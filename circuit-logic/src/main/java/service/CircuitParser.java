package service;

import model.CircuitNode;
import model.components.CircuitElm;

import java.io.File;
import java.util.List;

/**
 * Created by Jennifer on 10/12/2016.
 */
public interface CircuitParser {

    /**
     * Parses pseudo-spice txt file into nodes and elements
     * @param f file to parse
     * @modifies nodes to contain all circuit nodes for Modified Nodal Analysis
     * @modifies elements to contain all circuit elements
     */
    public void parse(File f);

    /**
     *
     * @return list of elements parsed from file
     */
    public List<CircuitElm> getElements();
}
