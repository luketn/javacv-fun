import com.mycodefu.javacv.fun.ImageDisplay;
import com.mycodefu.javacv.fun.ImageFile;
import com.mycodefu.javacv.fun.ImageWebServer;
import com.mycodefu.javacv.fun.VideoDisplay;
import com.mycodefu.javacv.fun.filters.FilterMode;
import nu.pattern.OpenCV;

/**
 * User: luke
 * Date: 7/05/13
 * Time: 6:50 AM
 */
public class EntryPoint {
    public enum Actions {
        videoDisplay,
        imageDisplay,
        imageFile,
        imageWebServer
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
//            args = new String[]{Actions.videoDisplay.name(), FilterMode.normal.name()};
//            args = new String[]{Actions.imageDisplay.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png"};
//            args = new String[]{Actions.imageFile.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png", "sampleImages/shapes-out.png"};
            args = new String[]{Actions.imageWebServer.name(), "8080"};
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
