package com.example.simon.circuit_solver2;

import java.util.Comparator;

/**
 * Created by Simon on 21.10.2016.
 */

public class LinesComparatorXY  implements Comparator<double[]> {
    @Override
    public int compare(double[] a, double[] b){
        if(a[0]>b[0]){
            return 1;
        }
        else if(a[0]==b[0]){
            if(a[1]>=b[1]){
                return 1;
            }
            return -1;
        }
        else{
            return -1;
        }
    }
}
