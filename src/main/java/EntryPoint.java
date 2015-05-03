import com.mycodefu.javacv.fun.ImageDisplay;
import com.mycodefu.javacv.fun.ImageFile;
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
        imageFile
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
//            args = new String[]{Actions.videoDisplay.name(), FilterMode.greyscale.name()};
//            args = new String[]{Actions.imageDisplay.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png"};
            args = new String[]{Actions.imageFile.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png", "sampleImages/shapes-out.png"};
        }

        OpenCV.loadLibrary();

        Actions action = Actions.valueOf(args[0]);
        FilterMode mode = FilterMode.valueOf(args[1]);

        switch(action) {
            case imageFile: {
                final String pathIn = args[2];
                final String pathOut = args[3];

                ImageFile imageFile = new ImageFile();
                imageFile.execute(mode, pathIn, pathOut);
                break;
            }
            case imageDisplay: {
                final String path = args[2];

                ImageDisplay imageDisplay = new ImageDisplay();
                imageDisplay.execute(mode, path);
                break;
            }
            case videoDisplay:
            default: {
                VideoDisplay videoDisplay = new VideoDisplay();
                videoDisplay.execute(mode);
                break;
            }
        }
    }
}
