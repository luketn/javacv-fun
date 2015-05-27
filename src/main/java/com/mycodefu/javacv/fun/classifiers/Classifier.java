package com.mycodefu.javacv.fun.classifiers;

import com.mycodefu.javacv.fun.filters.Filter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

import java.util.List;

import static com.mycodefu.javacv.fun.filters.Filter.greyscale;
import static com.mycodefu.javacv.fun.filters.Filter.scale;
import static org.opencv.core.Core.LINE_4;
import static org.opencv.core.Core.rectangle;

/**
 * General classifier base class.
 *
 * Created by lthompson on 20/05/15.
 */
public class Classifier {

    public static final double SCALE_FACTOR = 3.5; //this seems to give a good rate of 30 frames a second in the sample image I tested on the MacBook

    final CascadeClassifier cascadeClassifier;

    public Classifier(String classifierPath) {
        cascadeClassifier = new CascadeClassifier(classifierPath);
    }

    public void drawFeatures(Mat image, List<Rect> features) {
        for (Rect rect : features) {
            rectangle(image, rect.tl(), rect.br(), Filter.GREEN, LINE_4);
        }
    }

    /**
     * Take the image and make a scaled down version to classify (faster).
     *
     * Classify the image and return rectangles which highlight the detected features.
     */
    public List<Rect> detectFeatures(Mat image) {

        image = image.clone();
        greyscale(image);

        final Size size = image.size();
        Size scaledSize = new Size(size.width / SCALE_FACTOR, size.height / SCALE_FACTOR);

        scale(image, scaledSize);

        final MatOfRect objects = new MatOfRect();

        cascadeClassifier.detectMultiScale(image, objects);

        final List<Rect> rects = objects.toList();

        for (Rect rect : rects) {
            rect.x = (int) ((double)rect.x / scaledSize.width * size.width);
            rect.y = (int) ((double)rect.y / scaledSize.width * size.width);
            rect.width = (int) (rect.width * SCALE_FACTOR);
            rect.height = (int) (rect.height * SCALE_FACTOR);
        }

        return rects;
    }
}
