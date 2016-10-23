package com.circuitsolver.io;

import com.circuitsolver.model.components.CircuitElm;

import java.io.File;
import java.util.List;

/**
 * Created by Jennifer on 10/12/2016.
 */
public interface CircuitReader {

    /**
     * Parses pseudo-spice txt file into nodes and elements
     * @param f file to parse
     * @return list of CircuitElm parsed from file
     * @throws InvalidCircuitFileException if file doesn't contain a valid circuit according to circuit file specs
     */
    public List<CircuitElm> read(File f) throws InvalidCircuitFileException;

}
