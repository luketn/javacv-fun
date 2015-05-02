package com.mycodefu.javacv.fun.display;

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
public class DisplayImagesSwing extends DisplayImages {
    protected JFrame frame = null;

    @Override
    protected void draw(BufferedImage image) {
        frame.getGraphics().drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), (img, infoflags, x, y, width, height) -> false);
    }

    @Override
    protected void initializeWindow(int width, int height) {
        if (frame == null) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            frame = new JFrame();
            frame.setBounds(0, 0, max(width, screenSize.width), max(height, screenSize.height));
            frame.setVisible(true);
        }
    }

}
