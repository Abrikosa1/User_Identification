/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.controllers;


import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
/**
 *
 * @author Пользователь
 */
public class TestUpdater {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        String s1= "C:\\trainingImages";
//                String s2 ="C:\\1-denis1.png";
                
        String trainingDir = "C:\\trainingImages2";
       // Mat testImage = imread("C:\\denis-14.png", CV_LOAD_IMAGE_GRAYSCALE);
//if ((new File("result_train.xml")).exists()) {
//	System.out.println("\ngood");
//} else {
//	System.out.println("\nBad");
//}

        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);

        MatVector images = new MatVector(imageFiles.length);

        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();

        int counter = 0;

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);

            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            //System.out.println(label+"gg"+image);
            images.put(counter, img);
            labelsBuf.put(counter, label);

            counter++;
        }

        //FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
        // FaceRecognizer faceRecognizelabelsr = EigenFaceRecognizer.create();
        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        faceRecognizer.read("result_train2.xml");
        faceRecognizer.update(images, labels);
        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
        //faceRecognizer.predict(testImage, label, confidence);
        //int predictedLabel = label.get();
        //double confiden2 = confidence.get();
        //System.out.println(confiden2);
        //System.out.println("Predicted label: " + predictedLabel);
        faceRecognizer.save("result_train3.xml");
        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }
    
}
