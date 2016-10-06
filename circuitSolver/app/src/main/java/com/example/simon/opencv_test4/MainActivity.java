package com.example.simon.opencv_test4;

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
import org.opencv.imgproc.Imgproc;

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

        Bitmap bm = Bitmap.createBitmap(tmp2.cols(), tmp2.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(tmp2, bm);

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
