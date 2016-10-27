package com.circuitsolver.model.components;

import com.circuitsolver.model.CircuitNode;

import java.awt.*;

/**
 * Created by Jennifer on 10/10/2016.
 */
public abstract class CircuitElm {

    //protected CircuitNode[] nodes;
    protected CircuitNode n1;
    protected CircuitNode n2;
    protected Point[] posts;

    public CircuitElm(Point p1, Point p2){
        posts = new Point[2];
        //nodes = new CircuitNode[2];
        posts[0] = p1;
        posts[1] = p2;
    }

    /**
     * Find the voltage difference across the element
     * @return
     */
    public abstract double getVoltageDiff();

    /**
     * Find the current flowing through the element
     * @return
     */
    public abstract double calculateCurrent();

    /**
     * Sets the value of the element (resistance for ResistorElm, voltage for VoltageElm, etc...)
     * @param value
     */
    public abstract void setValue(double value);

    /**
     * Returns element type
     * @return element type (resistor, wire, etc.)
     */
    public abstract String getType();

    /**
     * Returns index of node corresponding to given node, else -1 if element does not correspond to node
     * @param node
     * @return
     */
    public int indexOfNode(CircuitNode node){
        if(node.equals(n1))
            return 0;
        else if (node.equals(n2))
            return 1;

        return -1;
    }

    /*    Getters and Setters     */

    /**
     *
     * @param i
     *      Requires i = 0 or 1
     * @param node
     */
    public void setNode(int i, CircuitNode node){
        if(i == 0)
            n1 = node;
        if(i == 1)
            n2 = node;
    }

    /**
     *
     * @param i
     *      Requires i <= 2
     * @return Node corresonding to index i, null if no node corresponds to i
     */
    public CircuitNode getNode(int i){
        if(i == 0)
            return n1;
        else if(i == 1)
            return n2;
        return null;
    }

    public int getNumPosts(){
        return 2;
    }

    public Point getPost(int i){
        if(i < posts.length){
            return posts[i];
        }
        else{
            return null; //TODO: throw an exception instead?
        }
    }

}
