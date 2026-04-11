package indi.nonoas.crm.component;

import indi.jfxmaker.splash.Splash;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2020-09-01 20:58
 */
public class CustomSplash extends Splash {

    private static final String DEFAULT_IMAGE = "/images/splash/Splash.png";

    @Override
    public Parent getParent() {
        final ProgressBar splashProgressBar = new ProgressBar();
        final VBox vbox = new VBox();

        URL imageUrl = getClass().getResource(DEFAULT_IMAGE);
        if (imageUrl != null) {
            ImageView imageView = new ImageView(imageUrl.toExternalForm());
            imageView.setFitWidth(700);
            imageView.setPreserveRatio(true);
            splashProgressBar.setPrefWidth(imageView.getFitWidth());
            vbox.getChildren().addAll(imageView, splashProgressBar);
        } else {
            splashProgressBar.setPrefWidth(700);
            vbox.getChildren().add(splashProgressBar);
        }

        return vbox;
    }

    @Override
    public boolean visible() {
        return true;
    }

    @Override
    public String getImagePath() {
        return DEFAULT_IMAGE;
    }
}