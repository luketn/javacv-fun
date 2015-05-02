package com.mycodefu.javacv.fun.filters;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.opencv.core.Core.inRange;
import static org.opencv.imgproc.Imgproc.*;

/**
 * A set of filter functions that can be used in a stream
 * <p>
 * Created by lthompson on 2/05/15.
 */
public class Filter {

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
        GaussianBlur(image, image, new Size(0, 0), 10, 10);
        return true;
    }

    public static boolean findBlue(Mat image) {
        //Create a HSV copy of the matrix to perform a threshold over
        cvtColor(image, image, COLOR_BGR2HSV);

        //create a black and white image highlighting only pixels which match an HSV threshold range for blue colours
        inRange(image, new Scalar(110, 50, 50), new Scalar(130, 255, 255), image);

        erode(image, image, new Mat());
        dilate(image, image, new Mat());

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
        }
        return true;
    }
}