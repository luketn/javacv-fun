package com.mycodefu.javacv.fun.streams.video;

import com.mycodefu.javacv.fun.streams.CloseableMatIterator;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

/**
 * Iterate a video capture device, returning a stream of images described by a matrix of pixels.
 *
 * Created by lthompson on 2/05/15.
 */
public class VideoIterator implements CloseableMatIterator {
    private VideoCapture videoCapture;
    private Mat mat;

    public VideoIterator(VideoCapture videoCapture) {
        this.videoCapture = videoCapture;
        this.mat = new Mat();
    }

    @Override
    public boolean hasNext() {
        return videoCapture.grab() && videoCapture.read(mat);
    }

    @Override
    public Mat next() {
        return mat;
    }

    @Override
    public void close() {
        videoCapture.release();
    }
}
