package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.filters.Filter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.util.List;

import static org.opencv.core.Core.rectangle;

/**
 * General classifier base class.
 *
 * Created by lthompson on 20/05/15.
 */
public class Classifier {
    final CascadeClassifier cascadeClassifier;

    public Classifier(String classifierPath) {
        cascadeClassifier = new CascadeClassifier(classifierPath);
    }

    protected void drawFeatures(Mat image, List<Rect> features) {
        for (Rect rect : features) {
            rectangle(image, rect.tl(), rect.br(), Filter.GREEN, 3);
        }
    }

    protected List<Rect> detectFeatures(Mat image) {
        final MatOfRect objects = new MatOfRect();
        cascadeClassifier.detectMultiScale(image, objects);
        return objects.toList();
    }
}
