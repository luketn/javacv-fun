import com.mycodefu.javacv.fun.ImageDisplay;
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
        video,
        image
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
//            args = new String[]{Actions.video.name(), FilterMode.findBlue.name()};
            args = new String[]{Actions.image.name(), FilterMode.findBlue.name(), "sampleImages/shapes.png"};
        }

        OpenCV.loadLibrary();

        Actions action = Actions.valueOf(args[0]);
        FilterMode mode = FilterMode.valueOf(args[1]);

        switch(action) {
            case image: {
                final String path = args[2];

                ImageDisplay imageDisplay = new ImageDisplay();
                imageDisplay.execute(mode, path);
                break;
            }
            case video:
            default: {
                VideoDisplay videoDisplay = new VideoDisplay();
                videoDisplay.execute(mode);
                break;
            }
        }
    }
}
