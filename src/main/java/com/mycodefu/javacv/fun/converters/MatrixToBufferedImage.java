package com.mycodefu.javacv.fun.converters;

import org.apache.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.HashMap;
import java.util.Map;

/**
 * Functionality for managing the conversion from matrix images to BufferedImage structures in a memory efficient way.
 *
 * Keeps a single BufferedImage as the active drawable images, overwriting its internal byte structure each time the
 * image is updated.
 *
 * Created by lthompson on 2/05/15.
 */
public class MatrixToBufferedImage {
    private static final Logger log = Logger.getLogger(MatrixToBufferedImage.class);

    protected Map<ImageTypeKey, BufferedImage> images = new HashMap<>();
    protected byte[] imageBytes = null;
    protected BufferedImage image = null;

    /**
     * Update the bytes in the single BufferedImage maintained by the class. Return the buffered image.
     *
     * It is the responsibility of the caller not to keep references to this image beyond the next time this method is called.
     */
    public BufferedImage convert(Mat imageMatrix) {
        initializeBuffers(imageMatrix);

        imageMatrix.get(0, 0, imageBytes);

        return image;
    }

    private void initializeBuffers(Mat imageMatrix) {
        if (this.image == null || !isMatch(imageMatrix, this.image)) {
            final ImageTypeKey key = new ImageTypeKey(imageMatrix.width(), imageMatrix.height(), imageMatrix.type());

            if (images.containsKey(key)) {
                this.image = images.get(key);
                this.imageBytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            } else {
                this.image = new BufferedImage(imageMatrix.width(), imageMatrix.height(), key.getImageType());
                this.imageBytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                images.put(key, this.image);

                log.debug("Created new image buffer " + image.toString());
            }
        }
    }

    protected boolean isMatch(Mat imageMatrix, BufferedImage image) {
        if (imageMatrix.width() != image.getWidth()) {
            return false;
        } else if (imageMatrix.height() != image.getHeight()) {
            return false;
        } else if (imageMatrix.type() == CvType.CV_8U && image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            return false;
        } else if (imageMatrix.type() == CvType.CV_8UC3 && image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            return false;
        }
        return true;
    }

    static class ImageTypeKey {
        private int width;
        private int height;
        private int type;

        public ImageTypeKey(int width, int height, int depth) {
            this.width = width;
            this.height = height;
            this.type = depth;
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

        public int getImageType() {
            if (type == CvType.CV_8U) {
                return BufferedImage.TYPE_BYTE_GRAY;
            } else {
                return BufferedImage.TYPE_3BYTE_BGR;
            }
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
}
