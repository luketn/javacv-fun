package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.filters.Filter;
import com.mycodefu.javacv.fun.filters.Filter.FilterMode;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * Read an image, apply the given filter and write out the result.
 *
 * Created by lthompson on 3/05/15.
 */
public class ImageFile {
    public void execute(FilterMode mode, String pathIn, String pathOut) {
        Mat im = imread(pathIn);
        Filter.run(mode, im);
        imwrite(pathOut, im);
    }
}
