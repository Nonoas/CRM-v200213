package indi.jfxmaker.control;

import javafx.scene.control.Button;

/**
 * @author Nonoas
 * @datetime 2021/12/4 21:35
 */
public class SysButton extends Button {

    public static final String styleClass = "sys-button";

    private final static Double DEFAULT_SIZE = 40.0;

    public SysButton() {
        this("");
    }

    public SysButton(String text) {
        super(text);
        getStylesheets().add("/css/style.css");
        getStyleClass().add(styleClass);
        setPrefSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

}
