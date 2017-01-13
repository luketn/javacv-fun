package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayView;
import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.filters.Filter.FilterMode;
import org.opencv.core.Mat;

import static org.opencv.highgui.Highgui.imread;

/**
 * Display a filtered image.
 *
 * Created by lthompson on 2/05/15.
 */
public class ImageDisplay extends DisplayView {
    public ImageDisplay(DisplayImages display) {
        super(display);
    }

    public void execute(FilterMode mode, String path){
        Mat im = imread(path);
        Filter.run(mode, im);

        display.draw(im);
    }
}
