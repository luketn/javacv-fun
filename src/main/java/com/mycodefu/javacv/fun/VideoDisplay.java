package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayImagesSwing;
import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.filters.FilterMode;
import com.mycodefu.javacv.fun.streams.video.Video;

public class VideoDisplay {

    public void execute(FilterMode mode) {
        final DisplayImages display = new DisplayImagesSwing();

        Video.stream(0)
                .filter(image -> Filter.run(mode, image))
                .forEach(display::updateImage);
    }

}