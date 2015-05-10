package com.mycodefu.javacv.fun.filters;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.opencv.core.Core.inRange;
import static org.opencv.core.Core.rectangle;
import static org.opencv.imgproc.Imgproc.*;

/**
 * A set of filter functions that can be used in a stream or over a single image.
 * <p>
 * Created by lthompson on 2/05/15.
 */
public class Filter {

    public static final Scalar GREEN = new Scalar(0, 255, 0);

    public static boolean edges(Mat image) {
        cvtColor(image, image, COLOR_BGR2GRAY);
        GaussianBlur(image, image, new Size(7, 7), 5.5, 5.5);
        Canny(image, image, 0d, 80d, 3, false);
        return true;
    }

    public static boolean greyscale(Mat image) {
        cvtColor(image, image, COLOR_BGR2GRAY);
        return true;
    }

    public static boolean blur(Mat image) {
        return blur(image, 10);
    }

    public static boolean blur(Mat image, int magnitude) {
        GaussianBlur(image, image, new Size(0, 0), magnitude, magnitude);
        return true;
    }

    public static boolean findBlue(Mat image) {
        final Mat workImage = image.clone();

        blur(workImage, 2);

        //create a black and white image highlighting only pixels which match an BGR threshold range for blue colours
        Scalar bgr_low_blue_range = new Scalar(100, 0, 0);
        Scalar bgr_high_blue_range = new Scalar(255, 60, 60);
        inRange(workImage, bgr_low_blue_range, bgr_high_blue_range, workImage);

        //clean up some of the noise in the resulting image
        final Mat kernel = getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(10, 10));
        final Mat kernelSmall = getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));

        dilate(workImage, workImage, kernel);
        erode(workImage, workImage, kernel);
        dilate(workImage, workImage, kernelSmall);

        /// Detect edges using canny
        final Mat edges = new Mat();
        Canny(workImage, edges, .5, 1, 3, false);

        /// Find contours in the edges
        final ArrayList<MatOfPoint> contours = new ArrayList<>();
        final Mat hierarchy = new Mat();
        findContours(edges, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE, new Point(0, 0));

        //Display the top 5 largest shapes
        contours.stream()
                .map(Imgproc::boundingRect)
                .distinct()
                .sorted((o1, o2) -> Double.compare(o2.area(), o1.area()))
                .limit(5)
                .forEach(rect -> rectangle(image, rect.tl(), rect.br(), GREEN));

        return true;
    }

    private static final AtomicBoolean colorAlternator = new AtomicBoolean();
    public static boolean run(FilterMode mode, Mat image) {
        switch (mode) {
            case blurry: {
                blur(image);
                break;
            }
            case greyscale: {
                greyscale(image);
                break;
            }
            case edges: {
                edges(image);
                break;
            }
            case findBlue: {
                findBlue(image);
                break;
            }
            case alternateColorGrey: {
                if (!colorAlternator.get()) {
                    Filter.greyscale(image);
                }
                colorAlternator.set(!colorAlternator.get());
                break;
            }
            case normal : {
                break;
            }
        }
        return true;
    }
}
