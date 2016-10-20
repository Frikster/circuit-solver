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

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
       //
        buttonPress(houghLines());

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


    /**Method to detect the components
     *
     * @return the bitmap with the detected lines and a circle around the components
     */
    public Bitmap houghLines(){

        //Convert to a canny edge detector grayscale mat
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.mipmap.circuitcomp33);
        Mat tmp = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Mat tmp2 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bMap, tmp);
        Imgproc.Canny(tmp, tmp2, 50, 200);
       Mat tmp3 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);

        //Execute the hough transform on the canny edge detector
        Mat lines = new Mat();
        Imgproc.HoughLinesP(tmp2,lines,1,Math.PI/180,0);

        cvtColor(tmp2, tmp3, COLOR_GRAY2BGR);

        //remove chunks from hough transform and make one line from them
        List<double[]> smoothedLines = smoothLines(MatToList(lines));

        //Draw the found lines
        for (int x = 0; x < smoothedLines.size(); x++)
        {
            double[] vec = smoothedLines.get(x);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);
            //System.out.println("line :("+x1+" : "+y1+") , ("+x2+" : "+y2+")");
            if(x%3 == 0){
                Imgproc.line(tmp3, start, end, new Scalar(255,0,0), 1);
            }
            else if(x% 3 == 1){
                Imgproc.line(tmp3, start, end, new Scalar(0,255,0), 1);
            }
            else{
                Imgproc.line(tmp3, start, end, new Scalar(0,0,255), 1);
            }


        }

        //Detect the components and draw circle around them
        circlesAroundComponentsByVote(smoothedLines,tmp3, 10, 40);

        //Create and return the final bitmap
        Bitmap bm = Bitmap.createBitmap(tmp3.cols(), tmp3.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(tmp3, bm);
        tmp.release();
        tmp2.release();
        tmp3.release();
        return bm;
    }

    /**
     *
     * @param lines containing all the lines
     * @param imageToWriteOn the opencv.Mat to wdraw the circles on
     * @param minVote The min nr of lines a component must have to be recognized as a component
     * @param maxVote The max nr of lines a component must have to be recognized as a component
     */
    private void circlesAroundComponentsByVote(List<double[]> lines, Mat imageToWriteOn, int minVote, int maxVote){
        List<double[]> linesCopy = new ArrayList<>(lines);
        List<double[]> componentsFound = new ArrayList<>();
        int maxLinesVote = maxVote;
        int minLinesVote = minVote;
        int radius = 12;

        //For all votes starting from the biggest number of lines
        for(int nrLine = maxLinesVote; nrLine >= minLinesVote; nrLine--){
            for (int i = 0; i < imageToWriteOn.cols(); i++) {
                for (int j = 0; j < imageToWriteOn.rows(); j++) {
                    int vote = 0;
                    Set<double[]> potentialLinesInComponent = new HashSet<>();
                    //Find the nr of lines from around a given position
                    for (double[] line : linesCopy) {
                        if (Math.sqrt(Math.pow(i - line[0], 2) + Math.pow(j - line[1], 2)) < radius) {
                            vote++;
                            potentialLinesInComponent.add(line);
                        }
                    }
                    //if the nr of lines around is sufficent, add the component to the found component and delete it from the found lines
                    if (vote >= nrLine) {
                        double[] circle = new double[3];
                        circle[0] = i;
                        circle[1] = j;
                        circle[2] = radius;
                        componentsFound.add(circle);
                        linesCopy.removeAll(potentialLinesInComponent);
                    }
                }
            }
            //Print to give an approximation of time (Takes roughly 0.5 seconds per iteration)
            System.out.println("Try to find components with "+nrLine+" lines");
        }


        System.out.println("Number of components found : "+componentsFound.size());
        drawCircles(imageToWriteOn,componentsFound);
    }

    /**
     *
     * @param dst Mat to draw the circles
     * @param circlesToDraw The list containing the circles
     * @return the mat with the drawn circles on it
     */
    private Mat drawCircles(Mat dst, List<double[]> circlesToDraw){
        double xi = 0.0;
        double yi = 0.0;
        int ri = 0;

        for( int i = 0; i < circlesToDraw.size(); i++ ) {
            double[] data = circlesToDraw.get(i);

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

    /**
     *
     * @param lines Mat of lines
     * @return a list of these lines
     */
    private List<double[]> MatToList(Mat lines){
        List<double[]> lineOneRound = new ArrayList<>();
        for (int x = 0; x < lines.rows(); x++) {
            double[] vec1 = lines.get(x, 0);
            lineOneRound.add(vec1);
        }
        return lineOneRound;
    }

    /**
     *
     * @param lines found from the original hough transform
     * @return the lines, smoothed
     */
    private List<double[]> smoothLines(List<double[]> lines){
        //minimum pixel number that an horizontal line should have
        int lengthOfALine = 20;
        List<double[]> lineOneRound = new ArrayList<>(lines);

        List<double[]> lineTwoRound = new ArrayList<>();

        for(int lineDist=1; lineDist<=lengthOfALine ; lineDist++) {
            //Put all the lines going from left to right (Xstart < Xend)
            List<double[]> lineFromLeftToRight = new ArrayList<>();

            for (int x = 0; x < lineOneRound.size(); x++) {
                double[] vec1 = lineOneRound.get(x);
                double x11 = vec1[0];
                double y11 = vec1[1];
                double x21 = vec1[2];
                double y21 = vec1[3];

                if (x11 > x21) {
                    double[] inversedLine = new double[4];
                    inversedLine[0] = x11;
                    inversedLine[1] = y11;
                    inversedLine[2] = x21;
                    inversedLine[3] = y21;
                    lineFromLeftToRight.add(inversedLine);
                } else {
                    lineFromLeftToRight.add(vec1);
                }

                //Sort by yStart and then byXstart
                Collections.sort(lineFromLeftToRight, new LinesComparator());
            }

            //get the lines 2 by 2, see if they are adjacent, and if so make one line from the two
            for (int x = 0; x < lineFromLeftToRight.size() - 1; x += 2) {
                double[] vec1 = lineFromLeftToRight.get(x);
                double x11 = vec1[0];
                double y11 = vec1[1];
                double x21 = vec1[2];
                double y21 = vec1[3];

                double[] vec2 = lineFromLeftToRight.get(x + 1);
                double x12 = vec2[0];
                double y12 = vec2[1];
                double x22 = vec2[2];
                double y22 = vec2[3];

                if ((y11 == y12) && (x21 + 1 == x12)) {

                    double[] newVec = new double[4];
                    newVec[0] = x11;
                    newVec[1] = y11;
                    newVec[2] = x22;
                    newVec[3] = y12;
                    lineTwoRound.add(newVec);
                } else {

                    lineTwoRound.add(vec1);
                    lineTwoRound.add(vec2);
                }
            }


            lineOneRound = new ArrayList<>(lineTwoRound);
            if(lineDist != lengthOfALine ) {
                lineTwoRound.clear();
            }
            //Repeat
        }
        return lineTwoRound;
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
