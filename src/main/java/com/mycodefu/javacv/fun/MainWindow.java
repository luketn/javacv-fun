package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.classifiers.Classifiers;
import com.mycodefu.javacv.fun.display.DisplayImages;
import nu.pattern.OpenCV;

import javax.swing.*;

/**
 * Created by lthompson on 10/1/17.
 */
public class MainWindow {
    public static void main(String[] args) {
        OpenCV.loadLocally();

        DisplayImages display = new DisplayImages();

        JFrame frame = new JFrame();

        //todo: add a few controls to switch between modes and display results etc..

        frame.getContentPane().add(display);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        VideoClassifier videoClassifier = new VideoClassifier(Classifiers.faces, display);
        videoClassifier.execute(0);
    }
}
