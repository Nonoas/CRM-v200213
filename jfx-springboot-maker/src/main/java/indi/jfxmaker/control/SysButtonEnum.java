package indi.jfxmaker.control;


import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

/**
 * @author Nonoas
 * @datetime 2021/12/4 20:44
 */
public enum SysButtonEnum {

    MINIMIZE(SysButtonEnum.minimizeButton()),
    MAXIMIZE(SysButtonEnum.maximizeButton()),
    CLOSE(SysButtonEnum.closeButton());

    private final Button btn;

    SysButtonEnum(Button button) {
        this.btn = button;
    }

    public Button get() {
        return this.btn;
    }

    private static Button minimizeButton() {
        Button button = new SysButton(new ImageView("/icon/sys_min.png"));
        button.setStyle("-fx-background-color: transparent");
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #ddd");
            } else {
                button.setStyle("-fx-background-color: transparent");
            }
        });
        return button;
    }

    private static Button maximizeButton() {
        Button button = new SysButton(new ImageView("/icon/sys_max.png"));
        button.setStyle("-fx-background-color: transparent");
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #dedede");
            } else {
                button.setStyle("-fx-background-color: transparent");
            }
        });
        return button;
    }

    private static Button closeButton() {
        ImageView iv = new ImageView("/icon/sys_close.png");

        ImageView ivHover = new ImageView("/icon/sys_close.png");
        ivHover.setEffect(new ColorAdjust(1, 1, 2, 1));

        SysButton button = new SysButton(iv);
        button.setStyle("-fx-background-color: transparent");
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #f55");
                button.setIcon(ivHover);
            } else {
                button.setStyle("-fx-background-color: transparent");
                button.setIcon(iv);
            }
        });
        return button;
    }

}
