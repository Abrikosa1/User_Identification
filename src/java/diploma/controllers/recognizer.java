/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Пользователь
 */
public class recognizer {

    public static int recognize(String img,String login) throws IOException {
        BufferedImage bi = String2BufferedImage(img);
        cutter.detect(bi, login);
        Mat testImage = imread("C:\\testImg\\"+login+".png",CV_LOAD_IMAGE_GRAYSCALE);
        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        //System.out.println(images);
        //faceRecognizer.train(images, labels);
        faceRecognizer.read("C:\\Users\\Пользователь\\Documents\\NetBeansProjects\\Diploma\\result_train.xml");

        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
        faceRecognizer.predict(testImage, label, confidence);
        int predictedLabel = label.get(0);
        double confiden2 = confidence.get();
        System.out.println(confiden2);
        System.out.println("Predicted label: " + predictedLabel);
        //faceRecognizer.save("result_train.xml");
        System.out.println(predictedLabel);
        return predictedLabel;
    }

    public static BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame, 1);
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
    
}
