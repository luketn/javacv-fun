# javacv-fun
A Java app which uses the C++ opencv library with JNI bindings to do fun things with images and video streams.

Executable JAR
--------------
This is an executable JAR package. To run it, use the following command:
```
java -jar javacv-fun.jar
```

To build, run the Maven package phase. Building the package produces a ZIP file containing the JAR and libraries in a nice easy to understand structure.

It has some optional parameters for different actions and modes e.g.

```
java -jar javacv-fun.jar videoDisplay normal
java -jar javacv-fun.jar videoDisplay edges
java -jar javacv-fun.jar imageFile findBlue sampleImages/shapes.png sampleImages/shapes-out.png
```

Currently tested on Mac OSX Yosemite and AWS Linux. 

It is using the nu.pattern.opencv Maven dependency to pull in OpenCV, which only has these two platforms supported right now.

To run on Linux the following commands were run to update to Java 8 and execute the JAR:

```
unzip javacv-fun.zip
sudo yum install -y java-1.8.0-openjdk
sudo alternatives --set java /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java
java -jar javacv-fun.jar imageFile findBlue sampleImages/shapes.png sampleImages/shapes-out.png
```


Ideas for More Fun
------------------

Here are some more ideas for future additions to the project:
* Anylgraph 3d
** Get a pair of webcams a fixed distance apart
** Use this technique: http://sonny03.blogspot.com.au/2012/02/redcyan-anaglyph-using-opencv-and-two.html
** Display an anylgraph image on screen and use a pair of red/green anylgraph glasses to view 3d

