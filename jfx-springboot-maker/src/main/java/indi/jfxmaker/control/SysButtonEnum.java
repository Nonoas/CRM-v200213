package indi.jfxmaker.control;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Nonoas
 * @datetime 2021/12/4 20:44
 */
public enum SysButtonEnum {

    INSTANCE;

    SysButtonEnum() {

    }

    public static Button minimizeButton() {
        Button button = new SysButton("-");
        return button;
    }

    public static Button maximizeButton() {
        Button button = new SysButton();
        ImageView imageView = new ImageView("/icon/sys_max.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
        return button;
    }

    public static Button closeButton() {
        Button button = new SysButton("X");
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: red;-fx-text-fill: white");
            } else {
                button.setStyle("-fx-background-color: transparent;-fx-text-fill:black");
            }
        });
        return button;
    }

}
