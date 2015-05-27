package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.classifiers.Classifier;
import com.mycodefu.javacv.fun.classifiers.Classifiers;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;

import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;

/**
 * Created by lthompson on 19/05/15.
 */
public class ImageClassifier{
    private final Classifier classifier;

    public ImageClassifier(Classifiers classifier) {
        this.classifier = classifier.create(1);
    }

    public void execute(String pathIn, String pathOut) {
        Mat image = imread(pathIn);
        final List<Rect> features = classifier.detectFeatures(image);
        classifier.drawFeatures(image, features);
        imwrite(pathOut, image);
    }
}
