package com.mycodefu.javacv.fun.filters;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.opencv.core.Core.*;
import static org.opencv.imgproc.Imgproc.*;

/**
 * A set of filter functions that can be used in a stream or over a single image.
 * <p>
 * Created by lthompson on 2/05/15.
 */
public class Filter {

    public static final Scalar GREEN = new Scalar(0, 255, 0);
    public static final Scalar BLACK = new Scalar(0, 0, 0);

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

    /**
     * A gaussian blur uses a technique called 'convolution', to alter one pixels value to be
     * similar to the surrounding pixels. The size of the 'kernel' determines how many pixels
     * surrounding the pixel are brought in, and how many times that one pixel would be smudged
     * by its neighbours.
     *
     * @param image The image to be blurred.
     * @param kernelRadius How much to blur. 2 pixels is a slight blur, 10 is quite fuzzy.
     */
    public static boolean blur(Mat image, int kernelRadius) {
        GaussianBlur(image, image, new Size(0, 0), kernelRadius, kernelRadius);
        return true;
    }

    /**
     * Locate blue objects in the image, and draw a green box around the largest 5.
     */
    public static boolean findBlue(Mat image) {
        final Mat workImage = image.clone();

        //smooth out the image with a 2 pixel square kernel gaussian blur (slight blur) to remove some noise
        blur(workImage, 2);

        //Create a HSV copy of the matrix to perform a threshold over. HSV color space makes finding ranges of specific colors much easier since the hue is picked out by itself. This is much harder with RGB as a 'blue' color can have many variations of red and green in it.
        cvtColor(image, workImage, COLOR_BGR2HSV);

        //define the upper and lower bounds of the hue, saturation and (value/brightness) of the objects we're looking for
        //http://colorizer.org/ is handy for playing with different values
        final double blue_hue_lower_bound = (185d / 360d) * 179d; //hue in degrees - type of colour - this ranges from pale blue to nearing purple
        final double blue_hue_upper_bound = (250d / 360d) * 179d; //pure blue is 240. Note OpenCV has a peculiarity using 0-179 range instead of 0-255 for hue.

        final double blue_saturation_lower_bound = (50d / 100d) * 255d; //saturation percentage - how colorful the color is (from white through washed out to full intensity)
        final double blue_saturation_upper_bound = (100d / 100d) * 255d; //0% would be no color, 100% is pure color. Below about 50% the color is so washed out it picks up whitish mixes. If saturation is 0, the hue is irrelevant and the color will be between black and white depending on the value of brightness.

        final double blue_value_lower_bound = (20d / 100d) * 255d; //value/brightness percentage - how bright the color is (from black to full color)
        final double blue_value_upper_bound = (100d / 100d) * 255d; //0% is black, 100% is full bright color. If value/brightness is 0, this color is black regardless of the values of hue and saturation.

        final Scalar blue_lower_hsv = new Scalar(blue_hue_lower_bound, blue_saturation_lower_bound, blue_value_lower_bound);
        final Scalar blue_upper_hsv = new Scalar(blue_hue_upper_bound, blue_saturation_upper_bound, blue_value_upper_bound);

        //create a black and white image highlighting only pixels which match an HSV threshold range for blue colours
        inRange(workImage,
                blue_lower_hsv,
                blue_upper_hsv,
                workImage);

        openPartial(workImage, 10, 5);

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

    public static boolean openPartial(Mat workImage, int firstOpenKernel, int secondOpenKernel) {
        //merge some of the noisy parts in the resulting image to bigger clumps
        final Mat kernel = getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(firstOpenKernel, firstOpenKernel));
        final Mat kernelSmall = getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(secondOpenKernel, secondOpenKernel));

        dilate(workImage, workImage, kernel);
        erode(workImage, workImage, kernel);
        dilate(workImage, workImage, kernelSmall);

        return true;
    }

    public static boolean findShape(Mat image, int sides) {
        return findShape(image, sides, false);
    }

    public static boolean findShape(Mat image, int sides, boolean showDebugInfo) {
        Mat workImage = image.clone();

        blur(workImage, 2);

        greyscale(workImage);

        /// Detect edges using canny
        double low_threshold = 100;
        double ratio = 3;
        final int kernel_size = 5;

        Canny(workImage, workImage, low_threshold, low_threshold * ratio, kernel_size, false);

        openPartial(workImage, 4, 2);

        /// Find contours in the edges
        final ArrayList<MatOfPoint> contours = new ArrayList<>();
        final Mat hierarchy = new Mat();
        findContours(workImage, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);

        //Highlight the rectangles in the image
        final int[] counter = {0};
        final double[] rectOffsetY = {100};

        contours.stream()
                .filter(contour -> isPolygonOf(contour, sides, 5000, 10))
                .map(Imgproc::boundingRect)
                .distinct()
                .sorted((o1, o2) -> Double.compare(o2.area(), o1.area()))
                .forEach(rect -> {
                    rectangle(image, rect.tl(), rect.br(), GREEN, 3);

                    if (showDebugInfo) {
                        putText(image, counter[0] + "", new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), FONT_HERSHEY_DUPLEX, 1d, BLACK, 2);
                        putText(image, counter[0] + ": area = " + rect.area(), new Point(10d, rectOffsetY[0]), FONT_HERSHEY_DUPLEX, 1d, BLACK, 2);
                        rectOffsetY[0] += 40;
                        counter[0]++;
                    }
                });

        if (showDebugInfo) {
            putText(image, counter[0] + " rectangles in " + contours.size() + " contours", new Point(10d, 60d), FONT_HERSHEY_DUPLEX, 1d, BLACK, 2);
        }

        return true;
    }

    public static boolean isPolygonOf(MatOfPoint contour, int sides, int minArea, int polygonCornerEpsilon) {
        MatOfPoint2f curve = new MatOfPoint2f();
        MatOfPoint2f polygon = new MatOfPoint2f();
        if (contourArea(contour) > minArea) {
            contour.convertTo(curve, CvType.CV_32FC2);
            approxPolyDP(curve, polygon, polygonCornerEpsilon, true);
            if (polygon.rows() == sides) {
                return true;
            }
        }
        return false;
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
            case findRectangles: {
                findShape(image, 4);
                break;
            }
            case findTriangles: {
                findShape(image, 3);
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
