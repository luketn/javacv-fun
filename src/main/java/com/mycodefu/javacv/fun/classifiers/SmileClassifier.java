package com.mycodefu.javacv.fun.classifiers;

/**
 * Created by lthompson on 27/05/15.
 */
public class SmileClassifier extends Classifier{
    public static final String CLASSIFIER_PATH = FaceClassifier.class.getResource("/haarcascade_smile.xml").getFile();

    public SmileClassifier() {
        super(CLASSIFIER_PATH);
    }
}
