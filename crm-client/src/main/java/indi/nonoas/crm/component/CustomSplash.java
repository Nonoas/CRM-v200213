package indi.nonoas.crm.component;

import indi.jfxmaker.splash.Splash;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:58
 */
public class CustomSplash extends Splash {

    private static final String DEFAULT_IMAGE = "config/splash/Splash.png";

    private static final double DEFAULT_WIDTH = 700d;
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
        imageView.setFitWidth(DEFAULT_WIDTH);
        imageView.setPreserveRatio(true);

        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(imageView.getFitWidth());
        splashProgressBar.setPrefHeight(4);

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
