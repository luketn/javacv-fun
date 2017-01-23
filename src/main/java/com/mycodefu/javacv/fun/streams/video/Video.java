package com.mycodefu.javacv.fun.streams.video;

import com.mycodefu.javacv.fun.streams.CloseableMatIterator;
import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Helper functions for streaming video.
 *
 * Created by lthompson on 2/05/15.
 */
public class Video {
    private static final Logger log = Logger.getLogger(Video.class);

    /**
     * Stream images from the given video source at the fileName location.
     */
    public static Stream<Mat> stream(String fileName) {
        VideoCapture videoCapture = new VideoCapture(fileName);

        return stream(videoCapture);
    }

    /**
     * Stream images from the given device.
     */
    public static Stream<Mat> stream(int device) {
        VideoCapture videoCapture = new VideoCapture(device);

        return stream(videoCapture);
    }

    private static Stream<Mat> stream(VideoCapture videoCapture) {
        if (!videoCapture.isOpened()) {
            final String message = "Unable to the video capture session.";
            log.error(message);
            throw new RuntimeException(message);
        }

        CloseableMatIterator iterator = new VideoIterator(videoCapture);
        try {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.DISTINCT), false)
                    .onClose(() -> closeIterator(iterator));
        } catch (Error|RuntimeException e) {
            closeIterator(iterator);
            throw e;
        }
    }

    private static void closeIterator(CloseableMatIterator iterator) {
        try{
            iterator.close();
        } catch(Exception e) {
            log.error("Error closing iterator.", e);
        }
    }

}
