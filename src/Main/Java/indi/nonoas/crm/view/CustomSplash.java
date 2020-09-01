package indi.nonoas.crm.view;

import de.felixroske.jfxsupport.SplashScreen;
import indi.nonoas.crm.config.ImageSrc;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:58
 */
public class CustomSplash extends SplashScreen {

    private static final String DEFAULT_IMAGE = "config/splash/Splash.png";
    private final ImageView imageView = new ImageView("file:" + DEFAULT_IMAGE);
    private final boolean flag;

    public CustomSplash() {
        flag = new File(DEFAULT_IMAGE).exists();
    }

    /**
     * 覆盖它以创建自己的初始窗格父节点。
     *
     * @return 标准图片
     */
    @Override
    public Parent getParent() {

        if (!flag)
            return super.getParent();

        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(imageView.getImage().getWidth());
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, splashProgressBar);
        return vbox;
    }

    /**
     * 自定义启动屏幕是否完全可见。
     *
     * @return 默认为true
     */
    @Override
    public boolean visible() {
        return true;
    }

    /**
     * 使用您自己的启动图像，而不是默认的启动图像。
     *
     * @return "图像路径"
     */
    @Override
    public String getImagePath() {
        if (!flag)
            return super.getImagePath();
        return DEFAULT_IMAGE;
    }
}
