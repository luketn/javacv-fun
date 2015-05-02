package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.display.DisplayImages;
import com.mycodefu.javacv.fun.display.DisplayImagesSwing;
import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.filters.FilterMode;
import org.opencv.core.Mat;

import static org.opencv.highgui.Highgui.imread;

/**
 * Image fun.
 *
 * Created by lthompson on 2/05/15.
 */
public class ImageDisplay {

    public void execute(FilterMode mode, String path){
        final DisplayImages display = new DisplayImagesSwing();

        Mat im = imread(path);
        Filter.run(mode, im);

        display.updateImage(im);
    }
}
