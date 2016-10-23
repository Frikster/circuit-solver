package com.circuitsolver.io;

import com.circuitsolver.model.components.CircuitElm;

import java.io.File;
import java.util.List;

/**
 * Created by Jennifer on 10/22/2016.
 */
public interface CircuitWriter {

    /**
     * Write the list of elements back into a file according to circuit file specs
     * @param f file to write to
     * @param elements list of circuit elements to write to file
     */
    public void write(File f, List<CircuitElm> elements);
}
