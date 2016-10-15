package service;

import model.components.ResistorElm;
import org.junit.*;
import service.*;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by jen on 2016-10-13.
 */
public class PseudoSpiceParserTest {

    CircuitParser parser;

    @Before
    public void setup(){
        parser = new PseudoSpiceParser();
    }

    @Test
    public void testGetElements_TwoNodeCircuit(){
        parser.parse(new File("twoNodeCircuit.txt"));
        assertEquals(2, parser.getElements().size());
        fail("not implemented");

    }

    @Test
    public void testGetElements_ThreeNodeCircuit(){
        fail("not implemented");

    }

    @Test
    public void testGetElements_FourNodeCircuit(){
        fail("not implemented");

    }

    private int countElementsOfType(){
        return 0; //TODO: implement this method
    }
}
