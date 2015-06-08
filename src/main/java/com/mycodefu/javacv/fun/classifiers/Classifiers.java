package com.mycodefu.javacv.fun.classifiers;

/**
 * Created by lthompson on 27/05/15.
 */
public enum Classifiers {
    faces("/haarcascade_frontalface_alt.xml"),
    smiles("/haarcascade_smile.xml"),
    eyes("/haarcascade_eye.xml"),
    fullBody("/haarcascade_fullbody.xml");

    private String path;

    Classifiers(String path) {
        this.path = path;
    }

    public Classifier create(double scaleFactor) {
        return new Classifier(Classifier.class.getResource(path).getFile(), scaleFactor);
    }
    public Classifier create() {
        return new Classifier(Classifier.class.getResource(path).getFile());
    }
}