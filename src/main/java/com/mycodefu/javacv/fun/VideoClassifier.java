package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.streams.video.Video;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.opencv.core.Core.rectangle;

/**
 * Run a classifier on a background thread, taking every nth frame of the video for the classifier to run
 * on so as not to overwhelm the CPU and make the video display choppy.
 *
 * Use a queue to add candidate images to, which the background classifier thread works on to classify and
 * return face rectangles to the main video display thread which draws the rects over the live stream.
 *
 * Created by lthompson on 19/05/15.
 */
public class VideoClassifier {
    final String classifier;
    final CascadeClassifier cascadeClassifier;
    final ArrayBlockingQueue<Mat> candidateImages;
    final CopyOnWriteArrayList<Rect> faces;
    final long[] frameNumber;

    public VideoClassifier() {
        classifier = this.getClass().getResource("/haarcascade_frontalface_alt.xml").getFile();
        cascadeClassifier = new CascadeClassifier(classifier);
        candidateImages = new ArrayBlockingQueue<Mat>(10, false);
        faces = new CopyOnWriteArrayList<>();
        frameNumber = new long[]{0};
    }

    public void execute() {
        final DisplayImages display = new DisplayImages();

        runClassifier();

        Video.stream(0)
                .filter(image -> drawImageWithFaces(display, image))
                .filter(image -> everyFifthFrame())
                .forEach(this::addCandidateForClassifier);
    }

    private boolean drawImageWithFaces(DisplayImages display, Mat image) {
        for (Rect rect : faces) {
            rectangle(image, rect.tl(), rect.br(), Filter.GREEN, 3);
        }
        display.draw(image);
        return true;
    }

    private boolean everyFifthFrame() {
        frameNumber[0]++;
        return frameNumber[0] % 5 == 0;
    }

    private void addCandidateForClassifier(Mat image) {
        if (candidateImages.isEmpty()) {
            candidateImages.add(image);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void runClassifier() {
        Thread classifierThread = new Thread(() -> {
            while(true) {
                Mat candidateImage = null;
                try {
                    candidateImage = candidateImages.take();
                    final MatOfRect objects = new MatOfRect();
                    cascadeClassifier.detectMultiScale(candidateImage, objects);

                    faces.clear();
                    faces.addAll(objects.toList());

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {}

                } catch (InterruptedException ignored) {}
            }
        }, "Classifier Thread");
        classifierThread.setDaemon(true);
        classifierThread.start();
    }

}
