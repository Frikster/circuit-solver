package service;

import model.*;
import model.components.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jennifer on 10/10/2016.
 *
 * Takes the pseudo-spice txt file representing circuit interpreted from opencv and parses it into Circuit elements
 */
public class PseudoSpiceParser implements CircuitParser{

    private List<CircuitElm> elements;

    public void parse(File f){
        List<Point> points = new ArrayList<Point>(); //tracks all points (coordinates where components intersect)
        //For each line...
        //CircuitElm elm = parseLineToElm(line)
        //elements.add(elm)
        //Add any new points discovered
        //The negative terminal of the first V source discovered is ground node
        //
        //parseNodes()
        //TODO
    }

    public List<CircuitElm> getElements(){
        return null; //TODO
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
