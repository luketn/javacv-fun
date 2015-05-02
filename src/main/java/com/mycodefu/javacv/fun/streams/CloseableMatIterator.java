package com.mycodefu.javacv.fun.streams;

import org.opencv.core.Mat;

import java.util.Iterator;

/**
 * Interface for iterators which return images as a matrix of pixels and can be closed.
 *
 * Created by lthompson on 2/05/15.
 */
public interface CloseableMatIterator extends Iterator<Mat>, AutoCloseable {

}
