/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.controllers;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;
import diploma.mappers.JsonUserMapper;
import diploma.model.User;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.json.JSONObject;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Пользователь
 */
public class cutter {

    public static Mat detect(BufferedImage buffImg, String login) throws IOException {

        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String opencvpath = "C:\\opencv\\build\\java\\x64\\";
            System.load(opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll");
        Mat matImg = BufferedImage2Mat(buffImg);
        int x = 0, y = 0, height = 0, width = 0;
        Rect rectCrop = null;
        System.out.println("\nRunning FaceDetector");
        CascadeClassifier faceDetector = new CascadeClassifier("C:\\haarcascade_frontalface_alt.xml");
        Mat image = matImg;

//        if ((new File("C:\\haarcascade_frontalface_alt.xml")).exists()) {
//            System.out.println("\nGood");
//        } else {
//            System.out.println("\nBad");
//        }
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
        Imgcodecs.imwrite("C:\\testImg\\"+login+ ".png", fin);
        return fin;
    }

    public static BufferedImage resizeImage(BufferedImage im) // resize to at least a standard size, then convert to grayscale 
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
        Imgcodecs.imencode(".png", matrix, mob);
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }

    public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }

    public static BufferedImage String2BufferedImage(String image) throws IOException {
        BufferedImage img = null;
        byte[] imageByte;
        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        img = ImageIO.read(bis);
        bis.close();
        return img;
    }
                 /*//bi to ip1image
            ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
            Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
            IplImage iplImage = iplConverter.convert(java2dConverter.convert(img));
             //*/
}
