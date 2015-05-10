package com.mycodefu.javacv.fun.converters;

import org.apache.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.HashMap;
import java.util.Map;

import static java.awt.image.BufferedImage.*;

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
    protected ImageTypeKey currentImageType = null;

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
        ImageTypeKey imageType = new ImageTypeKey(imageMatrix);
        if (this.image == null || !imageType.equals(currentImageType)) {
            if (images.containsKey(imageType)) {
                image = images.get(imageType);
            } else {
                image = createBufferedImage(imageMatrix);
                images.put(imageType, image);

                log.debug("Created new image buffer " + image.toString());
            }
            imageBytes = getBufferedImageBytes(image);
        }
    }

    private static byte[] getBufferedImageBytes(BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    private BufferedImage createBufferedImage(Mat imageMatrix) {
        return new BufferedImage(imageMatrix.width(), imageMatrix.height(), getImageType(imageMatrix));
    }

    public static int getImageType(Mat image) {
        if (image.type() == CvType.CV_8U) {
            return TYPE_BYTE_GRAY;
        } else {
            return TYPE_3BYTE_BGR;
        }
    }

}
