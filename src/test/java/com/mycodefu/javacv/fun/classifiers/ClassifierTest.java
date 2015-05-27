package com.mycodefu.javacv.fun.classifiers;

import com.google.common.base.Stopwatch;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opencv.highgui.Highgui.imread;

/**
 * Created by lthompson on 27/05/15.
 */
public class ClassifierTest {

    @BeforeClass
    public static void setup(){
        nu.pattern.OpenCV.loadLibrary();
    }

    @Test
    public void testDetectFeatures() throws Exception {
        Classifier classifer = new FaceClassifier();

        final Mat face = imread("sampleImages/face.png");

        for (int i = 0; i < 5; i++) {

            Stopwatch timeClassify = new Stopwatch().start();
            final List<Rect> rects = classifer.detectFeatures(face);
            timeClassify.stop();

            System.out.println("Took " + timeClassify + " to classify face.");
            assertEquals(1, rects.size());

            assertEquals(new Rect(602, 220, 262, 262), rects.get(0));

            assertTrue(timeClassify.elapsed(TimeUnit.MILLISECONDS) < 100);
        }
    }

}