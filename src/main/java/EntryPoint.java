import com.mycodefu.javacv.fun.VideoFun;
import javafx.stage.Stage;
import nu.pattern.OpenCV;

/**
 * User: luke
 * Date: 7/05/13
 * Time: 6:50 AM
 */
public class EntryPoint extends javafx.application.Application {
    public static final String VIDEO_ACTION = "video";

    @Override
    public void start(Stage stage) throws Exception {
        String[] args = new String[]{VIDEO_ACTION, "edges"};

        OpenCV.loadLibrary();

        String action = args[0];
        String mode = args[1];

        switch(action) {
            case "video":
            default: {
                VideoFun videoFun = new VideoFun();
                videoFun.execute(mode, stage);
                break;
            }
        }
    }
}
