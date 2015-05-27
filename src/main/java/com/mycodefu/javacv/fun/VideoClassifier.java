package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.classifiers.Classifier;
import com.mycodefu.javacv.fun.classifiers.FaceClassifier;
import com.mycodefu.javacv.fun.classifiers.SmileClassifier;
import com.mycodefu.javacv.fun.display.DisplayImages;
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
public class VideoClassifier {
    private final Classifier classifier;

    private VideoClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public void execute(String file) {
        execute(Video.stream(file));
    }

    public void execute(int device) {
        execute(Video.stream(device));
    }

    public void execute(Stream<Mat> videoStream) {
        final DisplayImages display = new DisplayImages();

        videoStream
                .filter(this::classify)
                .forEach(display::draw);
    }

    private boolean classify(Mat image) {
        final List<Rect> features = classifier.detectFeatures(image);
        classifier.drawFeatures(image, features);
        return true;
    }


    public static VideoClassifier faces(){
        return new VideoClassifier(new FaceClassifier());
    }

    public static VideoClassifier smiles(){
        return new VideoClassifier(new SmileClassifier());
    }

}
