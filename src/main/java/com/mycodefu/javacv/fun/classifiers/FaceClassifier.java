package com.mycodefu.javacv.fun.classifiers;

/**
 * Face classifier.
 *
 * Created by lthompson on 20/05/15.
 */
public class FaceClassifier extends Classifier {
    public static final String CLASSIFIER_PATH = FaceClassifier.class.getResource("/haarcascade_frontalface_alt.xml").getFile();

    public FaceClassifier() {
        super(CLASSIFIER_PATH);
    }

}
