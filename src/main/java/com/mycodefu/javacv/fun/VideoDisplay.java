package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayImagesSwing;
import com.mycodefu.javacv.fun.streams.Filter;
import com.mycodefu.javacv.fun.streams.video.Video;
import org.opencv.core.Mat;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class VideoDisplay {
    public void execute(VideoMode mode) {
        final DisplayImages display = new DisplayImagesSwing();

        try (final Stream<Mat> matStream = Video.stream(0)) {
            switch (mode) {
                case blurry: {
                    matStream
                            .filter(Filter::blur)
                            .forEach(display::updateImage);
                    break;
                }
                case greyscale: {
                    matStream
                            .filter(Filter::greyscale)
                            .forEach(display::updateImage);;
                    break;
                }
                case edges: {
                    matStream
                            .filter(Filter::edges)
                            .forEach(display::updateImage);;
                    break;
                }
                case alternateColorGrey : {
                    AtomicBoolean color = new AtomicBoolean();
                    matStream
                            .forEach((image) -> {
                                if (!color.get()) {
                                    Filter.greyscale(image);
                                }
                                color.set(!color.get());

                                display.updateImage(image);
                    });
                    break;
                }
                case normal:
                default: {
                    matStream
                            .forEach(display::updateImage);
                    break;
                }
            }
        }
    }


    public enum VideoMode{
        normal,
        alternateColorGrey,
        edges,
        blurry,
        greyscale
    }
}
