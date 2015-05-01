package com.mycodefu.javacv.fun.display;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Create a Java Swing window and display image pixel matrixes in it.
 *
 * Assumes that the image dimensions will not vary. Reuses the buffers to improve the efficiency based on this assumption.
 *
 * Created by lthompson on 2/05/15.
 */
public class DisplayImagesSwing extends DisplayImages {
    protected byte[] buffer = null;
    protected JFrame frame = null;
    protected ImageIcon imageIcon = null;

    @Override
    public void updateImage(Mat image) {
        initializeFromFirstImage(image);

        imageIcon.setImage(toBufferedImage(image));
        frame.repaint();
    }


    protected Image toBufferedImage(Mat m){
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        m.get(0, 0, buffer); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;

    }

    protected void initializeFromFirstImage(Mat image) {
        if (buffer == null) {
            buffer = new byte[image.channels()*image.cols()*image.rows()];
        }
        if (frame == null) {
            frame = new JFrame();
            imageIcon = new ImageIcon(new BufferedImage(image.width(),image.height(), BufferedImage.TYPE_3BYTE_BGR));

            frame.getContentPane().add(new JLabel(imageIcon));
            frame.pack();
            frame.setVisible(true);
        }
    }
}
