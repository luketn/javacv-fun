import com.mycodefu.javacv.fun.*;
import com.mycodefu.javacv.fun.filters.FilterMode;
import nu.pattern.OpenCV;
import org.apache.commons.lang3.StringUtils;

/**
 * User: luke
 * Date: 7/05/13
 * Time: 6:50 AM
 */
public class EntryPoint {
    public enum Actions {
        imageClassifier,
        videoClassifier,
        videoDisplay,
        imageDisplay,
        imageFile,
        imageWebServer
    }

    public enum Classifiers {
        faces,
        smiles
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
//            args = new String[]{Actions.imageClassifier.name(), "sampleImages/boardwalk.jpg", "sampleImages/boardwalk-out.jpg"};
//            args = new String[]{Actions.videoClassifier.name(), "/Users/lthompson/Downloads/heli7.mov"};
            args = new String[]{Actions.videoClassifier.name(), Classifiers.smiles.name(), "0"};
//            args = new String[]{Actions.imageDisplay.name(), FilterMode.findTriangles.name(), "sampleImages/shapes.png"};
//            args = new String[]{Actions.imageFile.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png", "sampleImages/shapes-out.png"};
//            args = new String[]{Actions.imageWebServer.name(), "8080"};
        }

        OpenCV.loadLibrary();

        Actions action = Actions.valueOf(args[0]);
        switch(action) {
            case imageFile: {
                FilterMode mode = FilterMode.valueOf(args[1]);
                final String pathIn = args[2];
                final String pathOut = args[3];

                ImageFile imageFile = new ImageFile();
                imageFile.execute(mode, pathIn, pathOut);
                break;
            }
            case imageWebServer: {
                int port = args.length > 1 ? Integer.parseInt(args[1]) : 80;
                ImageWebServer.run(port);
                break;
            }
            case imageDisplay: {
                FilterMode mode = FilterMode.valueOf(args[1]);
                final String path = args[2];

                ImageDisplay imageDisplay = new ImageDisplay();
                imageDisplay.execute(mode, path);
                break;
            }
            case videoClassifier: {
                VideoClassifier videoClassifier;
                if (args.length > 1) {
                    switch (Classifiers.valueOf(args[1])) {
                        case smiles: {
                            videoClassifier = VideoClassifier.smiles();
                            break;
                        }
                        case faces:
                        default: {
                            videoClassifier = VideoClassifier.faces();
                            break;
                        }
                    }
                } else {
                    videoClassifier = VideoClassifier.faces();
                }

                if (args.length > 2) {
                    final String videoSourceArg = args[2];
                    if (StringUtils.isNumeric(videoSourceArg)) {
                        videoClassifier.execute(Integer.parseInt(videoSourceArg));
                    } else {
                        videoClassifier.execute(videoSourceArg);
                    }
                } else {
                    videoClassifier.execute(0);
                }
                break;
            }
            case imageClassifier: {
                ImageFaceClassifier imageFaceClassifier = new ImageFaceClassifier();
                String fileIn = args.length > 1 ? args[1] : null;
                String fileOut = args.length > 2 ? args[2] : null;

                imageFaceClassifier.execute(fileIn, fileOut);
                break;
            }
            case videoDisplay:
            default: {
                FilterMode mode = FilterMode.valueOf(args[1]);

                VideoDisplay videoDisplay = new VideoDisplay();
                videoDisplay.execute(mode);
                break;
            }
        }
    }
}
