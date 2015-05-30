package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.filters.Filter.FilterMode;
import com.mycodefu.javacv.fun.streams.video.Video;
import org.opencv.core.Mat;

import java.util.stream.Stream;

/**
 * Capture video from the computer's primary video input device (i.e. a laptop's camera), apply a filter and display the stream.
 */
public class VideoDisplay {

    public void execute(FilterMode mode, Stream<Mat> stream) {
        final DisplayImages display = new DisplayImages();

        stream
                .filter(image -> Filter.run(mode, image))
                .forEach(display::draw);
    }

    public void execute(FilterMode mode, int device) {
        execute(mode, Video.stream(device));
    }

    public void execute(FilterMode mode, String videoSource) {
        execute(mode, Video.stream(videoSource));
    }
}