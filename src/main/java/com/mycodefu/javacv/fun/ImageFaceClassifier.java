package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.classifiers.FaceClassifier;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;

import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;

/**
 * Created by lthompson on 19/05/15.
 */
public class ImageFaceClassifier extends FaceClassifier {

    public void execute(String pathIn, String pathOut) {
        Mat image = imread(pathIn);
        final List<Rect> features = detectFeatures(image);
        drawFeatures(image, features);
        imwrite(pathOut, image);
    }
}
