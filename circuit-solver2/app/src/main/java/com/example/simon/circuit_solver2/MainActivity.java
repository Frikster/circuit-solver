package com.example.simon.circuit_solver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.COLOR_GRAY2BGR;
import static org.opencv.imgproc.Imgproc.cvtColor;


public class MainActivity extends AppCompatActivity {

    Button loadBtn;
    ImageView myIm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadBtn = (Button) findViewById(R.id.button);
        myIm = (ImageView) findViewById(R.id.myImage);
        buttonPress(houghCirclesProbabilistic());

    }

    public void buttonPress(Bitmap bm){
        final Bitmap bm1 = bm;
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIm.setImageBitmap(bm1);
            }
        });

    }

    public Bitmap houghCirclesProbabilistic(){

        //create the matrices needed
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.mipmap.shapes);
        Mat tmp = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Mat tmp2 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bMap, tmp);
        cvtColor( tmp, tmp2, COLOR_BGR2GRAY );

        List<double[]> circles = getCirclesFromBlurChanges(tmp2);

        int[] count = getCountsPerCircle(1.0,circles);


        //Get all the circles with a min vote in the double[][] array

        int minVote = 2;
        double[][] circlesWithVote = new double[circles.size()][3];
        int nrCircles = 0;
        for (int e=0;e<circles.size();e++){
            if(count[e]>=minVote){
                circlesWithVote[e]=circles.get(e);
                nrCircles++;
                System.out.println("x : "+circlesWithVote[e][0]+", y : "+circlesWithVote[e][1]);
            }
        }


        //Convert the grayscale image to a colour image

        Mat tmp3 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        cvtColor(tmp2, tmp3, COLOR_GRAY2BGR);

        Mat finalMat = drawCircles(tmp3, circlesWithVote, nrCircles);

        //Convert to
        Bitmap bm = Bitmap.createBitmap(finalMat.cols(), finalMat.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(finalMat, bm);
        tmp.release();
        tmp2.release();
        tmp3.release();
        finalMat.release();
        return bm;
    }


    private Mat drawCircles(Mat dst, double[][] circlesToDraw, int nrCircles){
        double xi = 0.0;
        double yi = 0.0;
        int ri = 0;

        for( int i = 0; i < nrCircles; i++ ) {
            double[] data = circlesToDraw[i];

            for(int j = 0 ; j < data.length ; j++){
                xi = data[0];
                yi = data[1];
                ri = (int) data[2];
            }

            Point center = new Point(Math.round(xi), Math.round(yi));
            // circle center
            Imgproc.circle(dst,center,2,new Scalar(0, 255, 0), 1,8,0);
            // circle outline
            Imgproc.circle(dst, center, ri, new Scalar(0, 0, 255), 1,8,0);
        }
        return dst;
    }

    public Bitmap houghCirclesNormal(){
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.mipmap.shapes);
        Mat tmp = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Mat tmp2 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bMap, tmp);
        cvtColor( tmp, tmp2, COLOR_BGR2GRAY );
        Mat tmp3 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);

        int k = 1;
        Imgproc.GaussianBlur( tmp2, tmp2, new Size(k, k), 2, 2 );

        Mat circles = new Mat();
        Imgproc.HoughCircles(tmp2, circles, Imgproc.CV_HOUGH_GRADIENT, 0.1, 10, 50, 30, 0, 0);
        cvtColor(tmp2, tmp3, COLOR_GRAY2BGR);

        double x=0.0;
        double y=0.0;
        int r=0;
        for( int i = 0; i < circles.cols(); i++ ) {
            double[] data = circles.get(0,i);

            for(int j = 0 ; j < data.length ; j++){
                x = data[0];
                y = data[1];
                r = (int) Math.round(data[2]);
            }

            Point center = new Point(Math.round(x), Math.round(y));
            // circle center
            Imgproc.circle(tmp3,center,2,new Scalar(0, 255, 0), 1,8,0);
            // circle outline
            Imgproc.circle(tmp3, center, r, new Scalar(0, 0, 255), 1,8,0);


        }
        Bitmap bm = Bitmap.createBitmap(tmp3.cols(), tmp3.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(tmp3, bm);
        tmp.release();
        tmp2.release();
        tmp3.release();
        return bm;
    }

    private List<double[]> getCirclesFromBlurChanges(Mat grayImage){

        List<double[]> points = new ArrayList<>();
        for(int e=0;e<5;e++) {
            for (int kernelSize = 1; kernelSize < 15; kernelSize += 2) {
                Imgproc.GaussianBlur(grayImage, grayImage, new Size(kernelSize, kernelSize), e, e);

                Mat circle = new Mat();
                Imgproc.HoughCircles(grayImage, circle, Imgproc.CV_HOUGH_GRADIENT, 0.1, 10, 50, 30, 0, 0);

                double xi = 0.0;
                double yi = 0;
                int ri = 0;

                for (int i = 0; i < circle.cols(); i++) {
                    double[] data = circle.get(0, i);

                    for (int j = 0; j < data.length; j++) {
                        xi = data[0];
                        yi = data[1];
                        ri = (int) Math.round(data[2]);
                    }
                    double[] myArray = new double[3];
                    myArray[0] = xi;
                    myArray[1] = yi;
                    myArray[2] = ri;
                    points.add(myArray);

                }
            }
        }
        return points;
    }

    private int[] getCountsPerCircle(double distanceThreshold, List<double[]> circles){

        int[] count = new int[circles.size()];

        for(int i=0;i<circles.size();i++){
            for(int j=i+1;j<circles.size();j++){
                double[] circle1 = circles.get(i);
                double[] circle2 = circles.get(j);
                if(circle1[0]>= circle2[0]-distanceThreshold && circle1[0]<=circle2[0]+distanceThreshold){
                    if(circle1[1]>= circle2[1]-distanceThreshold && circle1[1]<=circle2[1]+distanceThreshold){
                        if(count[i]==0){
                            count[i]=1;
                        }
                        count[i]+=1;
                    }
                }
            }
        }
        return count;
    }





    static{
        if(!OpenCVLoader.initDebug()){
            Log.i("opencv","opencv init failed");
        }else{
            Log.i("opencv","opencv init success");
        }
    }
}
