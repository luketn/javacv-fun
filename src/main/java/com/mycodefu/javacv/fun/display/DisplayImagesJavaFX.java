package com.mycodefu.javacv.fun.display;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Display images on screen for debugging / just for fun.
 *
 * Created by lthompson on 2/05/15.
 */
public class DisplayImagesJavaFX extends DisplayImages {
    protected byte[] buffer = null;
    protected ImageView imageView = null;
    protected Scene scene = null;
    protected final Stage stage;

    public DisplayImagesJavaFX(Stage stage){
        super();
        this.stage = stage;
    }

    @Override
    public void updateImage(Mat image){
        initializeFromFirstImage(image);

        image.get(0, 0, buffer);

        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        final WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);

        imageView.setImage(writableImage);

    }

    protected void initializeFromFirstImage(Mat image) {
        if (buffer == null) {
            buffer = new byte[image.channels()*image.cols()*image.rows()];
        }
        if (scene == null) {
            imageView = new ImageView();
            scene = new Scene(new Group(imageView), image.width(), image.height(), Color.BLACK);
            stage.setScene(scene);
            stage.show();
        }
    }

}
