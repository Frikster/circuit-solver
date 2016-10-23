package com.circuitsolver.io;

import com.circuitsolver.model.components.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jennifer on 10/10/2016.
 *
 * Takes the pseudo-spice txt file representing circuit interpreted from opencv and parses it into Circuit elements
 */
public class CircuitReaderTxtImpl implements CircuitReader {

    private List<CircuitElm> elements;

    @Override
    public List<CircuitElm> read(File f) throws InvalidCircuitFileException {
        return null;
    }

    //Constructs circuit element from line
    private CircuitElm parseLineToElm(String line){
        return null; //TODO
    }

    //TODO: rough method... redesign
//    private CircuitElm createElement(char c, double value){
//        if(c == 'r'){
//            return new ResistorElm(value);
//        }
//        else if(c == 'w'){
//            return new WireElm();
//        }
//        else if(c =='v'){
//            return new VoltageElm(value);
//        }
//        else{
//            throw new RuntimeException( c + " does not correspond to a circuit element");
//        }
//    }
}
