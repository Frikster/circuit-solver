package com.circuitsolver.io;

/**
 * Created by Jennifer on 10/22/2016.
 */
public class InvalidCircuitFileException extends Exception {

    public InvalidCircuitFileException(){
        super("Cannot read circuit file. Syntax is invalid");
    }

}
