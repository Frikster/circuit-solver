package main;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Hello {
	
	public static void main(String[] args) throws IOException{
		
		//Read image and convert it to an opencv mat()

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    File input = new File("resources/circuit1.jpg");
	    BufferedImage image = ImageIO.read(input);	

	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    mat.put(0, 0, data);

	    //processing the image with canny and Hough
	 
		Mat mat1 = new Mat();
		
		Imgproc.Canny(mat,mat1,0,200,3,true);
		Mat lines = new Mat();
		Imgproc.HoughLinesP(mat1,lines,1,Math.PI/180,0);
		

		//Drawing the lines into the mat
		
	    for (int x = 0; x < lines.cols(); x++) 
	    {
	          double[] vec = lines.get(0, x);
	          double x1 = vec[0], 
	                 y1 = vec[1],
	                 x2 = vec[2],
	                 y2 = vec[3];
	          Point start = new Point(x1, y1);
	          Point end = new Point(x2, y2);
	          
	          Imgproc.line(mat1, start, end, new Scalar(255,0,0), 3);

	    }
	   
	    //Printing the image into a file
	    
	    byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
	    mat1.get(0, 0, data1);
	    BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
	    image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

	    File ouptut = new File("grayscale.jpg");
	    ImageIO.write(image1, "jpg", ouptut);
	}
	
}

