/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.controllers;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Пользователь
 */
public class TestFaceDetector {

    private static Mat cropImage;

    public static void main(String[] args) throws Exception {

        int x = 0, y = 0, height = 0, width = 0;
        Rect rectCrop = null;
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");

//CascadeClassifier faceDetector = new       CascadeClassifier(FaceDetector.class.getResource("haarcascade_frontalface_alt.xml").getPath());
        // CascadeClassifier faceDetector = new CascadeClassifier(Snapshot.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
        CascadeClassifier faceDetector = new CascadeClassifier("src/java/diploma/controllers/haarcascade_frontalface_alt.xml");
        //faceDetector.load("C:/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread("C:\\image.jpg");

//if ((new File("C:\\image.jpg")).exists()) {
//	System.out.println(image);
//} else {
//	System.out.println("\nBad");
//}
        MatOfRect face_Detections = new MatOfRect();
        faceDetector.detectMultiScale(image, face_Detections);
        System.out.println(String.format("Detected %s faces", face_Detections.toArray().length));
        Rect rect_Crop = null;
        for (Rect rect : face_Detections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
        }
        Mat image_roi = new Mat(image, rectCrop);
        BufferedImage bi = Mat2BufferedImage(image_roi);
        BufferedImage resize = resizeImage(bi);
        Mat fin = BufferedImage2Mat(resize);
        Imgcodecs.imwrite("C:\\cropimage_912.jpg", fin);

    }

    private static BufferedImage resizeImage(BufferedImage im) // resize to at least a standard size, then convert to grayscale 
    {
        // resize the image so *at least* FACE_WIDTH*FACE_HEIGHT size
        int imWidth = im.getWidth();
        int imHeight = im.getHeight();
        System.out.println("Original (w,h): (" + imWidth + ", " + imHeight + ")");

        double widthScale = 125 / ((double) imWidth);
        double heightScale = 150 / ((double) imHeight);
        double scale = (widthScale > heightScale) ? widthScale : heightScale;

        int nWidth = (int) Math.round(imWidth * scale);
        int nHeight = (int) Math.round(imHeight * scale);

        // convert to grayscale while resizing
        BufferedImage grayIm = new BufferedImage(nWidth, nHeight,
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2 = grayIm.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(im, 0, 0, nWidth, nHeight, 0, 0, imWidth, imHeight, null);
        g2.dispose();

        System.out.println("Scaled gray (w,h): (" + nWidth + ", " + nHeight + ")");
        return grayIm;
    }  // 

    public static BufferedImage Mat2BufferedImage(Mat matrix) throws IOException {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }

    public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }

}
