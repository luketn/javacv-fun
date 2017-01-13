package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.classifiers.Classifier;
import com.mycodefu.javacv.fun.classifiers.Classifiers;
import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayView;
import com.mycodefu.javacv.fun.streams.video.Video;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;
import java.util.stream.Stream;

/**
 * Stream video and run a classifier over the feed.
 * <p>
 * Created by lthompson on 19/05/15.
 */
public class VideoClassifier extends DisplayView {
    private final Classifier classifier;

    public VideoClassifier(Classifiers classifier, DisplayImages display) {
        super(display);
        this.classifier = classifier.create();
    }

    public void execute(String file) {
        execute(Video.stream(file));
    }

    public void execute(int device) {
        execute(Video.stream(device));
    }

    public void execute(Stream<Mat> videoStream) {
        videoStream
                .filter(this::classify)
                .forEach(display::draw);
    }

    private boolean classify(Mat image) {
        final List<Rect> features = classifier.detectFeatures(image);
        classifier.drawFeatures(image, features);
        return true;
    }

}
