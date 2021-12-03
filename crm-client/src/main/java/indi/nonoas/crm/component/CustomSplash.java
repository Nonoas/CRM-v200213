package indi.nonoas.crm.component;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:58
 */
public class CustomSplash extends SplashScreen {

    private static final String DEFAULT_IMAGE = "config/splash/Splash.png";
    private final boolean flag;

    public CustomSplash() {
        flag = new File(DEFAULT_IMAGE).exists();
    }

    // 自定义启动界面图片
    @Override
    public Parent getParent() {

        if (!flag) {
            return super.getParent();
        }

        ImageView imageView = new ImageView("file:" + DEFAULT_IMAGE);
        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(imageView.getImage().getWidth());
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, splashProgressBar);
        return vbox;
    }

    /**
     * 设置为可见
     */
    @Override
    public boolean visible() {
        return true;
    }

    /**
     * 获取图片路径
     *
     * @return 图片路径
     */
    @Override
    public String getImagePath() {
        if (!flag) {
            return super.getImagePath();
        }
        return DEFAULT_IMAGE;
    }
}
