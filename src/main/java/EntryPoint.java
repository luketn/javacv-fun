import com.mycodefu.javacv.fun.*;
import com.mycodefu.javacv.fun.classifiers.Classifiers;
import com.mycodefu.javacv.fun.filters.Filter.FilterMode;
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

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
//            args = new String[]{Actions.imageClassifier.name(), "sampleImages/boardwalk.jpg", "sampleImages/boardwalk-out.jpg", Classifiers.fullBody.name()};
            args = new String[]{Actions.videoDisplay.name(), FilterMode.smiles.name()};
//            args = new String[]{Actions.videoClassifier.name(), Classifiers.faces.name(), "0"};
//            args = new String[]{Actions.imageDisplay.name(), FilterMode.findRectangles.name(), "sampleImages/shapes.png"};
//            args = new String[]{Actions.imageFile.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png", "sampleImages/shapes-out.png"};
//            args = new String[]{Actions.videoDisplay.name(), FilterMode.findBlue.name()};
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
                Classifiers classifier = args.length > 1 ? Classifiers.valueOf(args[1]) : Classifiers.faces;
                VideoClassifier videoClassifier = new VideoClassifier(classifier);

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
                String fileIn = args.length > 1 ? args[1] : null;
                String fileOut = args.length > 2 ? args[2] : null;
                Classifiers classifier = args.length > 3 ? Classifiers.valueOf(args[3]) : Classifiers.faces;

                ImageClassifier imageClassifier = new ImageClassifier(classifier);
                imageClassifier.execute(fileIn, fileOut);
                break;
            }
            case videoDisplay:
            default: {
                FilterMode mode = FilterMode.valueOf(args[1]);

                VideoDisplay videoDisplay = new VideoDisplay();
                if (args.length > 2) {
                    final String videoSourceArg = args[2];
                    if (StringUtils.isNumeric(videoSourceArg)) {
                        videoDisplay.execute(mode, Integer.parseInt(videoSourceArg));
                    } else {
                        videoDisplay.execute(mode, videoSourceArg);
                    }
                } else {
                    videoDisplay.execute(mode, 0);
                }
                break;
            }
        }
    }
}
