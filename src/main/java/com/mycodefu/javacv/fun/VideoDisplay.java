package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.filters.FilterMode;
import com.mycodefu.javacv.fun.streams.video.Video;

/**
 * Capture video from the computer's primary video input device (i.e. a laptop's camera), apply a filter and display the stream.
 */
public class VideoDisplay {

    public void execute(FilterMode mode) {
        final DisplayImages display = new DisplayImages();

        Video.stream(0)
                .filter(image -> Filter.run(mode, image))
                .forEach(display::draw);
    }

}