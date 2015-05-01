package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayImagesSwing;
import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;

import static org.opencv.imgproc.Imgproc.*;

public class VideoFun {
    private static final Logger log = Logger.getLogger(VideoFun.class);

    public void execute(VideoMode mode) {
        VideoCapture videoCapture = new VideoCapture(0);
        try{
            if (!videoCapture.isOpened()) {
                log.error("Failed to open the video stream.");
            } else {
                DisplayImages displayImages = new DisplayImagesSwing();

                Mat image = new Mat();
                while (videoCapture.grab()) {
                    if (videoCapture.read(image)) {

                        switch (mode) {
                            case blurry: {
                                GaussianBlur(image, image, new Size(0, 0), 10, 10);
                                break;
                            }
                            case greyscale: {
                                cvtColor(image, image, COLOR_BGR2GRAY);
                                break;
                            }
                            case edges: {
                                cvtColor(image, image, COLOR_BGR2GRAY);
                                GaussianBlur(image, image, new Size(7, 7), 5.5, 5.5);
                                Canny(image, image, 0d, 30d, 3, false);
                                break;
                            }
                        }

                        displayImages.updateImage(image);
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

            }

        } finally {
            videoCapture.release();
        }
    }

    public enum VideoMode{
        normal,
        edges,
        blurry,
        greyscale
    }
}
