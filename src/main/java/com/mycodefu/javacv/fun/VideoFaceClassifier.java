package com.mycodefu.javacv.fun;

import com.google.common.base.Stopwatch;
import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.streams.video.Video;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * Run a classifier on a background thread, taking every nth frame of the video for the classifier to run
 * on so as not to overwhelm the CPU and make the video display choppy.
 * <p>
 * Use a queue to add candidate images to, which the background classifier thread works on to classify and
 * return face rectangles to the main video display thread which draws the rects over the live stream.
 * <p>
 * Created by lthompson on 19/05/15.
 */
public class VideoFaceClassifier extends FaceClassifier {
    final ArrayBlockingQueue<Mat> candidateImages;
    final CopyOnWriteArrayList<Rect> faces;
    final long[] frameNumber;
    private int nthFrameToDetect;

    public VideoFaceClassifier() {
        this(1);
    }
    public VideoFaceClassifier(int nthFrameToDetect) {
        super();

        candidateImages = new ArrayBlockingQueue<>(10, false);
        faces = new CopyOnWriteArrayList<>();
        frameNumber = new long[]{0};

        this.nthFrameToDetect = nthFrameToDetect;
    }

    public void execute(String file) {
        final DisplayImages display = new DisplayImages();

        runClassifier();

        final Stream<Mat> videoStream;
        if (file == null) {
            videoStream = Video.stream(0);
        } else {
            videoStream = Video.stream(file);
        }

        videoStream
                .filter(image -> drawImageWithFaces(display, image))
                .filter(image -> everyNthFrame())
                .forEach(this::addCandidateForClassifier);
    }

    private boolean drawImageWithFaces(DisplayImages display, Mat image) {
        drawFeatures(image, faces);
        display.draw(image);
        return true;
    }

    private boolean everyNthFrame() {
        frameNumber[0]++;
        return frameNumber[0] % nthFrameToDetect == 0;
    }

    private void addCandidateForClassifier(Mat image) {
        if (candidateImages.isEmpty()) {
            candidateImages.add(image);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void runClassifier() {
        Thread classifierThread = new Thread(() -> {
            try {
                while (true) {
                    Mat candidateImage = candidateImages.take();
                    Stopwatch stopwatch = new Stopwatch().start();
                    final List<Rect> features = detectFeatures(candidateImage);
                    stopwatch.stop();
                    System.out.println("Detection took " + stopwatch.toString());
                    faces.clear();
                    faces.addAll(features);
                }
            } catch (InterruptedException ignored) {}
        }, "Classifier Thread");
        classifierThread.setDaemon(true);
        classifierThread.start();
    }

}
