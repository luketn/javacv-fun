# javacv-fun
A Java app which uses the C++ opencv library with JNI bindings to do fun things with images and video streams.

Executable JAR
--------------
This is an executable JAR package. To run it, use the following command:

java -jar javacv-fun.jar

It has some optional parameters for different actions and modes e.g.
java -jar javacv-fun.jar video blurry
java -jar javacv-fun.jar video normal
java -jar javacv-fun.jar video edges

Currently only tested on Mac OSX Yosemite and expected to work on Linux desktop as well. This is because it is using a Maven dependency to pull in OpenCV, which only has these two platforms supported right now.