package com.mycodefu.javacv.fun.streams;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.imgproc.Imgproc.*;

/**
 * A set of filter functions that can be used in a stream
 *
 * Created by lthompson on 2/05/15.
 */
public class Filter {

    public static boolean edges(Mat image) {
        cvtColor(image, image, COLOR_BGR2GRAY);
        GaussianBlur(image, image, new Size(7, 7), 5.5, 5.5);
        Canny(image, image, 0d, 30d, 3, false);
        return true;
    }

    public static boolean greyscale(Mat image) {
        cvtColor(image, image, COLOR_BGR2GRAY);
        return true;
    }

    public static boolean blur(Mat image) {
        GaussianBlur(image, image, new Size(0, 0), 10, 10);
        return true;
    }
}
