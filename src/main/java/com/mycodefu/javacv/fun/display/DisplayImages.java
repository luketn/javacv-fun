package com.mycodefu.javacv.fun.display;

import com.mycodefu.javacv.fun.converters.MatrixToBufferedImage;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.max;

/**
 * Create a Java Swing window in the matrix dimensions and draw images on it.
 *
 * Assumes that the image dimensions will not vary. Reuses the buffers to improve the efficiency based on this assumption.
 *
 * Created by lthompson on 2/05/15.
 */
public class DisplayImages {
    protected JFrame frame;
    private final MatrixToBufferedImage converter;

    public DisplayImages() {
        frame = null;
        converter = new MatrixToBufferedImage();
    }

    public void draw(Mat imageMatrix) {
        initializeWindow(imageMatrix.width(), imageMatrix.height());

        final BufferedImage image = converter.convert(imageMatrix);

        frame.getGraphics().drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), (img, infoflags, x, y, width, height) -> false);
    }

    protected void initializeWindow(int width, int height) {
        if (frame == null) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            frame = new JFrame();
            frame.setBounds(0, 0, max(width, screenSize.width), max(height, screenSize.height));
            frame.setVisible(true);
        }
    }

}
