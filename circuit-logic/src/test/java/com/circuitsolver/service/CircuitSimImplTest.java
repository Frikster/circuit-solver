package com.circuitsolver.service;

import com.circuitsolver.model.CircuitNode;
import com.circuitsolver.model.components.CircuitElm;
import com.circuitsolver.model.components.ResistorElm;
import com.circuitsolver.model.components.VoltageElm;
import com.circuitsolver.model.components.WireElm;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jennifer on 10/13/2016.
 */
public class CircuitSimImplTest {

    @Test
    public void testgetNodes(){
        //Doesn't correctly parse nodes thus far
        List<CircuitElm> elements = new ArrayList<CircuitElm>();
        elements.add(new ResistorElm(new Point(0, 10), new Point(10, 10), 10));
        elements.add(new VoltageElm(new Point(0, 0), new Point(10, 0), 10));
        elements.add(new WireElm(new Point(0,0), new Point(0, 10)));
        elements.add(new WireElm(new Point(10,10), new Point(10, 0)));



        CircuitSim circuitSim = new CircuitSim(elements);
        circuitSim.init();

        List<CircuitNode> result = circuitSim.getNodes();
        System.out.println(result);
        //Assert that nodes correspond to correct points
        //Node 1 should correspond to (0,0), (0,10)
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,10);
        CircuitNode cn = new CircuitNode();
        cn.addPoint(p1);
        cn.addPoint(p2);
        assertTrue(result.contains(cn));
        //Node 2 should correspond to (10,0), (10,10)
        p1 = new Point(10,0);
        p2 = new Point(10,10);
        cn = new CircuitNode();
        cn.addPoint(p1);
        cn.addPoint(p2);
        assertTrue("Expected: { (10, 0), (10, 10) }", result.contains(cn));



    }

    public void testGetCurrent_twoNodeCircuit_returnsResistorCurrent(){

    }


        @Test
    public void testGetCurrent_threeNodeCircuit_returnsVoltageCurrent(){
        fail("not implemented");
    }

    @Test
    public void testGetCurrent_sixNodeCircuit_returnsResistorCurrent(){
        fail("not implemented");
    }

    @Test
    public void testGetCurrent_sixNodeCircuit_returnsVoltageCurrent(){
        fail("not implemented");
    }


}
