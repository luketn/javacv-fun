import com.mycodefu.javacv.fun.VideoDisplay;
import com.mycodefu.javacv.fun.VideoDisplay.VideoMode;
import nu.pattern.OpenCV;

/**
 * User: luke
 * Date: 7/05/13
 * Time: 6:50 AM
 */
public class EntryPoint {
    public enum Actions {
        video
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            args = new String[]{Actions.video.name(), VideoMode.alternateColorGrey.name()};
        }

        OpenCV.loadLibrary();

        Actions action = Actions.valueOf(args[0]);
        VideoMode mode = VideoMode.valueOf(args[1]);

        switch(action) {
            case video:
            default: {
                VideoDisplay videoDisplay = new VideoDisplay();
                videoDisplay.execute(mode);
                break;
            }
        }
    }
}
