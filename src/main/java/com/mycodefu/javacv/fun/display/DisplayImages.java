package com.mycodefu.javacv.fun.display;

import com.mycodefu.javacv.fun.converters.MatrixToBufferedImage;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Create a Java Swing window in the matrix dimensions and draw images on it.
 *
 * Assumes that the image dimensions will not vary. Reuses the buffers to improve the efficiency based on this assumption.
 *
 * Created by lthompson on 2/05/15.
 */
public class DisplayImages extends JPanel {
    private final MatrixToBufferedImage converter;
    private Mat currentImage;

    public DisplayImages() {
        converter = new MatrixToBufferedImage();
    }

    @Override
    public Dimension getPreferredSize() {
        return getDimensions();
    }

    @Override
    public Dimension getSize(Dimension rv) {
        return getDimensions();
    }

    private Dimension getDimensions() {
        if (currentImage == null) {
            return new Dimension(640, 480);
        } else {
            return new Dimension(currentImage.width(), currentImage.height());
        }
    }

    public void draw(Mat imageMatrix) {
        currentImage = imageMatrix;
        draw();
    }

    private void draw() {
        if (currentImage != null) {
            final BufferedImage image = converter.convert(currentImage);
            getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), (img, infoflags, x, y, width, height) -> false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw();
    }
}
