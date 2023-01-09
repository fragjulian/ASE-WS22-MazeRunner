package com.example.mazerunner;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class EdgeDetectionWallDetector implements WallDetector {
    @Override
    public boolean[][] detectWall(Maze maze) {
        BufferedImage bufferedImage = maze.getBufferedImage();
        Mat openCvImage = new Mat(bufferedImage.getWidth(), bufferedImage.getHeight(), CvType.CV_32SC4);
        openCvImage.put(0, 0, ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData());
        Mat openCvImageGray = new Mat(bufferedImage.getWidth(), bufferedImage.getHeight(), openCvImage.type());
        Mat edges = new Mat(bufferedImage.getWidth(), bufferedImage.getHeight(), openCvImage.type());
        Mat dst = new Mat(bufferedImage.getWidth(), bufferedImage.getHeight(), openCvImage.type(), new Scalar(0));
        Imgproc.cvtColor(openCvImage, openCvImageGray, Imgproc.COLOR_RGB2GRAY);//convert image to gray scale first
        //Imgproc.blur(openCvImageGray, edges, new Size(2, 2));//optional, blur the image to improve edge detection
        Imgproc.Canny(edges, edges, 100, 100 * 3);
        //Copying the detected edges to the destination matrix
        openCvImage.copyTo(dst, edges);

        return new boolean[0][];
    }
}
