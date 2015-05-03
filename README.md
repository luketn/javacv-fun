# javacv-fun
A Java app which uses the C++ opencv library with JNI bindings to do fun things with images and video streams.

Executable JAR
--------------
To build, run the Maven package phase.

This is an executable JAR package. To run it, use the following command:

java -jar javacv-fun.jar

It has some optional parameters for different actions and modes e.g.

  java -jar javacv-fun.jar videoDisplay blurry
  java -jar javacv-fun.jar videoDisplay normal
  java -jar javacv-fun.jar videoDisplay edges

Currently tested on Mac OSX Yosemite and AWS Linux. 

It is using the nu.pattern.opencv Maven dependency to pull in OpenCV, which only has these two platforms supported right now.

To run on Linux the following commands were run to update to Java 8 and execute the JAR:

```
unzip javacv-fun.zip
sudo yum install -y java-1.8.0-openjdk
sudo alternatives --set java /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java
java -jar javacv-fun.jar imageFile findBlue sampleImages/shapes.png sampleImages/shapes-out.png
```

