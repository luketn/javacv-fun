package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayImagesJavaFX;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;

import static org.opencv.imgproc.Imgproc.*;

public class VideoFun {
    private static final Logger log = Logger.getLogger(VideoFun.class);

    public void execute(String mode, Stage stage) {
        VideoCapture videoCapture = new VideoCapture(0);
        try{
            if (!videoCapture.isOpened()) {
                log.error("Failed to open the video stream.");
            } else {
                Mat image = new Mat(480, 640, CvType.CV_8UC3);
                DisplayImages displayImages = new DisplayImagesJavaFX(stage);
                AnimationTimer animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        if (videoCapture.grab()) {
                            if (videoCapture.read(image)) {

                                switch (mode) {
                                    case "edges": {
                                        cvtColor(image, image, COLOR_BGR2GRAY);
                                        GaussianBlur(image, image, new Size(7, 7), 1.5, 1.5);
                                        Canny(image, image, 0d, 30d, 3, false);
                                        break;
                                    }
                                }

                                displayImages.updateImage(image);
                            }
                        }
                    }
                };
                animationTimer.start();
            }

        } finally {
            //videoCapture.release();
        }
    }
}