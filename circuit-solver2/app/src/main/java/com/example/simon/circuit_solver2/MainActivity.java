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
import org.opencv.imgproc.Imgproc;

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
        buttonPress(houghTransform());

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

    public Bitmap houghTransform(){

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.mipmap.circuit1);
        Mat tmp = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Mat tmp2 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bMap, tmp);
        Imgproc.Canny(tmp, tmp2, 50, 200);
        Mat tmp3 = new Mat (bMap.getWidth(), bMap.getHeight(), CvType.CV_8UC1);


        Mat lines = new Mat();
        Imgproc.HoughLinesP(tmp2,lines,1,Math.PI/180,0);

        cvtColor(tmp2, tmp3, COLOR_GRAY2BGR);
        //Drawing the lines into the mat

        for (int x = 0; x < lines.rows(); x++)
        {
            double[] vec = lines.get(x, 0);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            Imgproc.line(tmp3, start, end, new Scalar(255,0,0), 1);

        }

        Bitmap bm = Bitmap.createBitmap(tmp3.cols(), tmp3.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(tmp3, bm);
        tmp.release();
        tmp2.release();
        tmp3.release();
        return bm;
    }

    static{
        if(!OpenCVLoader.initDebug()){
            Log.i("opencv","opencv init failed");
        }else{
            Log.i("opencv","opencv init success");
        }
    }
}
