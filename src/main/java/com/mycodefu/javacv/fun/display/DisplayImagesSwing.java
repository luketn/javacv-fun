package com.mycodefu.javacv.fun.display;

import javax.swing.*;
import java.awt.image.BufferedImage;

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
        frame.getGraphics().drawImage(image, 0, 0, (img, infoflags, x, y, width, height) -> false);
    }

    @Override
    protected void initializeWindow(int width, int height) {
        if (frame == null) {
            frame = new JFrame();
            frame.setBounds(0, 0, width, height);
            frame.setVisible(true);
        }
    }

}
