package indi.jfxmaker.splash;

import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;

/**
 * A default standard splash pane implementation Subclass it and override it's
 * methods to customize with your own behavior. Be aware that you can not use
 * Spring features here yet.
 *
 * @author Felix Roske
 * @author Andreas Jay
 */
public class Splash {

    private static final String DEFAULT_IMAGE = "images/splash/Splash.png";

    /**
     * 覆盖它以创建您自己的启动窗格父节点。
     *
     * @return Parent对象
     */
    public Parent getParent() {
        final ProgressBar splashProgressBar = new ProgressBar();
        final VBox vbox = new VBox();

        final URL imageUrl = getClass().getResource(getImagePath());
        if (imageUrl != null) {
            final ImageView imageView = new ImageView(imageUrl.toExternalForm());
            splashProgressBar.setPrefWidth(imageView.getImage().getWidth());
            vbox.getChildren().addAll(imageView, splashProgressBar);
        } else {
            splashProgressBar.setPrefWidth(500);
            vbox.getChildren().add(splashProgressBar);
        }

        return vbox;
    }

    /**
     * Customize if the splash screen should be visible at all.
     *
     * @return true by default
     */
    public boolean visible() {
        return true;
    }

    /**
     * Use your own splash image instead of the default one.
     *
     * @return "config/splash/Splash.png"
     */
    public String getImagePath() {
        return DEFAULT_IMAGE;
    }

}
