package com.mycodefu.javacv.fun.converters;

import org.opencv.core.Mat;

/**
 * Hashable key-safe representation of an image width, height and type.
 *
 * Intended for use in creating buffered image maps.
 *
 * Created by lthompson on 10/05/15.
 */
class ImageTypeKey {
    private final int width;
    private final int height;
    private final int type;

    public ImageTypeKey(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.type = depth;
    }

    public ImageTypeKey(Mat imageMatrix) {
        this(imageMatrix.width(), imageMatrix.height(), imageMatrix.type());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageTypeKey that = (ImageTypeKey) o;

        if (width != that.width) return false;
        if (height != that.height) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "ImageTypeKey{" +
                "width=" + width +
                ", height=" + height +
                ", type=" + type +
                '}';
    }
}
