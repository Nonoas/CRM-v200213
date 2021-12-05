package indi.jfxmaker.control;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Nonoas
 * @datetime 2021/12/4 21:35
 */
public class SysButton extends Button {

    public static final String STYLE_CLASS = "sys-button";

    public static final String STYLE_PATH = "/css/style.css";

    private final static Double DEFAULT_SIZE = 40.0;

    private final static Double DEFAULT_ICON_SIZE = 22.0;

    {
        getStylesheets().add(STYLE_PATH);
        getStyleClass().add(STYLE_CLASS);
        setPrefSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public SysButton() {
    }

    public SysButton(String text) {
        super(text);
    }

    public SysButton(ImageView iv) {
        setIcon(iv);
    }

    /**
     * 在 {@code setGraphic}之前，设置 ImageView 大小为 {@link SysButton#DEFAULT_ICON_SIZE}
     *
     * @param iv 传入图标
     */
    public void setIcon(ImageView iv) {
        iv.setFitWidth(DEFAULT_ICON_SIZE);
        iv.setFitHeight(DEFAULT_ICON_SIZE);
        setGraphic(iv);
    }

}
